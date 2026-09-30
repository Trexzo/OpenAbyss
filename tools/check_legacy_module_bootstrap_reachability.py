#!/usr/bin/env python3
from __future__ import annotations

import argparse
import pathlib
import struct
import sys
import zipfile

STOCK = "Abyss/internal/jnic/StockClientBootstrap"
MODULE = "Abyss/module/Module"
LEGACY = {
    (
        MODULE,
        "Q",
        "(Ljava/lang/String;JBLjava/lang/Boolean;LAbyss/module/Category;Ljava/lang/Boolean;Ljava/lang/String;[LAbyss/setting/Setting;)LAbyss/module/Module;",
    ),
    (
        MODULE,
        "g",
        "(Ljava/lang/String;CLjava/lang/Boolean;LAbyss/module/Category;Ljava/lang/Boolean;JLjava/lang/String;ZZ[LAbyss/setting/Setting;)LAbyss/module/Module;",
    ),
}
STOCK_METHODS = {"W", "Z"}


def refs(data: bytes):
    off = 8
    cp_count = struct.unpack_from(">H", data, off)[0]
    off += 2
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag = data[off]
        off += 1
        if tag == 1:
            n = struct.unpack_from(">H", data, off)[0]
            off += 2
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
            idx = struct.unpack_from(">H", data, off)[0]
            off += 2
            cp[i] = (tag, idx)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, b = struct.unpack_from(">HH", data, off)
            off += 4
            cp[i] = (tag, a, b)
        elif tag == 15:
            off += 3
            cp[i] = (tag,)
        else:
            raise ValueError(f"unsupported cp tag {tag}")
        i += 1

    def utf(idx):
        e = cp[idx]
        return e[1] if e and e[0] == "Utf8" else None

    def cls(idx):
        e = cp[idx]
        return utf(e[1]) if e and e[0] == 7 else None

    out = []
    for e in cp:
        if not e or e[0] not in (10, 11):
            continue
        owner = cls(e[1])
        nt = cp[e[2]]
        if not nt or nt[0] != 12:
            continue
        out.append((owner, utf(nt[1]), utf(nt[2])))
    return out


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    stock_callers = []
    legacy_callers = []

    with zipfile.ZipFile(jar) as zf:
        for entry in zf.namelist():
            if not entry.endswith(".class"):
                continue
            caller = entry[:-6]
            for ref in refs(zf.read(entry)):
                owner, name, desc = ref
                if owner == STOCK and name in STOCK_METHODS:
                    stock_callers.append((caller, name, desc))
                if ref in LEGACY and caller != MODULE:
                    legacy_callers.append((caller, name, desc))

    print(f"LEGACY_STOCK_BOOTSTRAP_REFERENCES={len(stock_callers)}")
    for caller, name, desc in stock_callers:
        print(f"LEGACY_STOCK_BOOTSTRAP_CALLER={caller}|{name}|{desc}")

    print(f"LEGACY_MODULE_DECLARATION_EXTERNAL_CALLERS={len(legacy_callers)}")
    for caller, name, desc in legacy_callers:
        print(f"LEGACY_MODULE_DECLARATION_CALLER={caller}|{name}|{desc}")

    failures = []
    if any(caller != MODULE for caller, _name, _desc in stock_callers):
        failures.append("stock-bootstrap-referenced-outside-Module")
    stock_names = {name for caller, name, _desc in stock_callers if caller == MODULE}
    if not {"W", "Z"}.issubset(stock_names):
        failures.append("expected-dormant-legacy-stock-calls-changed")
    if legacy_callers:
        failures.append("legacy-module-declaration-reachable")

    print(f"LEGACY_MODULE_BOOTSTRAP_FAILURES={len(failures)}")
    for failure in failures:
        print("LEGACY_MODULE_BOOTSTRAP_FAILURE=" + failure)

    if failures:
        print("LEGACY_MODULE_BOOTSTRAP_GATE=FAIL")
        return 1

    print("LEGACY_MODULE_BOOTSTRAP_GATE=PASS dormant=true")
    return 0


if __name__ == "__main__":
    sys.exit(main())
