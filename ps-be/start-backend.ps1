#!/usr/bin/env pwsh
# PS-BE 后端服务启动脚本
# 作者: AI Assistant
# 描述: 启动 PS-BMP 后端服务，包含数据库连接配置

Write-Host "=== PS-BE 后端服务启动脚本 ===" -ForegroundColor Green
Write-Host "正在启动 PS-BMP 后端服务..." -ForegroundColor Yellow

# 设置环境变量
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "123456"

Write-Host "数据库配置:" -ForegroundColor Cyan
Write-Host "  用户名: $env:DB_USERNAME" -ForegroundColor White
Write-Host "  密码: [已设置]" -ForegroundColor White
Write-Host "" 

# 检查是否在正确的目录
if (-not (Test-Path "pom.xml")) {
    Write-Host "错误: 未找到 pom.xml 文件，请确保在 ps-be 目录下运行此脚本" -ForegroundColor Red
    exit 1
}

# 检查 Maven 是否可用
try {
    $mvnVersion = mvn --version 2>$null
    if ($LASTEXITCODE -ne 0) {
        throw "Maven 未安装或不在 PATH 中"
    }
    Write-Host "Maven 版本检查通过" -ForegroundColor Green
} catch {
    Write-Host "错误: Maven 未安装或不在 PATH 中，请先安装 Maven" -ForegroundColor Red
    exit 1
}

Write-Host "正在启动 Spring Boot 应用..." -ForegroundColor Yellow
Write-Host "服务将在 http://localhost:8088 启动" -ForegroundColor Cyan
Write-Host "按 Ctrl+C 停止服务" -ForegroundColor Gray
Write-Host "" 

# 启动 Spring Boot 应用
try {
    mvn spring-boot:run
} catch {
    Write-Host "启动失败，请检查错误信息" -ForegroundColor Red
    exit 1
}