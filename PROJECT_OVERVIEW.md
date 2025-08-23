# Spring Boot + Keycloak SSO 项目综述

本文件系统性记录该示例项目的目标、架构、关键配置、运行方式与改进建议，便于快速理解与落地。

## 1. 项目概览

- 目标: 演示使用 Keycloak 与 Spring Security OAuth2 Client 在两个独立系统间实现单点登录（SSO）与基本登出流程。
- 组成: 两个 Spring Boot 应用（system-a:8081、system-b:8082）+ Keycloak（基于 Postgres）+ 文档与示例 Realm 导出。
- 认证流: 标准 OAuth2 授权码模式。
  1) 浏览器跳转到 Keycloak 登录；2) 应用用授权码换取令牌；3) 建立本地会话并展示用户信息；4) 跨系统访问无需再次登录。

## 2. 目录结构

```
├── system-a/                      # 系统A（8081）
├── system-b/                      # 系统B（8082）
├── keycloak/realm-export.json     # Demo Realm 导出（含两个客户端与示例用户）
├── reference/                     # Keycloak 配置与授权码/令牌原理文档
├── docker-compose.yml             # 一键启动 Postgres + Keycloak + 两个系统
├── README.md                      # 快速入门与步骤说明
└── PROJECT_OVERVIEW.md            # 本文档
```

## 3. 技术栈

- 后端: Java 17、Spring Boot 3.2、Spring Security 6、Spring OAuth2 Client
- 前端: Thymeleaf、Bootstrap 5
- 认证: Keycloak 23.0.1（OIDC）、Postgres 15
- 容器: 多阶段 Docker 构建（Maven 构建 + Temurin JRE 运行）

## 4. 运行方式

### 4.1 本机（Maven）

```bash
cd system-a && mvn spring-boot:run
cd system-b && mvn spring-boot:run
```

### 4.2 容器编排（推荐）

```bash
docker-compose up -d
```

- 访问系统: `http://localhost:8081/system-a/`、`http://localhost:8082/system-b/`
- Keycloak 管理端: `http://localhost:8080/admin`（compose 默认 admin/admin123）

> 提示：`docker-compose.yml` 为两个应用注入了 provider 端点（`issuer/token/userinfo/jwk`）指向 `http://keycloak:8080`，以匹配容器内网络；客户端密钥仍读取各自 `application.yml`。

## 5. Keycloak 配置要点

- Realm: `demo`
- 客户端（Client）:
  - system-a-client → 回调: `http://localhost:8081/system-a/login/oauth2/code/keycloak`
  - system-b-client → 回调: `http://localhost:8082/system-b/login/oauth2/code/keycloak`
- Web Origins: 分别允许 `http://localhost:8081`、`http://localhost:8082`
- 客户端密钥: 需要写入两个应用的 `application.yml`（仓库中示例密钥仅用于演示，不可用于生产）
- Realm 导入: `keycloak/realm-export.json` 提供示例；compose 当前未启用自动导入，如需自动导入可调整 Keycloak 启动命令加 `--import-realm` 并挂载导入目录。

## 6. 应用配置与入口

- 端口与上下文路径: `system-a` → `8081` + `/system-a`；`system-b` → `8082` + `/system-b`
- `spring.security.oauth2.client.registration.keycloak`：`client-id`、`client-secret`、`redirect-uri`、`scope`
- `spring.security.oauth2.client.provider.keycloak`：`issuer-uri`、`authorization-uri`、`token-uri`、`user-info-uri`、`jwk-set-uri` 等
- 容器模式下，provider 端点通过环境变量覆盖为 `http://keycloak:8080/...`，避免容器内误用 `localhost`

## 7. 安全配置与控制器

- `SecurityConfig`：
  - 允许匿名访问 `/`, `/home`, 静态资源
  - `oauth2Login`: 登录入口 `/oauth2/authorization/keycloak`，默认成功跳转 `/dashboard`
  - 当前 `csrf().disable()` 为演示简化，生产应开启并按需放行特定端点
- `HomeController`（A/B 类似）：
  - `/dashboard` 展示 `preferred_username`、`email`、`name`
  - `/profile` 展示完整用户属性
  - 提供自定义 `GET /logout`：构造前端通道（front-channel）Keycloak end-session URL，并携带 `redirect_uri`
- 页面：Thymeleaf 模板含登录/仪表板/资料页与跨系统跳转按钮

## 8. 登出一致性（重要）

- 模板中的“退出登录”链接指向 `/system-a/logout`、`/system-b/logout`（由 Spring Security 处理），通常只清除本地会话；
- 控制器另有 `GET /logout` 实现前端重定向至 Keycloak end-session，但模板未使用该链接；
- 影响：点击“退出登录”后，Keycloak 会话仍有效，返回任一系统会被再次自动登录；

改进建议（二选一或同时）：
1) 在 `SecurityConfig` 使用 `OidcClientInitiatedLogoutSuccessHandler` 作为 `logoutSuccessHandler` 并设置 `postLogoutRedirectUri`，实现 OIDC 统一登出；
2) 将模板的登出链接改为控制器的 `GET /logout`（或统一到 `/system-a/logout` 且在 Security 中调用 Keycloak 端点）。

## 9. Docker Compose 细节

- `postgres` 与 `keycloak` 同网桥，健康检查启用；Keycloak 使用 `start-dev` 并开放健康与指标端点
- `system-a`、`system-b` 多阶段构建，依赖 Keycloak 就绪后启动
- Provider 端点通过环境变量注入；端口映射为 8081、8082；
- 客户端密钥未外部化，仍依赖应用内 `application.yml`（建议外部化）

## 10. 参考与文档

- `README.md`: 快速入门、Keycloak 手动配置、运行指令与排错
- `reference/01-keycloak-configuration-guide.md`: GUI 步骤详解（Realm、客户端、用户），含排错与最佳实践
- `reference/02-token-and-authorization-code-guide.md`: 授权码与令牌的职责与流程解释

## 11. 潜在改进

- 统一登出: 采用 `OidcClientInitiatedLogoutSuccessHandler` 或统一前端链接，完成真正的 SSO 级登出
- 机密管理: 客户端密钥改为环境变量/密钥管理，避免硬编码；生产启用 HTTPS
- CSRF 安全: 生产环境开启 CSRF 并按需忽略 API/回调路径
- Realm 自动导入: 在 compose 中挂载导入目录并添加 `--import-realm`
- 授权/角色: 在 Keycloak 定义角色与映射，在应用端基于 `hasRole/Authority` 保护端点
- 可观测性: Keycloak 审计日志、应用结构化日志与指标

## 12. 快速检查清单（落地自查）

- [ ] Keycloak 已运行，Realm `demo` 存在
- [ ] 为 `system-a-client`、`system-b-client` 配置了正确的回调/登出回调与 Web Origins
- [ ] 将真实 `client-secret` 写入两个应用配置或以环境变量注入
- [ ] 本地/容器访问域名与端口一致（避免容器中使用 `localhost`）
- [ ] 登出流程验证：任一系统登出后，另一个系统是否也需重新登录（SSO 级登出）
- [ ] 生产环境：开启 HTTPS、CSRF、防护与日志/监控

---

如需我直接提交统一登出、密钥外部化或 Realm 自动导入的改动，请告知优先级，我可提供对应补丁。

