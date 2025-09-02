/*!
* sso router
* (c) 2024
*/

var index = {}

export default [

	{
		path: '/sys/sso/session',
		meta: {
			title: '全局会话管理',
		},
		component: () => import('../../../views/sys/sso/session.vue'),
	}
]