-- 数据库迁移脚本：删除tp_tag表的actived字段
-- 执行时间：请在部署前执行此脚本
-- 注意：此操作不可逆，请确保已备份数据库

-- 1. 删除可能存在的actived字段相关索引
DROP INDEX IF EXISTS idx_tp_tag_actived ON tp_tag;

-- 2. 删除actived字段
ALTER TABLE tp_tag DROP COLUMN IF EXISTS actived;

-- 3. 验证字段是否已删除（可选）
-- SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS 
-- WHERE TABLE_NAME = 'tp_tag' AND COLUMN_NAME = 'actived';
-- 如果查询结果为空，说明字段已成功删除

COMMIT;