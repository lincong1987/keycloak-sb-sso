#!/usr/bin/env pwsh
# PS-BE 后端服务停止脚本
# 作者: AI Assistant
# 描述: 停止 PS-BE Spring Boot 后端服务

Write-Host "=== PS-BE 后端服务停止脚本 ===" -ForegroundColor Red
Write-Host "正在停止 PS-BE 后端服务..." -ForegroundColor Yellow
Write-Host ""

# 检查是否在正确的目录
if (-not (Test-Path "pom.xml")) {
    Write-Host "错误: 未找到 pom.xml 文件，请在 ps-be 目录下运行此脚本" -ForegroundColor Red
    exit 1
}

# 查找 Spring Boot 应用进程
Write-Host "正在查找 Spring Boot 应用进程..." -ForegroundColor Cyan

# 方法1: 通过 Java 进程名查找
$javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue | Where-Object {
    $_.CommandLine -like "*spring-boot:run*" -or 
    $_.CommandLine -like "*com.jiuxi.Application*" -or
    $_.CommandLine -like "*ps-bmp-backend*"
}

if ($javaProcesses) {
    Write-Host "找到 $($javaProcesses.Count) 个相关 Java 进程" -ForegroundColor Green
    
    foreach ($process in $javaProcesses) {
        Write-Host "正在停止进程 ID: $($process.Id) (名称: $($process.ProcessName))" -ForegroundColor Yellow
        
        try {
            # 尝试优雅停止
            $process.CloseMainWindow()
            Start-Sleep -Seconds 3
            
            # 检查进程是否仍在运行
            if (-not $process.HasExited) {
                Write-Host "优雅停止失败，强制终止进程..." -ForegroundColor Orange
                Stop-Process -Id $process.Id -Force
            }
            
            Write-Host "进程 $($process.Id) 已停止" -ForegroundColor Green
        }
        catch {
            Write-Host "停止进程 $($process.Id) 时出错: $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}
else {
    Write-Host "未找到运行中的 Spring Boot 应用进程" -ForegroundColor Yellow
}

# 方法2: 通过端口查找进程
Write-Host ""
Write-Host "检查端口 8088 上的进程..." -ForegroundColor Cyan

try {
    $portProcess = Get-NetTCPConnection -LocalPort 8088 -ErrorAction SilentlyContinue | Select-Object -First 1
    
    if ($portProcess) {
        $processId = $portProcess.OwningProcess
        $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
        
        if ($process) {
            Write-Host "找到占用端口 8088 的进程: $($process.ProcessName) (ID: $processId)" -ForegroundColor Yellow
            
            try {
                Stop-Process -Id $processId -Force
                Write-Host "已强制停止占用端口 8088 的进程" -ForegroundColor Green
            }
            catch {
                Write-Host "停止端口进程时出错: $($_.Exception.Message)" -ForegroundColor Red
            }
        }
    }
    else {
        Write-Host "端口 8088 未被占用" -ForegroundColor Green
    }
}
catch {
    Write-Host "检查端口时出错: $($_.Exception.Message)" -ForegroundColor Red
}

# 清理临时文件
Write-Host ""
Write-Host "清理临时文件..." -ForegroundColor Cyan

if (Test-Path "target") {
    try {
        # 清理 target 目录中的临时文件（保留编译结果）
        $tempFiles = Get-ChildItem "target" -Filter "*.tmp" -Recurse -ErrorAction SilentlyContinue
        if ($tempFiles) {
            $tempFiles | Remove-Item -Force -ErrorAction SilentlyContinue
            Write-Host "已清理 $($tempFiles.Count) 个临时文件" -ForegroundColor Green
        }
    }
    catch {
        Write-Host "清理临时文件时出错: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== PS-BE 后端服务停止完成 ===" -ForegroundColor Green
Write-Host "提示: 如果需要重新启动服务，请运行 start-backend.ps1" -ForegroundColor Cyan