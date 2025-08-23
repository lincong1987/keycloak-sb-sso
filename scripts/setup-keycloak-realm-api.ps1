# Keycloak Realm and Client Auto Configuration Script
# Create ps-bmp realm and ps-bmp-client via REST API

param(
    [string]$KeycloakUrl = "http://localhost:8180",
    [string]$AdminUser = "admin",
    [string]$AdminPassword = "admin123",
    [string]$RealmName = "ps-bmp",
    [string]$ClientId = "ps-bmp-client"
)

# Function: Get admin access token
function Get-AdminToken {
    param(
        [string]$KeycloakUrl,
        [string]$AdminUser,
        [string]$AdminPassword
    )
    
    $tokenUrl = "$KeycloakUrl/realms/master/protocol/openid-connect/token"
    $body = @{
        grant_type = "password"
        client_id = "admin-cli"
        username = $AdminUser
        password = $AdminPassword
    }
    
    try {
        $response = Invoke-RestMethod -Uri $tokenUrl -Method Post -Body $body -ContentType "application/x-www-form-urlencoded"
        return $response.access_token
    }
    catch {
        Write-Error "Failed to get admin token: $($_.Exception.Message)"
        return $null
    }
}

# Function: Create Realm
function Create-Realm {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName
    )
    
    $realmUrl = "$KeycloakUrl/admin/realms"
    $headers = @{
        "Authorization" = "Bearer $Token"
        "Content-Type" = "application/json"
    }
    
    $realmConfig = @{
        realm = $RealmName
        enabled = $true
        displayName = "PS BMP Realm"
        accessTokenLifespan = 300
        ssoSessionIdleTimeout = 1800
        ssoSessionMaxLifespan = 36000
        registrationAllowed = $false
        loginWithEmailAllowed = $true
        duplicateEmailsAllowed = $false
    } | ConvertTo-Json -Depth 10
    
    try {
        Invoke-RestMethod -Uri $realmUrl -Method Post -Headers $headers -Body $realmConfig
        Write-Host "Realm '$RealmName' created successfully" -ForegroundColor Green
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-Host "Realm '$RealmName' already exists" -ForegroundColor Yellow
            return $true
        }
        Write-Error "Failed to create realm: $($_.Exception.Message)"
        return $false
    }
}

# Function: Create Client
function Create-Client {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName,
        [string]$ClientId
    )
    
    $clientUrl = "$KeycloakUrl/admin/realms/$RealmName/clients"
    $headers = @{
        "Authorization" = "Bearer $Token"
        "Content-Type" = "application/json"
    }
    
    $clientConfig = @{
        clientId = $ClientId
        enabled = $true
        publicClient = $true
        directAccessGrantsEnabled = $true
        standardFlowEnabled = $true
        implicitFlowEnabled = $false
        serviceAccountsEnabled = $false
        redirectUris = @(
            "http://localhost/callback",
            "http://localhost/*",
            "http://localhost:8080/callback",
            "http://localhost:8080/*"
        )
        webOrigins = @(
            "http://localhost",
            "http://localhost:8080"
        )
        attributes = @{
            "access.token.lifespan" = "300"
        }
    } | ConvertTo-Json -Depth 10
    
    try {
        Invoke-RestMethod -Uri $clientUrl -Method Post -Headers $headers -Body $clientConfig
        Write-Host "Client '$ClientId' created successfully" -ForegroundColor Green
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-Host "Client '$ClientId' already exists" -ForegroundColor Yellow
            return $true
        }
        Write-Error "Failed to create client: $($_.Exception.Message)"
        return $false
    }
}

# Function: Create test user
function Create-TestUser {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName
    )
    
    $userUrl = "$KeycloakUrl/admin/realms/$RealmName/users"
    $headers = @{
        "Authorization" = "Bearer $Token"
        "Content-Type" = "application/json"
    }
    
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
                value = "password123"
                temporary = $false
            }
        )
    } | ConvertTo-Json -Depth 10
    
    try {
        Invoke-RestMethod -Uri $userUrl -Method Post -Headers $headers -Body $userConfig
        Write-Host "Test user 'testuser' created successfully" -ForegroundColor Green
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-Host "Test user 'testuser' already exists" -ForegroundColor Yellow
            return $true
        }
        Write-Error "Failed to create test user: $($_.Exception.Message)"
        return $false
    }
}

# Main execution flow
Write-Host "Starting Keycloak Realm and Client configuration..." -ForegroundColor Cyan

# Wait for Keycloak to start
Write-Host "Waiting for Keycloak service to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Get admin token
Write-Host "Getting admin access token..." -ForegroundColor Yellow
$token = Get-AdminToken -KeycloakUrl $KeycloakUrl -AdminUser $AdminUser -AdminPassword $AdminPassword

if (-not $token) {
    Write-Error "Cannot get admin token, script terminated"
    exit 1
}

Write-Host "Admin token obtained successfully" -ForegroundColor Green

# Create Realm
Write-Host "Creating Realm '$RealmName'..." -ForegroundColor Yellow
$realmCreated = Create-Realm -KeycloakUrl $KeycloakUrl -Token $token -RealmName $RealmName

if (-not $realmCreated) {
    Write-Error "Realm creation failed, script terminated"
    exit 1
}

# Create Client
Write-Host "Creating Client '$ClientId'..." -ForegroundColor Yellow
$clientCreated = Create-Client -KeycloakUrl $KeycloakUrl -Token $token -RealmName $RealmName -ClientId $ClientId

if (-not $clientCreated) {
    Write-Error "Client creation failed, script terminated"
    exit 1
}

# Create test user
Write-Host "Creating test user..." -ForegroundColor Yellow
Create-TestUser -KeycloakUrl $KeycloakUrl -Token $token -RealmName $RealmName

Write-Host "Keycloak configuration completed!" -ForegroundColor Green
Write-Host "Realm: $RealmName" -ForegroundColor Cyan
Write-Host "Client ID: $ClientId" -ForegroundColor Cyan
Write-Host "Test user: testuser / password123" -ForegroundColor Cyan
Write-Host "Admin console: $KeycloakUrl/admin" -ForegroundColor Cyan