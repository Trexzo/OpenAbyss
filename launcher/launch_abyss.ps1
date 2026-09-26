param(
    [string]$LauncherDir = (Split-Path -Parent $MyInvocation.MyCommand.Path),
    [string]$MinecraftDir = "",
    [string]$Java8 = "",
    [string]$Username = "Player",
    [int]$RamMB = 4096
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$LauncherDir = [IO.Path]::GetFullPath($LauncherDir)
$Jar = Join-Path $LauncherDir 'abyss.jar'
$Log = Join-Path $LauncherDir 'launcher-latest.log'
$Stdout = Join-Path $LauncherDir 'minecraft.stdout.log'
$Stderr = Join-Path $LauncherDir 'minecraft.stderr.log'
$GameDir = Join-Path $LauncherDir 'game'
$NativesDir = Join-Path $LauncherDir 'runtime-natives'
$CrashOut = Join-Path $LauncherDir 'crash-evidence'

function Log([string]$Message) {
    $line = '[' + (Get-Date -Format 'yyyy-MM-dd HH:mm:ss') + '] ' + $Message
    Add-Content -LiteralPath $Log -Value $line -Encoding UTF8
    Write-Host $Message
}

function Require([bool]$Condition, [string]$Message) {
    if (-not $Condition) { throw $Message }
}

function Test-Java8([string]$Candidate) {
    if (-not $Candidate) { return $null }
    $exe = $Candidate
    if (Test-Path -LiteralPath (Join-Path $Candidate 'bin\java.exe') -PathType Leaf) {
        $exe = Join-Path $Candidate 'bin\java.exe'
    }
    if (-not (Test-Path -LiteralPath $exe -PathType Leaf)) { return $null }
    try {
        $version = (& $exe -version 2>&1 | Out-String)
        if ($version -match 'version "1\.8\.' -or $version -match 'version "8') { return $exe }
    } catch {}
    return $null
}

function Find-Java8 {
    if ($Java8) {
        $hit = Test-Java8 $Java8
        if ($hit) { return $hit }
        throw "Configured Java 8 is invalid: $Java8"
    }

    $candidates = New-Object System.Collections.Generic.List[string]
    foreach ($relative in @('java','jre8','jdk8','runtime')) {
        $candidates.Add((Join-Path $LauncherDir $relative))
    }
    if ($env:JAVA_HOME) { $candidates.Add($env:JAVA_HOME) }
    foreach ($pattern in @(
        "$env:ProgramFiles\Eclipse Adoptium\jdk-8*",
        "$env:ProgramFiles\Amazon Corretto\jdk8*",
        "$env:ProgramFiles\BellSoft\LibericaJDK-8*",
        "$env:ProgramFiles\Java\jdk1.8*",
        "$env:LOCALAPPDATA\Programs\Eclipse Adoptium\jdk-8*"
    )) {
        foreach ($d in @(Get-ChildItem -Path $pattern -Directory -ErrorAction SilentlyContinue | Sort-Object Name -Descending)) {
            $candidates.Add($d.FullName)
        }
    }
    foreach ($candidate in $candidates) {
        $hit = Test-Java8 $candidate
        if ($hit) { return $hit }
    }
    foreach ($hitPath in @(where.exe java.exe 2>$null)) {
        $hit = Test-Java8 $hitPath
        if ($hit) { return $hit }
    }
    return $null
}

function Maven-Path([string]$Coordinate, [string]$LibraryRoot) {
    if (-not $Coordinate) { return $null }
    $coord = $Coordinate
    $ext = 'jar'
    if ($coord.Contains('@')) {
        $partsExt = $coord.Split('@')
        $coord = $partsExt[0]
        $ext = $partsExt[1]
    }
    $p = $coord.Split(':')
    if ($p.Length -lt 3) { return $null }
    $group = $p[0].Replace('.', '\')
    $artifact = $p[1]
    $version = $p[2]
    $classifier = if ($p.Length -ge 4 -and $p[3]) { '-' + $p[3] } else { '' }
    return Join-Path $LibraryRoot (Join-Path $group (Join-Path $artifact (Join-Path $version ($artifact + '-' + $version + $classifier + '.' + $ext))))
}

function Add-ClasspathLibrary([object]$Lib, [string]$LibraryRoot, [System.Collections.Generic.List[string]]$List) {
    $path = $null
    if ($Lib.downloads -and $Lib.downloads.artifact -and $Lib.downloads.artifact.path) {
        $path = Join-Path $LibraryRoot ([string]$Lib.downloads.artifact.path).Replace('/', '\')
    } elseif ($Lib.name) {
        $path = Maven-Path ([string]$Lib.name) $LibraryRoot
    }
    if ($path -and (Test-Path -LiteralPath $path -PathType Leaf) -and -not $List.Contains($path)) {
        $List.Add($path)
    }
}

function Get-NativeJar([object]$Lib, [string]$LibraryRoot) {
    if (-not $Lib.natives -or -not $Lib.natives.windows) { return $null }
    $classifier = ([string]$Lib.natives.windows).Replace('${arch}','64')

    if ($Lib.downloads -and $Lib.downloads.classifiers) {
        $prop = $Lib.downloads.classifiers.PSObject.Properties[$classifier]
        if ($prop -and $prop.Value.path) {
            $candidate = Join-Path $LibraryRoot ([string]$prop.Value.path).Replace('/', '\')
            if (Test-Path -LiteralPath $candidate -PathType Leaf) { return $candidate }
        }
    }

    if ($Lib.name) {
        $base = ([string]$Lib.name).Split(':')
        if ($base.Length -ge 3) {
            $candidate = Maven-Path ($base[0] + ':' + $base[1] + ':' + $base[2] + ':' + $classifier) $LibraryRoot
            if ($candidate -and (Test-Path -LiteralPath $candidate -PathType Leaf)) { return $candidate }
        }
    }
    return $null
}

function Extract-Natives([object[]]$Libraries, [string]$LibraryRoot, [string]$Destination) {
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    Remove-Item -LiteralPath $Destination -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path $Destination | Out-Null

    $nativeJars = New-Object System.Collections.Generic.HashSet[string]
    foreach ($lib in $Libraries) {
        $native = Get-NativeJar $lib $LibraryRoot
        if (-not $native -or -not $nativeJars.Add($native)) { continue }
        $zip = [IO.Compression.ZipFile]::OpenRead($native)
        try {
            foreach ($entry in $zip.Entries) {
                if (-not $entry.Name) { continue }
                if ($entry.FullName.StartsWith('META-INF/', [StringComparison]::OrdinalIgnoreCase)) { continue }
                if (-not $entry.Name.EndsWith('.dll', [StringComparison]::OrdinalIgnoreCase)) { continue }
                [IO.Compression.ZipFileExtensions]::ExtractToFile($entry, (Join-Path $Destination $entry.Name), $true)
            }
        } finally {
            $zip.Dispose()
        }
    }
    return $nativeJars.Count
}

function Quote-Arg([string]$Value) {
    if ($null -eq $Value) { return '""' }
    if ($Value -notmatch '[\s"]') { return $Value }
    return '"' + ($Value -replace '"','\"') + '"'
}

Set-Content -LiteralPath $Log -Value ('OpenAbyss standalone launcher ' + (Get-Date -Format o)) -Encoding UTF8
Remove-Item -LiteralPath $Stdout,$Stderr -Force -ErrorAction SilentlyContinue
Remove-Item -LiteralPath $CrashOut -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $GameDir,(Join-Path $GameDir 'mods'),$CrashOut | Out-Null

Require (Test-Path -LiteralPath $Jar -PathType Leaf) "abyss.jar is missing beside the launcher: $Jar"

if (-not $MinecraftDir) {
    $MinecraftDir = Join-Path $env:APPDATA '.minecraft'
}
$MinecraftDir = [IO.Path]::GetFullPath($MinecraftDir)
Require (Test-Path -LiteralPath $MinecraftDir -PathType Container) "Minecraft directory not found: $MinecraftDir"

$Java = Find-Java8
Require ($null -ne $Java) 'Java 8 was not found. Install a JDK/JRE 8 or pass -Java8.'
Log "Java 8: $Java"
Log "Minecraft source directory: $MinecraftDir"
Log "Isolated game directory: $GameDir"

$versions = Join-Path $MinecraftDir 'versions'
$forgeDir = Get-ChildItem -LiteralPath $versions -Directory -ErrorAction SilentlyContinue |
    Where-Object { $_.Name -match '1\.8\.9.*forge|forge.*1\.8\.9' } |
    Sort-Object Name -Descending |
    Select-Object -First 1
Require ($null -ne $forgeDir) 'No installed Forge 1.8.9 version was found under .minecraft\versions.'

$forgeJsonPath = Join-Path $forgeDir.FullName ($forgeDir.Name + '.json')
if (-not (Test-Path -LiteralPath $forgeJsonPath)) {
    $firstJson = Get-ChildItem -LiteralPath $forgeDir.FullName -Filter '*.json' -File | Select-Object -First 1
    if ($firstJson) { $forgeJsonPath = $firstJson.FullName }
}
Require (Test-Path -LiteralPath $forgeJsonPath -PathType Leaf) 'Forge version JSON is missing.'
$forgeJson = Get-Content -LiteralPath $forgeJsonPath -Raw | ConvertFrom-Json
Log "Forge profile: $($forgeDir.Name)"

$baseDir = Join-Path $versions '1.8.9'
$baseJsonPath = Join-Path $baseDir '1.8.9.json'
$baseJar = Join-Path $baseDir '1.8.9.jar'
Require (Test-Path -LiteralPath $baseJsonPath -PathType Leaf) 'Base Minecraft 1.8.9 JSON is missing.'
Require (Test-Path -LiteralPath $baseJar -PathType Leaf) 'Base Minecraft 1.8.9 JAR is missing.'
$baseJson = Get-Content -LiteralPath $baseJsonPath -Raw | ConvertFrom-Json

$libraryRoot = Join-Path $MinecraftDir 'libraries'
Require (Test-Path -LiteralPath $libraryRoot -PathType Container) 'Minecraft libraries directory is missing.'

$allLibraries = @()
if ($forgeJson.libraries) { $allLibraries += @($forgeJson.libraries) }
if ($baseJson.libraries) { $allLibraries += @($baseJson.libraries) }

$classPath = New-Object System.Collections.Generic.List[string]
foreach ($lib in $allLibraries) {
    Add-ClasspathLibrary $lib $libraryRoot $classPath
}
$classPath.Add($baseJar)
$classPath.Add($Jar)
Require ($classPath.Count -gt 20) "Too few runtime classpath entries were resolved: $($classPath.Count)"

$nativeCount = Extract-Natives $allLibraries $libraryRoot $NativesDir
Require ((Get-ChildItem -LiteralPath $NativesDir -Filter '*.dll' -File -ErrorAction SilentlyContinue).Count -gt 0) 'No Windows native DLLs could be extracted.'
Log "Classpath entries: $($classPath.Count)"
Log "Native library archives extracted: $nativeCount"

$modJar = Join-Path (Join-Path $GameDir 'mods') 'abyss.jar'
Copy-Item -LiteralPath $Jar -Destination $modJar -Force

$assets = Join-Path $MinecraftDir 'assets'
Require (Test-Path -LiteralPath $assets -PathType Container) 'Minecraft assets directory is missing.'

$uuid = [Guid]::NewGuid().ToString('N')
$mainClass = if ($forgeJson.mainClass) { [string]$forgeJson.mainClass } else { 'net.minecraft.launchwrapper.Launch' }

$jvmArgs = @(
    "-Xmx${RamMB}M",
    '-XX:+UseG1GC',
    "-Djava.library.path=$NativesDir",
    '-Dfml.coreMods.load=Abyss.ASM.CoreMod',
    '-Dminecraft.launcher.brand=OpenAbyssRecovery',
    '-Dminecraft.launcher.version=1'
)

$gameArgs = @(
    '--username', $Username,
    '--version', $forgeDir.Name,
    '--gameDir', $GameDir,
    '--assetsDir', $assets,
    '--assetIndex', '1.8',
    '--uuid', $uuid,
    '--accessToken', '0',
    '--userProperties', '{}',
    '--userType', 'Legacy',
    '--tweakClass', 'net.minecraftforge.fml.common.launcher.FMLTweaker'
)

$fullArgs = @($jvmArgs) + @('-cp', ($classPath -join ';'), $mainClass) + $gameArgs
$argString = ($fullArgs | ForEach-Object { Quote-Arg ([string]$_) }) -join ' '

Log "Main class: $mainClass"
Log "Launching packaged OpenAbyss..."
$p = Start-Process -FilePath $Java -ArgumentList $argString -WorkingDirectory $GameDir -RedirectStandardOutput $Stdout -RedirectStandardError $Stderr -PassThru -Wait
$exitCode = $p.ExitCode
Log "Minecraft/OpenAbyss exit code: $exitCode"

$crashDir = Join-Path $GameDir 'crash-reports'
foreach ($crash in @(Get-ChildItem -LiteralPath $crashDir -File -ErrorAction SilentlyContinue)) {
    Copy-Item -LiteralPath $crash.FullName -Destination $CrashOut -Force
}
foreach ($fatal in @(
    Get-ChildItem -LiteralPath $GameDir -Filter 'hs_err_pid*.log' -File -ErrorAction SilentlyContinue
    Get-ChildItem -LiteralPath $LauncherDir -Filter 'hs_err_pid*.log' -File -ErrorAction SilentlyContinue
)) {
    Copy-Item -LiteralPath $fatal.FullName -Destination $CrashOut -Force
}

$result = @(
    "EXIT_CODE=$exitCode",
    "JAVA=$Java",
    "FORGE=$($forgeDir.Name)",
    "CLASSPATH_COUNT=$($classPath.Count)",
    "NATIVE_ARCHIVES=$nativeCount",
    "JAR_SHA256=$((Get-FileHash -LiteralPath $Jar -Algorithm SHA256).Hash)",
    "CRASH_FILES=$(@(Get-ChildItem -LiteralPath $CrashOut -File -ErrorAction SilentlyContinue).Count)"
)
$result | Set-Content -LiteralPath (Join-Path $LauncherDir 'launcher-result.txt') -Encoding UTF8

if ($exitCode -ne 0) {
    throw "OpenAbyss exited with code $exitCode. See minecraft.stderr.log, game\logs\latest.log and crash-evidence."
}

Log 'OpenAbyss exited normally.'
