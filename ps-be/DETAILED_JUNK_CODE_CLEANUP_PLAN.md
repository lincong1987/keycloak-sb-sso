# 项目垃圾代码清理详细执行计划

## 1. 概述

本计划基于之前的垃圾代码清理方案，提供了更细粒度的任务分解和执行步骤，确保垃圾代码清理工作有序、安全地进行。

## 2. 清理任务详细分解

### 2.1 第一阶段：低风险代码清理（预计时间：2天）

#### 任务1：移除调试打印语句
**负责人：** 开发人员A
**预计时间：** 0.5天
**实际完成时间：** 2025-09-07

**具体任务：**
1. **TpOperateLogController.java**
   - 移除第146行的调试打印语句
   - 文件路径：`src/main/java/com/jiuxi/admin/core/controller/TpOperateLogController.java`

2. **OrgTreeChangeHistoryExample.java**
   - 移除第87行和第89行的调试打印语句
   - 文件路径：`src/main/java/com/jiuxi/admin/core/example/OrgTreeChangeHistoryExample.java`

3. **TpAccountServiceImpl.java**
   - 移除第960行的调试打印语句
   - 文件路径：`src/main/java/com/jiuxi/admin/core/service/impl/TpAccountServiceImpl.java`

4. **AesUtils.java**
   - 移除第184行和第186行的调试打印语句
   - 文件路径：`src/main/java/com/jiuxi/common/util/AesUtils.java`

5. **其他文件中的调试语句**
   - 搜索并移除所有System.out.println和System.err.println语句
   - 使用日志框架替代调试输出

**验证方式：**
- 代码审查确认所有调试语句已被移除
- 运行相关功能确保没有影响

**状态：** 已完成

#### 任务2：移除注释掉的无用代码
**负责人：** 开发人员B
**预计时间：** 1天

**具体任务：**
1. **AdminAutoConfiguration.java**
   - 移除注释掉的import语句（第8行）
   - 移除注释掉的方法downloadDataService()（第81-84行）
   - 文件路径：`src/main/java/com/jiuxi/admin/autoconfig/AdminAutoConfiguration.java`

2. **OrgTreeChangeHistory.java**
   - 移除注释掉的Swagger注解（第8-9行，第21行，第30、37、44、52、59、66、73、80行）
   - 文件路径：`src/main/java/com/jiuxi/admin/core/bean/OrgTreeChangeHistory.java`

3. **TpOperateLogController.java**
   - 移除注释掉的代码块（第49-61行）
   - 文件路径：`src/main/java/com/jiuxi/admin/core/controller/TpOperateLogController.java`

4. **其他文件中的注释代码**
   - 全面搜索项目中的注释代码块
   - 评估并移除确实无用的注释代码

**验证方式：**
- 代码审查确认注释代码已被移除
- 编译项目确保没有错误

#### 任务3：移除main方法中的测试代码
**负责人：** 开发人员C
**预计时间：** 0.5天
**实际完成时间：** 2025-09-07

**具体任务：**
1. **AesUtils.java**
   - 移除整个main方法（第80-195行）
   - 文件路径：`src/main/java/com/jiuxi/common/util/AesUtils.java`

2. **BcryptUtils.java**
   - 移除整个main方法（第64-68行）
   - 文件路径：`src/main/java/com/jiuxi/common/util/BcryptUtils.java`

3. **SensitiveUtils.java**
   - 移除整个main方法（第59-62行）
   - 文件路径：`src/main/java/com/jiuxi/common/util/SensitiveUtils.java`

4. **HtmlFilter.java**
   - 移除整个main方法（第115-127行）
   - 文件路径：`src/main/java/com/jiuxi/core/core/filter/HtmlFilter.java`

**验证方式：**
- 代码审查确认main方法已被移除
- 编译项目确保没有错误

**状态：** 已完成

### 2.2 第二阶段：中风险代码清理（预计时间：3天）

#### 任务4：处理未实现的TODO功能
**负责人：** 开发团队
**预计时间：** 3天

**具体任务：**
1. **TpTraceController.java**
   - 实现view、add、update、delete方法的具体功能
   - 或者如果功能确实不需要，添加@Deprecated注解并记录原因
   - 文件路径：`src/main/java/com/jiuxi/admin/core/controller/pc/TpTraceController.java`

2. **CJsonServiceImpl.java**
   - 处理第390行和第489行的TODO注释
   - 文件路径：`src/main/java/com/jiuxi/admin/core/service/impl/CJsonServiceImpl.java`

3. **TpAccountServiceImpl.java**
   - 处理第145行的TODO注释
   - 文件路径：`src/main/java/com/jiuxi/admin/core/service/impl/TpAccountServiceImpl.java`

4. **其他Service实现类中的TODO**
   - TpAccountThirdServiceImpl.java 第129行
   - TpAgentDealServiceImpl.java 第94行和第125行
   - TpAgentServiceImpl.java 第126行和第173行
   - TpCustomFormServiceImpl.java 第135行
   - TpCustomModuleServiceImpl.java 第171行
   - TpMediorgServiceImpl.java 第188行
   - TpMessageReadServiceImpl.java 第84行和第115行
   - TpMessageServiceImpl.java 第148行和第195行

**验证方式：**
- 单元测试确保新实现的功能正常工作
- 集成测试验证相关接口功能
- 代码审查确认TODO已被妥善处理

#### 任务5：完善未完成的方法实现
**负责人：** 开发人员D
**预计时间：** 1天

**具体任务：**
1. **PhoneDataMigrationUtil.java**
   - 实现validateMigration方法的具体功能
   - 或者如果方法确实不需要，移除该方法并记录原因
   - 文件路径：`src/main/java/com/jiuxi/common/util/PhoneDataMigrationUtil.java`

**验证方式：**
- 单元测试确保方法功能正常
- 代码审查确认实现符合需求

### 2.3 第三阶段：高风险代码清理（预计时间：1天）

#### 任务6：评估并处理临时性测试控制器
**负责人：** 架构师
**预计时间：** 1天

**具体任务：**
1. **SqlExecutorController.java**
   - 评估该控制器的安全风险
   - 如果确认为临时测试控制器且存在安全风险，应移除
   - 如果确实需要保留，应添加安全限制（如IP白名单、权限验证等）
   - 文件路径：`src/main/java/com/jiuxi/admin/core/controller/SqlExecutorController.java`

**验证方式：**
- 安全审查确认风险已处理
- 功能测试确保不影响正常业务

## 3. 时间计划

| 阶段 | 任务 | 负责人 | 预计开始时间 | 预计完成时间 | 实际完成时间 |
|------|------|--------|--------------|--------------|--------------|
| 第一阶段 | 移除调试打印语句 | 开发人员A | Day 1 | Day 1 | 2025-09-07 |
| 第一阶段 | 移除注释掉的无用代码 | 开发人员B | Day 1 | Day 2 | |
| 第一阶段 | 移除main方法中的测试代码 | 开发人员C | Day 2 | Day 2 | 2025-09-07 |
| 第二阶段 | 处理未实现的TODO功能 | 开发团队 | Day 3 | Day 5 | |
| 第二阶段 | 完善未完成的方法实现 | 开发人员D | Day 5 | Day 5 | |
| 第三阶段 | 评估并处理临时性测试控制器 | 架构师 | Day 6 | Day 6 | |

## 4. 风险控制

### 4.1 风险识别
1. **功能缺失风险** - 移除TODO功能可能导致某些业务功能缺失
2. **安全风险** - 移除或保留临时测试控制器可能带来安全问题
3. **回归风险** - 代码修改可能引入新的bug

### 4.2 风险应对措施
1. **功能缺失风险应对**：
   - 在移除TODO功能前，确认是否确实需要实现
   - 对于需要实现的功能，制定详细的需求文档
   - 通过测试确保功能正常

2. **安全风险应对**：
   - 对临时测试控制器进行安全评估
   - 如果保留，添加必要的安全限制
   - 如果移除，确保没有其他依赖

3. **回归风险应对**：
   - 每次修改后运行相关测试
   - 进行代码审查
   - 部署到测试环境验证

## 5. 验证方案

### 5.1 单元测试
- 运行所有现有单元测试，确保没有因代码清理而失败
- 为新实现的功能编写单元测试

### 5.2 集成测试
- 运行核心业务流程的集成测试
- 验证API接口功能正常

### 5.3 代码审查
- 每个任务完成后进行代码审查
- 确认垃圾代码已被正确清理
- 确认新代码符合编码规范

### 5.4 安全审查
- 对处理的临时测试控制器进行安全审查
- 确认没有引入新的安全漏洞

## 6. 回滚计划

如果在清理过程中发现严重问题，应立即停止并执行回滚：

1. **Git回滚**：
   - 使用git revert命令回滚相关提交
   - 确保回滚操作不会影响其他已合并的代码

2. **问题分析**：
   - 分析问题原因
   - 重新评估清理方案

3. **重新执行**：
   - 修正问题后重新执行清理任务
   - 加强测试和验证

## 7. 成功标准

1. 项目中不再存在明显的垃圾代码
2. 所有单元测试和集成测试通过
3. 代码审查通过
4. 安全审查通过
5. 系统功能正常运行