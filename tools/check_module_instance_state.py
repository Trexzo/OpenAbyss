#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import struct
import sys
import zipfile


ACC_STATIC = 0x0008


def u1(data: bytes, off: int):
    return data[off], off + 1


def u2(data: bytes, off: int):
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data: bytes, off: int):
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_fields(data: bytes):
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

    def utf(idx: int):
        entry = cp[idx]
        return entry[1] if entry and entry[0] == "Utf8" else None

    def cls(idx: int):
        entry = cp[idx]
        return utf(entry[1]) if entry and entry[0] == 7 else None

    _class_access, off = u2(data, off)
    this_idx, off = u2(data, off)
    _super_idx, off = u2(data, off)
    class_name = cls(this_idx)

    interfaces, off = u2(data, off)
    off += interfaces * 2

    field_count, off = u2(data, off)
    fields = []
    for _ in range(field_count):
        access, off = u2(data, off)
        name_idx, off = u2(data, off)
        desc_idx, off = u2(data, off)
        attr_count, off = u2(data, off)
        fields.append((utf(name_idx), utf(desc_idx), access))
        for __ in range(attr_count):
            _attr_name, off = u2(data, off)
            attr_len, off = u4(data, off)
            off += attr_len

    return class_name, fields


def load_reference(path: pathlib.Path):
    expected = {}
    total = 0
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        count_s, sha256, class_name = line.split(" ", 2)
        count = int(count_s)
        expected[class_name] = (count, sha256.lower())
        total += count
    if len(expected) != 140:
        raise SystemExit(
            f"REFERENCE_INSTANCE_CLASS_COUNT_BAD expected=140 actual={len(expected)}"
        )
    if total != 462:
        raise SystemExit(
            f"REFERENCE_INSTANCE_FIELD_COUNT_BAD expected=462 actual={total}"
        )
    return expected


def class_digest(fields):
    instance = sorted(
        (name, desc, access)
        for name, desc, access in fields
        if not (access & ACC_STATIC)
    )
    payload = "".join(
        f"{name}\t{desc}\t{access}\n"
        for name, desc, access in instance
    ).encode("utf-8")
    return len(instance), hashlib.sha256(payload).hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-instance-fields.txt",
    )
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    actual = {}

    with zipfile.ZipFile(args.jar) as zf:
        for entry in zf.namelist():
            if (
                not entry.startswith("Abyss/module/impl/")
                or not entry.endswith(".class")
                or "$" in entry
            ):
                continue
            class_name, fields = parse_fields(zf.read(entry))
            actual[class_name] = class_digest(fields)

    missing = sorted(set(expected) - set(actual))
    extra = sorted(set(actual) - set(expected))
    mismatched = sorted(
        class_name
        for class_name in set(expected) & set(actual)
        if expected[class_name] != actual[class_name]
    )

    print(f"MODULE_INSTANCE_STATE_REFERENCE_CLASSES={len(expected)}")
    print(f"MODULE_INSTANCE_STATE_CURRENT_CLASSES={len(actual)}")
    print(
        "MODULE_INSTANCE_STATE_REFERENCE_FIELDS="
        + str(sum(count for count, _sha in expected.values()))
    )
    print(
        "MODULE_INSTANCE_STATE_CURRENT_FIELDS="
        + str(sum(count for count, _sha in actual.values()))
    )
    print(f"MODULE_INSTANCE_STATE_MISSING_CLASSES={len(missing)}")
    print(f"MODULE_INSTANCE_STATE_EXTRA_CLASSES={len(extra)}")
    print(f"MODULE_INSTANCE_STATE_MISMATCHED_CLASSES={len(mismatched)}")

    for class_name in missing:
        print("MODULE_INSTANCE_STATE_MISSING=" + class_name)
    for class_name in extra:
        print("MODULE_INSTANCE_STATE_EXTRA=" + class_name)
    for class_name in mismatched:
        print(
            "MODULE_INSTANCE_STATE_BAD="
            + class_name
            + f" expected={expected[class_name][0]}/{expected[class_name][1]}"
            + f" actual={actual[class_name][0]}/{actual[class_name][1]}"
        )

    if missing or extra or mismatched:
        print("MODULE_INSTANCE_STATE_GATE=FAIL")
        return 1

    if len(actual) != 140 or sum(count for count, _sha in actual.values()) != 462:
        print("MODULE_INSTANCE_STATE_GATE=FAIL totals")
        return 1

    print("MODULE_INSTANCE_STATE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
