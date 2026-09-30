#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import re
import struct
import sys
import zipfile

# Runnable-era authority lives in tools/reference-module-body-shape.txt.
EVENT_RE = re.compile(r"^on[A-Z]")


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
        code_length = None
        max_stack = None
        max_locals = None
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
        methods[(name, desc)] = (access, code_length or 0, max_stack or 0, max_locals or 0, exceptions)
    return this_name, methods


def load_reference(path):
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
    if len(reference) != 129:
        raise SystemExit(
            f"REFERENCE_BODY_SHAPE_COUNT_BAD expected=129 actual={len(reference)}"
        )
    return reference


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-body-shape.txt",
    )
    parser.add_argument("--top", type=int, default=60)
    args = parser.parse_args()

    reference = load_reference(pathlib.Path(args.reference))
    current = {}

    with zipfile.ZipFile(args.jar) as zf:
        for name in zf.namelist():
            if not name.startswith("Abyss/module/impl/") or not name.endswith(".class") or "$" in name:
                continue
            class_name, methods = parse_class(zf.read(name))
            for (method_name, desc), metric in methods.items():
                key = (class_name, method_name, desc)
                if key in reference:
                    current[key] = metric

    missing = sorted(set(reference) - set(current))
    failures = []
    print(f"MODULE_BODY_REFERENCE_METHODS={len(reference)}")
    print(f"MODULE_BODY_CURRENT_MATCHED={len(current)}")
    print(f"MODULE_BODY_MISSING={len(missing)}")
    for key in missing[:50]:
        print("MODULE_BODY_MISSING_ITEM=" + "|".join(key))
    if missing:
        failures.append("missing-authoritative-methods")

    rows = []
    tiny = []
    for key, old in reference.items():
        if key not in current:
            continue
        _access, new_code, new_stack, new_locals, new_exc = current[key]
        old_code = old["old_code"]
        ratio = (new_code / old_code) if old_code else 1.0
        item = (ratio, old_code, new_code, key, new_stack, new_locals, new_exc)
        if old_code >= 40:
            rows.append(item)
        if old_code >= 40 and new_code <= 8:
            tiny.append(item)
        if old["old_exceptions"] > 0 and new_exc == 0:
            failures.append("exception-region-lost:" + "|".join(key))
        if old_code >= 80 and ratio < 0.75:
            # FastPlace's runnable-era bed scan was intentionally extracted into
            # findBedInRange(int). Validate that moved body separately below.
            if key != (
                "Abyss/module/impl/world/FastPlace",
                "onPreUpdate",
                "(LAbyss/event/events/PreUpdateEvent;J)V",
            ):
                failures.append(
                    f"major-handler-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
                )

    rows.sort(key=lambda x: (x[0], -x[1], x[3]))
    print(f"MODULE_BODY_OLD_GE40={len(rows)}")
    print(f"MODULE_BODY_SUSPICIOUS_TINY={len(tiny)}")
    for ratio, old_code, new_code, key, stack, locals_, exc in rows[:args.top]:
        print(
            "MODULE_BODY_SHRINK "
            f"ratio={ratio:.4f} old={old_code} new={new_code} "
            f"stack={stack} locals={locals_} exceptions={exc} "
            f"method={'|'.join(key)}"
        )

    if tiny:
        for _ratio, old_code, new_code, key, _stack, _locals, _exc in tiny:
            failures.append(
                f"tiny-handler-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
            )

    # FastPlace special case: the runnable body inlined the bed scan. Recovery
    # deliberately extracted it to a private helper. Require that helper to be
    # substantial and exception-protected, and require the combined body to stay
    # close to the old inline body rather than merely exempting the shrink.
    fast_key = (
        "Abyss/module/impl/world/FastPlace",
        "onPreUpdate",
        "(LAbyss/event/events/PreUpdateEvent;J)V",
    )
    fast_helper_key = ("findBedInRange", "(I)Lnet/minecraft/util/BlockPos;")
    fast_class = "Abyss/module/impl/world/FastPlace"
    helper_metric = None
    try:
        with zipfile.ZipFile(args.jar) as zf:
            _name, fast_methods = parse_class(zf.read(fast_class + ".class"))
            helper_metric = fast_methods.get(fast_helper_key)
    except Exception as exc:
        failures.append("fastplace-helper-read:" + str(exc))

    if helper_metric is None:
        failures.append("fastplace-helper-missing")
    else:
        _access, helper_code, _stack, _locals, helper_exc = helper_metric
        fast_old = reference[fast_key]["old_code"]
        fast_new = current[fast_key][1] if fast_key in current else 0
        combined = fast_new + helper_code
        print(
            "FASTPLACE_EXTRACTED_HELPER "
            f"old_inline={fast_old} new_handler={fast_new} helper={helper_code} "
            f"combined={combined} helper_exceptions={helper_exc}"
        )
        if helper_code < 120:
            failures.append(f"fastplace-helper-too-small:{helper_code}")
        if helper_exc < 1:
            failures.append("fastplace-helper-exception-region-missing")
        if combined < int(fast_old * 0.90):
            failures.append(
                f"fastplace-extracted-body-too-small:old={fast_old}:combined={combined}"
            )

    print(f"MODULE_BODY_HARD_FAILURES={len(failures)}")
    for failure in failures:
        print("MODULE_BODY_HARD_FAILURE=" + failure)

    if failures:
        print("MODULE_BODY_DIFFERENTIAL_GATE=FAIL")
        return 1

    print("MODULE_BODY_DIFFERENTIAL_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
