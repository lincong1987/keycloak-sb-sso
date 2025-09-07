# 欢迎使用监控客户端组件 topinfo-plugin-monitor-client-starter
## 1. 使用步骤
### 1.1 引入依赖
```xml
        <dependency>
               <groupId>com.topinfo.basic.platform.plugin</groupId>
               <artifactId>topinfo-plugin-monitor-client-starter</artifactId>
               <version>1.0.0-SNAPSHOT</version>
        </dependency>
```
### 1.2 yml配置

```yaml
topinfo:
  platform:
    plugin:
      monitor:
        # 服务端地址
        server-url: http://localhost:8800/sys/monitor/heartbeat
        # 本机描述，如：金华海工控机
        system-desc: 金华海工控机
        # 排除的服务
        exclude-source:
          - redis
```

