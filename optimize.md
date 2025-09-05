基于对ps-be项目的深入扫描和分析，我为您提供以下全面的项目目录结构优化和重构方案：

## PS-BE 项目目录结构优化和重构方案

### 一、项目现状分析

#### 1.1 项目基本信息
- **框架版本**: Spring Boot 2.7.18
- **构建工具**: Maven
- **数据库**: MariaDB
- **ORM框架**: MyBatis Plus 3.5.3.1
- **项目性质**: 基础管理平台后端（PS BMP Backend）

#### 1.2 当前目录结构
```
ps-be/
├── pom.xml
├── Dockerfile
├── DEPLOYMENT_GUIDE.md
├── 项目目录结构优化方案.md
├── nginx/
├── sql/                         # 数据库脚本（位置不规范）
├── src/main/
│   ├── java/com/jiuxi/
│   │   ├── Application.java
│   │   ├── admin/               # 管理模块
│   │   ├── captcha/             # 验证码模块
│   │   ├── common/              # 通用组件
│   │   ├── config/              # 配置类
│   │   ├── core/                # 核心功能
│   │   ├── mvc/                 # MVC配置
│   │   ├── mybatis/             # 数据访问
│   │   └── security/            # 安全模块
│   └── resources/
│       ├── config/
│       ├── mapper/
│       ├── i18n/
│       └── templates/
└── uploads/                     # 上传文件（不应在项目目录）
```

#### 1.3 主要问题识别

**架构层面问题：**
1. **模块边界不清晰**：功能混合在不同包中，缺乏清晰的模块划分
2. **包结构混乱**：存在 `com.jiuxi` 和 `com.ps` 两套包结构
3. **职责分离不明确**：Controller、Service、Mapper混合存放
4. **领域驱动设计不足**：缺乏明确的领域边界和聚合根

**技术层面问题：**
1. **配置管理分散**：配置文件分布在多个位置，缺乏统一管理
2. **资源文件杂乱**：Mapper、静态资源、配置混合存放
3. **数据库脚本管理混乱**：SQL文件分散，版本管理不规范
4. **测试结构缺失**：缺少完整的测试目录结构

**运维层面问题：**
1. **环境配置不分离**：开发、测试、生产环境配置混合
2. **文档不完善**：缺少API文档、开发指南等
3. **部署脚本分散**：Docker、Nginx配置位置不规范

### 二、重构目标和原则

#### 2.1 重构目标
1. **清晰的模块边界**：实现域驱动设计，明确业务边界
2. **标准化目录结构**：遵循Spring Boot最佳实践
3. **环境配置分离**：支持多环境部署
4. **完善的测试体系**：提供完整的测试框架
5. **规范化文档管理**：建立完善的文档体系

#### 2.2 设计原则
1. **单一职责原则**：每个模块只负责特定业务领域
2. **开闭原则**：便于扩展，不修改现有代码
3. **依赖倒置原则**：面向接口编程，降低耦合
4. **领域驱动设计**：以业务领域为核心组织代码

### 三、优化后的目录结构

#### 3.1 根目录结构
```
ps-be/
├── README.md                    # 项目说明
├── CHANGELOG.md                 # 版本变更记录
├── pom.xml                      # Maven配置
├── Dockerfile                   # Docker构建文件
├── docker-compose.yml           # Docker编排文件
├── .gitignore                   # 版本控制忽略
├── docs/                        # 项目文档
│   ├── api/                     # API文档
│   │   ├── openapi.yaml
│   │   └── postman_collection.json
│   ├── deployment/              # 部署文档
│   │   ├── docker-deployment.md
│   │   ├── k8s-deployment.md
│   │   └── environment-setup.md
│   ├── development/             # 开发文档
│   │   ├── coding-standards.md
│   │   ├── git-workflow.md
│   │   └── testing-guide.md
│   └── architecture/            # 架构文档
│       ├── system-architecture.md
│       ├── database-design.md
│       └── security-design.md
├── scripts/                     # 构建和部署脚本
│   ├── build/
│   │   ├── build.sh
│   │   └── package.sh
│   ├── deploy/
│   │   ├── deploy-dev.sh
│   │   ├── deploy-test.sh
│   │   └── deploy-prod.sh
│   └── database/
│       ├── init-schema.sql
│       └── backup.sh
├── config/                      # 外部配置文件
│   ├── nginx/
│   │   ├── nginx.conf
│   │   └── default.conf
│   ├── docker/
│   │   └── docker-compose.override.yml
│   └── k8s/
│       ├── deployment.yaml
│       └── service.yaml
└── database/                    # 数据库相关
    ├── migrations/              # 数据库迁移脚本
    │   ├── V1.0.0__Initial_Schema.sql
    │   ├── V1.0.1__Add_User_Tables.sql
    │   └── V1.1.0__Add_Security_Features.sql
    ├── seeds/                   # 初始化数据
    │   ├── dev/
    │   ├── test/
    │   └── prod/
    └── backup/                  # 数据备份脚本
```

#### 3.2 Java源码结构
```
src/main/java/com/jiuxi/
├── Application.java             # 主启动类
├── config/                      # 全局配置
│   ├── DatabaseConfig.java
│   ├── SecurityConfig.java
│   ├── RedisConfig.java
│   ├── WebMvcConfig.java
│   ├── SwaggerConfig.java
│   └── AsyncConfig.java
├── common/                      # 通用组件
│   ├── annotation/              # 自定义注解
│   │   ├── Authorization.java
│   │   ├── DataPermission.java
│   │   └── OperationLog.java
│   ├── aspect/                  # 切面类
│   │   ├── AuthorizationAspect.java
│   │   ├── DataPermissionAspect.java
│   │   └── OperationLogAspect.java
│   ├── constant/                # 常量定义
│   │   ├── SystemConstants.java
│   │   ├── ErrorCodes.java
│   │   └── CacheKeys.java
│   ├── enums/                   # 枚举类
│   │   ├── UserStatus.java
│   │   ├── OperationType.java
│   │   └── ResponseCode.java
│   ├── exception/               # 异常处理
│   │   ├── GlobalExceptionHandler.java
│   │   ├── BusinessException.java
│   │   └── ValidateException.java
│   ├── utils/                   # 工具类
│   │   ├── DateUtils.java
│   │   ├── StringUtils.java
│   │   ├── EncryptUtils.java
│   │   └── JsonUtils.java
│   ├── validator/               # 验证器
│   │   ├── PhoneValidator.java
│   │   ├── EmailValidator.java
│   │   └── IdCardValidator.java
│   └── bean/                    # 通用实体
│       ├── JsonResponse.java
│       ├── PageQuery.java
│       ├── TreeNode.java
│       └── SessionVO.java
├── infrastructure/              # 基础设施层
│   ├── persistence/             # 持久化
│   │   ├── config/
│   │   │   ├── MyBatisConfig.java
│   │   │   └── DataSourceConfig.java
│   │   ├── mapper/              # 基础Mapper
│   │   │   └── BaseMapper.java
│   │   └── entity/              # 基础实体
│   │       └── BaseEntity.java
│   ├── cache/                   # 缓存实现
│   │   ├── config/
│   │   │   └── CacheConfig.java
│   │   ├── service/
│   │   │   ├── RedisService.java
│   │   │   └── EhCacheService.java
│   │   └── annotation/
│   │       └── Cacheable.java
│   ├── messaging/               # 消息队列
│   │   ├── config/
│   │   ├── producer/
│   │   └── consumer/
│   ├── external/                # 外部服务
│   │   ├── sms/
│   │   ├── email/
│   │   └── storage/
│   └── security/                # 安全基础设施
│       ├── authentication/
│       ├── authorization/
│       └── encryption/
├── module/                      # 业务模块
│   ├── user/                    # 用户管理模块
│   │   ├── domain/              # 领域层
│   │   │   ├── entity/          # 领域实体
│   │   │   │   ├── User.java
│   │   │   │   ├── Account.java
│   │   │   │   └── UserProfile.java
│   │   │   ├── repository/      # 仓储接口
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── AccountRepository.java
│   │   │   ├── service/         # 领域服务
│   │   │   │   ├── UserDomainService.java
│   │   │   │   └── UserValidationService.java
│   │   │   └── event/           # 领域事件
│   │   │       ├── UserCreatedEvent.java
│   │   │       └── UserDeletedEvent.java
│   │   ├── application/         # 应用层
│   │   │   ├── service/         # 应用服务
│   │   │   │   ├── UserApplicationService.java
│   │   │   │   └── UserQueryService.java
│   │   │   ├── dto/             # 数据传输对象
│   │   │   │   ├── UserCreateDTO.java
│   │   │   │   ├── UserUpdateDTO.java
│   │   │   │   └── UserQueryDTO.java
│   │   │   └── assembler/       # 装配器
│   │   │       └── UserAssembler.java
│   │   ├── infrastructure/      # 基础设施层
│   │   │   ├── persistence/     # 持久化实现
│   │   │   │   ├── mapper/
│   │   │   │   │   ├── UserMapper.java
│   │   │   │   │   └── AccountMapper.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── UserPO.java
│   │   │   │   │   └── AccountPO.java
│   │   │   │   └── repository/
│   │   │   │       ├── UserRepositoryImpl.java
│   │   │   │       └── AccountRepositoryImpl.java
│   │   │   └── external/        # 外部服务适配
│   │   │       └── KeycloakUserSync.java
│   │   └── interface/           # 接口层
│   │       ├── web/             # Web接口
│   │       │   ├── UserController.java
│   │       │   └── UserQueryController.java
│   │       ├── api/             # API接口
│   │       │   └── UserApiController.java
│   │       └── facade/          # 外观接口
│   │           └── UserFacade.java
│   ├── organization/            # 组织架构模块
│   │   ├── domain/
│   │   │   ├── entity/
│   │   │   │   ├── Department.java
│   │   │   │   ├── City.java
│   │   │   │   └── Organization.java
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── event/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   └── interface/
│   ├── authorization/           # 权限管理模块
│   │   ├── domain/
│   │   │   ├── entity/
│   │   │   │   ├── Role.java
│   │   │   │   ├── Permission.java
│   │   │   │   └── Menu.java
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── event/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   └── interface/
│   ├── system/                  # 系统管理模块
│   │   ├── domain/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   └── interface/
│   ├── log/                     # 日志管理模块
│   │   ├── domain/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   └── interface/
│   └── file/                    # 文件管理模块
│       ├── domain/
│       ├── application/
│       ├── infrastructure/
│       └── interface/
└── shared/                      # 共享组件
    ├── domain/                  # 共享领域
    │   ├── event/               # 领域事件
    │   └── value/               # 值对象
    ├── application/             # 共享应用
    │   └── event/               # 应用事件
    └── infrastructure/          # 共享基础设施
        ├── event/               # 事件基础设施
        └── integration/         # 集成服务
```

#### 3.3 资源文件结构
```
src/main/resources/
├── application.yml              # 主配置文件
├── application-dev.yml          # 开发环境配置
├── application-test.yml         # 测试环境配置
├── application-prod.yml         # 生产环境配置
├── logback-spring.xml           # 日志配置
├── META-INF/
│   ├── spring.factories
│   └── additional-spring-configuration-metadata.json
├── config/                      # 配置文件目录
│   ├── cache/                   # 缓存配置
│   │   ├── ehcache.xml
│   │   └── redis.yml
│   ├── security/                # 安全配置
│   │   ├── security.yml
│   │   └── jwt.properties
│   ├── database/                # 数据库配置
│   │   ├── druid.yml
│   │   └── mybatis.yml
│   └── integration/             # 集成配置
│       ├── sms.yml
│       ├── email.yml
│       └── keycloak.yml
├── mapper/                      # MyBatis映射文件
│   ├── user/
│   │   ├── UserMapper.xml
│   │   └── AccountMapper.xml
│   ├── organization/
│   │   ├── DepartmentMapper.xml
│   │   └── CityMapper.xml
│   ├── authorization/
│   │   ├── RoleMapper.xml
│   │   ├── PermissionMapper.xml
│   │   └── MenuMapper.xml
│   ├── system/
│   │   ├── DictionaryMapper.xml
│   │   └── ConfigMapper.xml
│   └── log/
│       ├── OperateLogMapper.xml
│       └── LoginLogMapper.xml
├── static/                      # 静态资源
│   ├── css/
│   ├── js/
│   ├── images/
│   └── templates/               # 文档模板
│       ├── export/
│       └── report/
├── templates/                   # 模板文件
│   ├── email/
│   └── sms/
└── i18n/                        # 国际化资源
    ├── messages.properties
    ├── messages_en.properties
    ├── messages_zh_CN.properties
    └── validation.properties
```

#### 3.4 测试目录结构
```
src/test/java/com/jiuxi/
├── integration/                 # 集成测试
│   ├── user/
│   │   └── UserIntegrationTest.java
│   ├── organization/
│   └── authorization/
├── unit/                        # 单元测试
│   ├── module/
│   │   ├── user/
│   │   │   ├── domain/
│   │   │   │   └── UserDomainServiceTest.java
│   │   │   ├── application/
│   │   │   │   └── UserApplicationServiceTest.java
│   │   │   └── infrastructure/
│   │   │       └── UserRepositoryTest.java
│   │   ├── organization/
│   │   └── authorization/
│   └── common/
│       ├── utils/
│       └── validator/
├── performance/                 # 性能测试
│   ├── user/
│   └── system/
└── testcontainers/             # 容器化测试
    ├── DatabaseTestContainer.java
    └── RedisTestContainer.java

src/test/resources/
├── application-test.yml
├── logback-test.xml
├── test-data/
│   ├── user/
│   ├── organization/
│   └── authorization/
├── sql/
│   ├── test-schema.sql
│   └── test-data.sql
└── mock/
    ├── external-api/
    └── database/
```

### 四、分模块重构计划

#### 4.1 第一阶段：基础重构（2-3周）

**1. 包结构标准化**
- [ ] 统一包名为 `com.jiuxi`
- [ ] 移除 `com.ps` 包
- [ ] 更新所有import语句
- [ ] 修改配置文件中的包引用

**2. 配置文件整理**
- [ ] 按环境拆分配置文件
- [ ] 外置敏感配置
- [ ] 统一配置文件位置
- [ ] 优化日志配置

**3. 基础目录调整**
- [ ] 创建标准目录结构
- [ ] 移动现有文件到新位置
- [ ] 更新文件引用路径
- [ ] 清理无用文件

#### 4.2 第二阶段：领域建模（3-4周）

**1. 用户管理领域**
- [ ] 定义用户聚合根
- [ ] 实现用户仓储接口
- [ ] 创建用户领域服务
- [ ] 建立用户应用服务

**当前用户模块分析：**
```
现有Controller:
- TpPersonBasicinfoController (用户基本信息)
- TpAccountController (用户账户)

现有Service:
- TpPersonBasicinfoService (用户基本信息服务)
- TpAccountService (账户服务)
- PersonAccountService (人员账户关联服务)

现有Mapper:
- TpPersonBasicinfoMapper
- TpAccountMapper
```

**重构目标：**
```
用户聚合根：User
- 用户基本信息
- 账户信息
- 权限信息
- 状态管理

值对象：
- UserProfile (用户档案)
- ContactInfo (联系信息)
- LoginCredentials (登录凭据)
```

**2. 组织架构领域**
- [ ] 定义组织聚合根
- [ ] 实现组织层级管理
- [ ] 创建部门管理服务
- [ ] 建立行政区划服务

**3. 权限管理领域**
- [ ] 定义权限聚合根
- [ ] 实现角色权限模型
- [ ] 创建菜单管理服务
- [ ] 建立数据权限服务

**4. 系统管理领域**
- [ ] 定义系统配置聚合
- [ ] 实现字典管理
- [ ] 创建参数配置服务
- [ ] 建立日志管理服务

#### 4.3 第三阶段：接口标准化（2-3周）

**1. RESTful API设计**
- [ ] 统一API路径规范
- [ ] 标准化HTTP状态码
- [ ] 规范化响应格式
- [ ] 添加API版本控制

**2. 异常处理优化**
- [ ] 建立全局异常处理
- [ ] 定义业务异常类型
- [ ] 统一错误码管理
- [ ] 完善错误信息国际化

**3. 参数验证增强**
- [ ] 使用Bean Validation
- [ ] 自定义验证注解
- [ ] 分组验证策略
- [ ] 验证错误信息优化

#### 4.4 第四阶段：基础设施完善（2-3周）

**1. 数据访问层优化**
- [ ] 统一BaseMapper设计
- [ ] 实现通用CRUD操作
- [ ] 优化SQL映射文件
- [ ] 添加数据库版本管理

**2. 缓存策略实现**
- [ ] 多级缓存设计
- [ ] 缓存一致性保证
- [ ] 缓存穿透防护
- [ ] 缓存监控和管理

**3. 安全机制增强**
- [ ] JWT令牌管理
- [ ] 权限验证优化
- [ ] 数据权限控制
- [ ] 安全审计日志

**4. 集成服务构建**
- [ ] Keycloak集成优化
- [ ] 短信邮件服务
- [ ] 文件存储服务
- [ ] 消息队列集成

#### 4.5 第五阶段：质量保证（2-3周）

**1. 测试体系建设**
- [ ] 单元测试覆盖
- [ ] 集成测试设计
- [ ] 性能测试实施
- [ ] 端到端测试

**2. 代码质量提升**
- [ ] 代码规范检查
- [ ] 静态代码分析
- [ ] 代码重复检测
- [ ] 安全漏洞扫描

**3. 文档体系完善**
- [ ] API文档生成
- [ ] 开发指南编写
- [ ] 部署文档更新
- [ ] 架构设计文档

**4. CI/CD流水线**
- [ ] 自动构建配置
- [ ] 自动测试集成
- [ ] 自动部署流程
- [ ] 监控告警设置

### 五、重构效益分析

#### 5.1 开发效率提升
1. **模块化开发**：清晰的模块边界，支持并行开发
2. **代码复用**：通用组件和基础设施复用
3. **快速定位**：标准化目录结构，快速找到相关代码
4. **自动化工具**：减少重复性工作，提高开发效率

#### 5.2 维护成本降低
1. **统一规范**：降低代码理解和维护成本
2. **清晰架构**：便于问题定位和修复
3. **完善测试**：减少线上问题发生
4. **文档齐全**：降低知识传递成本

#### 5.3 系统可扩展性
1. **微服务就绪**：模块化设计便于拆分
2. **插件化架构**：支持功能扩展
3. **标准接口**：便于第三方集成
4. **云原生支持**：支持容器化部署

#### 5.4 团队协作改善
1. **明确职责**：清晰的模块负责人制度
2. **减少冲突**：标准化的开发流程
3. **知识共享**：完善的文档和培训体系
4. **质量保证**：自动化的质量检查流程

### 六、风险控制和实施建议

#### 6.1 主要风险
1. **重构风险**：大规模重构可能影响现有功能
2. **时间成本**：重构需要较长周期
3. **团队适应**：需要团队学习新的架构和规范
4. **业务中断**：重构期间可能影响业务开发

#### 6.2 风险控制措施
1. **分阶段实施**：逐步重构，降低风险
2. **充分测试**：确保重构不破坏现有功能
3. **并行开发**：重构和业务开发并行进行
4. **回滚机制**：保留回滚方案

#### 6.3 实施建议
1. **团队培训**：提前进行架构和技术培训
2. **文档先行**：完善设计文档和开发指南
3. **工具支持**：提供重构和开发工具
4. **持续集成**：建立自动化测试和部署流程

### 七、总结

这个重构方案基于领域驱动设计（DDD）原则，将现有的ps-be项目转换为更加模块化、可维护和可扩展的架构。通过分阶段实施，可以在控制风险的同时，逐步实现项目架构的现代化升级。

**重构核心价值：**
1. **业务导向**：以业务领域为核心组织代码
2. **技术先进**：采用现代软件架构最佳实践
3. **团队协作**：提供清晰的开发规范和流程
4. **持续改进**：建立可持续发展的技术架构

建议优先实施基础重构阶段，为后续的功能开发和架构升级奠定坚实基础。