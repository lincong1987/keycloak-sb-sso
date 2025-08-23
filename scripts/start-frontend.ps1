#!/usr/bin/env pwsh
# PS-FE 前端服务启动脚本
# 作者: AI Assistant
# 描述: 启动 PS-BMP 前端开发服务器

Write-Host "=== PS-FE 前端服务启动脚本 ===" -ForegroundColor Green
Write-Host "正在启动 PS-BMP 前端开发服务器..." -ForegroundColor Yellow

# 切换到 ps-fe 目录
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir
$frontendDir = Join-Path $projectRoot "ps-fe"

if (-not (Test-Path $frontendDir)) {
    Write-Host "错误: 未找到 ps-fe 目录" -ForegroundColor Red
    exit 1
}

Set-Location $frontendDir
Write-Host "工作目录: $frontendDir" -ForegroundColor Cyan

# 检查是否在正确的目录
if (-not (Test-Path "package.json")) {
    Write-Host "错误: 未找到 package.json 文件" -ForegroundColor Red
    exit 1
}

# 检查 Node.js 是否可用
try {
    $nodeVersion = node --version 2>$null
    if ($LASTEXITCODE -ne 0) {
        throw "Node.js 未安装或不在 PATH 中"
    }
    Write-Host "Node.js 版本: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "错误: Node.js 未安装或不在 PATH 中，请先安装 Node.js" -ForegroundColor Red
    exit 1
}

# 检查 npm 是否可用
try {
    $npmVersion = npm --version 2>$null
    if ($LASTEXITCODE -ne 0) {
        throw "npm 未安装或不在 PATH 中"
    }
    Write-Host "npm 版本: $npmVersion" -ForegroundColor Green
} catch {
    Write-Host "错误: npm 未安装或不在 PATH 中，请先安装 npm" -ForegroundColor Red
    exit 1
}

# 检查依赖是否已安装
if (-not (Test-Path "node_modules")) {
    Write-Host "未找到 node_modules 目录，正在安装依赖..." -ForegroundColor Yellow
    try {
        npm install
        if ($LASTEXITCODE -ne 0) {
            throw "依赖安装失败"
        }
        Write-Host "依赖安装完成" -ForegroundColor Green
    } catch {
        Write-Host "错误: 依赖安装失败，请检查网络连接和 npm 配置" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "依赖检查通过" -ForegroundColor Green
}

Write-Host "" 
Write-Host "正在启动前端开发服务器..." -ForegroundColor Yellow
Write-Host "开发服务器将自动打开浏览器" -ForegroundColor Cyan
Write-Host "按 Ctrl+C 停止服务" -ForegroundColor Gray
Write-Host "" 

# 启动开发服务器
try {
    npm run serve-dev
} catch {
    Write-Host "启动失败，请检查错误信息" -ForegroundColor Red
    exit 1
}