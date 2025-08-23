package com.example.systema.controller;

import com.example.systema.util.MaskingUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        Map<String, Object> resp = new HashMap<>();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");
            String phone = jwt.getClaimAsString("phone_number");

            Map<String, Object> user = new HashMap<>();
            user.put("username", username);
            user.put("email", MaskingUtils.maskEmail(email));
            user.put("name", MaskingUtils.maskName(name));
            user.put("phone", MaskingUtils.maskPhone(phone));
            resp.put("user", user);
        } else if (authentication != null) {
            Map<String, Object> user = new HashMap<>();
            user.put("username", authentication.getName());
            resp.put("user", user);
        }
        resp.put("system", "A");
        return resp;
    }
}

