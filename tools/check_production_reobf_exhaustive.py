#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile
from collections import defaultdict


def u1(data: bytes, off: int) -> tuple[int, int]:
    return data[off], off + 1


def u2(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_class(data: bytes) -> tuple[str | None, set[tuple[str, str]]]:
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
            off += 4
        elif tag in (5, 6):
            off += 8
            i += 1
        elif tag == 7:
            name_index, off = u2(data, off)
            cp[i] = ("Class", name_index)
        elif tag in (8, 16, 19, 20):
            off += 2
        elif tag in (9, 10, 11, 12, 17, 18):
            off += 4
        elif tag == 15:
            off += 3
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(index: int) -> str | None:
        entry = cp[index]
        if isinstance(entry, tuple) and entry and entry[0] == "Utf8":
            return str(entry[1])
        return None

    def class_name(index: int) -> str | None:
        if index == 0:
            return None
        entry = cp[index]
        if not (isinstance(entry, tuple) and entry and entry[0] == "Class"):
            return None
        return utf(int(entry[1]))

    _access, off = u2(data, off)
    _this, off = u2(data, off)
    super_index, off = u2(data, off)
    super_name = class_name(super_index)

    interfaces, off = u2(data, off)
    off += interfaces * 2

    field_count, off = u2(data, off)
    for _ in range(field_count):
        off += 6
        attrs, off = u2(data, off)
        for __ in range(attrs):
            _name, off = u2(data, off)
            length, off = u4(data, off)
            off += length

    methods: set[tuple[str, str]] = set()
    method_count, off = u2(data, off)
    for _ in range(method_count):
        _access, off = u2(data, off)
        name_index, off = u2(data, off)
        desc_index, off = u2(data, off)
        attrs, off = u2(data, off)
        name = utf(name_index)
        desc = utf(desc_index)
        if name is not None and desc is not None:
            methods.add((name, desc))
        for __ in range(attrs):
            _name, off = u2(data, off)
            length, off = u4(data, off)
            off += length

    return super_name, methods


def parse_mcp_to_srg(text: str) -> dict[str, dict[tuple[str, str], str]]:
    mappings: dict[str, dict[tuple[str, str], str]] = defaultdict(dict)
    conflicts: list[str] = []

    for raw in text.splitlines():
        line = raw.strip()
        if not line.startswith("MD: "):
            continue
        parts = line.split()
        if len(parts) != 5:
            continue

        left_path, left_desc, right_path, right_desc = parts[1:]
        left_owner, left_name = left_path.rsplit("/", 1)
        _right_owner, right_name = right_path.rsplit("/", 1)

        if left_desc != right_desc:
            # 1.8.9 MCP/SRG descriptors should remain structurally compatible
            # for the method-name reobfuscation check. Ignore exceptional lines.
            continue
        if left_name == right_name:
            continue

        key = (left_name, left_desc)
        previous = mappings[left_owner].get(key)
        if previous is not None and previous != right_name:
            conflicts.append(
                f"{left_owner}|{left_name}|{left_desc}|{previous}|{right_name}"
            )
        mappings[left_owner][key] = right_name

    if conflicts:
        raise ValueError(
            "mapping conflicts: " + "; ".join(conflicts[:10])
        )
    return mappings


def external_minecraft_root(
    class_name: str,
    supers: dict[str, str | None],
) -> str | None:
    seen: set[str] = set()
    current = class_name

    while current in supers:
        if current in seen:
            return None
        seen.add(current)
        parent = supers[current]
        if parent is None:
            return None
        if parent.startswith("net/minecraft/"):
            return parent
        if parent.startswith("Abyss/") or parent.startswith("loader_forgemod/"):
            current = parent
            continue
        return None

    return None


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

    supers: dict[str, str | None] = {}
    class_methods: dict[str, set[tuple[str, str]]] = {}

    with zipfile.ZipFile(jar) as zf:
        names = set(zf.namelist())
        if args.mapping_entry not in names:
            raise SystemExit(
                f"PRODUCTION_REOBF_EXHAUSTIVE_GATE=FAIL mapping-missing:{args.mapping_entry}"
            )

        mappings = parse_mcp_to_srg(
            zf.read(args.mapping_entry).decode("utf-8", "replace")
        )

        for entry in names:
            if not entry.endswith(".class"):
                continue
            class_name = entry[:-6]
            if not (
                class_name.startswith("Abyss/")
                or class_name.startswith("loader_forgemod/")
            ):
                continue
            super_name, methods = parse_class(zf.read(entry))
            supers[class_name] = super_name
            class_methods[class_name] = methods

    descendants: dict[str, str] = {}
    failures: list[str] = []
    checked_mcp_signatures = 0
    checked_classes = 0

    for class_name, methods in sorted(class_methods.items()):
        root = external_minecraft_root(class_name, supers)
        if root is None:
            continue
        checked_classes += 1
        descendants[class_name] = root

        owner_map = mappings.get(root, {})
        for name, desc in methods:
            target = owner_map.get((name, desc))
            if target is None:
                continue
            checked_mcp_signatures += 1
            failures.append(
                "mcp-override-still-present:"
                + class_name
                + "|super="
                + root
                + "|"
                + name
                + "|"
                + desc
                + "|expected="
                + target
            )

    print(f"PRODUCTION_REOBF_HIERARCHY_CLASSES={checked_classes}")
    print(f"PRODUCTION_REOBF_MCP_OVERRIDE_REMAINDERS={checked_mcp_signatures}")
    print(f"PRODUCTION_REOBF_HIERARCHY_ROOTS={len(set(descendants.values()))}")
    for root in sorted(set(descendants.values())):
        count = sum(1 for value in descendants.values() if value == root)
        print(f"PRODUCTION_REOBF_ROOT={root}|classes={count}")

    for failure in failures[:100]:
        print("PRODUCTION_REOBF_EXHAUSTIVE_BAD=" + failure)

    if failures:
        print("PRODUCTION_REOBF_EXHAUSTIVE_GATE=FAIL")
        return 1

    print("PRODUCTION_REOBF_EXHAUSTIVE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
