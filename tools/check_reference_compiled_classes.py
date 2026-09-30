#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import sys
import zipfile


REFERENCE_JAR_SHA256 = "13814827D8341CA6F4D7510F8B07C66F998EB7F43FA70BB18B0755AB7A4474F1"
REFERENCE_CLASS_COUNT = 1413
REFERENCE_CLASS_LIST_SHA256 = "B4F0CAAE3EC1EF01BFB6F3FE619C4F7EDF9E9C837E72D9A08B3D29CF85F85EE6"

REVIEWED_RECOVERY_EXTRAS = {
    "Abyss/command/impl/AbyssCommandConfig$1Probe.class",
    "Abyss/command/impl/AbyssCommandReset$1ProbeModule.class",
    "Abyss/event/EventBus$1.class",
    "Abyss/event/EventBus$1TestEvent.class",
    "Abyss/event/EventBus$1ThrowEvent.class",
    "Abyss/event/EventBus$2$1.class",
    "Abyss/event/EventBus$2.class",
    "Abyss/event/EventBus$3$1.class",
    "Abyss/event/EventBus$3.class",
    "Abyss/event/EventBus$4$1.class",
    "Abyss/event/EventBus$4.class",
    "Abyss/event/EventBus$5$1.class",
    "Abyss/event/EventBus$5.class",
    "Abyss/event/EventBus$6$1.class",
    "Abyss/event/EventBus$6.class",
    "Abyss/event/EventBus$7$1.class",
    "Abyss/event/EventBus$7.class",
    "Abyss/internal/auth/AccountLookupService$1.class",
    "Abyss/internal/auth/AccountLookupService$HttpResult.class",
    "Abyss/internal/auth/AuthService$1.class",
    "Abyss/internal/auth/AuthService$HttpResult.class",
    "Abyss/module/Module$1ProbeModule.class",
}


def inventory_hash(entries: set[str]) -> str:
    payload = ("\n".join(sorted(entries)) + "\n").encode("utf-8")
    return hashlib.sha256(payload).hexdigest().upper()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    with zipfile.ZipFile(jar) as zf:
        classes = {
            name
            for name in zf.namelist()
            if name.endswith(".class")
        }

    extras_present = classes & REVIEWED_RECOVERY_EXTRAS
    unreviewed_extras = classes - REVIEWED_RECOVERY_EXTRAS
    missing_reviewed = REVIEWED_RECOVERY_EXTRAS - classes

    baseline_count = len(unreviewed_extras)
    baseline_hash = inventory_hash(unreviewed_extras)

    print(f"REFERENCE_COMPILED_CLASS_REFERENCE_JAR_SHA256={REFERENCE_JAR_SHA256}")
    print(f"REFERENCE_COMPILED_CLASS_ACTUAL_TOTAL={len(classes)}")
    print(f"REFERENCE_COMPILED_CLASS_REVIEWED_EXTRAS_PRESENT={len(extras_present)}")
    print(f"REFERENCE_COMPILED_CLASS_REVIEWED_EXTRAS_EXPECTED={len(REVIEWED_RECOVERY_EXTRAS)}")
    print(f"REFERENCE_COMPILED_CLASS_BASELINE_COUNT={baseline_count}")
    print(f"REFERENCE_COMPILED_CLASS_BASELINE_HASH={baseline_hash}")

    failures: list[str] = []

    if missing_reviewed:
        for name in sorted(missing_reviewed):
            failures.append("reviewed-extra-missing:" + name)

    if baseline_count != REFERENCE_CLASS_COUNT:
        failures.append(
            "baseline-count:"
            + str(baseline_count)
            + "!="
            + str(REFERENCE_CLASS_COUNT)
        )

    if baseline_hash != REFERENCE_CLASS_LIST_SHA256:
        failures.append(
            "baseline-list-hash:"
            + baseline_hash
            + "!="
            + REFERENCE_CLASS_LIST_SHA256
        )

    # The count+hash pair above proves the post-extra inventory is exactly the
    # runnable-era 1,413-entry class-name set. Requiring all reviewed extras
    # simultaneously makes any new/removed compiled class an explicit review.
    expected_total = REFERENCE_CLASS_COUNT + len(REVIEWED_RECOVERY_EXTRAS)
    if len(classes) != expected_total:
        failures.append(
            "total-count:" + str(len(classes)) + "!=" + str(expected_total)
        )

    print(f"REFERENCE_COMPILED_CLASS_FAILURES={len(failures)}")
    for failure in failures:
        print("REFERENCE_COMPILED_CLASS_BAD=" + failure)

    if failures:
        print("REFERENCE_COMPILED_CLASS_GATE=FAIL")
        return 1

    print("REFERENCE_COMPILED_CLASS_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
