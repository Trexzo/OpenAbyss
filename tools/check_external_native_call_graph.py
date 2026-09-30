#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

ACC_NATIVE = 0x0100


def parse_class(data: bytes):
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

    _access, this_idx, _super = struct.unpack_from(">HHH", data, off)
    off += 6
    this_name = cls(this_idx)
    interfaces = struct.unpack_from(">H", data, off)[0]
    off += 2 + 2 * interfaces

    fields = struct.unpack_from(">H", data, off)[0]
    off += 2
    for _ in range(fields):
        off += 6
        attrs = struct.unpack_from(">H", data, off)[0]
        off += 2
        for __ in range(attrs):
            off += 2
            length = struct.unpack_from(">I", data, off)[0]
            off += 4 + length

    methods_count = struct.unpack_from(">H", data, off)[0]
    off += 2
    native = set()
    for _ in range(methods_count):
        access, name_idx, desc_idx, attrs = struct.unpack_from(">HHHH", data, off)
        off += 8
        name = utf(name_idx)
        desc = utf(desc_idx)
        if access & ACC_NATIVE:
            native.add((name, desc))
        for __ in range(attrs):
            off += 2
            length = struct.unpack_from(">I", data, off)[0]
            off += 4 + length

    refs = []
    for e in cp:
        if not e or e[0] not in (10, 11):
            continue
        owner = cls(e[1])
        nt = cp[e[2]]
        if not nt or nt[0] != 12:
            continue
        refs.append((owner, utf(nt[1]), utf(nt[2])))
    return this_name, native, refs


def load_reference(path: pathlib.Path):
    rows = set()
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        rows.add(tuple(line.split("|", 3)))
    if len(rows) != 16:
        raise SystemExit(
            f"REFERENCE_EXTERNAL_NATIVE_COUNT_BAD expected=16 actual={len(rows)}"
        )
    return rows


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-external-native-calls.txt",
    )
    args = parser.parse_args()

    allowed = load_reference(pathlib.Path(args.reference))
    classes = {}
    with zipfile.ZipFile(pathlib.Path(args.jar)) as zf:
        for entry in zf.namelist():
            if not entry.endswith(".class"):
                continue
            name, native, refs = parse_class(zf.read(entry))
            classes[name] = (native, refs)

    native_methods = {
        (owner, name, desc)
        for owner, (methods, _refs) in classes.items()
        for name, desc in methods
    }
    external = set()
    for caller, (_methods, refs) in classes.items():
        for owner, name, desc in refs:
            if caller != owner and (owner, name, desc) in native_methods:
                external.add((caller, owner, name, desc))

    unexpected = sorted(external - allowed)
    removed = sorted(allowed - external)

    print(f"EXTERNAL_NATIVE_CALLS_CURRENT={len(external)}")
    print(f"EXTERNAL_NATIVE_CALLS_UNEXPECTED={len(unexpected)}")
    print(f"EXTERNAL_NATIVE_CALLS_BASELINE_REMOVED={len(removed)}")
    for row in unexpected:
        print("EXTERNAL_NATIVE_UNEXPECTED=" + "|".join(row))
    for row in removed:
        print("EXTERNAL_NATIVE_BASELINE_REMOVED=" + "|".join(row))

    if unexpected:
        print("EXTERNAL_NATIVE_BOUNDARY_GATE=FAIL")
        return 1

    print("EXTERNAL_NATIVE_BOUNDARY_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
