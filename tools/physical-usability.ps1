param(
    [string]$Jdk8,
    [switch]$ReferenceBootstrap,
    [switch]$ReferenceRegistry,
    [switch]$Registry97,
    [switch]$Registry103,
    [switch]$ReferenceRuntime,
    [switch]$SkipChatMenu,
    [switch]$SkipCheaterDetector,
    [switch]$SkipAltManager,
    [switch]$SkipBuild
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$Root = Split-Path -Parent $PSScriptRoot
$Smoke = Join-Path $PSScriptRoot 'physical-smoke.ps1'
$Evidence = Join-Path $Root 'physical-smoke-evidence'
$Verdict = Join-Path $Evidence 'PHYSICAL-USABILITY-RESULT.txt'

if (-not (Test-Path -LiteralPath $Smoke -PathType Leaf)) {
    throw "physical-smoke.ps1 not found: $Smoke"
}

Write-Host ''
Write-Host '=== OpenAbyss physical usability certification ===' -ForegroundColor Cyan
Write-Host 'During this run:' -ForegroundColor Yellow
Write-Host '  1. Wait for the Minecraft main menu.'
Write-Host '  2. Enter a singleplayer world or a server.'
Write-Host '  3. Once in-world, press RSHIFT to open the ClickGUI.'
Write-Host '  4. Confirm the GUI appears, close it, remain in-world for ~10 seconds.'
Write-Host '  5. Close Minecraft normally.'
Write-Host ''
Write-Host 'The verifier will inspect recorded runtime milestones after exit.' -ForegroundColor DarkGray
Write-Host ''

$invoke = @{ KeepOpen = $true }
if ($Jdk8) { $invoke.Jdk8 = $Jdk8 }
if ($SkipBuild) { $invoke.SkipBuild = $true }
foreach ($name in @(
    'ReferenceBootstrap','ReferenceRegistry','Registry97','Registry103',
    'ReferenceRuntime','SkipChatMenu','SkipCheaterDetector','SkipAltManager'
)) {
    if (Get-Variable -Name $name -ValueOnly) {
        $invoke[$name] = $true
    }
}

$smokeError = $null
try {
    & $Smoke @invoke
}
catch {
    $smokeError = $_
    Write-Warning $_.Exception.Message
}

$runtimeCandidates = @(
    (Join-Path $Root 'run\abyss-runtime-stage.txt'),
    (Join-Path $Root 'abyss-runtime-stage.txt'),
    (Join-Path $Evidence 'abyss-runtime-stage.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$runtimeFile = $runtimeCandidates | Select-Object -First 1

$bootstrapCandidates = @(
    (Join-Path $Root 'run\abyss-bootstrap-stage.txt'),
    (Join-Path $Root 'abyss-bootstrap-stage.txt'),
    (Join-Path $Evidence 'abyss-bootstrap-stage.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$bootstrapFile = $bootstrapCandidates | Select-Object -First 1

$moduleFailureCandidates = @(
    (Join-Path $Root 'run\abyss-module-failure.txt'),
    (Join-Path $Root 'abyss-module-failure.txt'),
    (Join-Path $Evidence 'abyss-module-failure.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$moduleFailureFile = $moduleFailureCandidates | Select-Object -First 1

$eventFailureCandidates = @(
    (Join-Path $Root 'run\abyss-event-failure.txt'),
    (Join-Path $Root 'abyss-event-failure.txt'),
    (Join-Path $Evidence 'abyss-event-failure.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$eventFailureFile = $eventFailureCandidates | Select-Object -First 1

$configFailureCandidates = @(
    (Join-Path $Root 'run\abyss-config-failure.txt'),
    (Join-Path $Root 'abyss-config-failure.txt'),
    (Join-Path $Evidence 'abyss-config-failure.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$configFailureFile = $configFailureCandidates | Select-Object -First 1

$rendererFailureCandidates = @(
    (Join-Path $Root 'run\abyss-renderer-failure.txt'),
    (Join-Path $Root 'abyss-renderer-failure.txt'),
    (Join-Path $Evidence 'abyss-renderer-failure.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$rendererFailureFile = $rendererFailureCandidates | Select-Object -First 1

$runtimeText = if ($runtimeFile) { [IO.File]::ReadAllText($runtimeFile) } else { '' }
$bootstrapText = if ($bootstrapFile) { [IO.File]::ReadAllText($bootstrapFile) } else { '' }
$moduleFailureText = if ($moduleFailureFile) { [IO.File]::ReadAllText($moduleFailureFile) } else { '' }
$eventFailureText = if ($eventFailureFile) { [IO.File]::ReadAllText($eventFailureFile) } else { '' }
$configFailureText = if ($configFailureFile) { [IO.File]::ReadAllText($configFailureFile) } else { '' }
$rendererFailureText = if ($rendererFailureFile) { [IO.File]::ReadAllText($rendererFailureFile) } else { '' }

$checks = [ordered]@{
    BootstrapComplete = $bootstrapText.Contains("bootstrap-complete")
    MenuTick = $runtimeText.Contains("menu-no-world-tick")
    ClickGuiRequest = $runtimeText.Contains("clickgui-open-request")
    ClickGuiSuccess = $runtimeText.Contains("clickgui-open-success:")
    ClickGuiNullPointer = $runtimeText.Contains("clickgui-open-nullpointer:")
    PlayerJoinWorld = $runtimeText.Contains("entity-player-join-world")
    WorldReadyTick = $runtimeText.Contains("world-ready-tick")
    ModuleLifecycleStart = $runtimeText.Contains("world-module-lifecycle-start")
    ModuleLifecycleComplete = $runtimeText.Contains("world-module-lifecycle-complete")
    ModuleLifecycleFailure = -not [string]::IsNullOrWhiteSpace($moduleFailureText)
    EventCallbackFailure = -not [string]::IsNullOrWhiteSpace($eventFailureText)
    ConfigSaveFailure = -not [string]::IsNullOrWhiteSpace($configFailureText)
    RendererReloadFailure = -not [string]::IsNullOrWhiteSpace($rendererFailureText)
}

$pass = $checks.BootstrapComplete -and
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
        (-not $checks.ConfigSaveFailure) -and
        (-not $checks.RendererReloadFailure) -and
        ($null -eq $smokeError)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("OPENABYSS_PHYSICAL_USABILITY=" + $(if ($pass) { 'PASS' } else { 'FAIL' }))
foreach ($entry in $checks.GetEnumerator()) {
    $lines.Add(($entry.Key.ToUpperInvariant()) + "=" + $entry.Value)
}
$lines.Add("RUNTIME_STAGE_FILE=" + $(if ($runtimeFile) { $runtimeFile } else { '<none>' }))
$lines.Add("BOOTSTRAP_STAGE_FILE=" + $(if ($bootstrapFile) { $bootstrapFile } else { '<none>' }))
$lines.Add("MODULE_FAILURE_FILE=" + $(if ($moduleFailureFile) { $moduleFailureFile } else { '<none>' }))
$lines.Add("EVENT_FAILURE_FILE=" + $(if ($eventFailureFile) { $eventFailureFile } else { '<none>' }))
$lines.Add("CONFIG_FAILURE_FILE=" + $(if ($configFailureFile) { $configFailureFile } else { '<none>' }))
$lines.Add("RENDERER_FAILURE_FILE=" + $(if ($rendererFailureFile) { $rendererFailureFile } else { '<none>' }))
if ($smokeError) {
    $lines.Add("SMOKE_ERROR=" + $smokeError.Exception.Message)
}

$lines | Set-Content -LiteralPath $Verdict -Encoding UTF8

Write-Host ''
if ($pass) {
    Write-Host 'OPENABYSS_PHYSICAL_USABILITY=PASS' -ForegroundColor Green
} else {
    Write-Host 'OPENABYSS_PHYSICAL_USABILITY=FAIL' -ForegroundColor Red
}
foreach ($line in $lines) { Write-Host $line }
Write-Host "Verdict=$Verdict"

if (-not $pass) {
    exit 1
}
