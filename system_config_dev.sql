-- MySQL dump 10.13  Distrib 5.7.26, for Win64 (x86_64)
--
-- Host: alilaoba.cn    Database: ps-bmp
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `tp_system_config`
--
-- 清空现有数据（可选，根据需要决定是否包含）
DELETE FROM `tp_system_config`;

LOCK TABLES `tp_system_config` WRITE;
/*!40000 ALTER TABLE `tp_system_config` DISABLE KEYS */;
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('11','222','string','111','2025-08-29 22:55:27','2025-08-29 23:09:03');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('22','aa','string','bb','2025-08-29 23:07:08','2025-08-29 23:10:04');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('admin.logo','./logo/jpx-logo.png','string','管理后台Logo地址','2025-08-29 21:24:54','2025-09-03 22:00:25');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('admin.title','JPX3.0','string','管理后台标题','2025-08-29 21:24:54','2025-09-03 22:00:15');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('browser.title','JPX3.0','string','浏览器标题','2025-08-31 20:25:03','2025-09-03 22:00:45');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.admin.client-id','admin-cli','string','管理员客户端ID','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.admin.password','admin123','string','管理员密码','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.admin.username','admin','string','管理员用户名','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.realm','ps-realm','string','Keycloak Realm名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.server-url','http://localhost:18080','string','Keycloak服务器地址','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.client-id','ps-be','string','SSO客户端ID','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.client-secret','ps-be-secret','string','SSO客户端密钥','2025-09-03 15:14:38','2025-09-03 16:59:48');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.enabled','true','string','是否启用Keycloak SSO','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.jwt.cache-ttl','300','string','Token缓存时间（秒）','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.jwt.verify-audience','false','string','是否验证受众','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.jwt.verify-expiration','true','string','是否验证Token过期时间','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.jwt.verify-issuer','true','string','是否验证签发者','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.redirect.error-url','http://localhost:10801/#/login','string','登录失败后重定向地址','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.redirect.success-url','http://localhost:10801/#/sso/login','string','登录成功后重定向地址','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.email-header','X-User-Email','string','邮箱头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.name-header','X-User-Name','string','姓名头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.permissions-header','X-User-Permissions','string','权限头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.roles-header','X-User-Roles','string','角色头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.user-id-header','X-User-Id','string','用户ID头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('keycloak.sso.user-header.username-header','X-Username','string','用户名头名称','2025-09-03 15:14:38','2025-09-03 15:14:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('login.logo','./logo/jpx-logo.png','string','登录页面Logo地址','2025-08-29 21:24:54','2025-09-03 22:03:27');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('login.title','JPX3.0','string','登录页面标题','2025-08-29 21:24:54','2025-09-03 22:03:21');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('password.expiry.reminder.days','30','string','密码到期提醒天数','2025-08-30 00:07:40','2025-08-30 00:07:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('password.validity.months','3','string','密码有效期（月）','2025-08-30 00:07:40','2025-08-30 00:07:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('person.audit.enabled','false','boolean','是否启用个人信息修改审核功能','2025-08-31 17:39:31','2025-08-31 17:39:31');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('person.audit.fields','sex,idcard','array','需要审核的个人信息字段列表，逗号分隔','2025-08-31 17:39:31','2025-09-01 02:14:24');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.ip.blacklist','','string','IP黑名单','2025-08-30 06:18:45','2025-08-30 16:52:39');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.ip.deny.message','您的IP地址不在允许访问范围内，请联系管理员','string','IP访问拒绝消息','2025-08-30 06:18:45','2025-08-30 16:52:39');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.ip.enabled','false','string','IP访问控制开关','2025-08-30 06:18:45','2025-08-30 16:52:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.ip.log.enabled','true','string','IP访问日志开关','2025-08-30 06:18:45','2025-08-30 16:52:39');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.ip.whitelist','*','string','IP白名单','2025-08-30 06:18:45','2025-08-30 16:52:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.admin.bypass','true','string','管理员是否绕过访问控制，true绕过，false不绕过','2025-08-30 18:46:40','2025-08-30 18:46:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.default.policy','allow','string','默认访问策略，allow允许所有，deny拒绝所有','2025-08-30 18:46:40','2025-08-30 18:46:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.deny.message','您没有访问权限，请联系系统管理员','string','用户访问被拒绝时的提示信息','2025-08-30 18:46:40','2025-08-30 18:46:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.enabled','false','string','用户访问控制开关，true启用，false禁用','2025-08-30 18:46:40','2025-08-30 18:46:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.log.enabled','true','string','是否记录用户访问日志，true启用，false禁用','2025-08-30 18:46:40','2025-08-30 18:46:40');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('security.user.access.rules','# 管理员访问规则\n#allow role:ADMIN\n#allow user:admin\n\n# 普通用户访问规则\n#allow role:USER AND dept:IT部门\n#allow role:MANAGER AND time:09:00-18:00\n\n# 拒绝规则（优先级最高）\n#deny user:guest\n#deny role:TEMP\n\n# 组合规则示例\n#allow (role:USER OR role:MANAGER) AND dept:销售部门 AND time:08:00-20:00','string','用户访问控制规则，支持用户、角色、部门、时间等多种规则','2025-08-30 18:46:40','2025-08-30 18:58:55');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('sso.keycloak.callback.url','http://localhost:8082/ps-be/api/sso/callback','string','SSO回调地址','2025-09-03 16:08:23','2025-09-03 16:55:31');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('sso.keycloak.client.id','ps-be','string','Keycloak客户端ID','2025-09-03 16:08:23','2025-09-03 16:08:23');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('sso.keycloak.realm','ps-realm','string','Keycloak Realm名称','2025-09-03 16:08:23','2025-09-03 16:08:23');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('sso.keycloak.redirect.uri','http://localhost:8082/ps-be/api/sso/callback','string','Keycloak登录回调地址','2025-09-03 16:13:13','2025-09-03 16:55:25');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('sso.keycloak.server.url','http://localhost:18080','string','Keycloak服务器地址','2025-09-03 16:08:23','2025-09-03 16:08:23');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.copyright','© 2025 JPX3.0版权所有','string','版权信息','2025-08-29 21:24:54','2025-09-03 22:02:48');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.language','zh-CN','string','系统语言','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.name','JPX3.0','string','系统名称','2025-08-29 21:24:54','2025-09-03 22:02:56');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.theme','default','string','系统主题','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.timezone','Asia/Shanghai','string','系统时区','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.version','1.0.0','string','系统版本','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('test.config','test value111','string','Test configuration','2025-08-29 21:30:49','2025-08-29 23:05:33');
/*!40000 ALTER TABLE `tp_system_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-04 11:44:15
