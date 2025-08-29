/*!
* systemRouter
* (c) 2020 lincong1987
*/

// 导入应用路由模块
import appRouter from './app/index.js'
 

let index = [

	
	// SSO回调路由
	{
		path: '/sso/login',
		meta: {
			title: 'SSO登录回调',
		},
		component: () => {
			
			return import('@/views/sso/login.vue')
		},
	},
		// 应用管理路由
	...appRouter,
	 
]

export default index
