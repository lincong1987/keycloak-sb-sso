<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form ref="query-form" mode="query">
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="人员名称">
								<fb-input v-model="formData.userName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="11" offset="1">
							<fb-form-item label="性别">
								<fb-select v-model="formData.userGander"
										   :data="defaultForm.sex"
										   :placeholder="'请选择'"
										   clearable/>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="时间">
								<tp-datepicker v-model="formData.date" @rangFormat="rangeDateFormat"
											   format="YYYY-MM-DD HH:mm:ss" value-format="YYYYMMDDHHmmss" max-range="3D"
											   mode="range"
											   clearable></tp-datepicker>
							</fb-form-item>
						</fb-col>

						<fb-col span="11" offset="1">
							<fb-form-item label="时间选择" :content-style="{ }" style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date1" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择开始时间'"
											   clearable></tp-datepicker>
							</fb-form-item>
							<fb-form-item label="-" :label-style="{textAlign: 'center', paddingLeft: '18px'}"
										  style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date2" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择结束时间'"
											   clearable></tp-datepicker>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="时间区间" :content-style="{ }" style="display: inline-block; width: 80%">
								<tp-datepicker v-model="formData.date3" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择开始时间'"
											   :max-date="formData.mid1"
											   @on-change="startOnChangeDate" clearable></tp-datepicker>
							</fb-form-item>
							<fb-form-item label="-" :label-style="{textAlign: 'center', paddingLeft: '18px'}"
										  style="display: inline-block; width: 80%">
								<tp-datepicker v-model="formData.date4" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择结束时间'"
											   :min-date="formData.mid2"
											   @on-change="endOnChangeDate" clearable></tp-datepicker>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<!--<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission = "'SYS_ENTMANAGE_ENT_ADD'" icon="add-circle">add</fb-button> v-permission指令权限控制-->
				<fb-button ref="buttonAdd" @on-click="handleAdd" icon="add-circle">add</fb-button>
				<fb-button ref="buttonTabAdd" @on-click="handleTabAdd" icon="add-circle">tab-add</fb-button>
				<fb-button ref="buttonTabAdd" @on-click="handleFlowTabAdd" icon="add-circle">flow-tab-add</fb-button>
				<fb-button ref="buttonSelect" @on-click="handleUserSelect" icon="add-circle">人员多选</fb-button>
				<fb-button ref="buttonSingleSelect" @on-click="handleSingleSelect" icon="add-circle">人员单选</fb-button>
				<fb-button ref="buttonEntSelect" @on-click="handleEntSelect" icon="add-circle">企业多选</fb-button>
				<fb-button ref="buttonEntSingleSelect" @on-click="handleEntSingleSelect" icon="add-circle">企业单选
				</fb-button>
				<!--<fb-button ref="buttonDynamic" @on-click="handleDynamic" icon="add-circle">动态列(增加tabbr跳转)</fb-button>
				<fb-button ref="handleForm" @on-click="handleForm" icon="add-circle">form(增加tabbr跳转)</fb-button>
				<fb-button ref="buttonTabList" @on-click="handleTabList" icon="add-circle">tab-list(页面内跳转)</fb-button>-->
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery">查询</fb-button>
				<fb-button @on-click="formReset" icon="search">重 置</fb-button>
				<fb-button @on-click="handleOutput" icon="search" type="primary">导出</fb-button>

				<fb-button @on-click="$refs.advancedQueryForm.show()">高级查询</fb-button>
				<fb-drawer ref="advancedQueryForm" title="查询" position="right" width="500" :esc="false">
					<fb-card header="高级查询" height="" :body-style="{'padding-bottom': '80px'}">
						<fb-button slot="actions" @on-click="$refs.advancedQueryForm.hide()" round size="m" type="link"
								   icon="close"></fb-button>
						<fb-form label-position="top">
							<fb-fieldset label="请填写"></fb-fieldset>
							<fb-row flex justify="start">
								<fb-col span="11">
									<fb-form-item label="姓名">
										<fb-input value="骚攀"></fb-input>
									</fb-form-item>
								</fb-col>
								<fb-col span="11" offset="2">
									<fb-form-item label="姓名">
										<fb-input></fb-input>
									</fb-form-item>
								</fb-col>
							</fb-row>
						</fb-form>
						<div style="box-sizing: border-box; text-align: center; background-color: #FFFFFF; position: absolute; bottom: 0;
		left:       0;
		right:      0;
		padding:    16px 0;
		border-top: 1px solid #ccc">
							<fb-button type="link" @on-click="$refs.advancedQueryForm.hide()">取消</fb-button>
							<fb-button type="primary" @on-click="handleSubmitXXForm"
									   sstyle="margin-left: 12px">提交
							</fb-button>
						</div>
					</fb-card>
				</fb-drawer>
			</template>

			<template slot="table">
				<fb-simple-table
					ref="table"
					:service="table.service.search"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					:formatters="formatters"
					auto-load
					multiple
					rowNum
					:autoSelect="false"
					:scroll="{x:1500, y: 400, autoHeight: true}"
					:showTotalInfo="false"
					@on-cell-click="handleTableCellClick"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="props">
						<fb-space>
							<fb-button @on-click="handleEdit(props.row)" editor size="s">修改</fb-button>
							<fb-button @on-click="handleTabEdit(props.row)" editor size="s">授权</fb-button>
							<fb-button @on-click="handleDel(props.row)" danger size="s">删除</fb-button>
							<fb-button @on-click="handleDocument(props.row)" danger size="s">在线编辑</fb-button>
						</fb-space>
					</template>

					<template v-slot:view="props">
						<fb-badge v-if="props.row.userId === '1367674256661413888'" dot dotColor="#111"
								  style="margin-right: 5px; margin-bottom: 4px"
								  dot-size="14"></fb-badge>
						<fb-badge v-if="props.row.userId === '1366575600608739328'" dot dotColor="red"
								  style="margin-right: 5px; margin-bottom: 4px"
								  dot-size="14"></fb-badge>

						<fb-link :click="()=>handleView(props.row)" :label="props.row.userName"
								 type="primary"></fb-link>
					</template>

					<template v-slot:selectSex="props">
						<fb-select v-model="formData.userSex"
								   :data="defaultForm.sex"
								   :placeholder="'请选择'"
								   clearable/>
					</template>

					<template v-slot:radioActived="props">
						<fb-radio-group v-model="props.row.enabled"
										:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
										:reader="{label:'name', value:'id'}"></fb-radio-group>
					</template>

					<!--<template v-slot:editPro="props">
						<fb-radio-group  v-model="data[props.rowIndex].radioCheck"  :data="[{id: 1, name: '受控',}, {id: 0, name: '不受控',}]"
										:reader="{label:'name', value:'id'}"
										@on-change="handelRadio(row)">

						</fb-radio-group>
					</template>-->

					<template v-slot:view2="props">
						<fb-link-group>
							<fb-link :click="()=>handleView2(props.row)" :label="props.row.userGander"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:download="props">
						<fb-link-group>
							<fb-link :label="props.row.file"
									 :href="fileService.download({'attachId': props.row.attachId})"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:files="props">
						<template v-if="props.rowIndex/2==0">
							<fb-container v-for="(val, index) in props.row.files" :key="index" height="24px"
										  valign="center">
								<fb-link
									type="primary"
									icon="jpx-icon-setting-gear-fill"
									:label="val.name"
									:href="fileService.download({'attachId': val.id})"
								>
								</fb-link>
							</fb-container>
						</template>
						<template v-else>
							{{props.value}}
						</template>
					</template>

					<template v-slot:tabbar="props">
						<fb-link-group>
							<fb-link :click="()=>handleTabbar(props.row)" :label="props.row.tabbar"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:editPro="props">
					  <fb-input
						  v-if="props.rowIndex == tableClickIndex && tableClickName == 'editPro'"
						  v-model="props.row.editPro"
						  placeholder="请输入信息"
						  size="m"
					  		@on-blur="inputBlur"/>
						<span v-else>{{props.row.editPro}}</span>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-search>
		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
		<tp-dialog-tab ref="TpDialogTab" @closeTpDialog="closeDialogTab"></tp-dialog-tab>
		<tp-dialog-flow-tab ref="TpDialogFlowTab" @closeTpDialog="closeDialogFlowTab"></tp-dialog-flow-tab>
	</div>
</template>

<script>

	import Page from "@/util/Page"

	export default {
		mixins: [
			Page
		],
		// 组件
		components: {
			// 'component-a': ComponentA,

		},
		// 初始化方法
		mounted() {
			// 执行界面初始化方法
			this.handleQuery();
		},
		data() {
			return {
				// 请求的 url
				fileService: app.$svc.sys.file,
				formData: {
					userName: '',
					userGander: '',
					userSex: '',
					date: [null, null],
					date1: '',
					date2: '',
					date3: '',
					mid1: null,
					mid2: null,
					mindate3: '',
					maxdate3: '',
					date4: '',
					mindate4: '',
					maxdate4: '',
					startDate: '',
					endDate: '',
					enabled: 1,
				},

				formatters: {
					userGander(val) {
						return val == 1 ? '男' : val
					}
				},

				// 编辑表格判断
				tableClickIndex: '',
				tableClickName: '',

				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.demo,

					primaryKey: "userId",
					columns: [
						{
							name: 'userName',
							label: '人员名称',
							sortable: false,
							slot: 'view',
							width: 150
						}, {
							name: 'userSex',
							label: '选择',
							sortable: false,
							slot: 'selectSex',
							ellipsis: false,
							width: 150
						}, {
							name: 'actived',
							label: '单选',
							sortable: false,
							slot: 'radioActived',
							ellipsis: false,
							width: 150
						}, {
							name: 'userGander',
							label: '性别',
							slot: 'view2',
							sortable: false,
						}, {
							name: 'userTel',
							label: '手机号码',
							sortable: false,
							width: 200
						}, {
							name: 'file',
							label: '下载',
							sortable: false,
							slot: 'download',
							width: 100
						}, {
							name: 'tabbar',
							label: '打开tabbar',
							sortable: false,
							slot: 'tabbar',
							width: 100
						}, {
							name: 'desc',
							label: '描述',
							sortable: false,
							width: 200,
							ellipsis: false,
						}, {
							name: 'editPro',
							label: '编辑元素',
							sortable: false,
							slot: 'editPro',
							width: 100,
							ellipsis: false,
						}, {
							name: 'files',
							label: '多附件',
							sortable: false,
							slot: 'files',
							width: 100,
						}, {
							name: '',
							label: '操作',
							sortable: false,
							slot: 'actions',
							width: 292,
							// 冻结操作列
							freeze: 'right'
						},

					],
				},
			}
		},

		// 方法
		methods: {
			// 区间日期的格式化回调方法
			rangeDateFormat(start, end) {
				this.formData.startDate = start;
				this.formData.endDate = end;
			},
			// 列表方法
			handleQuery() {
				this.$refs.table.doSearch();
			},
			handleOutput() {

				let columns = this.table.columns.map(item => {
					if (item.name && item.name != '' && item.name != 'desc') {
						return item.name
					}
				});
				columns.push("date");
				columns.push("doubleData");
				columns.push("dicName");

				this.formData.columns = columns;
				this.formData.fileName = "测试excel导出";

				this.table.service.output(this.formData)

			},
			// 列表界面表格的按钮控制
			handleTableSelect(row, selectedRows) {
				// this.buttons.del.disabled = selectedRows.length != 1
				// this.buttons.edit.disabled = selectedRows.length != 1
				console.log(row, selectedRows)
			},
			// 查看方法
			handleAdd() {
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/add.vue', param, "新增");
			},

			// 新增方法
			handleTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/add.vue',
					label: '基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "tab新增");
			},
			handleView2(row) {
				let id = row.userId
				let param = {"id": id}
				let options = {
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view2.vue', param, "查看", options);
			},
			handleTabbar(row) {
				// 解密 this.$router.decrypt(id)

				// this.$router.push({
				// 	path: '/sys/demo/tree-list',
				// 	query: {id: this.$router.encrypt(row.userId), tabLabel: row.userName}
				// })

				this.pushTab('/sys/demo/tree-list', row.userId, row.userName)
			},
			// 新增方法
			handleFlowTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/add.vue',
					label: '基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogFlowTab.show(tabArry, param, "flow-tab新增");
			},
			// 修改方法
			handleEdit(row) {
				let param = {"id": row.userId};

				this.$refs.TpDialog.show('/sys/demo/add.vue', param, "修改");
			},
			// 修改方法
			handleTabEdit(row) {

				let param = {"id": row.userId};
				let tabArry = [{
					url: '/sys/demo/add.vue',
					label: '基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "修改");
			},
			// 删除方法
			handleDel(row) {
				this.$confirm('确定要删除吗？', () => {
					this.table.service.delete({"id": row.id}).then((result) => {
						if (result.code == 1) {
							this.$message.success('删除成功');
							this.handleQuery();
						} else {
							// 服务器返回失败
							this.$message.error('删除失败: ' + result.message)
						}
					});
				}, () => {
					// alert(" 我点了取消")
					row.userGander = 0
				})
			},
			// 查看方法
			handleView(row) {

				let id = row.userId
				let param = {"id": id}
				let options = {
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view.vue', param, "查看", options);
			},

			closeDialog(param) {
				// 单页面弹出框关闭时，回调方法，param是回传的参数
				console.log(param);
			},
			closeDialogTab(param) {
				// tab切换 页面弹出框关闭时，回调方法，param是回传的参数
				if (param) {
					this.handleQuery();
				}
			},
			closeDialogFlowTab(param) {
				console.log(param)
			},
			handleUserSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-person-multiple.vue', param, "人员选择", options);
			},
			handleSingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-person-single.vue', param, "人员单选", options);
			},
			handleEntSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-ent-multiple.vue', param, "企业选择", options);
			},
			handleEntSingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-ent-single.vue', param, "企业单选", options);
			},
			handleDynamic() {
				this.$router.push({
					path: '/sys/demo/dynamic', // 配置的路由
					query: {
						site: "aaa"  // 传递的参数
					}
				})
			},

			handleForm() {
				this.$router.push({
					name: 'tabbar-form', // 配置的路由
					params: {
						storageAddr: "我的地址我定义"  // 传递的参数
					}
				})
			},

			handleTabList() {
				this.$router.push({
					path: '/sys/demo/tab-page', // 配置的路由
					query: {
						id: "100001"  // 传递的参数
					}
				})
			},

			startOnChangeDate(value) {

			},

			endOnChangeDate(value) {


			},

			handelRadio(row) {

				console.log(row)
			},

			handleSubmitXXForm() {

				this.$loading.show({text: '正在上传你的C盘资料, 请耐心等待...'})

				setTimeout(() => {

					this.$refs.advancedQueryForm.hide()

				}, 15000)

			},

			handleTableRowClick(row, index) {
				console.log(row, index)
			},

			handleTableCellClick(row, index, column) {
				console.log(row, index, column)
			},

			handleTableCellClick (col, cellIndex, row, rowIndex, event) {
				// console.log(col, cellIndex, row, rowIndex, event)

				this.tableClickIndex = rowIndex
				this.tableClickName = col.name
				console.log(this.tableClickIndex, this.tableClickName)
			},

			handleDocument(row){
				let param = {"id": row.userId};

				// 屏幕可用工作区宽度
				// let currentWidth = window.screen.width - 100
				// let currentHeight = window.screen.height - 100
				// 网页分辨率宽
				let currentWidth = window.innerWidth - 50
				let currentHeight = window.innerHeight - 100

				let options = {
					width:currentWidth,
					height: currentHeight,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}


				this.$refs.TpDialog.show('/common/document/editor.vue', param, "在线编辑", options);
			},

			// 失去焦点初始化
			inputBlur() {
				console.log('lalalal')
				this.tableClickIndex = null
				this.tableClickName = ''
			},

		},
		watch: {
			"formData.date3"(value1, value2){
				this.formData.mid2 = app.dayjs(value1).toDate();
			},
			"formData.mid2"(value1, value2){
				this.formData.mid1 = app.dayjs(value1).toDate();
			}
		}
	}
</script>

<style lang="less" scoped>
</style>
