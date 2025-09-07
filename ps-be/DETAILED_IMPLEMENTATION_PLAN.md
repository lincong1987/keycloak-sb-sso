# PS-BE 项目结构优化详细实施计划

## 1. 第一阶段：基础结构调整

### 1.1 创建新的包结构

#### 1.1.1 创建应用层包结构
````
src/main/java/com/jiuxi/
├── app/
│   ├── auth/
│   ├── org/
│   ├── sys/
│   ├── user/
│   ├── log/
│   ├── file/
│   └── common/
````

**操作步骤：**
1. 创建 [app](file://src/main/java/com/jiuxi/app) 目录
2. 在 [app](file://src/main/java/com/jiuxi/app) 目录下创建 auth、org、sys、user、log、file、common 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.2 创建领域层包结构
````
src/main/java/com/jiuxi/
├── domain/
│   ├── auth/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   ├── org/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   ├── sys/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   ├── user/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   ├── log/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   ├── file/
│   │   ├── model/
│   │   ├── service/
│   │   ├── repo/
│   │   └── exception/
│   └── common/
│       ├── model/
│       ├── service/
│       ├── repo/
│       └── exception/
````

**操作步骤：**
1. 创建 [domain](file://src/main/java/com/jiuxi/domain) 目录
2. 在 [domain](file://src/main/java/com/jiuxi/domain) 目录下创建 auth、org、sys、user、log、file、common 子目录
3. 在每个业务领域目录下创建 model、service、repo、exception 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.3 创建基础设施层包结构
````
src/main/java/com/jiuxi/
├── infra/
│   ├── auth/
│   ├── org/
│   ├── sys/
│   ├── user/
│   ├── log/
│   ├── file/
│   ├── common/
│   └── config/
````

**操作步骤：**
1. 创建 [infra](file://src/main/java/com/jiuxi/infra) 目录
2. 在 [infra](file://src/main/java/com/jiuxi/infra) 目录下创建 auth、org、sys、user、log、file、common、config 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.4 创建接口层包结构
````
src/main/java/com/jiuxi/
├── intf/
│   ├── auth/
│   ├── org/
│   ├── sys/
│   ├── user/
│   ├── log/
│   ├── file/
│   └── common/
````

**操作步骤：**
1. 创建 [intf](file://src/main/java/com/jiuxi/intf) 目录
2. 在 [intf](file://src/main/java/com/jiuxi/intf) 目录下创建 auth、org、sys、user、log、file、common 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.5 创建配置层包结构
````
src/main/java/com/jiuxi/
├── config/
│   ├── sec/
│   ├── db/
│   ├── cache/
│   ├── web/
│   └── common/
````

**操作步骤：**
1. 创建 [config](file://src/main/java/com/jiuxi/config) 目录
2. 在 [config](file://src/main/java/com/jiuxi/config) 目录下创建 sec、db、cache、web、common 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.6 创建通用层包结构
````
src/main/java/com/jiuxi/
├── common/
│   ├── util/
│   ├── exception/
│   ├── constant/
│   └── annotation/
````

**操作步骤：**
1. 创建 [common](file://src/main/java/com/jiuxi/common_2) 目录（注意与现有 common 包区分）
2. 在 [common](file://src/main/java/com/jiuxi/common_2) 目录下创建 util、exception、constant、annotation 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.1.7 创建启动层包结构
````
src/main/java/com/jiuxi/
├── startup/
│   ├── config/
│   └── Application.java
````

**操作步骤：**
1. 创建 [startup](file://src/main/java/com/jiuxi/startup) 目录
2. 在 [startup](file://src/main/java/com/jiuxi/startup) 目录下创建 config 子目录

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

### 1.2 移动 Application.java

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/Application.java` 移动到 `src/main/java/com/jiuxi/startup/Application.java`
2. 更新 Application.java 中的包声明为 `package com.jiuxi.startup;`
3. 更新相关的导入语句

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

### 1.3 重构配置文件目录结构

#### 1.3.1 创建新的配置目录结构
````
src/main/resources/
├── application.yml
├── config/
│   ├── application-dev.yml
│   ├── application-test.yml
│   ├── application-prod.yml
│   ├── database.yml
│   ├── security.yml
│   ├── cache.yml
│   └── logback-spring.xml
````

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.3.2 迁移配置文件

**操作步骤：**
1. 创建新的 config 目录结构
2. 将 `src/main/resources/config/env/dev/application-dev.yml` 移动到 `src/main/resources/config/application-dev.yml`
3. 将 `src/main/resources/config/env/test/application-test.yml` 移动到 `src/main/resources/config/application-test.yml`
4. 将 `src/main/resources/config/env/prod/application-prod.yml` 移动到 `src/main/resources/config/application-prod.yml`
5. 将 `src/main/resources/config/db/database-dev.yml`、`database-test.yml`、`database-prod.yml` 合并为 `src/main/resources/config/database.yml`
6. 将 `src/main/resources/config/sec/security-dev.yml`、`security-test.yml`、`security-prod.yml` 合并为 `src/main/resources/config/security.yml`
7. 将 `src/main/resources/config/cache/cache-dev.yml`、`cache-test.yml`、`cache-prod.yml` 合并为 `src/main/resources/config/cache.yml`
8. 将 `src/main/resources/config/log/logback-spring.xml` 移动到 `src/main/resources/config/logback-spring.xml`

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 1.3.3 更新 application.yml

**操作步骤：**
1. 更新 `src/main/resources/application.yml` 中的配置文件导入路径
2. 将原来的：
   ````yaml
   spring:
     config:
       import: 
         - "classpath:config/env/${spring.profiles.active}/application-${spring.profiles.active}.yml"
         - "classpath:config/db/database-${spring.profiles.active}.yml"
         - "classpath:config/cache/cache-${spring.profiles.active}.yml"
         - "classpath:config/sec/security-${spring.profiles.active}.yml"
   ````
   修改为：
   ````yaml
   spring:
     config:
       import: 
         - "classpath:config/application-${spring.profiles.active}.yml"
         - "classpath:config/database.yml"
         - "classpath:config/cache.yml"
         - "classpath:config/security.yml"
   ````

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

## 2. 第二阶段：模块迁移

### 2.1 迁移 module 目录内容

#### 2.1.1 迁移 sys 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/sys/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/sys/`
2. 将 `src/main/java/com/jiuxi/module/sys/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/sys/model/` 和 `src/main/java/com/jiuxi/domain/sys/service/`
3. 将 `src/main/java/com/jiuxi/module/sys/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/sys/`
4. 将 `src/main/java/com/jiuxi/module/sys/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/sys/`

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 2.1.2 迁移 auth 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/auth/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/auth/`
2. 将 `src/main/java/com/jiuxi/module/auth/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/auth/model/` 和 `src/main/java/com/jiuxi/domain/auth/service/`
3. 将 `src/main/java/com/jiuxi/module/auth/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/auth/`
4. 将 `src/main/java/com/jiuxi/module/auth/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/auth/`

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 2.1.3 迁移 org 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/org/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/org/`
2. 将 `src/main/java/com/jiuxi/module/org/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/org/model/` 和 `src/main/java/com/jiuxi/domain/org/service/`
3. 将 `src/main/java/com/jiuxi/module/org/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/org/`
4. 将 `src/main/java/com/jiuxi/module/org/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/org/`

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 2.1.4 迁移 user 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/user/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/user/`
2. 将 `src/main/java/com/jiuxi/module/user/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/user/model/` 和 `src/main/java/com/jiuxi/domain/user/service/`
3. 将 `src/main/java/com/jiuxi/module/user/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/user/`
4. 将 `src/main/java/com/jiuxi/module/user/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/user/`
5. 修复迁移过程中发现的包引用和类路径问题
6. 更新Spring Boot主应用类的包扫描配置以包含新的user模块
7. 解决多个@MapperScan注解相互覆盖的问题

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 2.1.5 迁移 log 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/log/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/log/`
2. 将 `src/main/java/com/jiuxi/module/log/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/log/model/` 和 `src/main/java/com/jiuxi/domain/log/service/`
3. 将 `src/main/java/com/jiuxi/module/log/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/log/`
4. 将 `src/main/java/com/jiuxi/module/log/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/log/`

**状态：** 已完成 ✓
**完成时间：** 2025-09-07

#### 2.1.6 迁移 file 模块

**操作步骤：**
1. 将 `src/main/java/com/jiuxi/module/file/app/` 下的内容移动到 `src/main/java/com/jiuxi/app/file/`
2. 将 `src/main/java/com/jiuxi/module/file/domain/` 下的内容移动到 `src/main/java/com/jiuxi/domain/file/model/` 和 `src/main/java/com/jiuxi/domain/file/service/`
3. 将 `src/main/java/com/jiuxi/module/file/infra/` 下的内容移动到 `src/main/java/com/jiuxi/infra/file/`
4. 将 `src/main/java/com/jiuxi/module/file/interfaces/` 下的内容移动到 `src/main/java/com/jiuxi/intf/file/`
5. 创建FileAppService接口及其实现类FileAppServiceImpl
6. 创建FileController控制器类
7. 修复编译错误，确保项目能够成功编译

**状态：** 已完成 ✓
**完成时间：** 2025-09-08

### 2.2 整合功能模块到新的分层结构

#### 2.2.1 整合 admin 模块

**操作步骤：**
1. 分析 admin 模块的功能，将其归类到相应的业务领域
2. 将 admin/domain/ 下的系统相关模型移动到 domain/sys/model/
3. 将 admin/core/ 下的系统相关服务移动到 domain/sys/service/
4. 将 admin/bean/ 下的 DTO 移动到 app/sys/
5. 将 admin/constant/ 下的常量移动到 common/constant/

#### 2.2.2 整合 captcha 模块

**操作步骤：**
1. 在 app/ 目录下创建 captcha/ 子目录
2. 将 captcha/core/ 下的验证码服务移动到 app/captcha/
3. 在 domain/ 目录下创建 captcha/ 子目录
4. 将 captcha/bean/ 下的模型移动到 domain/captcha/model/
5. 将 captcha/util/ 下的工具类移动到 common/util/

#### 2.2.3 整合 security 模块

**操作步骤：**
1. 将 security/sso/ 下的内容移动到 config/security/
2. 将 security/core/ 下的认证服务移动到 domain/auth/service/
3. 将 security/bean/ 下的模型移动到 domain/auth/model/
4. 将 security/config/ 下的配置移动到 config/security/

#### 2.2.4 整合 mybatis 模块

**操作步骤：**
1. 将 mybatis/service/ 下的数据库服务移动到 infra/common/
2. 将 mybatis/core/ 下的核心功能移动到 infra/config/
3. 将 mybatis/bean/ 下的模型移动到 domain/common/model/
4. 将 mybatis/xss/ 下的 XSS 过滤器移动到 infra/config/
5. 将 mybatis/util/ 下的工具类移动到 common/util/

#### 2.2.5 整合 core 模块

**操作步骤：**
1. 将 core/core/ 下的线程池、HTTP 客户端等通用服务移动到 infra/common/
2. 将 core/bean/ 下的模型移动到 domain/common/model/
3. 将 core/autoconfig/ 下的自动配置移动到 config/common/
4. 将 core/config/ 下的配置移动到 config/common/

#### 2.2.6 整合 mvc 模块

**操作步骤：**
1. 将 mvc/core/ 下的 MVC 配置移动到 config/web/
2. 将 mvc/autoconfig/ 下的自动配置移动到 config/web/

#### 2.2.7 整合 monitor 模块

**操作步骤：**
1. 在 app/ 目录下创建 monitor/ 子目录
2. 将 monitor/client/ 和 monitor/server/ 下的服务移动到 app/monitor/
3. 在 domain/ 目录下创建 monitor/ 子目录
4. 将 monitor/common/ 下的模型移动到 domain/monitor/model/

### 2.3 简化 common 模块

**操作步骤：**
1. 审查 common 模块下的所有子目录
2. 将真正通用的工具类保留在 common/util/
3. 将异常类移动到 common/exception/
4. 将常量类移动到 common/constant/
5. 将其他特定功能的类移动到相应的业务领域

## 3. 第三阶段：代码重构

### 3.1 更新包引用

**操作步骤：**
1. 使用 IDE 的重构功能批量更新包引用
2. 手动检查并更新配置文件中的包引用
3. 更新 pom.xml 中的包扫描配置（如果有的话）

### 3.2 调整组件扫描配置

**操作步骤：**
1. 更新 Application.java 中的 @ComponentScan 注解，包含新的包结构
2. 更新各模块的自动配置类，确保能正确扫描到相关组件
3. 检查并更新 Spring 的配置文件

### 3.3 更新依赖关系

**操作步骤：**
1. 检查各模块间的依赖关系
2. 更新 Maven 依赖（如果需要）
3. 确保各层之间的依赖关系符合分层架构原则

## 4. 第四阶段：测试验证

### 4.1 单元测试验证

**操作步骤：**
1. 创建新的测试目录结构：
   ````
   src/test/java/com/jiuxi/
   ├── unit/
   │   ├── app/
   │   ├── domain/
   │   ├── infra/
   │   └── common/
   ├── integration/
   ├── performance/
   └── testcontainers/
   ````
2. 将现有的单元测试按照新的目录结构进行迁移
3. 运行所有单元测试，确保功能正常

### 4.2 集成测试验证

**操作步骤：**
1. 运行集成测试，确保各模块间的集成正常
2. 验证数据库访问、外部服务调用等功能
3. 检查配置文件加载是否正常

### 4.3 功能测试验证

**操作步骤：**
1. 运行端到端的功能测试
2. 验证所有 REST API 接口是否正常工作
3. 检查认证授权功能是否正常
4. 验证文件上传下载等核心功能

## 5. 时间计划和里程碑

### 5.1 第一阶段：基础结构调整 (1-2周)
- 第1周：创建新的包结构，移动 Application.java
- 第2周：重构配置文件目录结构，更新配置文件

### 5.2 第二阶段：模块迁移 (3-6周)
- 第3周：迁移 sys 和 auth 模块
- 第4周：迁移 org 和 user 模块
- 第5周：迁移 log 和 file 模块
- 第6周：整合功能模块到新的分层结构

### 5.3 第三阶段：代码重构 (7-8周)
- 第7周：更新包引用，调整组件扫描配置
- 第8周：更新依赖关系，解决重构过程中出现的问题

### 5.4 第四阶段：测试验证 (9-10周)
- 第9周：单元测试和集成测试验证
- 第10周：功能测试验证，修复发现的问题

## 6. 风险控制和应对措施

### 6.1 风险识别
1. **功能中断风险**：在迁移过程中可能导致部分功能暂时不可用
2. **编译错误风险**：包引用更新不完整可能导致编译错误
3. **配置错误风险**：配置文件调整可能导致运行时错误
4. **团队适应风险**：团队成员需要时间适应新的项目结构

### 6.2 应对措施
1. **分阶段实施**：采用渐进式迁移策略，每完成一个阶段就进行测试
2. **版本控制**：使用 Git 进行版本控制，确保可以回滚到之前的版本
3. **充分测试**：每个阶段完成后进行充分的测试
4. **文档更新**：及时更新相关文档和开发指南
5. **团队培训**：组织培训帮助团队成员熟悉新结构

## 7. 质量保证措施

### 7.1 代码审查
1. 每个阶段的代码变更都需要进行代码审查
2. 确保代码符合新的架构规范

### 7.2 自动化测试
1. 确保所有自动化测试用例都能通过
2. 增加必要的测试用例以覆盖新架构的功能

### 7.3 持续集成
1. 确保 CI/CD 流程能适应新的项目结构
2. 每次提交都触发自动化构建和测试

## 8. 文档更新计划

### 8.1 更新开发文档
1. 更新项目架构文档
2. 更新开发指南
3. 更新代码规范文档

### 8.2 更新配置文档
1. 更新配置文件说明文档
2. 更新环境配置指南

### 8.3 更新测试文档
1. 更新测试策略文档
2. 更新测试用例文档

## 9. 总结

本实施计划详细描述了项目结构优化的每个步骤和操作指南。通过分阶段、分步骤的方式实施，可以最大程度地降低风险，确保项目平稳过渡到新的架构。建议严格按照计划执行，并在每个阶段完成后进行充分的测试和验证。