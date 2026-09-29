#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re


ROOT = pathlib.Path(__file__).resolve().parents[1]
REGISTRY = ROOT / "src/main/java/Abyss/internal/restore/AbyssModuleRegistry.java"
WORLD_CI = ROOT / ".github/workflows/runtime-production-world-linux-smoke.yml"
MODULE_ROOT = ROOT / "src/main/java/Abyss/module/impl"


def registered_names() -> list[str]:
    text = REGISTRY.read_text(encoding="utf-8")
    names = re.findall(r',\s*[A-Za-z0-9_]+\.class,\s*"([A-Za-z0-9]+)"', text)
    # Preserve registry order while removing repeats from comments/helper paths.
    out: list[str] = []
    seen: set[str] = set()
    for name in names:
        if name not in seen:
            seen.add(name)
            out.append(name)
    return out


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

    for name in registered:
        if (
            f"module-pass:{name}" in ci
            or f"modules={name}" in ci
            or f":{name}:" in ci and "probe" in ci
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
