<template>
	<fb-property colon label-width="130px" mode="form">
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


			<fb-fieldset label="管控措施列表"/>
			<template>
				<fb-simple-table
					ref="table"
					:data="formData.dtlList"
					:columns="columns"
					:pk="primaryKey"
					:auto-load="false"
					:multiple="false">

					<template v-slot:view="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleView(row)" :label="row.ctrlMeasure" type="primary"></fb-link>
						</fb-link-group>
					</template>
				</fb-simple-table>
			</template>
		</div>
		<div class="tp-dialog-bottom">
			<fb-button style="padding-right: 20px" @on-click="handleClose">关闭</fb-button>
			<fb-button @on-click="handleView">打开view2</fb-button>
		</div>
		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
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
					enabled: 1,
					entDesc: '',
					list: [],
					dtlList: [],
				},

				primaryKey: "ctrlId",
				columns: [
					{
						name: 'ctrlId',
						hidden: true,
						width: 1,
					},
					{
						name: 'ctrlMeasure',
						label: '管控措施',
						sortable: false,
						slot: 'view',
						align: 'left',
						width: '20%'
					},
					{
						name: 'hdTypeName',
						label: '对应隐患类别',
						sortable: false,
						align: 'left',
						width: '20%'
					},
					{
						name: 'hdDesc',
						label: '对应隐患描述',
						sortable: false,
						align: 'left',
						width: '20%'
					},
					{
						name: 'fillPsnName',
						label: '填报人',
						sortable: false,
						align: 'left',
						width: '15%'
					},
					{
						name: 'fillDateView',
						label: '填报日期',
						sortable: false,
						align: 'left',
						width: '15%'
					},
				],

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
				// this.closeTpDialog(param);
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

			closeDialog(){
				console.log("")
			},
		}
	}
</script>

<style scoped lang="less">

</style>
