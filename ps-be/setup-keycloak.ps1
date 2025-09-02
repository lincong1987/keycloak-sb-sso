# Keycloak 配置脚本
# 创建 ps-bmp realm 和客户端

$keycloakUrl = "http://localhost:8180"
$adminUser = "admin"
$adminPassword = "admin123"
$realmName = "ps-realm"
$clientId = "ps-realm-client"
$redirectUri = "http://localhost/*"

Write-Host "开始配置 Keycloak..."

# 获取管理员访问令牌
Write-Host "获取管理员访问令牌..."
$tokenResponse = Invoke-RestMethod -Uri "$keycloakUrl/realms/master/protocol/openid-connect/token" -Method POST -ContentType "application/x-www-form-urlencoded" -Body "grant_type=password&client_id=admin-cli&username=$adminUser&password=$adminPassword"

$accessToken = $tokenResponse.access_token
Write-Host "访问令牌获取成功"

# 创建 realm
Write-Host "创建 realm: $realmName"
$realmConfig = @{
    realm = $realmName
    enabled = $true
    displayName = "PS BMP System"
    registrationAllowed = $false
    loginWithEmailAllowed = $true
    duplicateEmailsAllowed = $false
    resetPasswordAllowed = $true
    editUsernameAllowed = $false
    bruteForceProtected = $true
    accessTokenLifespan = 3600
    ssoSessionIdleTimeout = 1800
    ssoSessionMaxLifespan = 36000
} | ConvertTo-Json -Depth 10

try {
    Invoke-RestMethod -Uri "$keycloakUrl/admin/realms" -Method POST -Headers @{"Authorization" = "Bearer $accessToken"; "Content-Type" = "application/json"} -Body $realmConfig
    Write-Host "Realm 创建成功"
}
catch {
    if ($_.Exception.Response.StatusCode -eq 409) {
        Write-Host "Realm 已存在，跳过创建"
    }
    else {
        Write-Host "创建 Realm 失败: $($_.Exception.Message)"
        throw
    }
}

# 创建客户端
Write-Host "创建客户端: $clientId"
$clientConfig = @{
    clientId = $clientId
    enabled = $true
    protocol = "openid-connect"
    publicClient = $false
    bearerOnly = $false
    standardFlowEnabled = $true
    implicitFlowEnabled = $false
    directAccessGrantsEnabled = $true
    serviceAccountsEnabled = $false
    redirectUris = @($redirectUri)
    webOrigins = @("http://localhost")
    attributes = @{
        "access.token.lifespan" = "3600"
        "client.secret.creation.time" = [string][int64](Get-Date -UFormat %s)
    }
} | ConvertTo-Json -Depth 10

try {
    Invoke-RestMethod -Uri "$keycloakUrl/admin/realms/$realmName/clients" -Method POST -Headers @{"Authorization" = "Bearer $accessToken"; "Content-Type" = "application/json"} -Body $clientConfig
    Write-Host "客户端创建成功"
}
catch {
    if ($_.Exception.Response.StatusCode -eq 409) {
        Write-Host "客户端已存在，跳过创建"
    }
    else {
        Write-Host "创建客户端失败: $($_.Exception.Message)"
    }
}

# 获取客户端详情和密钥
Write-Host "获取客户端信息..."
$clients = Invoke-RestMethod -Uri "$keycloakUrl/admin/realms/$realmName/clients?clientId=$clientId" -Method GET -Headers @{"Authorization" = "Bearer $accessToken"}

if ($clients.Count -gt 0) {
    $client = $clients[0]
    $clientUuid = $client.id
    
    # 获取客户端密钥
    $clientSecret = Invoke-RestMethod -Uri "$keycloakUrl/admin/realms/$realmName/clients/$clientUuid/client-secret" -Method GET -Headers @{"Authorization" = "Bearer $accessToken"}
    
    Write-Host "客户端配置完成:"
    Write-Host "  Realm: $realmName"
    Write-Host "  Client ID: $clientId"
    Write-Host "  Client Secret: $($clientSecret.value)"
    Write-Host "  Auth URL: $keycloakUrl/realms/$realmName/protocol/openid-connect/auth"
    Write-Host "  Token URL: $keycloakUrl/realms/$realmName/protocol/openid-connect/token"
    Write-Host "  UserInfo URL: $keycloakUrl/realms/$realmName/protocol/openid-connect/userinfo"
}

# 创建测试用户
Write-Host "创建测试用户..."
$userConfig = @{
    username = "testuser"
    enabled = $true
    firstName = "Test"
    lastName = "User"
    email = "test@example.com"
    emailVerified = $true
    credentials = @(
        @{
            type = "password"
            value = "test123"
            temporary = $false
        }
    )
} | ConvertTo-Json -Depth 10

try {
    Invoke-RestMethod -Uri "$keycloakUrl/admin/realms/$realmName/users" -Method POST -Headers @{"Authorization" = "Bearer $accessToken"; "Content-Type" = "application/json"} -Body $userConfig
    Write-Host "测试用户创建成功 (用户名: testuser, 密码: test123)"
}
catch {
    if ($_.Exception.Response.StatusCode -eq 409) {
        Write-Host "测试用户已存在，跳过创建"
    }
    else {
        Write-Host "创建测试用户失败: $($_.Exception.Message)"
    }
}

Write-Host "Keycloak 配置完成！"
Write-Host "现在可以通过 http://localhost 访问应用，系统会自动重定向到 Keycloak 进行认证。"