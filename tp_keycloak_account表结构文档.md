# tp_keycloak_account 表结构文档

## 表基本信息
- **表名**: tp_keycloak_account
- **表注释**: Keycloak账号关联表
- **存储引擎**: InnoDB
- **字符集**: utf8mb4
- **排序规则**: utf8mb4_unicode_ci
- **创建时间**: 2025-01-21

## 字段详细信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 索引 | 说明 |
|--------|----------|------|----------|--------|------|------|
| id | varchar | 36 | NO | NULL | PRI | 主键ID（UUID格式） |
| account_id | varchar | 36 | NO | NULL | UNI | 关联账号ID（外键关联tp_account.ACCOUNT_ID） |
| kc_client_id | varchar | 100 | YES | NULL | MUL | Keycloak客户端ID |
| kc_client_secret | varchar | 500 | YES | NULL |  | Keycloak客户端密钥（建议加密存储） |
| kc_grant_type | varchar | 50 | YES | password |  | 授权类型（password/client_credentials等） |
| kc_username | varchar | 150 | YES | NULL | MUL | Keycloak用户名 |
| kc_user_id | varchar | 100 | YES | NULL |  | Keycloak用户ID |
| kc_password | varchar | 500 | YES | NULL |  | Keycloak密码（建议加密存储） |
| kc_refresh_token | text |  | YES | NULL |  | 刷新令牌 |
| kc_access_token | text |  | YES | NULL |  | 访问令牌 |
| kc_token_expires_at | datetime |  | YES | NULL |  | 访问令牌过期时间 |
| kc_refresh_expires_at | datetime |  | YES | NULL |  | 刷新令牌过期时间 |
| kc_post_logout_redirect_uri | varchar | 500 | YES | NULL |  | 登出后重定向URI |
| kc_realm | varchar | 100 | YES | master |  | Keycloak Realm名称 |
| kc_server_url | varchar | 500 | YES | NULL |  | Keycloak服务器地址 |
| enabled | tinyint | 1 | YES | 1 | MUL | 是否启用（0:禁用, 1:启用） |
| last_login_time | datetime |  | YES | NULL |  | 最后登录时间 |
| last_token_refresh_time | datetime |  | YES | NULL |  | 最后令牌刷新时间 |
| create_time | varchar | 14 | YES | NULL | MUL | 创建时间（YYYYMMDDHHMMSS） |
| update_time | varchar | 14 | YES | NULL |  | 更新时间（YYYYMMDDHHMMSS） |
| creator | varchar | 36 | YES | NULL |  | 创建人ID |
| updater | varchar | 36 | YES | NULL |  | 更新人ID |
| tenant_id | varchar | 36 | YES | NULL | MUL | 租户ID（多租户支持） |
| extend01 | varchar | 150 | YES | NULL |  | 扩展字段1 |
| extend02 | varchar | 150 | YES | NULL |  | 扩展字段2 |
| extend03 | varchar | 150 | YES | NULL |  | 扩展字段3 |

## 索引信息

### 主键索引
- **PRIMARY KEY**: `id`

### 唯一索引
- **uk_account_id**: `account_id` - 确保每个账号只能有一个Keycloak关联记录

### 普通索引
- **idx_kc_client_id**: `kc_client_id` - 提高按客户端ID查询的性能
- **idx_kc_username**: `kc_username` - 提高按Keycloak用户名查询的性能
- **idx_tenant_id**: `tenant_id` - 支持多租户查询优化
- **idx_enabled**: `enabled` - 提高按启用状态查询的性能
- **idx_create_time**: `create_time` - 支持按创建时间排序和查询

## 约束条件

### 外键约束
- **fk_tp_keycloak_account_account_id**: 
  - 外键字段: `account_id`
  - 引用表: `tp_account`
  - 引用字段: `ACCOUNT_ID`
  - 删除策略: CASCADE（级联删除）
  - 更新策略: CASCADE（级联更新）

## 业务说明

### 核心功能
1. **账号关联管理**: 建立系统账号与Keycloak账号的一对一关联关系
2. **认证配置存储**: 存储Keycloak客户端配置信息
3. **令牌管理**: 管理访问令牌和刷新令牌的生命周期
4. **多租户支持**: 通过tenant_id字段支持多租户架构

### Keycloak集成
1. **客户端配置**: 存储kc_client_id、kc_client_secret等客户端信息
2. **用户认证**: 支持用户名密码和客户端凭证两种授权模式
3. **令牌管理**: 自动管理令牌的获取、刷新和过期
4. **Realm支持**: 支持多个Keycloak Realm的配置

### 安全特性
1. **密码加密**: kc_client_secret和kc_password字段建议加密存储
2. **令牌安全**: 访问令牌和刷新令牌使用TEXT类型存储
3. **过期管理**: 记录令牌过期时间，支持自动刷新
4. **启用控制**: 通过enabled字段控制关联的启用状态

### 扩展性设计
1. **扩展字段**: 提供extend01-03三个扩展字段
2. **审计支持**: 记录创建人、更新人和时间信息
3. **多租户**: 支持tenant_id进行租户隔离
4. **灵活配置**: 支持不同的Keycloak服务器和Realm配置

## 相关表关联

### 主要关联
- **tp_account**: 通过account_id字段建立一对一关联
  - 关联类型: 一对一（每个账号最多一个Keycloak配置）
  - 级联操作: 删除账号时自动删除Keycloak配置

### 潜在关联
- **tp_person**: 通过tp_account间接关联到人员信息
- **tp_tenant**: 通过tenant_id关联到租户信息

## 注意事项

1. **数据安全**: 
   - kc_client_secret和kc_password字段包含敏感信息，建议加密存储
   - 令牌字段应定期清理过期数据

2. **性能优化**:
   - 已创建必要的索引，查询时应充分利用
   - 大量令牌数据可能影响性能，建议定期清理

3. **业务逻辑**:
   - account_id具有唯一约束，确保一个账号只能有一个Keycloak配置
   - enabled字段控制关联的有效性
   - 令牌过期时间需要与实际Keycloak配置保持一致

4. **维护建议**:
   - 定期检查和清理过期的令牌数据
   - 监控外键约束的完整性
   - 备份敏感配置信息

5. **开发注意**:
   - 操作此表时需要考虑与tp_account表的关联关系
   - 令牌刷新逻辑需要更新相关时间字段
   - 多租户环境下需要正确设置tenant_id