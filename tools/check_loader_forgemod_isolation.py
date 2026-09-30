#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

PREFIX = "loader_forgemod/"


def class_refs(data: bytes):
    off = 8
    cp_count = struct.unpack_from(">H", data, off)[0]
    off += 2
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag = data[off]
        off += 1
        if tag == 1:
            n = struct.unpack_from(">H", data, off)[0]
            off += 2
            cp[i] = ("Utf8", data[off:off+n].decode("utf-8", "replace"))
            off += n
        elif tag in (3, 4):
            cp[i] = (tag,)
            off += 4
        elif tag in (5, 6):
            cp[i] = (tag,)
            off += 8
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            idx = struct.unpack_from(">H", data, off)[0]
            off += 2
            cp[i] = (tag, idx)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, b = struct.unpack_from(">HH", data, off)
            off += 4
            cp[i] = (tag, a, b)
        elif tag == 15:
            off += 3
            cp[i] = (tag,)
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(idx):
        e = cp[idx]
        return e[1] if e and e[0] == "Utf8" else None

    refs = set()
    for e in cp:
        if e and e[0] == 7:
            name = utf(e[1])
            if name:
                refs.add(name)
    return refs


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    external = []
    with zipfile.ZipFile(pathlib.Path(args.jar)) as zf:
        for entry in zf.namelist():
            if not entry.endswith(".class"):
                continue
            caller = entry[:-6]
            if caller.startswith(PREFIX):
                continue
            for target in class_refs(zf.read(entry)):
                if target.startswith(PREFIX):
                    external.append((caller, target))

    external = sorted(set(external))
    print(f"LOADER_FORGEMOD_EXTERNAL_CLASS_REFERENCES={len(external)}")
    for caller, target in external:
        print(f"LOADER_FORGEMOD_EXTERNAL_REFERENCE={caller}|{target}")

    if external:
        print("LOADER_FORGEMOD_ISOLATION_GATE=FAIL")
        return 1

    print("LOADER_FORGEMOD_ISOLATION_GATE=PASS isolated=true")
    return 0


if __name__ == "__main__":
    sys.exit(main())
