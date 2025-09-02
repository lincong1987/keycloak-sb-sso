# Update Keycloak Client Redirect URI Configuration

Write-Host "Updating Keycloak client configuration..."

try {
    # Get admin token
    $tokenResponse = Invoke-RestMethod -Uri "http://localhost:8180/realms/master/protocol/openid-connect/token" -Method Post -Body "client_id=admin-cli&username=admin&password=admin&grant_type=password" -ContentType "application/x-www-form-urlencoded"
    $adminToken = $tokenResponse.access_token
    Write-Host "Successfully obtained admin token"
    
    # Get client information
    $clients = Invoke-RestMethod -Uri "http://localhost:8180/admin/realms/ps-realm/clients" -Method Get -Headers @{"Authorization"="Bearer $adminToken"}
$client = $clients | Where-Object {$_.clientId -eq "ps-realm-client"}
    
    if ($client) {
        Write-Host "Found client: ps-realm-client"
        
        # Update redirect URIs
        $client.redirectUris = @(
            "http://localhost/callback",
            "http://localhost/*"
        )
        $client.webOrigins = @(
            "http://localhost"
        )
        
        # Update client configuration
        $clientJson = $client | ConvertTo-Json -Depth 10
        Invoke-RestMethod -Uri "http://localhost:8180/admin/realms/ps-realm/clients/$($client.id)" -Method Put -Body $clientJson -Headers @{"Authorization"="Bearer $adminToken"; "Content-Type"="application/json"}
        
        Write-Host "Client configuration updated successfully"
        Write-Host "Redirect URIs: http://localhost/callback, http://localhost/*"
        Write-Host "Web Origins: http://localhost"
    } else {
        Write-Host "Client ps-realm-client not found"
    }
} catch {
    Write-Host "Configuration update failed: $($_.Exception.Message)"
    exit 1
}

Write-Host "Configuration update completed!"