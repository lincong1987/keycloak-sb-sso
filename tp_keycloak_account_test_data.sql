-- tp_keycloak_account 表测试数据
-- 基于现有 tp_account 数据创建对应的 Keycloak 关联记录

USE `ps-bmp`;

-- 插入一条测试数据
INSERT INTO `tp_keycloak_account` (
    `id`,
    `account_id`,
    `kc_client_id`,
    `kc_client_secret`,
    `kc_grant_type`,
    `kc_username`,
    `kc_password`,
    `kc_refresh_token`,
    `kc_access_token`,
    `kc_token_expires_at`,
    `kc_refresh_expires_at`,
    `kc_post_logout_redirect_uri`,
    `kc_realm`,
    `kc_server_url`,
    `enabled`,
    `last_login_time`,
    `last_token_refresh_time`,
    `create_time`,
    `update_time`,
    `creator`,
    `updater`,
    `tenant_id`,
    `extend01`,
    `extend02`,
    `extend03`
) VALUES (
    UUID(),                                    -- id: 生成UUID
    '1111111111111111111',                     -- account_id: 关联admin账号
    'ps-be',                                   -- kc_client_id: Keycloak客户端ID
    'test-client-secret-123',                  -- kc_client_secret: 测试客户端密钥
    'password',                                -- kc_grant_type: 密码授权类型
    'admin',                                   -- kc_username: Keycloak用户名
    'encrypted_password_hash',                 -- kc_password: 加密后的密码
    NULL,                                      -- kc_refresh_token: 刷新令牌（初始为空）
    NULL,                                      -- kc_access_token: 访问令牌（初始为空）
    NULL,                                      -- kc_token_expires_at: 令牌过期时间
    NULL,                                      -- kc_refresh_expires_at: 刷新令牌过期时间
    'http://localhost:10801/sso',              -- kc_post_logout_redirect_uri: 登出重定向
    'master',                                  -- kc_realm: Keycloak域
    'http://localhost:8080',                   -- kc_server_url: Keycloak服务器地址
    1,                                         -- enabled: 启用状态
    NULL,                                      -- last_login_time: 最后登录时间
    NULL,                                      -- last_token_refresh_time: 最后令牌刷新时间
    DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),        -- create_time: 创建时间
    DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),        -- update_time: 更新时间
    '1111111111111111111',                     -- creator: 创建人（admin账号ID）
    '1111111111111111111',                     -- updater: 更新人（admin账号ID）
    NULL,                                      -- tenant_id: 租户ID
    'test_extend_01',                          -- extend01: 扩展字段1
    'test_extend_02',                          -- extend02: 扩展字段2
    'test_extend_03'                           -- extend03: 扩展字段3
);

-- 验证插入结果
SELECT 
    id,
    account_id,
    kc_client_id,
    kc_username,
    kc_realm,
    kc_server_url,
    enabled,
    create_time
FROM `tp_keycloak_account` 
WHERE account_id = '1111111111111111111';

/*
测试数据说明：
1. 关联admin账号（ACCOUNT_ID: 1111111111111111111）
2. 使用ps-be作为Keycloak客户端ID
3. 配置本地Keycloak服务器地址
4. 设置为启用状态
5. 包含完整的审计字段
6. 预留扩展字段用于测试

注意事项：
- 实际使用时kc_client_secret和kc_password应该加密存储
- 令牌字段在首次登录时会被填充
- 时间字段使用YYYYMMDDHHMMSS格式
*/