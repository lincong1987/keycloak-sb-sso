# Keycloak Realm Setup Script
# This script creates a realm and clients for the PS application

# Color output function
function Write-ColorOutput {
    param(
        [string]$Message,
        [string]$Color = "White"
    )
    Write-Host $Message -ForegroundColor $Color
}

# Get admin token
function Get-AdminToken {
    param(
        [string]$KeycloakUrl,
        [string]$Username = "admin",
        [string]$Password = "admin123"
    )
    
    try {
        $tokenUrl = "$KeycloakUrl/realms/master/protocol/openid-connect/token"
        $body = "grant_type=password&client_id=admin-cli&username=$Username&password=$Password"
        
        $response = Invoke-RestMethod -Uri $tokenUrl -Method Post -Body $body -ContentType "application/x-www-form-urlencoded"
        return $response.access_token
    }
    catch {
        Write-ColorOutput "Failed to get admin token: $($_.Exception.Message)" "Red"
        return $null
    }
}

# Create Realm
function Create-Realm {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName
    )
    
    try {
        $realmUrl = "$KeycloakUrl/admin/realms"
        $headers = @{
            "Authorization" = "Bearer $Token"
            "Content-Type" = "application/json"
        }
        
        $realmConfig = @{
            realm = $RealmName
            enabled = $true
            displayName = "PS Application Realm"
            accessTokenLifespan = 300
            ssoSessionIdleTimeout = 1800
            ssoSessionMaxLifespan = 36000
            offlineSessionIdleTimeout = 2592000
            accessCodeLifespan = 60
            accessCodeLifespanUserAction = 300
            accessCodeLifespanLogin = 1800
            actionTokenGeneratedByAdminLifespan = 43200
            actionTokenGeneratedByUserLifespan = 300
            sslRequired = "external"
            registrationAllowed = $true
            registrationEmailAsUsername = $false
            rememberMe = $true
            verifyEmail = $false
            loginWithEmailAllowed = $true
            duplicateEmailsAllowed = $false
            resetPasswordAllowed = $true
            editUsernameAllowed = $false
            bruteForceProtected = $true
        } | ConvertTo-Json -Depth 10
        
        Invoke-RestMethod -Uri $realmUrl -Method Post -Headers $headers -Body $realmConfig
        Write-ColorOutput "Realm '$RealmName' created successfully" "Green"
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-ColorOutput "Realm '$RealmName' already exists" "Yellow"
            return $true
        }
        Write-ColorOutput "Failed to create realm: $($_.Exception.Message)" "Red"
        return $false
    }
}

# Create Client
function Create-Client {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName,
        [string]$ClientId,
        [string]$ClientSecret,
        [array]$RedirectUris,
        [array]$WebOrigins,
        [string]$Description
    )
    
    try {
        $clientUrl = "$KeycloakUrl/admin/realms/$RealmName/clients"
        $headers = @{
            "Authorization" = "Bearer $Token"
            "Content-Type" = "application/json"
        }
        
        $clientConfig = @{
            clientId = $ClientId
            name = $ClientId
            description = $Description
            enabled = $true
            clientAuthenticatorType = "client-secret"
            secret = $ClientSecret
            redirectUris = $RedirectUris
            webOrigins = $WebOrigins
            protocol = "openid-connect"
            publicClient = $false
            bearerOnly = $false
            consentRequired = $false
            standardFlowEnabled = $true
            implicitFlowEnabled = $false
            directAccessGrantsEnabled = $true
            serviceAccountsEnabled = $true
            authorizationServicesEnabled = $false
            fullScopeAllowed = $true
        } | ConvertTo-Json -Depth 10
        
        Invoke-RestMethod -Uri $clientUrl -Method Post -Headers $headers -Body $clientConfig
        Write-ColorOutput "Client '$ClientId' created successfully" "Green"
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-ColorOutput "Client '$ClientId' already exists" "Yellow"
            return $true
        }
        Write-ColorOutput "Failed to create client '$ClientId': $($_.Exception.Message)" "Red"
        return $false
    }
}

# Create User
function Create-User {
    param(
        [string]$KeycloakUrl,
        [string]$Token,
        [string]$RealmName,
        [string]$Username,
        [string]$Password,
        [string]$Email,
        [string]$FirstName,
        [string]$LastName
    )
    
    try {
        $userUrl = "$KeycloakUrl/admin/realms/$RealmName/users"
        $headers = @{
            "Authorization" = "Bearer $Token"
            "Content-Type" = "application/json"
        }
        
        $userConfig = @{
            username = $Username
            email = $Email
            firstName = $FirstName
            lastName = $LastName
            enabled = $true
            emailVerified = $true
            credentials = @(
                @{
                    type = "password"
                    value = $Password
                    temporary = $false
                }
            )
        } | ConvertTo-Json -Depth 10
        
        Invoke-RestMethod -Uri $userUrl -Method Post -Headers $headers -Body $userConfig
        Write-ColorOutput "User '$Username' created successfully" "Green"
        return $true
    }
    catch {
        if ($_.Exception.Response.StatusCode -eq 409) {
            Write-ColorOutput "User '$Username' already exists" "Yellow"
            return $true
        }
        Write-ColorOutput "Failed to create user '$Username': $($_.Exception.Message)" "Red"
        return $false
    }
}

# Main execution
$KeycloakUrl = "http://localhost:18080"

Write-ColorOutput "=== Starting Keycloak Realm Setup ===" "Cyan"

# Wait for Keycloak to be ready
Write-ColorOutput "Waiting for Keycloak to be ready..." "Yellow"
do {
    try {
        $response = Invoke-WebRequest -Uri "$KeycloakUrl/realms/master" -Method Get -TimeoutSec 5
        if ($response.StatusCode -eq 200) {
            Write-ColorOutput "Keycloak is ready!" "Green"
            break
        }
    }
    catch {
        Write-ColorOutput "Waiting for Keycloak..." "Yellow"
        Start-Sleep -Seconds 2
    }
} while ($true)

# Get admin token
Write-ColorOutput "Getting admin token..." "Yellow"
$adminToken = Get-AdminToken -KeycloakUrl $KeycloakUrl
if (-not $adminToken) {
    Write-ColorOutput "Failed to get admin token. Exiting." "Red"
    exit 1
}

# Create realm
Write-ColorOutput "Creating ps-realm..." "Yellow"
$realmCreated = Create-Realm -KeycloakUrl $KeycloakUrl -Token $adminToken -RealmName "ps-realm"
if (-not $realmCreated) {
    Write-ColorOutput "Failed to create realm. Exiting." "Red"
    exit 1
}

# Create ps-be client
Write-ColorOutput "Creating ps-be client..." "Yellow"
$beClientCreated = Create-Client -KeycloakUrl $KeycloakUrl -Token $adminToken -RealmName "ps-realm" -ClientId "ps-be" -ClientSecret "ps-be-secret" -RedirectUris @("http://localhost:8081/*", "http://localhost:8081/callback") -WebOrigins @("http://localhost:8081") -Description "PS Backend Service Client"

# Create ps-fe client
Write-ColorOutput "Creating ps-fe client..." "Yellow"
$feClientCreated = Create-Client -KeycloakUrl $KeycloakUrl -Token $adminToken -RealmName "ps-realm" -ClientId "ps-fe" -ClientSecret "ps-fe-secret" -RedirectUris @("http://localhost:3000/*", "http://localhost:3000/callback") -WebOrigins @("http://localhost:3000") -Description "PS Frontend Application Client"

# Create test user
Write-ColorOutput "Creating test user..." "Yellow"
$userCreated = Create-User -KeycloakUrl $KeycloakUrl -Token $adminToken -RealmName "ps-realm" -Username "testuser" -Password "testpass" -Email "test@example.com" -FirstName "Test" -LastName "User"

Write-ColorOutput "=== Keycloak Realm Configuration Completed ===" "Cyan"
Write-ColorOutput "" "White"
Write-ColorOutput "Configuration Summary:" "White"
Write-ColorOutput "- Realm: ps-realm" "White"
Write-ColorOutput "- Backend Client ID: ps-be" "White"
Write-ColorOutput "- Backend Client Secret: ps-be-secret" "White"
Write-ColorOutput "- Frontend Client ID: ps-fe" "White"
Write-ColorOutput "- Frontend Client Secret: ps-fe-secret" "White"
Write-ColorOutput "- Test User: testuser / testpass" "White"
Write-ColorOutput "" "White"
Write-ColorOutput "Admin Console: $KeycloakUrl/admin" "Cyan"
Write-ColorOutput "User Login Page: $KeycloakUrl/realms/ps-realm/account" "Cyan"