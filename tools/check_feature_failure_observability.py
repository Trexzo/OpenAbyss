#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys

root = pathlib.Path("src/main/java")
client = (root / "Abyss/AbyssClient.java").read_text(encoding="utf-8")

checks = {
    "feature_signature_set": "FEATURE_FAILURE_SIGNATURES" in client,
    "feature_recorder": "public static void recordFeatureFailure(" in client,
    "feature_dedupe": "if (!FEATURE_FAILURE_SIGNATURES.add(signature))" in client,
    "feature_file": 'new File("abyss-feature-failure.txt")' in client,
    "bed_scan_observable": 'recordFeatureFailure("BedNuker", "schedule-bed-scan"' in client,
}

targets = {
    "Abyss/module/impl/movement/NoSlow.java": [
        '"NoSlow", "blink-ownership-check"',
        '"NoSlow", "restore-use-key"',
        '"NoSlow", "release-use-key"',
        '"NoSlow", "killaura-ownership-check"',
    ],
    "Abyss/module/impl/world/FastPlace.java": [
        '"FastPlace", "pre-update-delay"',
        '"FastPlace", "post-right-click-delay"',
    ],
    "Abyss/module/impl/player/ChestStealer.java": [
        '"ChestStealer", "window-click"',
    ],
    "Abyss/module/impl/visual/HUD.java": [
        '"HUD", "render-2d"',
    ],
    "Abyss/module/impl/visual_utility/Indicators.java": [
        '"Indicators", "render-2d"',
    ],
    "Abyss/module/impl/visual_utility/MegaWallsDetector.java": [
        '"MegaWallsDetector", "scoreboard-health-lookup"',
    ],
    "Abyss/module/impl/visual_utility/TargetHUD.java": [
        '"TargetHUD", "persist-drag-position"',
    ],
}

empty = re.compile(
    r"catch\s*\([^)]*\)\s*\{\s*(?://\s*empty catch block)?\s*\}",
    re.MULTILINE,
)

for rel, markers in targets.items():
    text = (root / rel).read_text(encoding="utf-8")
    key = rel.rsplit("/", 1)[-1].replace(".java", "")
    checks[f"{key}_markers"] = all(marker in text for marker in markers)
    checks[f"{key}_no_empty_catch"] = empty.search(text) is None

marker_only_targets = {
    "Abyss/ASM/Hooks/Gui/GuiEventHooks.java": [
        '"GuiEventHooks", "alt-manager-init"',
        '"GuiEventHooks", "disconnected-init"',
    ],
    "Abyss/ASM/Hooks/MiscHooks.java": [
        '"MiscHooks", "main-menu-init"',
        '"MiscHooks", "main-menu-draw"',
        '"MiscHooks", "main-menu-post-init"',
        '"MiscHooks", "item-renderer-update"',
        '"MiscHooks", "orient-camera-cloud-fog"',
    ],
    "Abyss/ui/abyss/AbyssClickGuiScreen.java": [
        '"AbyssClickGuiScreen", "draw-screen"',
        '"AbyssClickGuiScreen", "mouse-click"',
        '"AbyssClickGuiScreen", "key-typed"',
        '"AbyssClickGuiScreen", "module-settings"',
        '"AbyssClickGuiScreen", "module-click"',
        '"AbyssClickGuiScreen", "setting-activate"',
        '"AbyssClickGuiScreen", "panel-module-enumeration"',
    ],
    "Abyss/ui/abyss/AbyssArrayListVisibility.java": [
        '"AbyssArrayListVisibility", "load"',
        '"AbyssArrayListVisibility", "save"',
        '"AbyssArrayListVisibility", "is-shown"',
        '"AbyssArrayListVisibility", "set-shown"',
    ],
    "Abyss/command/AbyssCommands.java": [
        '"AbyssCommands", "command-data-load"',
        '"AbyssCommands", "registry-note"',
        '"AbyssCommands", "alias-resolve"',
        '"AbyssCommands", "command-name-list"',
        '"AbyssCommands", "chat-output"',
    ],
    "Abyss/module/Module.java": [
        '"notification-disable"',
        '"notification-enable"',
        '"settings-scan"',
    ],
    "Abyss/command/impl/AbyssCommandConfig.java": [
        '"AbyssCommandConfig", "keybind-load"',
        '"AbyssCommandConfig", "module-metadata-apply"',
        '"AbyssCommandConfig", "setting-serialize"',
        '"AbyssCommandConfig", "setting-apply"',
    ],
    "Abyss/internal/restore/AbyssConfig.java": [
        '"AbyssConfig", "boot-snapshot"',
    ],
    "Abyss/util/BrowserLauncher.java": [
        '"BrowserLauncher", "clipboard-copy"',
        '"BrowserLauncher", "browser-open"',
    ],
    "Abyss/util/render/abyss/FontManager.java": [
        '"FontManager", "custom-font-setting"',
    ],
    "Abyss/util/SoundEngine.java": [
        '"SoundEngine", "resource-playback"',
    ],
    "Abyss/ui/abyss/AbyssUserInfoRenderer.java": [
        '"AbyssUserInfoRenderer", "setting-read"',
        '"AbyssUserInfoRenderer", "render"',
    ],
    "Abyss/util/ControllerEnvironmentImpl.java": [
        '"ControllerEnvironmentImpl", "plugin-directory-load"',
        '"ControllerEnvironmentImpl", "platform-plugin-load"',
    ],
}

for rel, markers in marker_only_targets.items():
    text = (root / rel).read_text(encoding="utf-8")
    key = rel.rsplit("/", 1)[-1].replace(".java", "")
    checks[f"{key}_feature_markers"] = all(marker in text for marker in markers)

for name, ok in checks.items():
    print(f"FEATURE_FAILURE_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"FEATURE_FAILURE_BAD={name}")
    print("FEATURE_FAILURE_GATE=FAIL")
    sys.exit(1)

print("FEATURE_FAILURE_GATE=PASS")
