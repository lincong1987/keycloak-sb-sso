<!-- 
此页面用于新增或编辑 keycloak 客户端信息 
此页面通过ps-be后台接口 `/sys/sso/admin/client/get` 获取 单个 keycloak client 信息
本页面的表单项的定义需要与 keycloak 一致
点击保存后，会调用 ps-be 后台接口 `/sys/sso/admin/client/save` 保存，然后同步至 keycloak

总体要求，要求提供便捷的应用接入配置

-->