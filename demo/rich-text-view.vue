<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">


				<fb-container align="center">
					<fb-text :color="colors.text_emphasize" size="xxl">
						{{ formData.title }}
					</fb-text>
				</fb-container>

				<fb-container align="center">
					<fb-text :color="colors.text_secondary">
						发布日期：{{ formData.date }}
					</fb-text>
					<fb-text style="margin-left: 30px" :color="colors.text_secondary">
						发布单位：{{ formData.dw }}
					</fb-text>
				</fb-container>

				<fb-divider/>

				<fb-container v-html="formData.fwb"/>

				<fb-container pl="15px" pt="100px">

					<fb-link-group :data="[{label: '附件下载'}]"></fb-link-group>

				</fb-container>

		</div>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>

</template>

<script>

	import Page from "@/util/Page"

	export default {
		name: 'demo-view',
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

				formData: {
					title: '通知公告标题',
					dw: '浙江图讯科技股份有限公司',
					date: '2021-04-15',
					fwb: "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 标题</p> <hr /> <p>&nbsp; 防静电搜破发78934u83092jf0dfds&lt;&gt;:}:}\"}[][]\\[][]【】【】【&hellip;&hellip;@&amp;*￥（）&amp;（）！）*#）！】</p> <p>&nbsp; <span style=\"text-decoration: underline;\">反倒是交际i90rf430f9jk0f、&lsquo;&rsquo;】{}{}{【】【、&lsquo;；&lsquo;&amp;&hellip;&hellip;*（）@（）￥@&rsquo;&rsquo;】}；</span></p> <p>&nbsp; <span style=\"text-decoration: line-through;\">非的搜集fsdfpokdsr9- 开放肉片：&ldquo;&rdquo;？{}；&lsquo;&rsquo;；&lsquo;&rsquo;】】【。@&amp;*($&amp;@】{}</span></p>",
				},

			}
		},

		// 方法
		methods: {
			init(param) {
				this.formData.id = param.id;
				this.view();
			},
			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			// 查看
			view() {
				// 调用新增service方法
				/*let that = this;
				this.service.view({"id": that.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						that.formData = result.data
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})*/

				/*this.service.expView({"id": that.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						if (result.data !== "") {
							that.expFormData = result.data
						}
					} else {
						// 服务器返回失败
						//console.log(result.data.message);
						this.$message.error('查询失败')
					}
				})*/

			},
		}
	}
</script>

<style lang="less" scoped>

</style>
