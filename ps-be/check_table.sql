-- 检查org_tree_change_history表是否存在
SHOW TABLES LIKE 'org_tree_change_history';

-- 如果表存在，显示表结构
DESC org_tree_change_history;

-- 显示表中的记录数
SELECT COUNT(*) as record_count FROM org_tree_change_history;

-- 显示最近的几条记录
SELECT * FROM org_tree_change_history ORDER BY operation_time DESC LIMIT 5;