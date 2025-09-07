# 项目垃圾代码清理方案

## 1. 概述

在对ps-be项目进行代码审查后，发现了多处垃圾代码，包括：
- 未实现的TODO注释
- 调试用的打印语句
- 注释掉的无用代码
- 临时性的测试代码
- 未完成的方法实现

本方案旨在清理这些垃圾代码，提高代码质量和可维护性。

## 2. 垃圾代码分类及清理方案

### 2.1 TODO注释

#### 2.1.1 未实现功能的TODO
在多个Controller和Service类中发现了未实现功能的TODO注释，这些方法通常返回空结果或抛出异常。

**示例文件：**
- [TpTraceController.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/core/controller/pc/TpTraceController.java) - view, add, update, delete方法
- 多个Service实现类中的TODO注释

**清理方案：**
1. 对于确实需要实现的功能，创建具体的实现代码
2. 对于不需要的功能，移除相关方法或添加适当的注释说明

### 2.2 调试打印语句

#### 2.2.1 System.out.println语句
在多个业务逻辑代码中发现了调试用的System.out.println语句。

**示例文件：**
- [TpOperateLogController.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/core/controller/TpOperateLogController.java) - 第146行
- [OrgTreeChangeHistoryExample.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/core/example/OrgTreeChangeHistoryExample.java) - 第87、89行
- [TpAccountServiceImpl.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/core/service/impl/TpAccountServiceImpl.java) - 第960行

**清理方案：**
1. 将调试打印语句替换为合适的日志记录
2. 或者直接移除这些调试语句

### 2.3 注释掉的代码

#### 2.3.1 无用的注释代码块
在多个配置类和实体类中发现了被注释掉的无用代码。

**示例文件：**
- [AdminAutoConfiguration.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/autoconfig/AdminAutoConfiguration.java) - 注释掉的import和方法
- [OrgTreeChangeHistory.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/admin/core/bean/OrgTreeChangeHistory.java) - 注释掉的Swagger注解

**清理方案：**
1. 如果代码确实不再需要，直接删除
2. 如果代码可能在未来使用，添加明确的注释说明

### 2.4 临时性测试代码

#### 2.4.1 main方法测试代码
在多个工具类中发现了用于测试的main方法。

**示例文件：**
- [AesUtils.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/common/util/AesUtils.java) - 第80行
- [BcryptUtils.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/common/util/BcryptUtils.java) - 第64行

**清理方案：**
1. 移除main方法中的测试代码
2. 或者将测试代码移到专门的测试类中

### 2.5 未完成的方法实现

#### 2.5.1 抛出UnsupportedOperationException的方法
在一些工具类中发现了未完成的方法实现。

**示例文件：**
- [PhoneDataMigrationUtil.java](file:///d%3A/jiuxi/project/keycloak-sb-sso/ps-be/src/main/java/com/jiuxi/common/util/PhoneDataMigrationUtil.java) - 第231行

**清理方案：**
1. 实现具体功能
2. 或者移除该方法

## 3. 风险评估

### 3.1 低风险
- 移除调试打印语句
- 移除注释掉的无用代码

### 3.2 中风险
- 实现TODO功能
- 完善未完成的方法

### 3.3 高风险
- 移除临时性测试控制器（如SqlExecutorController）

## 4. 实施步骤

### 4.1 第一阶段：低风险清理
1. 移除所有调试打印语句
2. 移除注释掉的无用代码
3. 移除main方法中的测试代码

### 4.2 第二阶段：中风险清理
1. 处理TODO注释
2. 完善未完成的方法实现

### 4.3 第三阶段：高风险清理
1. 评估并处理临时性测试控制器
2. 移除不再需要的配置类

## 5. 验证方案

1. 运行单元测试确保功能正常
2. 进行集成测试验证系统稳定性
3. 代码审查确认清理效果