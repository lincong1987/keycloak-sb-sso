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
-- Table structure for table `tp_system_config`
--

DROP TABLE IF EXISTS `tp_system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tp_system_config` (
  `config_key` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置键',
  `config_value` text COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置值',
  `config_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'string' COMMENT '配置项类型：string-字符串，number-数字，boolean-布尔值，json-JSON对象，array-数组',
  `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置描述',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tp_system_config`
--

LOCK TABLES `tp_system_config` WRITE;
/*!40000 ALTER TABLE `tp_system_config` DISABLE KEYS */;
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('11','222','string','111','2025-08-29 22:55:27','2025-08-29 23:09:03');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('22','aa','string','bb','2025-08-29 23:07:08','2025-08-29 23:10:04');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('admin.logo','./logo/JPX-logo.png','string','管理后台Logo地址','2025-08-29 21:24:54','2025-08-29 23:18:37');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('admin.title','JPX ','string','管理后台标题','2025-08-29 21:24:54','2025-08-29 23:13:47');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('browser.title','JPX','string','浏览器标题','2025-08-31 20:25:03','2025-08-31 20:25:03');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('login.logo','./logo/JPX-logo.png','string','登录页面Logo地址','2025-08-29 21:24:54','2025-09-02 17:04:45');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('login.title','JPX','string','登录页面标题','2025-08-29 21:24:54','2025-08-29 21:51:17');
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
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.copyright','© 2025 JPX 版权所有','string','版权信息','2025-08-29 21:24:54','2025-08-29 21:57:52');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.language','zh-CN','string','系统语言','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.name','管理系统','string','系统名称','2025-08-29 21:24:54','2025-08-29 22:09:38');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.theme','default','string','系统主题','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.timezone','Asia/Shanghai','string','系统时区','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('system.version','1.0.0','string','系统版本','2025-08-29 21:24:54','2025-08-29 21:24:54');
INSERT INTO `tp_system_config` (`config_key`, `config_value`, `config_type`, `description`, `create_time`, `update_time`) VALUES ('test.config','test value111','string','Test configuration','2025-08-29 21:30:49','2025-08-29 23:05:33');
/*!40000 ALTER TABLE `tp_system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ps-bmp'
--
/*!50003 DROP FUNCTION IF EXISTS `get_next_org_version` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `get_next_org_version`() RETURNS bigint(20)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE next_version BIGINT;
    UPDATE org_tree_version_sequence SET current_version = current_version + 1 WHERE id = 1;
    SELECT current_version INTO next_version FROM org_tree_version_sequence WHERE id = 1;
    RETURN next_version;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_org_tree_change` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `record_org_tree_change`(
    IN p_operation_type VARCHAR(20),
    IN p_operator_id BIGINT,
    IN p_operator_name VARCHAR(100),
    IN p_dept_id BIGINT,
    IN p_dept_name VARCHAR(200),
    IN p_parent_id BIGINT,
    IN p_old_parent_id BIGINT,
    IN p_dept_code VARCHAR(50),
    IN p_old_dept_code VARCHAR(50),
    IN p_dept_level INT,
    IN p_old_dept_level INT,
    IN p_sort_order INT,
    IN p_old_sort_order INT,
    IN p_description TEXT,
    IN p_before_data JSON,
    IN p_after_data JSON
)
BEGIN
    DECLARE new_version BIGINT;
    SET new_version = get_next_org_version();
    
    INSERT INTO org_tree_change_history (
        version, operation_type, operator_id, operator_name,
        dept_id, dept_name, parent_id, old_parent_id,
        dept_code, old_dept_code, dept_level, old_dept_level,
        sort_order, old_sort_order, description,
        before_data, after_data
    ) VALUES (
        new_version, p_operation_type, p_operator_id, p_operator_name,
        p_dept_id, p_dept_name, p_parent_id, p_old_parent_id,
        p_dept_code, p_old_dept_code, p_dept_level, p_old_dept_level,
        p_sort_order, p_old_sort_order, p_description,
        p_before_data, p_after_data
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-02 21:10:15
