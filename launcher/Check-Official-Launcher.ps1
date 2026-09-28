param(
    [string]$PackageDir = (Split-Path -Parent $MyInvocation.MyCommand.Path),
    [switch]$FixtureSelfTest
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$PackageDir = [IO.Path]::GetFullPath($PackageDir)
$GameDir = Join-Path $PackageDir 'official-game'
$Runtime = Join-Path $GameDir 'abyss-runtime-stage.txt'
$Bootstrap = Join-Path $GameDir 'abyss-bootstrap-stage.txt'
$ModuleFailure = Join-Path $GameDir 'abyss-module-failure.txt'
$FeatureFailure = Join-Path $GameDir 'abyss-feature-failure.txt'
$EventFailure = Join-Path $GameDir 'abyss-event-failure.txt'
$ConfigFailure = Join-Path $GameDir 'abyss-config-failure.txt'
$RendererFailure = Join-Path $GameDir 'abyss-renderer-failure.txt'
$Diag = Join-Path $GameDir 'abyss-bootstrap-diagnostics.txt'
$Census = Join-Path $GameDir 'abyss-census.tsv'
$LatestLog = Join-Path $GameDir 'logs\latest.log'
$CrashDir = Join-Path $GameDir 'crash-reports'
$SessionFile = Join-Path $GameDir 'openabyss-test-session.txt'
$InstalledJar = Join-Path $GameDir 'mods\abyss.jar'
$BuildInfo = Join-Path $PackageDir 'BUILD-INFO.txt'
$Verdict = Join-Path $PackageDir 'official-usability-result.txt'

if ($FixtureSelfTest) {
    Remove-Item -LiteralPath $GameDir -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path (Join-Path $GameDir 'logs') | Out-Null
    @(
        'SESSION_ID=fixture'
        ('SESSION_START_UTC=' + (Get-Date).ToUniversalTime().AddSeconds(-1).ToString('o'))
    ) | Set-Content -LiteralPath $SessionFile -Encoding UTF8

    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $InstalledJar) | Out-Null
    'fixture jar bytes' | Set-Content -LiteralPath $InstalledJar -Encoding ASCII
    $fixtureHash = (Get-FileHash -LiteralPath $InstalledJar -Algorithm SHA256).Hash.ToUpperInvariant()
    @(
        'OPENABYSS_WINDOWS_LAUNCHER_BUILD=1'
        ('JAR_SHA256=' + $fixtureHash)
    ) | Set-Content -LiteralPath $BuildInfo -Encoding UTF8

    'fixture latest log' | Set-Content -LiteralPath $LatestLog -Encoding UTF8
    @(
        ((Get-Date).Ticks.ToString() + "	bootstrap-complete")
    ) | Set-Content -LiteralPath $Bootstrap -Encoding UTF8
    @(
        ((Get-Date).Ticks.ToString() + "	menu-no-world-tick")
        ((Get-Date).Ticks.ToString() + "	menu-cleanup-complete:packetBuffer=false:u=0:v=0:a=0")
        ((Get-Date).Ticks.ToString() + "	clickgui-open-request")
        ((Get-Date).Ticks.ToString() + "	clickgui-open-success:Abyss.ui.studio.StudioClickGuiScreen")
        ((Get-Date).Ticks.ToString() + "	entity-player-join-world")
        ((Get-Date).Ticks.ToString() + "	world-ready-tick")
        ((Get-Date).Ticks.ToString() + "	world-module-lifecycle-start")
        ((Get-Date).Ticks.ToString() + "	world-module-lifecycle-complete")
    ) | Set-Content -LiteralPath $Runtime -Encoding UTF8
    @(
        '[ABYSSDIAG] command selftest  = PASS commands=19 primaryAliases=19 moduleSetting=PASS keybind=TRUSTED config=PASS'
        '[ABYSSDIAG] command line      = READY'
        '[ABYSSDIAG] account selftest   = PASS json-roundtrip'
        '[ABYSSDIAG] authservice selftest= PASS loopback daemon-callback timeout=300s'
        '[ABYSSDIAG] cookie selftest    = PASS cookie-parsers daemon-workers'
        '[ABYSSDIAG] accountgui selftest= PASS daemon-worker'
        '[ABYSSDIAG] mslogin selftest   = PASS daemon-worker'
        '[ABYSSDIAG] cookiegui selftest = PASS daemon-worker'
        '[ABYSSDIAG] accesstoken selftest= PASS daemon-workers synchronized-results'
        '[ABYSSDIAG] refreshtoken selftest= PASS daemon-workers synchronized-results'
        '[ABYSSDIAG] altstore selftest  = PASS file-roundtrip upsert'
        '[ABYSSDIAG] fixture'
    ) | Set-Content -LiteralPath $Diag -Encoding UTF8
    1..112 | ForEach-Object { "fixture$($_)	Module$($_)	Misc	0" } |
        Set-Content -LiteralPath $Census -Encoding UTF8
}

function Read-All([string]$Path) {
    if (Test-Path -LiteralPath $Path -PathType Leaf) {
        return [IO.File]::ReadAllText($Path)
    }
    return ''
}

$runtimeText = Read-All $Runtime
$bootstrapText = Read-All $Bootstrap
$moduleFailureText = Read-All $ModuleFailure
$featureFailureText = Read-All $FeatureFailure
$eventFailureText = Read-All $EventFailure
$configFailureText = Read-All $ConfigFailure
$rendererFailureText = Read-All $RendererFailure
$diagText = Read-All $Diag

$sessionStartUtc = $null
if (Test-Path -LiteralPath $SessionFile -PathType Leaf) {
    foreach ($line in @(Get-Content -LiteralPath $SessionFile)) {
        if ($line -match '^SESSION_START_UTC=(.+)$') {
            try { $sessionStartUtc = [DateTime]::Parse($matches[1]).ToUniversalTime() } catch {}
        }
    }
}

function Is-Fresh([string]$Path) {
    if ($null -eq $sessionStartUtc) { return $false }
    if (-not (Test-Path -LiteralPath $Path -PathType Leaf)) { return $false }
    return (Get-Item -LiteralPath $Path).LastWriteTimeUtc -ge $sessionStartUtc.AddSeconds(-2)
}

$expectedJarHash = $null
if (Test-Path -LiteralPath $BuildInfo -PathType Leaf) {
    foreach ($line in @(Get-Content -LiteralPath $BuildInfo)) {
        if ($line -match '^JAR_SHA256=([0-9A-Fa-f]{64})$') {
            $expectedJarHash = $matches[1].ToUpperInvariant()
        }
    }
}
$installedJarHash = if (Test-Path -LiteralPath $InstalledJar -PathType Leaf) {
    (Get-FileHash -LiteralPath $InstalledJar -Algorithm SHA256).Hash.ToUpperInvariant()
} else { $null }

$checks = [ordered]@{
    GameDirectoryExists = Test-Path -LiteralPath $GameDir -PathType Container
    InstalledJarExists = Test-Path -LiteralPath $InstalledJar -PathType Leaf
    BuildInfoJarHash = $null -ne $expectedJarHash
    InstalledJarHashMatch = ($null -ne $expectedJarHash -and $installedJarHash -eq $expectedJarHash)
    SessionMarker = $null -ne $sessionStartUtc
    LatestLogExists = Test-Path -LiteralPath $LatestLog -PathType Leaf
    LatestLogFresh = Is-Fresh $LatestLog
    BootstrapStageFresh = Is-Fresh $Bootstrap
    RuntimeStageFresh = Is-Fresh $Runtime
    BootstrapDiagnosticsFresh = Is-Fresh $Diag
    CensusFresh = Is-Fresh $Census
    BootstrapDiagnostics = -not [string]::IsNullOrWhiteSpace($diagText)
    CommandSelfTest = $diagText.Contains('[ABYSSDIAG] command selftest  = PASS commands=19 primaryAliases=19 moduleSetting=PASS')
    CommandLineReady = $diagText.Contains('[ABYSSDIAG] command line      = READY')
    BootstrapComplete = $bootstrapText.Contains('bootstrap-complete')
    MenuTick = $runtimeText.Contains('menu-no-world-tick')
    MenuCleanup = $runtimeText.Contains('menu-cleanup-complete:packetBuffer=false:u=0:v=0:a=0')
    ClickGuiRequest = $runtimeText.Contains('clickgui-open-request')
    ClickGuiSuccess = $runtimeText.Contains('clickgui-open-success:')
    ClickGuiNullPointer = $runtimeText.Contains('clickgui-open-nullpointer:')
    PlayerJoinWorld = $runtimeText.Contains('entity-player-join-world')
    WorldReadyTick = $runtimeText.Contains('world-ready-tick')
    ModuleLifecycleStart = $runtimeText.Contains('world-module-lifecycle-start')
    ModuleLifecycleComplete = $runtimeText.Contains('world-module-lifecycle-complete')
    ModuleLifecycleFailure = -not [string]::IsNullOrWhiteSpace($moduleFailureText)
    FeatureFailure = -not [string]::IsNullOrWhiteSpace($featureFailureText)
    EventCallbackFailure = -not [string]::IsNullOrWhiteSpace($eventFailureText)
    ConfigSaveFailure = -not [string]::IsNullOrWhiteSpace($configFailureText)
    RendererReloadFailure = -not [string]::IsNullOrWhiteSpace($rendererFailureText)
    AccountSelfTest = $diagText.Contains('[ABYSSDIAG] account selftest   = PASS json-roundtrip')
    AuthServiceSelfTest = $diagText.Contains('[ABYSSDIAG] authservice selftest= PASS loopback daemon-callback timeout=300s')
    CookieSelfTest = $diagText.Contains('[ABYSSDIAG] cookie selftest    = PASS cookie-parsers')
    AccountGuiSelfTest = $diagText.Contains('[ABYSSDIAG] accountgui selftest= PASS daemon-worker')
    MicrosoftLoginSelfTest = $diagText.Contains('[ABYSSDIAG] mslogin selftest   = PASS daemon-worker')
    CookieGuiSelfTest = $diagText.Contains('[ABYSSDIAG] cookiegui selftest = PASS daemon-worker')
    AccessTokenSelfTest = $diagText.Contains('[ABYSSDIAG] accesstoken selftest= PASS daemon-workers synchronized-results')
    RefreshTokenSelfTest = $diagText.Contains('[ABYSSDIAG] refreshtoken selftest= PASS daemon-workers synchronized-results')
    AltStoreSelfTest = $diagText.Contains('[ABYSSDIAG] altstore selftest  = PASS file-roundtrip')
    ModuleCensus112 = $false
}

if (Test-Path -LiteralPath $Census -PathType Leaf) {
    $rows = @(Get-Content -LiteralPath $Census | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
    $checks.ModuleCensus112 = ($rows.Count -eq 112)
}

$crashFiles = if (Test-Path -LiteralPath $CrashDir -PathType Container) {
    @(Get-ChildItem -LiteralPath $CrashDir -File -ErrorAction SilentlyContinue).Count
} else { 0 }

$pass = $checks.GameDirectoryExists -and
        $checks.InstalledJarExists -and
        $checks.BuildInfoJarHash -and
        $checks.InstalledJarHashMatch -and
        $checks.SessionMarker -and
        $checks.LatestLogExists -and
        $checks.LatestLogFresh -and
        $checks.BootstrapStageFresh -and
        $checks.RuntimeStageFresh -and
        $checks.BootstrapDiagnosticsFresh -and
        $checks.CensusFresh -and
        $checks.BootstrapDiagnostics -and
        $checks.CommandSelfTest -and
        $checks.CommandLineReady -and
        $checks.BootstrapComplete -and
        $checks.MenuTick -and
        $checks.MenuCleanup -and
        $checks.ClickGuiRequest -and
        $checks.ClickGuiSuccess -and
        (-not $checks.ClickGuiNullPointer) -and
        $checks.PlayerJoinWorld -and
        $checks.WorldReadyTick -and
        $checks.ModuleLifecycleStart -and
        $checks.ModuleLifecycleComplete -and
        (-not $checks.ModuleLifecycleFailure) -and
        (-not $checks.FeatureFailure) -and
        (-not $checks.EventCallbackFailure) -and
        (-not $checks.ConfigSaveFailure) -and
        (-not $checks.RendererReloadFailure) -and
        $checks.AccountSelfTest -and
        $checks.AuthServiceSelfTest -and
        $checks.CookieSelfTest -and
        $checks.AccountGuiSelfTest -and
        $checks.MicrosoftLoginSelfTest -and
        $checks.CookieGuiSelfTest -and
        $checks.AccessTokenSelfTest -and
        $checks.RefreshTokenSelfTest -and
        $checks.AltStoreSelfTest -and
        $checks.ModuleCensus112 -and
        ($crashFiles -eq 0)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('OPENABYSS_OFFICIAL_USABILITY=' + $(if ($pass) { 'PASS' } else { 'FAIL' }))
foreach ($entry in $checks.GetEnumerator()) {
    $lines.Add($entry.Key.ToUpperInvariant() + '=' + $entry.Value)
}
$lines.Add('CRASH_FILES=' + $crashFiles)
$lines.Add('GAME_DIRECTORY=' + $GameDir)
$lines.Add('INSTALLED_JAR=' + $(if (Test-Path -LiteralPath $InstalledJar -PathType Leaf) { $InstalledJar } else { '<none>' }))
$lines.Add('EXPECTED_JAR_SHA256=' + $(if ($expectedJarHash) { $expectedJarHash } else { '<none>' }))
$lines.Add('INSTALLED_JAR_SHA256=' + $(if ($installedJarHash) { $installedJarHash } else { '<none>' }))
$lines.Add('LATEST_LOG=' + $(if (Test-Path -LiteralPath $LatestLog -PathType Leaf) { $LatestLog } else { '<none>' }))
$lines.Add('RUNTIME_STAGE=' + $(if (Test-Path -LiteralPath $Runtime -PathType Leaf) { $Runtime } else { '<none>' }))
$lines.Add('BOOTSTRAP_STAGE=' + $(if (Test-Path -LiteralPath $Bootstrap -PathType Leaf) { $Bootstrap } else { '<none>' }))
$lines.Add('MODULE_FAILURE=' + $(if (Test-Path -LiteralPath $ModuleFailure -PathType Leaf) { $ModuleFailure } else { '<none>' }))
$lines.Add('FEATURE_FAILURE=' + $(if (Test-Path -LiteralPath $FeatureFailure -PathType Leaf) { $FeatureFailure } else { '<none>' }))
$lines.Add('EVENT_FAILURE=' + $(if (Test-Path -LiteralPath $EventFailure -PathType Leaf) { $EventFailure } else { '<none>' }))
$lines.Add('CONFIG_FAILURE=' + $(if (Test-Path -LiteralPath $ConfigFailure -PathType Leaf) { $ConfigFailure } else { '<none>' }))
$lines.Add('RENDERER_FAILURE=' + $(if (Test-Path -LiteralPath $RendererFailure -PathType Leaf) { $RendererFailure } else { '<none>' }))

$lines | Set-Content -LiteralPath $Verdict -Encoding UTF8
$lines | ForEach-Object { Write-Host $_ }
Write-Host "Verdict=$Verdict"

if ($FixtureSelfTest) {
    if (-not $pass) {
        throw 'Official usability checker fixture self-test did not reach PASS.'
    }
    Write-Host 'OPENABYSS_OFFICIAL_USABILITY_CHECKER_SELFTEST=PASS'
    Remove-Item -LiteralPath $GameDir -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -LiteralPath $Verdict -Force -ErrorAction SilentlyContinue
    exit 0
}

if (-not $pass) {
    Write-Host ''
    Write-Host 'For a full usability PASS, launch the prepared Forge 1.8.9 profile, enter a world,' -ForegroundColor Yellow
    Write-Host 'press RSHIFT so the ClickGUI opens, remain in-world for several seconds, then close Minecraft normally.' -ForegroundColor Yellow
    exit 1
}
