<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">
				<fb-fieldset label="基本信息"/>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="名称">
							<fb-input v-model="formData.name" readonly></fb-input>
						</fb-form-item>
					</fb-col>

					<fb-col span="12">
						<fb-form-item label="头像">
							<tp-upload
								view="avatar"
								v-model="file.fileList"
								:service="$svc.sys.file"
								:param="{'referType': file.referType}"
								:referid="formData.id"
								:accept="'.png,.jpeg,.jpg'"
								readonly
							></tp-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="性别">
							<fb-select v-model="formData.sex"
									   :data="defaultForm.sex"
									   readonly/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="身份证">
							<fb-input v-model="formData.idcard" readonly></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="统一社会信用代码">
							<fb-input v-model="formData.entUnifiedCode" readonly>
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="类型">
							<fb-select v-model="formData.type"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'E10'}"
									   readonly/>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="注册日期">
							<tp-datepicker v-model="formData.date" format="YYYY-MM-DD"
										   value-format="YYYYMMDD" readonly></tp-datepicker>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="注册资金">
							<fb-input v-model="formData.regFund" readonly>
								<template slot="append">万元</template>
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="联系人姓名">
							<fb-input v-model="formData.linkPsnName" readonly></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="联系手机">
							<fb-input v-model="formData.linkPsnTel" readonly></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>


				<fb-row>
					<fb-col span="12">
						<fb-form-item label="注册地址">
							<fb-tree-select
								ref="addrCodeTree"
								v-model="formData.addrCode"
								:service="$svc.sys.city.tree"
								:param="{'sync': 1, 'expand': true, 'cityId': '-1'}"
								:reader="{value:'extend02', label: 'text'}"
								readonly>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="注册地址">
							<fb-input v-model="formData.addr" clearable></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="email">
							<fb-input v-model="formData.email" readonly></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="是否启用">
							<fb-radio-group v-model="formData.enabled"
											:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
											:reader="{label:'name', value:'id'}" readonly></fb-radio-group>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="24">
						<fb-form-item label="简介">
							<fb-textarea rows="5" v-model="formData.entDesc"
										 type="text"
										 maxlength="200" readonly>
							</fb-textarea>
						</fb-form-item>
					</fb-col>
				</fb-row>


				<fb-fieldset label="扩展信息"/>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="多个图片">∂
							<tp-upload
								view="image"
								v-model="tabFileList.fileList"
								:service="$svc.sys.file"
								:param="{'referType': tabFileList.referType}"
								:referid="formData.id"
								:accept="'.png,.jpeg,.jpg'"
								readonly
							></tp-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="生产地行政区划">
							<fb-input v-model="expFormData.cityId" readonly></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="生产经营地址">
							<fb-input v-model="expFormData.prodAddress" readonly></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="行业类别">
							<fb-select v-model="expFormData.industryTypeCode"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'SYS16'}"
									   readonly/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="条线">
							<fb-select v-model="expFormData.lineCode"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'SYS24'}"
									   readonly/>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="企业规模">
							<fb-select v-model="expFormData.scaleType"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'C36'}"
									   readonly/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="是否启用">
							<fb-radio-group v-model="expFormData.enabled"
											:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
											:reader="{label:'name', value:'id'}" readonly></fb-radio-group>
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

				// 附件列表
				// 附件列表
				file: {
					referType: 'SYS1014',
					fileList: [],
				},
				tabFileList: {
					referType: 'SYS1021',
					fileList: [],
				},

				formData: {
					id: '',
					name: '',
					profilePhoto: '',
					sex: '',
					idcard: '',
					entUnifiedCode: '',
					type: '',
					date: '',
					regFund: '',
					email: '',
					linkPsnName: '',
					linkPsnTel: '',
					addrCode: '',
					addr: '',
					enabled: 1,
					entDesc: '',
				},

				expFormData: {
					imgs: '',
					cityId: '',
					prodAddress: '',
					industryTypeCode: '',
					lineCode: '',
					scaleType: '',
					enabled: '',
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
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})

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
