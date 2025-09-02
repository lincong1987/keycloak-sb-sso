# Update Keycloak Frontend URL

# Get admin token
$tokenResponse = Invoke-RestMethod -Uri "http://localhost:8180/realms/master/protocol/openid-connect/token" `
    -Method POST `
    -ContentType "application/x-www-form-urlencoded" `
    -Body "grant_type=password&client_id=admin-cli&username=admin&password=admin123"

$adminToken = $tokenResponse.access_token
Write-Host "Admin token obtained successfully"

# Get current realm configuration
$headers = @{
    "Authorization" = "Bearer $adminToken"
    "Content-Type" = "application/json"
}

try {
    # Get current realm config
    $currentRealm = Invoke-RestMethod -Uri "http://localhost:8180/admin/realms/ps-realm" `
        -Method GET `
        -Headers $headers
    
    Write-Host "Current realm config retrieved"
    
    # Update only the frontendUrl
    $currentRealm.frontendUrl = "http://localhost:8080"
    
    # Convert to JSON
    $realmUpdate = $currentRealm | ConvertTo-Json -Depth 10
    
    # Update the realm
    Invoke-RestMethod -Uri "http://localhost:8180/admin/realms/ps-realm" `
        -Method PUT `
        -Headers $headers `
        -Body $realmUpdate
    
    Write-Host "Realm frontend URL updated successfully to http://localhost:8080"
    
} catch {
    Write-Host "Error: $($_.Exception.Message)"
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response body: $responseBody"
    }
}

# Verify the update
$realmInfo = Invoke-WebRequest -Uri 'http://localhost:8180/realms/ps-realm' | ConvertFrom-Json
Write-Host "Current frontend URL: $($realmInfo.frontendUrl)"
Write-Host "Current auth server URL: $($realmInfo.authServerUrl)"