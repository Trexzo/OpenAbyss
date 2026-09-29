param(
    [string]$Jdk8,
    [switch]$ExtendedProbes,
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
if ($ExtendedProbes) {
    Write-Host '  3. Stay in-world while the full extended functional chain (through probe92) settles.'
    Write-Host '  4. Press RSHIFT to open the ClickGUI; confirm its labels/text are visibly rendered, then close it.'
    Write-Host '  5. Remain in-world until the deep probe chain has had time to complete.'
    Write-Host '  6. Close Minecraft normally.'
} else {
    Write-Host '  3. Once in-world, press RSHIFT to open the ClickGUI.'
    Write-Host '  4. Confirm the GUI appears with visible labels/text, close it, remain in-world for ~10 seconds.'
    Write-Host '  5. Close Minecraft normally.'
}
Write-Host ''
Write-Host 'The verifier will inspect recorded runtime milestones after exit.' -ForegroundColor DarkGray
Write-Host ''

$invoke = @{ KeepOpen = $true }
if ($Jdk8) { $invoke.Jdk8 = $Jdk8 }
if ($SkipBuild) { $invoke.SkipBuild = $true }
if ($ExtendedProbes) { $invoke.ExtendedProbes = $true }
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

$featureFailureCandidates = @(
    (Join-Path $Root 'run\abyss-feature-failure.txt'),
    (Join-Path $Root 'abyss-feature-failure.txt'),
    (Join-Path $Evidence 'abyss-feature-failure.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$featureFailureFile = $featureFailureCandidates | Select-Object -First 1

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
$featureFailureText = if ($featureFailureFile) { [IO.File]::ReadAllText($featureFailureFile) } else { '' }
$eventFailureText = if ($eventFailureFile) { [IO.File]::ReadAllText($eventFailureFile) } else { '' }
$configFailureText = if ($configFailureFile) { [IO.File]::ReadAllText($configFailureFile) } else { '' }
$rendererFailureText = if ($rendererFailureFile) { [IO.File]::ReadAllText($rendererFailureFile) } else { '' }
$diagCandidates = @(
    (Join-Path $Root 'run\abyss-bootstrap-diagnostics.txt'),
    (Join-Path $Root 'abyss-bootstrap-diagnostics.txt'),
    (Join-Path $Evidence 'abyss-bootstrap-diagnostics.txt')
) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf }
$diagFile = $diagCandidates | Select-Object -First 1
$diagText = if ($diagFile) { [IO.File]::ReadAllText($diagFile) } else { '' }

$checks = [ordered]@{
    BootstrapComplete = $bootstrapText.Contains("bootstrap-complete")
    MenuTick = $runtimeText.Contains("menu-no-world-tick")
    MenuCleanup = $runtimeText.Contains("menu-cleanup-complete:packetBuffer=false:u=0:v=0:a=0")
    ClickGuiRequest = $runtimeText.Contains("clickgui-open-request")
    ClickGuiSuccess = $runtimeText.Contains("clickgui-open-success:")
    ClickGuiNullPointer = $runtimeText.Contains("clickgui-open-nullpointer:")
    PlayerJoinWorld = $runtimeText.Contains("entity-player-join-world")
    WorldReadyTick = $runtimeText.Contains("world-ready-tick")
    ModuleLifecycleStart = $runtimeText.Contains("world-module-lifecycle-start")
    ModuleLifecycleComplete = $runtimeText.Contains("world-module-lifecycle-complete")
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
    ExtendedWorldFunctional = (-not $ExtendedProbes) -or $runtimeText.Contains('world-functional-probe-pass')
    ExtendedCategoryLifecycle9 = (-not $ExtendedProbes) -or $runtimeText.Contains('category-lifecycle-probe-pass:9')
    ExtendedFastPlaceEffect = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('event-functional-probe-effect-pass:FastPlace:rightClickDelay=1') -and
        $runtimeText.Contains('event-functional-probe-restore-state-pass:FastPlace') -and
        $runtimeText.Contains('event-functional-probe-pass:FastPlace:')
    )
    ExtendedNoJumpDelayEffect = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('movement-functional-probe-effect-pass:NoJumpDelay:jumpTicks=1') -and
        $runtimeText.Contains('movement-functional-probe-restore-state-pass:NoJumpDelay') -and
        $runtimeText.Contains('movement-functional-probe-pass:NoJumpDelay:')
    )
    ExtendedNoHitDelayEffect = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('player-functional-probe-effect-pass:NoHitDelay:leftClickCounter=0') -and
        $runtimeText.Contains('player-functional-probe-restore-state-pass:NoHitDelay') -and
        $runtimeText.Contains('player-functional-probe-pass:NoHitDelay:')
    )
    ExtendedKeepSprintEffect = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('combat-functional-probe-effect-pass:KeepSprint:motion=1.0,-0.6:sprinting=true') -and
        $runtimeText.Contains('combat-functional-probe-restore-state-pass:KeepSprint') -and
        $runtimeText.Contains('combat-functional-probe-pass:KeepSprint:')
    )
    ExtendedAmbiencePacketCancel = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('packet-functional-probe-effect-pass:Ambience:S03PacketTimeUpdate:cancelled=true') -and
        $runtimeText.Contains('render-functional-probe-effect-pass:Ambience:worldTime=6000') -and
        $runtimeText.Contains('render-functional-probe-worldtime-restore-pass:Ambience') -and
        $runtimeText.Contains('packet-functional-probe-restore-state-pass:Ambience') -and
        $runtimeText.Contains('packet-functional-probe-pass:Ambience:')
    )
    ExtendedMacro1Action = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('macro-functional-probe-client-send-pass:Macro1:OPENABYSS_MACRO_PROBE_7E51') -and
        $runtimeText.Contains('macro-functional-probe-restore-state-pass:Macro1') -and
        $runtimeText.Contains('macro-functional-probe-pass:Macro1:')
    )
    ExtendedInventoryHudEffect = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('visual-utility-functional-probe-effect-pass:InventoryHUD:cache0=stone*3') -and
        $runtimeText.Contains('visual-utility-functional-probe-restore-state-pass:InventoryHUD') -and
        $runtimeText.Contains('visual-utility-functional-probe-pass:InventoryHUD:')
    )
    ExtendedCommandRuntime = (-not $ExtendedProbes) -or $runtimeText.Contains('command-runtime-probe-pass:commands=7:')
    ExtendedEventBusOwnership = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('category-lifecycle-probe-pass:9') -and
        $runtimeText.Contains('command-runtime-probe-pass:commands=7:')
    )
    ExtendedHighRiskChain92 = (-not $ExtendedProbes) -or (
        $runtimeText.Contains('high-risk-functional-probe92-pass:1') -and
        -not $runtimeText.Contains('high-risk-functional-probe92-fail:')
    )
}

$pass = $checks.BootstrapComplete -and
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
        $checks.ExtendedWorldFunctional -and
        $checks.ExtendedCategoryLifecycle9 -and
        $checks.ExtendedFastPlaceEffect -and
        $checks.ExtendedNoJumpDelayEffect -and
        $checks.ExtendedNoHitDelayEffect -and
        $checks.ExtendedKeepSprintEffect -and
        $checks.ExtendedAmbiencePacketCancel -and
        $checks.ExtendedMacro1Action -and
        $checks.ExtendedInventoryHudEffect -and
        $checks.ExtendedCommandRuntime -and
        $checks.ExtendedEventBusOwnership -and
        $checks.ExtendedHighRiskChain92 -and
        ($null -eq $smokeError)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("OPENABYSS_PHYSICAL_USABILITY=" + $(if ($pass) { 'PASS' } else { 'FAIL' }))
$lines.Add("EXTENDED_PROBES=$ExtendedProbes")
foreach ($entry in $checks.GetEnumerator()) {
    $lines.Add(($entry.Key.ToUpperInvariant()) + "=" + $entry.Value)
}
$lines.Add("RUNTIME_STAGE_FILE=" + $(if ($runtimeFile) { $runtimeFile } else { '<none>' }))
$lines.Add("BOOTSTRAP_STAGE_FILE=" + $(if ($bootstrapFile) { $bootstrapFile } else { '<none>' }))
$lines.Add("MODULE_FAILURE_FILE=" + $(if ($moduleFailureFile) { $moduleFailureFile } else { '<none>' }))
$lines.Add("EVENT_FAILURE_FILE=" + $(if ($eventFailureFile) { $eventFailureFile } else { '<none>' }))
$lines.Add("CONFIG_FAILURE_FILE=" + $(if ($configFailureFile) { $configFailureFile } else { '<none>' }))
$lines.Add("RENDERER_FAILURE_FILE=" + $(if ($rendererFailureFile) { $rendererFailureFile } else { '<none>' }))
$lines.Add("DIAGNOSTICS_FILE=" + $(if ($diagFile) { $diagFile } else { '<none>' }))
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
