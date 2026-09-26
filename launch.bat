@echo off
setlocal
cd /d "%~dp0"

echo OpenAbyss recovered client launcher
echo ----------------------------------
echo This launches the packaged recovery JAR through the verified Forge 1.8.9 runtime.
echo Runtime evidence is written to physical-smoke-evidence.
echo.

powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0tools\physical-smoke.ps1" -KeepOpen %*
set "RC=%ERRORLEVEL%"

echo.
if not "%RC%"=="0" (
    echo OPENABYSS_RUNTIME_FAILED exit=%RC%
    echo Check physical-smoke-evidence and physical-smoke-evidence\crash-evidence.
) else (
    echo OPENABYSS_RUNTIME_COMPLETED
)
echo.
pause
exit /b %RC%
