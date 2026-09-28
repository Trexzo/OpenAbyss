#!/usr/bin/env python3
from __future__ import annotations

from pathlib import Path
import sys

ROOT=Path(__file__).resolve().parents[1]
client=(ROOT/"src/main/java/Abyss/AbyssClient.java").read_text(encoding="utf-8")
boot=(ROOT/"src/main/java/Abyss/internal/restore/AbyssBootstrap.java").read_text(encoding="utf-8")
harness=(ROOT/"tools/run_production_persistence_smoke.sh").read_text(encoding="utf-8")

sentinel="OPENABYSS_PROMOTED_PERSIST_7E51"

required_client=[
    'AntiNick.suffix.O("' + sentinel + '")',
    'Modules.J(AntiNick.class)',
    'AntiNick did not enter enabled state before save',
    'promotedModule=true,promotedText=' + sentinel,
]
required_boot=[
    'ModuleManager.byName("AntiNick")',
    'AntiNick.suffix == null ? null : AntiNick.suffix.X()',
    '"' + sentinel + '".equals(antiNickSuffix)',
    'promotedModule=true,promotedText=' + sentinel,
]
required_harness=[
    "PERSISTENCE_CONFIG_ANTINICK_BLOCK_MISSING",
    "PERSISTENCE_CONFIG_ANTINICK_STATUS_BAD",
    "PERSISTENCE_CONFIG_ANTINICK_SUFFIX_BAD",
    sentinel,
    "PERSISTENCE_PROMOTED_ANTINICK_RESTART=PASS",
]

for label,text,tokens in (
    ("client",client,required_client),
    ("bootstrap",boot,required_boot),
    ("harness",harness,required_harness),
):
    missing=[t for t in tokens if t not in text]
    if missing:
        raise SystemExit(f"PROMOTED_PERSISTENCE_AUTHORITY=FAIL {label} missing={missing}")

if client.count(sentinel) < 2:
    raise SystemExit("PROMOTED_PERSISTENCE_AUTHORITY=FAIL client sentinel underrepresented")
if boot.count(sentinel) < 3:
    raise SystemExit("PROMOTED_PERSISTENCE_AUTHORITY=FAIL bootstrap sentinel underrepresented")
if harness.count(sentinel) < 4:
    raise SystemExit("PROMOTED_PERSISTENCE_AUTHORITY=FAIL harness sentinel underrepresented")

print("PROMOTED_PERSISTENCE_AUTHORITY=PASS module=AntiNick status=true suffix="+sentinel)
