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
