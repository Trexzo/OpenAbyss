param(
    [string]$Jdk8,
    [int]$RunSeconds = 180,
    [switch]$SkipBuild,
    [switch]$KeepOpen,
    [switch]$DevRuntime,
    [switch]$ExtendedProbes,
    [switch]$ReferenceBootstrap,
    [switch]$ReferenceRegistry,
    [switch]$Registry97,
    [switch]$Registry103,
    [switch]$ReferenceRuntime,
    [switch]$SkipChatMenu,
    [switch]$SkipCheaterDetector,
    [switch]$SkipAltManager
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$UseSkipChatMenu = $SkipChatMenu -or $ReferenceBootstrap -or $ReferenceRuntime
$UseSkipCheaterDetector = $SkipCheaterDetector -or $ReferenceBootstrap -or $ReferenceRuntime
$UseSkipAltManager = $SkipAltManager -or $ReferenceBootstrap -or $ReferenceRuntime
$UseReferenceBootstrap = $UseSkipChatMenu -and $UseSkipCheaterDetector -and $UseSkipAltManager

$RegistrySwitchCount = @(@($ReferenceRegistry,$Registry97,$Registry103,$ReferenceRuntime) | Where-Object { $_ }).Count
if ($RegistrySwitchCount -gt 1) {
    throw 'Choose only one registry compatibility stage: -ReferenceRegistry (92), -Registry97, -Registry103, or -ReferenceRuntime.'
}
$RegistryTarget = if ($ReferenceRegistry -or $ReferenceRuntime) { 92 } elseif ($Registry97) { 97 } elseif ($Registry103) { 103 } else { 112 }

if ($ExtendedProbes -and ($RegistryTarget -ne 112 -or $UseReferenceBootstrap)) {
    throw 'ExtendedProbes requires the full 112-module/default bootstrap runtime; do not combine it with reference compatibility switches.'
}

$Root = Split-Path -Parent $PSScriptRoot
$Evidence = Join-Path $Root 'physical-smoke-evidence'
$GradleCache = Join-Path $env:LOCALAPPDATA 'OpenAbyss-Recovery'
$GradleZip = Join-Path $GradleCache 'gradle-2.14.1-bin.zip'
$GradleHome = Join-Path $GradleCache 'gradle-2.14.1'
$Gradle = Join-Path $GradleHome 'bin\gradle.bat'
$GradleUserHome = Join-Path $GradleCache 'gradle-user-home'

New-Item -ItemType Directory -Force -Path $Evidence,$GradleCache,$GradleUserHome | Out-Null
Remove-Item -LiteralPath @(
    (Join-Path $Evidence 'PASS.txt'),
    (Join-Path $Evidence 'RESULT.txt'),
    (Join-Path $Evidence 'FAILED-contracts.txt'),
    (Join-Path $Evidence 'FAILED-runtime-exit.txt')
) -Force -ErrorAction SilentlyContinue

function Require([bool]$Condition, [string]$Message) {
    if (-not $Condition) { throw $Message }
}

Push-Location $Root
try {
    Require (Test-Path -LiteralPath (Join-Path $Root '.git')) "Run this from a Git checkout of OpenAbyss."

    $Branch = (& git branch --show-current).Trim()
    $Head = (& git rev-parse HEAD).Trim()
    Require ($LASTEXITCODE -eq 0 -and $Head) 'git rev-parse HEAD failed.'
    Require ($Branch -eq 'recovery/r38-compile-pass') ("Expected recovery/r38-compile-pass, current branch is '" + $Branch + "'.")

    $Status = @(& git status --porcelain --untracked-files=no)
    Require ($LASTEXITCODE -eq 0) 'git status failed.'
    $DirtyTracked = $Status.Count -gt 0
    if ($DirtyTracked) {
        Write-Warning 'Tracked working-tree changes are present. Physical-smoke evidence will record dirty=true and is not release-authoritative.'
    }

    function Get-Java8Version([string]$Home) {
        if (-not $Home) { return $null }
        $Exe = Join-Path $Home 'bin\java.exe'
        if (-not (Test-Path -LiteralPath $Exe -PathType Leaf)) { return $null }
        try {
            $Version = (& $Exe -version 2>&1 | Out-String)
            if ($Version -match 'version "1\.8\.0_') { return $Version }
        }
        catch {
        }
        return $null
    }

    if (-not $Jdk8) {
        $Candidates = New-Object System.Collections.Generic.List[string]
        if ($env:JAVA_HOME) { $Candidates.Add($env:JAVA_HOME) }

        foreach ($Pattern in @(
            (Join-Path $env:ProgramFiles 'Eclipse Adoptium\jdk-8*'),
            (Join-Path $env:ProgramFiles 'Amazon Corretto\jdk8*'),
            (Join-Path $env:ProgramFiles 'BellSoft\LibericaJDK-8*'),
            (Join-Path $env:ProgramFiles 'Java\jdk1.8*')
        )) {
            foreach ($Dir in @(Get-ChildItem -Path $Pattern -Directory -ErrorAction SilentlyContinue | Sort-Object Name -Descending)) {
                $Candidates.Add($Dir.FullName)
            }
        }

        foreach ($Candidate in $Candidates) {
            if (Get-Java8Version $Candidate) {
                $Jdk8 = $Candidate
                break
            }
        }
    }

    if (-not $Jdk8) {
        throw 'No JDK 8 installation was found. Install Temurin/Corretto/Liberica JDK 8 or pass -Jdk8 "C:\Path\To\jdk8".'
    }

    $Java = Join-Path $Jdk8 'bin\java.exe'
    Require (Test-Path -LiteralPath $Java -PathType Leaf) "java.exe not found under JDK path: $Jdk8"

    $JavaVersion = Get-Java8Version $Jdk8
    Require ($null -ne $JavaVersion) ("Expected Java 8 under " + $Jdk8)
    Write-Host "JDK8=$Jdk8"

    if (-not (Test-Path -LiteralPath $Gradle)) {
        Write-Host 'Downloading Gradle 2.14.1...'
        & curl.exe -L --fail --silent --show-error 'https://services.gradle.org/distributions/gradle-2.14.1-bin.zip' -o $GradleZip
        if ($LASTEXITCODE -ne 0) { throw "Gradle download failed with exit code $LASTEXITCODE" }
        Expand-Archive -LiteralPath $GradleZip -DestinationPath $GradleCache -Force
    }
    Require (Test-Path -LiteralPath $Gradle) "Gradle 2.14.1 missing: $Gradle"

    $env:JAVA_HOME = $Jdk8
    $env:PATH = (Join-Path $Jdk8 'bin') + ';' + $env:PATH
    $env:GRADLE_USER_HOME = $GradleUserHome
    $env:GITHUB_TOKEN = $null
    $env:ACTIONS_RUNTIME_TOKEN = $null
    $env:ACTIONS_ID_TOKEN_REQUEST_TOKEN = $null
    $env:ACTIONS_ID_TOKEN_REQUEST_URL = $null
    $env:ABYSS_PAYLOAD_KEY = $null
    $ExtendedProbeOptions = ''
    if ($ExtendedProbes) {
        $ExtendedProbeArgs = @(
            '-Dabyss.worldFunctionalProbe=true'
            '-Dabyss.categoryLifecycleProbe=true'
            '-Dabyss.promotedRegistryProbe=true'
            '-Dabyss.eventFunctionalProbe=true'
            '-Dabyss.movementFunctionalProbe=true'
            '-Dabyss.playerFunctionalProbe=true'
            '-Dabyss.combatFunctionalProbe=true'
            '-Dabyss.packetFunctionalProbe=true'
            '-Dabyss.macroFunctionalProbe=true'
            '-Dabyss.visualUtilityFunctionalProbe=true'
            '-Dabyss.highRiskFunctionalProbe=true'
        )
        foreach ($ProbeIndex in 2..69) {
            $ExtendedProbeArgs += "-Dabyss.highRiskFunctionalProbe${ProbeIndex}=true"
        }
        $ExtendedProbeArgs += '-Dabyss.commandRuntimeProbe=true'
        $ExtendedProbeOptions = ' ' + ($ExtendedProbeArgs -join ' ')
    }

    $env:JAVA_TOOL_OPTIONS = '-Dabyss.runtimeSelfTest=true' +
        $ExtendedProbeOptions +
        $(if ($UseSkipChatMenu) { ' -Dabyss.skipChatMenu=true' } else { '' }) +
        $(if ($UseSkipCheaterDetector) { ' -Dabyss.skipCheaterDetector=true' } else { '' }) +
        $(if ($UseSkipAltManager) { ' -Dabyss.skipAltManager=true' } else { '' }) +
        $(if ($RegistryTarget -ne 112) { " -Dabyss.referenceRegistryCount=$RegistryTarget" } else { '' })


    $Meta = @(
        "timestamp=$(Get-Date -Format o)"
        "branch=$Branch"
        "head=$Head"
        "java_home=$Jdk8"
        "gradle=$Gradle"
        "keep_open=$KeepOpen"
        "run_seconds=$RunSeconds"
        "runtime_mode=$(if ($DevRuntime) { 'dev-source' } else { 'packaged-jar' })"
        "extended_probes=$ExtendedProbes"
        "extended_probe_max=$(if ($ExtendedProbes) { 69 } else { 0 })"
        "reference_bootstrap=$UseReferenceBootstrap"
        "skip_chat_menu=$UseSkipChatMenu"
        "skip_cheater_detector=$UseSkipCheaterDetector"
        "skip_alt_manager=$UseSkipAltManager"
        "registry_target=$RegistryTarget"
        "dirty_tracked=$DirtyTracked"
    )
    $Meta | Set-Content -LiteralPath (Join-Path $Evidence 'environment.txt') -Encoding UTF8
    $JavaVersion | Add-Content -LiteralPath (Join-Path $Evidence 'environment.txt') -Encoding UTF8

    if (-not $SkipBuild) {
        Write-Host 'Building recovered client...'
        & $Gradle --no-daemon clean build
        if ($LASTEXITCODE -ne 0) { throw "clean build failed: $LASTEXITCODE" }

        Write-Host 'Preparing Minecraft 1.8 assets...'
        & $Gradle --no-daemon getAssets
        if ($LASTEXITCODE -ne 0) { throw "getAssets failed: $LASTEXITCODE" }
    }

    $Jar = Get-ChildItem -LiteralPath (Join-Path $Root 'build\libs') -Filter 'OpenAbyss-*.jar' -File | Sort-Object LastWriteTimeUtc -Descending | Select-Object -First 1
    Require ($null -ne $Jar) 'No OpenAbyss JAR found under build\libs.'

    $JarHash = (Get-FileHash -LiteralPath $Jar.FullName -Algorithm SHA256).Hash
    @(
        "jar=$($Jar.FullName)"
        "jar_sha256=$JarHash"
        "jar_bytes=$($Jar.Length)"
    ) | Add-Content -LiteralPath (Join-Path $Evidence 'environment.txt') -Encoding UTF8

    $Stdout = Join-Path $Evidence 'runClient.stdout.log'
    $Stderr = Join-Path $Evidence 'runClient.stderr.log'
    $RuntimeExit = 'RUNNING_OR_TIMEOUT'
    $BootstrapStage = Join-Path $Root 'abyss-bootstrap-stage.txt'
    $BootstrapStageRun = Join-Path $Root 'run\abyss-bootstrap-stage.txt'
    $RuntimeStage = Join-Path $Root 'abyss-runtime-stage.txt'
    $RuntimeStageRun = Join-Path $Root 'run\abyss-runtime-stage.txt'
    $ModuleFailure = Join-Path $Root 'abyss-module-failure.txt'
    $ModuleFailureRun = Join-Path $Root 'run\abyss-module-failure.txt'
    $FeatureFailure = Join-Path $Root 'abyss-feature-failure.txt'
    $FeatureFailureRun = Join-Path $Root 'run\abyss-feature-failure.txt'
    $EventFailure = Join-Path $Root 'abyss-event-failure.txt'
    $EventFailureRun = Join-Path $Root 'run\abyss-event-failure.txt'
    $ConfigFailure = Join-Path $Root 'abyss-config-failure.txt'
    $ConfigFailureRun = Join-Path $Root 'run\abyss-config-failure.txt'
    $RendererFailure = Join-Path $Root 'abyss-renderer-failure.txt'
    $RendererFailureRun = Join-Path $Root 'run\abyss-renderer-failure.txt'
    $NetworkStage = Join-Path $Root 'abyss-network-stage.txt'
    $NetworkStageRun = Join-Path $Root 'run\abyss-network-stage.txt'
    Remove-Item -LiteralPath $Stdout,$Stderr,$BootstrapStage,$BootstrapStageRun,$RuntimeStage,$RuntimeStageRun,$ModuleFailure,$ModuleFailureRun,$FeatureFailure,$FeatureFailureRun,$EventFailure,$EventFailureRun,$ConfigFailure,$ConfigFailureRun,$RendererFailure,$RendererFailureRun,$NetworkStage,$NetworkStageRun -Force -ErrorAction SilentlyContinue

    $RunArgs = @('--offline','--no-daemon')
    if (-not $DevRuntime) {
        $RunArgs += '-PabyssPackaged=true'
    }
    $RunArgs += 'runClient'

    if ($KeepOpen) {
        Write-Host ('Launching interactive ' + $(if ($DevRuntime) { 'dev-source' } else { 'packaged-JAR' }) + ' runClient. Close Minecraft normally when testing is complete.')
    } else {
        Write-Host ('Launching ' + $(if ($DevRuntime) { 'dev-source' } else { 'packaged-JAR' }) + " runClient for up to $RunSeconds seconds...")
    }
    $P = Start-Process -FilePath $Gradle -ArgumentList $RunArgs -WorkingDirectory $Root -RedirectStandardOutput $Stdout -RedirectStandardError $Stderr -PassThru

    $UnexpectedExitCode = $null
    if ($KeepOpen) {
        while (-not $P.HasExited) {
            Start-Sleep -Seconds 2
            $P.Refresh()
        }
        $P.WaitForExit()
        $P.Refresh()
        Write-Host "RUNCLIENT_EXIT=$($P.ExitCode)"
        $RuntimeExit = [string]$P.ExitCode
        if ($P.ExitCode -ne 0) {
            $UnexpectedExitCode = $P.ExitCode
        }
    } else {
        $Deadline = (Get-Date).AddSeconds($RunSeconds)
        while (-not $P.HasExited -and (Get-Date) -lt $Deadline) {
            Start-Sleep -Seconds 2
            $P.Refresh()
        }

        if (-not $P.HasExited) {
            Write-Host 'Stopping runClient after evidence window.'
            $RuntimeExit = 'TIMEOUT_WINDOW_REACHED'
            & taskkill.exe /PID $P.Id /T /F | Out-Null
            Start-Sleep -Seconds 2
        } else {
            $P.WaitForExit()
            $P.Refresh()
            Write-Host "RUNCLIENT_EXIT=$($P.ExitCode)"
            $RuntimeExit = [string]$P.ExitCode
            if ($P.ExitCode -ne 0) {
                $UnexpectedExitCode = $P.ExitCode
            }
        }
    }

    $CrashEvidence = Join-Path $Evidence 'crash-evidence'
    Remove-Item -LiteralPath $CrashEvidence -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path $CrashEvidence | Out-Null

    foreach ($CrashFile in @(
        Get-ChildItem -LiteralPath (Join-Path $Root 'run\crash-reports') -File -ErrorAction SilentlyContinue
        Get-ChildItem -LiteralPath (Join-Path $Root 'run') -Filter 'hs_err_pid*.log' -File -ErrorAction SilentlyContinue
        Get-ChildItem -LiteralPath $Root -Filter 'hs_err_pid*.log' -File -ErrorAction SilentlyContinue
    )) {
        Copy-Item -LiteralPath $CrashFile.FullName -Destination $CrashEvidence -Force
    }

    $Sources = @(
        (Join-Path $Root 'run\logs\latest.log'),
        (Join-Path $Root 'run\abyss-bootstrap-diagnostics.txt'),
        (Join-Path $Root 'abyss-bootstrap-diagnostics.txt'),
        (Join-Path $Root 'run\abyss-census.tsv'),
        (Join-Path $Root 'abyss-census.tsv'),
        (Join-Path $Root 'run\abyss-bootstrap-stage.txt'),
        (Join-Path $Root 'abyss-bootstrap-stage.txt'),
        (Join-Path $Root 'run\abyss-runtime-stage.txt'),
        (Join-Path $Root 'abyss-runtime-stage.txt'),
        (Join-Path $Root 'run\abyss-module-failure.txt'),
        (Join-Path $Root 'abyss-module-failure.txt'),
        (Join-Path $Root 'run\abyss-feature-failure.txt'),
        (Join-Path $Root 'abyss-feature-failure.txt'),
        (Join-Path $Root 'run\abyss-event-failure.txt'),
        (Join-Path $Root 'abyss-event-failure.txt'),
        (Join-Path $Root 'run\abyss-config-failure.txt'),
        (Join-Path $Root 'abyss-config-failure.txt'),
        (Join-Path $Root 'run\abyss-renderer-failure.txt'),
        (Join-Path $Root 'abyss-renderer-failure.txt'),
        (Join-Path $Root 'run\abyss-network-stage.txt'),
        (Join-Path $Root 'abyss-network-stage.txt'),
        (Join-Path $env:TEMP 'abyss-inject.log')
    )
    foreach ($Source in $Sources) {
        if (Test-Path -LiteralPath $Source) {
            Copy-Item -LiteralPath $Source -Destination $Evidence -Force
        }
    }

    $Diag = @(
        (Join-Path $Root 'run\abyss-bootstrap-diagnostics.txt'),
        (Join-Path $Root 'abyss-bootstrap-diagnostics.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1

    Require ($null -ne $Diag) 'No bootstrap diagnostics were produced.'
    $DiagText = [IO.File]::ReadAllText($Diag)

    $LatestLog = Join-Path $Root 'run\logs\latest.log'
    $Combined = ''
    foreach ($LogPath in @($Stdout,$Stderr,$LatestLog,(Join-Path $env:TEMP 'abyss-inject.log'))) {
        if ($LogPath -and (Test-Path -LiteralPath $LogPath)) {
            $Combined += [Environment]::NewLine + [IO.File]::ReadAllText($LogPath)
        }
    }
    if (-not $DevRuntime) {
        Require ($Combined.Contains('ABYSS_PACKAGED_RUNTIME_JAR=')) 'Packaged runtime marker was not observed.'
        Require ($Combined.Contains('ABYSS_PACKAGED_RUNTIME_DEV_OUTPUTS_PRESENT=0')) 'Packaged runtime dev-output isolation marker is missing.'
    }

    $ExpectedModuleCount = $RegistryTarget
    $ExpectedToggleable = $RegistryTarget - 3

    $ExpectedReferenceBootstrap = $(if ($UseReferenceBootstrap) { 'true' } else { 'false' })
    $ExpectedSkipChatMenu = $(if ($UseSkipChatMenu) { 'true' } else { 'false' })
    $ExpectedSkipCheaterDetector = $(if ($UseSkipCheaterDetector) { 'true' } else { 'false' })
    $ExpectedSkipAltManager = $(if ($UseSkipAltManager) { 'true' } else { 'false' })

    $Required = @(
        "[ABYSSDIAG] reference bootstrap = $ExpectedReferenceBootstrap",
        "[ABYSSDIAG] skip chat/menu       = $ExpectedSkipChatMenu",
        "[ABYSSDIAG] skip cheater         = $ExpectedSkipCheaterDetector",
        "[ABYSSDIAG] skip altmanager      = $ExpectedSkipAltManager",
        "[ABYSSDIAG] registry target      = $ExpectedModuleCount",
        "[ABYSSDIAG] module count     = $ExpectedModuleCount of $ExpectedModuleCount OK",
        '[ABYSSDIAG] config writable   = writable=true',
        "[ABYSSDIAG] module usability   = toggleable=$ExpectedToggleable stockDisabled=3 invalid=0 nullSettings=0",
        '[ABYSSDIAG] eventbus selftest  = PASS',
        '[ABYSSDIAG] module selftest    = PASS',
        '[ABYSSDIAG] config selftest    = PASS',
        '[ABYSSDIAG] session selftest   = PASS',
        '[ABYSSDIAG] command selftest  = PASS commands=19 primaryAliases=19 moduleSetting=PASS',
        '[ABYSSDIAG] command line      = READY',
        '[ABYSSDIAG] clickgui selftest = PASS mode=',
        '[ABYSSDIAG] altmenu selftest  = PASS',
        "[ABYSSDIAG] tD.S (list)      = $ExpectedModuleCount",
        '[ABYSSDIAG] zu_3.B VESTIGE   = live',
        '[ABYSSDIAG] zu_3.Y STUDIO    = live',
        '[ABYSSDIAG] zu_3.F RAVEN     = live'
    )

    $Failures = @()
    foreach ($Needle in $Required) {
        if (-not $DiagText.Contains($Needle)) { $Failures += $Needle }
    }

    if ($ExtendedProbes) {
        $RuntimeEvidence = @(
            (Join-Path $Root 'run\abyss-runtime-stage.txt'),
            (Join-Path $Root 'abyss-runtime-stage.txt')
        ) | Where-Object { Test-Path -LiteralPath $_ -PathType Leaf } | Select-Object -First 1
        if (-not $RuntimeEvidence) {
            $Failures += 'extended-probes:runtime-stage-missing'
        }
        else {
            $RuntimeEvidenceText = [IO.File]::ReadAllText($RuntimeEvidence)
            foreach ($Needle in @(
                'world-functional-probe-pass',
                'category-lifecycle-probe-pass:9',
                'event-functional-probe-effect-pass:FastPlace:rightClickDelay=1',
                'event-functional-probe-restore-state-pass:FastPlace',
                'event-functional-probe-pass:FastPlace:',
                'movement-functional-probe-effect-pass:NoJumpDelay:jumpTicks=1',
                'movement-functional-probe-restore-state-pass:NoJumpDelay',
                'movement-functional-probe-pass:NoJumpDelay:',
                'player-functional-probe-effect-pass:NoHitDelay:leftClickCounter=0',
                'player-functional-probe-restore-state-pass:NoHitDelay',
                'player-functional-probe-pass:NoHitDelay:',
                'combat-functional-probe-effect-pass:KeepSprint:motion=1.0,-0.6:sprinting=true',
                'combat-functional-probe-restore-state-pass:KeepSprint',
                'combat-functional-probe-pass:KeepSprint:',
                'packet-functional-probe-effect-pass:Ambience:S03PacketTimeUpdate:cancelled=true',
                'render-functional-probe-effect-pass:Ambience:worldTime=6000',
                'render-functional-probe-worldtime-restore-pass:Ambience',
                'packet-functional-probe-restore-state-pass:Ambience',
                'packet-functional-probe-pass:Ambience:',
                'macro-functional-probe-client-send-pass:Macro1:OPENABYSS_MACRO_PROBE_7E51',
                'macro-functional-probe-restore-state-pass:Macro1',
                'macro-functional-probe-pass:Macro1:',
                'visual-utility-functional-probe-effect-pass:InventoryHUD:cache0=stone*3',
                'visual-utility-functional-probe-restore-state-pass:InventoryHUD',
                'visual-utility-functional-probe-pass:InventoryHUD:',
                'promoted-registry-probe-pass:20',
                'high-risk-functional-probe-module-pass:Velocity',
                'high-risk-functional-probe-module-pass:NoSlow',
                'high-risk-functional-probe-module-pass:Blink',
                'high-risk-functional-probe-module-pass:Speed',
                'high-risk-functional-probe-pass:4',
                'high-risk-functional-probe2-module-pass:Sprint',
                'high-risk-functional-probe2-module-pass:BackTrack',
                'high-risk-functional-probe2-module-pass:NoInteract',
                'high-risk-functional-probe2-pass:3',
                'high-risk-functional-probe3-effect-pass:WTap:forward=0.0:strafe=0.0',
                'high-risk-functional-probe3-restore-pass:WTap',
                'high-risk-functional-probe3-pass:1',
                'high-risk-functional-probe4-effect-pass:ChestESP:visible=true',
                'high-risk-functional-probe4-effect-pass:ChestESP:ignoreOpened=true:hidden=true',
                'high-risk-functional-probe4-restore-pass:ChestESP',
                'high-risk-functional-probe4-pass:1',
                'high-risk-functional-probe5-effect-pass:AntiDebuff:disabled:blindnessVisible=true:confusionVisible=true',
                'high-risk-functional-probe5-effect-pass:AntiDebuff:enabled:blindnessVisible=false:confusionVisible=false',
                'high-risk-functional-probe5-restore-pass:AntiDebuff:',
                'high-risk-functional-probe5-pass:1',
                'high-risk-functional-probe6-effect-pass:NoHurtCam:disabled:identityDelta=',
                'high-risk-functional-probe6-effect-pass:NoHurtCam:enabled:identityDelta=0.0:effect=0',
                'high-risk-functional-probe6-restore-pass:NoHurtCam:',
                'high-risk-functional-probe6-pass:1',
                'high-risk-functional-probe7-effect-pass:BarrierVisible:disabled:cancelled=false:returnValue=71',
                'high-risk-functional-probe7-effect-pass:BarrierVisible:enabled:cancelled=true:returnValue=3',
                'high-risk-functional-probe7-restore-pass:BarrierVisible:',
                'high-risk-functional-probe7-pass:1',
                'high-risk-functional-probe8-effect-pass:ViewClip:disabled:takeover=false:result=null',
                'high-risk-functional-probe8-effect-pass:ViewClip:enabled:takeover=true:result=',
                'high-risk-functional-probe8-restore-pass:ViewClip:',
                'high-risk-functional-probe8-pass:1',
                'high-risk-functional-probe9-effect-pass:Animations:disabled:cancelled=false:noRotations=true',
                'high-risk-functional-probe9-effect-pass:Animations:enabled:cancelled=true:noRotations=true',
                'high-risk-functional-probe9-restore-pass:Animations:',
                'high-risk-functional-probe9-pass:1',
                'high-risk-functional-probe10-baseline-pass:Timer:timerSpeed=',
                'high-risk-functional-probe10-effect-pass:Timer:enabled:timerSpeed=1.37',
                'high-risk-functional-probe10-effect-pass:Timer:disabled:timerSpeed=',
                'high-risk-functional-probe10-restore-pass:Timer:',
                'high-risk-functional-probe10-pass:1',
                'high-risk-functional-probe11-effect-pass:Chams:disabled:pre=false:post=false',
                'high-risk-functional-probe11-effect-pass:Chams:enabled:pre=true:post=false',
                'high-risk-functional-probe11-effect-pass:Chams:restored:',
                'high-risk-functional-probe11-restore-pass:Chams:',
                'high-risk-functional-probe11-pass:1',
                'high-risk-functional-probe12-effect-pass:Freelook:enabled:view=1:active=true:yaw=37.25:pitch=-18.5',
                'high-risk-functional-probe12-effect-pass:Freelook:disabled:view=0:active=false:yaw=37.25:pitch=-18.5',
                'high-risk-functional-probe12-restore-pass:Freelook:',
                'high-risk-functional-probe12-pass:1',
                'high-risk-functional-probe55-pass:1',
                'high-risk-functional-probe56-pass:1',
                'high-risk-functional-probe57-pass:1',
                'high-risk-functional-probe58-pass:2',
                'high-risk-functional-probe59-pass:1',
                'high-risk-functional-probe60-pass:1',
                'high-risk-functional-probe61-pass:1',
                'high-risk-functional-probe62-pass:1',
                'high-risk-functional-probe63-pass:1',
                'high-risk-functional-probe64-pass:1',
                'high-risk-functional-probe65-pass:1',
                'high-risk-functional-probe66-pass:1',
                'high-risk-functional-probe67-pass:1',
                'high-risk-functional-probe68-pass:1',
                'high-risk-functional-probe69-pass:1',
                'command-runtime-probe-pass:commands=7:'
            )) {
                if (-not $RuntimeEvidenceText.Contains($Needle)) {
                    $Failures += "extended-probes:missing:$Needle"
                }
            }
            foreach ($ModuleName in @(
                'HitBox','Notifications','Macro1','NameHider','NoJumpDelay',
                'NoHitDelay','NoHurtCam','Tracers','AutoTool'
            )) {
                $Needle = "category-lifecycle-probe-module-pass:${ModuleName}:"
                if (-not $RuntimeEvidenceText.Contains($Needle)) {
                    $Failures += "extended-probes:missing:$Needle"
                }
            }
            $PromotedPasses = ([regex]::Matches($RuntimeEvidenceText, 'promoted-registry-probe-module-pass:')).Count
            if ($PromotedPasses -ne 20) {
                $Failures += "extended-probes:promoted-module-pass-count:$PromotedPasses/20"
            }
            foreach ($FailurePrefix in @(
                'high-risk-functional-probe-fail:',
                'high-risk-functional-probe2-fail:',
                'high-risk-functional-probe3-fail:',
                'high-risk-functional-probe4-fail:',
                'high-risk-functional-probe5-fail:',
                'high-risk-functional-probe6-fail:',
                'high-risk-functional-probe7-fail:',
                'high-risk-functional-probe8-fail:',
                'high-risk-functional-probe9-fail:',
                'high-risk-functional-probe10-fail:',
                'high-risk-functional-probe11-fail:',
                'high-risk-functional-probe12-fail:',
                'promoted-registry-probe-fail:'
            )) {
                if ($RuntimeEvidenceText.Contains($FailurePrefix)) {
                    $Failures += "extended-probes:failure-marker:$FailurePrefix"
                }
            }
        }

    }

    $Graphics = @()
    if (Test-Path -LiteralPath $LatestLog) {
        $LogText = [IO.File]::ReadAllText($LatestLog)
        foreach ($Pattern in @('OpenGL: .*','LWJGL Version: .*','Setting user: .*')) {
            $M = [regex]::Match($LogText,$Pattern)
            if ($M.Success) { $Graphics += $M.Value }
        }
    }
    $Graphics | Set-Content -LiteralPath (Join-Path $Evidence 'graphics-session.txt') -Encoding UTF8

    $StageCandidates = @(
        (Join-Path $Root 'run\abyss-bootstrap-stage.txt'),
        (Join-Path $Root 'abyss-bootstrap-stage.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $StageFile = $StageCandidates | Select-Object -First 1
    $LastStage = '<none>'
    if ($StageFile) {
        $StageLines = @(Get-Content -LiteralPath $StageFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($StageLines.Count -gt 0) {
            $LastStage = ($StageLines[-1] -split "\t",2)[-1]
        }
    }
    $RuntimeStageCandidates = @(
        (Join-Path $Root 'run\abyss-runtime-stage.txt'),
        (Join-Path $Root 'abyss-runtime-stage.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $RuntimeStageFile = $RuntimeStageCandidates | Select-Object -First 1
    $LastRuntimeStage = '<none>'
    if ($RuntimeStageFile) {
        $RuntimeStageLines = @(Get-Content -LiteralPath $RuntimeStageFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($RuntimeStageLines.Count -gt 0) {
            $LastRuntimeStage = ($RuntimeStageLines[-1] -split "\t",2)[-1]
        }
    }

    $ModuleFailureCandidates = @(
        (Join-Path $Root 'run\abyss-module-failure.txt'),
        (Join-Path $Root 'abyss-module-failure.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $ModuleFailureFile = $ModuleFailureCandidates | Select-Object -First 1
    $LastModuleFailure = '<none>'
    if ($ModuleFailureFile) {
        $ModuleFailureLines = @(Get-Content -LiteralPath $ModuleFailureFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($ModuleFailureLines.Count -gt 0) {
            $LastModuleFailure = $ModuleFailureLines[-1]
        }
    }

    $FeatureFailureCandidates = @(
        (Join-Path $Root 'run\abyss-feature-failure.txt'),
        (Join-Path $Root 'abyss-feature-failure.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $FeatureFailureFile = $FeatureFailureCandidates | Select-Object -First 1
    $LastFeatureFailure = '<none>'
    if ($FeatureFailureFile) {
        $FeatureFailureLines = @(Get-Content -LiteralPath $FeatureFailureFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($FeatureFailureLines.Count -gt 0) {
            $LastFeatureFailure = $FeatureFailureLines[-1]
        }
    }

    $EventFailureCandidates = @(
        (Join-Path $Root 'run\abyss-event-failure.txt'),
        (Join-Path $Root 'abyss-event-failure.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $EventFailureFile = $EventFailureCandidates | Select-Object -First 1
    $LastEventFailure = '<none>'
    if ($EventFailureFile) {
        $EventFailureLines = @(Get-Content -LiteralPath $EventFailureFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($EventFailureLines.Count -gt 0) {
            $LastEventFailure = $EventFailureLines[-1]
        }
    }

    $ConfigFailureCandidates = @(
        (Join-Path $Root 'run\abyss-config-failure.txt'),
        (Join-Path $Root 'abyss-config-failure.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $ConfigFailureFile = $ConfigFailureCandidates | Select-Object -First 1
    $LastConfigFailure = '<none>'
    if ($ConfigFailureFile) {
        $ConfigFailureLines = @(Get-Content -LiteralPath $ConfigFailureFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($ConfigFailureLines.Count -gt 0) {
            $LastConfigFailure = $ConfigFailureLines[-1]
        }
    }

    $RendererFailureCandidates = @(
        (Join-Path $Root 'run\abyss-renderer-failure.txt'),
        (Join-Path $Root 'abyss-renderer-failure.txt')
    ) | Where-Object { Test-Path -LiteralPath $_ }
    $RendererFailureFile = $RendererFailureCandidates | Select-Object -First 1
    $LastRendererFailure = '<none>'
    if ($RendererFailureFile) {
        $RendererFailureLines = @(Get-Content -LiteralPath $RendererFailureFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
        if ($RendererFailureLines.Count -gt 0) {
            $LastRendererFailure = $RendererFailureLines[-1]
        }
    }

    $CrashCount = @(Get-ChildItem -LiteralPath $CrashEvidence -File -ErrorAction SilentlyContinue).Count
    @(
        "HEAD=$Head"
        "JAR_SHA256=$JarHash"
        "RUNTIME_MODE=$(if ($DevRuntime) { 'dev-source' } else { 'packaged-jar' })"
        "EXTENDED_PROBES=$ExtendedProbes"
        "EXTENDED_PROBE_MAX=$(if ($ExtendedProbes) { 69 } else { 0 })"
        "REFERENCE_BOOTSTRAP=$UseReferenceBootstrap"
        "SKIP_CHAT_MENU=$UseSkipChatMenu"
        "SKIP_CHEATER_DETECTOR=$UseSkipCheaterDetector"
        "SKIP_ALT_MANAGER=$UseSkipAltManager"
        "REGISTRY_TARGET=$RegistryTarget"
        "RUNCLIENT_EXIT=$RuntimeExit"
        "LAST_BOOTSTRAP_STAGE=$LastStage"
        "LAST_RUNTIME_STAGE=$LastRuntimeStage"
        "LAST_MODULE_FAILURE=$LastModuleFailure"
        "LAST_FEATURE_FAILURE=$LastFeatureFailure"
        "LAST_EVENT_FAILURE=$LastEventFailure"
        "LAST_CONFIG_FAILURE=$LastConfigFailure"
        "LAST_RENDERER_FAILURE=$LastRendererFailure"
        "CRASH_EVIDENCE_FILES=$CrashCount"
    ) | Set-Content -LiteralPath (Join-Path $Evidence 'RESULT.txt') -Encoding UTF8

    if ($UnexpectedExitCode -ne $null) {
        ("RUNCLIENT_UNEXPECTED_EXIT=" + $UnexpectedExitCode) | Set-Content -LiteralPath (Join-Path $Evidence 'FAILED-runtime-exit.txt') -Encoding UTF8
        throw ("Minecraft/OpenAbyss exited unexpectedly with code " + $UnexpectedExitCode + ". Crash evidence was captured under " + $CrashEvidence)
    }

    if ($Failures.Count -gt 0) {
        $Failures | Set-Content -LiteralPath (Join-Path $Evidence 'FAILED-contracts.txt') -Encoding UTF8
        throw "Physical smoke bootstrap contract failed: $($Failures -join '; ')"
    }

    'PHYSICAL_SMOKE_BOOTSTRAP_PASS' | Set-Content -LiteralPath (Join-Path $Evidence 'PASS.txt') -Encoding UTF8
    Write-Host ''
    Write-Host 'PHYSICAL_SMOKE_BOOTSTRAP_PASS'
    Write-Host "HEAD=$Head"
    Write-Host "JAR_SHA256=$JarHash"
    Write-Host "Evidence=$Evidence"
}
finally {
    Pop-Location
}
