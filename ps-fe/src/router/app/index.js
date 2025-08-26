/*!
* app router
* (c) 2024
*/

var index = {}

export default [

	{
		path: '/app/list',
		meta: {
			title: '应用管理',
		},
		component: () => import('../../views/app/list.vue'),
	},
	{
		path: '/app/add',
		meta: {
			title: '新增应用',
		},
		component: () => import('../../views/app/add.vue'),
	},
	{
		path: '/app/edit/:id',
		meta: {
			title: '编辑应用',
		},
		component: () => import('../../views/app/add.vue'),
	},
	{
		path: '/app/view/:id',
		meta: {
			title: '查看应用',
		},
		component: () => import('../../views/app/view.vue'),
	}

]