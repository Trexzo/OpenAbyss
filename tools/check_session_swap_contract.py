#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

root = pathlib.Path("src/main/java")
legacy_calls: list[str] = []
verified_calls: list[str] = []

for path in root.rglob("*.java"):
    text = path.read_text(encoding="utf-8")
    rel = path.as_posix()
    for match in re.finditer(r"SessionAccessor\.k\s*\(", text):
        if rel.endswith("/Abyss/internal/auth/SessionAccessor.java"):
            continue
        line = text.count("\n", 0, match.start()) + 1
        legacy_calls.append(f"{rel}:{line}")
    for match in re.finditer(r"SessionAccessor\.set\s*\(", text):
        line = text.count("\n", 0, match.start()) + 1
        verified_calls.append(f"{rel}:{line}")

print(f"SESSION_SWAP_LEGACY_EXTERNAL_CALLS={len(legacy_calls)}")
for item in legacy_calls:
    print(f"SESSION_SWAP_LEGACY_BAD={item}")
print(f"SESSION_SWAP_VERIFIED_CALLS={len(verified_calls)}")
for item in verified_calls:
    print(f"SESSION_SWAP_VERIFIED={item}")

if legacy_calls:
    print("SESSION_SWAP_CONTRACT=FAIL legacy external call")
    sys.exit(1)
if len(verified_calls) < 4:
    print("SESSION_SWAP_CONTRACT=FAIL too few verified callers")
    sys.exit(1)

print("SESSION_SWAP_CONTRACT=PASS")
