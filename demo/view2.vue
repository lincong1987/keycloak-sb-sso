<template>
	<fb-property colon label-width="140px">
		<div :style="param.styles">
			<fb-fieldset label="基本信息"/>
			<fb-row>
				<fb-col span="12">
					<fb-row>
						<fb-col span="24">
							<fb-property-item label="名称">
								<fb-link-group>
									<fb-link :click="()=>handleClose()" :label="formData.name"></fb-link>
								</fb-link-group>
							</fb-property-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="24">
							<fb-property-item label="性别">
								{{formData.sexName}}
							</fb-property-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="24">
							<fb-property-item label="身份证">
								{{formData.idcard}}
							</fb-property-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="24">
							<fb-property-item label="统一社会信用代码">
								<fb-tooltip slot="label-extra" placement="top">
									<fb-container slot="content" width="80px">
										统一社会信用代码？
									</fb-container>
									<fb-icon name="information"/>
								</fb-tooltip>
								{{formData.entUnifiedCode}}
							</fb-property-item>
						</fb-col>
					</fb-row>
				</fb-col>
				<fb-col span="12">
					<fb-upload
						view="avatar"
						v-model="formData.profilePhoto"
						:service="$svc.sys.file"
						:file-list="fileList"
						readonly>
					</fb-upload>
				</fb-col>
			</fb-row>

			<fb-row>
				<fb-col span="12">
					<fb-property-item label="新框架开发">
						<fb-tooltip slot="label-extra" placement="top">
							<fb-container slot="content" width="160px">
								群是新框架开发的沟通群，后续有关框架的问题都在群里进行沟通，如果有什么建议或意见统统发群里吧，新框架刚刚出来，难免有各种各样的问题，大家多担待
							</fb-container>
							<fb-icon name="information"/>
						</fb-tooltip>

						asdasdasd
					</fb-property-item>
				</fb-col>
			</fb-row>

			<fb-row>
				<fb-col span="12">
					<fb-property-item label="类型">
						{{formData.typeName}}
					</fb-property-item>
				</fb-col>
			</fb-row>
			<fb-row>
				<fb-col span="12">
					<fb-property-item label="附件">
						<fb-upload
							:v-model="formData.files"
							:service="$svc.sys.file"
							:file-list="tabFileList"
							:accept="'.doc,.docx,.xlsx'"
							multiple
							readonly
						></fb-upload>
					</fb-property-item>
				</fb-col>
			</fb-row>

			<fb-fieldset label="基本信息"/>
			<fb-row>
				<fb-col span="12">
					<fb-property-item label="注册日期">
						{{formData.date}}
					</fb-property-item>
				</fb-col>
				<fb-col span="12">
					<fb-property-item label="注册资金">
						{{formData.regFund}} 万元
					</fb-property-item>
				</fb-col>
			</fb-row>

			<fb-row>
				<fb-col span="24">
					<fb-property-item label="注册地址" span="2">
						{{formData.addr}}
					</fb-property-item>
				</fb-col>
			</fb-row>

			<fb-row>
				<fb-col span="24">
					<fb-property-item label="富文本" span="2">
						<fb-container v-html="formData.fwb"/>
					</fb-property-item>
				</fb-col>
			</fb-row>

		</div>
		<div class="tp-dialog-bottom">
			<fb-button style="padding-right: 20px" @on-click="handleClose">关闭</fb-button>
			<fb-button @on-click="handleView">打开view2</fb-button>
		</div>
		<!--
                <tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
        -->
	</fb-property>

</template>

<script>
	import Page from "@/util/Page"

	export default {
		name: 'demo-view2',
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
		components: {},
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

				// 附件列表
				fileList: [],
				tabFileList: [],

				formData: {
					id: '',
					name: '',
					profilePhoto: '',
					files: '',
					sex: '',
					sexName: '',
					idcard: '',
					entUnifiedCode: '',
					type: '',
					typeName: '',
					date: '',
					regFund: '',
					email: '',
					linkPsnName: '',
					linkPsnTel: '',
					addrCode: '',
					addr: '',
					fwb: "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 标题</p> <hr /> <p>&nbsp; 防静电搜破发78934u83092jf0dfds&lt;&gt;:}:}\"}[][]\\[][]【】【】【&hellip;&hellip;@&amp;*￥（）&amp;（）！）*#）！】</p> <p>&nbsp; <span style=\"text-decoration: underline;\">反倒是交际i90rf430f9jk0f、&lsquo;&rsquo;】{}{}{【】【、&lsquo;；&lsquo;&amp;&hellip;&hellip;*（）@（）￥@&rsquo;&rsquo;】}；</span></p> <p>&nbsp; <span style=\"text-decoration: line-through;\">非的搜集fsdfpokdsr9- 开放肉片：&ldquo;&rdquo;？{}；&lsquo;&rsquo;；&lsquo;&rsquo;】】【。@&amp;*($&amp;@】{}</span></p>",
					enabled: 1,
					entDesc: '',
					list: []
				},

				expFormData: {
					imgs: '',
					cityId: '',
					prodAddress: '',
					industryTypeCode: '',
					lineCode: '',
					scaleType: '',
					enabled: '',
					imgs: '',
					list: []
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
				let that = this;
				this.service.view({"id": that.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						that.formData = result.data

						// 图片回显
						that.fileList = result.data.list;
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})

				// this.service.expView({"id": that.formData.id}).then((result) => {
				// 	// 判断code
				// 	if (result.code == 1) {
				// 		if (result.data !== "") {
				// 			that.expFormData = result.data
				// 			// 图片回显
				// 			that.tabFileList = result.data.list;
				// 		}
				// 	} else {
				// 		// 服务器返回失败
				// 		//console.log(result.data.message);
				// 		this.$message.error('查询失败')
				// 	}
				// })

			},

			// 查看
			handleView(row) {
				let that = this
				// 取到要修改的这条数据
				let options = {
					height: 500,
					width: 800,
					top: '15vh',
					callBack: function (result) {

					}
				}
				this.$refs.TpDialog.show('/sys/demo/view2', {}, "查看2", options);
			},
		}
	}
</script>

<style scoped lang="less">

</style>
