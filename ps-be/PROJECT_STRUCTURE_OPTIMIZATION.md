# PS-BE 项目结构优化方案

## 1. 当前项目结构分析

### 1.1 项目概述
当前 ps-be 项目是一个基于 Spring Boot 2.7.18 的后端服务，采用了模块化分层架构设计。项目包含多个功能模块，如 admin、captcha、common、config、core、module、monitor、mvc、mybatis、security 等。

### 1.2 当前目录结构
```
ps-be/
├── nginx/                      # Nginx 配置文件
├── sql/                        # 数据库脚本
├── src/main/
│   ├── java/com/jiuxi/
│   │   ├── Application.java    # Spring Boot 启动类
│   │   ├── admin/             # 管理模块
│   │   ├── captcha/           # 验证码模块
│   │   ├── common/            # 通用模块
│   │   ├── config/            # 配置模块
│   │   ├── core/              # 核心模块
│   │   ├── module/            # 业务模块
│   │   ├── monitor/           # 监控模块
│   │   ├── mvc/               # MVC 模块
│   │   ├── mybatis/           # MyBatis 集成模块
│   │   └── security/          # 安全模块
│   └── resources/
│       ├── META-INF/
│       ├── application.yml    # 主配置文件
│       ├── config/            # 配置文件目录
│       ├── i18n/              # 国际化资源
│       └── mapper/            # MyBatis 映射文件
├── pom.xml                    # Maven 配置文件
└── uploads/                   # 上传文件目录
```

### 1.3 存在的问题

1. **模块划分不够清晰**：
   - common 模块过于庞大，包含 16 个子目录
   - module 业务模块与功能模块（如 admin、security）存在重叠
   - 部分功能模块职责不够明确

2. **包命名规范不统一**：
   - 部分模块使用 app、domain、infra、interfaces 分层
   - 部分模块使用 core、bean、config 等传统分层方式

3. **配置文件管理复杂**：
   - config 目录下有多个子目录（cache、db、env、log、sec）
   - 配置文件分散在不同目录中，维护困难

4. **业务模块组织方式不一致**：
   - module 目录下的业务模块采用 DDD 分层（app、domain、infra、interfaces）
   - 其他功能模块采用传统分层方式

## 2. 优化目标

1. **统一架构风格**：采用一致的分层架构和命名规范
2. **模块职责清晰**：每个模块有明确的职责边界
3. **提高可维护性**：简化目录结构，降低维护成本
4. **增强可扩展性**：便于添加新功能模块
5. **遵循最佳实践**：符合 Spring Boot 项目结构最佳实践

## 3. 优化方案

### 3.1 总体架构调整

建议采用以下分层架构：

```
ps-be/
├── app/               # 应用层（控制器、DTO、事件等）
├── domain/                   # 领域层（实体、领域服务、仓储接口等）
├── infra/           # 基础设施层（仓储实现、外部服务适配器等）
├── intf/               # 接口层（API 接口、消息接口等）
├── config/            # 配置层（所有配置类）
├── common/                  # 通用层（工具类、异常、常量等）
└── startup/                 # 启动层（主类、启动配置等）
```

### 3.2 详细优化方案

#### 3.2.1 包结构调整

1. **启动层 (startup)**
   - 将 Application.java 移至 startup 包
   - 包含所有与应用启动相关的配置

2. **应用层 (app)**
   - 整合现有的 admin、captcha、module/*/app 等控制器和应用服务
   - 按业务领域组织，如 application/system/、application/auth/ 等

3. **领域层 (domain)**
   - 整合现有的 module/*/domain、admin/domain 等领域模型
   - 按业务领域组织，如 domain/system/、domain/auth/ 等

4. **基础设施层 (infra)**
   - 整合现有的 mybatis、module/*/infra 等数据访问实现
   - 包含数据库访问、外部服务调用等实现

5. **接口层 (intf)**
   - 整合现有的 module/*/interfaces、admin/controller 等接口定义
   - 包含 REST API、消息接口等

6. **配置层 (config)**
   - 整合现有的 autoconfig、config 等配置类
   - 按功能模块组织，如 configuration/security/、configuration/database/ 等

7. **通用层 (common)**
   - 简化现有的 common 模块
   - 仅保留真正通用的工具类、异常、常量等

#### 3.2.2 配置文件优化

1. **统一配置目录结构**
   ```
   resources/
   ├── application.yml          # 主配置文件
   ├── config/
   │   ├── application-dev.yml  # 开发环境配置
   │   ├── application-test.yml # 测试环境配置
   │   ├── application-prod.yml # 生产环境配置
   │   ├── database.yml         # 数据库配置
   │   ├── security.yml         # 安全配置
   │   ├── cache.yml            # 缓存配置
   │   └── logback-spring.xml   # 日志配置
   ├── i18n/
   ├── mapper/
   └── static/
   ```

2. **简化配置文件管理**
   - 减少配置文件层级
   - 使用 Spring Profiles 管理环境差异

#### 3.2.3 业务模块重组

1. **按业务领域重组**
   - 将现有的 module 目录按业务领域重新组织
   - 每个业务领域包含完整的分层结构

2. **示例结构**
   ```
   domain/
   ├── system/                 # 系统管理领域
   │   ├── model/              # 领域模型
   │   ├── service/            # 领域服务
   │   ├── repository/         # 仓储接口
   │   └── exception/          # 领域异常
   ├── auth/                   # 认证授权领域
   ├── org/                    # 组织管理领域
   ├── user/                   # 用户管理领域
   └── log/                    # 日志管理领域
   ```

### 3.3 迁移步骤

#### 第一阶段：基础结构调整
1. 创建新的包结构（app、domain、infra、intf、config、common、startup）
2. 将 Application.java 移至 startup 包
3. 重构配置文件目录结构

#### 第二阶段：模块迁移
1. 按业务领域迁移 module 目录内容
2. 整合 admin、captcha、security 等功能模块到新的分层结构
3. 简化 common 模块

#### 第三阶段：代码重构
1. 更新包引用
2. 调整组件扫描配置
3. 更新依赖关系

#### 第四阶段：测试验证
1. 单元测试验证
2. 集成测试验证
3. 功能测试验证

## 4. 预期收益

1. **提高代码可读性**：统一的架构风格使代码更易于理解
2. **降低维护成本**：清晰的模块职责减少维护复杂度
3. **增强可扩展性**：模块化设计便于添加新功能
4. **提升团队协作效率**：统一的规范减少沟通成本
5. **更好的测试性**：分层架构便于编写单元测试和集成测试

## 5. 风险与应对措施

### 5.1 风险识别
1. **迁移过程中的功能中断风险**
2. **包引用更新不完整导致的编译错误**
3. **配置文件调整导致的运行时错误**
4. **团队成员对新结构不熟悉的学习成本**

### 5.2 应对措施
1. **分阶段迁移**：采用渐进式迁移策略，降低风险
2. **充分测试**：每个阶段完成后进行充分测试
3. **文档更新**：及时更新相关文档和开发指南
4. **团队培训**：组织培训帮助团队成员熟悉新结构

## 6. 实施建议

1. **成立专项小组**：由架构师牵头，核心开发人员参与
2. **制定详细计划**：明确各阶段目标和时间节点
3. **建立代码规范**：制定新的代码规范和提交要求
4. **持续集成保障**：确保 CI/CD 流程适应新的项目结构
5. **文档同步更新**：及时更新项目文档和开发指南

## 7. 总结

本次优化方案旨在解决当前项目结构中存在的问题，通过统一架构风格、明确模块职责、简化配置管理等方式，提升项目的可维护性、可扩展性和团队协作效率。建议按照分阶段的方式逐步实施，确保项目稳定过渡到新的架构。