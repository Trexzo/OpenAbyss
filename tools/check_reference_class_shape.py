#!/usr/bin/env python3
from __future__ import annotations

import argparse
import collections
import pathlib
import sys
import zipfile


def load_reference(path: pathlib.Path) -> dict[str, int]:
    expected: dict[str, int] = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        count, package = line.split(" ", 1)
        expected[package] = int(count)
    if len(expected) != 65:
        raise SystemExit(f"REFERENCE_PACKAGE_COUNT_MISMATCH expected=65 actual={len(expected)}")
    if sum(expected.values()) != 1227:
        raise SystemExit(f"REFERENCE_TOP_LEVEL_COUNT_MISMATCH expected=1227 actual={sum(expected.values())}")
    return expected


def source_counts(root: pathlib.Path) -> collections.Counter[str]:
    result: collections.Counter[str] = collections.Counter()
    for path in root.rglob("*.java"):
        rel = path.relative_to(root).as_posix()
        package = rel.rsplit("/", 1)[0] if "/" in rel else ""
        result[package] += 1
    return result


def jar_counts(path: pathlib.Path) -> collections.Counter[str]:
    result: collections.Counter[str] = collections.Counter()
    with zipfile.ZipFile(path) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            simple = name.rsplit("/", 1)[-1][:-6]
            if "$" in simple:
                continue
            package = name.rsplit("/", 1)[0] if "/" in name else ""
            result[package] += 1
    return result


def verify(label: str, expected: dict[str, int], actual: collections.Counter[str]) -> None:
    packages = set(expected) | set(actual)
    mismatches = [
        (pkg, expected.get(pkg, 0), actual.get(pkg, 0))
        for pkg in sorted(packages)
        if expected.get(pkg, 0) != actual.get(pkg, 0)
    ]
    print(f"{label}_PACKAGES={len(actual)}")
    print(f"{label}_TOP_LEVEL_CLASSES={sum(actual.values())}")
    print(f"{label}_PACKAGE_MISMATCHES={len(mismatches)}")
    for pkg, want, got in mismatches:
        print(f"{label}_PACKAGE_BAD={pkg} expected={want} actual={got}")
    if mismatches:
        raise SystemExit(f"{label}_CLASS_PACKAGE_GATE=FAIL")
    if len(actual) != 65 or sum(actual.values()) != 1227:
        raise SystemExit(f"{label}_CLASS_PACKAGE_GATE=FAIL totals")
    print(f"{label}_CLASS_PACKAGE_GATE=PASS")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--reference",
        default="tools/reference-class-package-counts.txt",
    )
    parser.add_argument(
        "--source-root",
        default="src/main/java",
    )
    parser.add_argument("--jar", required=True)
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    verify("SOURCE", expected, source_counts(pathlib.Path(args.source_root)))
    verify("JAR", expected, jar_counts(pathlib.Path(args.jar)))
    print("RUNNABLE_ERA_CLASS_SHAPE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
