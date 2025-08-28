# 欢迎使用 topinfo-platform-log-starter-autoconfigurer
## 一、简介

日志组件服务设计范围：
- 1、记录用户操作的业务动作及环境。如模块、时间、动作、浏览器版本、IP地址。
- 2、提供相关的统计分析，如在线人数、累计人数等

不记录用户操作业务数据的变更情况，相关数据执行脚本是通过数据库的日志审计功能进行记录（在表中通过创建人、创建时间、修改人、修改时间）。

在进行日志跟踪排查的时候，可以通过日志服务器中记录的操作人、模块编码，操作时间、操作内容等，配合日志审计功能，追踪到每个操作语句的操作数据变更情况。
由此可以完整的还原当时的场景：  
- 时间： 操作时间  
- 地点： 浏览器+IP   
- 人物： 创建人  
- 动作： 模块+操作动作  
- 数据： 数据变更情况  

动作取值范围:
```javascript

// 日志，操作类型
operterType: [
    {value: 'login', label: '登陆'},
    {value: 'logout', label: '登出'},
    {value: 'add', label: '新增'},
    {value: 'delete', label: '删除'},
    {value: 'edit', label: '修改'},
    {value: 'query', label: '查询'},
    {value: 'pass', label: '通过'},
    {value: 'unpass', label: '未通过'}
]

```

日志组件服务是一个可独立部署的，存储支撑Mysql和TDengine两种数据库，在区县等小用户量的场景，直接用Mysql，与业务系统同一个数据库即可；
在省厅等大用户量的场景中，采用TDengine数据库，且独立部署的模式。

引入依赖，建议独立部署一个war，不要与业务系统war进行耦合：  

```xml
    <!--  log 组件  -->
    <dependency>
       <groupId>com.topinfo.basic.platform</groupId>
       <artifactId>topinfo-platform-log-starter</artifactId>
       <version>2.0.0-SNAPSHOT</version>
    </dependency>
```

> 前置条件，需要有以下组件（如果有admin组件，则不需要再次依赖）：
> ```xml
>
>    <!-- 验证码 -->
>    <dependency>
>        <groupId>com.topinfo.basic.platform</groupId>
>        <artifactId>topinfo-platform-captcha-starter</artifactId>
>        <version>1.1.1-SNAPSHOT</version>
>    </dependency>
> 
>    <!-- 文件服务器客户端 剥离阿里云 -->
>    <dependency>
>        <groupId>com.topinfo.basic.platform</groupId>
>        <artifactId>topinfo-platform-jdfs-client-starter</artifactId>
>        <scope>provided</scope>
>        <version>1.1.0-SNAPSHOT</version>
>    </dependency>
>
>```

TDengine建表脚本：  
```sql
-- 创建超级表 tp_operate_log
create table if not exists tp_log(ts timestamp, module_code nchar(30), operter_type nchar(20),operter_rid nchar(100),ascn_id nchar(50),city_code nchar(30),operter_browser nchar(500),operter_ip nchar(100),extend01 nchar(500),extend02 nchar(500),extend03 nchar(500),) TAGS (username nchar(30), category tinyint);
 
```

Mysql 建表脚本：
```sql
-- Mysql 建表脚本
CREATE TABLE `tp_operate_log` (
    `LOG_ID` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
    `PERSON_ID` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '操作人员id',
    `MODULE_CODE` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '模块code',
    `OPERTER_TIME` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '操作时间',
    `OPERTER_TYPE` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '操作类型',
    `OPERTER_RID` varchar(19) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作记录ID、修改、删除时，记录ID',
    `OPERTER_IP` varchar(900) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人IP',
    `OPERTER_BROWSER` varchar(900) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人浏览器',
    `USERNAME` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账号',
    `ASCN_ID` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '单位ID',
    `CATEGORY` int(1) NOT NULL COMMENT '人员类别：政府人员or企业人员or其他',
    `CITY_CODE` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '行政区划代码',
    `ACTIVED` int(1) NOT NULL COMMENT '有效标记',
    `CREATOR` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
    `CREATE_TIME` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '创建时间',
    `UPDATOR` varchar(19) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
    `UPDATE_TIME` varchar(14) COLLATE utf8mb4_bin NOT NULL COMMENT '修改时间',
    `EXTEND01` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
    `EXTEND02` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
    `EXTEND03` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`LOG_ID`),
    KEY `idx_time_mcode_tccu` (`OPERTER_TIME`,`MODULE_CODE`,`OPERTER_TYPE`,`CATEGORY`,`CITY_CODE`,`USERNAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='操作日志表';
```
 

## 配置

```yaml


topinfo:
  ##########################################################################
  #############  logger 配置模板
  ##########################################################################
  logger:
    # 日志数据库类型: 如果不配置，则默认Mysql，如果数据库是TDengine，则配置为：TD 
    #dbtype: TD
    # 设置开启日志记录的模块
    customizeModuleMap:
      "menu" : "测试模块"



  ##########################################################################
  #############  topinfo-platform-mybatis-starter-autoconfigurer
  #############  配置模板
  ##########################################################################
  mybatis:
    ## 数据中心（可选）
    datacenterId: 0
    ## 机器（可选）
    machineId: 0
    ## 数据源配置
    datasource-config:
      ## mysql连接配置
      ## useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
      # url: jdbc:mysql://127.0.0.1:3306/topinfo?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
      url: jdbc:mariadb://192.168.0.76:3306/tp_admin_dev?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
      ## 用户名
      username: root
      ## 密码
      password: topinfo
      ## 最新连接数（可选）， 默认：5
      minIdle: 5
      ## 最大连接数（可选）， 默认：500
      maxActive: 500
      ## 校验连接sql（可选）, 默认： SELECT 1
      validationQuery: SELECT 1
      
      ## TDengine连接配置
      #driver-class: com.taosdata.jdbc.rs.RestfulDriver
      ### jdbc:TAOS-RS://192.168.0.76:6041/topinfo?timezone=Asia/Beijing&charset=utf-8
      ### jdbc:TAOS://taosdemo.com:6030/test?user=root&password=taosdata
      #url: jdbc:TAOS-RS://192.168.0.50:6041/topinfo?timezone=Asia/Beijing&charset=utf-8
      ### 用户名
      #username: root
      ### 密码
      #password: taosdata
      ### 最新连接数（可选）， 默认：5
      #minIdle: 5
      ### 最大连接数（可选）， 默认：500
      #maxActive: 300
      ### 校验连接sql（可选）, 默认： select server_status()
      #validationQuery: select server_status();
```

> 注意：  
> 文件上传留痕，需要引入文件服务器组件，并需要在业务代码中调用 tpFileLogService.collection 方法。  

## 前端修改

### 替换 logger-sdk.js和 修改constant.js文件

在前端项目util目录中，替换 logger-sdk.js 和 修改constant.js文件

logger-sdk.js :

```javascript

/*!
 * logger-sdk.js
 * (c) 2021 杨攀
 */

import projectConfig from '../config/project'

/**
 * 日志服务
 * @param opts
 *
 *
 let data = {
	// 模块编码
	moduleCode: 'xxx',
	// 操作类型： login/logout/add/delete/edit/query/pass/unpass, 可以自己定义
	operterType: 'dateTest',
	// 操作记录ID、修改、删除时，记录ID, 新增时可以为空
	operterRid: xxxxxxxxxxxxxxxxxx,
	// extend01: 扩展信息，可选，长度不能超过100
}

 // 日志埋点
 this.$logger.send(data);


 */

export class WebLogger {

    constructor() {
        // 获取在线人数定时
        this.ivonline = "";
        // 当前项目名
        this.project = projectConfig.name
    }

    /**
     * 发送埋点数据
     * @param data
     */
    send(data) {

        if (!projectConfig.sysconfig.loggerSwitch) {
            // 如果开关没有开启，这里直接return，不发送请求
            return;
        }

        // 日志服务地址 baseURL
        this.baseURL = app.service.defaults.baseURL

        if (typeof data.moduleCode === 'undefined') {
            throw new Error('模块编码不能为空！')
        }

        if (typeof data.operterType == 'undefined') {
            throw new Error('操作类型不能为空！')
        }

        // 获取当前登录用户信息
        let userInfo = app.$datax.get('userInfo')
        // 用户名
        data.username = userInfo.userName;
        // 用户类型
        data.category = userInfo.category;

        let token = app.$datax.get('token')

        // 组装请求地址， 小于 10k
        let query = this.baseURL + '/platform/logger/collection?jt=' + token;

        for (let [key, value] of Object.entries(data)) {
            query += '&' + key + '=' + encodeURIComponent(value)
        }

        let img = new Image()
        img.onerror = img.onload = function () {
        }
        img.src = query
    }

    /**
     * 发送 登录 埋点数据
     * @param data
     */
    login(data) {

        if (!projectConfig.sysconfig.loggerSwitch) {
            // 如果开关没有开启，这里直接return，不发送请求
            return;
        }

        // 日志服务地址 baseURL
        this.baseURL = app.service.defaults.baseURL

        if (typeof data.moduleCode === 'undefined') {
            throw new Error('模块编码不能为空！')
        }

        if (typeof data.operterType == 'undefined') {
            throw new Error('操作类型不能为空！')
        }

        // 获取当前登录用户信息
        let userInfo = app.$datax.get('userInfo')
        // 用户名
        data.username = userInfo.userName;
        // 用户类型
        data.category = userInfo.category;

        let token = app.$datax.get('token')

        // 组装请求地址， 小于 10k
        let query = this.baseURL + '/platform/logger/login?jt=' + token;

        for (let [key, value] of Object.entries(data)) {
            query += '&' + key + '=' + encodeURIComponent(value)
        }

        let img = new Image()
        img.onerror = img.onload = function () {
        }
        img.src = query
    }


}

let install = WebLogger.install = (adapter) => {
    app.$logger = adapter.prototype.$logger = new WebLogger()
}

export default {
    WebLogger,
    install,
}

```

在constant.js中，添加 日志 的静态参数:

```javascript
const constant = {
    // 日志，模块编码
    moduleCode: [
        {value: 'login', label: '登陆'},
    ],
    // 日志，操作类型
    operterType: [
        {value: 'login', label: '登陆'},
        {value: 'logout', label: '登出'},
        {value: 'add', label: '新增'},
        {value: 'delete', label: '删除'},
        {value: 'edit', label: '修改'},
        {value: 'query', label: '查询'},
        {value: 'pass', label: '通过'},
        {value: 'unpass', label: '未通过'}
    ],
    // 日志，人员类别
    category: [
        {value: '0', label: '政府'},
        {value: '1', label: '企业'},
        {value: '2', label: '中介'},
        {value: '3', label: '其它'},
    ]


}

let install = constant.install = (adapter) => {
    app.$constant = adapter.prototype.$constant = constant
}

export default {
    constant,
    install
}


```

### 新增日志查看模块

日志模块:  
1、 界面: sys/logger/list.vue  
2、 路由替换

### 修改原来的日志埋点地方

替换其他埋点地方：  
 - src/components/login/system-scancode-dd.vue  
 - src/views/main/login/ScreenLogin.vue
 - src/views/main/login/SystemApplicationLogin2.vue
 - src/views/main/components/AdminHeader.vue  
 - src/index.js



