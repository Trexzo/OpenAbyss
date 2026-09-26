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
$EventFailure = Join-Path $GameDir 'abyss-event-failure.txt'
$Diag = Join-Path $GameDir 'abyss-bootstrap-diagnostics.txt'
$Census = Join-Path $GameDir 'abyss-census.tsv'
$LatestLog = Join-Path $GameDir 'logs\latest.log'
$CrashDir = Join-Path $GameDir 'crash-reports'
$SessionFile = Join-Path $GameDir 'openabyss-test-session.txt'
$Verdict = Join-Path $PackageDir 'official-usability-result.txt'

if ($FixtureSelfTest) {
    Remove-Item -LiteralPath $GameDir -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path (Join-Path $GameDir 'logs') | Out-Null
    @(
        'SESSION_ID=fixture'
        ('SESSION_START_UTC=' + (Get-Date).ToUniversalTime().AddSeconds(-1).ToString('o'))
    ) | Set-Content -LiteralPath $SessionFile -Encoding UTF8

    'fixture latest log' | Set-Content -LiteralPath $LatestLog -Encoding UTF8
    @(
        ((Get-Date).Ticks.ToString() + "	bootstrap-complete")
    ) | Set-Content -LiteralPath $Bootstrap -Encoding UTF8
    @(
        ((Get-Date).Ticks.ToString() + "	menu-no-world-tick")
        ((Get-Date).Ticks.ToString() + "	clickgui-open-request")
        ((Get-Date).Ticks.ToString() + "	clickgui-open-success:Abyss.ui.studio.StudioClickGuiScreen")
        ((Get-Date).Ticks.ToString() + "	entity-player-join-world")
        ((Get-Date).Ticks.ToString() + "	world-ready-tick")
        ((Get-Date).Ticks.ToString() + "	world-module-lifecycle-start")
        ((Get-Date).Ticks.ToString() + "	world-module-lifecycle-complete")
    ) | Set-Content -LiteralPath $Runtime -Encoding UTF8
    '[ABYSSDIAG] fixture' | Set-Content -LiteralPath $Diag -Encoding UTF8
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
$eventFailureText = Read-All $EventFailure
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

$checks = [ordered]@{
    GameDirectoryExists = Test-Path -LiteralPath $GameDir -PathType Container
    SessionMarker = $null -ne $sessionStartUtc
    LatestLogExists = Test-Path -LiteralPath $LatestLog -PathType Leaf
    LatestLogFresh = Is-Fresh $LatestLog
    BootstrapStageFresh = Is-Fresh $Bootstrap
    RuntimeStageFresh = Is-Fresh $Runtime
    BootstrapDiagnosticsFresh = Is-Fresh $Diag
    CensusFresh = Is-Fresh $Census
    BootstrapDiagnostics = -not [string]::IsNullOrWhiteSpace($diagText)
    BootstrapComplete = $bootstrapText.Contains('bootstrap-complete')
    MenuTick = $runtimeText.Contains('menu-no-world-tick')
    ClickGuiRequest = $runtimeText.Contains('clickgui-open-request')
    ClickGuiSuccess = $runtimeText.Contains('clickgui-open-success:')
    ClickGuiNullPointer = $runtimeText.Contains('clickgui-open-nullpointer:')
    PlayerJoinWorld = $runtimeText.Contains('entity-player-join-world')
    WorldReadyTick = $runtimeText.Contains('world-ready-tick')
    ModuleLifecycleStart = $runtimeText.Contains('world-module-lifecycle-start')
    ModuleLifecycleComplete = $runtimeText.Contains('world-module-lifecycle-complete')
    ModuleLifecycleFailure = -not [string]::IsNullOrWhiteSpace($moduleFailureText)
    EventCallbackFailure = -not [string]::IsNullOrWhiteSpace($eventFailureText)
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
        $checks.SessionMarker -and
        $checks.LatestLogExists -and
        $checks.LatestLogFresh -and
        $checks.BootstrapStageFresh -and
        $checks.RuntimeStageFresh -and
        $checks.BootstrapDiagnosticsFresh -and
        $checks.CensusFresh -and
        $checks.BootstrapDiagnostics -and
        $checks.BootstrapComplete -and
        $checks.MenuTick -and
        $checks.ClickGuiRequest -and
        $checks.ClickGuiSuccess -and
        (-not $checks.ClickGuiNullPointer) -and
        $checks.PlayerJoinWorld -and
        $checks.WorldReadyTick -and
        $checks.ModuleLifecycleStart -and
        $checks.ModuleLifecycleComplete -and
        (-not $checks.ModuleLifecycleFailure) -and
        (-not $checks.EventCallbackFailure) -and
        $checks.ModuleCensus112 -and
        ($crashFiles -eq 0)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('OPENABYSS_OFFICIAL_USABILITY=' + $(if ($pass) { 'PASS' } else { 'FAIL' }))
foreach ($entry in $checks.GetEnumerator()) {
    $lines.Add($entry.Key.ToUpperInvariant() + '=' + $entry.Value)
}
$lines.Add('CRASH_FILES=' + $crashFiles)
$lines.Add('GAME_DIRECTORY=' + $GameDir)
$lines.Add('LATEST_LOG=' + $(if (Test-Path -LiteralPath $LatestLog -PathType Leaf) { $LatestLog } else { '<none>' }))
$lines.Add('RUNTIME_STAGE=' + $(if (Test-Path -LiteralPath $Runtime -PathType Leaf) { $Runtime } else { '<none>' }))
$lines.Add('BOOTSTRAP_STAGE=' + $(if (Test-Path -LiteralPath $Bootstrap -PathType Leaf) { $Bootstrap } else { '<none>' }))
$lines.Add('MODULE_FAILURE=' + $(if (Test-Path -LiteralPath $ModuleFailure -PathType Leaf) { $ModuleFailure } else { '<none>' }))
$lines.Add('EVENT_FAILURE=' + $(if (Test-Path -LiteralPath $EventFailure -PathType Leaf) { $EventFailure } else { '<none>' }))

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
