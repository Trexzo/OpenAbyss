#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile


ALLOWED_BODY_DELTAS = {
    ("Abyss/setting/settings/HeaderSetting", "prune", "(Ljava/util/List;)Ljava/util/List;"): 69,
    ("Abyss/setting/settings/HeaderSetting", "occupied", "(Ljava/util/List;I)Z"): 59,
    ("Abyss/setting/settings/ModeSetting", "<init>", "(Ljava/lang/String;ZLjava/lang/String;[Ljava/lang/String;)V"): 64,
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
        if not (access & 0x0008):
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
                raise ValueError(
                    f"attribute parse drift {class_name}.{name}{desc}"
                )
        if name != "<clinit>":
            methods[(name, desc)] = (
                access,
                code_length,
                exceptions,
                max_stack,
                max_locals,
            )

    return class_name, sorted(fields), methods


def load_reference(path: pathlib.Path):
    classes = {}
    current = None
    fields_total = 0
    methods_total = 0

    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        parts = line.split(" ")
        if parts[0] == "CLASS":
            current = parts[1]
            classes[current] = {"fields": [], "methods": {}}
        elif parts[0] == "FIELD":
            if current is None:
                raise SystemExit("SETTING_FRAMEWORK_REFERENCE_FIELD_WITHOUT_CLASS")
            access = int(parts[1])
            name = parts[2]
            desc = parts[3]
            classes[current]["fields"].append((access, name, desc))
            fields_total += 1
        elif parts[0] == "METHOD":
            if current is None:
                raise SystemExit("SETTING_FRAMEWORK_REFERENCE_METHOD_WITHOUT_CLASS")
            name = parts[1]
            desc = parts[2]
            metric = tuple(map(int, parts[3:8]))
            classes[current]["methods"][(name, desc)] = metric
            methods_total += 1
        else:
            raise SystemExit("SETTING_FRAMEWORK_REFERENCE_BAD_LINE=" + line)

    if len(classes) != 11:
        raise SystemExit(
            f"SETTING_FRAMEWORK_REFERENCE_CLASS_COUNT_BAD expected=11 actual={len(classes)}"
        )
    if fields_total != 13:
        raise SystemExit(
            f"SETTING_FRAMEWORK_REFERENCE_FIELD_COUNT_BAD expected=13 actual={fields_total}"
        )
    if methods_total != 55:
        raise SystemExit(
            f"SETTING_FRAMEWORK_REFERENCE_METHOD_COUNT_BAD expected=55 actual={methods_total}"
        )
    for item in classes.values():
        item["fields"].sort()
    return classes


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-setting-framework.txt",
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
                print("SETTING_FRAMEWORK_MISSING_CLASS=" + class_name)
                continue
            parsed_name, fields, methods = parse_class(zf.read(entry))
            if parsed_name != class_name:
                raise SystemExit(
                    f"SETTING_FRAMEWORK_CLASS_NAME_BAD expected={class_name} actual={parsed_name}"
                )
            actual[class_name] = {"fields": fields, "methods": methods}

    failures = []
    exact_bodies = 0
    allowed_body_deltas = 0

    missing_classes = sorted(set(expected) - set(actual))
    for class_name in missing_classes:
        failures.append("missing-class:" + class_name)

    for class_name in sorted(set(expected) & set(actual)):
        want_fields = expected[class_name]["fields"]
        got_fields = actual[class_name]["fields"]
        if want_fields != got_fields:
            failures.append(
                "instance-fields:"
                + class_name
                + ":expected="
                + repr(want_fields)
                + ":actual="
                + repr(got_fields)
            )

        want_methods = expected[class_name]["methods"]
        got_methods = actual[class_name]["methods"]
        if set(want_methods) != set(got_methods):
            missing = sorted(set(want_methods) - set(got_methods))
            extra = sorted(set(got_methods) - set(want_methods))
            failures.append(
                "method-api:"
                + class_name
                + ":missing="
                + repr(missing)
                + ":extra="
                + repr(extra)
            )
            continue

        for key in sorted(want_methods):
            want = want_methods[key]
            got = got_methods[key]
            want_access, want_code, want_exc, want_stack, want_locals = want
            got_access, got_code, got_exc, got_stack, got_locals = got

            if got_access != want_access:
                failures.append(
                    f"access:{class_name}|{key[0]}|{key[1]}:"
                    f"expected={want_access}:actual={got_access}"
                )
            if got_exc != want_exc:
                failures.append(
                    f"exception-regions:{class_name}|{key[0]}|{key[1]}:"
                    f"expected={want_exc}:actual={got_exc}"
                )
            if got_stack != want_stack or got_locals != want_locals:
                failures.append(
                    f"frame-shape:{class_name}|{key[0]}|{key[1]}:"
                    f"expected={want_stack}/{want_locals}:"
                    f"actual={got_stack}/{got_locals}"
                )

            full_key = (class_name, key[0], key[1])
            allowed_code = ALLOWED_BODY_DELTAS.get(full_key)
            if allowed_code is not None:
                if got_code != allowed_code:
                    failures.append(
                        f"reviewed-body-delta:{class_name}|{key[0]}|{key[1]}:"
                        f"expected-current={allowed_code}:actual={got_code}"
                    )
                else:
                    allowed_body_deltas += 1
            elif got_code != want_code:
                failures.append(
                    f"body-length:{class_name}|{key[0]}|{key[1]}:"
                    f"expected={want_code}:actual={got_code}"
                )
            else:
                exact_bodies += 1

    print("SETTING_FRAMEWORK_REFERENCE_CLASSES=11")
    print("SETTING_FRAMEWORK_REFERENCE_INSTANCE_FIELDS=13")
    print("SETTING_FRAMEWORK_REFERENCE_METHODS=55")
    print(f"SETTING_FRAMEWORK_CURRENT_CLASSES={len(actual)}")
    print(f"SETTING_FRAMEWORK_EXACT_BODIES={exact_bodies}")
    print(
        "SETTING_FRAMEWORK_REVIEWED_BODY_DELTAS="
        + str(allowed_body_deltas)
        + "/"
        + str(len(ALLOWED_BODY_DELTAS))
    )
    print(f"SETTING_FRAMEWORK_FAILURES={len(failures)}")

    for failure in failures:
        print("SETTING_FRAMEWORK_FAILURE=" + failure)

    if failures or allowed_body_deltas != len(ALLOWED_BODY_DELTAS):
        print("SETTING_FRAMEWORK_GATE=FAIL")
        return 1

    print("SETTING_FRAMEWORK_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
