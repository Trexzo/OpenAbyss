#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

helper = pathlib.Path("src/main/java/Abyss/util/SmoothMouseHelper.java").read_text(encoding="utf-8")
raw = pathlib.Path("src/main/java/Abyss/module/impl/misc/RawInput.java").read_text(encoding="utf-8")

checks = {
    "daemon_worker": '"OpenAbyss-RawInput"' in helper and "worker.setDaemon(true);" in helper,
    "shutdown_now": "this.A.shutdownNow();" in helper,
    "controller_before_install": (
        helper.find("this.p = new ControllerEnvironmentImpl();")
        < helper.find("mouseHelper = this;")
    ),
    "schedules_before_install": (
        helper.find("this.A.scheduleAtFixedRate(this::V")
        < helper.find("mouseHelper = this;")
        and helper.find("this.A.scheduleAtFixedRate(this::j")
        < helper.find("mouseHelper = this;")
    ),
    "failure_propagates": 'throw new IllegalStateException("RawInput initialization failed", failure);' in helper,
    "silent_init_catch_absent": "catch (NullPointerException nullPointerException)" not in helper,
    "raw_restores_previous_helper": "RawInput.f.mouseHelper = var3 != null ? var3 : new MouseHelper();" in raw,
}

for name, ok in checks.items():
    print(f"RAW_INPUT_LIFECYCLE {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"RAW_INPUT_LIFECYCLE_BAD={name}")
    print("RAW_INPUT_LIFECYCLE_GATE=FAIL")
    sys.exit(1)

print("RAW_INPUT_LIFECYCLE_GATE=PASS")
