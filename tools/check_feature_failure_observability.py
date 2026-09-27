#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

root = pathlib.Path("src/main/java")
client = (root / "Abyss/AbyssClient.java").read_text(encoding="utf-8")

checks = {
    "feature_signature_set": "FEATURE_FAILURE_SIGNATURES" in client,
    "feature_recorder": "public static void recordFeatureFailure(" in client,
    "feature_dedupe": "if (!FEATURE_FAILURE_SIGNATURES.add(signature))" in client,
    "feature_file": 'new File("abyss-feature-failure.txt")' in client,
    "bed_scan_observable": 'recordFeatureFailure("BedNuker", "schedule-bed-scan"' in client,
}

targets = {
    "Abyss/module/impl/movement/NoSlow.java": [
        '"NoSlow", "blink-ownership-check"',
        '"NoSlow", "restore-use-key"',
        '"NoSlow", "release-use-key"',
        '"NoSlow", "killaura-ownership-check"',
    ],
    "Abyss/module/impl/world/FastPlace.java": [
        '"FastPlace", "pre-update-delay"',
        '"FastPlace", "post-right-click-delay"',
    ],
    "Abyss/module/impl/player/ChestStealer.java": [
        '"ChestStealer", "window-click"',
    ],
    "Abyss/module/impl/visual/HUD.java": [
        '"HUD", "render-2d"',
    ],
    "Abyss/module/impl/visual_utility/Indicators.java": [
        '"Indicators", "render-2d"',
    ],
    "Abyss/module/impl/visual_utility/MegaWallsDetector.java": [
        '"MegaWallsDetector", "scoreboard-health-lookup"',
    ],
    "Abyss/module/impl/visual_utility/TargetHUD.java": [
        '"TargetHUD", "persist-drag-position"',
    ],
}

empty = re.compile(
    r"catch\s*\([^)]*\)\s*\{\s*(?://\s*empty catch block)?\s*\}",
    re.MULTILINE,
)

for rel, markers in targets.items():
    text = (root / rel).read_text(encoding="utf-8")
    key = rel.rsplit("/", 1)[-1].replace(".java", "")
    checks[f"{key}_markers"] = all(marker in text for marker in markers)
    checks[f"{key}_no_empty_catch"] = empty.search(text) is None

for name, ok in checks.items():
    print(f"FEATURE_FAILURE_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"FEATURE_FAILURE_BAD={name}")
    print("FEATURE_FAILURE_GATE=FAIL")
    sys.exit(1)

print("FEATURE_FAILURE_GATE=PASS")
