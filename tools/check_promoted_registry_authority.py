#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

ROOT = pathlib.Path(__file__).resolve().parents[1]
AUTHORITY = ROOT / "tools" / "promoted-registry-authority.txt"
REGISTRY = ROOT / "src" / "main" / "java" / "Abyss" / "internal" / "restore" / "AbyssModuleRegistry.java"
IMPL = ROOT / "src" / "main" / "java" / "Abyss" / "module" / "impl"

CATEGORY_DIRS = {
    "CustomCape":"configuration","Font":"configuration","Gadgets":"configuration","Language":"configuration","Theme":"configuration",
    "InputFix":"misc","NoObfuscation":"misc","RawInput":"misc","VisualSpoof":"configuration","CaveXray":"visual","ItemScale":"visual",
    "AntiNick":"misc","ContainerKeeper":"misc","BindGUI":"visual","KeyStrokes":"visual","TeamInvisible":"visual",
    "ClosestPlayerHUD":"visual_utility","FKCounter":"visual_utility","FallIndicator":"visual_utility","LeapModeHUD":"visual_utility",
}

rows=[]
for raw in AUTHORITY.read_text(encoding="utf-8").splitlines():
    line=raw.strip()
    if not line or line.startswith("#"):
        continue
    stage, event_backed, name=line.split()
    rows.append((int(stage), event_backed=="true", name))

if len(rows)!=20:
    raise SystemExit(f"PROMOTED_REGISTRY_AUTHORITY=FAIL count={len(rows)}")

counts={}
for stage, _, _ in rows:
    counts[stage]=counts.get(stage,0)+1
if counts!={97:5,103:6,112:9}:
    raise SystemExit(f"PROMOTED_REGISTRY_STAGE_COUNTS=FAIL {counts}")

if sum(1 for _,e,_ in rows if e)!=9:
    raise SystemExit("PROMOTED_REGISTRY_BINDER_SPLIT=FAIL event_backed")
if sum(1 for _,e,_ in rows if not e)!=11:
    raise SystemExit("PROMOTED_REGISTRY_BINDER_SPLIT=FAIL binderless")

registry=REGISTRY.read_text(encoding="utf-8")
for stage,event_backed,name in rows:
    if f'"{name}"' not in registry:
        raise SystemExit(f"PROMOTED_REGISTRY_REGISTRY_MISSING={name}")
    path=IMPL / CATEGORY_DIRS[name] / f"{name}.java"
    if not path.is_file():
        raise SystemExit(f"PROMOTED_REGISTRY_SOURCE_MISSING={name}:{path}")
    text=path.read_text(encoding="utf-8")
    actual_event=bool(re.search(r"implements\s+EventSubscriber", text) and re.search(r"void\s+x\s*\(\s*long[^)]*EventBus", text))
    if actual_event!=event_backed:
        raise SystemExit(f"PROMOTED_REGISTRY_BINDER_MISMATCH={name}:expected={event_backed}:actual={actual_event}")

# Verify the cumulative retirement blocks correspond to the staged authority.
stage112={name for stage,_,name in rows if stage==112}
stage103={name for stage,_,name in rows if stage==103}
stage97={name for stage,_,name in rows if stage==97}
for threshold,names in ((103,stage112),(97,stage103),(92,stage97)):
    marker=f"if (registryTarget <= {threshold})"
    start=registry.find(marker)
    if start<0:
        raise SystemExit(f"PROMOTED_REGISTRY_THRESHOLD_MISSING={threshold}")
    end=registry.find("}", start)
    block=registry[start:end]
    missing=sorted(n for n in names if f'"{n}"' not in block)
    if missing:
        raise SystemExit(f"PROMOTED_REGISTRY_THRESHOLD_BAD={threshold}:missing={missing}")

print("PROMOTED_REGISTRY_AUTHORITY=PASS total=20 stages=5/6/9 binderless=11 event_backed=9")
