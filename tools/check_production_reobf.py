#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile


REQUIRED = {
    "Abyss/ui/studio/StudioClickGuiScreen": {
        ("func_73863_a", "(IIF)V"),
        ("func_73866_w_", "()V"),
        ("func_73864_a", "(III)V"),
    },
    "Abyss/ui/screen/AccountManagerScreen": {
        ("func_73863_a", "(IIF)V"),
        ("func_73866_w_", "()V"),
        ("func_146284_a", "(Lnet/minecraft/client/gui/GuiButton;)V"),
    },
    "Abyss/ui/screen/DracuRiotMenuButton": {
        ("func_146112_a", "(Lnet/minecraft/client/Minecraft;II)V"),
    },
    "Abyss/util/SmoothMouseHelper": {
        ("func_74374_c", "()V"),
    },
    "Abyss/internal/auth/AccountListSlot": {
        ("func_148127_b", "()I"),
        ("func_180791_a", "(IIIIII)V"),
    },
}

FORBIDDEN = {
    "Abyss/ui/studio/StudioClickGuiScreen": {
        "drawScreen", "initGui", "mouseClicked",
    },
    "Abyss/ui/screen/AccountManagerScreen": {
        "drawScreen", "initGui", "actionPerformed",
    },
    "Abyss/ui/screen/DracuRiotMenuButton": {"drawButton"},
    "Abyss/util/SmoothMouseHelper": {"mouseXYChange"},
    "Abyss/internal/auth/AccountListSlot": {
        "getSize", "drawSlot",
    },
}


def u1(data, off):
    return data[off], off + 1


def u2(data, off):
    return struct.unpack_from(">H", data, off)[0], off + 2


def u4(data, off):
    return struct.unpack_from(">I", data, off)[0], off + 4


def methods(data: bytes) -> set[tuple[str, str]]:
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
            length, off = u2(data, off)
            cp[i] = ("Utf8", data[off:off + length].decode("utf-8", "replace"))
            off += length
        elif tag in (3, 4):
            off += 4
        elif tag in (5, 6):
            off += 8
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            off += 2
        elif tag in (9, 10, 11, 12, 17, 18):
            off += 4
        elif tag == 15:
            off += 3
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(index):
        entry = cp[index]
        return entry[1] if entry and entry[0] == "Utf8" else None

    _access, off = u2(data, off)
    _this, off = u2(data, off)
    _super, off = u2(data, off)

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

    result: set[tuple[str, str]] = set()
    method_count, off = u2(data, off)
    for _ in range(method_count):
        _access, off = u2(data, off)
        name_index, off = u2(data, off)
        desc_index, off = u2(data, off)
        attrs, off = u2(data, off)
        result.add((utf(name_index), utf(desc_index)))
        for __ in range(attrs):
            _name, off = u2(data, off)
            length, off = u4(data, off)
            off += length

    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    failures: list[str] = []

    with zipfile.ZipFile(jar) as zf:
        for class_name, required in REQUIRED.items():
            entry = class_name + ".class"
            if entry not in zf.namelist():
                failures.append("class-missing:" + class_name)
                continue

            actual = methods(zf.read(entry))
            for signature in sorted(required):
                if signature not in actual:
                    failures.append(
                        "srg-method-missing:"
                        + class_name
                        + "|"
                        + signature[0]
                        + "|"
                        + signature[1]
                    )

            actual_names = {name for name, _desc in actual}
            for name in sorted(FORBIDDEN.get(class_name, set())):
                if name in actual_names:
                    failures.append(
                        "mcp-method-still-present:" + class_name + "|" + name
                    )

    print("PRODUCTION_REOBF_CLASSES=" + str(len(REQUIRED)))
    print(
        "PRODUCTION_REOBF_REQUIRED_METHODS="
        + str(sum(len(x) for x in REQUIRED.values()))
    )
    print(f"PRODUCTION_REOBF_FAILURES={len(failures)}")
    for failure in failures:
        print("PRODUCTION_REOBF_BAD=" + failure)

    if failures:
        print("PRODUCTION_REOBF_GATE=FAIL")
        return 1

    print("PRODUCTION_REOBF_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
