<!-- 
此页面是列表页面，用于展示 keycloak `事件列表`（本项目内叫`应用日志列表`）
需要实现分页及查询
ps-be后台接口 `/sys/sso/admin/admin-event/list` ，service 在 `ps-fe\@fb\admin-base\service\sys\sso\index.js`， 没有的话需要创建

表格没有操作列
用户列 可以点击查看详情 ，点击后用 `tp-dialog#show` 打开查看页，并将row对象传过去
查看页位置页面 `ps-fe\@fb\admin-base\views\sys\sso\log\admin\view.vue`

-->