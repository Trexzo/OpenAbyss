#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

TARGET = "Abyss/ui/swing/ConfigManagerWindow"


def parse_methodrefs(data: bytes):
    if len(data) < 10 or data[:4] != b"\xca\xfe\xba\xbe":
        raise ValueError("bad class")
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

    def utf(idx: int):
        e = cp[idx]
        return e[1] if e and e[0] == "Utf8" else None

    def cls(idx: int):
        e = cp[idx]
        return utf(e[1]) if e and e[0] == 7 else None

    refs = []
    for e in cp:
        if not e or e[0] not in (10, 11):
            continue
        owner = cls(e[1])
        nt = cp[e[2]]
        if not nt or nt[0] != 12:
            continue
        name = utf(nt[1])
        desc = utf(nt[2])
        refs.append((owner, name, desc))
    return refs


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    callers = []
    with zipfile.ZipFile(jar) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            refs = parse_methodrefs(zf.read(name))
            for owner, method, desc in refs:
                if owner == TARGET and method == "<init>":
                    callers.append((name[:-6], desc))

    print(f"CONFIG_MANAGER_CONSTRUCTOR_REFERENCES={len(callers)}")
    for caller, desc in callers:
        print(f"CONFIG_MANAGER_CONSTRUCTOR_CALLER={caller}|{desc}")

    if callers:
        print("CONFIG_MANAGER_REACHABILITY_GATE=FAIL")
        return 1

    print("CONFIG_MANAGER_REACHABILITY_GATE=PASS dormant-native-window=true")
    return 0


if __name__ == "__main__":
    sys.exit(main())
