<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">
				<fb-row>
					<fb-col span="12">
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="名称" prop="entFullName" :rule="{required: true}">
									<fb-input v-model="formData.name"></fb-input>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="性别" prop="sex" :rule="{required: true}">
									<fb-select v-model="formData.sex"
											   :data="defaultForm.sex"
											   :placeholder="'请选择'"
											   clearable/>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="证件类型" prop="idtype" :rule='{required:true}'>
									<fb-select v-model="formData.idtype"
											   :placeholder="'请选择证件类型'"
											   :service="$svc.sys.dict.select"
											   :param="{'pdicCode': 'Y24'}"/>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<!-- 动态校验规则，三元表达式写法 -->
								<fb-form-item label="证件号（三元表达式）" prop="idcard"
											  :rule="formData.idtype === 'Y2401' ? [{required: true}, {type: 'idcard'}] : {}">
									<fb-input
										v-model="formData.idcard"
										placeholder="请输入证件号">
									</fb-input>
								</fb-form-item>
							</fb-col>
						</fb-row>
					</fb-col>

					<fb-col span="12">
						<fb-form-item label="头像">
							<fb-upload
								view="avatar"
								v-model="formData.profilePhoto"
								:service="$svc.sys.file"
								:file-list="fileList"
								:param="{'referType':'SYS1014'}"
								:accept="'.png,.jpeg,.jpg'"
								@on-change="fileListChange">
								<fb-icon name="add-normal" size="30"></fb-icon>
							</fb-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="简介" prop="entDesc" :rule="{required: true}">
							<fb-textarea rows="5" v-model="formData.entDesc"
										 type="text"
										 placeholder="请输入内容"
										 maxlength="200">
							</fb-textarea>
						</fb-form-item>
					</fb-col>
				</fb-row>


				<div style="margin-bottom: 10px">
					<template>
						<fb-button ref="buttonAdd" @on-click="handleAdd" style="margin-right: 20px" icon="add-circle">
							新增
						</fb-button>
						<fb-button ref="buttonDel" @on-click="handleDel" icon="reduce-circle">
							删除
						</fb-button>
					</template>
				</div>

				<template>
					<fb-simple-table
						ref="table"
						:data="formData.data"
						:columns="columns"
						:pk="primaryKey"
						:auto-load="false"
						multiple
						:scroll="{x:500, y: 600, autoHeight: true}"
						@on-cell-click="handleTableCellClick">
						<template v-slot:actions="props">
							<fb-space>
								<fb-button @on-click="handleEdit(props.row)" editor size="s">修改</fb-button>
							</fb-space>
						</template>

						<template v-slot:edit="props">
							<fb-input
								v-if="props.rowIndex == tableClickIndex && tableClickName == 'controlTypeName'"
								placeholder="请输入信息"
								size="m"
								v-model="formData.data[props.rowIndex].controlTypeName"
								@on-blur="inputBlur"
							/>
							<span v-else>{{formData.data[props.rowIndex].controlTypeName}}</span>
						</template>


						<template v-slot:allNum="props">
							<fb-form-item prop="totalScore"
										  :rule="[{required: true}, {type: 'money', fixed: 2, message:'请输入合法字符'}, {type : 'number', max: formData.data[props.rowIndex].maxScore}]">
								<fb-input
									placeholder="请输入扩展信息1"
									size="m"
									v-model.number="formData.data[props.rowIndex].totalScore"/>
							</fb-form-item>
						</template>

					</fb-simple-table>
				</template>
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
	import {cloneDeep} from "lodash-es"
	import {each} from "lodash-es"

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
				fileList: [],
				// 证件号动态校验规则默认值，身份证校验
				idcardRule: [{required: true}, {type: 'idcard'}],
				// 表单数据
				formData: {
					name: '',
					sex: '',
					profilePhoto: '',
					idtype: 'Y2401',
					idcard: '',
					entDesc: '',
					data: [],
					list: []
				},
				// 编辑表格判断
				tableClickIndex: '',
				tableClickName: '',
				primaryKey: "id",
				columns: [
					{
						name: 'assessYear',
						label: '年份',
						sortable: false,
						width: 50,
					}, {
						name: 'assessMonth',
						label: '月份',
						sortable: false,
						width: 100,
					}, {
						name: 'townName',
						label: '城镇',
						sortable: false,
					},
					{
						name: 'controlTypeName',
						label: '事故总量',
						slot: 'edit',
						sortable: false,
					},
					{
						name: 'totalScore',
						label: '输入量',
						slot: 'allNum',
						sortable: false,
					},
					{
						name: '',
						label: '操作',
						sortable: false,
						slot: 'actions',
						width: 100,
					},
				],
				rules: {
					// 校验器名称（必要，唯一，可任意）
					"extend1": {
						// 自定义 校验方法，方法名与参数固定不变
						validator: (rule, value, callback, source, options) => {
							debugger
							// 可通过 _this 获取上下文
							if (value !== this.forgetForm1.password) {
								// 校验未通过，返回错误信息
								return callback('两次输入的密码不一致，请重新输入');
							} else {
								// 校验通过，返回空参数
								return callback();
							}
						}
					}
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
					this.formData.id = param.id;
				}
					this.view()
			},

			// 取消
			handleClose() {

				let param = {};
				this.closeTpDialog(param);
			},
			add() {
				let that = this;
				each(this.$refs, (item, key) => {
					if (key.indexOf("sale_form_") >= 0) {
						// 遍历form
						debugger
						if (item.$parent._data.myValue === true) {
							debugger
						}
					}
				})


				// 图片处理，后端list接收
				// that.formData.list = this.fileList;
				// 界面校验
				/*this.$refs.fbform.validate((result) => {
					if (result === true) {
						if (that.formData.personId) {
							// 修改
							this.service.update(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									this.$message.success('修改成功');
								} else {
									// 服务器返回失败
									this.$message.error('修改失败:' + result.data.message)
								}
							})
						} else {
							// 新增，调用新增service方法
							this.service.org.add(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									this.$message.success('新增成功');

									// 1 单弹出框新增成功，关闭弹出框，param是回传的参数
									let param = {};
									this.closeTpDialog(param);

									// 2 tab 新增，不用关闭弹出框，将返回的主键放入到缓存，可以在tab切换时，获取到主表的主键
									// let id = result.data.id;
									// this.formData.id = id;
									// this.setPageParam(id);
								} else {
									// 服务器返回失败
									this.$message.error('新增失败')
								}
							})
						}
					}
				})*/
			},
			fileListChange(info) {
				console.log(info)
				if (info.file.res) {
					this.fileList.push(info.file.res.data);
				}
			},
			handleTableCellClick(col, cellIndex, row, rowIndex, event) {
				// console.log(col, cellIndex, row, rowIndex, event)

				this.tableClickIndex = rowIndex
				this.tableClickName = col.name
				console.log(this.tableClickIndex, this.tableClickName)
			},
			// 失去焦点初始化
			inputBlur() {
				this.tableClickIndex = null
				this.tableClickName = ''

			},
			// 查询信息
			view() {
				let that = this;
				// 调用新增service方法
				this.service.view({"id": this.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						debugger
						result = {"code":1,"message":"成功","data":{"records":[{"sMcTowmcontrolList":"","maxScore":6,"towmcontrolId":"1405076344085151744","townId":"330603114","assessYear":"2021","assessMonth":"06","townName":"夏履镇","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":7,"towmcontrolId":"1405076344114511872","townId":"330603114","assessYear":"2021","assessMonth":"06","townName":"夏履镇","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":7,"towmcontrolId":"1405076344139677696","townId":"330603114","assessYear":"2021","assessMonth":"06","townName":"夏履镇","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":6,"towmcontrolId":"1405076343728635904","townId":"330603111","assessYear":"2021","assessMonth":"06","townName":"漓渚镇","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":1.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343749607424","townId":"330603111","assessYear":"2021","assessMonth":"06","townName":"漓渚镇","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343787356160","townId":"330603111","assessYear":"2021","assessMonth":"06","townName":"漓渚镇","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076343439228928","townId":"330603110","assessYear":"2021","assessMonth":"06","townName":"杨汛桥街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343460200448","townId":"330603110","assessYear":"2021","assessMonth":"06","townName":"杨汛桥街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343481171968","townId":"330603110","assessYear":"2021","assessMonth":"06","townName":"杨汛桥街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076343154016256","townId":"330603109","assessYear":"2021","assessMonth":"06","townName":"稽东镇","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343174987776","townId":"330603109","assessYear":"2021","assessMonth":"06","townName":"稽东镇","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076343200153600","townId":"330603109","assessYear":"2021","assessMonth":"06","townName":"稽东镇","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076342877192192","townId":"330603108","assessYear":"2021","assessMonth":"06","townName":"兰亭街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342898163712","townId":"330603108","assessYear":"2021","assessMonth":"06","townName":"兰亭街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342919135232","townId":"330603108","assessYear":"2021","assessMonth":"06","townName":"兰亭街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076342591979520","townId":"330603107","assessYear":"2021","assessMonth":"06","townName":"王坛镇","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342617145344","townId":"330603107","assessYear":"2021","assessMonth":"06","townName":"王坛镇","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342638116864","townId":"330603107","assessYear":"2021","assessMonth":"06","townName":"王坛镇","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076342310961152","townId":"330603106","assessYear":"2021","assessMonth":"06","townName":"安昌街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342331932672","townId":"330603106","assessYear":"2021","assessMonth":"06","townName":"安昌街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342352904192","townId":"330603106","assessYear":"2021","assessMonth":"06","townName":"安昌街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076342021554176","townId":"330603105","assessYear":"2021","assessMonth":"06","townName":"平水镇","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342042525696","townId":"330603105","assessYear":"2021","assessMonth":"06","townName":"平水镇","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076342067691520","townId":"330603105","assessYear":"2021","assessMonth":"06","townName":"平水镇","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076341702787072","townId":"330603104","assessYear":"2021","assessMonth":"06","townName":"马鞍街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341723758592","townId":"330603104","assessYear":"2021","assessMonth":"06","townName":"马鞍街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341748924416","townId":"330603104","assessYear":"2021","assessMonth":"06","townName":"马鞍街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076341400797184","townId":"330603103","assessYear":"2021","assessMonth":"06","townName":"福全街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341425963008","townId":"330603103","assessYear":"2021","assessMonth":"06","townName":"福全街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341446934528","townId":"330603103","assessYear":"2021","assessMonth":"06","townName":"福全街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076341132361728","townId":"330603101","assessYear":"2021","assessMonth":"06","townName":"钱清街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341149138944","townId":"330603101","assessYear":"2021","assessMonth":"06","townName":"钱清街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076341165916160","townId":"330603101","assessYear":"2021","assessMonth":"06","townName":"钱清街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076340863926272","townId":"330603100","assessYear":"2021","assessMonth":"06","townName":"齐贤街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340884897792","townId":"330603100","assessYear":"2021","assessMonth":"06","townName":"齐贤街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340905869312","townId":"330603100","assessYear":"2021","assessMonth":"06","townName":"齐贤街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076340549353472","townId":"330603004","assessYear":"2021","assessMonth":"06","townName":"湖塘街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340578713600","townId":"330603004","assessYear":"2021","assessMonth":"06","townName":"湖塘街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340603879424","townId":"330603004","assessYear":"2021","assessMonth":"06","townName":"湖塘街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076340251557888","townId":"330603003","assessYear":"2021","assessMonth":"06","townName":"华舍街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340276723712","townId":"330603003","assessYear":"2021","assessMonth":"06","townName":"华舍街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076340297695232","townId":"330603003","assessYear":"2021","assessMonth":"06","townName":"华舍街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076339932790784","townId":"330603002","assessYear":"2021","assessMonth":"06","townName":"柯岩街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076339953762304","townId":"330603002","assessYear":"2021","assessMonth":"06","townName":"柯岩街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076339983122432","townId":"330603002","assessYear":"2021","assessMonth":"06","townName":"柯岩街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"6","towmcontrolId":"1405076339626606592","townId":"330603001","assessYear":"2021","assessMonth":"06","townName":"柯桥街道","controlType":"HX70101","controlTypeName":"事故总量（6）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076339647578112","townId":"330603001","assessYear":"2021","assessMonth":"06","townName":"柯桥街道","controlType":"HX70102","controlTypeName":"较大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""},{"sMcTowmcontrolList":"","maxScore":"7","towmcontrolId":"1405076339668549632","townId":"330603001","assessYear":"2021","assessMonth":"06","townName":"柯桥街道","controlType":"HX70103","controlTypeName":"重特大事故（7）","totalScore":0.00,"actived":"","creator":"","createTime":"","updator":"","updateTime":""}],"total":48,"size":500,"current":1,"orders":[],"searchCount":true,"pages":1},"expand":""};
						that.formData.data = result.data.records

						// 图片回显
						that.fileList = result.data.list;
					} else {
						// 服务器返回失败
						that.$message.error('错误提示:' + result.message)
					}
				})
			},

			idtypeChange(value) {
				let rule = []
				switch (value) {
					case 'Y2401':
						rule = [{required: true}, {type: 'idcard'}]
						break;
					case 'Y2402':
						rule = [{required: true}]
						break;
					default :
						rule = {}
						break;
				}
				this.idcardRule = rule
				this.formData.idcard1 = '';
			},

			handleAdd() {
				debugger
				// let copyData = cloneDeep(this.formData.data);
				this.formData.data.push({
					'userName': '',
					'userGander': '',
					'userTel': '',
					'desc': '',
				});
				// this.formData.data = copyData;
			},

			handleDel() {
				let rows = this.$refs.table.getSelectedRows()
				rows.forEach((val, ind) => {
					this.formData.data.some((item, i) => {
						if (item.id == val.id) {
							this.formData.data.splice(i, 1)
							//在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
							return true
						}
					})
				})
			},

			handleEdit(row) {
				let editData = this.formData.data.filter(item => item.id == row.id)
				editData[0].userName = "zhangsan";
			}

		}
	}
</script>

<style lang="less" scoped>

</style>
