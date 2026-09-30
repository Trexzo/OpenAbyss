OpenAbyss Windows package

This ZIP supports two launch paths.

1. RECOMMENDED FOR NORMAL / AUTHENTICATED USE
   Run:
       Prepare-Official-Launcher.bat

   It creates:
       .\official-game\mods\abyss.jar

   and prints the exact Game Directory path.

   In the official Minecraft Launcher:
   - create or select a Forge 1.8.9 installation
   - edit that installation
   - set Game Directory to the printed official-game path
   - launch normally

   Microsoft/Mojang authentication remains entirely with Minecraft Launcher.
   The OpenAbyss package never handles your account password.

2. DIRECT STANDALONE / DIAGNOSTIC USE
   Run:
       Launcher.bat

   Requirements:
   - Windows x64
   - Java 8
   - Minecraft 1.8.9 installed
   - Forge 1.8.9 installed (11.15.1.2318 recommended)

   This path launches with a local legacy/offline username and is intended
   primarily for runtime diagnosis and offline/local testing. It does not
   implement Microsoft account authentication.

Diagnostic files from the direct launcher:
- launcher-latest.log
- launcher-result.txt
- minecraft.stdout.log
- minecraft.stderr.log
- crash-evidence\
- game\logs\latest.log

launcher-result.txt includes the last recovered bootstrap/runtime stage and
the last module lifecycle failure, when those diagnostics were produced.

The package uses isolated game/config directories beside the launcher so it
does not need to overwrite your normal Minecraft configuration.
