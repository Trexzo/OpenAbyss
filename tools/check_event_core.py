#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile


EVENTBUS = "Abyss/event/EventBus"
LISTENER = "Abyss/event/ListenerBinding"

ALLOWED_EVENTBUS_FIELDS = {
    # Reviewed recovery delta: zero-listener passive owners need explicit
    # activation state so unsubscribe can distinguish active from cached.
    (18, "ownerActive", "Ljava/util/Map;"),
    (18, "ownerNames", "Ljava/util/Map;"),
    (18, "recordedFailures", "Ljava/util/Map;"),
    (2, "failureEvidenceEnabled", "Z"),
}
ALLOWED_EVENTBUS_METHODS = {
    ("recordFailure", "(LAbyss/event/ListenerBinding;LAbyss/event/Event;Ljava/lang/Throwable;)V"),
    ("selfTest", "()Ljava/lang/String;"),
    ("isOwnerActive", "(Ljava/lang/Object;)Z"),
}
ALLOWED_CODE_LENGTHS = {
    # Reviewed passive-owner lifecycle fix proven by production-world
    # FullBright enable/subscribe/disable/unsubscribe runtime probe.
    (EVENTBUS, "<init>", "()V"): 98,
    (EVENTBUS, "s", "(Ljava/lang/Object;J)V"): 94,
    (EVENTBUS, "B", "(Ljava/lang/Object;)V"): 74,
    (EVENTBUS, "R", "(Ljava/lang/Object;Ljava/lang/Class;ILAbyss/event/EventInvoker;)V"): 240,
    (EVENTBUS, "e", "(LAbyss/event/Event;J)V"): 142,
    (EVENTBUS, "z", "(JLjava/lang/Object;)V"): 444,
    (LISTENER, "S", "(LAbyss/event/ListenerBinding;Z)Z"): 10,
}
ALLOWED_EXCEPTION_REGIONS = {
    (EVENTBUS, "R", "(Ljava/lang/Object;Ljava/lang/Class;ILAbyss/event/EventInvoker;)V"): 4,
    (EVENTBUS, "z", "(JLjava/lang/Object;)V"): 7,
}


def u1(data, off):
    return data[off], off + 1


def u2(data, off):
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data, off):
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

    def utf(idx):
        entry = cp[idx]
        return entry[1] if entry and entry[0] == "Utf8" else None

    def cls(idx):
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
            _attr_name, off = u2(data, off)
            length, off = u4(data, off)
            off += length

    method_count, off = u2(data, off)
    methods = {}
    for _ in range(method_count):
        access, off = u2(data, off)
        name_idx, off = u2(data, off)
        desc_idx, off = u2(data, off)
        attrs, off = u2(data, off)
        name = utf(name_idx)
        desc = utf(desc_idx)
        code_length = 0
        exceptions = 0
        max_stack = 0
        max_locals = 0
        for __ in range(attrs):
            attr_name_idx, off = u2(data, off)
            attr_len, off = u4(data, off)
            attr_name = utf(attr_name_idx)
            start = off
            if attr_name == "Code":
                max_stack, off = u2(data, off)
                max_locals, off = u2(data, off)
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
                raise ValueError(f"attribute parse drift {class_name}.{name}{desc}")
        if name != "<clinit>" and not name.startswith("lambda$"):
            methods[(name, desc)] = (
                access,
                code_length,
                exceptions,
                max_stack,
                max_locals,
            )

    return class_name, set(fields), methods


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
            classes[current] = {"fields": set(), "methods": {}}
        elif parts[0] == "FIELD":
            classes[current]["fields"].add(
                (int(parts[1]), parts[2], parts[3])
            )
        elif parts[0] == "METHOD":
            key = (parts[2], parts[3])
            classes[current]["methods"][key] = tuple(
                map(int, [parts[1]] + parts[4:8])
            )
        else:
            raise SystemExit("EVENT_CORE_REFERENCE_BAD_LINE=" + line)
    if set(classes) != {
        "Abyss/event/EventBus",
        "Abyss/event/Event",
        "Abyss/event/ListenerBinding",
    }:
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

    expected = load_reference(pathlib.Path(args.reference))
    actual = {}
    with zipfile.ZipFile(jar) as zf:
        for class_name in expected:
            entry = class_name + ".class"
            if entry not in zf.namelist():
                raise SystemExit("EVENT_CORE_CLASS_MISSING=" + class_name)
            name, fields, methods = parse_class(zf.read(entry))
            actual[name] = {"fields": fields, "methods": methods}

    failures = []
    allowed_deltas = 0

    for class_name in sorted(expected):
        want_fields = expected[class_name]["fields"]
        got_fields = actual[class_name]["fields"]

        if class_name == EVENTBUS:
            if not want_fields.issubset(got_fields):
                failures.append(
                    "eventbus-fields-missing:"
                    + repr(sorted(want_fields - got_fields))
                )
            extras = got_fields - want_fields
            if extras != ALLOWED_EVENTBUS_FIELDS:
                failures.append(
                    "eventbus-fields-extra:"
                    + repr(sorted(extras))
                )
        elif want_fields != got_fields:
            failures.append(
                "fields:"
                + class_name
                + ":expected="
                + repr(sorted(want_fields))
                + ":actual="
                + repr(sorted(got_fields))
            )

        want_methods = expected[class_name]["methods"]
        got_methods = actual[class_name]["methods"]
        want_keys = set(want_methods)
        got_keys = set(got_methods)

        if class_name == EVENTBUS:
            if not want_keys.issubset(got_keys):
                failures.append(
                    "eventbus-methods-missing:"
                    + repr(sorted(want_keys - got_keys))
                )
            extras = got_keys - want_keys
            if extras != ALLOWED_EVENTBUS_METHODS:
                failures.append(
                    "eventbus-methods-extra:"
                    + repr(sorted(extras))
                )
        elif want_keys != got_keys:
            failures.append(
                "method-api:"
                + class_name
                + ":missing="
                + repr(sorted(want_keys - got_keys))
                + ":extra="
                + repr(sorted(got_keys - want_keys))
            )

        for key in sorted(want_keys & got_keys):
            want_access, want_code, want_exc, want_stack, want_locals = want_methods[key]
            got_access, got_code, got_exc, got_stack, got_locals = got_methods[key]
            full = (class_name, key[0], key[1])

            if got_access != want_access:
                failures.append(f"access:{class_name}|{key}")
            expected_code = ALLOWED_CODE_LENGTHS.get(full, want_code)
            if got_code != expected_code:
                failures.append(
                    f"body:{class_name}|{key[0]}|{key[1]}:"
                    f"expected-current={expected_code}:actual={got_code}"
                )
            elif full in ALLOWED_CODE_LENGTHS:
                allowed_deltas += 1

            expected_exc = ALLOWED_EXCEPTION_REGIONS.get(full, want_exc)
            if got_exc != expected_exc:
                failures.append(
                    f"exceptions:{class_name}|{key[0]}|{key[1]}:"
                    f"expected-current={expected_exc}:actual={got_exc}"
                )

            # Stack/local changes are allowed only on reviewed body-delta methods.
            if full not in ALLOWED_CODE_LENGTHS:
                if got_stack != want_stack or got_locals != want_locals:
                    failures.append(
                        f"frame:{class_name}|{key[0]}|{key[1]}:"
                        f"expected={want_stack}/{want_locals}:"
                        f"actual={got_stack}/{got_locals}"
                    )

    print("EVENT_CORE_REFERENCE_CLASSES=3")
    print("EVENT_CORE_EVENTBUS_ALLOWED_FIELDS=4")
    print("EVENT_CORE_EVENTBUS_ALLOWED_METHODS=3")
    print(
        "EVENT_CORE_REVIEWED_BODY_DELTAS="
        + str(allowed_deltas)
        + "/"
        + str(len(ALLOWED_CODE_LENGTHS))
    )
    print(f"EVENT_CORE_FAILURES={len(failures)}")
    for failure in failures:
        print("EVENT_CORE_FAILURE=" + failure)

    if failures or allowed_deltas != len(ALLOWED_CODE_LENGTHS):
        print("EVENT_CORE_GATE=FAIL")
        return 1

    print("EVENT_CORE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
