-- 系统配置表
CREATE TABLE IF NOT EXISTS tp_system_config (
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(500) COMMENT '配置描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (config_key)
) COMMENT='系统配置表';

-- 初始化基础配置数据
INSERT INTO tp_system_config (config_key, config_value, description) VALUES
('system.name', '管理系统', '系统名称'),
('system.version', '1.0.0', '系统版本'),
('login.title', '用户登录', '登录页面标题'),
('login.logo', '/static/images/login-logo.png', '登录页面Logo地址'),
('admin.title', '管理后台', '管理后台标题'),
('admin.logo', '/static/images/admin-logo.png', '管理后台Logo地址'),
('system.copyright', '© 2024 管理系统 版权所有', '版权信息'),
('system.theme', 'default', '系统主题'),
('system.language', 'zh-CN', '系统语言'),
('system.timezone', 'Asia/Shanghai', '系统时区')
ON DUPLICATE KEY UPDATE 
    config_value = VALUES(config_value),
    description = VALUES(description),
    update_time = CURRENT_TIMESTAMP;