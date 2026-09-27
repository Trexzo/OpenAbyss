#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

root = pathlib.Path("src/main/java/Abyss/module/impl")

method = re.compile(
    r"(?P<mods>\b(?:public|protected)\b[^\n\{;]*?)"
    r"\b(?P<name>on[A-Z]\w*|i|A|P|L)\s*"
    r"\((?P<args>[^)]*)\)\s*"
    r"(?:throws\s+[^\{]+)?\{\s*\}",
    re.MULTILINE,
)

rows: list[tuple[str, int, str, str]] = []
for path in sorted(root.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    for m in method.finditer(text):
        name = m.group("name")
        line = text.count("\n", 0, m.start()) + 1
        rows.append((path.as_posix(), line, name, m.group("args").strip()))

print(f"MODULE_EMPTY_HANDLER_TOTAL={len(rows)}")
for path, line, name, args in rows:
    kind = "event" if name.startswith("on") else "lifecycle"
    print(f"MODULE_EMPTY_HANDLER kind={kind} file={path} line={line} method={name} args={args}")

if rows:
    print("MODULE_EMPTY_HANDLER_GATE=FAIL")
    print("REFERENCE_JAR_SHA256=13814827D8341CA6F4D7510F8B07C66F998EB7F43FA70BB18B0755AB7A4474F1")
    print("REFERENCE_EMPTY_PUBLIC_PROTECTED_EVENT_LIFECYCLE_HANDLERS=0")
    sys.exit(1)

print("MODULE_EMPTY_HANDLER_GATE=PASS")
print("REFERENCE_JAR_SHA256=13814827D8341CA6F4D7510F8B07C66F998EB7F43FA70BB18B0755AB7A4474F1")
print("REFERENCE_EMPTY_PUBLIC_PROTECTED_EVENT_LIFECYCLE_HANDLERS=0")
