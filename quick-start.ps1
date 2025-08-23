#!/usr/bin/env pwsh
# PS 快速启动脚本
# 作者: AI Assistant
# 描述: 快速启动开发环境，同时启动前后端服务

Write-Host "=== PS 快速启动脚本 ===" -ForegroundColor Green
Write-Host "正在快速启动开发环境..." -ForegroundColor Yellow
Write-Host "" 

# 设置错误处理
$ErrorActionPreference = "Stop"

try {
    # 检查项目结构
    if (-not (Test-Path "ps-be\pom.xml") -or -not (Test-Path "ps-fe\package.json")) {
        throw "项目结构不完整，请确保在项目根目录下运行"
    }
    
    Write-Host "✓ 项目结构检查通过" -ForegroundColor Green
    
    # 启动后端服务（后台）
    Write-Host "🚀 启动后端服务..." -ForegroundColor Cyan
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:PWD
        Set-Location "ps-be"
        $env:DB_USERNAME = "root"
        $env:DB_PASSWORD = "123456"
        mvn spring-boot:run -q
    }
    
    Write-Host "⏳ 等待后端服务启动 (15秒)..." -ForegroundColor Yellow
    Start-Sleep -Seconds 15
    
    # 检查后端状态
    $backendStatus = Get-Job -Id $backendJob.Id
    if ($backendStatus.State -ne "Running") {
        throw "后端服务启动失败"
    }
    
    Write-Host "✓ 后端服务启动成功 (http://localhost:8088)" -ForegroundColor Green
    
    # 启动前端服务
    Write-Host "🎨 启动前端服务..." -ForegroundColor Cyan
    Set-Location "ps-fe"
    
    # 检查依赖
    if (-not (Test-Path "node_modules")) {
        Write-Host "📦 安装前端依赖..." -ForegroundColor Yellow
        npm install --silent
    }
    
    Write-Host "" 
    Write-Host "🎉 开发环境启动完成!" -ForegroundColor Green
    Write-Host "" 
    Write-Host "服务地址:" -ForegroundColor Cyan
    Write-Host "  后端: http://localhost:8088" -ForegroundColor White
    Write-Host "  前端: 即将自动打开浏览器" -ForegroundColor White
    Write-Host "" 
    Write-Host "按 Ctrl+C 停止所有服务" -ForegroundColor Gray
    Write-Host "" 
    
    # 启动前端开发服务器
    npm run serve-dev
    
} catch {
    Write-Host "" 
    Write-Host "❌ 启动失败: $($_.Exception.Message)" -ForegroundColor Red
    
    # 清理后台任务
    if ($backendJob) {
        Write-Host "🧹 清理后端服务..." -ForegroundColor Yellow
        Stop-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
        Remove-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
    }
    
    exit 1
} finally {
    # 清理后台任务
    if ($backendJob) {
        Write-Host "" 
        Write-Host "🧹 停止后端服务..." -ForegroundColor Yellow
        Stop-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
        Remove-Job -Id $backendJob.Id -ErrorAction SilentlyContinue
        Write-Host "✓ 所有服务已停止" -ForegroundColor Green
    }
}