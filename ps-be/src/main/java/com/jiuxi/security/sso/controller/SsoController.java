package com.jiuxi.security.sso.controller;

import com.jiuxi.security.sso.config.KeycloakSsoProperties;
import com.jiuxi.security.sso.principal.KeycloakUserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * SSO 控制器
 * 
 * 处理 SSO 登录、登出和用户信息获取
 * 
 * @author SSO Integration
 * @since 2.2.2
 */
@RestController
@RequestMapping("/api/sso")
@ConditionalOnProperty(name = "keycloak.sso.enabled", havingValue = "true", matchIfMissing = false)
public class SsoController {
    
    private static final Logger logger = LoggerFactory.getLogger(SsoController.class);
    
    @Autowired
    private KeycloakSsoProperties properties;
    
    /**
     * 获取当前用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        
        if (!subject.isAuthenticated()) {
            return ResponseEntity.status(401).body(createErrorResponse("用户未认证"));
        }
        
        Object principal = subject.getPrincipal();
        if (!(principal instanceof KeycloakUserPrincipal)) {
            return ResponseEntity.status(500).body(createErrorResponse("无效的用户主体类型"));
        }
        
        KeycloakUserPrincipal userPrincipal = (KeycloakUserPrincipal) principal;
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userPrincipal.getUserId());
        userInfo.put("username", userPrincipal.getUsername());
        userInfo.put("email", userPrincipal.getEmail());
        userInfo.put("name", userPrincipal.getName());
        userInfo.put("roles", userPrincipal.getRoles());
        userInfo.put("permissions", userPrincipal.getPermissions());
        userInfo.put("attributes", userPrincipal.getAttributes());
        userInfo.put("tokenExpired", userPrincipal.isTokenExpired());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", userInfo);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 检查认证状态
     * 
     * @return 认证状态
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        Subject subject = SecurityUtils.getSubject();
        
        Map<String, Object> status = new HashMap<>();
        status.put("authenticated", subject.isAuthenticated());
        status.put("remembered", subject.isRemembered());
        
        if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipal();
            if (principal instanceof KeycloakUserPrincipal) {
                KeycloakUserPrincipal userPrincipal = (KeycloakUserPrincipal) principal;
                status.put("username", userPrincipal.getUsername());
                status.put("tokenExpired", userPrincipal.isTokenExpired());
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", status);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取登录 URL
     * 
     * @param request HTTP 请求
     * @return 登录 URL
     */
    @GetMapping("/login-url")
    public ResponseEntity<Map<String, Object>> getLoginUrl(HttpServletRequest request) {
        String redirectUri = request.getParameter("redirect_uri");
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            redirectUri = request.getHeader("Referer");
        }
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            redirectUri = "/";
        }
        
        // 构建 Keycloak 登录 URL
        String loginUrl = String.format(
            "%s/realms/%s/protocol/openid-connect/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=openid",
            properties.getServerUrl(),
            properties.getRealm(),
            properties.getClientId(),
            redirectUri
        );
        
        Map<String, Object> data = new HashMap<>();
        data.put("loginUrl", loginUrl);
        data.put("redirectUri", redirectUri);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 登出
     * 
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        
        try {
            // Shiro 登出
            subject.logout();
            
            // 构建 Keycloak 登出 URL
            String logoutUrl = String.format(
                "%s/realms/%s/protocol/openid-connect/logout",
                properties.getServerUrl(),
                properties.getRealm()
            );
            
            Map<String, Object> data = new HashMap<>();
            data.put("logoutUrl", logoutUrl);
            data.put("message", "登出成功");
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("data", data);
            
            return ResponseEntity.ok(responseData);
            
        } catch (Exception e) {
            logger.error("登出失败", e);
            return ResponseEntity.status(500).body(createErrorResponse("登出失败: " + e.getMessage()));
        }
    }
    
    /**
     * 处理 OIDC 回调
     * 
     * @param code 授权码
     * @param state 状态参数
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @return 回调处理结果
     */
    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            HttpServletRequest request, HttpServletResponse response) {
        
        logger.info("收到 OIDC 回调请求: code={}, state={}, error={}", code, state, error);
        
        // 检查是否有错误
        if (error != null) {
            logger.error("OIDC 认证失败: {} - {}", error, errorDescription);
            Map<String, Object> errorResponse = createErrorResponse("认证失败: " + errorDescription);
            return ResponseEntity.status(400).body(errorResponse);
        }
        
        // 检查授权码
        if (code == null || code.trim().isEmpty()) {
            logger.error("未收到授权码");
            return ResponseEntity.status(400).body(createErrorResponse("未收到授权码"));
        }
        
        try {
            // 这里应该用授权码换取访问令牌，然后创建 Shiro 会话
            // 目前先返回成功响应，表示回调端点工作正常
            Map<String, Object> data = new HashMap<>();
            data.put("message", "回调处理成功");
            data.put("code", code);
            data.put("redirectUrl", "/"); // 重定向到主页
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("data", data);
            
            return ResponseEntity.ok(responseData);
            
        } catch (Exception e) {
            logger.error("处理回调失败", e);
            return ResponseEntity.status(500).body(createErrorResponse("处理回调失败: " + e.getMessage()));
        }
    }
    
    /**
     * 重定向到登录页面
     * 
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws IOException IO 异常
     */
    @GetMapping("/login")
    public void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUri = request.getParameter("redirect_uri");
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            redirectUri = request.getHeader("Referer");
        }
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            redirectUri = "/";
        }
        
        String loginUrl = String.format(
            "%s/realms/%s/protocol/openid-connect/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=openid",
            properties.getServerUrl(),
            properties.getRealm(),
            properties.getClientId(),
            redirectUri
        );
        
        response.sendRedirect(loginUrl);
    }
    
    /**
     * 创建错误响应
     * 
     * @param message 错误消息
     * @return 错误响应
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }
}