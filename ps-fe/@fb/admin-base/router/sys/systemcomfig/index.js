/*!
* sys router
* (c) 2024
*/

export default [
	{
		path: '/sys/systemconfig/list',
		meta: {
			title: '系统配置管理',
		},
		component: () => import('@fb/admin-base/views/sys/systemconfig/list.vue'),
	},
 
 
]