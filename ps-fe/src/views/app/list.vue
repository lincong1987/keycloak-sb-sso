<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form ref="query-form" mode="query" :label-width="160">
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="应用名称">
								<fb-input v-model="formData.appName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="11" offset="1">
							<fb-form-item label="应用状态">
								<fb-tree-select v-model="formData.appStatus"
												:service="$svc.sys.dict.tree"
												:param="{'dicCode': 'APP_STATUS'}"
												:reader="{value: 'id', label: 'text'}"
												placeholder="请选择"
												clearable>
								</fb-tree-select>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="应用类型">
								<fb-tree-select v-model="formData.appType"
												:service="$svc.sys.dict.tree"
												:param="{'dicCode': 'APP_TYPE'}"
												:reader="{value: 'id', label: 'text'}"
												placeholder="请选择"
												clearable>
								</fb-tree-select>
							</fb-form-item>
						</fb-col>
						<fb-col span="11" offset="1">
							<fb-form-item label="创建时间">
								<tp-datepicker clearable
											   format="YYYY-MM-DD HH:mm:ss" v-model="formData.createDate"
											   value-format="YYYYMMDDHHmmss"></tp-datepicker>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission="'SYS_APPMANAGE_APP_ADD'"
						   icon="add-circle">新增
				</fb-button>
			</template>

			<template slot="actions">
				<fb-button @on-click="handleQuery" icon="search" type="primary">查 询</fb-button>
				<fb-button @on-click="formReset" icon="search">重 置</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					ref="table"
					:service="table.service"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					auto-load
					:multiple="false"
					:scroll="{x:1200, y: 372, autoHeight: true}"
					@on-row-select="handleTableSelect">
					<template v-slot:actions="props">
						<fb-space>
							<fb-button @on-click="handleEdit(props.row)" v-permission="'SYS_APPMANAGE_APP_UPDATE'"
									   editor size="s">修改</fb-button>
							<fb-button @on-click="handleDel(props.row)" v-permission="'SYS_APPMANAGE_APP_DELETE'"
									   danger size="s">删除</fb-button>
						</fb-space>
					</template>

					<template v-slot:view="props">
						<fb-link-group>
							<fb-link :click="()=>handleView(props.row)" :label="props.row.appName" type="primary"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:status="props">
						<fb-tag :type="getStatusType(props.row.appStatus)">{{ props.row.appStatusName }}</fb-tag>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-search>
		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
		<tp-dialog-tab ref="TpDialogTab" @closeTpDialog="closeDialogTab"></tp-dialog-tab>
	</div>
</template>

<script>

	export default {
		mixins: [

		],
		// 组件
		components: {
			// 'component-a': ComponentA,
		},
		// 初始化方法
		mounted() {
			// 执行界面初始化
			this.createdPage()
		},
		data() {
			return {
				formData: {
					appName: '',
					appStatus: '',
					appType: '',
					createDate: null,
				},
				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.app,

					primaryKey: "appId",
					columns: [
						{
							name: 'appName',
							label: '应用名称',
							slot: 'view',
							sortable: false,
							width: 200
						}, {
							name: 'appCode',
							label: '应用编码',
							sortable: false,
							width: 150
						}, {
							name: 'appTypeName',
							label: '应用类型',
							sortable: false,
							width: 120
						}, {
							name: 'appVersion',
							label: '版本号',
							sortable: false,
							width: 100
						}, {
							name: 'appUrl',
							label: '应用地址',
							sortable: false,
							width: 200
						}, {
							name: 'appStatusName',
							label: '状态',
							slot: 'status',
							sortable: false,
							width: 100
						}, {
							name: 'createUserName',
							label: '创建人',
							sortable: false,
							width: 100
						}, {
							name: 'createDateStr',
							label: '创建时间',
							sortable: false,
							width: 150
						}, {
							name: '',
							label: '操作',
							sortable: false,
							slot: 'actions',
							width: 124,
						}
					],
				},
			}
		},

		// 方法
		methods: {
			// 列表方法
			handleQuery() {
				this.$refs.table.doSearch()
			},
			// 重置表单
			formReset() {
				this.formData = {
					appName: '',
					appStatus: '',
					appType: '',
					createDate: null,
				};
				this.$refs.table.doSearch();
			},
			// 新增方法
			handleAdd() {
				let param = {};
				let options = {
					tabChangeConfirm: false,
					callBack: (result) => {
						this.$refs.table.doReload()
					}
				}
				this.$refs.TpDialog.show(import('./add.vue'), param, "新增应用", options);
			},
			// 修改方法
			handleEdit(row) {
				let param = {"id": row.appId, "passKey": row.passKey};
				let options = {
					tabChangeConfirm: false,
					callBack: (result) => {
						this.$refs.table.doReload()
					}
				}
				this.$refs.TpDialog.show(import('./add.vue'), param, "修改应用", options);
			},
			// 删除方法
			handleDel(row) {
				this.$confirm('确定要删除该应用吗？', () => {
					this.table.service.delete({"appId": row.appId, "passKey": row.passKey}).then((result) => {
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
				console.log("查看应用...");

				var id = row.appId
				var param = {"id": id, "passKey": row.passKey}

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show(import('./view.vue'), param, "查看应用", options);
			},
			// 表格选择
			handleTableSelect(selection, row) {
				console.log('选择行:', row);
			},
			// 获取状态类型
			getStatusType(status) {
				switch (status) {
					case '1':
						return 'success'; // 启用
					case '0':
						return 'danger'; // 禁用
					case '2':
						return 'warning'; // 维护中
					default:
						return 'default';
				}
			},
			// 页面初始化
			createdPage() {
				console.log('应用列表页面初始化');
			},
			// 关闭对话框
			closeDialog(param) {
				this.$refs.table.doReload()
			},
			closeDialogTab(param) {
				this.$refs.table.doReload()
			},
		}
	}
</script>

<style lang="less" scoped>

</style>