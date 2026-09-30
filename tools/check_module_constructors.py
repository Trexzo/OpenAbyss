#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile


def u1(data, off): return data[off], off + 1
def u2(data, off): return struct.unpack_from(">H", data, off)[0], off + 2
def u4(data, off): return struct.unpack_from(">I", data, off)[0], off + 4


def parse_methods(data: bytes):
    off = 0
    magic, off = u4(data, off)
    if magic != 0xCAFEBABE:
        raise ValueError("bad class magic")
    _minor, off = u2(data, off)
    _major, off = u2(data, off)
    cp_count, off = u2(data, off)
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag, off = u1(data, off)
        if tag == 1:
            n, off = u2(data, off)
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
            idx, off = u2(data, off)
            cp[i] = (tag, idx)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, off = u2(data, off)
            b, off = u2(data, off)
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

    _access, off = u2(data, off)
    this_idx, off = u2(data, off)
    _super_idx, off = u2(data, off)
    class_name = cls(this_idx)

    interfaces, off = u2(data, off)
    off += interfaces * 2

    fields, off = u2(data, off)
    for _ in range(fields):
        off += 6
        attrs, off = u2(data, off)
        for __ in range(attrs):
            _name, off = u2(data, off)
            length, off = u4(data, off)
            off += length

    count, off = u2(data, off)
    methods = {}
    for _ in range(count):
        _method_access, off = u2(data, off)
        name_idx, off = u2(data, off)
        desc_idx, off = u2(data, off)
        attrs, off = u2(data, off)
        name = utf(name_idx)
        desc = utf(desc_idx)
        code_length = 0
        exceptions = 0
        for __ in range(attrs):
            attr_name_idx, off = u2(data, off)
            attr_len, off = u4(data, off)
            attr_name = utf(attr_name_idx)
            start = off
            if attr_name == "Code":
                _max_stack, off = u2(data, off)
                _max_locals, off = u2(data, off)
                code_length, off = u4(data, off)
                off += code_length
                exceptions, off = u2(data, off)
                off += exceptions * 8
                nested, off = u2(data, off)
                for ___ in range(nested):
                    _nested_name, off = u2(data, off)
                    nested_len, off = u4(data, off)
                    off += nested_len
            else:
                off += attr_len
            if off != start + attr_len:
                raise ValueError(f"attribute drift in {class_name}.{name}{desc}")
        methods[(name, desc)] = (code_length, exceptions)

    return class_name, methods


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        old_code_s, old_exc_s, key = line.split(" ", 2)
        class_name, desc = key.split("|", 1)
        expected[(class_name, desc)] = (int(old_code_s), int(old_exc_s))
    if len(expected) != 157:
        raise SystemExit(
            f"REFERENCE_CONSTRUCTOR_COUNT_BAD expected=157 actual={len(expected)}"
        )
    return expected


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-constructors.txt",
    )
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    current = {}

    with zipfile.ZipFile(args.jar) as zf:
        for entry in zf.namelist():
            if (
                not entry.startswith("Abyss/module/impl/")
                or not entry.endswith(".class")
                or "$" in entry
            ):
                continue
            class_name, methods = parse_methods(zf.read(entry))
            for (name, desc), metric in methods.items():
                if name == "<init>":
                    current[(class_name, desc)] = metric

    missing = sorted(set(expected) - set(current))
    extra = sorted(set(current) - set(expected))
    failures = []

    for key in missing:
        failures.append("missing:" + "|".join(key))
    for key in extra:
        failures.append("extra:" + "|".join(key))

    exact = 0
    lowest = (999.0, None)
    for key in sorted(set(expected) & set(current)):
        old_code, old_exc = expected[key]
        new_code, new_exc = current[key]
        if (old_code, old_exc) == (new_code, new_exc):
            exact += 1
        ratio = (new_code / old_code) if old_code else 1.0
        if ratio < lowest[0]:
            lowest = (ratio, key)
        if ratio < 0.90:
            failures.append(
                f"constructor-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
            )
        if old_exc > 0 and new_exc == 0:
            failures.append("constructor-exception-region-lost:" + "|".join(key))

    print(f"MODULE_CONSTRUCTOR_REFERENCE={len(expected)}")
    print(f"MODULE_CONSTRUCTOR_CURRENT={len(current)}")
    print(f"MODULE_CONSTRUCTOR_EXACT_METRICS={exact}")
    print(f"MODULE_CONSTRUCTOR_MISSING={len(missing)}")
    print(f"MODULE_CONSTRUCTOR_EXTRA={len(extra)}")
    if lowest[1] is not None:
        print(
            "MODULE_CONSTRUCTOR_LOWEST_RATIO="
            f"{lowest[0]:.4f} method={'|'.join(lowest[1])}"
        )
    print(f"MODULE_CONSTRUCTOR_FAILURES={len(failures)}")
    for failure in failures:
        print("MODULE_CONSTRUCTOR_FAILURE=" + failure)

    if failures:
        print("MODULE_CONSTRUCTOR_GATE=FAIL")
        return 1

    print("MODULE_CONSTRUCTOR_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
