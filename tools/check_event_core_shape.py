#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile


TARGETS = (
    "Abyss/event/EventBus",
    "Abyss/event/Event",
    "Abyss/event/ListenerBinding",
)

ALLOWED_FIELDS = {
    "Abyss/event/EventBus": {
        ("ownerNames", "Ljava/util/Map;"),
        ("recordedFailures", "Ljava/util/Map;"),
        ("failureEvidenceEnabled", "Z"),
    }
}
ALLOWED_METHODS = {
    "Abyss/event/EventBus": {
        ("recordFailure", "(LAbyss/event/ListenerBinding;LAbyss/event/Event;Ljava/lang/Throwable;)V"),
        ("selfTest", "()Ljava/lang/String;"),
    }
}
REVIEWED_CODE_LENGTHS = {
    ("Abyss/event/EventBus", "<init>", "()V"): 87,
    ("Abyss/event/EventBus", "R", "(Ljava/lang/Object;Ljava/lang/Class;ILAbyss/event/EventInvoker;)V"): 238,
    ("Abyss/event/EventBus", "e", "(LAbyss/event/Event;J)V"): 142,
    ("Abyss/event/ListenerBinding", "S", "(LAbyss/event/ListenerBinding;Z)Z"): 10,
}


def u1(data: bytes, off: int):
    return data[off], off + 1


def u2(data: bytes, off: int):
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data: bytes, off: int):
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_class(data: bytes):
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
        fields.append((access, utf(name_idx), utf(desc_idx)))
        for __ in range(attrs):
            _an, off = u2(data, off)
            n, off = u4(data, off)
            off += n

    method_count, off = u2(data, off)
    methods = []
    for _ in range(method_count):
        access, off = u2(data, off)
        name_idx, off = u2(data, off)
        desc_idx, off = u2(data, off)
        attrs, off = u2(data, off)
        name = utf(name_idx)
        desc = utf(desc_idx)
        code_len = -1
        exception_count = 0
        max_stack = 0
        max_locals = 0
        for __ in range(attrs):
            attr_name_idx, off = u2(data, off)
            attr_len, off = u4(data, off)
            attr_name = utf(attr_name_idx)
            attr_start = off
            if attr_name == "Code":
                max_stack, off2 = u2(data, off)
                max_locals, off2 = u2(data, off2)
                code_len, off2 = u4(data, off2)
                off2 += code_len
                exception_count, off2 = u2(data, off2)
                off2 += exception_count * 8
            off = attr_start + attr_len
        methods.append((access, name, desc, code_len, exception_count, max_stack, max_locals))

    return class_name, fields, methods


def load_reference(path: pathlib.Path):
    classes = {}
    current = None
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        parts = line.split(" ")
        if parts[0] == "CLASS":
            current = parts[1]
            classes[current] = {"fields": [], "methods": []}
        elif parts[0] == "FIELD":
            if current is None:
                raise SystemExit("EVENT_CORE_REFERENCE_FIELD_WITHOUT_CLASS")
            classes[current]["fields"].append(
                (int(parts[1]), parts[2], parts[3])
            )
        elif parts[0] == "METHOD":
            if current is None:
                raise SystemExit("EVENT_CORE_REFERENCE_METHOD_WITHOUT_CLASS")
            classes[current]["methods"].append(
                (
                    int(parts[1]),
                    parts[2],
                    parts[3],
                    int(parts[4]),
                    int(parts[5]),
                    int(parts[6]),
                    int(parts[7]),
                )
            )
        else:
            raise SystemExit("EVENT_CORE_REFERENCE_BAD_LINE=" + line)
    if set(classes) != set(TARGETS):
        raise SystemExit("EVENT_CORE_REFERENCE_CLASS_SET_BAD")
    return classes


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-event-core.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    reference = load_reference(pathlib.Path(args.reference))
    current = {}
    with zipfile.ZipFile(jar) as zf:
        for class_name in TARGETS:
            entry = class_name + ".class"
            if entry not in zf.namelist():
                raise SystemExit("EVENT_CORE_CLASS_MISSING=" + class_name)
            parsed_name, fields, methods = parse_class(zf.read(entry))
            if parsed_name != class_name:
                raise SystemExit(
                    f"EVENT_CORE_CLASS_NAME_BAD expected={class_name} actual={parsed_name}"
                )
            current[class_name] = {"fields": fields, "methods": methods}

    failures = []

    for class_name in TARGETS:
        ref_fields = {
            (access, name, desc)
            for access, name, desc in reference[class_name]["fields"]
        }
        cur_fields = {
            (access, name, desc)
            for access, name, desc in current[class_name]["fields"]
        }

        missing_fields = sorted(ref_fields - cur_fields)
        for item in missing_fields:
            failures.append(f"field-missing:{class_name}:{item}")

        expected_additions = ALLOWED_FIELDS.get(class_name, set())
        actual_extra_fields = {
            (name, desc)
            for access, name, desc in (cur_fields - ref_fields)
        }
        if actual_extra_fields != expected_additions:
            failures.append(
                "field-extra:"
                + class_name
                + ":expected="
                + repr(sorted(expected_additions))
                + ":actual="
                + repr(sorted(actual_extra_fields))
            )

        ref_methods = {
            (access, name, desc): (code_len, exc, stack, locals_)
            for access, name, desc, code_len, exc, stack, locals_
            in reference[class_name]["methods"]
        }
        cur_methods = {
            (access, name, desc): (code_len, exc, stack, locals_)
            for access, name, desc, code_len, exc, stack, locals_
            in current[class_name]["methods"]
        }

        for key, ref_metrics in ref_methods.items():
            if key not in cur_methods:
                failures.append(f"method-missing:{class_name}:{key}")
                continue
            cur_metrics = cur_methods[key]
            reviewed_len = REVIEWED_CODE_LENGTHS.get(
                (class_name, key[1], key[2])
            )
            expected_code_len = (
                reviewed_len if reviewed_len is not None else ref_metrics[0]
            )
            expected_metrics = (
                expected_code_len,
                ref_metrics[1],
                ref_metrics[2],
                ref_metrics[3],
            )
            # Reviewed methods intentionally changed body size and may require
            # different stack/local counts; their exception-table shape must
            # still remain identical unless explicitly changed in authority.
            if reviewed_len is not None:
                if (
                    cur_metrics[0] != expected_code_len
                    or cur_metrics[1] != ref_metrics[1]
                ):
                    failures.append(
                        f"method-reviewed-delta:{class_name}:{key}:"
                        f"expected-code/exc={expected_code_len}/{ref_metrics[1]}:"
                        f"actual={cur_metrics[0]}/{cur_metrics[1]}"
                    )
            elif cur_metrics != expected_metrics:
                failures.append(
                    f"method-shape:{class_name}:{key}:"
                    f"expected={expected_metrics}:actual={cur_metrics}"
                )

        ref_keys = set(ref_methods)
        cur_keys = set(cur_methods)
        extra_keys = cur_keys - ref_keys
        expected_method_additions = ALLOWED_METHODS.get(class_name, set())
        actual_method_additions = {(name, desc) for _access, name, desc in extra_keys}
        # javac may synthesize lambda helpers when implementation details use
        # lambdas; do not treat those as semantic public/private API additions.
        synthetic_lambda = {
            item for item in actual_method_additions if item[0].startswith("lambda$")
        }
        actual_semantic = actual_method_additions - synthetic_lambda
        if actual_semantic != expected_method_additions:
            failures.append(
                "method-extra:"
                + class_name
                + ":expected="
                + repr(sorted(expected_method_additions))
                + ":actual="
                + repr(sorted(actual_semantic))
            )

    print("EVENT_CORE_REFERENCE_CLASSES=3")
    print("EVENT_CORE_CURRENT_CLASSES=" + str(len(current)))
    print("EVENT_CORE_ALLOWED_FIELD_ADDITIONS=3")
    print("EVENT_CORE_ALLOWED_METHOD_ADDITIONS=2")
    print("EVENT_CORE_REVIEWED_BODY_DELTAS=4")
    print("EVENT_CORE_FAILURES=" + str(len(failures)))
    for failure in failures[:100]:
        print("EVENT_CORE_BAD=" + failure)

    if failures:
        print("EVENT_CORE_GATE=FAIL")
        return 1

    print("EVENT_CORE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
