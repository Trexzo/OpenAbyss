#!/usr/bin/env python3
from __future__ import annotations

import argparse
import collections
import pathlib
import struct
import sys
import zipfile


JAVA8_MAJOR = 52


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    path = pathlib.Path(args.jar)
    if not path.is_file():
        raise SystemExit(f"JAR_MISSING {path}")

    versions: collections.Counter[tuple[int, int]] = collections.Counter()
    invalid: list[str] = []
    classes = 0

    with zipfile.ZipFile(path) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            classes += 1
            data = zf.read(name)
            if len(data) < 8 or data[:4] != b"\xca\xfe\xba\xbe":
                invalid.append(f"{name}:bad-magic")
                continue
            minor, major = struct.unpack(">HH", data[4:8])
            versions[(major, minor)] += 1
            if major != JAVA8_MAJOR:
                invalid.append(f"{name}:major={major}:minor={minor}")

    print(f"JAR_CLASS_COUNT={classes}")
    for (major, minor), count in sorted(versions.items()):
        print(f"JAR_CLASS_VERSION major={major} minor={minor} count={count}")

    if classes == 0:
        raise SystemExit("JAR_BYTECODE_GATE=FAIL no classes")
    if invalid:
        for item in invalid[:50]:
            print(f"JAR_BYTECODE_INVALID={item}")
        raise SystemExit(f"JAR_BYTECODE_GATE=FAIL invalid={len(invalid)}")

    print("JAR_BYTECODE_GATE=PASS java=8 major=52")
    return 0


if __name__ == "__main__":
    sys.exit(main())
