

/* 生成 监控配置【tp_monitor_config】表结构全量脚本 */
-- drop table if exists tp_monitor_config ;
create table tp_monitor_config(
          config_id varchar(36)  not null comment '主键',
          cpu_threshold numeric(12,2) comment 'cpu报警阈值',
          memory_threshold numeric(12,2) comment '内存报警阈值',
          disk_threshold numeric(12,2) comment '磁盘报警阈值',
          send_mail integer default 0 comment '是否发送邮件（1：是，0：否）',
          principal varchar(300) comment '系统负责人（多个逗号分隔）',
          mobile varchar(300) comment '负责人手机号',
          email varchar(300) comment '负责人邮箱（多个逗号分隔）',
          tenant_id varchar(36) comment '租户id',
          actived int default 1 not null comment '是否有效',
          creator varchar(36) comment '创建人',
          create_time varchar(14) comment '创建时间',
          updator varchar(36) comment '修改人',
          update_time varchar(14) comment '修改时间',
          extend01 varchar(150) comment '扩展字段01',
          extend02 varchar(150) comment '扩展字段02',
          extend03 varchar(150) comment '扩展字段03',
          extend04 varchar(150) comment '扩展字段04',
          extend05 varchar(150) comment '扩展字段05'
)engine=innodb default charset=utf8mb4 collate=utf8mb4_bin comment= '监控配置';
alter table tp_monitor_config add primary key(config_id);

/* 生成 邮件发送记录表【tp_send_mail_record】表结构全量脚本 */
-- drop table if exists tp_send_mail_record ;
create table tp_send_mail_record(
        record_id varchar(36)  not null comment '记录id',
        person_name varchar(500) comment '收件人姓名',
        email varchar(500)  not null comment '电子邮箱',
        send_time char(14)  not null comment '发送时间',
        message varchar(5000)  not null comment '发送内容',
        status integer comment '发送状态（1:成功，0：失败）',
        tenant_id varchar(36) comment '租户id',
        actived int default 1 not null comment '是否有效',
        creator varchar(36) comment '创建人',
        create_time varchar(14) comment '创建时间',
        updator varchar(36) comment '修改人',
        update_time varchar(14) comment '修改时间',
        extend01 varchar(150) comment '扩展字段01',
        extend02 varchar(150) comment '扩展字段02',
        extend03 varchar(150) comment '扩展字段03',
        extend04 varchar(150) comment '扩展字段04',
        extend05 varchar(150) comment '扩展字段05'
)engine=innodb default charset=utf8mb4 collate=utf8mb4_bin comment= '邮件发送记录表';
alter table tp_send_mail_record add primary key(record_id);
/* 生成 客户端基本信息【tp_monitor_client】表结构全量脚本 */
-- drop table if exists tp_monitor_client ;
create table tp_monitor_client(
          client_id varchar(36)  not null comment '客户端id',
          application_name varchar(100) comment '系统名称',
          mac_addr varchar(300) comment 'mac地址',
          absolute_path varchar(200) comment '系统部署的绝对路径',
          system_desc varchar(100) comment '系统描述',
          ip varchar(300) comment 'ip链路',
          remark varchar(300) comment '备注',
          system_url varchar(200) comment '系统访问地址',
          tenant_id varchar(36) comment '租户id',
          actived int default 1 not null comment '是否有效',
          creator varchar(36) comment '创建人',
          create_time varchar(14) comment '创建时间',
          updator varchar(36) comment '修改人',
          update_time varchar(14) comment '修改时间',
          extend01 varchar(150) comment '扩展字段01',
          extend02 varchar(150) comment '扩展字段02',
          extend03 varchar(150) comment '扩展字段03',
          extend04 varchar(150) comment '扩展字段04',
          extend05 varchar(150) comment '扩展字段05'
)engine=innodb default charset=utf8mb4 collate=utf8mb4_bin comment= '客户端基本信息';
alter table tp_monitor_client add primary key(client_id);