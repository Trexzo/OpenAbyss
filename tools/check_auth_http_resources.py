#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

TARGETS = [
    pathlib.Path("src/main/java/Abyss/internal/auth/AuthService.java"),
    pathlib.Path("src/main/java/Abyss/internal/auth/AccountLookupService.java"),
]

decl = re.compile(r"CloseableHttpResponse\s+(\w+)\s*=\s*[^;]*\.execute\s*\(")

bad: list[str] = []
for path in TARGETS:
    text = path.read_text(encoding="utf-8")
    matches = list(decl.finditer(text))
    safe = 0
    for match in matches:
        line_start = text.rfind("\n", 0, match.start()) + 1
        prefix = text[line_start:match.start()]
        line = text[line_start:text.find("\n", match.start()) if "\n" in text[match.start():] else len(text)]
        if "try (" in prefix and match.group(1) == "response":
            safe += 1
        else:
            bad.append(f"{path}:{line.strip()}")
    print(f"AUTH_HTTP_RESPONSE_DECLS file={path} total={len(matches)} safe_try_with={safe}")
    if len(matches) != 1 or safe != 1:
        bad.append(f"{path}:expected exactly one try-with-resources response helper")

if bad:
    for item in bad:
        print(f"AUTH_HTTP_RESPONSE_BAD={item}")
    print("AUTH_HTTP_RESOURCE_GATE=FAIL")
    sys.exit(1)

print("AUTH_HTTP_RESOURCE_GATE=PASS")
