#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import re
import subprocess
import sys


REGISTRY_CLASS = "Abyss.internal.restore.AbyssModuleRegistry"
CACHE_CLASS = "Abyss.internal.restore.AbyssCtorCache"


def javap(jar: pathlib.Path, class_name: str) -> str:
    return subprocess.check_output(
        [
            "javap",
            "-classpath",
            str(jar),
            "-p",
            "-c",
            "-s",
            class_name,
        ],
        text=True,
        stderr=subprocess.DEVNULL,
    )


def sections(text: str) -> dict[str, list[str]]:
    result: dict[str, list[str]] = {}
    current = None
    code: list[str] = []
    in_code = False

    for raw in text.splitlines():
        line = raw.strip()

        if (
            raw.startswith("  ")
            and not raw.startswith("    ")
            and line.endswith(";")
            and "(" in line
            and not line.startswith("descriptor:")
        ):
            if current is not None:
                result[current] = code
            current = (
                line.split("(", 1)[0]
                .split()[-1]
                .split(".")[-1]
            )
            code = []
            in_code = False
            continue

        if current and line == "Code:":
            in_code = True
            continue

        if current and in_code and re.match(r"^\d+:", line):
            code.append(re.sub(r"^\d+:\s*", "", line))

    if current is not None:
        result[current] = code

    return result


def previous_string(lines: list[str], index: int) -> str | None:
    for pos in range(index - 1, max(-1, index - 12), -1):
        match = re.search(r"// String (.+)$", lines[pos])
        if match:
            return match.group(1)
    return None


def registration_names(
    lines: list[str],
    call_marker: str,
) -> list[str]:
    names = []
    for index, line in enumerate(lines):
        if call_marker not in line:
            continue
        name = previous_string(lines, index)
        if name is None:
            raise RuntimeError(
                "could not resolve module name before " + line
            )
        names.append(name)
    return names


def plan_names(lines: list[str]) -> list[str]:
    names = []
    for index, line in enumerate(lines):
        match = re.search(
            r"// class (Abyss/module/impl/[\w/$]+)$",
            line,
        )
        if not match:
            continue

        class_name = match.group(1)
        simple_name = class_name.rsplit("/", 1)[-1]
        for pos in range(index + 1, min(len(lines), index + 7)):
            string_match = re.search(r"// String (.+)$", lines[pos])
            if string_match and string_match.group(1) == simple_name:
                names.append(simple_name)
                break

    return names


def load_reference(path: pathlib.Path) -> list[str]:
    result = []
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        result.append(line)

    if len(result) != 112:
        raise SystemExit(
            f"REGISTRY_ORDER_REFERENCE_COUNT_BAD expected=112 actual={len(result)}"
        )
    if len(set(result)) != 112:
        raise SystemExit("REGISTRY_ORDER_REFERENCE_DUPLICATES")
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-registry-order.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    expected = load_reference(pathlib.Path(args.reference))

    registry = sections(javap(jar, REGISTRY_CLASS))
    cache = sections(javap(jar, CACHE_CLASS))

    required_registry = {
        "publish",
        "publishSolvedCarriers",
        "publishAnchoredCarriers",
    }
    required_cache = {"plans", "splans"}

    if not required_registry.issubset(registry):
        raise SystemExit(
            "REGISTRY_ORDER_MISSING_REGISTRY_METHODS="
            + repr(sorted(required_registry - set(registry)))
        )
    if not required_cache.issubset(cache):
        raise SystemExit(
            "REGISTRY_ORDER_MISSING_CACHE_METHODS="
            + repr(sorted(required_cache - set(cache)))
        )

    direct = registration_names(
        registry["publish"],
        "Method reg:",
    )
    solved = registration_names(
        registry["publishSolvedCarriers"],
        "Method reg:",
    )
    anchored = registration_names(
        registry["publishAnchoredCarriers"],
        "Method anchored:",
    )
    plans = plan_names(cache["plans"])
    splans = plan_names(cache["splans"])

    actual = direct + solved + anchored + plans + splans

    component_counts = (
        len(direct),
        len(solved),
        len(anchored),
        len(plans),
        len(splans),
    )
    expected_components = (71, 3, 6, 31, 1)

    payload = "".join(name + "\n" for name in actual).encode("utf-8")
    digest = hashlib.sha256(payload).hexdigest()

    print(
        "MODULE_REGISTRY_ORDER_COMPONENTS="
        + "/".join(map(str, component_counts))
    )
    print(f"MODULE_REGISTRY_ORDER_COUNT={len(actual)}")
    print(f"MODULE_REGISTRY_ORDER_UNIQUE={len(set(actual))}")
    print(f"MODULE_REGISTRY_ORDER_SHA256={digest}")

    failures = []

    if component_counts != expected_components:
        failures.append(
            f"components expected={expected_components} actual={component_counts}"
        )
    if len(actual) != 112:
        failures.append(f"count expected=112 actual={len(actual)}")
    if len(set(actual)) != 112:
        failures.append(
            f"unique expected=112 actual={len(set(actual))}"
        )

    if actual != expected:
        first = None
        for index, (want, got) in enumerate(
            zip(expected, actual),
            start=1,
        ):
            if want != got:
                first = (index, want, got)
                break
        if first is None and len(expected) != len(actual):
            first = (
                min(len(expected), len(actual)) + 1,
                expected[min(len(expected), len(actual))]
                if len(expected) > len(actual)
                else "<end>",
                actual[min(len(expected), len(actual))]
                if len(actual) > len(expected)
                else "<end>",
            )
        failures.append("order " + repr(first))

    print(f"MODULE_REGISTRY_ORDER_FAILURES={len(failures)}")
    for failure in failures:
        print("MODULE_REGISTRY_ORDER_BAD=" + failure)

    if failures:
        print("MODULE_REGISTRY_ORDER_GATE=FAIL")
        return 1

    print("MODULE_REGISTRY_ORDER_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
