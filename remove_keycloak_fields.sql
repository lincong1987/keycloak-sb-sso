-- 从 tp_account 表中删除 Keycloak 相关字段
-- 执行前请备份数据库

USE `ps-bmp`;

-- 删除 Keycloak 相关字段
ALTER TABLE `tp_account` 
DROP COLUMN `kc_client_id`,
DROP COLUMN `kc_client_secret`,
DROP COLUMN `kc_grant_type`,
DROP COLUMN `kc_username`,
DROP COLUMN `kc_password`,
DROP COLUMN `kc_refresh_token`,
DROP COLUMN `kc_post_logout_redirect_uri`;

-- 查看表结构确认字段删除成功
DESC `tp_account`;

-- 显示完整的表创建语句
SHOW CREATE TABLE `tp_account`;

/*
删除的字段列表：
1. kc_client_id - Keycloak 客户端ID
2. kc_client_secret - Keycloak 客户端密钥
3. kc_grant_type - OAuth2 授权类型
4. kc_username - Keycloak 用户名
5. kc_password - Keycloak 密码
6. kc_refresh_token - Keycloak 刷新令牌
7. kc_post_logout_redirect_uri - 登出后重定向URI

注意事项：
- 删除字段会永久丢失数据，请确保已备份重要数据
- 如果有应用程序正在使用这些字段，请先停止相关服务
- 建议在测试环境先验证删除操作
*/