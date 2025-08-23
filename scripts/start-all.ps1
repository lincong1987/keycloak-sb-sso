# PS 全栈服务启动脚本
# 描述: 按顺序启动 Redis、后端服务和前端服务

Write-Host "=== PS 全栈服务启动脚本 ===" -ForegroundColor Green
Write-Host "正在启动 PS-BMP 全栈应用..." -ForegroundColor Yellow
Write-Host ""

# 获取脚本目录和项目根目录
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir

# 检查项目目录结构
$beDir = Join-Path $projectRoot "ps-be"
$feDir = Join-Path $projectRoot "ps-fe"
$bePom = Join-Path $beDir "pom.xml"
$fePackage = Join-Path $feDir "package.json"

if (-not (Test-Path $bePom)) {
    Write-Host "错误: 未找到 ps-be/pom.xml 文件" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $fePackage)) {
    Write-Host "错误: 未找到 ps-fe/package.json 文件" -ForegroundColor Red
    exit 1
}

# 检查启动脚本是否存在
$backendStartScript = Join-Path $scriptDir "start-backend.ps1"
$frontendStartScript = Join-Path $scriptDir "start-frontend.ps1"

if (-not (Test-Path $backendStartScript)) {
    Write-Host "错误: 未找到 start-backend.ps1 启动脚本" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $frontendStartScript)) {
    Write-Host "错误: 未找到 start-frontend.ps1 启动脚本" -ForegroundColor Red
    exit 1
}

Write-Host "项目结构检查通过" -ForegroundColor Green
Write-Host ""

# 提示用户选择启动模式
Write-Host "请选择启动模式:" -ForegroundColor Cyan
Write-Host "1. 仅启动后端服务 (ps-be)" -ForegroundColor White
Write-Host "2. 仅启动前端服务 (ps-fe)" -ForegroundColor White
Write-Host "3. 启动全栈服务 (Redis + 后端 + 前端，推荐)" -ForegroundColor White
Write-Host "4. 退出" -ForegroundColor White
Write-Host ""

$choice = Read-Host "请输入选择 (1-4)"

if ($choice -eq "1") {
    Write-Host "正在启动后端服务..." -ForegroundColor Yellow
    & $backendStartScript
}
elseif ($choice -eq "2") {
    Write-Host "正在启动前端服务..." -ForegroundColor Yellow
    & $frontendStartScript
}
elseif ($choice -eq "3") {
    Write-Host "正在启动全栈服务 (Redis + 后端 + 前端)..." -ForegroundColor Yellow
    Write-Host ""
    
    # 启动 Redis Docker 容器
    Write-Host "[1/3] 启动 Redis 服务..." -ForegroundColor Cyan
    try {
        $redisContainer = docker ps --filter "name=ps-redis" --format "{{.Names}}" 2>$null
        if ($redisContainer -eq "ps-redis") {
            Write-Host "Redis 容器已在运行" -ForegroundColor Green
        } else {
            Write-Host "启动 Redis 容器..." -ForegroundColor Yellow
            docker-compose -f docker\docker-compose.yml up redis -d 2>$null
            Start-Sleep -Seconds 3
            
            # 验证 Redis 启动
            $redisCheck = docker exec ps-redis redis-cli ping 2>$null
            if ($redisCheck -eq "PONG") {
                Write-Host "Redis 服务启动成功" -ForegroundColor Green
            } else {
                Write-Host "Redis 服务启动失败" -ForegroundColor Red
                exit 1
            }
        }
    }
    catch {
        Write-Host "启动 Redis 时出错" -ForegroundColor Red
        exit 1
    }
    
    Write-Host ""
    
    # 启动后端服务（后台运行）
    Write-Host "[2/3] 启动后端服务..." -ForegroundColor Cyan
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:beDir
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
        Write-Host "后端服务启动成功" -ForegroundColor Green
    } else {
        Write-Host "后端服务启动失败" -ForegroundColor Red
        Receive-Job -Id $backendJob.Id
        Remove-Job -Id $backendJob.Id
        exit 1
    }
    
    Write-Host ""
    Write-Host "[3/3] 启动前端服务..." -ForegroundColor Cyan
    
    # 启动前端服务
    try {
        & $frontendStartScript
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