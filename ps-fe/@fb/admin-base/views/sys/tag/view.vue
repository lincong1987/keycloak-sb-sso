<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform">
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="标签名称">
							<fb-input v-model="formData.tagName" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="标签类别">
							<fb-input v-model="formData.category" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="标签颜色">
							<div style="display: flex; align-items: center;">
								<fb-input v-model="formData.tagColor" :disabled="true" style="margin-right: 10px;"></fb-input>
								<div :style="{width: '20px', height: '20px', backgroundColor: formData.tagColor, border: '1px solid #ccc'}"></div>
							</div>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="排序">
							<fb-input v-model="formData.orderIndex" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="24">
						<fb-form-item label="标签描述">
							<fb-textarea rows="3" v-model="formData.tagDesc" :disabled="true">
							</fb-textarea>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="是否有效">
							<fb-input :value="formData.isValid === 1 ? '有效' : '无效'" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="创建时间">
							<fb-input v-model="formData.createTime" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="创建人">
							<fb-input v-model="formData.createPersonName" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="修改时间">
							<fb-input v-model="formData.updateTime" :disabled="true"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
			</fb-form>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'view',
		mixins: [],
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
		created() {},
		// 初始化方法
		mounted() {
			// 执行界面初始化
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.tag,
				// 表单数据
				formData: {
					tagId: '',
					tagName: '',
					category: '',
					tagColor: '',
					orderIndex: '',
					tagDesc: '',
					isValid: 1,
					createTime: '',
					createPersonName: '',
					updateTime: '',
				},
			}
		},

		// 方法
		methods: {
			// 设置标题
			init(param) {
				if (param && param.id) {
					let tagId = param.id;
					this.formData.tagId = tagId;
					this.view(tagId, param.passKey);
				}
			},

			// 取消
			handleClose() {
				// 关闭，并传递参数
				this.closeTpDialog("xxxx");
			},

			view(tagId, passKey) {
				// 调用查看service方法
				this.service.view({"tagId": tagId, "passKey": passKey}).then((result) => {
					// 判断code
					if (result.code == 1) {
						this.formData = result.data
					} else {
						// 服务器返回失败
						this.$message.error('查询失败: ' + result.message)
					}
				}).catch((err) => {
					// 服务器返回失败
					console.log(err);
				})
			},
		}
	}
</script>

<style lang="less" scoped>

</style>