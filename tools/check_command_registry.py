#!/usr/bin/env python3
from __future__ import annotations

import argparse
import ast
import pathlib
import re
import sys


def load_reference(path: pathlib.Path):
    rows = []
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        cls, aliases, j = line.split("|")
        rows.append((cls, aliases.split(","), j == "true"))
    if len(rows) != 19:
        raise SystemExit(f"REFERENCE_COMMAND_COUNT_MISMATCH expected=19 actual={len(rows)}")
    return rows


def java_strings(text: str):
    # Java string literals in these command metadata paths are simple enough
    # to decode with Python after replacing Java unicode escapes naturally
    # through literal_eval semantics.
    out = []
    for token in re.findall(r'"(?:\\.|[^"\\])*"', text):
        try:
            out.append(ast.literal_eval(token))
        except Exception:
            out.append(token[1:-1])
    return out


def method_body(source: str, signature_pattern: str) -> str:
    m = re.search(signature_pattern, source)
    if not m:
        raise ValueError("method signature not found")
    start = source.find("{", m.end())
    if start < 0:
        raise ValueError("method body start not found")
    depth = 0
    for i in range(start, len(source)):
        ch = source[i]
        if ch == "{":
            depth += 1
        elif ch == "}":
            depth -= 1
            if depth == 0:
                return source[start + 1:i]
    raise ValueError("method body end not found")


def simple_aliases(path: pathlib.Path):
    source = path.read_text(encoding="utf-8")
    body = method_body(source, r"public\s+String\[\]\s+e\s*\(\s*long\b[^)]*\)")
    m = re.search(r"return\s+new\s+String\s*\[\]\s*\{(?P<body>.*?)\}\s*;", body, re.S)
    if not m:
        raise ValueError(f"literal alias return missing in {path}")
    aliases = java_strings(m.group("body"))
    jbody = method_body(source, r"public\s+boolean\s+J\s*\(\s*\)")
    jm = re.search(r"return\s+(true|false)\s*;", jbody)
    if not jm:
        raise ValueError(f"literal J() return missing in {path}")
    return aliases, jm.group(1) == "true"


def registry_entries(source: str):
    body = method_body(source, r"public\s+static\s+void\s+install\s*\(")
    entries = []
    for stmt in re.findall(r"StockCommandRegistry\.L\.add\s*\(\s*new\s+([^;]+?)\)\s*;", body, re.S):
        cm = re.match(r"(?P<class>\w+)\s*\((?P<args>.*)\)\s*$", stmt.strip(), re.S)
        if not cm:
            raise ValueError(f"cannot parse registry entry: {stmt}")
        entries.append((cm.group("class"), cm.group("args")))
    return entries


def special_aliases(cls: str, args: str):
    strings = java_strings(args)
    if cls == "AbyssCommandNames":
        # Constructor is (mode, description, usage[], aliases...).
        # The aliases are the trailing strings after the 3 usage strings.
        if len(strings) < 5:
            raise ValueError(f"too few strings for {cls}: {strings}")
        # Usage strings begin with two spaces; description begins with §.
        return [s for s in strings if not s.startswith("  .") and not s.startswith("§")]
    if cls == "AbyssCommandVisible":
        # Constructor is (visible, description, aliases...).
        return [s for s in strings if not s.startswith("§")]
    raise ValueError(f"not a special command: {cls}")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--reference", default="tools/reference-command-registry.txt")
    parser.add_argument("--source-root", default="src/main/java")
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    root = pathlib.Path(args.source_root)
    commands_java = root / "Abyss/command/AbyssCommands.java"
    entries = registry_entries(commands_java.read_text(encoding="utf-8"))

    print(f"COMMAND_REGISTRY_COUNT={len(entries)}")
    if len(entries) != len(expected):
        raise SystemExit(f"COMMAND_REGISTRY_GATE=FAIL count={len(entries)} expected={len(expected)}")

    mismatches = []
    for idx, ((want_cls, want_aliases, want_j), (got_cls, got_args)) in enumerate(zip(expected, entries)):
        got_aliases = None
        got_j = None
        if got_cls in {"AbyssCommandNames", "AbyssCommandVisible"}:
            got_aliases = special_aliases(got_cls, got_args)
            # Runnable-era special classes also returned false from J().
            source = (root / f"Abyss/command/impl/{got_cls}.java").read_text(encoding="utf-8")
            jbody = method_body(source, r"public\s+boolean\s+J\s*\(\s*\)")
            jm = re.search(r"return\s+(true|false)\s*;", jbody)
            got_j = bool(jm and jm.group(1) == "true")
        else:
            got_aliases, got_j = simple_aliases(root / f"Abyss/command/impl/{got_cls}.java")

        if got_cls != want_cls or got_aliases != want_aliases or got_j != want_j:
            mismatches.append(
                f"{idx:02d}: expected={want_cls}|{want_aliases}|{want_j} "
                f"actual={got_cls}|{got_aliases}|{got_j}"
            )

    print(f"COMMAND_REGISTRY_MISMATCHES={len(mismatches)}")
    for item in mismatches:
        print(f"COMMAND_REGISTRY_BAD={item}")
    if mismatches:
        raise SystemExit("COMMAND_REGISTRY_GATE=FAIL")

    print("COMMAND_REGISTRY_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
