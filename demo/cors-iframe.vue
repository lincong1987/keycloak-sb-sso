<template>
	<div>

		<!--		<iframe :src="$svc.sys.file.previewPDF({'attachId':'1367458439860584448'})" frameborder="0" style="width: 100%; height: 500px"></iframe>-->
		<iframe :src="srcUrl" frameborder="0" style="width: 100%; height: 500px"></iframe>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose" style="margin-right: 10px">关闭</fb-button>
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
			this.init();
			window.addEventListener('message', this.scanLogin);
		},
		beforeDestroy() {
			window.removeEventListener('message', this.scanLogin);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				// pdf地址
				// url: app.service.defaults.target + app.service.defaults.baseURL,
				srcUrl: "",
			}
		},

		// 方法
		methods: {
			init(param) {
				// this.srcUrl = "https://login.dg-work.cn/oauth2/auth.htm?response_type=code&client_id=gyqyaqzx_dingoa_dingoa&redirect_uri=http://topinfo.natapp4.cc/topinfo-admin/dingd/scan-login/qr-call-back&scope=get_user_info&authType=QRCODE&embedMode=true";

				return app.service.request({
					url: '/dingd/scan-login/qr',
					method: 'get', // 请求方式 post,get, 默认是 get,
					// `data` 是作为请求主体被发送的数据， post 采用
					params: {},
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					// `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
					responseType: 'json', // 默认的
					// `timeout` 指定请求超时的毫秒数(0 表示无超时时间)
					// 如果请求话费了超过 `timeout` 的时间，请求将被中断
					timeout: 5000,
					// 是否显示loading, 默认true,
					loading: false,
				}).then(response => {
					debugger
						this.srcUrl = response.data;
					},
					err => {
						console.log(err)
					})
			},


			scanLogin(event) {
				return app.service.request({
					url: '/dingd/scan-login/qr-call-back',
					method: 'get', // 请求方式 post,get, 默认是 get,
					// `data` 是作为请求主体被发送的数据， post 采用
					params: {"code": event.data.code},
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					// `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
					responseType: 'json', // 默认的
					// `timeout` 指定请求超时的毫秒数(0 表示无超时时间)
					// 如果请求话费了超过 `timeout` 的时间，请求将被中断
					timeout: 5000,
					// 是否显示loading, 默认true,
					loading: false,
				}).then(response => {
						debugger
						console.log(response)
					},
					err => {
						console.log(err)
					})
			},

			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},

		}
	}

</script>

<style lang="less" scoped>

</style>
