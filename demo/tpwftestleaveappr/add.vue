<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="审批主键" prop="appId" :rule="[{required: true}]">
							<fb-input v-model="formData.appId" placeholder="请输入审批主键"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="请假单主键" prop="levId" :rule="[{required: true}]">
							<fb-input v-model="formData.levId" placeholder="请输入请假单主键"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="审批部门" prop="appDept" :rule="[{required: true}]">
							<fb-input v-model="formData.appDept" placeholder="请输入审批部门"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="审批内容" prop="appContent" :rule="[{required: true}]">
							<fb-input v-model="formData.appContent" placeholder="请输入审批内容"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="审批结果" prop="appResult" :rule="[{required: true}]">
							<fb-input v-model="formData.appResult" placeholder="请输入审批结果"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="有效标记" prop="actived" :rule="[{required: true}]">
							<fb-input v-model="formData.actived" placeholder="请输入有效标记"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="创建人" prop="creator" :rule="[{required: true}]">
							<fb-input v-model="formData.creator" placeholder="请输入创建人"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="创建时间" prop="createTime" :rule="[{required: true}]">
							<fb-input v-model="formData.createTime" placeholder="请输入创建时间"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="修改人" prop="updator" :rule="[{required: true}]">
							<fb-input v-model="formData.updator" placeholder="请输入修改人"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="修改时间" prop="updateTime" :rule="[{required: true}]">
							<fb-input v-model="formData.updateTime" placeholder="请输入修改时间"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="备用" prop="extend01" :rule="[{required: true}]">
							<fb-input v-model="formData.extend01" placeholder="请输入备用"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="备用" prop="extend02" :rule="[{required: true}]">
							<fb-input v-model="formData.extend02" placeholder="请输入备用"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="备用" prop="extend03" :rule="[{required: true}]">
							<fb-input v-model="formData.extend03" placeholder="请输入备用"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
			</fb-form>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">保存</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>
</template>


<script>

	import Page from "@/util/Page"

	export default {
		name: 'add-basicinfo',
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
			// 执行界面初始化
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: app.$svc.sys.demo.tpwftestleaveappr,
				// 表单数据
				formData: {
					appId: '',
					levId: '',
					appDept: '',
					appContent: '',
					appResult: '',
					actived: '',
					creator: '',
					createTime: '',
					updator: '',
					updateTime: '',
					extend01: '',
					extend02: '',
					extend03: '',
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
				// 有值表示是修改方法
				if (param.id) { // 传ID表示修改
					this.formData.appId = param.id;
					this.view()
				}
			},

			// 取消
			handleClose() {
				// 隐藏
				this.closeTpDialog(this.formData.appId);
			},

			add() {
				let that = this
				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						if (that.formData.appId) {
							// 调用修改service方法
							that.service.update(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									that.$message.success('修改成功');
									this.handleClose(that.formData.appId);
								} else {
									// 服务器返回失败
									that.$message.error('修改失败:' + result.message)
								}
								;
								that.updateCount = 0;
							})
						} else {
							// 调用新增service方法
							that.service.add(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									that.$message.success('新增成功');
									this.handleClose(result.data.appId);
								} else {
									// 服务器返回失败
									that.$message.error('新增失败:' + result.message)
								}
								;
								that.updateCount = 0;
							})
						}
					}
				})
			},

			// 查询信息
			view() {

				// 调用新增service方法
				this.service.view({"appId": this.formData.appId}).then((result) => {
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
