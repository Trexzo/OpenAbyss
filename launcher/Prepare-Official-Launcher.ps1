param(
    [string]$PackageDir = (Split-Path -Parent $MyInvocation.MyCommand.Path)
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$PackageDir = [IO.Path]::GetFullPath($PackageDir)
$Jar = Join-Path $PackageDir 'abyss.jar'
$GameDir = Join-Path $PackageDir 'official-game'
$ModsDir = Join-Path $GameDir 'mods'
$Dest = Join-Path $ModsDir 'abyss.jar'
$Info = Join-Path $PackageDir 'official-launcher-setup.txt'

if (-not (Test-Path -LiteralPath $Jar -PathType Leaf)) {
    throw "abyss.jar is missing beside the installer: $Jar"
}

New-Item -ItemType Directory -Force -Path $ModsDir | Out-Null

Remove-Item -LiteralPath (Join-Path $GameDir 'crash-reports') -Recurse -Force -ErrorAction SilentlyContinue

foreach ($evidenceName in @(
    'abyss-bootstrap-stage.txt',
    'abyss-runtime-stage.txt',
    'abyss-module-failure.txt',
    'abyss-bootstrap-diagnostics.txt',
    'abyss-census.tsv'
)) {
    Remove-Item -LiteralPath (Join-Path $GameDir $evidenceName) -Force -ErrorAction SilentlyContinue
}

$SessionId = [Guid]::NewGuid().ToString('N')
$SessionStart = (Get-Date).ToUniversalTime().ToString('o')
@(
    "SESSION_ID=$SessionId"
    "SESSION_START_UTC=$SessionStart"
) | Set-Content -LiteralPath (Join-Path $GameDir 'openabyss-test-session.txt') -Encoding UTF8

Copy-Item -LiteralPath $Jar -Destination $Dest -Force

$hash = (Get-FileHash -LiteralPath $Dest -Algorithm SHA256).Hash
$BuildInfo = Join-Path $PackageDir 'BUILD-INFO.txt'
if (Test-Path -LiteralPath $BuildInfo -PathType Leaf) {
    $map = @{}
    foreach ($line in @(Get-Content -LiteralPath $BuildInfo)) {
        if ($line -match '^([^=]+)=(.*)$') {
            $map[$matches[1]] = $matches[2]
        }
    }
    if ($map.ContainsKey('JAR_SHA256') -and $map['JAR_SHA256'] -ne $hash) {
        throw 'BUILD-INFO.txt JAR hash does not match abyss.jar.'
    }
    if ($map.ContainsKey('JAR_BYTES') -and [int64]$map['JAR_BYTES'] -ne (Get-Item -LiteralPath $Dest).Length) {
        throw 'BUILD-INFO.txt JAR size does not match abyss.jar.'
    }
}
@(
    'OPENABYSS_OFFICIAL_LAUNCHER_SETUP=READY'
    "GAME_DIRECTORY=$GameDir"
    "MOD_JAR=$Dest"
    "MOD_JAR_SHA256=$hash"
    "SESSION_ID=$SessionId"
    "SESSION_START_UTC=$SessionStart"
    ''
    'Minecraft Launcher steps:'
    '1. Install/create a Minecraft Java Edition Forge 1.8.9 installation.'
    '2. Edit that installation.'
    "3. Set Game Directory to: $GameDir"
    '4. Launch that Forge 1.8.9 installation normally through the official launcher.'
    ''
    'Authentication remains entirely with the official Minecraft Launcher.'
) | Set-Content -LiteralPath $Info -Encoding UTF8

Write-Host ''
Write-Host 'OpenAbyss official-launcher game directory is ready.' -ForegroundColor Green
Write-Host "Game Directory: $GameDir"
Write-Host "Installed JAR : $Dest"
Write-Host "SHA-256       : $hash"
Write-Host ''
Write-Host 'Use that Game Directory on a Forge 1.8.9 installation in Minecraft Launcher.'
Write-Host "Setup record: $Info"
