param(
    [int]$RunSeconds = 45,
    [string]$Jdk8
)

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'

$Root = Split-Path -Parent $PSScriptRoot
$Smoke = Join-Path $PSScriptRoot 'physical-smoke.ps1'
$Evidence = Join-Path $Root 'physical-smoke-evidence'
$Out = Join-Path $Root 'physical-bisect-evidence'

if (-not (Test-Path -LiteralPath $Smoke -PathType Leaf)) {
    throw "physical-smoke.ps1 not found: $Smoke"
}

Remove-Item -LiteralPath $Out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $Out | Out-Null

$Scenarios = @(
    @{ Name='00-default'; Args=@() },
    @{ Name='10-skip-chat-menu'; Args=@('-SkipChatMenu') },
    @{ Name='20-skip-cheater'; Args=@('-SkipCheaterDetector') },
    @{ Name='30-skip-altmanager'; Args=@('-SkipAltManager') },
    @{ Name='40-reference-bootstrap'; Args=@('-ReferenceBootstrap') },
    @{ Name='50-registry-103'; Args=@('-Registry103') },
    @{ Name='60-registry-97'; Args=@('-Registry97') },
    @{ Name='70-registry-92'; Args=@('-ReferenceRegistry') },
    @{ Name='80-reference-runtime'; Args=@('-ReferenceRuntime') }
)

$Summary = New-Object System.Collections.Generic.List[string]
$Summary.Add("scenario	outcome	result")

$First = $true
foreach ($Scenario in $Scenarios) {
    $Name = [string]$Scenario.Name
    Write-Host ''
    Write-Host ('=== ' + $Name + ' ===') -ForegroundColor Cyan

    $Invoke = @{
        RunSeconds = $RunSeconds
    }
    if ($Jdk8) { $Invoke.Jdk8 = $Jdk8 }
    if (-not $First) { $Invoke.SkipBuild = $true }

    foreach ($Switch in @($Scenario.Args)) {
        $Invoke[$Switch.TrimStart('-')] = $true
    }

    $Outcome = 'PASS_OR_TIMEOUT'
    try {
        & $Smoke @Invoke
    }
    catch {
        $Outcome = 'FAIL'
        Write-Warning ($Name + ': ' + $_.Exception.Message)
    }

    $ResultPath = Join-Path $Evidence 'RESULT.txt'
    $ResultCopy = Join-Path $Out ($Name + '-RESULT.txt')
    if (Test-Path -LiteralPath $ResultPath) {
        Copy-Item -LiteralPath $ResultPath -Destination $ResultCopy -Force
        $Flat = ((Get-Content -LiteralPath $ResultPath) -join '; ')
    } else {
        $Flat = 'RESULT_MISSING'
        Set-Content -LiteralPath $ResultCopy -Value $Flat -Encoding UTF8
    }

    foreach ($Extra in @('FAILED-runtime-exit.txt','FAILED-contracts.txt')) {
        $Path = Join-Path $Evidence $Extra
        if (Test-Path -LiteralPath $Path) {
            Copy-Item -LiteralPath $Path -Destination (Join-Path $Out ($Name + '-' + $Extra)) -Force
        }
    }

    $Summary.Add($Name + "	" + $Outcome + "	" + $Flat)
    $First = $false
}

$SummaryPath = Join-Path $Out 'SUMMARY.tsv'
$Summary | Set-Content -LiteralPath $SummaryPath -Encoding UTF8
Write-Host ''
Write-Host 'PHYSICAL_MENU_BISECT_COMPLETE' -ForegroundColor Green
Write-Host ('Summary=' + $SummaryPath)
