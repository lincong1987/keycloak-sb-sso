# Spring Boot + Keycloak SSO 最小示例

这是一个演示如何使用Keycloak实现单点登录(SSO)的最小Spring Boot示例项目。项目包含两个独立的Spring Boot应用程序（系统A和系统B），它们都集成了Keycloak进行身份认证。

## 项目结构

```
springboot-keycloak-sso/
├── system-a/                 # 系统A (端口: 8081)
│   ├── src/main/java/
│   │   └── com/example/systema/
│   │       ├── SystemAApplication.java
│   │       ├── config/SecurityConfig.java
│   │       └── controller/HomeController.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── templates/
│   │       ├── home.html
│   │       ├── dashboard.html
│   │       └── profile.html
│   └── pom.xml
├── system-b/                 # 系统B (端口: 8082)
│   ├── src/main/java/
│   │   └── com/example/systemb/
│   │       ├── SystemBApplication.java
│   │       ├── config/SecurityConfig.java
│   │       └── controller/HomeController.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── templates/
│   │       ├── home.html
│   │       ├── dashboard.html
│   │       └── profile.html
│   └── pom.xml
└── README.md
```

## 前置条件

1. **Java 17+**
2. **Maven 3.6+**
3. **Keycloak服务器** (假设已在本地运行在 `http://localhost:8080`)

## Keycloak配置

### 1. 创建Realm

1. 登录Keycloak管理控制台 (`http://localhost:8080/admin`)
2. 创建一个新的Realm，命名为 `demo`

### 2. 创建客户端

#### 系统A客户端配置

1. 在 `demo` realm中创建新客户端
2. **Client ID**: `system-a-client`
3. **Client Type**: `OpenID Connect`
4. **Client authentication**: `On`
5. **Valid redirect URIs**: `http://localhost:8081/system-a/login/oauth2/code/keycloak`
6. **Web origins**: `http://localhost:8081`
7. 在 **Credentials** 标签页记录 **Client Secret**

#### 系统B客户端配置

1. 在 `demo` realm中创建新客户端
2. **Client ID**: `system-b-client`
3. **Client Type**: `OpenID Connect`
4. **Client authentication**: `On`
5. **Valid redirect URIs**: `http://localhost:8082/system-b/login/oauth2/code/keycloak`
6. **Web origins**: `http://localhost:8082`
7. 在 **Credentials** 标签页记录 **Client Secret**

### 3. 创建用户

1. 在 `demo` realm中创建测试用户
2. 设置用户名、邮箱、姓名等信息
3. 在 **Credentials** 标签页设置密码

## 应用程序配置

### 更新Client Secret

在运行应用程序之前，需要更新配置文件中的客户端密钥：

**system-a/src/main/resources/application.yml**
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-secret: YOUR_SYSTEM_A_CLIENT_SECRET  # 替换为实际的密钥
```

**system-b/src/main/resources/application.yml**
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-secret: YOUR_SYSTEM_B_CLIENT_SECRET  # 替换为实际的密钥
```

## 运行应用程序

### 方法1: 使用Maven

#### 启动系统A
```bash
cd system-a
mvn spring-boot:run
```

#### 启动系统B
```bash
cd system-b
mvn spring-boot:run
```

### 方法2: 使用JAR包

#### 构建并运行系统A
```bash
cd system-a
mvn clean package
java -jar target/system-a-1.0.0.jar
```

#### 构建并运行系统B
```bash
cd system-b
mvn clean package
java -jar target/system-b-1.0.0.jar
```

## 访问应用程序

- **系统A**: http://localhost:8081/system-a/
- **系统B**: http://localhost:8082/system-b/
- **Keycloak管理控制台**: http://localhost:8080/admin

## SSO测试流程

1. **首次登录**:
   - 访问系统A: http://localhost:8081/system-a/
   - 点击"使用Keycloak登录"
   - 输入Keycloak用户凭据
   - 成功登录后查看仪表板和个人资料

2. **SSO验证**:
   - 在系统A中点击"访问系统B"链接
   - 观察到无需重新登录即可访问系统B
   - 在系统B中查看相同的用户信息

3. **单点登出**:
   - 在任一系统中点击"退出登录"
   - 验证两个系统都已登出

## 功能特性

### 系统A功能
- 首页展示和登录入口
- 用户仪表板显示基本信息
- 详细的用户资料页面
- 跨系统导航链接

### 系统B功能
- 首页展示和登录入口
- 用户仪表板显示基本信息
- 详细的用户资料页面
- 系统特色功能展示
- 用户权限信息显示

### 安全特性
- OAuth2/OpenID Connect集成
- 自动令牌刷新
- 安全的会话管理
- CSRF保护

## 技术栈

- **Spring Boot 3.2.0**
- **Spring Security 6**
- **Spring OAuth2 Client**
- **Thymeleaf模板引擎**
- **Bootstrap 5前端框架**
- **Keycloak身份提供者**

## 故障排除

### 常见问题

1. **无法连接到Keycloak**
   - 确认Keycloak服务正在运行
   - 检查URL配置是否正确

2. **客户端认证失败**
   - 验证Client ID和Client Secret是否正确
   - 确认重定向URI配置匹配

3. **跨域问题**
   - 检查Keycloak客户端的Web origins配置
   - 确认应用程序端口配置正确

### 调试日志

应用程序已配置详细的OAuth2调试日志。查看控制台输出以获取详细的认证流程信息。

## 扩展建议

1. **添加角色和权限控制**
2. **集成数据库持久化**
3. **添加API端点保护**
4. **实现细粒度的访问控制**
5. **添加用户注册功能**
6. **集成邮件通知**

## 许可证

本项目仅用于学习和演示目的。