// 创建应用service
export default {
	user: {
        status(){
            return app.service.get('/sso/user/status')
        },
        clients(){
            return app.service.get('/sso/user/clients')
        },
        roles(){
            return app.service.get('/sso/user/roles')
        },
        resources(){
            return app.service.get('/sso/user/resources')
        },
        logout(){
            return app.service.post('/sso/user/logout')
        }
    },
	admin: {
		session: {
			// 获取keycloak的全局会话列表
			list(params) {
				return app.service.get('/sso/admin/session/list', params)
			},
			// 强制注销keycloak的全局会话
			logout(sessionId) {
				return app.service.post('/sso/admin/session/logout', { sessionId })
			},
			// 批量强制注销会话
			batchLogout(sessionIds) {
				return app.service.post('/sso/admin/session/batch-logout', { sessionIds })
			}
		}
	}
}