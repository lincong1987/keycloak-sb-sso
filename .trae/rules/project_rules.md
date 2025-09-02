# 数据库相关
如果你需要进行数据库连接，可以使用以下信息：
- **数据库地址**: alilaoba.cn:13307
- **数据库名称**: ps-bmp
- **用户名**: root
- **密码**: W4HV=QxtHz
- **驱动**: MariaDB/MySQL
- mysql 命令不要使用 < `The '<' operator is reserved for future use.`

# 后端
端口为`8082`
context-path 是 `/ps-be`
如果你需要创建一些测试用的接口，mappping 请使用 `test_` 开头，如果 `test_user_is_enable`
`JsonResponse` 类的正确包路径是`com.jiuxi.common.bean.JsonResponse`
JSON返回都是使用 `JsonResponse`，可以使用 `buildSuccess` 和 `buildFailure` 方法，示例如下：
``` java

// JsonResponse 示例
    /**
     * 消息发送
     *
     * @param tpMessage
     * @param jwtpid    当前登录人id
     * @return com.jiuxi.common.bean.JsonResponse
     */
    @RequestMapping("/send")
    public JsonResponse send(@Validated(value = AddGroup.class) @RequestBody TpMessageVO tpMessage, String jwtpid) {

        // 创建人id
        tpMessage.setCreator(jwtpid);

        boolean bool = tpMessageService.send(tpMessage);

        if (bool) {
            return JsonResponse.buildSuccess();
        } else {
            return JsonResponse.buildFailure("消息发送失败");
        }
    }

```

分页使用 `Ipage`，路径为 `import com.baomidou.mybatisplus.core.metadata.IPage;`
分页示例：
``` java
/** 
* 查询代理商列表
*
* @param query 查询条件
* @param jwtpid jwt的pid
* @return JsonResponse 包含查询结果的Json响应对象
*/
    @RequestMapping("/list")
    public JsonResponse list(TpAgentQuery query, String jwtpid) {
        IPage<TpAgentVO> page = tpAgentService.queryPage(query);
        return JsonResponse.buildSuccess(page);
    }
```
spring boot 的配置文件为 `application-dev.yml`，请不要使用 `application.yml`，否则会报错, 位置在 `D:\projects\ps-bmp\keycloak-sb-sso\ps-be\src\main\resources\config\environments\dev\application-dev.yml`

# 前端
端口为10801
本系统的样式库为LESS
本系统使用的是自研前端框架， 为`fb-ui`，位置为 `D:\projects\ps-bmp\keycloak-sb-sso\ps-fe\src\fb-ui`
典型页面组件及布局参考：
- 列表页 `D:\projects\ps-bmp\keycloak-sb-sso\ps-fe\@fb\admin-base\views\sys\tag\list.vue`
- 详情页 `D:\projects\ps-bmp\keycloak-sb-sso\ps-fe\@fb\admin-base\views\sys\tag\view.vue`
- 新增/编辑页 `D:\projects\ps-bmp\keycloak-sb-sso\ps-fe\@fb\admin-base\views\sys\tag\add.vue`
常用组件易错点：
`fb-button` 组件的点击事件名是 `@on-click`
表格组件是 `fb-simple-table`


# keycloak 
服务地址为 `http://localhost:8180`
realm 为 `ps-realm`
客户端ID 为 `ps-be`
admin账号为 `admin`
admin密码为 `admin123`