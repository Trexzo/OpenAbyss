#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

TARGET = "Abyss/inject/InjectNativeBridge"
METHOD = "retransformLoaded"
DESC = "()V"


def methodrefs(data: bytes):
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

    def cls(idx):
        e = cp[idx]
        return utf(e[1]) if e and e[0] == 7 else None

    out = []
    for e in cp:
        if not e or e[0] not in (10, 11):
            continue
        owner = cls(e[1])
        nt = cp[e[2]]
        if not nt or nt[0] != 12:
            continue
        out.append((owner, utf(nt[1]), utf(nt[2])))
    return out


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    callers = []
    with zipfile.ZipFile(pathlib.Path(args.jar)) as zf:
        for entry in zf.namelist():
            if not entry.endswith(".class"):
                continue
            caller = entry[:-6]
            if caller == TARGET:
                continue
            for owner, name, desc in methodrefs(zf.read(entry)):
                if owner == TARGET and name == METHOD and desc == DESC:
                    callers.append(caller)

    callers = sorted(set(callers))
    print(f"INJECT_RETRANSFORM_LOADED_EXTERNAL_CALLERS={len(callers)}")
    for caller in callers:
        print(f"INJECT_RETRANSFORM_LOADED_CALLER={caller}")

    if callers:
        print("INJECT_RETRANSFORM_DORMANCY_GATE=FAIL")
        return 1

    print("INJECT_RETRANSFORM_DORMANCY_GATE=PASS dormant=true")
    return 0


if __name__ == "__main__":
    sys.exit(main())
