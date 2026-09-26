param(
    [string]$LauncherDir = (Split-Path -Parent $MyInvocation.MyCommand.Path),
    [string]$MinecraftDir = "",
    [string]$Java8 = "",
    [string]$Username = "Player",
    [int]$RamMB = 4096,
    [string]$ForgeVersion = "",
    [switch]$ValidateOnly,
    [switch]$ResolverSelfTest
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

function Validate-AbyssJar([string]$Path) {
    Require (Test-Path -LiteralPath $Path -PathType Leaf) "abyss.jar is missing: $Path"
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [IO.Compression.ZipFile]::OpenRead($Path)
    try {
        $manifestEntry = $zip.GetEntry('META-INF/MANIFEST.MF')
        Require ($null -ne $manifestEntry) 'abyss.jar has no META-INF/MANIFEST.MF.'
        $reader = New-Object IO.StreamReader($manifestEntry.Open())
        try { $manifest = $reader.ReadToEnd() } finally { $reader.Dispose() }

        foreach ($line in @(
            'FMLCorePlugin: Abyss.ASM.CoreMod',
            'FMLCorePluginContainsFMLMod: true',
            'ForceLoadAsMod: true',
            'ModSide: CLIENT'
        )) {
            Require ($manifest.IndexOf($line, [StringComparison]::Ordinal) -ge 0) "abyss.jar manifest is missing: $line"
        }

        foreach ($entryName in @(
            'Abyss/ASM/CoreMod.class',
            'Abyss/AbyssClient.class',
            'assets/abyss/asm/mcp-notch.srg',
            'assets/abyss/asm/mcp-srg.srg',
            'mcmod.info'
        )) {
            Require ($null -ne $zip.GetEntry($entryName)) "abyss.jar is missing required entry: $entryName"
        }
    }
    finally {
        $zip.Dispose()
    }
    return (Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash
}

function Get-Prop([object]$Object, [string]$Name) {
    if ($null -eq $Object) { return $null }
    $prop = $Object.PSObject.Properties[$Name]
    if ($null -eq $prop) { return $null }
    return $prop.Value
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

function Test-LibraryAllowed([object]$Lib) {
    $rules = Get-Prop $Lib 'rules'
    if (-not $rules) { return $true }

    $allowed = $false
    foreach ($rule in @($rules)) {
        $action = [string](Get-Prop $rule 'action')
        $os = Get-Prop $rule 'os'
        $matches = $true
        if ($os) {
            $name = [string](Get-Prop $os 'name')
            if ($name -and $name -ne 'windows') { $matches = $false }
            $arch = [string](Get-Prop $os 'arch')
            if ($arch -and $arch -notmatch 'x86_64|amd64|64') { $matches = $false }
            $version = [string](Get-Prop $os 'version')
            if ($version) {
                try {
                    if (-not ([Environment]::OSVersion.VersionString -match $version)) { $matches = $false }
                } catch {
                    $matches = $false
                }
            }
        }
        if ($matches) {
            $allowed = ($action -eq 'allow')
        }
    }
    return $allowed
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

function Add-ClasspathLibrary([object]$Lib, [string]$LibraryRoot, [System.Collections.Generic.List[string]]$List, [System.Collections.Generic.List[string]]$Missing) {
    if (-not (Test-LibraryAllowed $Lib)) { return }
    $path = $null
    $downloads = Get-Prop $Lib 'downloads'
    $artifactDownload = Get-Prop $downloads 'artifact'
    $artifactPath = Get-Prop $artifactDownload 'path'
    $name = Get-Prop $Lib 'name'

    if ($artifactPath) {
        $path = Join-Path $LibraryRoot ([string]$artifactPath).Replace('/', '\')
    } elseif ($name) {
        $path = Maven-Path ([string]$name) $LibraryRoot
    }
    if ($path -and (Test-Path -LiteralPath $path -PathType Leaf)) {
        if (-not $List.Contains($path)) { $List.Add($path) }
    } elseif ($path) {
        $Missing.Add($path)
    }
}

function Get-NativeJar([object]$Lib, [string]$LibraryRoot) {
    if (-not (Test-LibraryAllowed $Lib)) { return $null }
    $natives = Get-Prop $Lib 'natives'
    $windowsNative = Get-Prop $natives 'windows'
    if (-not $windowsNative) { return $null }

    $classifier = ([string]$windowsNative).Replace('${arch}','64')
    $downloads = Get-Prop $Lib 'downloads'
    $classifiers = Get-Prop $downloads 'classifiers'
    if ($classifiers) {
        $prop = $classifiers.PSObject.Properties[$classifier]
        if ($prop) {
            $downloadPath = Get-Prop $prop.Value 'path'
            if ($downloadPath) {
                $candidate = Join-Path $LibraryRoot ([string]$downloadPath).Replace('/', '\')
                if (Test-Path -LiteralPath $candidate -PathType Leaf) { return $candidate }
            }
        }
    }

    $name = Get-Prop $Lib 'name'
    if ($name) {
        $base = ([string]$name).Split(':')
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

if ($ResolverSelfTest) {
    $selfRoot = Join-Path $env:TEMP 'openabyss-launcher-resolver-selftest'
    Remove-Item -LiteralPath $selfRoot -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path $selfRoot | Out-Null
    try {
        $legacy = [pscustomobject]@{ name = 'example.group:legacy-lib:1.2.3' }
        $legacyPath = Maven-Path $legacy.name $selfRoot
        New-Item -ItemType Directory -Force -Path (Split-Path -Parent $legacyPath) | Out-Null
        Set-Content -LiteralPath $legacyPath -Value 'fixture'

        $list = New-Object System.Collections.Generic.List[string]
        $missing = New-Object System.Collections.Generic.List[string]
        Add-ClasspathLibrary $legacy $selfRoot $list $missing
        Require ($list.Count -eq 1) 'Resolver self-test failed legacy Maven coordinate resolution.'
        Require ($missing.Count -eq 0) 'Resolver self-test incorrectly marked existing legacy library missing.'

        $windowsAllowed = ConvertFrom-Json '{"name":"example:win:1","rules":[{"action":"allow","os":{"name":"windows"}}]}'
        $linuxOnly = ConvertFrom-Json '{"name":"example:linux:1","rules":[{"action":"allow","os":{"name":"linux"}}]}'
        Require (Test-LibraryAllowed $windowsAllowed) 'Resolver self-test failed Windows allow rule.'
        Require (-not (Test-LibraryAllowed $linuxOnly)) 'Resolver self-test failed non-Windows exclusion rule.'

        $classifierFixture = ConvertFrom-Json '{"name":"org.lwjgl.lwjgl:lwjgl-platform:2.9.4-nightly-20150209","natives":{"windows":"natives-windows-${arch}"}}'
        $nativePath = Maven-Path 'org.lwjgl.lwjgl:lwjgl-platform:2.9.4-nightly-20150209:natives-windows-64' $selfRoot
        New-Item -ItemType Directory -Force -Path (Split-Path -Parent $nativePath) | Out-Null
        Set-Content -LiteralPath $nativePath -Value 'fixture'
        $resolvedNative = Get-NativeJar $classifierFixture $selfRoot
        Require ($resolvedNative -eq $nativePath) 'Resolver self-test failed native classifier substitution.'

        Write-Host 'OPENABYSS_LAUNCHER_RESOLVER_SELFTEST=PASS'
        exit 0
    }
    finally {
        Remove-Item -LiteralPath $selfRoot -Recurse -Force -ErrorAction SilentlyContinue
    }
}

Set-Content -LiteralPath $Log -Value ('OpenAbyss standalone launcher ' + (Get-Date -Format o)) -Encoding UTF8
$BootstrapStage = Join-Path $GameDir 'abyss-bootstrap-stage.txt'
$RuntimeStage = Join-Path $GameDir 'abyss-runtime-stage.txt'
$ModuleFailure = Join-Path $GameDir 'abyss-module-failure.txt'
$EventFailure = Join-Path $GameDir 'abyss-event-failure.txt'
$ConfigFailure = Join-Path $GameDir 'abyss-config-failure.txt'
$RendererFailure = Join-Path $GameDir 'abyss-renderer-failure.txt'
$BootstrapDiag = Join-Path $GameDir 'abyss-bootstrap-diagnostics.txt'
$Census = Join-Path $GameDir 'abyss-census.tsv'
Remove-Item -LiteralPath $Stdout,$Stderr,$BootstrapStage,$RuntimeStage,$ModuleFailure,$EventFailure,$ConfigFailure,$RendererFailure,$BootstrapDiag,$Census -Force -ErrorAction SilentlyContinue
Remove-Item -LiteralPath $CrashOut -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $GameDir,(Join-Path $GameDir 'mods'),$CrashOut | Out-Null

$JarHash = Validate-AbyssJar $Jar
$BuildInfoPath = Join-Path $LauncherDir 'BUILD-INFO.txt'
if (Test-Path -LiteralPath $BuildInfoPath -PathType Leaf) {
    $BuildInfoMap = @{}
    foreach ($line in @(Get-Content -LiteralPath $BuildInfoPath)) {
        if ($line -match '^([^=]+)=(.*)$') {
            $BuildInfoMap[$matches[1]] = $matches[2]
        }
    }
    if ($BuildInfoMap.ContainsKey('JAR_SHA256')) {
        Require ($BuildInfoMap['JAR_SHA256'] -eq $JarHash) 'BUILD-INFO.txt JAR hash does not match abyss.jar.'
    }
    if ($BuildInfoMap.ContainsKey('JAR_BYTES')) {
        Require ([int64]$BuildInfoMap['JAR_BYTES'] -eq (Get-Item -LiteralPath $Jar).Length) 'BUILD-INFO.txt JAR size does not match abyss.jar.'
    }
}
Log "OpenAbyss JAR SHA-256: $JarHash"

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
$forgeCandidates = @(Get-ChildItem -LiteralPath $versions -Directory -ErrorAction SilentlyContinue |
    Where-Object { $_.Name -match '1\.8\.9.*forge|forge.*1\.8\.9' })

$forgeDir = $null
if ($ForgeVersion) {
    $forgeDir = $forgeCandidates | Where-Object { $_.Name -eq $ForgeVersion } | Select-Object -First 1
    Require ($null -ne $forgeDir) "Requested Forge profile is not installed: $ForgeVersion"
} else {
    $preferredForge = '1.8.9-forge1.8.9-11.15.1.2318-1.8.9'
    $forgeDir = $forgeCandidates | Where-Object { $_.Name -eq $preferredForge } | Select-Object -First 1
    if (-not $forgeDir) {
        $forgeDir = $forgeCandidates | Sort-Object Name -Descending | Select-Object -First 1
    }
}
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
$forgeLibraries = Get-Prop $forgeJson 'libraries'
$baseLibraries = Get-Prop $baseJson 'libraries'
if ($forgeLibraries) { $allLibraries += @($forgeLibraries) }
if ($baseLibraries) { $allLibraries += @($baseLibraries) }

$classPath = New-Object System.Collections.Generic.List[string]
$missingLibraries = New-Object System.Collections.Generic.List[string]
foreach ($lib in $allLibraries) {
    Add-ClasspathLibrary $lib $libraryRoot $classPath $missingLibraries
}
$classPath.Add($baseJar)
$classPath.Add($Jar)
Require ($classPath.Count -gt 20) "Too few runtime classpath entries were resolved: $($classPath.Count)"

$criticalJarPatterns = @(
    'launchwrapper-*.jar',
    'forge-1.8.9-*.jar',
    'lwjgl-2.9*.jar',
    'lwjgl_util-2.9*.jar'
)
foreach ($pattern in $criticalJarPatterns) {
    $hit = @($classPath | Where-Object { [IO.Path]::GetFileName($_) -like $pattern })
    Require ($hit.Count -gt 0) "Critical runtime library missing from classpath: $pattern"
}

if ($missingLibraries.Count -gt 0) {
    Log ("Optional/ruled runtime libraries missing: " + $missingLibraries.Count)
    $missingLibraries | Set-Content -LiteralPath (Join-Path $LauncherDir 'missing-libraries.txt') -Encoding UTF8
} else {
    Remove-Item -LiteralPath (Join-Path $LauncherDir 'missing-libraries.txt') -Force -ErrorAction SilentlyContinue
}

$nativeCount = Extract-Natives $allLibraries $libraryRoot $NativesDir
$nativeFiles = @(Get-ChildItem -LiteralPath $NativesDir -Filter '*.dll' -File -ErrorAction SilentlyContinue)
Require ($nativeFiles.Count -gt 0) 'No Windows native DLLs could be extracted.'
foreach ($requiredNative in @('lwjgl64.dll','OpenAL64.dll')) {
    Require (Test-Path -LiteralPath (Join-Path $NativesDir $requiredNative) -PathType Leaf) "Required Windows native missing: $requiredNative"
}
Log "Classpath entries: $($classPath.Count)"
Log "Missing library files: $($missingLibraries.Count)"
Log "Native library archives extracted: $nativeCount"
Log "Native DLLs extracted: $($nativeFiles.Count)"

$staleModJar = Join-Path (Join-Path $GameDir 'mods') 'abyss.jar'
Remove-Item -LiteralPath $staleModJar -Force -ErrorAction SilentlyContinue
Log 'Using packaged JAR from the classpath/coremod path; no duplicate copy is placed in game\mods.'

$assets = Join-Path $MinecraftDir 'assets'
Require (Test-Path -LiteralPath $assets -PathType Container) 'Minecraft assets directory is missing.'

$uuid = [Guid]::NewGuid().ToString('N')
$forgeMainClass = Get-Prop $forgeJson 'mainClass'
$mainClass = if ($forgeMainClass) { [string]$forgeMainClass } else { 'net.minecraft.launchwrapper.Launch' }

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

if ($ValidateOnly) {
    @(
        'OPENABYSS_STANDALONE_PREFLIGHT=PASS'
        "JAVA=$Java"
        "FORGE=$($forgeDir.Name)"
        "CLASSPATH_COUNT=$($classPath.Count)"
        "MISSING_LIBRARIES=$($missingLibraries.Count)"
        "NATIVE_ARCHIVES=$nativeCount"
        "NATIVE_DLLS=$($nativeFiles.Count)"
        "JAR_SHA256=$JarHash"
        "GAME_DIR=$GameDir"
    ) | Set-Content -LiteralPath (Join-Path $LauncherDir 'launcher-result.txt') -Encoding UTF8
    Log 'Standalone launcher preflight passed; -ValidateOnly requested, Minecraft was not started.'
    exit 0
}

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

function Last-Stage([string]$Path) {
    if (-not (Test-Path -LiteralPath $Path -PathType Leaf)) { return '<none>' }
    $lines = @(Get-Content -LiteralPath $Path | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
    if ($lines.Count -eq 0) { return '<none>' }
    return ($lines[-1] -split "\t", 2)[-1]
}

function Last-Line([string]$Path) {
    if (-not (Test-Path -LiteralPath $Path -PathType Leaf)) { return '<none>' }
    $lines = @(Get-Content -LiteralPath $Path | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
    if ($lines.Count -eq 0) { return '<none>' }
    return $lines[-1]
}

$lastBootstrap = Last-Stage $BootstrapStage
$lastRuntime = Last-Stage $RuntimeStage
$lastModuleFailure = Last-Line $ModuleFailure
$lastEventFailure = Last-Line $EventFailure
$lastConfigFailure = Last-Line $ConfigFailure
$lastRendererFailure = Last-Line $RendererFailure
$diagPresent = Test-Path -LiteralPath $BootstrapDiag -PathType Leaf
$censusCount = if (Test-Path -LiteralPath $Census -PathType Leaf) {
    @(Get-Content -LiteralPath $Census | Where-Object { -not [string]::IsNullOrWhiteSpace($_) }).Count
} else { -1 }

foreach ($evidenceFile in @($BootstrapStage,$RuntimeStage,$ModuleFailure,$EventFailure,$ConfigFailure,$RendererFailure,$BootstrapDiag,$Census)) {
    if (Test-Path -LiteralPath $evidenceFile -PathType Leaf) {
        Copy-Item -LiteralPath $evidenceFile -Destination $CrashOut -Force
    }
}

$result = @(
    "EXIT_CODE=$exitCode",
    "JAVA=$Java",
    "FORGE=$($forgeDir.Name)",
    "CLASSPATH_COUNT=$($classPath.Count)",
    "MISSING_LIBRARIES=$($missingLibraries.Count)",
    "NATIVE_ARCHIVES=$nativeCount",
    "NATIVE_DLLS=$($nativeFiles.Count)",
    "JAR_SHA256=$JarHash",
    "BOOTSTRAP_DIAGNOSTICS=$diagPresent",
    "MODULE_CENSUS_COUNT=$censusCount",
    "LAST_BOOTSTRAP_STAGE=$lastBootstrap",
    "LAST_RUNTIME_STAGE=$lastRuntime",
    "LAST_MODULE_FAILURE=$lastModuleFailure",
    "LAST_EVENT_FAILURE=$lastEventFailure",
    "LAST_CONFIG_FAILURE=$lastConfigFailure",
    "LAST_RENDERER_FAILURE=$lastRendererFailure",
    "CRASH_FILES=$(@(Get-ChildItem -LiteralPath $CrashOut -File -ErrorAction SilentlyContinue).Count)"
)
$result | Set-Content -LiteralPath (Join-Path $LauncherDir 'launcher-result.txt') -Encoding UTF8

if ($exitCode -ne 0) {
    throw "OpenAbyss exited with code $exitCode. See minecraft.stderr.log, game\logs\latest.log and crash-evidence."
}

Log 'OpenAbyss exited normally.'
