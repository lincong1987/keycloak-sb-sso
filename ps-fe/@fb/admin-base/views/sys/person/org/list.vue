<template>
	<div>
		<fb-page-tree-table title="机构树">
			<template slot="tree">
				<fb-tree
					style="overflow: auto"
					v-autoheight="152"
					ref="deptTree"
					:data="deptData"
					:reader="{value: 'id', label: 'text'}"
					:load-data="loadDeptTreeData"
					@on-select-change="handleSelectChange"></fb-tree>

<!--	同步树			<fb-tree-->
<!--					v-autoheight="152"-->
<!--					ref="tree"-->
<!--					:service="$svc.sys.dept.org.tree"-->
<!--					:param="{deptId: '', sync: 1}"-->
<!--					:reader="{value:'id', label: 'text'}"-->
<!--					@on-select-change="handleSelectChange"-->
<!--					@on-data-load="callBack"></fb-tree>-->
			</template>
			<template slot="tree-actions">
				<fb-button :icon="treeExpand ? 'tree-expansion': 'tree-closed'"
						   @on-click="handleTreeExpand"></fb-button>
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
				<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission="'SYS_ORG_PERSON_ADD'" icon="add-circle"
						   :disabled="selectNode.deptId === ''">
					新增
				</fb-button>
				<fb-button ref="buttonExport" @on-click="handleExportExcel"  icon="download"
						   :disabled="selectNode.deptId === ''" :loading="exportLoading">
					导出Excel {{ table.selectRow && table.selectRow.length > 0 ? '（' + table.selectRow.length + '）' : '' }}
				</fb-button>
<!--				<fb-button ref="buttonDel" @on-click="handleDels" v-permission="'SYS_ORG_PERSON_DELETE'"-->
<!--						   icon="reduce-circle" :disabled="table.selectRow.length === 0">-->
<!--					批量删除-->
<!--				</fb-button>-->
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery" :disabled="selectNode.deptId == ''">
					查询
				</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					ref="table"
					v-model="table.selectRow"
					:service="table.service.org.list"
					:param="formData"
					:pk="'personId'"
					:columns="table.columns"
					:auto-load="false"
					multiple
					autoSelect
					:scroll="{x:1000, y: 368, autoHeight: true}"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="props">
						<fb-space>
							<fb-button @on-click="handleEdit(props.row)" v-permission="'SYS_ORG_PERSON_UPDATE'" v-if="props.row.defaultDept == 1"
									   editor size="s">修改</fb-button>
							<fb-button @on-click="handleAccount(props.row)" v-permission="'SYS_ORG_PERSON_ACCOUNT'"
									   editor size="s">账号</fb-button>
							<fb-button @on-click="handleAuth(props.row)" v-permission="'SYS_ORG_PERSON_AUTHADD'"
									   editor size="s">授权</fb-button>
							<fb-button @on-click="handleParttimejob(props.row)" v-permission="'SYS_ORG_PERSON_PARTTIMEADD'" v-if="props.row.defaultDept == 1"
									   editor size="s">兼职</fb-button>
							<fb-button @on-click="handleDataPermission(props.row)"  v-permission="'SYS_ORG_PERSON_DATA_PERM'"
									   editor size="s">数据权限</fb-button>
							<fb-button @on-click="handleDel(props.row)" v-permission="'SYS_ORG_PERSON_DELETE'"
									   danger size="s">删除</fb-button>

						</fb-space>
					</template>

					<template v-slot:view="props">
						<fb-link-group>
							<fb-link :click="()=>handleView(props.row)" :label="props.row.personName" type="primary"></fb-link>
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


	export default {
		name: 'list',
		mixins: [

		],
		created() {

		},
		// 初始化方法
		mounted() {
			// 执行界面初始化方法
			this.initDeptTreeData('', '');
		},
		data() {
			return {
				// 部门树加载数据
				deptData: [],
				treeExpand: true,
				// 导出Excel加载状态
				exportLoading: false,
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
				service_dept: this.$svc.sys.dept,
				// Table列
				table: {
					// 请求的 url
					selectRow: [],
					service: app.$svc.sys.person,
					primaryKey: "id",
					columns: [
						{
							name: 'personName',
							label: '用户名称',
							sortable: false,
							slot: 'view',
							width: 120
						}, {
							name: 'ascnName',
							label: '单位名称',
							sortable: false,
							width: 120
						}, {
							name: 'deptSimpleName',
							label: '部门名称',
							sortable: false,
							width: 120
						}, {
							name: 'defaultDeptName',
							label: '是否兼职',
							sortable: false,
							width: 100
						}, {
							name: 'personNo',
							label: '人员编号',
							sortable: false,
							width: 100
						}, {
							name: 'sexName',
							label: '性别',
							sortable: false,
						},
						{
							name: 'phone',
							label: '手机号码',
							sortable: false,
							width: 120
						},
						{
							name: '',
							label: '操作',
							sortable: false,
							slot: 'actions',
							width: 370,
							freeze: 'right'
						},
					],
				},
			}
		},

		// 方法
		methods: {

			// 初始化树数据
			initDeptTreeData(deptId, selectDeptId) {
				this.service_dept.org.tree({deptId: deptId, 'sync': 0}).then((result) => {
					if (result.code == 1) {
						if (result.data.length > 0) {
							this.deptData = result.data;
							// 默认选中根节点
							this.$nextTick(() => {
								if (selectDeptId) {
									this.$refs.deptTree.selectNodeByValue(selectDeptId);
								} else {
									this.$refs.deptTree.selectNodeByValue(result.data[0].id);
								}
							})
						}
					} else {
						// 服务器返回失败
						this.$message.error('部门树加载失败' + result.message)
					}
				})
			},

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
					url: import('../../../../views/sys/person/org/add-basicinfo.vue'),
					label: '人员基本信息',
					icon: "chart-line"
				},
					{
						url: import('../../../../views/sys/person/org/add-exinfo.vue'),
						label: '人员扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "新增", {callBack: ()=>{ } });
			},
			// 修改方法
			handleEdit(row) {

				let param = {"id": row.personId, "deptId": row.deptId, "passKey": row.passKey};
				let tabArry = [{
					url: import('../../../../views/sys/person/org/add-basicinfo.vue'),
					label: '人员基本信息',
					icon: "chart-line"
				},
					{
						url: import('../../../../views/sys/person/org/add-exinfo.vue'),
						label: '人员扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "修改", {callBack: ()=>{

				} });
			},
			// 删除方法
			handleDel(row) {
				this.delete(row.deptId, row.personId, row.passKey);
			},
			// 执行删除动作
			delete(deptIds, personIds, passKey) {
				this.$confirm('确定要删除吗？', () => {
					app.$svc.sys.person.delete({"deptIds": deptIds, "personIds": personIds, "passKey": passKey}).then((result) => {
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
				let param = {"id": row.personId, "deptId": row.deptId, "passKey": row.passKey};
				this.$refs.TpDialog.show(import('../../../../views/sys/person/org/view.vue'), param, "查看");
			},
			// 授权角色
			handleAuth(row) {
				let param = {"id": row.personId, "deptId": row.deptId, "passKey": row.passKey};
				this.$refs.TpDialog.show(import('../../../../views/sys/person/org/auth.vue'), param, "授权");
			},
			// 兼职部门
			handleParttimejob(row) {
				let param = {"id": row.personId, "passKey": row.passKey};
				this.$refs.TpDialog.show(import('../../../../views/sys/person/org/parttime-job.vue'), param, "兼职部门");
			},

			// 开通账号
			handleAccount(row) {
				let param = {"id": row.personId, "passKey": row.passKey};
				this.$refs.TpDialog.show(import('../../../../views/sys/person/org/account-add.vue'), param, "账号");
			},

			// 数据权限
			handleDataPermission(row) {
				let param = {"personId": row.personId, "deptId": row.deptId, "passKey": row.passKey};
				this.$refs.TpDialog.show(import('../../../../views/sys/person/org/data-perm.vue'), param, "数据权限");
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

				this.handleQuery();
			},

			handleTreeExpand() {
				this.treeExpand = !this.treeExpand
				this.$refs.tree.expandAll(this.treeExpand)
			},
			// 导出Excel方法
			handleExportExcel() {

 
				if (!this.selectNode.deptId) {
					this.$message.error('请选择部门导出人员信息');
					return;
				}

				this.exportLoading = true;
				
				// 构建导出参数
				const exportParams = {
					...this.formData
				};
				
				// 如果有选中的用户，则只导出选中的用户
				if (this.table.selectRow && this.table.selectRow.length > 0) {
					exportParams.selectedUserIds = this.table.selectRow 
					this.$message.info(`将导出选中的 ${this.table.selectRow.length} 个用户`);
				} else {
					this.$message.info('将导出当前部门的所有用户');
				}

				// 调用导出API
				app.$svc.sys.person.exportExcel(exportParams).then((response) => {
					// 创建下载链接
					const blob = new Blob([response.data], {
						type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					});
blob.text().then(text => {

					console.log(' excel text',text);

				});
					 
					const url = window.URL.createObjectURL(blob);
					const link = document.createElement('a');
					link.href = url;

				 
					
					// 从响应头获取文件名，如果没有则使用默认名称
					let contentDisposition = '';
					let fileName = '人员信息.xlsx';
					if (response.headers && response.headers['content-disposition']) {
						contentDisposition = response.headers['content-disposition'];
					}
					if (contentDisposition) {
						const fileNameMatch = contentDisposition.match(/filename=(.+)/);
						if (fileNameMatch) {
							fileName = decodeURIComponent(fileNameMatch[1]);
						}
					}
					
					link.download = fileName;
					document.body.appendChild(link);
					link.click();
					document.body.removeChild(link);
					window.URL.revokeObjectURL(url);
					
					this.$message.success('导出成功');
				}).catch((error) => {
					console.error('导出失败:', error);
					this.$message.error('导出失败，请稍后重试');
				}).finally(() => {
					this.exportLoading = false;
				});
			}
		}
	}
</script>

<style lang="less" scoped>

</style>
