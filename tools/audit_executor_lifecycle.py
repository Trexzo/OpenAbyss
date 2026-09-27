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


thread_pattern = re.compile(r"new\s+Thread\s*\(")
thread_count = 0
thread_non_daemon: list[str] = []
for path in sorted(root.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    for match in thread_pattern.finditer(text):
        thread_count += 1
        line = text.count("\n", 0, match.start()) + 1
        start = max(0, match.start() - 500)
        end = min(len(text), match.start() + 1200)
        window = text[start:end]
        daemon = "setDaemon(true)" in window
        started = ".start()" in window
        print(
            f"THREAD_CENSUS file={path.as_posix()} line={line} "
            f"daemon_near={str(daemon).lower()} start_near={str(started).lower()}"
        )
        if started and not daemon:
            thread_non_daemon.append(f"{path.as_posix()}:{line}")

print(f"THREAD_CENSUS_TOTAL={thread_count}")
print(f"THREAD_STARTED_NON_DAEMON={len(thread_non_daemon)}")
for item in thread_non_daemon:
    print(f"THREAD_STARTED_NON_DAEMON_ITEM={item}")
