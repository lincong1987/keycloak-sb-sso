<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form ref="query-form" mode="query" :label-width="160">
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="姓名">
								<fb-input v-model="formData.name"></fb-input>
							</fb-form-item>
							<fb-form-item label="年龄">
								<fb-input v-model="formData.age"></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<!--<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission = "'SYS_ENTMANAGE_ENT_ADD'" icon="add-circle">add</fb-button> v-permission指令权限控制-->
				<fb-button ref="buttonAdd" @on-click="handleAdd" icon="add-circle">新增</fb-button>
				<fb-button ref="buttonImport" @on-click="handleImport" icon="add-circle">导入</fb-button>
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery">查询</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					ref="table"
					:service="table.leaveService.agencySearch"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					:multiple="false"
					:scroll="{x:1100, y: 372, autoHeight: true}"
					auto-load>

					<template v-slot:actions="props">
						<fb-space>
							<fb-button @on-click="handleApproval(props.row)" v-permission="''" editor size="s">审批
							</fb-button>
							<fb-button @on-click="handleDel(props.row)" v-permission="''" danger size="s">删除</fb-button>
						</fb-space>
					</template>

					<template v-slot:view="props">
						<fb-link-group>
							<fb-link :click="()=>handleView(props.row)" :label="props.row.levType"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-search>
		<tp-dialog ref="TpDialog"></tp-dialog>
		<tp-dialog-tab ref="TpDialogTab"></tp-dialog-tab>
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
				formData: {
					name: '',
					age: '',
				},
				// Table列
				table: {
					// 请求的 url
					leaveService: app.$svc.sys.demo.tpwftestleave,

					service: app.$svc.sys.demo.tpwftestleaveappr,

					columns: [
						{
							name: 'levType',
							label: '请假类型',
							sortable: false,
							width: 150,
							slot: 'view',
						},
						{
							name: 'nodeName',
							label: '审批节点',
							sortable: false,
							width: 150,
						},
						{
							name: 'creator',
							label: '申请人',
							sortable: false,
							width: 150,
						},
						{
							name: 'createTime',
							label: '申请时间',
							sortable: false,
							width: 150,
						},
						{
							name: '',
							label: '操作',
							sortable: false,
							slot: 'actions',
							width: 124,
						},
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
			// 新增方法
			handleAdd() {
				let param = {}
				let that = this;

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
						that.$refs.table.doReload()
					}
				}

				this.$refs.TpDialog.show('/demo/tpwftestleaveappr/add.vue', param, "新增", options);
			},

			handleImport() {
				let param = {};
				let that = this;

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
					}
				}

				this.$refs.TpDialog.show('/demo/tpwftestleaveappr/import.vue', param, "导入", options);
			},

			// 修改方法
			handleApproval(row) {
				let param = {"id": row.appId};
				let that = this;

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
						that.$refs.table.doReload()
					}
				}

				this.$refs.TpDialog.show('/demo/tpwftestleaveappr/add.vue', param, "修改", options);
			},
			// 删除方法
			handleDel(row) {
				this.$confirm('确定要删除吗？', () => {
					this.table.service.delete({"appIds": row.appId}).then((result) => {
						if (result.code == 1) {
							this.$message.success('删除成功');
							this.doReload();
						} else {
							// 服务器返回失败
							this.$message.error('删除失败: ' + result.message)
						}
					});
				})
			},
			// 查看方法
			handleView(row) {
				let param = {"id": row.levId, "taskId": row.taskId}

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}

				this.$refs.TpDialog.show('/sys/demo/tpwftestleaveappr/appr.vue', param, "审批", options);
			},
		}
	}
</script>

<style lang="less" scoped>
</style>
