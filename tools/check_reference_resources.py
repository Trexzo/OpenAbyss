#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import sys
import zipfile


def load_reference(path: pathlib.Path) -> list[str]:
    entries: list[str] = []
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        entries.append(line.replace("\\", "/"))
    if len(entries) != 88:
        raise SystemExit(f"REFERENCE_RESOURCE_COUNT_MISMATCH expected=88 actual={len(entries)}")
    if len(set(entries)) != len(entries):
        raise SystemExit("REFERENCE_RESOURCE_DUPLICATES")
    return sorted(entries)


def source_entries(root: pathlib.Path) -> set[str]:
    if not root.is_dir():
        raise SystemExit(f"SOURCE_RESOURCE_ROOT_MISSING {root}")
    return {
        p.relative_to(root).as_posix()
        for p in root.rglob("*")
        if p.is_file()
    }


def jar_entries(path: pathlib.Path) -> set[str]:
    if not path.is_file():
        raise SystemExit(f"JAR_MISSING {path}")
    with zipfile.ZipFile(path) as zf:
        return {name for name in zf.namelist() if not name.endswith("/")}


def check(label: str, reference: list[str], actual: set[str]) -> None:
    ref = set(reference)
    missing = sorted(ref - actual)
    extras = sorted(actual - ref)
    print(f"{label}_REFERENCE={len(ref)}")
    print(f"{label}_ACTUAL={len(actual)}")
    print(f"{label}_MISSING={len(missing)}")
    print(f"{label}_EXTRAS={len(extras)}")
    for item in missing:
        print(f"{label}_MISSING_ITEM={item}")
    if missing:
        raise SystemExit(f"{label}_REFERENCE_RESOURCE_GATE=FAIL")
    print(f"{label}_REFERENCE_RESOURCE_GATE=PASS")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--reference",
        default="tools/reference-abyss-resources.txt",
    )
    parser.add_argument(
        "--source-root",
        default="src/main/resources",
    )
    parser.add_argument("--jar")
    args = parser.parse_args()

    reference = load_reference(pathlib.Path(args.reference))
    check("SOURCE", reference, source_entries(pathlib.Path(args.source_root)))

    if args.jar:
        check("JAR", reference, jar_entries(pathlib.Path(args.jar)))

    print("REFERENCE_RESOURCE_AUTHORITY=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
