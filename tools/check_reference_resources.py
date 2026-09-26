#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
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


def load_hashes(path: pathlib.Path) -> dict[str, tuple[str, int]]:
    expected: dict[str, tuple[str, int]] = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        sha256, size, name = line.split(" ", 2)
        expected[name.replace("\\", "/")] = (sha256.lower(), int(size))
    if len(expected) != 88:
        raise SystemExit(f"REFERENCE_RESOURCE_HASH_COUNT_MISMATCH expected=88 actual={len(expected)}")
    return expected


def digest_bytes(data: bytes) -> tuple[str, int]:
    return hashlib.sha256(data).hexdigest(), len(data)


def verify_source_hashes(root: pathlib.Path, expected: dict[str, tuple[str, int]]) -> None:
    bad: list[str] = []
    for name, want in expected.items():
        path = root / pathlib.PurePosixPath(name)
        if not path.is_file():
            bad.append(f"{name}:missing")
            continue
        got = digest_bytes(path.read_bytes())
        if got != want:
            bad.append(f"{name}:expected={want[0]}/{want[1]} got={got[0]}/{got[1]}")
    print(f"SOURCE_RESOURCE_HASH_MISMATCHES={len(bad)}")
    for item in bad[:50]:
        print(f"SOURCE_RESOURCE_HASH_BAD={item}")
    if bad:
        raise SystemExit("SOURCE_RESOURCE_HASH_GATE=FAIL")
    print("SOURCE_RESOURCE_HASH_GATE=PASS")


def verify_jar_hashes(path: pathlib.Path, expected: dict[str, tuple[str, int]]) -> None:
    bad: list[str] = []
    with zipfile.ZipFile(path) as zf:
        names = set(zf.namelist())
        for name, want in expected.items():
            if name not in names:
                bad.append(f"{name}:missing")
                continue
            got = digest_bytes(zf.read(name))
            if got != want:
                bad.append(f"{name}:expected={want[0]}/{want[1]} got={got[0]}/{got[1]}")
    print(f"JAR_RESOURCE_HASH_MISMATCHES={len(bad)}")
    for item in bad[:50]:
        print(f"JAR_RESOURCE_HASH_BAD={item}")
    if bad:
        raise SystemExit("JAR_RESOURCE_HASH_GATE=FAIL")
    print("JAR_RESOURCE_HASH_GATE=PASS")


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
    parser.add_argument(
        "--hash-reference",
        default="tools/reference-abyss-resource-hashes.txt",
    )
    parser.add_argument("--jar")
    args = parser.parse_args()

    reference = load_reference(pathlib.Path(args.reference))
    hashes = load_hashes(pathlib.Path(args.hash_reference))
    source_root = pathlib.Path(args.source_root)

    check("SOURCE", reference, source_entries(source_root))
    verify_source_hashes(source_root, hashes)

    if args.jar:
        jar_path = pathlib.Path(args.jar)
        check("JAR", reference, jar_entries(jar_path))
        verify_jar_hashes(jar_path, hashes)

    print("REFERENCE_RESOURCE_AUTHORITY=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
