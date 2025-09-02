# 系统配置表分析报告

## 📊 基本统计信息

- **表名**: `tp_system_config`
- **总配置项数量**: 29 项
- **导出时间**: 2025-01-31
- **数据库**: ps-bmp

## 🏗️ 表结构

| 字段名 | 数据类型 | 是否必填 | 默认值 | 说明 |
|--------|----------|----------|--------|---------|
| config_key | varchar(100) | 是 | - | 配置键（主键） |
| config_value | text | 否 | - | 配置值 |
| config_type | varchar(50) | 否 | 'string' | 配置类型 |
| description | varchar(500) | 否 | - | 配置描述 |
| create_time | datetime | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | 否 | CURRENT_TIMESTAMP | 更新时间 |

## 📋 配置分类统计

### 1. 系统基础配置 (7项)
- `system.name` - 系统名称
- `system.version` - 系统版本
- `system.language` - 系统语言
- `system.timezone` - 系统时区
- `system.theme` - 系统主题
- `system.copyright` - 版权信息
- `browser.title` - 浏览器标题

### 2. 界面配置 (4项)
- `admin.title` - 管理后台标题
- `admin.logo` - 管理后台Logo地址
- `login.title` - 登录页面标题
- `login.logo` - 登录页面Logo地址

### 3. 安全配置 (2项)
- `password.validity.months` - 密码有效期（月）
- `password.expiry.reminder.days` - 密码到期提醒天数

### 4. IP访问控制 (5项)
- `security.ip.enabled` - IP访问控制开关
- `security.ip.whitelist` - IP白名单
- `security.ip.blacklist` - IP黑名单
- `security.ip.log.enabled` - IP访问日志开关
- `security.ip.deny.message` - IP访问拒绝消息

### 5. 用户访问控制 (6项)
- `security.user.access.enabled` - 用户访问控制开关
- `security.user.access.default.policy` - 默认访问策略
- `security.user.access.admin.bypass` - 管理员绕过控制
- `security.user.access.rules` - 访问控制规则
- `security.user.access.log.enabled` - 用户访问日志开关
- `security.user.access.deny.message` - 用户访问拒绝消息

### 6. 个人信息审核 (2项)
- `person.audit.enabled` - 个人信息修改审核功能开关
- `person.audit.fields` - 需要审核的字段列表

### 7. 测试配置 (3项)
- `test.config` - 测试配置项
- `11` - 测试用配置1
- `22` - 测试用配置2

## 📈 配置类型分布

| 配置类型 | 数量 | 占比 |
|----------|------|------|
| string | 27 | 93.1% |
| boolean | 1 | 3.4% |
| array | 1 | 3.4% |

## 🔧 重要配置项说明

### 系统标识配置
- **系统名称**: 陕西行政管理学院业务中台
- **系统版本**: 1.0.0
- **浏览器标题**: 陕西行政学院

### 安全功能状态
- **IP访问控制**: 已禁用 (`security.ip.enabled: false`)
- **用户访问控制**: 已禁用 (`security.user.access.enabled: false`)
- **个人信息审核**: 已启用 (`person.audit.enabled: true`)
- **密码有效期**: 3个月
- **密码到期提醒**: 30天

### 审核字段配置
- **需要审核的字段**: sex, idcard (性别、身份证号)

## 📁 导出文件说明

本次导出生成了以下文件：

1. **`system_config_export.sql`** - 原始数据导出（制表符分隔）
2. **`system_config_insert_statements.sql`** - INSERT语句格式
3. **`tp_system_config_complete_export.sql`** - 完整导出（包含表结构+数据+说明）
4. **`system_config_table_format.txt`** - 表格格式导出
5. **`system_config_analysis_report.md`** - 本分析报告

## 💡 建议

1. **清理测试数据**: 建议删除配置键为 `11`、`22`、`test.config` 的测试配置项
2. **安全配置**: 根据实际需求考虑启用IP访问控制和用户访问控制
3. **配置规范**: 建议为所有配置项添加完整的描述信息
4. **备份策略**: 定期备份系统配置表，确保配置安全

## 📞 联系信息

如需更多信息或有疑问，请联系系统管理员。

---
*报告生成时间: 2025-01-31*
*数据来源: ps-bmp.tp_system_config*