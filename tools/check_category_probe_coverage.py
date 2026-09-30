#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

ROOT = pathlib.Path("src/main/java")
CLIENT = ROOT / "Abyss/AbyssClient.java"

EXPECTED = {
    "HitBox": ("Combat", ROOT / "Abyss/module/impl/combat/HitBox.java"),
    "Notifications": ("Configuration", ROOT / "Abyss/module/impl/configuration/Notifications.java"),
    "Macro1": ("Macro", ROOT / "Abyss/module/impl/macro/Macro1.java"),
    "NameHider": ("Misc", ROOT / "Abyss/module/impl/misc/NameHider.java"),
    "NoJumpDelay": ("Movement", ROOT / "Abyss/module/impl/movement/NoJumpDelay.java"),
    "NoHitDelay": ("Player", ROOT / "Abyss/module/impl/player/NoHitDelay.java"),
    "NoHurtCam": ("Visual", ROOT / "Abyss/module/impl/visual/NoHurtCam.java"),
    "Tracers": ("Visual_utility", ROOT / "Abyss/module/impl/visual_utility/Tracers.java"),
    "AutoTool": ("World", ROOT / "Abyss/module/impl/world/AutoTool.java"),
}

def main() -> int:
    text = CLIENT.read_text(encoding="utf-8")
    m = re.search(
        r"CATEGORY_LIFECYCLE_PROBE_MODULES\s*=\s*new\s+String\[\]\s*\{(.*?)\};",
        text,
        re.S,
    )
    if not m:
        print("CATEGORY_PROBE_GATE=FAIL missing-array")
        return 1

    names = re.findall(r'"([^"]+)"', m.group(1))
    print("CATEGORY_PROBE_MODULE_COUNT=" + str(len(names)))
    print("CATEGORY_PROBE_MODULES=" + ",".join(names))

    failures: list[str] = []
    if names != list(EXPECTED):
        failures.append("probe-order-or-membership expected=" + ",".join(EXPECTED) + " actual=" + ",".join(names))

    categories = []
    for name, (expected_category, path) in EXPECTED.items():
        if not path.is_file():
            failures.append(f"{name}:source-missing:{path}")
            continue
        source = path.read_text(encoding="utf-8")
        d = re.search(
            r'this\.declare\("' + re.escape(name) + r'",\s*Category\.([A-Za-z_]+)',
            source,
        )
        if not d:
            failures.append(f"{name}:declaration-missing")
            continue
        actual_category = d.group(1)
        categories.append(actual_category)
        print(f"CATEGORY_PROBE_MAP={name}:{actual_category}")
        if actual_category != expected_category:
            failures.append(f"{name}:expected={expected_category}:actual={actual_category}")

    expected_categories = {v[0] for v in EXPECTED.values()}
    if set(categories) != expected_categories:
        failures.append(
            "category-set expected="
            + ",".join(sorted(expected_categories))
            + " actual="
            + ",".join(sorted(set(categories)))
        )
    if len(categories) != len(set(categories)):
        failures.append("duplicate-category")

    print("CATEGORY_PROBE_FAILURES=" + str(len(failures)))
    for failure in failures:
        print("CATEGORY_PROBE_FAILURE=" + failure)

    if failures:
        print("CATEGORY_PROBE_GATE=FAIL")
        return 1

    print("CATEGORY_PROBE_GATE=PASS categories=9")
    return 0

if __name__ == "__main__":
    sys.exit(main())
