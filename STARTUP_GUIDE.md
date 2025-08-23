# PS 项目启动指南

本指南介绍如何使用提供的启动脚本来运行 PS-BMP 项目的前后端服务。

## 📁 项目结构

```
keycloak-sb-sso/
├── ps-be/                    # 后端服务 (Spring Boot)
│   ├── start-backend.ps1     # 后端启动脚本
│   └── pom.xml
├── ps-fe/                    # 前端服务 (Vue.js)
│   ├── start-frontend.ps1    # 前端启动脚本
│   └── package.json
├── start-all.ps1             # 交互式启动脚本
├── quick-start.ps1           # 快速启动脚本
└── STARTUP_GUIDE.md          # 本文档
```

## 🚀 启动方式

### 方式一：快速启动 (推荐)

适用于日常开发，一键启动前后端服务：

```powershell
# 在项目根目录执行
.\quick-start.ps1
```

**特点：**
- 自动启动后端服务 (后台运行)
- 等待后端启动完成后启动前端
- 自动安装前端依赖 (如果需要)
- 按 Ctrl+C 自动停止所有服务

### 方式二：交互式启动

提供多种启动选项：

```powershell
# 在项目根目录执行
.\start-all.ps1
```

**选项：**
1. 仅启动后端服务
2. 仅启动前端服务
3. 同时启动前后端服务
4. 退出

### 方式三：单独启动

#### 启动后端服务

```powershell
# 进入后端目录
cd ps-be

# 执行启动脚本
.\start-backend.ps1
```

#### 启动前端服务

```powershell
# 进入前端目录
cd ps-fe

# 执行启动脚本
.\start-frontend.ps1
```

## 🔧 环境要求

### 后端服务 (ps-be)
- Java 8 或更高版本
- Maven 3.6 或更高版本
- MariaDB 数据库 (配置在 application-dev.yml)

### 前端服务 (ps-fe)
- Node.js 14 或更高版本
- npm 6 或更高版本

## 🌐 服务地址

启动成功后，可以通过以下地址访问服务：

- **后端服务**: http://localhost:8088
- **前端服务**: 通常为 http://localhost:3000 (具体端口由 rsbuild 配置决定)

## ⚙️ 配置说明

### 数据库配置

后端服务使用以下环境变量进行数据库连接：

```
DB_USERNAME=root
DB_PASSWORD=123456
```

数据库连接地址配置在 `ps-be/src/main/resources/config/environments/dev/application-dev.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mariadb://alilaoba.cn:13307
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:your-password}
```

### 前端配置

前端服务使用 rsbuild 作为构建工具，配置文件：
- `rsbuild.config.js` - 主配置文件
- `.env.dev` - 开发环境变量

## 🛠️ 故障排除

### 常见问题

1. **Maven 命令未找到**
   - 确保 Maven 已安装并添加到 PATH 环境变量
   - 运行 `mvn --version` 验证安装

2. **Node.js 命令未找到**
   - 确保 Node.js 已安装并添加到 PATH 环境变量
   - 运行 `node --version` 验证安装

3. **数据库连接失败**
   - 检查数据库服务是否运行
   - 验证数据库连接信息和权限
   - 确认防火墙设置

4. **端口被占用**
   - 后端默认端口：8088
   - 前端端口由 rsbuild 自动分配
   - 使用 `netstat -ano | findstr :8088` 检查端口占用

5. **前端依赖安装失败**
   - 检查网络连接
   - 尝试使用国内镜像：`npm config set registry https://registry.npmmirror.com`
   - 清除缓存：`npm cache clean --force`

### 日志查看

- **后端日志**: 直接在控制台输出
- **前端日志**: 浏览器开发者工具控制台

## 📝 开发建议

1. **开发流程**
   - 使用 `quick-start.ps1` 进行日常开发
   - 修改代码后，前端会自动热重载
   - 后端代码修改需要重启服务

2. **调试技巧**
   - 后端 API 调试：使用 Postman 或浏览器开发者工具
   - 前端调试：使用浏览器开发者工具
   - 数据库调试：使用 DBeaver 或其他数据库客户端

3. **性能优化**
   - 开发环境下关闭不必要的日志输出
   - 使用浏览器缓存加速前端资源加载

## 🔄 更新日志

- **v1.0.0** (2024-01-XX)
  - 初始版本
  - 支持前后端独立启动
  - 支持一键快速启动
  - 支持交互式启动选择

---

如有问题，请检查控制台输出的错误信息，或参考项目文档。