@echo off
setlocal
cd /d "%~dp0"
title OpenAbyss Recovery [Forge 1.8.9]
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0launch_abyss.ps1" -LauncherDir "%~dp0" %*
set "RC=%ERRORLEVEL%"
echo.
if not "%RC%"=="0" (
  echo OpenAbyss launcher failed with exit %RC%.
  echo See launcher-latest.log, minecraft.stderr.log and crash-evidence.
) else (
  echo OpenAbyss closed normally.
)
pause
exit /b %RC%
