#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import sys
import zipfile


EXPECTED_COUNT = 1227
EXPECTED_SHA256 = "8B42A1C6726541D793A03AF2106EDFA5B8786EB83D06C9DE2687CB9B09967609"
REFERENCE_JAR_SHA256 = "13814827D8341CA6F4D7510F8B07C66F998EB7F43FA70BB18B0755AB7A4474F1"


def fingerprint(names: list[str]) -> str:
    payload = "\n".join(sorted(names)).encode("utf-8")
    return hashlib.sha256(payload).hexdigest().upper()


def source_names(root: pathlib.Path) -> list[str]:
    return sorted(
        p.relative_to(root).as_posix()[:-5]
        for p in root.rglob("*.java")
        if p.is_file()
    )


def jar_names(path: pathlib.Path) -> list[str]:
    result: list[str] = []
    with zipfile.ZipFile(path) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            simple = name.rsplit("/", 1)[-1][:-6]
            if "$" in simple:
                continue
            result.append(name[:-6])
    return sorted(result)


def verify(label: str, names: list[str]) -> None:
    digest = fingerprint(names)
    unique = len(set(names))
    print(f"{label}_TOP_LEVEL_CLASS_COUNT={len(names)}")
    print(f"{label}_TOP_LEVEL_CLASS_UNIQUE={unique}")
    print(f"{label}_TOP_LEVEL_CLASSSET_SHA256={digest}")
    if len(names) != EXPECTED_COUNT or unique != EXPECTED_COUNT:
        raise SystemExit(
            f"{label}_EXACT_CLASSSET_GATE=FAIL count={len(names)} unique={unique}"
        )
    if digest != EXPECTED_SHA256:
        raise SystemExit(
            f"{label}_EXACT_CLASSSET_GATE=FAIL expected={EXPECTED_SHA256} actual={digest}"
        )
    print(f"{label}_EXACT_CLASSSET_GATE=PASS")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source-root", default="src/main/java")
    parser.add_argument("--jar", required=True)
    args = parser.parse_args()

    source_root = pathlib.Path(args.source_root)
    jar_path = pathlib.Path(args.jar)
    if not source_root.is_dir():
        raise SystemExit(f"SOURCE_ROOT_MISSING {source_root}")
    if not jar_path.is_file():
        raise SystemExit(f"JAR_MISSING {jar_path}")

    verify("SOURCE", source_names(source_root))
    verify("JAR", jar_names(jar_path))
    print(f"REFERENCE_JAR_SHA256={REFERENCE_JAR_SHA256}")
    print("RUNNABLE_ERA_EXACT_TOP_LEVEL_CLASSSET=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
