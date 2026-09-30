#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

path = pathlib.Path("src/main/java/Abyss/AbyssClient.java")
text = path.read_text(encoding="utf-8")

checks = {
    "named_bed_scan_scheduler": '"OpenAbyss-BedScan"' in text,
    "bed_scan_worker_daemon": "worker.setDaemon(true);" in text,
    "constructor_uses_factory": "this.U = AbyssClient.newBedScanScheduler();" in text,
    "selftest_present": "public static String schedulerSelfTest()" in text,
    "selftest_shutdown": "scheduler.shutdownNow();" in text,
    "raw_default_pool_absent": "this.U = Executors.newScheduledThreadPool(1);" not in text,
}

for name, ok in checks.items():
    print(f"BED_SCAN_SCHEDULER_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"BED_SCAN_SCHEDULER_BAD={name}")
    print("BED_SCAN_SCHEDULER_GATE=FAIL")
    sys.exit(1)

print("BED_SCAN_SCHEDULER_GATE=PASS")
