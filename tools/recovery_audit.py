#!/usr/bin/env python3
from __future__ import annotations

import argparse
import collections
import json
import re
from pathlib import Path

WARNING_STRINGS = (
    "Unable to fully structure code",
    "Loose catch block",
    "Removed try catching itself",
    "Could not reconstruct switch",
    "Could not resolve type clashes",
    "Exception decompiling",
    "Decompilation failed",
)

LINE_PATTERNS = {
    "synthetic_block_label": re.compile(r"^\s*block\d+\s*:"),
    "labelled_break_continue": re.compile(r"\b(?:break|continue)\s+block\d+\s*;"),
    "monitor_exit_artifact": re.compile(r"MonitorExit\[|shouldn't be in output"),
    "void_declaration_warning": re.compile(r"WARNING\s*-\s*void declaration", re.IGNORECASE),
    "cfr_warning_comment": re.compile(
        r"(?i)(?:CFR|decompil|unable to fully structure|loose catch block|removed try catching itself|could not reconstruct switch)"
    ),
    "suspicious_throwaway_ternary_assignment": re.compile(
        r"\b(?:double|float|int|long|boolean)\s+\w+\s*=.*\?[^;]*:\s*\(\w+\s*="
    ),
}

FINALLY_CONTROL = re.compile(
    r"finally\s*\{(?P<body>.{0,1600}?)\}",
    re.DOTALL,
)

EMPTY_HANDLER_THEN_BLOCK = re.compile(
    r"(?:catch\s*\([^)]*\)|finally)\s*\{\s*\}\s*\{",
    re.DOTALL,
)

CATCH_THROWABLE = re.compile(r"catch\s*\(\s*Throwable\b")


# Silent semantic-loss families recovered elsewhere in this tree.
# These are intentionally narrow so that they complement, rather than
# duplicate, the broad CFR comment/label inventory.
HASH_SELECTOR_BLOCK = re.compile(
    r"(?P<decl>\bint\s+(?P<selector>\w+)\s*=\s*(?:-?1|0)\s*;)"
    r"(?P<body>.{0,2600}?switch\s*\(\s*(?P<textvar>\w+)\.hashCode\(\)\s*\)"
    r".{0,2200}?switch\s*\(\s*(?P=selector)\s*\))",
    re.DOTALL,
)

PARTIAL_TERNARY_INIT = re.compile(
    r"\b(?P<type>double|float|int|long|boolean)\s+(?P<target>\w+)\s*;\s*"
    r"(?P<gap>.{0,700}?)"
    r"\b(?:double|float|int|long|boolean)\s+\w+\s*=\s*[^;?]+\?[^:;]+:"
    r"\s*\(\s*(?P=target)\s*=",
    re.DOTALL,
)

COLLAPSED_ARRAY_LOOP = re.compile(
    r"\bint\s+(?P<idx>\w+)\s*=\s*0\s*;\s*"
    r"(?P<array_type>[\w.$<>?]+(?:\[\])+)\s+(?P<array>\w+)\s*=\s*[^;]+;\s*"
    r"int\s+(?P<len>\w+)\s*=\s*(?P=array)\.length\s*;\s*"
    r"if\s*\(\s*(?P=idx)\s*>=\s*(?P=len)\s*\)\s*(?:continue|break|return)\b",
    re.DOTALL,
)

EMPTY_IF_BODY = re.compile(
    r"if\s*\((?P<cond>[^{}\n]{1,220})\)\s*\{\s*\}",
    re.MULTILINE,
)

READ_ASSIGNMENT = re.compile(
    r"(?P<var>\w+)\s*=\s*[^;\n]*\.read\s*\(",
)


def add(findings, category, path, line, excerpt, confidence="high"):
    findings.append(
        {
            "category": category,
            "path": path.as_posix(),
            "line": int(line),
            "confidence": confidence,
            "excerpt": excerpt.strip()[:500],
        }
    )


def line_number(text: str, offset: int) -> int:
    return text.count("\n", 0, offset) + 1


def scan_file(path: Path, root: Path):
    rel = path.relative_to(root)
    text = path.read_text(encoding="utf-8", errors="replace")
    lines = text.splitlines()
    findings = []

    for idx, line in enumerate(lines, 1):
        for category, pattern in LINE_PATTERNS.items():
            if pattern.search(line):
                confidence = "medium" if category == "suspicious_throwaway_ternary_assignment" else "high"
                add(findings, category, rel, idx, line, confidence)

        for marker in WARNING_STRINGS:
            if marker in line:
                add(findings, "decompiler_warning_marker", rel, idx, line)

    for m in FINALLY_CONTROL.finditer(text):
        body = m.group("body")
        hit = re.search(r"\b(return|break|continue)\b", body)
        if hit:
            off = m.start("body") + hit.start()
            add(
                findings,
                "finally_control_transfer",
                rel,
                line_number(text, off),
                body[max(0, hit.start()-100):hit.end()+180],
                "medium",
            )

    for m in EMPTY_HANDLER_THEN_BLOCK.finditer(text):
        add(
            findings,
            "empty_handler_followed_by_bare_block",
            rel,
            line_number(text, m.start()),
            m.group(0),
        )

    # A string hash-switch followed by a numeric selector switch is normal
    # only when the selector is actually assigned by the hash cases. BedPlates
    # lost those assignments and silently selected case 0 forever.
    for m in HASH_SELECTOR_BLOCK.finditer(text):
        selector = m.group("selector")
        body = m.group("body")
        assignments = len(re.findall(r"\b" + re.escape(selector) + r"\s*=", body))
        if assignments == 0:
            add(
                findings,
                "hash_switch_selector_never_assigned",
                rel,
                line_number(text, m.start()),
                m.group(0)[:900],
            )

    # MoveUtil's forward/backward ternary declared the real variable first,
    # then assigned it only in the false branch while storing the full ternary
    # in a throwaway local.
    for m in PARTIAL_TERNARY_INIT.finditer(text):
        add(
            findings,
            "partial_ternary_initialization",
            rel,
            line_number(text, m.start()),
            m.group(0)[:900],
        )

    # CFR can collapse a for/array iteration into an index declaration plus one
    # element access and no increment/loop-back. AbyssSettingStatics exhibited
    # this exact family.
    for m in COLLAPSED_ARRAY_LOOP.finditer(text):
        add(
            findings,
            "collapsed_array_iteration",
            rel,
            line_number(text, m.start()),
            m.group(0)[:900],
        )

    # Empty condition bodies are common enough to be noisy, so only promote
    # stream-termination shapes that have already proven semantic.
    read_vars = {m.group("var") for m in READ_ASSIGNMENT.finditer(text)}
    for m in EMPTY_IF_BODY.finditer(text):
        cond = m.group("cond")
        cond_compact = re.sub(r"\s+", " ", cond).strip()
        eos_like = re.search(r"\.eos\s*\(\s*\)\s*!=\s*0", cond) is not None
        eof_vars = [
            name for name in read_vars
            if re.search(r"\b" + re.escape(name) + r"\s*<=\s*0", cond)
        ]
        if eos_like:
            add(
                findings,
                "empty_stream_eos_branch",
                rel,
                line_number(text, m.start()),
                cond_compact,
            )
        elif eof_vars:
            tail = text[m.end():m.end()+220]
            same_feed = any(
                re.search(r"\.wrote\s*\(\s*" + re.escape(name) + r"\s*\)", tail)
                for name in eof_vars
            )
            if same_feed:
                add(
                    findings,
                    "empty_stream_eof_branch",
                    rel,
                    line_number(text, m.start()),
                    cond_compact + " -> " + tail[:140].strip(),
                )

    # Duplicate Throwable catches inside a short local window were the exact
    # StallWatchdog failure family. Report, but mark medium because nested
    # handlers can be legitimate.
    throwable_lines = [i for i, line in enumerate(lines, 1) if CATCH_THROWABLE.search(line)]
    for a, b in zip(throwable_lines, throwable_lines[1:]):
        if b - a <= 35:
            excerpt = "\n".join(lines[max(0, a-2):min(len(lines), b+2)])
            add(findings, "nearby_duplicate_throwable_catches", rel, a, excerpt, "medium")

    # ZKM static decoders deserve extra attention when synthetic labels or
    # labelled control transfer remain inside the same file.
    if "zkm$clinit" in text:
        has_label = any(f["category"] == "synthetic_block_label" for f in findings)
        has_transfer = any(f["category"] == "labelled_break_continue" for f in findings)
        if has_label or has_transfer:
            first = text.find("zkm$clinit")
            add(
                findings,
                "zkm_decoder_with_synthetic_control_flow",
                rel,
                line_number(text, first),
                "zkm$clinit contains synthetic block-label/control-transfer remnants",
            )

    return findings


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--source", default="src/main/java")
    ap.add_argument("--out", default="build/recovery-audit")
    args = ap.parse_args()

    root = Path(".").resolve()
    source = (root / args.source).resolve()
    out = (root / args.out).resolve()
    out.mkdir(parents=True, exist_ok=True)

    java_files = sorted(source.rglob("*.java"))
    findings = []
    for path in java_files:
        findings.extend(scan_file(path, root))

    findings.sort(key=lambda x: (x["path"], x["line"], x["category"]))
    counts = collections.Counter(f["category"] for f in findings)
    by_file = collections.Counter(f["path"] for f in findings)
    confidence = collections.Counter(f["confidence"] for f in findings)

    payload = {
        "java_files_scanned": len(java_files),
        "finding_count": len(findings),
        "counts_by_category": dict(sorted(counts.items())),
        "counts_by_confidence": dict(sorted(confidence.items())),
        "top_files": by_file.most_common(50),
        "findings": findings,
    }
    (out / "recovery-audit.json").write_text(json.dumps(payload, indent=2), encoding="utf-8")

    with (out / "recovery-audit.tsv").open("w", encoding="utf-8", newline="") as fh:
        fh.write("confidence\tcategory\tpath\tline\texcerpt\n")
        for f in findings:
            excerpt = f["excerpt"].replace("\t", " ").replace("\r", " ").replace("\n", "\\n")
            fh.write(
                f'{f["confidence"]}\t{f["category"]}\t{f["path"]}\t{f["line"]}\t{excerpt}\n'
            )

    md = []
    md.append("# OpenAbyss recovery audit")
    md.append("")
    md.append(f"- Java files scanned: **{len(java_files)}**")
    md.append(f"- Findings: **{len(findings)}**")
    md.append(f"- High confidence: **{confidence.get('high', 0)}**")
    md.append(f"- Medium confidence: **{confidence.get('medium', 0)}**")
    md.append("")
    md.append("## Categories")
    md.append("")
    if counts:
        md.append("| Category | Count |")
        md.append("|---|---:|")
        for category, count in sorted(counts.items(), key=lambda x: (-x[1], x[0])):
            md.append(f"| {category} | {count} |")
    else:
        md.append("No heuristic findings.")
    md.append("")
    md.append("## Top files")
    md.append("")
    if by_file:
        md.append("| File | Findings |")
        md.append("|---|---:|")
        for path, count in by_file.most_common(30):
            md.append(f"| `{path}` | {count} |")
    else:
        md.append("No files flagged.")

    summary = "\n".join(md) + "\n"
    (out / "summary.md").write_text(summary, encoding="utf-8")
    print(summary)
    print("RECOVERY_AUDIT_REPORT=" + str(out / "recovery-audit.json"))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
