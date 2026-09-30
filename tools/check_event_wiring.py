#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import struct
import sys
import zipfile


KINDS = {
    "BINDER": "Abyss/event/binder/",
    "INVOKER": "Abyss/event/invoker/",
}


def u1(data, off):
    return data[off], off + 1


def u2(data, off):
    return struct.unpack_from(">H", data, off)[0], off + 2


def parse_wiring(data: bytes):
    off = 8
    cp_count, off = u2(data, off)
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag, off = u1(data, off)
        if tag == 1:
            length, off = u2(data, off)
            cp[i] = ("Utf8", data[off:off + length].decode("utf-8", "replace"))
            off += length
        elif tag in (3, 4):
            cp[i] = (tag,)
            off += 4
        elif tag in (5, 6):
            cp[i] = (tag,)
            off += 8
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            index, off = u2(data, off)
            cp[i] = (tag, index)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, off = u2(data, off)
            b, off = u2(data, off)
            cp[i] = (tag, a, b)
        elif tag == 15:
            kind, off = u1(data, off)
            index, off = u2(data, off)
            cp[i] = (tag, kind, index)
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(index):
        entry = cp[index]
        return entry[1] if entry and entry[0] == "Utf8" else None

    def class_name(index):
        entry = cp[index]
        return utf(entry[1]) if entry and entry[0] == 7 else None

    def name_type(index):
        entry = cp[index]
        if not entry or entry[0] != 12:
            return None, None
        return utf(entry[1]), utf(entry[2])

    classes = set()
    methods = set()
    fields = set()

    for index, entry in enumerate(cp):
        if not entry:
            continue
        tag = entry[0]
        if tag == 7:
            name = class_name(index)
            if name and (
                name.startswith("Abyss/")
                or name.startswith("net/minecraft/")
            ):
                classes.add(name)
        elif tag in (10, 11):
            owner = class_name(entry[1])
            name, desc = name_type(entry[2])
            methods.add((tag, owner, name, desc))
        elif tag == 9:
            owner = class_name(entry[1])
            name, desc = name_type(entry[2])
            fields.add((owner, name, desc))

    payload = (
        "".join(f"C\t{name}\n" for name in sorted(classes))
        + "".join(
            f"M\t{tag}\t{owner}\t{name}\t{desc}\n"
            for tag, owner, name, desc in sorted(methods)
        )
        + "".join(
            f"F\t{owner}\t{name}\t{desc}\n"
            for owner, name, desc in sorted(fields)
        )
    ).encode("utf-8")

    return (
        hashlib.sha256(payload).hexdigest(),
        len(classes),
        len(methods),
        len(fields),
    )


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        kind, count_s, sha256, classes_s, methods_s, fields_s = line.split()
        expected[kind] = (
            int(count_s),
            sha256.lower(),
            int(classes_s),
            int(methods_s),
            int(fields_s),
        )

    if set(expected) != set(KINDS):
        raise SystemExit("EVENT_WIRING_REFERENCE_KIND_SET_BAD")
    return expected


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-event-wiring.txt",
    )
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    by_kind = {kind: {} for kind in KINDS}

    with zipfile.ZipFile(args.jar) as zf:
        for entry in zf.namelist():
            if not entry.endswith(".class") or "$" in entry:
                continue
            class_name = entry[:-6]
            for kind, prefix in KINDS.items():
                if class_name.startswith(prefix):
                    by_kind[kind][class_name] = parse_wiring(zf.read(entry))
                    break

    failures = []

    for kind, prefix in KINDS.items():
        classes = by_kind[kind]
        rows = []
        class_ref_total = 0
        method_ref_total = 0
        field_ref_total = 0

        for class_name in sorted(classes):
            sha256, class_refs, method_refs, field_refs = classes[class_name]
            rows.append(f"{class_name}\t{sha256}\n")
            class_ref_total += class_refs
            method_ref_total += method_refs
            field_ref_total += field_refs

        aggregate = hashlib.sha256("".join(rows).encode("utf-8")).hexdigest()
        actual = (
            len(classes),
            aggregate,
            class_ref_total,
            method_ref_total,
            field_ref_total,
        )
        want = expected[kind]

        print(f"EVENT_WIRING_{kind}_CLASSES={actual[0]}")
        print(f"EVENT_WIRING_{kind}_SHA256={actual[1]}")
        print(f"EVENT_WIRING_{kind}_CLASS_REFS={actual[2]}")
        print(f"EVENT_WIRING_{kind}_METHOD_REFS={actual[3]}")
        print(f"EVENT_WIRING_{kind}_FIELD_REFS={actual[4]}")

        if actual != want:
            failures.append(
                f"{kind}:expected={want}:actual={actual}"
            )

    print(f"EVENT_WIRING_FAILURES={len(failures)}")
    for failure in failures:
        print("EVENT_WIRING_BAD=" + failure)

    if failures:
        print("EVENT_WIRING_GATE=FAIL")
        return 1

    print("EVENT_WIRING_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
