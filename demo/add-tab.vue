<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="注册日期">
							<tp-datepicker v-model="formData.date" format="YYYY-MM-DD HH:mm:ss"
										   value-format="YYYYMMDDHHmmss" @on-change="dateChange"
										   clearable></tp-datepicker>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="注册资金">
							<fb-input placeholder="请输入内容" v-model="formData.regFund">
								<template slot="append">万元</template>
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="多个图片">
							<tp-upload
								view="image"
								v-model="imgList.fileList"
								:service="$svc.sys.file"
								:param="{'referType': imgList.referType}"
								:referid="formData.id"
								:accept="'.png,.jpeg,.jpg'"
								:maxLength=2
							></tp-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="多个附件">
							<tp-upload
								v-model="fileList.fileList"
								:service="$svc.sys.file"
								:param="{'referType': fileList.referType}"
								:referid="formData.id"
								:accept="'.doc,.docx,.xlsx'"
								multiple
							></tp-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="生产地行政区划">
							<fb-input v-model="formData.cityId"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="生产经营地址">
							<fb-input v-model="formData.prodAddress"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="行业类别">
							<fb-select v-model="formData.industryTypeCode"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'SYS16'}"
									   :placeholder="'请选择'"
									   clearable/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="条线">
							<fb-select v-model="formData.lineCode"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'SYS24'}"
									   :placeholder="'请选择'"
									   clearable/>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="企业规模">
							<fb-select v-model="formData.scaleType"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'C36'}"
									   :placeholder="'请选择'"
									   clearable/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="是否启用">
							<fb-radio-group v-model="formData.enabled"
											:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
											:reader="{label:'name', value:'id'}"></fb-radio-group>
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
			// 执行界面初始化
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				// 附件列表
				imgList: {
					referType: 'SYS1021',
					fileList: [],
				},
				fileList: {
					referType: 'SYS1014',
					fileList: [],
				},
				// 表单数据
				formData: {
					id: '',
					date: '',
					cityId: '',
					prodAddress: '',
					industryTypeCode: '',
					lineCode: '',
					scaleType: '',
					enabled: 1,
					imgs: '',
					files: '',
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
				this.formData.id = '1366575600608739328';
				// 有值表示是修改方法
				if (param.id) { // 传ID表示修改
					this.formData.id = param.id;
					this.view()
				}
			},

			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			add() {
				// 界面校验
				let that = this;
				// 附件提交，后台用list接收
				that.formData.imgList = that.imgList;
				that.formData.fileList = that.fileList;
				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						/*if (!that.formData.id) {
							if (JSON.stringify(that.getPageParam()) == "{}") {
								this.$message.error('请先保存主表基本信息！')
								return;
							}
							// 获取缓存的主表id
							that.formData.id = that.getPageParam();
						}*/

						this.service.expAdd(that.formData).then((result) => {
							// 判断code
							if (result.code == 1) {
								that.$message.success('保存成功');
							} else {
								// 服务器返回失败
								that.$message.error('保存失败')
							}
						})
					}
				})
			},

			// 查询信息
			view() {
				// 调用新增service方法
				this.service.view({"id": this.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						this.formData = result.data
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})
			},

			dateChange(date) {
				console.log("date=", date)
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
