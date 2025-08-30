package com.jiuxi.security.sso.controller;

import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.security.core.entity.vo.AccountVO;
import com.jiuxi.security.core.service.AccountService;
import com.jiuxi.security.core.service.TopinfoSecurityCommonService;
import com.jiuxi.security.sso.config.KeycloakSsoProperties;
import com.jiuxi.security.sso.principal.KeycloakUserPrincipal;
import com.jiuxi.security.sso.service.KeycloakOAuth2Service;
import com.jiuxi.admin.core.service.TpTimeRuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    
    @Autowired
    private KeycloakOAuth2Service oAuth2Service;
    
    @Autowired
    @Qualifier("pwdAccountService")
    private AccountService accountService;
    
    @Autowired
    private TopinfoSecurityCommonService topinfoSecurityCommonService;
    
    @Autowired
    private TpTimeRuleService tpTimeRuleService;
    
    public SsoController() {
        System.out.println("SsoController 已创建！");
        logger.info("SsoController 已创建！");
    }
    
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
        System.out.println("=== getLoginUrl 方法被调用 ===");
        logger.info("收到 SSO 登录 URL 请求");
        logger.info("Keycloak SSO 配置 - enabled: {}, serverUrl: {}, realm: {}, clientId: {}", 
                   properties.isEnabled(), properties.getServerUrl(), properties.getRealm(), properties.getClientId());
        
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
        
        logger.info("生成的登录 URL: {}", loginUrl);
        
        Map<String, Object> data = new HashMap<>();
        data.put("loginUrl", loginUrl);
        data.put("redirectUri", redirectUri);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        
        logger.info("返回响应: {}", response);
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
    public void handleCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        logger.info("收到 OIDC 回调请求: code={}, state={}, error={}", code, state, error);
        
        // 检查是否有错误
        if (error != null) {
            logger.error("OIDC 认证失败: {} - {}", error, errorDescription);
            String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode(errorDescription != null ? errorDescription : error, "UTF-8");
            response.sendRedirect(errorUrl);
            return;
        }
        
        // 检查授权码
        if (code == null || code.trim().isEmpty()) {
            logger.error("未收到授权码");
            String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode("未收到授权码", "UTF-8");
            response.sendRedirect(errorUrl);
            return;
        }
        
        try {
            // 1. 使用授权码向 Keycloak 换取访问令牌
            String redirectUri = request.getRequestURL().toString();
            KeycloakOAuth2Service.TokenResponse tokenResponse = oAuth2Service.exchangeCodeForToken(code, redirectUri);
            
            // 2. 使用访问令牌获取用户信息
            KeycloakUserPrincipal userPrincipal = oAuth2Service.getUserInfoFromToken(tokenResponse.getAccessToken());
            String keycloakUsername = userPrincipal.getUsername();
            
            logger.info("从Keycloak获取到用户信息: username={}, email={}", keycloakUsername, userPrincipal.getEmail());
            
            // 3. 在ps-be系统中校验用户名是否存在
            AccountVO accountVO = null;
            try {
                accountVO = accountService.queryAccountByUsername(keycloakUsername);
                if (accountVO == null) {
                    logger.warn("用户名 {} 在ps-be系统中不存在", keycloakUsername);
                    String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode("用户名在系统中不存在: " + keycloakUsername, "UTF-8");
                    response.sendRedirect(errorUrl);
                    return;
                }
                logger.info("在ps-be系统中找到用户: personId={}, userName={}", accountVO.getPersonId(), accountVO.getUserName());
            } catch (Exception e) {
                logger.error("查询用户信息时发生异常: {}", e.getMessage(), e);
                String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode("查询用户信息失败", "UTF-8");
                response.sendRedirect(errorUrl);
                return;
            }
            
            // 4. 验证用户登录时间规则
            try {
                // 获取用户角色列表
                List<String> roleIds = new ArrayList<>();
                if (accountVO.getRoleIds() != null && !accountVO.getRoleIds().trim().isEmpty()) {
                    roleIds = Arrays.asList(accountVO.getRoleIds().split(","));
                }
                
                // 验证登录时间规则
                TpTimeRuleService.LoginTimeValidationResult validationResult = 
                    tpTimeRuleService.validateLoginTimeWithReason(accountVO.getPersonId(), roleIds);
                
                if (!validationResult.isAllowed()) {
                    logger.warn("用户 {} 登录被时间规则拒绝: {}", keycloakUsername, validationResult.getReason());
                    String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + 
                        java.net.URLEncoder.encode("登录失败: " + validationResult.getReason(), "UTF-8");
                    response.sendRedirect(errorUrl);
                    return;
                }
                
                logger.info("用户 {} 通过时间规则验证", keycloakUsername);
            } catch (Exception e) {
                logger.error("验证登录时间规则时发生异常: {}", e.getMessage(), e);
                String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + 
                    java.net.URLEncoder.encode("登录时间验证失败", "UTF-8");
                response.sendRedirect(errorUrl);
                return;
            }
            
            // 5. 生成ps-be系统的token
            try {
                String token = topinfoSecurityCommonService.createToken(accountVO);
                accountVO.setToken(token);
                
                logger.info("成功为用户 {} 生成token", keycloakUsername);
                
                // 登录成功，重定向到成功页面
                String successUrl = properties.getRedirect().getSuccessUrl();
                // 可以在URL中添加token参数，供前端使用
                if (successUrl.contains("?")) {
                    successUrl += "&token=" + java.net.URLEncoder.encode(token, "UTF-8");
                } else {
                    successUrl += "?token=" + java.net.URLEncoder.encode(token, "UTF-8");
                }
                response.sendRedirect(successUrl);
                return;
                
            } catch (Exception e) {
                logger.error("生成token时发生异常: {}", e.getMessage(), e);
                String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode("生成token失败", "UTF-8");
                response.sendRedirect(errorUrl);
                return;
            }
            
        } catch (Exception e) {
            logger.error("处理回调失败", e);
            String errorUrl = properties.getRedirect().getErrorUrl() + "&error_detail=" + java.net.URLEncoder.encode("处理回调失败: " + e.getMessage(), "UTF-8");
            response.sendRedirect(errorUrl);
            return;
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