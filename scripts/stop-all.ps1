# PS-BMP Stop All Services Script
# Author: Trae AI Assistant
# Description: Interactive script to stop PS-BE and PS-FE services

Write-Host "=== PS-BMP Stop All Services ===" -ForegroundColor Red
Write-Host ""

# Get script directory and project root
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir

# Check if stop scripts exist
$backendStopScript = Join-Path $scriptDir "stop-backend.ps1"
$frontendStopScript = Join-Path $scriptDir "stop-frontend.ps1"

if (-not (Test-Path $backendStopScript)) {
    Write-Host "Warning: Backend stop script not found at $backendStopScript" -ForegroundColor Yellow
}

if (-not (Test-Path $frontendStopScript)) {
    Write-Host "Warning: Frontend stop script not found at $frontendStopScript" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Please select an option:" -ForegroundColor Cyan
Write-Host "1. Stop Backend Only (PS-BE)"
Write-Host "2. Stop Frontend Only (PS-FE)"
Write-Host "3. Stop Both Services and Redis (PS-BE + PS-FE + Redis)"
Write-Host "4. Force Stop All Java and Node Processes"
Write-Host "5. Exit"
Write-Host ""

$choice = Read-Host "Enter your choice (1-5)"

switch ($choice) {
    "1" {
        Write-Host "Stopping Backend Service..." -ForegroundColor Yellow
        if (Test-Path $backendStopScript) {
            & $backendStopScript
        } else {
            Write-Host "Backend stop script not found. Stopping Java processes manually..." -ForegroundColor Yellow
            Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Java processes stopped" -ForegroundColor Green
        }
    }
    "2" {
        Write-Host "Stopping Frontend Service..." -ForegroundColor Yellow
        if (Test-Path $frontendStopScript) {
            & $frontendStopScript
        } else {
            Write-Host "Frontend stop script not found. Stopping Node processes manually..." -ForegroundColor Yellow
            Get-Process -Name "node" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Node processes stopped" -ForegroundColor Green
        }
    }
    "3" {
        Write-Host "Stopping Both Services and Redis..." -ForegroundColor Yellow
        
        # Stop Frontend first
        Write-Host "Stopping Frontend..." -ForegroundColor Cyan
        if (Test-Path $frontendStopScript) {
            & $frontendStopScript
        } else {
            Get-Process -Name "node" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Node processes stopped" -ForegroundColor Green
        }
        
        Start-Sleep -Seconds 2
        
        # Stop Backend
        Write-Host "Stopping Backend..." -ForegroundColor Cyan
        if (Test-Path $backendStopScript) {
            & $backendStopScript
        } else {
            Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Java processes stopped" -ForegroundColor Green
        }
        
        Start-Sleep -Seconds 2
        
        # Stop Redis Docker container
        Write-Host "Stopping Redis Docker container..." -ForegroundColor Cyan
        try {
            $redisContainer = docker ps --filter "name=ps-redis" --format "{{.Names}}" 2>$null
            if ($redisContainer -eq "ps-redis") {
                docker stop ps-redis 2>$null
                Write-Host "Redis container stopped" -ForegroundColor Green
            } else {
                Write-Host "Redis container not running" -ForegroundColor Gray
            }
        }
        catch {
            Write-Host "Error stopping Redis container: $($_.Exception.Message)" -ForegroundColor Red
        }
        
        Write-Host "All services and Redis stopped successfully!" -ForegroundColor Green
    }
    "4" {
        Write-Host "Force stopping all Java and Node processes..." -ForegroundColor Red
        
        # Stop all Java processes
        $javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue
        if ($javaProcesses) {
            $javaProcesses | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Stopped $($javaProcesses.Count) Java process(es)" -ForegroundColor Green
        } else {
            Write-Host "No Java processes found" -ForegroundColor Gray
        }
        
        # Stop all Node processes
        $nodeProcesses = Get-Process -Name "node" -ErrorAction SilentlyContinue
        if ($nodeProcesses) {
            $nodeProcesses | Stop-Process -Force -ErrorAction SilentlyContinue
            Write-Host "Stopped $($nodeProcesses.Count) Node process(es)" -ForegroundColor Green
        } else {
            Write-Host "No Node processes found" -ForegroundColor Gray
        }
        
        # Clean up ports
        Write-Host "Cleaning up ports..." -ForegroundColor Cyan
        $ports = @(8088, 10801, 10802)
        foreach ($port in $ports) {
            $process = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
            if ($process) {
                Stop-Process -Id $process.OwningProcess -Force -ErrorAction SilentlyContinue
                Write-Host "Released port $port" -ForegroundColor Green
            }
        }
        
        Write-Host "Force stop completed!" -ForegroundColor Green
    }
    "5" {
        Write-Host "Exiting..." -ForegroundColor Gray
        exit 0
    }
    default {
        Write-Host "Invalid choice. Please run the script again and select 1-5." -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "Tip: To restart services, run the corresponding startup scripts" -ForegroundColor Cyan
Write-Host "Done!" -ForegroundColor Green