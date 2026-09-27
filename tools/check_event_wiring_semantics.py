#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import re
import subprocess
import struct
import sys
import zipfile


CLASS_RE = re.compile(r"public final class ([\w.$]+)")
EVENTBUS_CALL = "Abyss/event/EventBus.R:"
INVOKER_CLASS_RE = re.compile(
    r"^new\s+#\d+\s+// class (Abyss/event/invoker/[\w$]+)"
)
LDC_CLASS_RE = re.compile(
    r"^ldc(?:_w)?\s+#\d+\s+// class ([\w/$]+)"
)
CHECKCAST_RE = re.compile(
    r"checkcast\s+#\d+\s+// class ([\w/$]+)"
)
METHOD_RE = re.compile(
    r"// (?:Method|InterfaceMethod) "
    r"([\w/$]+)\.\"?([\w$<>]+)\"?:(\S+)"
)
LONG_RE = re.compile(r"// long (-?\d+)l")


def class_names(jar: pathlib.Path, prefix: str) -> list[str]:
    with zipfile.ZipFile(jar) as zf:
        return [
            entry[:-6].replace("/", ".")
            for entry in sorted(zf.namelist())
            if entry.startswith(prefix)
            and entry.endswith(".class")
            and "$" not in entry
        ]


def javap(jar: pathlib.Path, names: list[str]) -> str:
    command = [
        "javap",
        "-classpath",
        str(jar),
        "-p",
        "-c",
        "-s",
        *names,
    ]
    return subprocess.check_output(
        command,
        text=True,
        stderr=subprocess.DEVNULL,
    )


def method_sections(text: str):
    result: dict[str, dict[str, list[str]]] = {}
    current_class = None
    current_method = None
    code: list[str] = []
    in_code = False

    for raw in text.splitlines():
        line = raw.strip()

        match = CLASS_RE.match(line)
        if match:
            if current_class and current_method:
                result.setdefault(current_class, {})[current_method] = code
            current_class = match.group(1).replace(".", "/")
            current_method = None
            code = []
            in_code = False
            continue

        if current_class is None:
            continue

        if line.endswith(";") and "(" in line:
            if current_method:
                result.setdefault(current_class, {})[current_method] = code
            current_method = line.split("(", 1)[0].split()[-1].split(".")[-1]
            code = []
            in_code = False
            continue

        if current_method and line == "Code:":
            in_code = True
            continue

        if current_method and in_code and re.match(r"^\d+:", line):
            code.append(re.sub(r"^\d+:\s*", "", line))

    if current_class and current_method:
        result.setdefault(current_class, {})[current_method] = code

    return result


def pushed_int(line: str):
    opcode = line.split()[0]
    if opcode.startswith("iconst_"):
        value = opcode[len("iconst_"):]
        return -1 if value == "m1" else int(value)
    if opcode in ("bipush", "sipush"):
        return int(line.split()[1])

    match = re.search(r"// int (-?\d+)", line)
    return int(match.group(1)) if match else None


def binder_rows(jar: pathlib.Path):
    names = class_names(jar, "Abyss/event/binder/")
    sections = method_sections(javap(jar, names))
    rows = []

    for class_name in sorted(sections):
        for method_name, code in sections[class_name].items():
            for index, line in enumerate(code):
                if EVENTBUS_CALL not in line:
                    continue

                window = code[max(0, index - 12):index]
                invoker = None
                new_index = None
                for pos, previous in enumerate(window):
                    match = INVOKER_CLASS_RE.search(previous)
                    if match:
                        invoker = match.group(1)
                        new_index = pos

                if invoker is None or new_index is None:
                    raise RuntimeError(
                        f"binder invoker parse failure: {class_name}.{method_name}"
                    )

                priority = None
                priority_index = None
                for pos in range(new_index - 1, -1, -1):
                    value = pushed_int(window[pos])
                    if value is not None:
                        priority = value
                        priority_index = pos
                        break

                if priority is None or priority_index is None:
                    raise RuntimeError(
                        f"binder priority parse failure: {class_name}.{method_name}"
                    )

                event_class = None
                for pos in range(priority_index - 1, -1, -1):
                    match = LDC_CLASS_RE.search(window[pos])
                    if match:
                        event_class = match.group(1)
                        break

                if event_class is None:
                    raise RuntimeError(
                        f"binder event parse failure: {class_name}.{method_name}"
                    )

                rows.append(
                    (class_name, event_class, priority, invoker)
                )

    return sorted(rows)


def _u1(data: bytes, off: int):
    return data[off], off + 1


def _u2(data: bytes, off: int):
    return struct.unpack_from(">H", data, off)[0], off + 2


def _u4(data: bytes, off: int):
    return struct.unpack_from(">I", data, off)[0], off + 4


_FIXED_OPCODE_LENGTH = [1] * 256
for _op in (
    0x10, 0x12, 0x15, 0x16, 0x17, 0x18, 0x19,
    0x36, 0x37, 0x38, 0x39, 0x3A, 0xA9, 0xBC,
):
    _FIXED_OPCODE_LENGTH[_op] = 2
for _op in (
    0x11, 0x13, 0x14, 0x84,
    *range(0x99, 0xA9),
    0xC6, 0xC7,
    0xB2, 0xB3, 0xB4, 0xB5,
    0xB6, 0xB7, 0xB8,
    0xBB, 0xBD, 0xC0, 0xC1,
):
    _FIXED_OPCODE_LENGTH[_op] = 3
_FIXED_OPCODE_LENGTH[0xC5] = 4
_FIXED_OPCODE_LENGTH[0xB9] = 5
_FIXED_OPCODE_LENGTH[0xBA] = 5
_FIXED_OPCODE_LENGTH[0xC8] = 5
_FIXED_OPCODE_LENGTH[0xC9] = 5
_FIXED_OPCODE_LENGTH[0xAA] = -1
_FIXED_OPCODE_LENGTH[0xAB] = -1
_FIXED_OPCODE_LENGTH[0xC4] = -1


def _instructions(code: bytes):
    pc = 0
    while pc < len(code):
        opcode = code[pc]
        yield pc, opcode
        length = _FIXED_OPCODE_LENGTH[opcode]
        if length > 0:
            pc += length
            continue

        if opcode == 0xAA:  # tableswitch
            pos = pc + 1
            while pos % 4:
                pos += 1
            _default, low, high = struct.unpack_from(">iii", code, pos)
            pc = pos + 12 + 4 * (high - low + 1)
            continue

        if opcode == 0xAB:  # lookupswitch
            pos = pc + 1
            while pos % 4:
                pos += 1
            _default, pairs = struct.unpack_from(">ii", code, pos)
            pc = pos + 8 + 8 * pairs
            continue

        if opcode == 0xC4:  # wide
            nested = code[pc + 1]
            pc += 6 if nested == 0x84 else 4
            continue

        raise ValueError(f"unsupported variable-length opcode {opcode:#x}")


def _parse_invoker_class(data: bytes):
    off = 0
    magic, off = _u4(data, off)
    if magic != 0xCAFEBABE:
        raise ValueError("bad class magic")
    _minor, off = _u2(data, off)
    _major, off = _u2(data, off)

    cp_count, off = _u2(data, off)
    cp = [None] * cp_count
    index = 1
    while index < cp_count:
        tag, off = _u1(data, off)
        if tag == 1:
            length, off = _u2(data, off)
            cp[index] = (
                "Utf8",
                data[off:off + length].decode("utf-8", "replace"),
            )
            off += length
        elif tag == 3:
            value = struct.unpack_from(">i", data, off)[0]
            off += 4
            cp[index] = ("Int", value)
        elif tag == 4:
            off += 4
            cp[index] = ("Float",)
        elif tag == 5:
            value = struct.unpack_from(">q", data, off)[0]
            off += 8
            cp[index] = ("Long", value)
            index += 1
        elif tag == 6:
            off += 8
            cp[index] = ("Double",)
            index += 1
        elif tag in (7, 8, 16, 19, 20):
            ref, off = _u2(data, off)
            cp[index] = (tag, ref)
        elif tag in (9, 10, 11, 12, 17, 18):
            first, off = _u2(data, off)
            second, off = _u2(data, off)
            cp[index] = (tag, first, second)
        elif tag == 15:
            kind, off = _u1(data, off)
            ref, off = _u2(data, off)
            cp[index] = (tag, kind, ref)
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        index += 1

    def utf(ref):
        entry = cp[ref]
        return entry[1] if entry and entry[0] == "Utf8" else None

    def cls(ref):
        entry = cp[ref]
        return utf(entry[1]) if entry and entry[0] == 7 else None

    def name_type(ref):
        entry = cp[ref]
        if not entry or entry[0] != 12:
            return None, None
        return utf(entry[1]), utf(entry[2])

    def method_ref(ref):
        entry = cp[ref]
        if not entry or entry[0] not in (10, 11):
            return None
        name, desc = name_type(entry[2])
        return cls(entry[1]), name, desc

    _access, off = _u2(data, off)
    this_ref, off = _u2(data, off)
    _super_ref, off = _u2(data, off)
    class_name = cls(this_ref)

    interface_count, off = _u2(data, off)
    off += 2 * interface_count

    field_count, off = _u2(data, off)
    for _ in range(field_count):
        off += 6
        attr_count, off = _u2(data, off)
        for __ in range(attr_count):
            _attr_name, off = _u2(data, off)
            attr_len, off = _u4(data, off)
            off += attr_len

    callback_code = None
    method_count, off = _u2(data, off)
    for _ in range(method_count):
        _method_access, off = _u2(data, off)
        name_ref, off = _u2(data, off)
        desc_ref, off = _u2(data, off)
        attr_count, off = _u2(data, off)
        name = utf(name_ref)
        _desc = utf(desc_ref)

        code = None
        for __ in range(attr_count):
            attr_name_ref, off = _u2(data, off)
            attr_len, off = _u4(data, off)
            attr_name = utf(attr_name_ref)
            attr_start = off
            if attr_name == "Code":
                _max_stack, pos = _u2(data, off)
                _max_locals, pos = _u2(data, pos)
                code_len, pos = _u4(data, pos)
                code = data[pos:pos + code_len]
            off = attr_start + attr_len

        if name == "c":
            if callback_code is not None:
                raise RuntimeError(
                    f"multiple invoker c methods: {class_name}"
                )
            callback_code = code

    if callback_code is None:
        raise RuntimeError(f"missing invoker callback: {class_name}")

    event_class = None
    targets = []
    constants = []

    for pc, opcode in _instructions(callback_code):
        if opcode == 0xC0:  # checkcast
            ref = struct.unpack_from(">H", callback_code, pc + 1)[0]
            cast = cls(ref)
            if cast and (
                cast.startswith("Abyss/event/events/")
                or cast.startswith("java/awt/event/")
            ):
                event_class = cast

        elif opcode in (0xB6, 0xB7, 0xB8, 0xB9):
            ref = struct.unpack_from(">H", callback_code, pc + 1)[0]
            target = method_ref(ref)
            if target is not None:
                owner, name, desc = target
                if (
                    name != "<init>"
                    and not owner.startswith("java/lang/Object")
                ):
                    targets.append((owner, name, desc))

        elif opcode == 0x14:  # ldc2_w
            ref = struct.unpack_from(">H", callback_code, pc + 1)[0]
            entry = cp[ref]
            if entry and entry[0] == "Long":
                constants.append(entry[1])

    if not targets:
        raise RuntimeError(
            f"invoker callback target parse failure: {class_name}"
        )

    return (
        class_name,
        event_class,
        tuple(targets),
        tuple(constants),
    )


def invoker_rows(jar: pathlib.Path):
    rows = []
    with zipfile.ZipFile(jar) as zf:
        for entry in sorted(zf.namelist()):
            if (
                not entry.startswith("Abyss/event/invoker/")
                or not entry.endswith(".class")
                or "$" in entry
            ):
                continue
            rows.append(_parse_invoker_class(zf.read(entry)))
    return sorted(rows)


def aggregate(rows) -> str:
    payload = "".join(repr(row) + "\n" for row in rows).encode("utf-8")
    return hashlib.sha256(payload).hexdigest()


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        kind, count_s, sha256 = line.split()
        expected[kind] = (int(count_s), sha256.lower())

    if set(expected) != {"BINDER_REG", "INVOKER_CALL"}:
        raise SystemExit("EVENT_WIRING_SEMANTICS_REFERENCE_KIND_SET_BAD")
    return expected


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-event-wiring-semantics.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    expected = load_reference(pathlib.Path(args.reference))
    binder = binder_rows(jar)
    invoker = invoker_rows(jar)

    actual = {
        "BINDER_REG": (len(binder), aggregate(binder)),
        "INVOKER_CALL": (len(invoker), aggregate(invoker)),
    }

    failures = []
    for kind in ("BINDER_REG", "INVOKER_CALL"):
        print(f"EVENT_WIRING_SEMANTICS_{kind}_COUNT={actual[kind][0]}")
        print(f"EVENT_WIRING_SEMANTICS_{kind}_SHA256={actual[kind][1]}")
        if actual[kind] != expected[kind]:
            failures.append(
                f"{kind}:expected={expected[kind]}:actual={actual[kind]}"
            )

    print(f"EVENT_WIRING_SEMANTICS_FAILURES={len(failures)}")
    for failure in failures:
        print("EVENT_WIRING_SEMANTICS_BAD=" + failure)

    if failures:
        print("EVENT_WIRING_SEMANTICS_GATE=FAIL")
        return 1

    print("EVENT_WIRING_SEMANTICS_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
