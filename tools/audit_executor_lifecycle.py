#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re

root = pathlib.Path("src/main/java")
factory = re.compile(r"Executors\.new(?:SingleThreadExecutor|FixedThreadPool|CachedThreadPool|ScheduledThreadPool|SingleThreadScheduledExecutor|WorkStealingPool)\s*\(")

count = 0
unsafe: list[str] = []
for path in sorted(root.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    for match in factory.finditer(text):
        count += 1
        line = text.count("\n", 0, match.start()) + 1
        start = max(0, text.rfind("\n", 0, max(0, match.start() - 700)))
        end = text.find("\n", min(len(text), match.start() + 1200))
        if end < 0:
            end = len(text)
        window = text[start:end]
        daemon = "setDaemon(true)" in window
        shutdown = "shutdown()" in text or "shutdownNow()" in text
        print(
            f"EXECUTOR_CENSUS file={path.as_posix()} line={line} "
            f"daemon_near={str(daemon).lower()} shutdown_in_file={str(shutdown).lower()}"
        )
        compact = " ".join(window.split())
        print(f"EXECUTOR_CONTEXT={compact[:1800]}")
        if not daemon:
            unsafe.append(f"{path.as_posix()}:{line}")

print(f"EXECUTOR_CENSUS_TOTAL={count}")
print(f"EXECUTOR_NON_DAEMON={len(unsafe)}")
for item in unsafe:
    print(f"EXECUTOR_NON_DAEMON_ITEM={item}")
if unsafe:
    raise SystemExit("EXECUTOR_DAEMON_GATE=FAIL")
print("EXECUTOR_DAEMON_GATE=PASS")
