#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

path = pathlib.Path("src/main/java/Abyss/AbyssClient.java")
text = path.read_text(encoding="utf-8")

keybind_section = text[
    text.find("public void onSetKeyBindState"):
    text.find("private static ScheduledExecutorService newBedScanScheduler")
]

checks = {
    "failure_signature_set": "MODULE_FAILURE_SIGNATURES" in text,
    "dedupe_add": "if (!MODULE_FAILURE_SIGNATURES.add(signature))" in text,
    "pre_update_isolated": 'moduleFailure("pre-update", var11, failure);' in text,
    "keybind_isolated": 'moduleFailure("keybind-toggle", var12, failure);' in text,
    "pre_mouse_isolated": 'moduleFailure("pre-mouse-input", var9, failure);' in text,
    "keybind_silent_catch_removed": "// empty catch block" not in keybind_section,
    "lifecycle_enable_failfast": 'moduleFailure("enable", var29, failure);' in text and "throw failure;" in text,
    "lifecycle_disable_failfast": 'moduleFailure("disable", var29, failure);' in text,
    "lifecycle_reset_failfast": 'moduleFailure("disabled-reset", var29, failure);' in text,
}

for name, ok in checks.items():
    print(f"MODULE_DISPATCH_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"MODULE_DISPATCH_BAD={name}")
    print("MODULE_DISPATCH_GATE=FAIL")
    sys.exit(1)

print("MODULE_DISPATCH_GATE=PASS")
