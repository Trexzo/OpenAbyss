#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re


ROOT = pathlib.Path(__file__).resolve().parents[1]
REGISTRY_ORDER = ROOT / "tools/reference-module-registry-order.txt"
WORLD_CI = ROOT / ".github/workflows/runtime-production-world-linux-smoke.yml"
MODULE_ROOT = ROOT / "src/main/java/Abyss/module/impl"


def registered_names() -> list[str]:
    names = [
        line.strip()
        for line in REGISTRY_ORDER.read_text(encoding="utf-8").splitlines()
        if line.strip() and not line.lstrip().startswith("#")
    ]
    if len(names) != 112 or len(set(names)) != 112:
        raise SystemExit(
            f"REGISTRY_ORDER_AUTHORITY_MISMATCH count={len(names)} unique={len(set(names))}"
        )
    return names


def module_sources() -> dict[str, pathlib.Path]:
    result: dict[str, pathlib.Path] = {}
    for path in MODULE_ROOT.rglob("*.java"):
        text = path.read_text(encoding="utf-8", errors="replace")
        match = re.search(r"public\s+class\s+(\w+)\s+extends\s+Module\b", text)
        if match:
            result[match.group(1)] = path
    return result


def main() -> int:
    registered = registered_names()
    ci = WORLD_CI.read_text(encoding="utf-8")
    sources = module_sources()

    proven: list[str] = []
    explicit_disabled: list[str] = []
    unproven: list[str] = []

    loop_proven: set[str] = set()
    for match in re.finditer(r"for module in ([^;\n]+); do(?P<body>.*?)done", ci, re.S):
        body = match.group("body")
        if "module-pass:$module" not in body and "module-pass:${module}" not in body:
            continue
        loop_proven.update(match.group(1).split())

    contract_proven: set[str] = set()
    for match in re.finditer(r"PASS modules=([A-Za-z0-9_,.-]+)", ci):
        contract_proven.update(
            item for item in match.group(1).split(",")
            if item and not item.isdigit()
        )

    marker_proven: set[str] = set()
    for line in ci.splitlines():
        if "grep -Fq" not in line or "pass:" not in line:
            continue
        for name in registered:
            if f"pass:{name}:" in line or f"pass:{name}'" in line:
                marker_proven.add(name)

    subsystem_contracts = {
        "ClickGUI": (
            "clickgui-mode-probe-pass:3:restored=",
            "clickgui-open-success:",
        ),
        "InvMove": (
            "invmove-physical-probe-pass:1",
            "invmove-physical-probe-effect-pass:forwardBinding=true:",
        ),
    }
    subsystem_proven = {
        name
        for name, markers in subsystem_contracts.items()
        if all(marker in ci for marker in markers)
    }

    for name in registered:
        if (
            f"module-pass:{name}" in ci
            or name in contract_proven
            or name in marker_proven
            or name in subsystem_proven
            or name in loop_proven
        ):
            proven.append(name)
            continue

        source = sources.get(name)
        if source:
            text = source.read_text(encoding="utf-8", errors="replace")
            if "This module is currently disabled" in text:
                explicit_disabled.append(name)
                continue
        unproven.append(name)

    print(f"REGISTERED_UNIQUE={len(registered)}")
    print(f"PRODUCTION_FUNCTIONAL_EVIDENCE={len(proven)}")
    print(f"EXPLICIT_DISABLED_SHELLS={len(explicit_disabled)}")
    print(f"UNPROVEN_LIVE_OR_UNCLASSIFIED={len(unproven)}")
    print("PROVEN=" + ",".join(proven))
    print("EXPLICIT_DISABLED=" + ",".join(explicit_disabled))
    print("UNPROVEN=" + ",".join(unproven))

    if len(registered) != 112:
        print("WARNING=registry parser did not recover 112 unique names")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
