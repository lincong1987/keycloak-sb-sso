#!/usr/bin/env pwsh
# PS-FE 前端服务停止脚本
# 作者: AI Assistant
# 描述: 停止 PS-FE Vue.js 前端开发服务器

Write-Host "=== PS-FE 前端服务停止脚本 ===" -ForegroundColor Red
Write-Host "正在停止 PS-FE 前端服务..." -ForegroundColor Yellow
Write-Host ""

# 检查是否在正确的目录
if (-not (Test-Path "package.json")) {
    Write-Host "错误: 未找到 package.json 文件，请在 ps-fe 目录下运行此脚本" -ForegroundColor Red
    exit 1
}

# 查找 Node.js 应用进程
Write-Host "正在查找 Node.js 应用进程..." -ForegroundColor Cyan

# 方法1: 通过 Node.js 进程名查找
$nodeProcesses = Get-Process -Name "node" -ErrorAction SilentlyContinue | Where-Object {
    $_.CommandLine -like "*serve-dev*" -or 
    $_.CommandLine -like "*rsbuild*" -or
    $_.CommandLine -like "*ps-fe*" -or
    $_.Path -like "*ps-fe*"
}

if ($nodeProcesses) {
    Write-Host "找到 $($nodeProcesses.Count) 个相关 Node.js 进程" -ForegroundColor Green
    
    foreach ($process in $nodeProcesses) {
        Write-Host "正在停止进程 ID: $($process.Id) (名称: $($process.ProcessName))" -ForegroundColor Yellow
        
        try {
            # 尝试优雅停止
            $process.CloseMainWindow()
            Start-Sleep -Seconds 2
            
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
    Write-Host "未找到运行中的 Node.js 应用进程" -ForegroundColor Yellow
}

# 方法2: 通过端口查找进程
Write-Host ""
Write-Host "检查端口 10801 和 10802 上的进程..." -ForegroundColor Cyan

$ports = @(10801, 10802)
foreach ($port in $ports) {
    try {
        $portProcess = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue | Select-Object -First 1
        
        if ($portProcess) {
            $processId = $portProcess.OwningProcess
            $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
            
            if ($process) {
                Write-Host "找到占用端口 $port 的进程: $($process.ProcessName) (ID: $processId)" -ForegroundColor Yellow
                
                try {
                    Stop-Process -Id $processId -Force
                    Write-Host "已强制停止占用端口 $port 的进程" -ForegroundColor Green
                }
                catch {
                    Write-Host "停止端口 $port 进程时出错: $($_.Exception.Message)" -ForegroundColor Red
                }
            }
        }
        else {
            Write-Host "端口 $port 未被占用" -ForegroundColor Green
        }
    }
    catch {
        Write-Host "检查端口 $port 时出错: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 清理临时文件和缓存
Write-Host ""
Write-Host "清理临时文件和缓存..." -ForegroundColor Cyan

# 清理 dist 目录
if (Test-Path "dist") {
    try {
        Remove-Item "dist" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "已清理 dist 目录" -ForegroundColor Green
    }
    catch {
        Write-Host "清理 dist 目录时出错: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 清理 .rsbuild 缓存目录
if (Test-Path ".rsbuild") {
    try {
        Remove-Item ".rsbuild" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "已清理 .rsbuild 缓存目录" -ForegroundColor Green
    }
    catch {
        Write-Host "清理 .rsbuild 缓存时出错: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 清理其他临时文件
$tempPatterns = @("*.log", "*.tmp", ".DS_Store")
foreach ($pattern in $tempPatterns) {
    try {
        $tempFiles = Get-ChildItem -Filter $pattern -Recurse -ErrorAction SilentlyContinue
        if ($tempFiles) {
            $tempFiles | Remove-Item -Force -ErrorAction SilentlyContinue
            Write-Host "已清理 $($tempFiles.Count) 个 $pattern 文件" -ForegroundColor Green
        }
    }
    catch {
        Write-Host "清理 $pattern 文件时出错: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== PS-FE 前端服务停止完成 ===" -ForegroundColor Green
Write-Host "提示: 如果需要重新启动服务，请运行 start-frontend.ps1" -ForegroundColor Cyan