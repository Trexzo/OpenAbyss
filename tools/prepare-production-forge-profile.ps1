param(
    [Parameter(Mandatory=$true)]
    [string]$MinecraftDir,
    [string]$ForgeVersion = '1.8.9-11.15.1.2318-1.8.9',
    [switch]$DownloadAssets
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$MinecraftDir = [IO.Path]::GetFullPath($MinecraftDir)
$Libraries = Join-Path $MinecraftDir 'libraries'
$Versions = Join-Path $MinecraftDir 'versions'
$Assets = Join-Path $MinecraftDir 'assets'
$Temp = Join-Path $MinecraftDir '.openabyss-bootstrap'

foreach ($dir in @($MinecraftDir,$Libraries,$Versions,$Assets,$Temp)) {
    New-Item -ItemType Directory -Force -Path $dir | Out-Null
}

function Download-File([string]$Url, [string]$Destination) {
    if (Test-Path -LiteralPath $Destination -PathType Leaf) {
        return
    }
    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $Destination) | Out-Null
    & curl.exe -L --fail --silent --show-error --retry 3 --retry-delay 2 $Url -o $Destination
    if ($LASTEXITCODE -ne 0) {
        throw "Download failed: $Url"
    }
    if (-not (Test-Path -LiteralPath $Destination -PathType Leaf)) {
        throw "Download did not create: $Destination"
    }
}

function Maven-Parts([string]$Coordinate) {
    $coord = $Coordinate
    $extension = 'jar'
    if ($coord.Contains('@')) {
        $at = $coord.Split('@')
        $coord = $at[0]
        $extension = $at[1]
    }
    $parts = $coord.Split(':')
    if ($parts.Length -lt 3) {
        throw "Invalid Maven coordinate: $Coordinate"
    }
    $group = $parts[0]
    $artifact = $parts[1]
    $version = $parts[2]
    $classifier = if ($parts.Length -ge 4 -and $parts[3]) { $parts[3] } else { $null }
    $file = $artifact + '-' + $version + $(if ($classifier) { '-' + $classifier } else { '' }) + '.' + $extension
    $relative = ($group.Replace('.', '/') + '/' + $artifact + '/' + $version + '/' + $file)
    return @{
        Relative = $relative
        File = $file
    }
}

function Download-Maven([string]$Coordinate, [string]$BaseUrl) {
    $parts = Maven-Parts $Coordinate
    $relative = [string]$parts.Relative
    $destination = Join-Path $Libraries $relative.Replace('/', '\')
    $base = if ($BaseUrl) { $BaseUrl } else { 'https://libraries.minecraft.net/' }
    if (-not $base.EndsWith('/')) { $base += '/' }
    $base = $base.Replace('http://files.minecraftforge.net/maven/', 'https://maven.minecraftforge.net/')
    $base = $base.Replace('http://maven.minecraftforge.net/', 'https://maven.minecraftforge.net/')
    Download-File ($base + $relative) $destination
    return $destination
}

function Rule-AllowsWindows([object]$Library) {
    $rulesProp = $Library.PSObject.Properties['rules']
    if ($null -eq $rulesProp -or $null -eq $rulesProp.Value) {
        return $true
    }
    $allowed = $false
    foreach ($rule in @($rulesProp.Value)) {
        $matches = $true
        $osProp = $rule.PSObject.Properties['os']
        if ($osProp -and $osProp.Value) {
            $nameProp = $osProp.Value.PSObject.Properties['name']
            if ($nameProp -and $nameProp.Value -and [string]$nameProp.Value -ne 'windows') {
                $matches = $false
            }
            $archProp = $osProp.Value.PSObject.Properties['arch']
            if ($archProp -and $archProp.Value -and [string]$archProp.Value -notmatch 'x86_64|amd64|64') {
                $matches = $false
            }
        }
        if ($matches) {
            $allowed = ([string]$rule.action -eq 'allow')
        }
    }
    return $allowed
}

Write-Host '=== OpenAbyss production Forge profile preparation ==='
Write-Host "MinecraftDir=$MinecraftDir"
Write-Host "ForgeVersion=$ForgeVersion"

# Mojang 1.8.9 metadata and client.
$ManifestUrl = 'https://piston-meta.mojang.com/mc/game/version_manifest_v2.json'
$manifest = Invoke-RestMethod -Uri $ManifestUrl
$baseMeta = @($manifest.versions | Where-Object { $_.id -eq '1.8.9' }) | Select-Object -First 1
if (-not $baseMeta) { throw 'Minecraft 1.8.9 metadata not found.' }

$BaseDir = Join-Path $Versions '1.8.9'
New-Item -ItemType Directory -Force -Path $BaseDir | Out-Null
$BaseJsonPath = Join-Path $BaseDir '1.8.9.json'
Download-File ([string]$baseMeta.url) $BaseJsonPath
$baseJson = Get-Content -LiteralPath $BaseJsonPath -Raw | ConvertFrom-Json

$BaseJar = Join-Path $BaseDir '1.8.9.jar'
Download-File ([string]$baseJson.downloads.client.url) $BaseJar

$baseLibraryCount = 0
foreach ($lib in @($baseJson.libraries)) {
    if (-not (Rule-AllowsWindows $lib)) { continue }

    $downloads = $lib.PSObject.Properties['downloads']
    $artifact = if ($downloads -and $downloads.Value) { $downloads.Value.PSObject.Properties['artifact'] } else { $null }
    if ($artifact -and $artifact.Value -and $artifact.Value.url) {
        $dest = Join-Path $Libraries ([string]$artifact.Value.path).Replace('/', '\')
        Download-File ([string]$artifact.Value.url) $dest
        $baseLibraryCount++
    } elseif ($lib.name) {
        [void](Download-Maven ([string]$lib.name) 'https://libraries.minecraft.net/')
        $baseLibraryCount++
    }

    $classifiers = if ($downloads -and $downloads.Value) { $downloads.Value.PSObject.Properties['classifiers'] } else { $null }
    if ($classifiers -and $classifiers.Value) {
        foreach ($prop in @($classifiers.Value.PSObject.Properties)) {
            if ($prop.Name -notmatch 'windows') { continue }
            if (-not $prop.Value.url) { continue }
            $dest = Join-Path $Libraries ([string]$prop.Value.path).Replace('/', '\')
            Download-File ([string]$prop.Value.url) $dest
        }
    }
}

# Asset index is enough for bootstrap/resource-manager structure. Full object
# download is optional because CI production smoke only needs client bootstrap.
if ($baseJson.assetIndex -and $baseJson.assetIndex.url) {
    $indexId = [string]$baseJson.assetIndex.id
    $IndexDir = Join-Path $Assets 'indexes'
    New-Item -ItemType Directory -Force -Path $IndexDir | Out-Null
    $IndexPath = Join-Path $IndexDir ($indexId + '.json')
    Download-File ([string]$baseJson.assetIndex.url) $IndexPath

    if ($DownloadAssets) {
        $assetIndex = Get-Content -LiteralPath $IndexPath -Raw | ConvertFrom-Json
        $objects = $assetIndex.objects.PSObject.Properties
        $seen = New-Object 'System.Collections.Generic.HashSet[string]'
        foreach ($entry in $objects) {
            $hash = [string]$entry.Value.hash
            if (-not $seen.Add($hash)) { continue }
            $prefix = $hash.Substring(0,2)
            $dest = Join-Path $Assets ("objects\$prefix\$hash")
            Download-File ("https://resources.download.minecraft.net/$prefix/$hash") $dest
        }
    }
}

# Forge 1.8.9 installer contains install_profile.json with versionInfo.
$InstallerName = "forge-$ForgeVersion-installer.jar"
$InstallerPath = Join-Path $Temp $InstallerName
$InstallerUrl = "https://maven.minecraftforge.net/net/minecraftforge/forge/$ForgeVersion/$InstallerName"
Download-File $InstallerUrl $InstallerPath

Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [IO.Compression.ZipFile]::OpenRead($InstallerPath)
try {
    $profileEntry = $zip.GetEntry('install_profile.json')
    if ($null -eq $profileEntry) {
        throw 'Forge installer has no install_profile.json.'
    }
    $reader = New-Object IO.StreamReader($profileEntry.Open())
    try { $profileRaw = $reader.ReadToEnd() } finally { $reader.Dispose() }
}
finally {
    $zip.Dispose()
}

$profile = $profileRaw | ConvertFrom-Json
$versionInfoProp = $profile.PSObject.Properties['versionInfo']
if ($null -eq $versionInfoProp -or $null -eq $versionInfoProp.Value) {
    throw 'Forge 1.8.9 install_profile.json has no versionInfo.'
}
$forgeInfo = $versionInfoProp.Value
$forgeId = [string]$forgeInfo.id
if (-not $forgeId) {
    throw 'Forge versionInfo.id is empty.'
}

$ForgeDir = Join-Path $Versions $forgeId
New-Item -ItemType Directory -Force -Path $ForgeDir | Out-Null
$ForgeJson = Join-Path $ForgeDir ($forgeId + '.json')
$forgeInfo | ConvertTo-Json -Depth 100 | Set-Content -LiteralPath $ForgeJson -Encoding UTF8

$forgeLibraryCount = 0
foreach ($lib in @($forgeInfo.libraries)) {
    $clientReq = $lib.PSObject.Properties['clientreq']
    if ($clientReq -and $clientReq.Value -eq $false) { continue }
    if (-not $lib.name) { continue }

    $urlProp = $lib.PSObject.Properties['url']
    $baseUrl = if ($urlProp -and $urlProp.Value) { [string]$urlProp.Value } else { 'https://libraries.minecraft.net/' }

    # Forge itself is hosted on the Forge Maven even if the old profile omits URL.
    if ([string]$lib.name -like 'net.minecraftforge:forge:*') {
        $baseUrl = 'https://maven.minecraftforge.net/'
    }

    try {
        [void](Download-Maven ([string]$lib.name) $baseUrl)
    }
    catch {
        # A few old profile entries rely on Forge Maven despite a missing/stale URL.
        if ($baseUrl -ne 'https://maven.minecraftforge.net/') {
            [void](Download-Maven ([string]$lib.name) 'https://maven.minecraftforge.net/')
        } else {
            throw
        }
    }
    $forgeLibraryCount++
}

# The standalone launcher expects an assets directory and the base/Forge
# version metadata in this exact structure.
$record = Join-Path $MinecraftDir 'openabyss-production-profile.txt'
@(
    'OPENABYSS_PRODUCTION_FORGE_PROFILE=READY'
    "MC_VERSION=1.8.9"
    "FORGE_VERSION=$ForgeVersion"
    "FORGE_PROFILE_ID=$forgeId"
    "BASE_LIBRARIES=$baseLibraryCount"
    "FORGE_LIBRARIES=$forgeLibraryCount"
    "BASE_JAR_SHA256=$((Get-FileHash -LiteralPath $BaseJar -Algorithm SHA256).Hash)"
    "FORGE_JSON=$ForgeJson"
) | Set-Content -LiteralPath $record -Encoding UTF8

Write-Host "OPENABYSS_PRODUCTION_FORGE_PROFILE=READY"
Write-Host "FORGE_PROFILE_ID=$forgeId"
Write-Host "BASE_LIBRARIES=$baseLibraryCount"
Write-Host "FORGE_LIBRARIES=$forgeLibraryCount"
Write-Host "PROFILE_RECORD=$record"
