package com.example.systemb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/verify")
    public Map<String, Object> verify(Authentication authentication) {
        Map<String, Object> resp = new HashMap<>();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var jwt = jwtAuth.getToken();
            resp.put("active", true);
            resp.put("sub", jwt.getSubject());
            resp.put("preferred_username", jwt.getClaimAsString("preferred_username"));
            resp.put("email", jwt.getClaimAsString("email"));
            resp.put("scope", jwt.getClaimAsString("scope"));
            resp.put("iat", jwt.getIssuedAt());
            resp.put("exp", jwt.getExpiresAt());
            resp.put("iss", jwt.getIssuer());
            resp.put("aud", jwt.getAudience());
        } else if (authentication != null) {
            resp.put("active", true);
            resp.put("principal", authentication.getName());
        } else {
            resp.put("active", false);
        }
        resp.put("system", "B");
        return resp;
    }

    @GetMapping("/token")
    public Map<String, Object> token(@RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient client) {
        Map<String, Object> resp = new HashMap<>();
        if (client != null && client.getAccessToken() != null) {
            resp.put("access_token", client.getAccessToken().getTokenValue());
            resp.put("token_type", client.getAccessToken().getTokenType().getValue());
            resp.put("expires_at", client.getAccessToken().getExpiresAt());
            resp.put("scopes", client.getAccessToken().getScopes());
        }
        return resp;
    }
}
