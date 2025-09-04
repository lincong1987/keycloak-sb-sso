CREATE TABLE `tp_data_permission_scope` (
        `perm_id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '物理主键',
        `person_id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '人员id',
        `dept_id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '授权时部门id',
        `data_scope` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '数据范围',
        `tenant_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '租户id',
        `creator` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
        `create_time` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
        `updator` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
        `update_time` varchar(14) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
        `extend01` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '扩展字段01',
        `extend02` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '扩展字段02',
        `extend03` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '扩展字段03',
        PRIMARY KEY (`perm_id`),
        KEY `idx_psn_dept_id` (`person_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据权限范围表';

CREATE TABLE `tp_data_permission_dept` (
       `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '物理主键',
       `perm_id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '数据权限id',
       `dept_id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '指定的部门id',
       `tenant_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '租户id',
       PRIMARY KEY (`id`),
       UNIQUE KEY `uk_perm_dept_id` (`perm_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据权限部门表';