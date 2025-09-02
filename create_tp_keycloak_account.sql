-- 创建 tp_keycloak_account 关联表
-- 用于存储账号与 Keycloak 的关联配置信息

USE `ps-bmp`;

-- 创建 tp_keycloak_account 表
CREATE TABLE `tp_keycloak_account` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键ID',
  `account_id` VARCHAR(36) NOT NULL COMMENT '关联的账号ID（tp_account.ACCOUNT_ID）',
  `kc_client_id` VARCHAR(100) NULL COMMENT 'Keycloak 客户端ID',
  `kc_client_secret` VARCHAR(500) NULL COMMENT 'Keycloak 客户端密钥（加密存储）',
  `kc_grant_type` VARCHAR(50) NULL DEFAULT 'password' COMMENT 'OAuth2 授权类型（password/authorization_code/client_credentials等）',
  `kc_username` VARCHAR(150) NULL COMMENT 'Keycloak 用户名',
  `kc_password` VARCHAR(500) NULL COMMENT 'Keycloak 密码（加密存储）',
  `kc_refresh_token` TEXT NULL COMMENT 'Keycloak 刷新令牌',
  `kc_access_token` TEXT NULL COMMENT 'Keycloak 访问令牌',
  `kc_token_expires_at` DATETIME NULL COMMENT '访问令牌过期时间',
  `kc_refresh_expires_at` DATETIME NULL COMMENT '刷新令牌过期时间',
  `kc_post_logout_redirect_uri` VARCHAR(500) NULL COMMENT '登出后重定向URI',
  `kc_realm` VARCHAR(100) NULL DEFAULT 'master' COMMENT 'Keycloak Realm名称',
  `kc_server_url` VARCHAR(500) NULL COMMENT 'Keycloak 服务器地址',
  `enabled` TINYINT(1) NULL DEFAULT 1 COMMENT '是否启用（0:禁用, 1:启用）',
  `last_login_time` DATETIME NULL COMMENT '最后登录时间',
  `last_token_refresh_time` DATETIME NULL COMMENT '最后令牌刷新时间',
  `create_time` VARCHAR(14) NULL COMMENT '创建时间（YYYYMMDDHHMMSS）',
  `update_time` VARCHAR(14) NULL COMMENT '更新时间（YYYYMMDDHHMMSS）',
  `creator` VARCHAR(36) NULL COMMENT '创建人ID',
  `updater` VARCHAR(36) NULL COMMENT '更新人ID',
  `tenant_id` VARCHAR(36) NULL COMMENT '租户ID（多租户支持）',
  `extend01` VARCHAR(150) NULL COMMENT '扩展字段1',
  `extend02` VARCHAR(150) NULL COMMENT '扩展字段2',
  `extend03` VARCHAR(150) NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account_id` (`account_id`),
  KEY `idx_kc_client_id` (`kc_client_id`),
  KEY `idx_kc_username` (`kc_username`),
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_enabled` (`enabled`),
  KEY `idx_create_time` (`create_time`),
  CONSTRAINT `fk_tp_keycloak_account_account_id` FOREIGN KEY (`account_id`) REFERENCES `tp_account` (`ACCOUNT_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Keycloak账号关联表';

-- 查看表结构
DESC `tp_keycloak_account`;

-- 显示完整的表创建语句
SHOW CREATE TABLE `tp_keycloak_account`;

/*
表设计说明：

1. 主要字段：
   - id: 主键，使用UUID格式
   - account_id: 关联tp_account表的账号ID，设置为唯一键确保一对一关系
   - kc_client_id/kc_client_secret: Keycloak客户端配置
   - kc_grant_type: OAuth2授权类型，支持多种模式
   - kc_username/kc_password: Keycloak认证凭据
   - kc_refresh_token/kc_access_token: 令牌管理
   - kc_token_expires_at/kc_refresh_expires_at: 令牌过期时间管理

2. 配置字段：
   - kc_realm: Keycloak域配置
   - kc_server_url: Keycloak服务器地址
   - kc_post_logout_redirect_uri: 登出重定向配置

3. 状态管理：
   - enabled: 启用状态控制
   - last_login_time: 登录时间跟踪
   - last_token_refresh_time: 令牌刷新时间跟踪

4. 审计字段：
   - create_time/update_time: 时间戳
   - creator/updater: 操作人员跟踪
   - tenant_id: 多租户支持

5. 扩展性：
   - extend01/02/03: 预留扩展字段

6. 索引设计：
   - 主键索引: id
   - 唯一索引: account_id（确保一对一关系）
   - 普通索引: kc_client_id, kc_username, tenant_id, enabled, create_time
   - 外键约束: 关联tp_account表，支持级联删除和更新

7. 安全考虑：
   - 密码和密钥字段建议加密存储
   - 令牌字段使用TEXT类型支持长内容
   - 外键约束确保数据一致性

8. 性能优化：
   - 合理的索引设计提升查询性能
   - 字段长度设计考虑实际使用场景
   - 使用InnoDB引擎支持事务和外键
*/