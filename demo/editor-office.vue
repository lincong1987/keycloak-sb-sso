<template>
	<div class="tp-dialog">

		<!--		<iframe :src="$svc.sys.file.previewPDF({'attachId':'1367458439860584448'})" frameborder="0" style="width: 100%; height: 500px"></iframe>-->
		<iframe :src="pdfUrl" frameborder="0" style="width: 100%; height: 92%"></iframe>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose" style="margin-right: 10px">关闭</fb-button>
			<fb-button @on-click="downFile">下载</fb-button>
		</div>
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
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				pdfUrl: "",
				// pdf地址
				// url: app.service.defaults.target + app.service.defaults.baseURL,
			}
		},

		// 方法
		methods: {
			init(param) {
				let attachName = "涉爆粉尘企业风险监测预警系统接入规范-tx20211027(1).docx";
				let personName = "张三";
				this.pdfUrl = "http://192.168.90.141:8081"+encodeURI(`/topinfo-office/platform/office/editor?attachId=1478658068727201792&attachName=${encodeURIComponent(attachName)}&personName=${encodeURIComponent(personName)}&thirdToken=111111`)
			},

			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},

			downFile() {

				return app.service.request({
					url: '/sys/file/download',
					method: 'get', // 请求方式 post,get, 默认是 get,
					// `data` 是作为请求主体被发送的数据， post 采用
					params: {'id': '1401840337965547520'},
					// `headers` 是即将被发送的自定义请求头
					headers: {'Content-Type': 'application/json'},
					// `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
					responseType: 'blob', // 返回服务器返回的数据类型为流
					// `timeout` 指定请求超时的毫秒数(0 表示无超时时间)
					// 如果请求话费了超过 `timeout` 的时间，请求将被中断
					timeout: 5000,
					// 是否显示loading, 默认true,
					loading: false,
				}).then(response => {
						app.download(response)
					},
					err => {
						console.log(err)
					})
			},

			view() {
				// 调用新增service方法
				this.service.viewPdf({}).then((result) => {
					// 判断code
					if (result.code == 1) {
						this.formData = result.data
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
