-- 操作日志表
CREATE TABLE `tp_operate_log` (
  `LOG_ID` varchar(32) NOT NULL COMMENT '日志ID',
  `PERSON_ID` varchar(32) DEFAULT NULL COMMENT '人员ID',
  `MODULE_CODE` varchar(50) DEFAULT NULL COMMENT '模块代码',
  `OPERTER_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `OPERTER_TYPE` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `OPERTER_RID` varchar(100) DEFAULT NULL COMMENT '操作资源ID',
  `OPERTER_IP` varchar(50) DEFAULT NULL COMMENT '操作IP',
  `OPERTER_BROWSER` varchar(500) DEFAULT NULL COMMENT '操作浏览器',
  `USERNAME` varchar(100) DEFAULT NULL COMMENT '用户名',
  `ASCN_ID` varchar(32) DEFAULT NULL COMMENT '归属ID',
  `CATEGORY` varchar(20) DEFAULT NULL COMMENT '用户类别',
  `CITY_CODE` varchar(20) DEFAULT NULL COMMENT '城市代码',
  `ACTIVED` varchar(1) DEFAULT '1' COMMENT '是否激活(1:激活 0:未激活)',
  `CREATOR` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATOR` varchar(32) DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXTEND01` varchar(200) DEFAULT NULL COMMENT '扩展字段1',
  `EXTEND02` varchar(200) DEFAULT NULL COMMENT '扩展字段2',
  `EXTEND03` varchar(200) DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`LOG_ID`),
  KEY `idx_operter_time` (`OPERTER_TIME`),
  KEY `idx_username` (`USERNAME`),
  KEY `idx_module_code` (`MODULE_CODE`),
  KEY `idx_operter_type` (`OPERTER_TYPE`),
  KEY `idx_operter_ip` (`OPERTER_IP`),
  KEY `idx_category` (`CATEGORY`),
  KEY `idx_city_code` (`CITY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 插入示例数据
INSERT INTO `tp_operate_log` (`LOG_ID`, `PERSON_ID`, `MODULE_CODE`, `OPERTER_TIME`, `OPERTER_TYPE`, `OPERTER_RID`, `OPERTER_IP`, `OPERTER_BROWSER`, `USERNAME`, `ASCN_ID`, `CATEGORY`, `CITY_CODE`, `ACTIVED`, `CREATOR`, `CREATE_TIME`, `UPDATOR`, `UPDATE_TIME`, `EXTEND01`, `EXTEND02`, `EXTEND03`) VALUES
('log001', 'user001', 'LOGIN', NOW(), 'LOGIN', NULL, '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'admin', 'org001', 'ADMIN', '110000', '1', 'SYSTEM', NOW(), 'SYSTEM', NOW(), NULL, NULL, NULL),
('log002', 'user001', 'MENU', NOW(), 'VIEW', 'menu001', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'admin', 'org001', 'ADMIN', '110000', '1', 'SYSTEM', NOW(), 'SYSTEM', NOW(), NULL, NULL, NULL),
('log003', 'user001', 'USER', NOW(), 'CREATE', 'user002', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'admin', 'org001', 'ADMIN', '110000', '1', 'SYSTEM', NOW(), 'SYSTEM', NOW(), NULL, NULL, NULL);