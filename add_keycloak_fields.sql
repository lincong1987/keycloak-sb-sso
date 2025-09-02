-- 为 tp_account 表添加 Keycloak 相关字段
-- 执行前请备份数据库

USE `ps-bmp`;

-- 添加 Keycloak 相关字段到 tp_account 表
ALTER TABLE `tp_account` 
ADD COLUMN `kc_client_id` VARCHAR(100) NULL COMMENT 'Keycloak 客户端ID' AFTER `keycloak_id`,
ADD COLUMN `kc_client_secret` VARCHAR(500) NULL COMMENT 'Keycloak 客户端密钥' AFTER `kc_client_id`,
ADD COLUMN `kc_grant_type` VARCHAR(50) NULL DEFAULT 'password' COMMENT 'Keycloak 授权类型（password/authorization_code等）' AFTER `kc_client_secret`,
ADD COLUMN `kc_username` VARCHAR(150) NULL COMMENT 'Keycloak 用户名' AFTER `kc_grant_type`,
ADD COLUMN `kc_password` VARCHAR(500) NULL COMMENT 'Keycloak 密码（加密存储）' AFTER `kc_username`,
ADD COLUMN `kc_refresh_token` TEXT NULL COMMENT 'Keycloak 刷新令牌' AFTER `kc_password`,
ADD COLUMN `kc_post_logout_redirect_uri` VARCHAR(500) NULL COMMENT 'Keycloak 登出后重定向URI' AFTER `kc_refresh_token`;

-- 为新增字段添加索引（可选，根据查询需求）
-- ALTER TABLE `tp_account` ADD INDEX `idx_kc_client_id` (`kc_client_id`);
-- ALTER TABLE `tp_account` ADD INDEX `idx_kc_username` (`kc_username`);

-- 查看表结构确认字段添加成功
DESC `tp_account`;

-- 显示完整的表创建语句
SHOW CREATE TABLE `tp_account`;

/*
字段说明：
1. kc_client_id: Keycloak 客户端标识符，用于标识应用程序
2. kc_client_secret: Keycloak 客户端密钥，用于客户端认证
3. kc_grant_type: OAuth2 授权类型，默认为 password（用户名密码模式）
4. kc_username: Keycloak 中的用户名，可能与系统用户名不同
5. kc_password: Keycloak 中的密码，建议加密存储
6. kc_refresh_token: 用于刷新访问令牌的刷新令牌
7. kc_post_logout_redirect_uri: 用户登出后的重定向地址

注意事项：
- 所有字段都允许为 NULL，便于渐进式迁移
- 密码和密钥字段使用较大长度以支持加密存储
- refresh_token 使用 TEXT 类型以支持较长的令牌
- 建议在生产环境中对敏感字段进行加密处理
*/