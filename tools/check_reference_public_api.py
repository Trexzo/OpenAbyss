#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import struct
import sys
import zipfile

PUB = 0x0001
PROT = 0x0004
STATIC = 0x0008
FINAL = 0x0010
SYNC = 0x0020
VOLATILE_OR_BRIDGE = 0x0040
TRANSIENT_OR_VARARGS = 0x0080
NATIVE = 0x0100
SYNTHETIC = 0x1000

FIELD_MASK = PUB | PROT | STATIC | FINAL | VOLATILE_OR_BRIDGE | TRANSIENT_OR_VARARGS | SYNTHETIC
METHOD_MASK = PUB | PROT | STATIC | FINAL | SYNC | VOLATILE_OR_BRIDGE | TRANSIENT_OR_VARARGS | NATIVE | SYNTHETIC
EXPECTED_DIGEST = "FA389078936371C906F26D7C2EF7A220F7CA28A40181735EEA2B6CBA6FAF1669"
EXPECTED_BASELINE_MEMBERS = 9180


def parse_class(data: bytes):
    off = 0

    def u1():
        nonlocal off
        value = data[off]
        off += 1
        return value

    def u2():
        nonlocal off
        value = struct.unpack_from(">H", data, off)[0]
        off += 2
        return value

    def u4():
        nonlocal off
        value = struct.unpack_from(">I", data, off)[0]
        off += 4
        return value

    if u4() != 0xCAFEBABE:
        raise ValueError("invalid class magic")
    u2()
    u2()
    cp_count = u2()
    cp = [None] * cp_count
    index = 1
    while index < cp_count:
        tag = u1()
        if tag == 1:
            length = u2()
            value = data[off : off + length].decode("utf-8", "replace")
            off += length
            cp[index] = ("utf8", value)
        elif tag in (3, 4):
            off += 4
            cp[index] = (tag,)
        elif tag in (5, 6):
            off += 8
            cp[index] = (tag,)
            index += 1
        elif tag in (7, 8, 16, 19, 20):
            cp[index] = (tag, u2())
        elif tag in (9, 10, 11, 12, 17, 18):
            cp[index] = (tag, u2(), u2())
        elif tag == 15:
            cp[index] = (tag, u1(), u2())
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        index += 1

    def utf(cp_index: int) -> str:
        entry = cp[cp_index]
        if not entry or entry[0] != "utf8":
            raise ValueError(f"constant-pool item {cp_index} is not UTF8")
        return entry[1]

    def class_name(cp_index: int) -> str:
        entry = cp[cp_index]
        if not entry or entry[0] != 7:
            raise ValueError(f"constant-pool item {cp_index} is not Class")
        return utf(entry[1])

    u2()
    this_class = class_name(u2())
    u2()
    interface_count = u2()
    for _ in range(interface_count):
        u2()

    def members(kind: str):
        nonlocal off
        result = []
        count = u2()
        for _ in range(count):
            access = u2()
            name = utf(u2())
            desc = utf(u2())
            attributes = u2()
            for _ in range(attributes):
                u2()
                length = u4()
                off += length
            if access & (PUB | PROT):
                mask = FIELD_MASK if kind == "F" else METHOD_MASK
                result.append((kind, this_class, name, desc, access & mask))
        return result

    fields = members("F")
    methods = members("M")
    return fields + methods


def load_authority(path: pathlib.Path):
    missing = set()
    extras = {}
    access = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        parts = line.split("|")
        mode = parts[0]
        if mode == "MISSING":
            _, kind, cls, name, desc = parts
            missing.add((kind, cls, name, desc))
        elif mode == "EXTRA":
            _, kind, cls, name, desc, flags = parts
            extras[(kind, cls, name, desc)] = int(flags, 16)
        elif mode == "ACCESS":
            _, kind, cls, name, desc, old_flags, current_flags = parts
            access[(kind, cls, name, desc)] = (int(old_flags, 16), int(current_flags, 16))
        else:
            raise SystemExit(f"UNKNOWN_AUTHORITY_MODE={mode}")
    if len(missing) != 3:
        raise SystemExit(f"API_AUTHORITY_MISSING_COUNT expected=3 actual={len(missing)}")
    if len(extras) != 57:
        raise SystemExit(f"API_AUTHORITY_EXTRA_COUNT expected=57 actual={len(extras)}")
    if len(access) != 5:
        raise SystemExit(f"API_AUTHORITY_ACCESS_COUNT expected=5 actual={len(access)}")
    return missing, extras, access


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--authority",
        default="tools/reference-public-api-authority.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    authority = pathlib.Path(args.authority)
    missing, expected_extras, access_exceptions = load_authority(authority)

    members = {}
    with zipfile.ZipFile(jar) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            for kind, cls, member, desc, flags in parse_class(zf.read(name)):
                members[(kind, cls, member, desc)] = flags

    actual_extras = {
        signature: flags
        for signature, flags in members.items()
        if signature in expected_extras
    }
    absent_extras = sorted(set(expected_extras) - set(actual_extras))
    bad_extra_flags = sorted(
        (sig, expected_extras[sig], actual_extras[sig])
        for sig in actual_extras
        if expected_extras[sig] != actual_extras[sig]
    )
    if absent_extras or bad_extra_flags:
        for sig in absent_extras:
            print("API_EXPECTED_EXTRA_MISSING=" + "|".join(sig))
        for sig, want, got in bad_extra_flags:
            print(f"API_EXTRA_ACCESS_BAD={'|'.join(sig)} expected={want:04x} actual={got:04x}")
        raise SystemExit("RUNNABLE_ERA_PUBLIC_API_GATE=FAIL reviewed extras")

    canonical = []
    current_nonextra = {
        signature: flags
        for signature, flags in members.items()
        if signature not in expected_extras
    }

    for signature, flags in current_nonextra.items():
        if signature in access_exceptions:
            old_flags, expected_current = access_exceptions[signature]
            if flags != expected_current:
                print(
                    f"API_ACCESS_EXCEPTION_BAD={'|'.join(signature)} "
                    f"expected_current={expected_current:04x} actual={flags:04x}"
                )
                raise SystemExit("RUNNABLE_ERA_PUBLIC_API_GATE=FAIL access exception")
            flags = old_flags
        canonical.append("|".join(signature) + f"|{flags:04x}")

    canonical.sort()
    digest = hashlib.sha256("\n".join(canonical).encode("utf-8")).hexdigest().upper()

    print(f"API_CURRENT_PUBLIC_PROTECTED_MEMBERS={len(members)}")
    print(f"API_REVIEWED_EXTRAS={len(expected_extras)}")
    print(f"API_REVIEWED_MISSING={len(missing)}")
    print(f"API_REVIEWED_ACCESS_DRIFTS={len(access_exceptions)}")
    print(f"API_CANONICAL_BASELINE_MEMBERS={len(canonical)}")
    print(f"API_CANONICAL_DIGEST={digest}")

    if len(canonical) != EXPECTED_BASELINE_MEMBERS:
        raise SystemExit(
            f"RUNNABLE_ERA_PUBLIC_API_GATE=FAIL member_count "
            f"expected={EXPECTED_BASELINE_MEMBERS} actual={len(canonical)}"
        )
    if digest != EXPECTED_DIGEST:
        raise SystemExit(
            f"RUNNABLE_ERA_PUBLIC_API_GATE=FAIL digest "
            f"expected={EXPECTED_DIGEST} actual={digest}"
        )

    print("RUNNABLE_ERA_PUBLIC_API_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
