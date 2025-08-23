# 小白友好指南：本工程的单点登录与安全 API 概念速通

这份文档面向初学者，带你从 0 到 1 了解本工程所用到的协议、关键概念与端到端流程：

- 单点登录（SSO）是怎么实现的？
- 第三方如何用“标准 API”校验用户身份？
- 如何安全地返回“脱敏”的用户信息？
- API 的权限控制与调用日志如何工作？

不需要你有安全背景，只需按文档一步步理解术语与流程即可。

---

## 一、参与者与组件

- Keycloak：开源的身份认证与授权服务器（IdP，身份提供方）。
- System A / System B：两个独立的 Spring Boot 应用（本工程示例）。
- 浏览器用户：访问 Web 页面，通过 Keycloak 登录后在 A 与 B 之间免登切换（SSO）。
- 第三方应用：拿着用户的访问令牌（Access Token）来调用 A/B 的标准 API 做身份校验或获取脱敏信息。

---

## 二、核心协议与关键概念

以下概念理解后，整套系统就“通了”。

- OAuth 2.0 授权码模式（Authorization Code）
  - 用户在 Keycloak 登录，应用用“授权码”去 Keycloak 换“令牌”（Access Token / ID Token）。
  - 令牌是后续访问受保护资源的“通行证”。

- OpenID Connect（OIDC）
  - 在 OAuth2 之上扩展“身份”能力，标准化登录、ID Token、UserInfo 等。
  - 我们使用 OIDC 提供登录（SSO）与统一登出能力。

- 访问令牌 Access Token（JWT）
  - Keycloak 签发的 JSON Web Token，内含“声明”（Claims），如：发行方（iss）、主体（sub）、作用域（scope）、过期时间（exp）等。
  - 第三方调用 API 时，将它放在请求头 `Authorization: Bearer <token>`。

- ID Token（JWT）
  - 面向“客户端”的用户身份信息令牌，常用于登录后在前端识别用户（本项目主要用 Access Token 来访问 API）。

- JWT 与 JWK（公钥）
  - JWT（JWS 签名）可被“资源服务器”用 Keycloak 公钥（JWK）离线验签，无需每次询问 Keycloak。
  - 应用通过 `issuer-uri` 自动下载 Keycloak 的发现文档与 JWK 公钥。

- Scope（作用域）与 Claim（声明）
  - scope 决定令牌“能做什么”，例如 `profile` 代表读取基本资料。
  - claim 是令牌中携带的字段，例：`preferred_username`、`email`、`scope`。

- 角色（Role）
  - Keycloak 可给用户授予角色（如 `admin`）；应用可把角色映射为权限（`ROLE_admin`）限制访问。

- 统一登出（OIDC RP‑Initiated Logout）
  - 应用登出时引导浏览器访问 Keycloak 的“登出端点”，清理 Keycloak 会话并回跳到应用，从而实现“真·统一登出”。

---

## 三、单点登录（SSO）流程（浏览器 → A/B 应用）

1) 用户访问 A 的受保护页面（如 `/system-a/dashboard`）。
2) A 发现未登录，重定向到 Keycloak 登录页。
3) 用户在 Keycloak 输入账号密码并通过 MFA（如有）。
4) Keycloak 将浏览器带回 A，并附上“授权码”。
5) A 用授权码向 Keycloak 换取 Access Token（以及 ID Token）。
6) A 建立本地会话，展示用户信息。
7) 用户访问 B 时，因为 Keycloak 会话仍有效，B 也能完成同样流程而无需再次输入密码（SSO 完成）。
8) 登出时，A/B 使用 OIDC 登出处理器调用 Keycloak end‑session，实现统一登出。

你可以把 SSO 理解为“Keycloak 保持着登录态，A/B 只是各自拿授权码换令牌并维持自己的会话”。

---

## 四、第三方身份校验：标准 API 怎么用？

第三方需要校验“手上的令牌是否有效且属于某个用户”，就调用我们的校验接口。

- 端点（以 B 为例）：
  - `GET http://localhost:8082/system-b/api/v1/auth/verify`
  - 请求头：`Authorization: Bearer <Access Token>`
  - 返回（示意）：
    ```json
    {
      "active": true,
      "sub": "用户唯一ID",
      "preferred_username": "用户名",
      "email": "user@example.com",
      "scope": "openid profile email",
      "iat": "签发时间",
      "exp": "过期时间",
      "iss": "发行者",
      "aud": ["受众"],
      "system": "B"
    }
    ```

幕后做了什么？
- Spring Security Resource Server 先用 Keycloak 的公钥验签 JWT，并校验 `iss/aud/exp`。
- 验证通过后，控制器从 `JwtAuthenticationToken` 中提取关键 claims 回给调用者。

---

## 五、安全脱敏的用户信息接口

当第三方或前端需要“当前用户信息”，我们提供“脱敏”输出：

- 端点（以 B 为例）：
  - `GET http://localhost:8082/system-b/api/v1/users/me`
  - 请求头：`Authorization: Bearer <Access Token>`
  - 返回会保留用户名，但对邮箱、姓名、手机号做遮蔽：
    - 邮箱：只保留本地部分首尾字符，中间打星（`j***e@example.com`）
    - 姓名：只保留首字母，其余打星（`张**` / `J***`）
    - 手机：只保留后 4 位（`*******1234`）

实现要点：
- 我们只使用令牌里的 claims（如 `email/name/phone_number`），不回源 Keycloak。
- 脱敏逻辑在 `MaskingUtils` 中集中实现，方便替换/升级策略。
- 注意：若某些字段（如 `phone_number`）需要出现，需在 Keycloak 客户端配置 Protocol Mapper 进行映射。

---

## 六、权限控制与 API 调用日志

权限控制（Authorization）：
- 令牌内的 `scope` 会被映射为 Spring 权限 `SCOPE_xxx`。
- 我们要求：
  - `/api/v1/auth/verify`、`/api/v1/users/me` 需要 `SCOPE_profile`。
  - `/api/v1/auth/token` 仅需登录会话（便于本地联调）。
- 可扩展：把 Keycloak 角色（realm roles）映射为 `ROLE_xxx` 来做更细粒度控制（如 `ROLE_admin`）。

调用日志（Audit）：
- 过滤器 `ApiAuditLoggingFilter` 只拦截 `/api/` 路径：
  - 记录：方法、URI、状态码、耗时、用户名、用户ID、scope、IP 等。
  - 便于审计与排错（不记录敏感头/请求体）。

---

## 七、与 Keycloak 的连接点（你需要知道的配置）

- Realm / Client / 用户：在 Keycloak 中创建 `demo` realm，配置 `system-a-client` 和 `system-b-client`，并创建用户。
- 回调与登出回调：
  - 回调 URI（A）：`http://localhost:8081/system-a/login/oauth2/code/keycloak`
  - 回调 URI（B）：`http://localhost:8082/system-b/login/oauth2/code/keycloak`
  - Post‑logout Redirect（A/B）：分别配置回跳到各自首页。
- Scope：确保默认授权范围含 `profile,email`，令牌里才会出现基本资料并满足访问权限。
- JWK 公钥：应用通过 `issuer-uri`（如 `http://localhost:8080/realms/demo`）自动下载 JWK，用于离线验签。

---

## 八、常见问题（FAQ）

- 401 Unauthorized：
  - 原因：没带 Bearer 令牌、令牌过期、令牌被改动或 `iss/aud` 不匹配。
  - 处理：重新获取令牌，检查请求头格式：`Authorization: Bearer <token>`。

- 403 Forbidden：
  - 原因：令牌缺少 `profile` scope。
  - 处理：在 Keycloak 客户端默认授权范围加入 `profile`，或从应用的 `/api/v1/auth/token` 获取令牌（包含所需 scope）。

- 字段不返回：
  - 例如手机号，需在 Keycloak Protocol Mapper 中把用户属性映射到 `phone_number` claim。

- 统一登出不生效：
  - 确认已启用 OIDC RP‑Initiated Logout，并在 Keycloak 配置 Post‑logout Redirect。

---

## 九、我该怎么验证？（最短路径）

1) 启动 Keycloak、System A、System B。
2) 浏览器打开 A，点击登录完成 SSO。
3) 在 A（或 B）里访问 `/api/v1/auth/token` 拿到一个 Access Token。
4) 用 curl 调 B 的身份校验与脱敏接口：
   - `curl -s http://localhost:8082/system-b/api/v1/auth/verify -H "Authorization: Bearer <TOKEN>"`
   - `curl -s http://localhost:8082/system-b/api/v1/users/me -H "Authorization: Bearer <TOKEN>"`
5) 查看服务日志中的 `api_audit` 行，确认审计记录产生。

---

## 十、延伸阅读（知道即可）

- Introspection（令牌自省）：在线查询令牌有效性；我们当前采用“本地验签”，无需每次回源。
- UserInfo 端点：需要“实时用户资料”时可以调用；本工程的 `/users/me` 出于性能直接用令牌 claims。
- CORS 与 CSRF：浏览器前端跨域访问 API 需要 CORS 配置；CSRF 主要保护“浏览器→同域表单”场景，Bearer API 默认不受 CSRF 影响。

---

恭喜，你已经掌握了本工程中 SSO 与安全 API 的核心概念与实践路径。建议结合 `reference/API_VALIDATION_GUIDE.md` 的接口清单与时序图一起食用，效果更佳。

