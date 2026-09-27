#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import re
import struct
import sys
import zipfile
from collections import Counter


# Namespaces supplied by Java 8, Minecraft 1.8.9, Forge 1.8.9,
# LaunchWrapper, and the libraries in the stock 1.8.9 client profile.
RUNTIME_PREFIXES = (
    "java/",
    "javax/",
    "sun/",
    "com/sun/",
    "net/minecraft/",
    "net/minecraftforge/",
    "net/java/games/input/",
    "org/lwjgl/",
    "org/apache/logging/log4j/",
    "org/apache/commons/",
    "org/apache/http/",
    "org/objectweb/asm/",
    "com/google/common/",
    "com/google/gson/",
    "com/mojang/",
    "io/netty/",
    "joptsimple/",
    "scala/",
)

DESCRIPTOR_CLASS = re.compile(r"L([^;<]+)")


def u1(data: bytes, off: int) -> tuple[int, int]:
    return data[off], off + 1


def u2(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data: bytes, off: int) -> tuple[int, int]:
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_class_dependencies(data: bytes) -> set[str]:
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

    deps: set[str] = set()

    # CONSTANT_Class references cover bytecode instructions, catch types,
    # superclasses/interfaces and many verifier-visible symbolic refs.
    for entry in cp:
        if not (isinstance(entry, tuple) and entry and entry[0] == "Class"):
            continue
        name = utf(int(entry[1]))
        if not name:
            continue
        if name.startswith("["):
            for match in DESCRIPTOR_CLASS.finditer(name):
                deps.add(match.group(1))
        else:
            deps.add(name)

    # Parse declared field/method descriptors as well. Descriptor Utf8 entries
    # can reference a type without requiring a standalone CONSTANT_Class entry.
    for entry in cp:
        if not (isinstance(entry, tuple) and entry and entry[0] == "Utf8"):
            continue
        value = str(entry[1])
        if not ("L" in value and ";" in value):
            continue
        # Restrict this to descriptor/signature-looking strings, avoiding
        # arbitrary text resources and encrypted constants.
        if not (
            value.startswith("(")
            or value.startswith("L")
            or value.startswith("[")
            or "<" in value
        ):
            continue
        for match in DESCRIPTOR_CLASS.finditer(value):
            deps.add(match.group(1))

    return deps


def namespace(name: str) -> str:
    parts = name.split("/")
    if len(parts) <= 1:
        return "<default>"
    if len(parts) == 2:
        return parts[0] + "/" + parts[1]
    return "/".join(parts[:3])


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    with zipfile.ZipFile(jar) as zf:
        class_entries = {
            name[:-6]
            for name in zf.namelist()
            if name.endswith(".class")
        }

        all_deps: set[str] = set()
        scanned = 0
        for entry in zf.namelist():
            if not entry.endswith(".class"):
                continue
            class_name = entry[:-6]
            if not (
                class_name.startswith("Abyss/")
                or class_name.startswith("loader_forgemod/")
                or class_name.startswith("com/formdev/flatlaf/")
            ):
                continue
            scanned += 1
            all_deps.update(parse_class_dependencies(zf.read(entry)))

    unresolved = sorted(
        dep
        for dep in all_deps
        if dep not in class_entries
        and not dep.startswith(RUNTIME_PREFIXES)
        and not dep.startswith("[")
    )

    external = sorted(
        dep
        for dep in all_deps
        if dep not in class_entries
    )
    ns = Counter(namespace(dep) for dep in external)

    print(f"DEPENDENCY_CLOSURE_CLASSES_SCANNED={scanned}")
    print(f"DEPENDENCY_CLOSURE_REFERENCED_CLASSES={len(all_deps)}")
    print(f"DEPENDENCY_CLOSURE_BUNDLED_CLASSES={len(class_entries)}")
    print(f"DEPENDENCY_CLOSURE_EXTERNAL_REFS={len(external)}")
    print(f"DEPENDENCY_CLOSURE_UNKNOWN_REFS={len(unresolved)}")

    for name, count in sorted(ns.items()):
        print(f"DEPENDENCY_CLOSURE_EXTERNAL_NAMESPACE={name}|refs={count}")

    for dep in unresolved[:200]:
        print("DEPENDENCY_CLOSURE_UNKNOWN=" + dep)

    if unresolved:
        print("DEPENDENCY_CLOSURE_GATE=FAIL")
        return 1

    print("DEPENDENCY_CLOSURE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
