# OpenAbyss

A deobfuscated and decrypted Forge 1.8.9 client source project.

## Recovery branch

Active recovery work lives on `recovery/r38-compile-pass`. The branch is intentionally kept separate from `main` while runtime recovery is still being validated.

Current recovery contracts cover the full 112-module registry, config persistence, commands, ClickGUI publication/selection, Minecraft session swapping, Alt Manager menu injection/reconnect initialization, required internal subscribers, and ASM transforms.

## Toolchain

- Java: JDK 8
- Gradle: 2.14.1
- Minecraft: 1.8.9
- Forge: 11.15.1.2318
- mappings: stable_22

## Build

With Java 8 active:

```powershell
gradle --no-daemon clean build
```

The compiled JAR is written to `build/libs/`.

## Windows launch

From a checked-out copy of the recovery branch, double-click `launch.bat` or run:

```powershell
.\launch.bat
```

The launcher uses the packaged OpenAbyss JAR runtime, keeps Minecraft open for interactive testing, and writes runtime/crash evidence under `physical-smoke-evidence/`. A non-zero Minecraft/OpenAbyss exit is treated as a failed runtime rather than a successful bootstrap.

## Physical-PC smoke test

The recovery branch includes a Windows PowerShell harness that reproduces the hosted recovery checks on a real GPU and collects the evidence needed before the recovery PR can be considered runnable on physical hardware.

From the repository root, for an automated bootstrap evidence window:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\tools\physical-smoke.ps1
```

For an actual interactive real-GPU test, keep Minecraft open until you close it normally:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\tools\physical-smoke.ps1 -KeepOpen
```

The harness auto-detects common local JDK 8 installations (or accepts `-Jdk8` explicitly), obtains Gradle 2.14.1 when needed, runs `clean build` and `getAssets`, clears GitHub runtime-token variables plus `ABYSS_PAYLOAD_KEY`, launches Forge with `--offline runClient`, records the branch HEAD and built JAR SHA-256, and captures Minecraft/OpenAbyss bootstrap, census, injection, graphics, and session evidence.

It requires the recovered module/config/EventBus/session/command/ClickGUI/Alt Manager contracts to pass. Evidence is written to `physical-smoke-evidence/`; a successful bootstrap creates `PASS.txt` containing `PHYSICAL_SMOKE_BOOTSTRAP_PASS`.

The harness proves bootstrap/runtime integrity, not every gameplay module or online service. Keep the recovery PR draft until the physical GPU/GUI smoke evidence is clean.
