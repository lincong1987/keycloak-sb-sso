-- 为 tp_keycloak_account 表添加 kc_user_id 字段
-- 执行时间: 2025-01-21
-- 执行人: 系统管理员

USE `ps-bmp`;

-- 添加 kc_user_id 字段
ALTER TABLE `tp_keycloak_account` 
ADD COLUMN `kc_user_id` VARCHAR(100) NULL COMMENT 'Keycloak用户ID' AFTER `kc_username`;

-- 验证字段添加结果
DESC `tp_keycloak_account`;

-- 显示完整的表创建语句
SHOW CREATE TABLE `tp_keycloak_account`;

/*
字段说明：
- kc_user_id: Keycloak用户ID，用于存储Keycloak系统中的用户唯一标识符
- 数据类型: VARCHAR(100)
- 允许为空: YES
- 默认值: NULL
- 位置: 在kc_username字段之后

用途：
1. 存储Keycloak系统中用户的唯一标识符
2. 用于与Keycloak用户管理API的集成
3. 支持用户身份验证和授权流程
4. 便于跟踪和管理Keycloak用户状态

注意事项：
- 该字段与kc_username配合使用，提供更完整的Keycloak用户信息
- 在实际使用中，kc_user_id通常是Keycloak系统自动生成的UUID
- 建议在业务逻辑中同时维护kc_username和kc_user_id的一致性
*/

-- 可选：为新字段添加索引（根据查询需求决定）
-- ALTER TABLE `tp_keycloak_account` ADD INDEX `idx_kc_user_id` (`kc_user_id`);