# 最轻量级身份验证方案

基于 Keycloak + Nginx 反向代理 + Shiro JWT 的企业级身份验证解决方案。

## 🚀 核心优势

- **极简后端**：只需实现 JWT 解析，无需复杂登录逻辑
- **零侵入性**：现有业务代码无需修改
- **高性能**：无状态 JWT 认证，支持水平扩展
- **统一管理**：Keycloak 提供完整的用户管理和 SSO

## 📋 架构概览

```
用户 → Nginx 反向代理 → Keycloak 认证 → PS BMP 后端
     ↑                ↓
     └── JWT Token ────┘
```

**工作流程：**
1. 用户访问 `/app/*` 路径
2. Nginx 检查认证状态，未登录则重定向到 Keycloak
3. 用户在 Keycloak 完成登录
4. Nginx 获取 JWT Token 并转发给后端
5. 后端 Shiro 验证 JWT 并完成授权

## ⚡ 快速开始

### 1. 环境要求
- Docker 20.10+
- Docker Compose 2.0+
- 4GB+ 内存

### 2. 一键启动
```bash
# 克隆项目
git clone <repository-url>
cd keycloak-sb-sso/ps-test

# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps
```

### 3. 验证部署
```bash
# 检查服务健康状态
curl http://localhost/health          # Nginx
curl http://localhost:8080/actuator/health  # 后端应用
curl http://localhost:8180/health/ready     # Keycloak
```

### 4. 测试认证
1. 浏览器访问：http://localhost/app/dashboard
2. 自动重定向到 Keycloak 登录页
3. 使用测试账号登录：
   - 用户名：`testuser`
   - 密码：`password123`
4. 登录成功后自动跳转回应用

## 🔧 核心组件

### Nginx 反向代理
- **位置**：`reverse-proxy-config/nginx/`
- **功能**：OIDC 认证处理、JWT 转发、负载均衡
- **配置**：支持自定义认证规则和路由策略

### Keycloak 身份认证
- **位置**：`reverse-proxy-config/keycloak/`
- **功能**：用户管理、SSO、JWT 签发
- **管理界面**：http://localhost:8180/admin (admin/admin123)

### Shiro JWT Realm
- **位置**：`ps-bmp-backend/src/main/java/com/jiuxi/security/sso/`
- **功能**：JWT 验证、用户授权、权限控制
- **特点**：无状态、高性能、易扩展

## 📁 项目结构

```
ps-test/
├── docker-compose.yml              # Docker 编排文件
├── DEPLOYMENT_GUIDE.md             # 详细部署指南
├── README.md                       # 本文件
├── reverse-proxy-config/           # 反向代理配置
│   ├── nginx/                      # Nginx 配置
│   │   ├── nginx.conf             # 主配置文件
│   │   ├── conf.d/
│   │   │   ├── upstream.conf      # 上游服务配置
│   │   │   └── sso.conf           # SSO 站点配置
│   │   └── lua/
│   │       └── auth.lua           # 认证脚本
│   └── keycloak/
│       └── realm-config.json      # Realm 配置
└── ps-bmp-backend/                 # 后端应用
    ├── src/main/java/com/jiuxi/security/sso/
    │   ├── realm/                  # Shiro Realm 实现
    │   ├── service/                # JWT 验证服务
    │   ├── controller/             # SSO API 控制器
    │   └── config/                 # Shiro 配置
    └── src/main/resources/
        └── application-sso.yml     # SSO 配置文件
```

## 🔑 关键配置

### JWT Token 流转
```
Nginx 获取 JWT → 设置请求头 → 后端验证
                ↓
    Authorization: Bearer <token>
    X-User-ID: <user_id>
    X-User-Name: <username>
    X-User-Email: <email>
    X-User-Roles: <roles>
```

### Shiro 配置要点
```java
// 无状态会话
sessionStorageEvaluator.setSessionStorageEnabled(false);

// JWT Realm
@Bean
public KeycloakJwtRealm keycloakJwtRealm() {
    return new KeycloakJwtRealm();
}

// 认证过滤器
filterChainDefinitionMap.put("/api/**", "keycloakJwt");
```

## 🛠️ 开发指南

### 添加新的受保护路径
```nginx
# nginx/conf.d/sso.conf
location /new-api/ {
    # 自动继承 OIDC 认证
    proxy_pass http://ps_bmp_backend/new-api/;
    # 其他代理配置...
}
```

### 自定义权限控制
```java
@RestController
public class MyController {
    
    @RequiresRoles("admin")
    @GetMapping("/admin/users")
    public List<User> getUsers() {
        // 只有 admin 角色可访问
    }
    
    @RequiresPermissions("user:read")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        // 需要 user:read 权限
    }
}
```

### 获取当前用户信息
```java
@RestController
public class UserController {
    
    @GetMapping("/current-user")
    public KeycloakUserPrincipal getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        return (KeycloakUserPrincipal) subject.getPrincipal();
    }
}
```

## 📊 监控和运维

### 健康检查
```bash
# 服务状态检查
curl http://localhost/health
curl http://localhost:8080/actuator/health
curl http://localhost:8180/health/ready

# JWT 缓存统计
curl http://localhost:8080/api/sso/cache/stats

# 用户会话信息
curl -H "Authorization: Bearer $TOKEN" \
     http://localhost:8080/api/sso/user/info
```

### 日志监控
```bash
# 查看认证日志
docker-compose logs nginx | grep "OIDC"

# 查看 JWT 验证日志
docker-compose logs ps-bmp-backend | grep "JWT"

# 查看用户登录事件
docker-compose logs keycloak | grep "LOGIN"
```

## 🔒 安全特性

- **JWT 签名验证**：使用 Keycloak 公钥验证 Token 完整性
- **Token 过期检查**：自动检查 Token 有效期
- **角色权限控制**：基于 Keycloak 角色的细粒度权限
- **会话管理**：支持单点登录和统一登出
- **HTTPS 支持**：生产环境强制 HTTPS
- **CSRF 防护**：内置 CSRF 攻击防护

## 🚀 性能特点

- **无状态认证**：JWT Token 无需服务端存储
- **缓存优化**：公钥和 Token 验证结果缓存
- **连接池**：Nginx 上游连接池优化
- **压缩传输**：Gzip 压缩减少网络开销
- **水平扩展**：支持多实例负载均衡

## 📚 更多文档

- [详细部署指南](DEPLOYMENT_GUIDE.md) - 完整的部署和配置说明
- [API 文档](http://localhost:8080/swagger-ui.html) - 后端 API 接口文档
- [Keycloak 管理](http://localhost:8180/admin) - Keycloak 管理控制台

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目。

## 📄 许可证

MIT License

---

**这就是最轻量级的身份验证方案 - 让认证变得简单而强大！** 🎉

## 项目概述

Topinfo Platform Admin 是一个基于 Spring Boot 的企业级管理平台后端系统，提供完整的用户管理、权限控制、部门管理、角色管理等基础功能。系统采用微服务架构设计，支持多数据源、分布式缓存、安全认证等企业级特性。

## 技术栈

### 核心框架
- **Spring Boot**: 2.7.18-topinfo
- **Spring Cloud**: 2021.0.8
- **Spring Cloud Alibaba**: 2021.0.5.0
- **MyBatis Plus**: 3.5.3.1
- **Spring Security**: 集成安全认证

### 数据库
- **MariaDB**: 3.1.4 (主要数据库)
- **MySQL**: 8.0.33 (兼容支持)
- **Druid**: 1.2.20 (连接池)

### 缓存与消息
- **Redis**: 通过 Redisson 3.20.1 集成
- **RocketMQ**: 2.2.3 (消息队列)

### 工具库
- **Hutool**: 5.8.18 (Java工具库)
- **Fastjson**: 1.2.83 (JSON处理)
- **Guava**: 31.1-jre (Google工具库)
- **Lombok**: 1.18.26 (代码简化)
- **JWT**: 4.4.0 (Token认证)

### 文档与监控
- **Swagger**: 3.0.0 (API文档)
- **Logback**: 日志管理
- **Netty**: 4.1.94.Final (网络通信)

## 模块结构

### 核心模块

#### 1. topinfo-parent
- **功能**: 父级POM，统一管理依赖版本
- **作用**: 版本控制、依赖管理

#### 2. topinfo-platform-admin
- **功能**: 核心管理模块
- **包含**: 用户管理、部门管理、角色管理、菜单管理
- **主要实体**: 
  - 用户信息 (tp_person_basicinfo)
  - 部门信息 (tp_dept_basicinfo)
  - 角色管理 (tp_role)
  - 菜单权限 (tp_menu, tp_role_menu)

#### 3. topinfo-platform-common
- **功能**: 公共工具模块
- **包含**: 通用工具类、常量定义、异常处理

#### 4. topinfo-platform-security-starter
- **功能**: 安全认证模块
- **特性**: 
  - JWT Token认证
  - 权限控制
  - 密码加密
  - 登录限制

#### 5. topinfo-platform-mybatis-starter
- **功能**: 数据访问层
- **特性**:
  - 多数据源支持
  - 分页插件
  - 租户模式
  - 数据权限

#### 6. topinfo-platform-mvc-starter
- **功能**: Web层配置
- **特性**:
  - 拦截器配置
  - 跨域处理
  - 参数验证

#### 7. topinfo-platform-core-starter
- **功能**: 核心功能模块
- **特性**:
  - XSS防护
  - 缓存管理
  - 工具类集成

#### 8. topinfo-admin-demo
- **功能**: 示例应用
- **包含**: 完整的应用示例和测试用例

#### 9. topinfo-admin-ui-demo
- **功能**: 前端示例
- **技术**: Vue.js + Element UI

## 数据库设计

### 核心表结构

#### 用户相关
- `tp_person_basicinfo`: 人员基本信息
- `tp_account`: 账户信息
- `tp_account_exinfo`: 账户扩展信息
- `tp_person_dept`: 人员部门关系
- `tp_person_role`: 人员角色关系

#### 组织架构
- `tp_dept_basicinfo`: 部门基本信息
- `tp_ent_basicinfo`: 企业基本信息

#### 权限管理
- `tp_role`: 角色信息
- `tp_menu`: 菜单信息
- `tp_role_menu`: 角色菜单关系

#### 系统管理
- `tp_dictionary`: 数据字典
- `tp_custom_module`: 自定义模块
- `tp_scheduled_task`: 定时任务
- `tp_licence`: 许可证管理

## 安装指南

### 环境要求
- JDK 8+
- Maven 3.6+
- MariaDB 10.3+ 或 MySQL 8.0+
- Redis 6.0+

### 安装步骤

1. **克隆项目**
```bash
git clone [项目地址]
cd admin-all
```

2. **数据库初始化**
```sql
-- 创建数据库
CREATE DATABASE your_database_name DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;

-- 执行SQL脚本
source topinfo-platform-admin/sql/all/mysql-schema-all.sql
```

3. **配置文件修改**
```yaml
# 修改 application-dev.yml
topinfo:
  mybatis:
    datasource-config:
      url: jdbc:mariadb://your-host:port/your-database
      username: your-username
      password: your-password
```

4. **编译打包**
```bash
mvn clean install
```

5. **启动应用**
```bash
cd topinfo-admin-demo
mvn spring-boot:run
```

## 配置说明

### 数据源配置
```yaml
topinfo:
  mybatis:
    datasource-config:
      url: jdbc:mariadb://localhost:3306/database
      username: username
      password: password
      minIdle: 5
      maxActive: 500
```

### 安全配置
```yaml
topinfo:
  security:
    enable: true
    password-encryption: true
    authentication:
      expTime: 720  # Token过期时间(小时)
      errCount: 5   # 密码错误次数限制
      excludePaths: # 排除认证路径
        - /static/**
        - /sys/captcha/**
```

### Redis配置
```yaml
spring:
  redis:
    redisson:
      config: classpath:redis/redisson-single.yml
```

## API接口文档

### 认证接口

#### 用户登录
- **接口**: `POST /sys/login`
- **参数**: 
  ```json
  {
    "loginName": "用户名",
    "password": "密码",
    "captcha": "验证码"
  }
  ```
- **响应**: 
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "token": "jwt_token",
      "userInfo": {...}
    }
  }
  ```

### 用户管理接口

#### 用户列表查询
- **接口**: `GET /sys/person/ent-list`
- **请求头**: `Token: jwt_token`
- **参数**: 
  - `page`: 页码
  - `pageSize`: 每页大小
  - `deptId`: 部门ID
  - `personName`: 用户姓名
  - `sex`: 性别
  - `phone`: 手机号
- **响应**: 
  ```json
  {
    "code": 200,
    "data": {
      "records": [...],
      "total": 100,
      "current": 1,
      "size": 10
    }
  }
  ```

#### 用户详情查询
- **接口**: `GET /sys/person/view`
- **请求头**: `Token: jwt_token`
- **参数**: `personId`: 用户ID
- **响应**: 
  ```json
  {
    "code": 200,
    "data": {
      "personId": "用户ID",
      "personName": "用户姓名",
      "phone": "手机号",
      ...
    }
  }
  ```

### 部门管理接口

#### 部门树查询
- **接口**: `GET /sys/dept/tree`
- **请求头**: `Token: jwt_token`
- **参数**: 
  - `deptId`: 部门ID (可选)
  - `returnTopNode`: 是否返回顶级节点
- **响应**: 
  ```json
  {
    "code": 200,
    "data": [
      {
        "id": "部门ID",
        "text": "部门名称",
        "children": [...]
      }
    ]
  }
  ```

### 角色管理接口

#### 角色列表查询
- **接口**: `GET /sys/role/list`
- **请求头**: `Token: jwt_token`
- **参数**: 
  - `page`: 页码
  - `pageSize`: 每页大小
  - `roleName`: 角色名称
- **响应**: 分页数据格式

#### 角色权限配置
- **接口**: `POST /sys/role/auth`
- **请求头**: `Token: jwt_token`
- **参数**: 
  ```json
  {
    "roleId": "角色ID",
    "menuIds": ["菜单ID1", "菜单ID2"]
  }
  ```

### 菜单管理接口

#### 菜单树查询
- **接口**: `GET /sys/menu/tree`
- **请求头**: `Token: jwt_token`
- **响应**: 树形菜单结构

#### 菜单详情
- **接口**: `GET /sys/menu/view`
- **请求头**: `Token: jwt_token`
- **参数**: `menuId`: 菜单ID

#### 菜单新增
- **接口**: `POST /sys/menu/add`
- **请求头**: `Token: jwt_token`
- **参数**: 
  ```json
  {
    "menuName": "菜单名称",
    "menuUri": "菜单路径",
    "menuPid": "父菜单ID",
    "menuType": "菜单类型",
    "orderIndex": 排序
  }
  ```

### 系统管理接口

#### 数据字典查询
- **接口**: `GET /platform/tree-node/tree-dic-code`
- **参数**: 
  - `dicCode`: 字典编码
  - `returnTopNode`: 是否返回顶级节点

#### 日志级别动态调整
- **接口**: `POST /platform/loggerLevel/update`
- **参数**: 
  - `ticket`: 操作票据
  - `loggerName`: 日志器名称
  - `level`: 日志级别

## 使用说明

### 开发环境启动

1. **启动Redis服务**
2. **启动数据库服务**
3. **修改配置文件**
4. **运行主类**: `TopinfoAdminDemoApplication`
5. **访问**: `http://localhost:8088/chemicalpark-manage-app`

### 生产环境部署

1. **打包应用**
```bash
mvn clean package -Dmaven.test.skip=true
```

2. **部署运行**
```bash
java -jar topinfo-admin-demo-1.0.0.jar --spring.profiles.active=prod
```

### 权限配置

1. **创建角色**: 在角色管理中创建业务角色
2. **分配权限**: 为角色分配菜单权限
3. **用户授权**: 为用户分配角色
4. **数据权限**: 配置用户的数据访问范围

## 安全特性

### 认证机制
- JWT Token认证
- 密码加密存储
- 登录失败锁定
- Token自动刷新

### 权限控制
- RBAC权限模型
- 菜单级权限控制
- 按钮级权限控制
- 数据级权限控制

### 安全防护
- XSS攻击防护
- SQL注入防护
- CSRF防护
- 接口访问限制

## 监控与日志

### 日志配置
- 支持动态调整日志级别
- 分级日志输出
- 异常日志收集
- 操作日志记录

### 性能监控
- 数据库连接池监控
- Redis连接监控
- 接口性能监控

## 扩展功能

### 文件管理
- 本地文件存储
- 分布式文件存储
- 图片压缩处理
- 文件预览功能

### 消息通知
- 站内消息
- 短信通知
- 邮件通知
- 实时推送

### 定时任务
- 动态任务调度
- 任务执行监控
- 任务结果统计

## 贡献指南

### 开发规范

1. **代码规范**
   - 遵循阿里巴巴Java开发手册
   - 使用统一的代码格式化配置
   - 添加必要的注释和文档

2. **提交规范**
   - 使用语义化的提交信息
   - 每次提交包含单一功能
   - 提交前进行代码检查

3. **测试要求**
   - 编写单元测试
   - 确保测试覆盖率
   - 进行集成测试

### 参与贡献

1. **Fork项目**
2. **创建功能分支**: `git checkout -b feature/new-feature`
3. **提交更改**: `git commit -am 'Add new feature'`
4. **推送分支**: `git push origin feature/new-feature`
5. **创建Pull Request**

### 问题反馈

- 通过Issue报告Bug
- 提供详细的错误信息
- 包含复现步骤
- 建议解决方案

## 许可证

本项目采用 [许可证类型] 许可证，详情请查看 LICENSE 文件。

## 联系方式

- **项目维护**: Topinfo团队
- **技术支持**: [技术支持邮箱]
- **官方网站**: www.tuxun.net

---

**注意**: 本文档会随着项目的发展持续更新，请关注最新版本。