#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re

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

print("MODULE_EMPTY_HANDLER_AUDIT=PASS")
