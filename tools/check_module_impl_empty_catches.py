#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

root = pathlib.Path("src/main/java/Abyss/module/impl")
pattern = re.compile(
    r"catch\s*\([^)]*\)\s*\{\s*(?://\s*empty catch block)?\s*\}",
    re.MULTILINE,
)

rows: list[tuple[str, int]] = []
for path in sorted(root.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    for match in pattern.finditer(text):
        line = text.count("\n", 0, match.start()) + 1
        rows.append((path.as_posix(), line))

print(f"MODULE_IMPL_EMPTY_CATCH_TOTAL={len(rows)}")
for path, line in rows:
    print(f"MODULE_IMPL_EMPTY_CATCH file={path} line={line}")

if rows:
    print("MODULE_IMPL_EMPTY_CATCH_GATE=FAIL")
    sys.exit(1)

print("MODULE_IMPL_EMPTY_CATCH_GATE=PASS")
