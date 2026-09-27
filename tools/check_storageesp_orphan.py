#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import sys
import zipfile


TARGET = "Abyss/module/impl/visual_utility/StorageESP"
ALLOWED_OWNERS = {
    TARGET + ".class",
    TARGET + "$1.class",
}


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    references = []
    with zipfile.ZipFile(jar) as zf:
        names = set(zf.namelist())
        for required in ALLOWED_OWNERS:
            if required not in names:
                raise SystemExit(f"STORAGEESP_REQUIRED_CLASS_MISSING={required}")

        for name in sorted(names):
            if not name.endswith(".class"):
                continue
            data = zf.read(name)
            if TARGET.encode("utf-8") in data:
                references.append(name)

    external = sorted(set(references) - ALLOWED_OWNERS)

    print(f"STORAGEESP_REFERENCE_CLASSES={len(references)}")
    for name in references:
        print("STORAGEESP_REFERENCE_CLASS=" + name)
    print(f"STORAGEESP_EXTERNAL_REFERENCES={len(external)}")
    for name in external:
        print("STORAGEESP_EXTERNAL_REFERENCE=" + name)

    if set(references) != ALLOWED_OWNERS or external:
        print("STORAGEESP_ORPHAN_GATE=FAIL")
        return 1

    print("STORAGEESP_DECLARED_BUT_UNREGISTERED=TRUE")
    print("STORAGEESP_ORPHAN_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
