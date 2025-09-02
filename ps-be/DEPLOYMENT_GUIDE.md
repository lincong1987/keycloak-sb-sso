# 最轻量级身份验证方案部署指南

## 概述

本指南介绍如何部署基于 Keycloak + Nginx 反向代理 + Shiro JWT 的最轻量级身份验证方案。该方案的核心优势是：

- **后端极简**：只需实现 JWT 解析功能
- **登录流程外置**：完整的登录流程由反向代理和 Keycloak 协同处理
- **零侵入性**：现有业务代码无需修改
- **高性能**：无状态 JWT 认证，支持水平扩展

## 架构图

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   用户浏览器 │────│ Nginx 反向代理 │────│   Keycloak   │    │  PS BMP 后端  │
│             │    │             │    │   身份认证    │    │             │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │                   │
       │ 1. 访问 /app/*     │                   │                   │
       │ ─────────────────→ │                   │                   │
       │                   │ 2. 检查认证状态    │                   │
       │                   │ ─────────────────→ │                   │
       │ 3. 重定向到登录页   │                   │                   │
       │ ←───────────────── │                   │                   │
       │ 4. 用户登录        │                   │                   │
       │ ─────────────────────────────────────→ │                   │
       │ 5. 返回 JWT Token  │                   │                   │
       │ ←───────────────── │ ←───────────────── │                   │
       │ 6. 携带 Token 访问 │                   │                   │
       │ ─────────────────→ │ 7. 转发请求+Token │                   │
       │                   │ ─────────────────────────────────────→ │
       │ 8. 返回业务数据    │                   │                   │
       │ ←───────────────── │ ←───────────────────────────────────── │
```

## 部署步骤

### 1. 环境准备

#### 系统要求
- Docker 20.10+
- Docker Compose 2.0+
- 至少 4GB 可用内存
- 至少 10GB 可用磁盘空间

#### 端口规划
- `80/443`: Nginx 反向代理
- `8080`: PS BMP 后端应用
- `8180`: Keycloak 认证服务
- `5432`: PostgreSQL 数据库
- `6379`: Redis 缓存

### 2. 快速启动

#### 2.1 克隆项目
```bash
git clone <repository-url>
cd keycloak-sb-sso/ps-test
```

#### 2.2 启动所有服务
```bash
# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

#### 2.3 验证部署
```bash
# 检查 Keycloak 健康状态
curl http://localhost:8180/health/ready

# 检查后端应用健康状态
curl http://localhost:8080/actuator/health

# 检查 Nginx 代理状态
curl http://localhost/health
```

### 3. 详细配置

#### 3.1 Keycloak 配置

**访问管理控制台**
- URL: http://localhost:8180/admin
- 用户名: admin
- 密码: admin123

**Realm 配置**
```json
{
  "realm": "ps-realm",
  "displayName": "PS BMP System",
  "enabled": true,
  "accessTokenLifespan": 3600,
  "refreshTokenMaxReuse": 0,
  "ssoSessionIdleTimeout": 1800,
  "ssoSessionMaxLifespan": 36000
}
```

**客户端配置**
```json
{
  "clientId": "ps-realm-client",
  "name": "PS BMP Client",
  "enabled": true,
  "clientAuthenticatorType": "client-secret",
  "secret": "your-client-secret",
  "redirectUris": [
    "http://localhost/auth/callback",
    "http://localhost/app/*"
  ],
  "webOrigins": [
    "http://localhost"
  ],
  "protocol": "openid-connect",
  "attributes": {
    "access.token.lifespan": "3600",
    "client.secret.creation.time": "1640995200"
  }
}
```

#### 3.2 Nginx 配置

**主配置文件** (`nginx.conf`)
```nginx
worker_processes auto;
worker_rlimit_nofile 65535;

events {
    worker_connections 1024;
    use epoll;
    multi_accept on;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    
    # 日志格式
    log_format sso_format '$remote_addr - $remote_user [$time_local] '
                         '"$request" $status $body_bytes_sent '
                         '"$http_referer" "$http_user_agent" '
                         'rt=$request_time uct="$upstream_connect_time" '
                         'uht="$upstream_header_time" urt="$upstream_response_time" '
                         'user_id="$http_x_user_id" session="$http_x_session_id"';
    
    # 包含站点配置
    include /etc/nginx/conf.d/*.conf;
}
```

**SSO 站点配置** (`conf.d/sso.conf`)
```nginx
upstream ps_bmp_backend {
    server ps-bmp-backend:8080;
    keepalive 32;
}

upstream keycloak_auth {
    server keycloak:8080;
    keepalive 16;
}

server {
    listen 80;
    server_name localhost;
    
    # OIDC 认证配置
    access_by_lua_block {
        local opts = {
            discovery = "http://keycloak:8080/realms/ps-realm/.well-known/openid_configuration",
            client_id = "ps-realm-client",
            client_secret = "your-client-secret",
            redirect_uri = "http://localhost/auth/callback",
            scope = "openid profile email",
            session_contents = {id_token=true, access_token=true, user=true}
        }
        
        local res, err = require("resty.openidc").authenticate(opts)
        if err then
            ngx.log(ngx.ERR, "OIDC authentication failed: ", err)
            ngx.status = 401
            ngx.say("Authentication failed")
            ngx.exit(401)
        end
        
        -- 设置用户信息到请求头
        ngx.req.set_header("Authorization", "Bearer " .. res.access_token)
        ngx.req.set_header("X-User-ID", res.id_token.sub)
        ngx.req.set_header("X-User-Name", res.id_token.preferred_username)
        ngx.req.set_header("X-User-Email", res.id_token.email)
        ngx.req.set_header("X-User-Roles", table.concat(res.id_token.realm_access.roles or {}, ","))
    }
    
    # 应用路径
    location /app/ {
        proxy_pass http://ps_bmp_backend/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    # API 路径
    location /api/ {
        proxy_pass http://ps_bmp_backend/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    # 认证回调
    location /auth/callback {
        access_by_lua_block {
            local opts = {
                discovery = "http://keycloak:8080/realms/ps-realm/.well-known/openid_configuration",
        client_id = "ps-realm-client",
                client_secret = "your-client-secret",
                redirect_uri = "http://localhost/auth/callback"
            }
            require("resty.openidc").authenticate(opts)
        }
        
        return 302 /app/;
    }
    
    # 登出处理
    location /auth/logout {
        access_by_lua_block {
            local opts = {
                discovery = "http://keycloak:8080/realms/ps-bmp/.well-known/openid_configuration",
                client_id = "ps-bmp-client"
            }
            require("resty.openidc").logout(opts)
        }
        
        return 302 /;
    }
    
    # 健康检查
    location /health {
        access_log off;
        return 200 "OK";
        add_header Content-Type text/plain;
    }
}
```

#### 3.3 后端应用配置

**Spring Boot 配置** (`application-sso.yml`)
```yaml
keycloak:
  enabled: true
  base-url: ${KEYCLOAK_BASE_URL:http://keycloak:8080}
  realm: ${KEYCLOAK_REALM:ps-realm}
    client-id: ${KEYCLOAK_CLIENT_ID:ps-realm-client}
  
  jwt:
    clock-skew: 60
    cache-parsed-tokens: true
    cache-ttl: 300
  
  shiro:
    session-stateless: true
    cache-enabled: true
    authorization-cache-enabled: true
    authentication-cache-enabled: true

app:
  reverse-proxy:
    enabled: true
    trusted-proxies: 172.18.0.0/16
    user-header-prefix: X-User-
```

**Shiro 配置类**
```java
@Configuration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true")
public class KeycloakShiroConfig {
    
    @Bean
    public SecurityManager securityManager(List<Realm> realms) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        
        // 无状态会话配置
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        
        return securityManager;
    }
    
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("keycloakJwt", new KeycloakJwtAuthenticationFilter());
        filterFactoryBean.setFilters(filters);
        
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/health", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/api/**", "keycloakJwt");
        filterChainDefinitionMap.put("/**", "keycloakJwt");
        
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactoryBean;
    }
}
```

### 4. 测试验证

#### 4.1 功能测试

**测试未认证访问**
```bash
# 访问受保护的资源，应该重定向到登录页
curl -v http://localhost/app/dashboard
```

**测试认证流程**
1. 浏览器访问: http://localhost/app/dashboard
2. 自动重定向到 Keycloak 登录页
3. 输入用户名密码登录
4. 登录成功后重定向回原页面
5. 后续访问自动携带 JWT Token

**测试 API 访问**
```bash
# 获取 JWT Token（通过浏览器登录后从开发者工具获取）
TOKEN="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."

# 使用 Token 访问 API
curl -H "Authorization: Bearer $TOKEN" \
     http://localhost/api/sso/user/info
```

#### 4.2 性能测试

**并发测试**
```bash
# 使用 ab 进行并发测试
ab -n 1000 -c 10 -H "Authorization: Bearer $TOKEN" \
   http://localhost/api/sso/user/info
```

**压力测试**
```bash
# 使用 wrk 进行压力测试
wrk -t12 -c400 -d30s -H "Authorization: Bearer $TOKEN" \
    http://localhost/api/sso/user/info
```

### 5. 监控和日志

#### 5.1 日志配置

**Nginx 访问日志**
```bash
# 查看 SSO 访问日志
docker-compose logs nginx | grep sso_format

# 实时监控访问日志
docker-compose logs -f nginx
```

**应用日志**
```bash
# 查看应用日志
docker-compose logs ps-bmp-backend

# 查看 SSO 相关日志
docker-compose logs ps-bmp-backend | grep "com.jiuxi.security.sso"
```

**Keycloak 日志**
```bash
# 查看 Keycloak 日志
docker-compose logs keycloak

# 查看认证事件日志
docker-compose logs keycloak | grep "LOGIN\|LOGOUT"
```

#### 5.2 监控指标

**健康检查端点**
- Nginx: http://localhost/health
- 后端应用: http://localhost:8080/actuator/health
- Keycloak: http://localhost:8180/health/ready

**性能指标**
- 应用指标: http://localhost:8080/actuator/metrics
- Prometheus 指标: http://localhost:9090
- Grafana 面板: http://localhost:3001

### 6. 故障排除

#### 6.1 常见问题

**问题1: 无法重定向到 Keycloak 登录页**
```bash
# 检查 Nginx 配置
docker-compose exec nginx nginx -t

# 检查 Keycloak 连接
curl http://localhost:8180/realms/ps-realm/.well-known/openid_configuration
```

**问题2: JWT Token 验证失败**
```bash
# 检查公钥获取
curl http://localhost:8180/realms/ps-realm/protocol/openid-connect/certs

# 检查应用日志
docker-compose logs ps-bmp-backend | grep "JWT"
```

**问题3: 用户信息获取失败**
```bash
# 检查 Token 内容
echo "$TOKEN" | cut -d. -f2 | base64 -d | jq .

# 检查用户映射配置
curl -H "Authorization: Bearer $TOKEN" \
     http://localhost:8080/api/sso/user/info
```

#### 6.2 调试模式

**启用调试日志**
```yaml
# application-sso.yml
logging:
  level:
    com.jiuxi.security.sso: DEBUG
    org.apache.shiro: DEBUG
    io.jsonwebtoken: DEBUG
```

**Nginx 调试**
```nginx
# nginx.conf
error_log /var/log/nginx/error.log debug;
access_log /var/log/nginx/access.log sso_format;
```

### 7. 生产部署

#### 7.1 安全配置

**HTTPS 配置**
```nginx
server {
    listen 443 ssl http2;
    server_name yourdomain.com;
    
    ssl_certificate /etc/nginx/ssl/cert.pem;
    ssl_certificate_key /etc/nginx/ssl/key.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers ECDHE-RSA-AES256-GCM-SHA512:DHE-RSA-AES256-GCM-SHA512;
    
    # 其他配置...
}
```

**环境变量配置**
```bash
# .env 文件
KEYCLOAK_BASE_URL=https://keycloak.yourdomain.com
KEYCLOAK_CLIENT_SECRET=your-production-secret
POSTGRES_PASSWORD=your-production-password
REDIS_PASSWORD=your-production-redis-password
```

#### 7.2 性能优化

**Nginx 优化**
```nginx
worker_processes auto;
worker_rlimit_nofile 65535;

events {
    worker_connections 4096;
    use epoll;
    multi_accept on;
}

http {
    sendfile on;
    tcp_nopush on;
    tcp_nodelay on;
    keepalive_timeout 65;
    
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css application/json application/javascript;
}
```

**JVM 优化**
```bash
JAVA_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UseStringDeduplication"
```

#### 7.3 备份和恢复

**数据库备份**
```bash
# 备份 PostgreSQL
docker-compose exec postgres pg_dump -U keycloak keycloak > keycloak_backup.sql

# 恢复数据库
docker-compose exec -T postgres psql -U keycloak keycloak < keycloak_backup.sql
```

**配置备份**
```bash
# 导出 Keycloak Realm 配置
curl -X GET "http://localhost:8180/admin/realms/ps-realm" \
     -H "Authorization: Bearer $ADMIN_TOKEN" > realm_backup.json
```

## 总结

本部署指南提供了完整的最轻量级身份验证方案实施步骤。该方案的核心优势：

1. **极简后端实现**：只需配置 JWT Realm，无需复杂的登录逻辑
2. **零业务代码侵入**：现有业务逻辑完全不需要修改
3. **高性能无状态**：基于 JWT 的无状态认证，支持水平扩展
4. **统一身份管理**：Keycloak 提供完整的用户管理和 SSO 功能
5. **灵活的部署方式**：支持 Docker 容器化部署，易于维护和扩展

通过反向代理层处理认证流程，后端应用只需要专注于业务逻辑，大大简化了系统的复杂度，是一个真正轻量级且实用的身份验证解决方案。