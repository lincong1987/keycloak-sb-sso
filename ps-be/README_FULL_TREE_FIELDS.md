# 组织机构历史记录全节点树功能

## 功能概述

本功能扩展了组织机构变更历史记录的能力，除了记录单个节点的变更数据外，还能记录变更前后的完整组织机构树结构，为审计、回滚和问题排查提供更全面的数据支持。

## 新增功能

### 1. 数据库字段扩展

在 `org_tree_change_history` 表中新增了两个字段：

- `before_full_tree` (LONGTEXT): 变更前的完整组织机构节点树JSON
- `after_full_tree` (LONGTEXT): 变更后的完整组织机构节点树JSON

### 2. 服务层新增方法

#### TpDeptBasicinfoService 接口

```java
/**
 * 获取完整的组织机构树（包含所有节点）
 * 用于记录变更历史时保存完整的组织架构快照
 */
List<TreeNode> getFullTree(String rootId, int category);
```

#### OrgTreeChangeHistoryService 接口

```java
/**
 * 记录组织机构树变更（包含全节点树）
 */
Long recordChangeWithFullTree(String operationType, Long operatorId, 
                             String beforeData, String afterData, 
                             String beforeFullTree, String afterFullTree);
```

## 使用方法

### 数据库更新

首先需要执行数据库更新脚本：

```sql
-- 执行 ps-be/sql/update/add_full_tree_columns.sql
```

### 代码使用

#### 方式一：使用新方法（推荐）

```java
// 记录包含完整树结构的变更历史
orgTreeChangeHistoryService.recordChangeWithFullTree(
    "UPDATE", 
    operatorId, 
    beforeData, 
    afterData,
    beforeFullTreeJson,  // 变更前的完整树JSON
    afterFullTreeJson    // 变更后的完整树JSON
);
```

#### 方式二：使用原有方法（向后兼容）

```java
// 继续使用原有方法，只记录单个节点变更
orgTreeChangeHistoryService.recordChange(
    "UPDATE", 
    operatorId, 
    beforeData, 
    afterData
);
```

## 自动集成

### 部门管理操作自动记录

所有通过 `TpDeptBasicinfoService` 进行的部门增删改操作都已自动集成了完整树记录功能：

1. **新增部门**: 自动记录变更后的完整树结构
2. **修改部门**: 自动记录变更前后的完整树结构
3. **删除部门**: 自动记录变更前后的完整树结构

### 自动执行时机

- **变更前树获取**: 在数据库操作之前获取
- **变更后树获取**: 在数据库操作之后获取
- **错误处理**: 获取树结构失败不会影响主业务流程

## 性能考虑

### 优化策略

1. **异步处理**: 变更历史记录不影响主业务性能
2. **错误隔离**: 历史记录失败只记录警告日志，不抛出异常
3. **条件获取**: 只在必要时获取完整树结构

### 注意事项

1. 完整树结构可能包含大量数据，建议定期清理历史记录
2. 在高并发场景下，建议监控数据库性能
3. 如果组织架构过于庞大，可考虑分批记录或采用增量记录方式

## 查询示例

### 查询包含完整树的变更记录

```java
// 获取最新的变更记录
OrgTreeChangeHistory latestRecord = orgTreeChangeHistoryService.getLatestVersion();

if (latestRecord != null) {
    // 检查是否包含完整树数据
    boolean hasFullTree = latestRecord.getBeforeFullTree() != null || 
                         latestRecord.getAfterFullTree() != null;
    
    if (hasFullTree) {
        // 解析完整树JSON
        List<TreeNode> beforeTree = JSONObject.parseArray(
            latestRecord.getBeforeFullTree(), TreeNode.class);
        List<TreeNode> afterTree = JSONObject.parseArray(
            latestRecord.getAfterFullTree(), TreeNode.class);
        
        // 进行树结构对比分析
        // ...
    }
}
```

### 查询特定操作类型的记录

```java
// 查询所有更新操作的记录
List<OrgTreeChangeHistory> updateRecords = 
    orgTreeChangeHistoryService.getByOperationType("UPDATE");

// 统计包含完整树的记录数量
long fullTreeRecordCount = updateRecords.stream()
    .filter(record -> record.getBeforeFullTree() != null || 
                     record.getAfterFullTree() != null)
    .count();
```

## 测试验证

可以使用提供的示例类进行功能验证：

```java
@Autowired
private OrgTreeFullHistoryExample example;

// 运行所有演示
example.runAllDemonstrations();

// 或单独运行特定演示
example.demonstrateGetFullTree();
example.demonstrateAddDeptWithFullTree();
```

## 故障排除

### 常见问题

1. **完整树为空**: 检查 `getFullTree` 方法是否正确实现
2. **历史记录失败**: 查看日志中的警告信息，通常不影响主业务
3. **性能问题**: 监控数据库查询性能，考虑优化树查询逻辑

### 日志监控

关注以下日志信息：

- `获取变更前/后的完整组织树失败`
- `记录组织架构变更历史失败`
- `recordChangeWithFullTree` 相关的错误日志

## 版本兼容性

- **向后兼容**: 原有的 `recordChange` 方法继续可用
- **数据库兼容**: 新字段允许为空，不影响现有数据
- **API兼容**: 不影响现有业务接口

## 后续扩展

可以基于完整树数据实现更多功能：

1. **树结构差异分析**: 对比变更前后的树结构差异
2. **可视化展示**: 图形化展示组织架构变更过程
3. **智能回滚**: 基于历史树结构实现精确回滚
4. **变更影响分析**: 分析组织架构变更的影响范围