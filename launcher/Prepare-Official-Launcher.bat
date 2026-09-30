@echo off
setlocal
cd /d "%~dp0"
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0Prepare-Official-Launcher.ps1" -PackageDir "%~dp0"
set "RC=%ERRORLEVEL%"
echo.
pause
exit /b %RC%
