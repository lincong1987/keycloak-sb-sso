<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			在线人数：{{this.formData.count}}

			<iframe src="www.baidu.com" ></iframe>

		</div>
		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>
	</div>
</template>


<script>

	import Page from "@/util/Page"

	export default {
		name: 'add',
		mixins: [
			Page
		],
		// 接收父组件的传参
		props: {
			param: {
				type: Object,
				require: false
			},
			parentPage: {
				type: Object,
				default: null
			}
		},
		// 组件
		components: {
			// 'component-a': ComponentA,

		},
		// 创建方法
		created() {
			// 记录原来的默认值，用于表单重置
		},
		// 初始化方法
		mounted() {

			// Map demo
			// let mapData = new Map();
			// mapData.set('key1', 'value1')
			// mapData.set('key2', 'value2')
			// mapData.set('key1', 'value3')
			// let aaa = this.formatMap(mapData);

			// 执行界面初始化
			this.init(this.param);

		},
		data() {
			return {
				// 行政区划树加载数据
				cityData: [],
				// 表单数据
				formData: {
					id: '',
					name: '',
					count: 0,
				},
			}
		},

		// 方法
		methods: {
			/**
			 * 显示窗口
			 * param 参数
			 * title 标题
			 */
			init(param) {



				//app.$logger.online(this.callback)
			},

			callback(count) {
				this.formData.count = count;
			},
			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			//设置cookie
			setCookie(cname, cvalue, exdays) {


			}
		}
	}
</script>

<style lang="less" scoped>

</style>
