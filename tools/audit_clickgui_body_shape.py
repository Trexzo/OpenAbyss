#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

TARGETS = {
    "Abyss/module/impl/configuration/ClickGUI",
    "Abyss/ui/abyss/AbyssClickGuiScreen",
    "Abyss/ui/studio/StudioClickGuiScreen",
    "Abyss/ui/raven/RavenClickGuiScreen",
    "Abyss/ui/vestige/VestigeClickGuiScreen",
    "Abyss/ui/swing/ConfigManagerWindow",
}


def _u1(data: bytes, off: int):
    return data[off], off + 1


def _u2(data: bytes, off: int):
    return struct.unpack_from(">H", data, off)[0], off + 2


def _u4(data: bytes, off: int):
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_class(data: bytes):
    off = 0
    magic, off = _u4(data, off)
    if magic != 0xCAFEBABE:
        raise ValueError("bad class magic")
    _minor, off = _u2(data, off)
    _major, off = _u2(data, off)
    cp_count, off = _u2(data, off)
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag, off = _u1(data, off)
        if tag == 1:
            n, off = _u2(data, off)
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
            idx, off = _u2(data, off)
            cp[i] = (tag, idx)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, off = _u2(data, off)
            b, off = _u2(data, off)
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

    _access, off = _u2(data, off)
    this_idx, off = _u2(data, off)
    _super_idx, off = _u2(data, off)
    this_name = cls(this_idx)

    interfaces, off = _u2(data, off)
    off += 2 * interfaces

    fields, off = _u2(data, off)
    for _ in range(fields):
        off += 6
        attr_count, off = _u2(data, off)
        for __ in range(attr_count):
            _name_idx, off = _u2(data, off)
            length, off = _u4(data, off)
            off += length

    methods_count, off = _u2(data, off)
    methods = {}
    for _ in range(methods_count):
        access, off = _u2(data, off)
        name_idx, off = _u2(data, off)
        desc_idx, off = _u2(data, off)
        attr_count, off = _u2(data, off)
        name = utf(name_idx)
        desc = utf(desc_idx)
        code_length = 0
        max_stack = 0
        max_locals = 0
        exceptions = 0
        for __ in range(attr_count):
            attr_name_idx, off = _u2(data, off)
            attr_len, off = _u4(data, off)
            attr_name = utf(attr_name_idx)
            start = off
            if attr_name == "Code":
                max_stack, off = _u2(data, off)
                max_locals, off = _u2(data, off)
                code_length, off = _u4(data, off)
                off += code_length
                exceptions, off = _u2(data, off)
                off += 8 * exceptions
                nested, off = _u2(data, off)
                for ___ in range(nested):
                    _nested_name, off = _u2(data, off)
                    nested_len, off = _u4(data, off)
                    off += nested_len
            else:
                off += attr_len
            if off != start + attr_len:
                raise ValueError(f"attribute parse drift {this_name}.{name}{desc}")
        methods[(name, desc)] = (access, code_length, max_stack, max_locals, exceptions)
    return this_name, methods


def load_reference(path: pathlib.Path):
    reference = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        old_code_s, old_exc_s, key_s = line.split(" ", 2)
        class_name, method_name, descriptor = key_s.split("|", 2)
        reference[(class_name, method_name, descriptor)] = {
            "old_code": int(old_code_s),
            "old_exceptions": int(old_exc_s),
        }
    if len(reference) != 137:
        raise SystemExit(
            f"REFERENCE_CLICKGUI_BODY_COUNT_BAD expected=137 actual={len(reference)}"
        )
    return reference


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-clickgui-body-shape.txt",
    )
    parser.add_argument("--top", type=int, default=50)
    args = parser.parse_args()

    reference = load_reference(pathlib.Path(args.reference))
    current = {}

    with zipfile.ZipFile(args.jar) as zf:
        for target in sorted(TARGETS):
            name = target + ".class"
            if name not in zf.namelist():
                continue
            class_name, methods = parse_class(zf.read(name))
            for (method_name, desc), metric in methods.items():
                key = (class_name, method_name, desc)
                if key in reference:
                    current[key] = metric

    missing = sorted(set(reference) - set(current))
    failures = []
    print(f"CLICKGUI_BODY_REFERENCE_METHODS={len(reference)}")
    print(f"CLICKGUI_BODY_CURRENT_MATCHED={len(current)}")
    print(f"CLICKGUI_BODY_MISSING={len(missing)}")
    for key in missing[:50]:
        print("CLICKGUI_BODY_MISSING_ITEM=" + "|".join(key))
    if missing:
        failures.append("missing-authoritative-methods")

    rows = []
    for key, old in reference.items():
        if key not in current:
            continue
        _access, new_code, new_stack, new_locals, new_exc = current[key]
        old_code = old["old_code"]
        ratio = (new_code / old_code) if old_code else 1.0
        if old_code >= 40:
            rows.append((ratio, old_code, new_code, key, new_stack, new_locals, new_exc))
        if old["old_exceptions"] > 0 and new_exc == 0:
            failures.append("exception-region-lost:" + "|".join(key))
        if old_code >= 80 and new_code <= 8:
            failures.append(
                f"tiny-clickgui-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
            )
        if old_code >= 160 and ratio < 0.45:
            failures.append(
                f"major-clickgui-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
            )

    rows.sort(key=lambda x: (x[0], -x[1], x[3]))
    print(f"CLICKGUI_BODY_OLD_GE40={len(rows)}")
    for ratio, old_code, new_code, key, stack, locals_, exc in rows[:args.top]:
        print(
            "CLICKGUI_BODY_SHRINK "
            f"ratio={ratio:.4f} old={old_code} new={new_code} "
            f"stack={stack} locals={locals_} exceptions={exc} "
            f"method={'|'.join(key)}"
        )

    print(f"CLICKGUI_BODY_HARD_FAILURES={len(failures)}")
    for failure in failures:
        print("CLICKGUI_BODY_HARD_FAILURE=" + failure)

    if failures:
        print("CLICKGUI_BODY_DIFFERENTIAL_GATE=FAIL")
        return 1

    print("CLICKGUI_BODY_DIFFERENTIAL_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
