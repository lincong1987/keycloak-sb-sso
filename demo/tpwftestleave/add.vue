<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="请假类型" prop="levType" :rule="[{required: true}]">
							<fb-input v-model="formData.levType" placeholder="请输入请假类型"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="请假时间开始" prop="levTimeStart" :rule="[{required: true}]">
							<tp-datepicker v-model="formData.levTimeStart" format="YYYY-MM-DD HH:mm:ss"
										   value-format="YYYYMMDDHHmmss" placeholder="请选择开始时间日期"
										   :min-date="new Date('2021-03-01')" :max-date="new Date('2031-03-15')"
										   :showFootLeftBtns="['yesterday']"
										   clearable/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="请假时间结束" prop="levTimeEnd" :rule="[{required: true}]">
							<tp-datepicker v-model="formData.levTimeEnd" format="YYYY-MM-DD HH:mm:ss"
										   value-format="YYYYMMDDHHmmss" placeholder="请选择结束时间日期"
										   :min-date="new Date('2021-03-01')" :max-date="new Date('2031-03-15')"
										   :showFootLeftBtns="['yesterday']"
										   clearable/>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="时长（天）" prop="levDay" :rule="[{required: true}]">
							<fb-input v-model="formData.levDay" placeholder="请输入时长（天）"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="请假事由">
							<fb-input v-model="formData.levReason" placeholder="请输入请假事由"></fb-input>
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
				service: app.$svc.sys.demo.tpwftestleave,
				// 表单数据
				formData: {
					taskId: '',
					levId: '',
					levType: '',
					levTimeStart: '',
					levTimeEnd: '',
					levDay: '',
					levReason: '',
					levDept: '',
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
					this.formData.levId = param.id;
					this.formData.taskId = param.taskId;
					this.view()
				}
			},

			// 取消
			handleClose() {
				// 隐藏
				this.closeTpDialog(this.formData.levId);
			},

			add() {
				let that = this
				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						if (that.formData.levId) {
							// 调用修改service方法
							that.service.update(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									that.$message.success('修改成功');
									this.handleClose(that.formData.levId);
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
									this.handleClose(result.data.levId);
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
				this.service.view({"levId": this.formData.levId}).then((result) => {
					// 判断code
					if (result.code == 1) {
						result.data.taskId = this.formData.taskId
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
