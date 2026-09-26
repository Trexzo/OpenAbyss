param(
    [string]$PackageDir = (Split-Path -Parent $MyInvocation.MyCommand.Path)
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$PackageDir = [IO.Path]::GetFullPath($PackageDir)
$GameDir = Join-Path $PackageDir 'official-game'
$Runtime = Join-Path $GameDir 'abyss-runtime-stage.txt'
$Bootstrap = Join-Path $GameDir 'abyss-bootstrap-stage.txt'
$ModuleFailure = Join-Path $GameDir 'abyss-module-failure.txt'
$Diag = Join-Path $GameDir 'abyss-bootstrap-diagnostics.txt'
$Census = Join-Path $GameDir 'abyss-census.tsv'
$LatestLog = Join-Path $GameDir 'logs\latest.log'
$CrashDir = Join-Path $GameDir 'crash-reports'
$Verdict = Join-Path $PackageDir 'official-usability-result.txt'

function Read-All([string]$Path) {
    if (Test-Path -LiteralPath $Path -PathType Leaf) {
        return [IO.File]::ReadAllText($Path)
    }
    return ''
}

$runtimeText = Read-All $Runtime
$bootstrapText = Read-All $Bootstrap
$moduleFailureText = Read-All $ModuleFailure
$diagText = Read-All $Diag

$checks = [ordered]@{
    GameDirectoryExists = Test-Path -LiteralPath $GameDir -PathType Container
    LatestLogExists = Test-Path -LiteralPath $LatestLog -PathType Leaf
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
        $checks.LatestLogExists -and
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

$lines | Set-Content -LiteralPath $Verdict -Encoding UTF8
$lines | ForEach-Object { Write-Host $_ }
Write-Host "Verdict=$Verdict"

if (-not $pass) {
    Write-Host ''
    Write-Host 'For a full usability PASS, launch the prepared Forge 1.8.9 profile, enter a world,' -ForegroundColor Yellow
    Write-Host 'press RSHIFT so the ClickGUI opens, remain in-world for several seconds, then close Minecraft normally.' -ForegroundColor Yellow
    exit 1
}
