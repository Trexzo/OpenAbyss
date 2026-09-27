#!/usr/bin/env python3
from __future__ import annotations

import argparse
import collections
import pathlib
import re
import subprocess
import sys
import zipfile

from check_event_wiring_semantics import binder_rows, class_names

DECL_RE = re.compile(
    r"public class ([\w.$]+).*?\{",
    re.DOTALL,
)
BINDER_RE = re.compile(
    r"// Method (Abyss/event/binder/[\w$]+)\."
)
X_RE = re.compile(
    r"\bvoid x\(long, Abyss\.event\.EventBus\);"
)

EXPECTED_SUBSCRIBERS = 85
EXPECTED_UNIQUE_BINDERS = 85
EXPECTED_REFERENCED_REGISTRATIONS = 222


def javap(jar: pathlib.Path, names: list[str]) -> str:
    chunks: list[str] = []
    for offset in range(0, len(names), 40):
        command = [
            "javap",
            "-classpath",
            str(jar),
            "-p",
            "-c",
            *names[offset:offset + 40],
        ]
        chunks.append(
            subprocess.check_output(
                command,
                text=True,
                stderr=subprocess.DEVNULL,
            )
        )
    return "\n".join(chunks)


def subscriber_rows(jar: pathlib.Path):
    names = class_names(jar, "Abyss/module/impl/")
    text = javap(jar, names)
    rows = []

    for block in re.split(r'(?=Compiled from ")', text):
        match = DECL_RE.search(block)
        if not match:
            continue

        name = match.group(1)
        header = block[:block.find("{")]
        if "implements Abyss.event.EventSubscriber" not in header:
            continue

        binders = tuple(sorted(set(BINDER_RE.findall(block))))
        rows.append(
            (
                name,
                bool(X_RE.search(block)),
                binders,
            )
        )

    return sorted(rows)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    subscribers = subscriber_rows(jar)
    registrations = binder_rows(jar)
    registration_counts = collections.Counter(
        binder for binder, _event, _priority, _invoker in registrations
    )

    failures: list[str] = []
    referenced_binders: set[str] = set()

    for module, has_x, binders in subscribers:
        if not has_x:
            failures.append(f"subscriber-missing-x:{module}")
        if len(binders) != 1:
            failures.append(
                f"subscriber-binder-count:{module}:{len(binders)}:{binders}"
            )
            continue

        binder = binders[0]
        referenced_binders.add(binder)
        count = registration_counts.get(binder, 0)
        if count < 1:
            failures.append(
                f"subscriber-empty-binder:{module}:{binder}"
            )

    referenced_registration_count = sum(
        registration_counts[binder]
        for binder in referenced_binders
    )

    if len(subscribers) != EXPECTED_SUBSCRIBERS:
        failures.append(
            f"subscriber-count:expected={EXPECTED_SUBSCRIBERS}:actual={len(subscribers)}"
        )
    if len(referenced_binders) != EXPECTED_UNIQUE_BINDERS:
        failures.append(
            f"binder-count:expected={EXPECTED_UNIQUE_BINDERS}:actual={len(referenced_binders)}"
        )
    if referenced_registration_count != EXPECTED_REFERENCED_REGISTRATIONS:
        failures.append(
            "referenced-registration-count:"
            f"expected={EXPECTED_REFERENCED_REGISTRATIONS}:"
            f"actual={referenced_registration_count}"
        )

    print(f"MODULE_SUBSCRIBER_CLASSES={len(subscribers)}")
    print(f"MODULE_SUBSCRIBER_UNIQUE_BINDERS={len(referenced_binders)}")
    print(
        "MODULE_SUBSCRIBER_REFERENCED_REGISTRATIONS="
        + str(referenced_registration_count)
    )
    print(
        "MODULE_SUBSCRIBER_EMPTY_BINDERS="
        + str(
            sum(
                registration_counts.get(binder, 0) == 0
                for binder in referenced_binders
            )
        )
    )
    print(f"MODULE_SUBSCRIBER_FAILURES={len(failures)}")
    for failure in failures:
        print("MODULE_SUBSCRIBER_BAD=" + failure)

    if failures:
        print("MODULE_SUBSCRIBER_COVERAGE_GATE=FAIL")
        return 1

    print("MODULE_SUBSCRIBER_COVERAGE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
