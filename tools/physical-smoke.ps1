param(
    [string]$Jdk8,
    [int]$RunSeconds = 180,
    [switch]$SkipBuild,
    [switch]$KeepOpen,
    [switch]$DevRuntime
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$Root = Split-Path -Parent $PSScriptRoot
$Evidence = Join-Path $Root 'physical-smoke-evidence'
$GradleCache = Join-Path $env:LOCALAPPDATA 'OpenAbyss-Recovery'
$GradleZip = Join-Path $GradleCache 'gradle-2.14.1-bin.zip'
$GradleHome = Join-Path $GradleCache 'gradle-2.14.1'
$Gradle = Join-Path $GradleHome 'bin\gradle.bat'
$GradleUserHome = Join-Path $GradleCache 'gradle-user-home'

New-Item -ItemType Directory -Force -Path $Evidence,$GradleCache,$GradleUserHome | Out-Null

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
    $env:JAVA_TOOL_OPTIONS = '-Dabyss.runtimeSelfTest=true'

    $Meta = @(
        "timestamp=$(Get-Date -Format o)"
        "branch=$Branch"
        "head=$Head"
        "java_home=$Jdk8"
        "gradle=$Gradle"
        "keep_open=$KeepOpen"
        "run_seconds=$RunSeconds"
        "runtime_mode=$(if ($DevRuntime) { 'dev-source' } else { 'packaged-jar' })"
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
    $BootstrapStage = Join-Path $Root 'abyss-bootstrap-stage.txt'
    $BootstrapStageRun = Join-Path $Root 'run\abyss-bootstrap-stage.txt'
    Remove-Item -LiteralPath $Stdout,$Stderr,$BootstrapStage,$BootstrapStageRun -Force -ErrorAction SilentlyContinue

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
            & taskkill.exe /PID $P.Id /T /F | Out-Null
            Start-Sleep -Seconds 2
        } else {
            $P.WaitForExit()
            $P.Refresh()
            Write-Host "RUNCLIENT_EXIT=$($P.ExitCode)"
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

    $Required = @(
        '[ABYSSDIAG] module count     = 112 of 112 OK',
        '[ABYSSDIAG] config writable   = writable=true',
        '[ABYSSDIAG] module usability   = toggleable=109 stockDisabled=3 invalid=0 nullSettings=0',
        '[ABYSSDIAG] eventbus selftest  = PASS',
        '[ABYSSDIAG] module selftest    = PASS',
        '[ABYSSDIAG] config selftest    = PASS',
        '[ABYSSDIAG] session selftest   = PASS',
        '[ABYSSDIAG] command selftest  = PASS commands=19 primaryAliases=19',
        '[ABYSSDIAG] clickgui selftest = PASS mode=',
        '[ABYSSDIAG] altmenu selftest  = PASS',
        '[ABYSSDIAG] tD.S (list)      = 112',
        '[ABYSSDIAG] zu_3.B VESTIGE   = live',
        '[ABYSSDIAG] zu_3.Y STUDIO    = live',
        '[ABYSSDIAG] zu_3.F RAVEN     = live'
    )

    $Failures = @()
    foreach ($Needle in $Required) {
        if (-not $DiagText.Contains($Needle)) { $Failures += $Needle }
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
