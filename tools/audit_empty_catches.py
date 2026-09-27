#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re

root = pathlib.Path("src/main/java")
pattern = re.compile(
    r"catch\s*\([^)]*\)\s*\{\s*(?://\s*empty catch block)?\s*\}",
    re.MULTILINE,
)

rows: list[tuple[str, int, str]] = []
for path in sorted(root.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    for match in pattern.finditer(text):
        line = text.count("\n", 0, match.start()) + 1
        before = text[max(0, match.start() - 320):match.start()]
        after = text[match.end():min(len(text), match.end() + 320)]
        context = " ".join((before + " <EMPTY_CATCH> " + after).split())
        rows.append((path.as_posix(), line, context[:1200]))

print(f"EMPTY_CATCH_TOTAL={len(rows)}")
by_file: dict[str, int] = {}
for path, line, context in rows:
    by_file[path] = by_file.get(path, 0) + 1
    print(f"EMPTY_CATCH file={path} line={line}")
    print(f"EMPTY_CATCH_CONTEXT={context}")

for path, count in sorted(by_file.items(), key=lambda item: (-item[1], item[0])):
    print(f"EMPTY_CATCH_FILE_COUNT file={path} count={count}")
