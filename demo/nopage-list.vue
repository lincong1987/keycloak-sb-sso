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
								<tp-datepicker mode="range" v-model="formData.date" @rangFormat="rangeDateFormat"
											   format="YYYY-MM-DD HH:mm:ss" value-format="YYYYMMDDHHmmss"
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
							<fb-form-item label="时间区间" :content-style="{ }" style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date3" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择开始时间'"
											   :min-date="new Date('2021-03-01')" :max-date="new Date('2021-03-15')"
											   @on-change="startOnChangeDate" clearable></tp-datepicker>
							</fb-form-item>
							<fb-form-item label="-" :label-style="{textAlign: 'center', paddingLeft: '18px'}"
										  style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date4" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择结束时间'"
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
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery">查询</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					v-autoheight="120"
					ref="table"
					:data="table.data"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					:auto-load="false"
					:multiple="false"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleEdit(row)" label="update" type="primary"></fb-link>
							<fb-link :click="()=>handleTabEdit(row)" label="tab-update" type="primary"></fb-link>
							<fb-link :click="()=>handleDel(row)" label="delete" type="danger"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:view="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleView(row)" :label="row.entName" type="primary"></fb-link>
						</fb-link-group>
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
		},
		data() {
			return {
				// 请求的 url
				fileService: app.$svc.sys.file,
				formData: {
					userName: '',
					userGander: '',
					date: [null, null],
					date1: '',
					date2: '',
					date3: '',
					mindate3: '',
					maxdate3: '',
					date4: '',
					mindate4: '',
					maxdate4: '',
					startDate: '',
					endDate: '',
				},
				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.demo,

					primaryKey: "entId",
					columns: [
						{
							name: 'entId',
							hidden: true,
							width: 1,
						}, {
							name: 'attachId',
							hidden: true,
							width: 1,
						}, {
							name: 'entName',
							label: '企业名称',
							sortable: false,
							slot: 'view',
							align: 'left',
						}, {
							name: 'allNum',
							label: '数量',
							sortable: false,
							align: 'left',
							formatter: function (val, row) {
								val = val == 1 ? '男' : val
								return val;
							},
						}, {
							name: 'e4301Num',
							label: 'e4301Num',
							hidden: true,
							sortable: false,
							align: 'left',
						}, {
							name: 'e4302Num',
							label: 'e4302Num',
							sortable: false,
							align: 'left',
						}, {
							name: 'e4303Num',
							label: 'e4303Num',
							sortable: false,
							align: 'left',
						}, {
							name: 'e4304Num',
							label: 'e4304Num',
							sortable: false,
							align: 'left',
						}

					],
					data: [{
						"entId": "1364854930606653440",
						"entName": "浙江图讯科技股份有限公司",
						"allNum": 2,
						"e4301Num": 0,
						"e4302Num": 0,
						"e4303Num": 0,
						"e4304Num": 2
					}, {
						"entId": "1367430696464809985",
						"entName": "测试企业二已号",
						"allNum": 8,
						"e4301Num": 1,
						"e4302Num": 1,
						"e4303Num": 1,
						"e4304Num": 5
					}],
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
				this.$refs.table.doSearch()
			},
			// 列表界面表格的按钮控制
			handleTableSelect(row, selectedRows) {
				// this.buttons.del.disabled = selectedRows.length != 1
				// this.buttons.edit.disabled = selectedRows.length != 1
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

				this.$router.push({
					path: '/sys/demo/tree-list',
					query: {id: this.$router.encrypt(row.userId), tabLabel: row.userName}
				})
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

			startOnChangeDate(value) {

			},

			endOnChangeDate(value) {


			},
		}
	}
</script>

<style lang="less" scoped>
</style>
