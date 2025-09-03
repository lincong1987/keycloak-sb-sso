-- Keycloak配置项迁移到tp_system_config表的SQL脚本
-- 执行前请确保tp_system_config表已存在

-- 插入Keycloak基础配置
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `description`) VALUES
-- 基础服务配置
('keycloak.server-url', 'http://localhost:18080', 'Keycloak服务器地址'),
('keycloak.realm', 'ps-realm', 'Keycloak Realm名称'),

-- SSO配置
('keycloak.sso.enabled', 'true', '是否启用Keycloak SSO'),
('keycloak.sso.client-id', 'ps-be', 'SSO客户端ID'),
('keycloak.sso.client-secret', 'xMXvDGzby4Z48szob7i2fuZlZy5Wlqrh', 'SSO客户端密钥'),

-- 重定向配置
('keycloak.sso.redirect.success-url', 'http://localhost:10801/#/sso/login', '登录成功后重定向地址'),
('keycloak.sso.redirect.error-url', 'http://localhost:10801/#/login', '登录失败后重定向地址'),

-- JWT验证配置
('keycloak.sso.jwt.verify-expiration', 'true', '是否验证Token过期时间'),
('keycloak.sso.jwt.verify-issuer', 'true', '是否验证签发者'),
('keycloak.sso.jwt.verify-audience', 'false', '是否验证受众'),
('keycloak.sso.jwt.cache-ttl', '300', 'Token缓存时间（秒）'),

-- 用户信息头配置
('keycloak.sso.user-header.user-id-header', 'X-User-Id', '用户ID头名称'),
('keycloak.sso.user-header.username-header', 'X-Username', '用户名头名称'),
('keycloak.sso.user-header.email-header', 'X-User-Email', '邮箱头名称'),
('keycloak.sso.user-header.name-header', 'X-User-Name', '姓名头名称'),
('keycloak.sso.user-header.roles-header', 'X-User-Roles', '角色头名称'),
('keycloak.sso.user-header.permissions-header', 'X-User-Permissions', '权限头名称'),

-- 管理员配置
('keycloak.admin.client-id', 'admin-cli', '管理员客户端ID'),
('keycloak.admin.username', 'admin', '管理员用户名'),
('keycloak.admin.password', 'admin123', '管理员密码');

-- 查询验证插入的配置
SELECT * FROM `tp_system_config` WHERE `config_key` LIKE 'keycloak.%' ORDER BY `config_key`;

-- 如果需要回滚，可以使用以下SQL删除所有Keycloak配置
-- DELETE FROM `tp_system_config` WHERE `config_key` LIKE 'keycloak.%';

-- 配置项说明：
-- 1. 所有配置项都以'keycloak.'为前缀，便于管理和查询
-- 2. 配置值保持与原YAML文件中的值一致
-- 3. 布尔值使用字符串'true'/'false'存储
-- 4. 数字值使用字符串存储，使用时需要转换
-- 5. 密钥等敏感信息建议在生产环境中加密存储

-- 使用示例：
-- 获取Keycloak服务器地址：
-- SELECT config_value FROM tp_system_config WHERE config_key = 'keycloak.server-url';

-- 更新配置值：
-- UPDATE tp_system_config SET config_value = 'http://new-keycloak-server:8080' WHERE config_key = 'keycloak.server-url';

-- 获取所有Keycloak配置：
-- SELECT * FROM tp_system_config WHERE config_key LIKE 'keycloak.%';