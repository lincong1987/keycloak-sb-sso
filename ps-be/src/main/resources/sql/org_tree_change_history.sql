-- 组织机构树变更历史记录表
CREATE TABLE IF NOT EXISTS org_tree_change_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    version BIGINT NOT NULL COMMENT '版本号',
    operation_type VARCHAR(20) NOT NULL COMMENT '操作类型：CREATE, UPDATE, DELETE, MOVE',
    operation_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(100) COMMENT '操作人姓名',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    dept_name VARCHAR(200) COMMENT '部门名称',
    parent_id BIGINT COMMENT '父部门ID',
    old_parent_id BIGINT COMMENT '原父部门ID（移动操作时使用）',
    dept_code VARCHAR(50) COMMENT '部门编码',
    old_dept_code VARCHAR(50) COMMENT '原部门编码（更新操作时使用）',
    dept_level INT COMMENT '部门层级',
    old_dept_level INT COMMENT '原部门层级（移动操作时使用）',
    sort_order INT COMMENT '排序号',
    old_sort_order INT COMMENT '原排序号（更新操作时使用）',
    description TEXT COMMENT '变更描述',
    before_data JSON COMMENT '变更前数据（JSON格式）',
    after_data JSON COMMENT '变更后数据（JSON格式）',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    
    INDEX idx_version (version),
    INDEX idx_operation_time (operation_time),
    INDEX idx_operator_id (operator_id),
    INDEX idx_dept_id (dept_id),
    INDEX idx_operation_type (operation_type),
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织机构树变更历史记录表';

-- 创建版本号序列表
CREATE TABLE IF NOT EXISTS org_tree_version_sequence (
    id INT PRIMARY KEY DEFAULT 1,
    current_version BIGINT NOT NULL DEFAULT 0,
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构版本号序列表';

-- 初始化版本号
INSERT IGNORE INTO org_tree_version_sequence (id, current_version) VALUES (1, 0);

-- 创建获取下一个版本号的函数
DELIMITER //
CREATE FUNCTION IF NOT EXISTS get_next_org_version() RETURNS BIGINT
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE next_version BIGINT;
    UPDATE org_tree_version_sequence SET current_version = current_version + 1 WHERE id = 1;
    SELECT current_version INTO next_version FROM org_tree_version_sequence WHERE id = 1;
    RETURN next_version;
END//
DELIMITER ;

-- 创建记录变更的存储过程
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS record_org_tree_change(
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
END//
DELIMITER ;

-- 创建视图：最新版本记录
CREATE OR REPLACE VIEW v_org_tree_latest_changes AS
SELECT 
    h.*,
    ROW_NUMBER() OVER (PARTITION BY h.dept_id ORDER BY h.version DESC) as rn
FROM org_tree_change_history h
WHERE h.version = (SELECT MAX(version) FROM org_tree_change_history);

-- 创建视图：操作统计
CREATE OR REPLACE VIEW v_org_tree_operation_stats AS
SELECT 
    operation_type,
    COUNT(*) as operation_count,
    DATE(operation_time) as operation_date,
    operator_name,
    COUNT(DISTINCT dept_id) as affected_dept_count
FROM org_tree_change_history
GROUP BY operation_type, DATE(operation_time), operator_name
ORDER BY operation_date DESC, operation_count DESC;