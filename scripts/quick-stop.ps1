#!/usr/bin/env pwsh
# PS Quick Stop Script
# Author: AI Assistant
# Description: Quickly stop PS-BMP full-stack application services

Write-Host "=== PS Quick Stop Script ===" -ForegroundColor Red
Write-Host "Stopping PS-BMP full-stack application..." -ForegroundColor Yellow
Write-Host ""

# Stop frontend services
Write-Host "[1/3] Stopping frontend services..." -ForegroundColor Cyan
try {
    # Find and stop Node.js processes
    $nodeProcesses = Get-Process -Name "node" -ErrorAction SilentlyContinue
    
    if ($nodeProcesses) {
        foreach ($process in $nodeProcesses) {
            Stop-Process -Id $process.Id -Force -ErrorAction SilentlyContinue
            Write-Host "Stopped frontend process ID: $($process.Id)" -ForegroundColor Green
        }
    }
    
    # Check and release frontend ports
    $frontendPorts = @(10801, 10802)
    foreach ($port in $frontendPorts) {
        $portProcess = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($portProcess) {
            $processId = $portProcess.OwningProcess
            Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
            Write-Host "Released frontend port $port" -ForegroundColor Green
        }
    }
    
    Write-Host "Frontend services stopped" -ForegroundColor Green
}
catch {
    Write-Host "Error stopping frontend services: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Stop backend services
Write-Host "[2/3] Stopping backend services..." -ForegroundColor Cyan
try {
    # Find and stop Java processes
    $javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue
    
    if ($javaProcesses) {
        foreach ($process in $javaProcesses) {
            Stop-Process -Id $process.Id -Force -ErrorAction SilentlyContinue
            Write-Host "Stopped backend process ID: $($process.Id)" -ForegroundColor Green
        }
    }
    
    # Check and release backend port
    $portProcess = Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($portProcess) {
        $processId = $portProcess.OwningProcess
        Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
        Write-Host "Released backend port 8088" -ForegroundColor Green
    }
    
    Write-Host "Backend services stopped" -ForegroundColor Green
}
catch {
    Write-Host "Error stopping backend services: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Clean temporary files
Write-Host "[3/3] Cleaning temporary files..." -ForegroundColor Cyan
try {
    # Clean frontend temporary files
    if (Test-Path "ps-fe\dist") {
        Remove-Item "ps-fe\dist" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "Cleaned frontend dist directory" -ForegroundColor Green
    }
    
    if (Test-Path "ps-fe\.rsbuild") {
        Remove-Item "ps-fe\.rsbuild" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "Cleaned frontend .rsbuild cache" -ForegroundColor Green
    }
    
    Write-Host "Temporary files cleaned" -ForegroundColor Green
}
catch {
    Write-Host "Error cleaning temporary files: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== PS-BMP Full-stack Application Quick Stop Complete ===" -ForegroundColor Green
Write-Host "All services stopped, ports released, temporary files cleaned" -ForegroundColor Cyan
Write-Host "Tip: To restart services, run quick-start.ps1 or start-all.ps1" -ForegroundColor Yellow