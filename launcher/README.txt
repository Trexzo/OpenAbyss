OpenAbyss standalone Windows launcher

Requirements:
- Windows x64
- Java 8
- Minecraft 1.8.9 installed
- Forge 1.8.9 installed (11.15.1.2318 recommended)

Double-click Launcher.bat.

The launcher keeps its mods/configs under .\game and uses your existing
.minecraft libraries/assets. It extracts the required Windows native DLLs
into .\runtime-natives and captures stdout, stderr, launcher status and
crash reports beside the launcher.

This package uses offline/legacy launch arguments. It does not implement
Microsoft account authentication.
