#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile
from dataclasses import dataclass


@dataclass(frozen=True)
class MemberRef:
    tag: int
    owner: str
    name: str
    desc: str


def u1(data: bytes, off: int) -> tuple[int, int]:
    return data[off], off + 1


def u2(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">I", data, off)[0], off + 4


def member_refs(data: bytes) -> set[MemberRef]:
    off = 0
    magic, off = u4(data, off)
    if magic != 0xCAFEBABE:
        raise ValueError("bad class magic")
    _minor, off = u2(data, off)
    _major, off = u2(data, off)

    cp_count, off = u2(data, off)
    cp: list[object | None] = [None] * cp_count
    i = 1
    while i < cp_count:
        tag, off = u1(data, off)
        if tag == 1:
            length, off = u2(data, off)
            cp[i] = ("Utf8", data[off:off + length].decode("utf-8", "replace"))
            off += length
        elif tag in (3, 4):
            cp[i] = ("Number",)
            off += 4
        elif tag in (5, 6):
            cp[i] = ("WideNumber",)
            off += 8
            i += 1
        elif tag == 7:
            name_index, off = u2(data, off)
            cp[i] = ("Class", name_index)
        elif tag == 8:
            string_index, off = u2(data, off)
            cp[i] = ("String", string_index)
        elif tag in (9, 10, 11):
            class_index, off = u2(data, off)
            nt_index, off = u2(data, off)
            cp[i] = ("Ref", tag, class_index, nt_index)
        elif tag == 12:
            name_index, off = u2(data, off)
            desc_index, off = u2(data, off)
            cp[i] = ("NameAndType", name_index, desc_index)
        elif tag == 15:
            _kind, off = u1(data, off)
            ref_index, off = u2(data, off)
            cp[i] = ("MethodHandle", ref_index)
        elif tag == 16:
            desc_index, off = u2(data, off)
            cp[i] = ("MethodType", desc_index)
        elif tag in (17, 18):
            bootstrap_index, off = u2(data, off)
            nt_index, off = u2(data, off)
            cp[i] = ("Dynamic", tag, bootstrap_index, nt_index)
        elif tag in (19, 20):
            name_index, off = u2(data, off)
            cp[i] = ("ModulePackage", tag, name_index)
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(index: int) -> str | None:
        entry = cp[index]
        if isinstance(entry, tuple) and entry and entry[0] == "Utf8":
            return str(entry[1])
        return None

    def class_name(index: int) -> str | None:
        entry = cp[index]
        if not (isinstance(entry, tuple) and entry and entry[0] == "Class"):
            return None
        return utf(int(entry[1]))

    result: set[MemberRef] = set()
    for entry in cp:
        if not (isinstance(entry, tuple) and entry and entry[0] == "Ref"):
            continue
        _kind, tag, class_index, nt_index = entry
        owner = class_name(int(class_index))
        nt = cp[int(nt_index)]
        if not (
            owner
            and isinstance(nt, tuple)
            and nt
            and nt[0] == "NameAndType"
        ):
            continue
        name = utf(int(nt[1]))
        desc = utf(int(nt[2]))
        if name is None or desc is None:
            continue
        result.add(MemberRef(int(tag), owner, name, desc))
    return result


def parse_mappings(
    text: str,
) -> tuple[
    dict[tuple[str, str], tuple[str, str]],
    dict[tuple[str, str, str], tuple[str, str, str]],
]:
    fields: dict[tuple[str, str], tuple[str, str]] = {}
    methods: dict[tuple[str, str, str], tuple[str, str, str]] = {}

    for raw in text.splitlines():
        line = raw.strip()
        if line.startswith("FD: "):
            parts = line.split()
            if len(parts) != 3:
                continue
            left, right = parts[1:]
            left_owner, left_name = left.rsplit("/", 1)
            right_owner, right_name = right.rsplit("/", 1)
            if (left_owner, left_name) != (right_owner, right_name):
                fields[(left_owner, left_name)] = (right_owner, right_name)

        elif line.startswith("MD: "):
            parts = line.split()
            if len(parts) != 5:
                continue
            left_path, left_desc, right_path, right_desc = parts[1:]
            left_owner, left_name = left_path.rsplit("/", 1)
            right_owner, right_name = right_path.rsplit("/", 1)
            if (left_owner, left_name, left_desc) != (
                right_owner,
                right_name,
                right_desc,
            ):
                methods[(left_owner, left_name, left_desc)] = (
                    right_owner,
                    right_name,
                    right_desc,
                )

    return fields, methods


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--mapping-entry",
        default="assets/abyss/asm/mcp-srg.srg",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    failures: list[str] = []
    mc_field_refs = 0
    mc_method_refs = 0
    mapped_field_refs = 0
    mapped_method_refs = 0
    scanned_classes = 0

    with zipfile.ZipFile(jar) as zf:
        names = set(zf.namelist())
        if args.mapping_entry not in names:
            raise SystemExit(
                f"PRODUCTION_MEMBER_REFS_GATE=FAIL mapping-missing:{args.mapping_entry}"
            )

        fields, methods = parse_mappings(
            zf.read(args.mapping_entry).decode("utf-8", "replace")
        )

        for entry in sorted(names):
            if not entry.endswith(".class"):
                continue
            class_name = entry[:-6]
            if not (
                class_name.startswith("Abyss/")
                or class_name.startswith("loader_forgemod/")
            ):
                continue
            scanned_classes += 1

            for ref in member_refs(zf.read(entry)):
                if not ref.owner.startswith("net/minecraft/"):
                    continue

                if ref.tag == 9:
                    mc_field_refs += 1
                    target = fields.get((ref.owner, ref.name))
                    if target is None:
                        continue
                    mapped_field_refs += 1
                    failures.append(
                        "mcp-field-ref-still-present:"
                        + class_name
                        + "|"
                        + ref.owner
                        + "|"
                        + ref.name
                        + "|expected="
                        + target[0]
                        + "/"
                        + target[1]
                    )

                elif ref.tag in (10, 11):
                    mc_method_refs += 1
                    target = methods.get((ref.owner, ref.name, ref.desc))
                    if target is None:
                        continue
                    mapped_method_refs += 1
                    failures.append(
                        "mcp-method-ref-still-present:"
                        + class_name
                        + "|"
                        + ref.owner
                        + "|"
                        + ref.name
                        + "|"
                        + ref.desc
                        + "|expected="
                        + target[0]
                        + "/"
                        + target[1]
                        + "|"
                        + target[2]
                    )

    print(f"PRODUCTION_MEMBER_REFS_CLASSES={scanned_classes}")
    print(f"PRODUCTION_MEMBER_REFS_MC_FIELDS={mc_field_refs}")
    print(f"PRODUCTION_MEMBER_REFS_MC_METHODS={mc_method_refs}")
    print(f"PRODUCTION_MEMBER_REFS_MCP_FIELDS_REMAINING={mapped_field_refs}")
    print(f"PRODUCTION_MEMBER_REFS_MCP_METHODS_REMAINING={mapped_method_refs}")
    print(f"PRODUCTION_MEMBER_REFS_FAILURES={len(failures)}")

    for failure in failures[:100]:
        print("PRODUCTION_MEMBER_REFS_BAD=" + failure)

    if failures:
        print("PRODUCTION_MEMBER_REFS_GATE=FAIL")
        return 1

    print("PRODUCTION_MEMBER_REFS_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
