#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import struct
import sys
import zipfile


ACC_STATIC = 0x0008
SCAFFOLD_DESCRIPTORS = {
    "J",
    "[Ljava/lang/String;",
    "[Ljava/lang/Object;",
    "Ljava/util/Map;",
}
ALLOWED_ADDITIONS = {
    (
        "Abyss/module/impl/configuration/ClickGUI",
        "mode",
        "LAbyss/setting/settings/ModeSetting;",
        0x0009,
    ),
    (
        "Abyss/module/impl/configuration/ClickGUI",
        "keybind",
        "LAbyss/setting/settings/TextSetting;",
        0x0009,
    ),
}


def u1(data, off): return data[off], off + 1
def u2(data, off): return struct.unpack_from(">H", data, off)[0], off + 2
def u4(data, off): return struct.unpack_from(">I", data, off)[0], off + 4


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

    field_count, off = u2(data, off)
    fields = []
    for _ in range(field_count):
        access, off = u2(data, off)
        name_idx, off = u2(data, off)
        desc_idx, off = u2(data, off)
        attrs, off = u2(data, off)
        fields.append((utf(name_idx), utf(desc_idx), access))
        for __ in range(attrs):
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
            f"REFERENCE_SEMANTIC_STATIC_CLASS_COUNT_BAD expected=140 actual={len(expected)}"
        )
    if total != 1016:
        raise SystemExit(
            f"REFERENCE_SEMANTIC_STATIC_FIELD_COUNT_BAD expected=1016 actual={total}"
        )
    return expected


def digest(fields):
    semantic = sorted(
        (name, desc, access)
        for name, desc, access in fields
        if (access & ACC_STATIC) and desc not in SCAFFOLD_DESCRIPTORS
    )
    payload = "".join(
        f"{name}\t{desc}\t{access}\n"
        for name, desc, access in semantic
    ).encode("utf-8")
    return semantic, hashlib.sha256(payload).hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-semantic-static-fields.txt",
    )
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    actual_fields = {}

    with zipfile.ZipFile(args.jar) as zf:
        for entry in zf.namelist():
            if (
                not entry.startswith("Abyss/module/impl/")
                or not entry.endswith(".class")
                or "$" in entry
            ):
                continue
            class_name, fields = parse_fields(zf.read(entry))
            actual_fields[class_name] = fields

    if set(actual_fields) != set(expected):
        missing = sorted(set(expected) - set(actual_fields))
        extra = sorted(set(actual_fields) - set(expected))
        for class_name in missing:
            print("MODULE_SEMANTIC_STATIC_MISSING_CLASS=" + class_name)
        for class_name in extra:
            print("MODULE_SEMANTIC_STATIC_EXTRA_CLASS=" + class_name)
        print("MODULE_SEMANTIC_STATIC_GATE=FAIL class-set")
        return 1

    allowed_by_class = {}
    for class_name, name, desc, access in ALLOWED_ADDITIONS:
        allowed_by_class.setdefault(class_name, set()).add((name, desc, access))

    mismatches = []
    unexpected_additions = []
    current_reference_count = 0

    for class_name in sorted(expected):
        fields, _raw_hash = digest(actual_fields[class_name])
        allowed = allowed_by_class.get(class_name, set())
        filtered = [field for field in fields if field not in allowed]
        additions_seen = set(fields) & allowed

        payload = "".join(
            f"{name}\t{desc}\t{access}\n"
            for name, desc, access in filtered
        ).encode("utf-8")
        got = (len(filtered), hashlib.sha256(payload).hexdigest())
        current_reference_count += len(filtered)

        if got != expected[class_name]:
            mismatches.append((class_name, expected[class_name], got))

        for field in fields:
            if field in allowed:
                continue
        if class_name in allowed_by_class:
            expected_allowed = allowed_by_class[class_name]
            if additions_seen != expected_allowed:
                unexpected_additions.append(
                    (class_name, sorted(expected_allowed), sorted(additions_seen))
                )

    # Ensure there are no semantic-static additions outside the two explicit ones.
    total_semantic_current = 0
    for class_name, fields in actual_fields.items():
        semantic, _ = digest(fields)
        total_semantic_current += len(semantic)

    expected_current_total = 1016 + len(ALLOWED_ADDITIONS)
    if total_semantic_current != expected_current_total:
        unexpected_additions.append(
            ("<total>", expected_current_total, total_semantic_current)
        )

    print("MODULE_SEMANTIC_STATIC_REFERENCE_CLASSES=140")
    print("MODULE_SEMANTIC_STATIC_REFERENCE_FIELDS=1016")
    print(f"MODULE_SEMANTIC_STATIC_CURRENT_FIELDS={total_semantic_current}")
    print(f"MODULE_SEMANTIC_STATIC_ALLOWED_ADDITIONS={len(ALLOWED_ADDITIONS)}")
    print(f"MODULE_SEMANTIC_STATIC_MISMATCHED_CLASSES={len(mismatches)}")
    print(f"MODULE_SEMANTIC_STATIC_ADDITION_ERRORS={len(unexpected_additions)}")

    for item in mismatches:
        print("MODULE_SEMANTIC_STATIC_BAD=" + repr(item))
    for item in unexpected_additions:
        print("MODULE_SEMANTIC_STATIC_ADDITION_BAD=" + repr(item))

    if mismatches or unexpected_additions or current_reference_count != 1016:
        print("MODULE_SEMANTIC_STATIC_GATE=FAIL")
        return 1

    print("MODULE_SEMANTIC_STATIC_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
