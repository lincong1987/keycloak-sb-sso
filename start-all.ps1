#!/usr/bin/env pwsh
# PS 全栈服务启动脚本
# 作者: AI Assistant
# 描述: 同时启动 PS-BE 后端服务和 PS-FE 前端服务

Write-Host "=== PS 全栈服务启动脚本 ===" -ForegroundColor Green
Write-Host "正在启动 PS-BMP 全栈应用..." -ForegroundColor Yellow
Write-Host ""

# 检查项目目录结构
if (-not (Test-Path "ps-be\pom.xml")) {
    Write-Host "错误: 未找到 ps-be/pom.xml 文件，请确保在项目根目录下运行此脚本" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path "ps-fe\package.json")) {
    Write-Host "错误: 未找到 ps-fe/package.json 文件，请确保在项目根目录下运行此脚本" -ForegroundColor Red
    exit 1
}

# 检查启动脚本是否存在
if (-not (Test-Path "ps-be\start-backend.ps1")) {
    Write-Host "错误: 未找到 ps-be/start-backend.ps1 启动脚本" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path "ps-fe\start-frontend.ps1")) {
    Write-Host "错误: 未找到 ps-fe/start-frontend.ps1 启动脚本" -ForegroundColor Red
    exit 1
}

Write-Host "项目结构检查通过" -ForegroundColor Green
Write-Host ""

# 提示用户选择启动模式
Write-Host "请选择启动模式:" -ForegroundColor Cyan
Write-Host "1. 仅启动后端服务 (ps-be)" -ForegroundColor White
Write-Host "2. 仅启动前端服务 (ps-fe)" -ForegroundColor White
Write-Host "3. 同时启动前后端服务 (推荐)" -ForegroundColor White
Write-Host "4. 退出" -ForegroundColor White
Write-Host ""

$choice = Read-Host "请输入选择 (1-4)"

if ($choice -eq "1") {
    Write-Host "正在启动后端服务..." -ForegroundColor Yellow
    Set-Location "ps-be"
    & ".\start-backend.ps1"
}
elseif ($choice -eq "2") {
    Write-Host "正在启动前端服务..." -ForegroundColor Yellow
    Set-Location "ps-fe"
    & ".\start-frontend.ps1"
}
elseif ($choice -eq "3") {
    Write-Host "正在同时启动前后端服务..." -ForegroundColor Yellow
    Write-Host ""
    
    # 启动后端服务（后台运行）
    Write-Host "[1/2] 启动后端服务..." -ForegroundColor Cyan
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:PWD
        Set-Location "ps-be"
        $env:DB_USERNAME = "root"
        $env:DB_PASSWORD = "123456"
        mvn spring-boot:run
    }
    
    # 等待后端服务启动
    Write-Host "等待后端服务启动..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
    
    # 检查后端服务状态
    $backendStatus = Get-Job -Id $backendJob.Id
    if ($backendStatus.State -eq "Running") {
        Write-Host "后端服务启动成功 (Job ID: $($backendJob.Id))" -ForegroundColor Green
    } else {
        Write-Host "后端服务启动失败" -ForegroundColor Red
        Receive-Job -Id $backendJob.Id
        Remove-Job -Id $backendJob.Id
        exit 1
    }
    
    Write-Host ""
    Write-Host "[2/2] 启动前端服务..." -ForegroundColor Cyan
    
    # 启动前端服务
    try {
        Set-Location "ps-fe"
        & ".\start-frontend.ps1"
    } finally {
        # 清理后端服务
        Write-Host ""
        Write-Host "正在停止后端服务..." -ForegroundColor Yellow
        Stop-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
        Remove-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
        Write-Host "服务已停止" -ForegroundColor Green
    }
}
elseif ($choice -eq "4") {
    Write-Host "退出启动脚本" -ForegroundColor Gray
    exit 0
}
else {
    Write-Host "无效选择，请重新运行脚本" -ForegroundColor Red
    exit 1
}