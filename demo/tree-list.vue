<template>
	<div>
		<fb-page-tree-table title="树">
			<template slot="tree">
				<fb-tree
					v-autoheight="152"
					ref="tree"
					:service="$svc.sys.dept.org.tree"
					:param="{deptId: '', sync: 1}"
					:reader="{value:'id', label: 'text'}"
					@on-select-change="handleSelectChange"
					@on-data-load="callBack"></fb-tree>
			</template>
			<template slot="query">

				<fb-form ref="query-form" mode="query">
					<fb-row>
						<fb-col span="8">
							<fb-form-item label="用户名">
								<fb-input v-model="formData.personName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="8">
							<fb-form-item label="手机号码">
								<fb-input v-model="formData.phone"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="8">
							<fb-form-item label="性别">
								<fb-select v-model="formData.sex"
										   :data="defaultForm.sex"
										   :placeholder="'请选择'"
										   clearable/>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<fb-button ref="buttonAdd" @on-click="handleAdd"  icon="add-circle"
						   :disabled="selectNode.deptId === ''">
					新增
				</fb-button>
				<fb-button ref="buttonDel" @on-click="handleDels"
						   icon="reduce-circle" :disabled="buttons.del.disabled">
					批量删除
				</fb-button>
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery" :disabled="selectNode.deptId == ''">
					查询
				</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					v-autoheight="152"
					ref="table"
					:service="table.service.org.list"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					:auto-load="false"
					:multiple="true"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleEdit(row)" v-permission="'SYS_ORG_PERSON_UPDATE'" label="修改"
									 type="primary"></fb-link>
							<fb-link :click="()=>handleDel(row)" v-permission="'SYS_ORG_PERSON_DELETE'" label="删除"
									 type="danger"></fb-link>
							<fb-link :click="()=>handleAuth(row)" v-permission="'SYS_ORG_PERSON_AUTHADD'" label="授权"
									 type="primary"></fb-link>
							<fb-link :click="()=>handleParttimejob(row)" v-permission="'SYS_ORG_PERSON_PARTTIMEADD'"
									 label="兼职" type="primary"></fb-link>
							<fb-link :click="()=>handleAccount(row)" v-permission="'SYS_ORG_PERSON_ACCOUNT'" label="账号"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>
					<template v-slot:view="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleView(row)" v-permission="'SYS_ORG_PERSON_VIEW'" :label="row.personName"
									 type="primary"></fb-link>
						</fb-link-group>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-tree-table>

		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
		<tp-dialog-tab ref="TpDialogTab" @closeTpDialog="closeDialogTab"></tp-dialog-tab>
	</div>
</template>

<script>
	import Page from '@/util/Page'
	import TpDialog from '@/components/tp-dialog/tp-dialog.vue'
	import TpDialogTab from '@/components/tp-dialog-tab/tp-dialog-tab.vue'

	export default {
		name: 'list',
		mixins: [
			Page
		],
		// 组件
		components: {
			// 'component-a': ComponentA,
			TpDialog,
			TpDialogTab,
		},
		created() {

		},
		// 初始化方法
		mounted() {
			// 执行界面初始化方法
			let param = this.getRouteParam();

			if (param.path === '/sys/demo/tree-list') { // 防止 keep-alive watch下多次触发

				this.$message.success(`我的id:${param.id}，名字：${param.tabLabel}`)
			}
		},
		data() {
			return {
				// 选中的节点
				selectNode: {
					deptId: '',
					ascnId: '',
				},
				// 列表查询参数
				formData: {
					deptId: '',
					// 0：默认，本级   1：本级及下级（需要根据层级code模糊查询）
					levelFlag: 0,
					deptLevelcode: '',
					personName: '',
					sex: '',
					phone: '',
					office: '',
					ascnId: '',
				},
				buttons: {
					auth: {
						disabled: true,
					},
					parttimejob: {
						disabled: true,
					},
					account: {
						disabled: true,
					},
				},
				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.person,
					primaryKey: "personId",
					columns: [
						{
							name: 'personId',
							hidden: true,
							width: 1,
						}, {
							name: 'deptId',
							hidden: true,
							width: 1,
						}, {
							name: 'personName',
							label: '用户名称',
							slot: 'view',
							sortable: false,
							align: 'left',
						}, {
							name: 'deptSimpleName',
							label: '部门名称',
							sortable: false,
							align: 'left',
						}, {
							name: 'personNo',
							label: '人员编号',
							sortable: false,
							align: 'left',
						}, {
							name: 'sexName',
							label: '性别',
							sortable: false,
							align: 'left',
						},
						{
							name: 'phone',
							label: '手机号码',
							sortable: false,
							align: 'left',
						},
						{
							name: '',
							label: '操作',
							sortable: false,
							align: 'right',
							slot: 'actions',
							width: '30%'
						},
					],
				},
			}
		},

		// 方法
		methods: {
			// 列表方法
			handleQuery() {
				// 列表查询
				if (this.selectNode.deptId !== '') {
					this.$refs.table.doSearch()
				}
			},
			// 列表界面表格的按钮控制
			handleTableSelect(row, selectedRows) {
				this.buttons.del.disabled = selectedRows.length == 0
				this.buttons.edit.disabled = selectedRows.length != 1
				this.buttons.auth.disabled = selectedRows.length != 1
				this.buttons.parttimejob.disabled = selectedRows.length != 1
				this.buttons.account.disabled = selectedRows.length != 1
			},
			// 新增方法
			handleAdd() {
				if (!this.selectNode.deptId) {
					this.$message.error('请选择部门添加人员')
					return
				}

				// 界面跳转
				let param = {"id": "", "deptId": this.selectNode.deptId, "ascnId": this.selectNode.ascnId};
				let tabArry = [{
					url: '/sys/person/org/add-basicinfo.vue',
					label: '人员基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/person/org/add-exinfo.vue',
						label: '人员扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "新增");
			},
			// 修改方法
			handleEdit(row) {
				let param = {"id": row.personId, "deptId": row.deptId};
				let tabArry = [{
					url: '/sys/person/org/add-basicinfo.vue',
					label: '人员基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/person/org/add-exinfo.vue',
						label: '人员扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "修改");
			},
			// 删除方法
			handleDel(row) {
				this.delete(row.personId);
			},
			handleDels() {
				let personIds = '';
				let rows = this.$refs.table.getSelectedRows()

				if (rows.length == 0) {
					this.$message.error('请先选择一条记录！')
					return;
				} else {
					rows.forEach(function (r) {
						personIds += "," + r.personId;
					});
				}
				this.delete(personIds);
			},
			// 执行删除动作
			delete(personIds) {
				this.$confirm('确定要删除吗？', () => {
					app.$svc.sys.person.delete(personIds).then((result) => {
						// 判断code
						if (result.code == 1) {
							this.handleQuery();
							this.$message.success('删除成功');
						} else {
							// 服务器返回失败
							this.$message.error('删除失败')
						}
					}).catch((err) => {
						// 服务器返回失败
						console.log(err);
					})
				})
			},

			// 查看方法
			handleView(row) {
				let param = {"id": row.personId, "deptId": row.deptId};

				this.$refs.TpDialog.show("/sys/person/org/view.vue", param, "查看");
			},
			// 授权角色
			handleAuth(row) {
				let param = {"id": row.personId, "deptId": row.deptId};
				this.$refs.TpDialog.show("/sys/person/org/auth.vue", param, "授权");
			},
			// 兼职部门
			handleParttimejob(row) {
				let param = {"id": row.personId};
				this.$refs.TpDialog.show("/sys/person/org/parttime-job.vue", param, "兼职部门");
			},
			// 开通账号
			handleAccount(row) {
				let param = {"id": row.personId};
				this.$refs.TpDialog.show("/sys/person/org/account-add.vue", param, "账号");
			},
			callBack(node) {
				// 树的回调方法, 默认选中根节点
				this.$nextTick(() => {
					if (node.data.length > 0) {
						// 默认选择第一个根节点的根node
						this.$refs.tree.selectNodeByValue(node.data[0].id);
					}
				})

				// 默认查询条件为所有根节点本级及下级的数据
				// this.formData.levelFlag = 1;
				// 查询根节点本级的列表数据
				// this.handleQuery();

			},
			handleSelectChange(node) {
				if (node) {
					// 选中那个节点，查询哪个节点本级的数据
					// this.formData.levelFlag = 0
					this.formData.deptId = node.value
					this.formData.ascnId = node.extend01
					this.selectNode.deptId = node.value
					this.selectNode.ascnId = node.extend01
					this.handleQuery();
				} else {
					// this.formData.levelFlag = 1;
					// 选中节点置空
					this.selectNode.deptId = ''
					this.selectNode.ascnId = ''
					// 没有选中的节点，查询数据为所有根节点下本级及下级的全部数据
					this.formData.deptId = ''
					this.formData.ascnId = ''
				}
			},
			closeDialog(param) {
				console.log(param);
			},
			closeDialogTab(param) {
				if (param) {
					this.handleQuery();
				}
			}
		}
	}
</script>

<style lang="less" scoped>

</style>
