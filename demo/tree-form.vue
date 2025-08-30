<template>
	<div>

		<!--
		<fb-tree
		ref="deptTree"
		:service="$svc.sys.dept.org.tree"
		:param="{pdeptId: '', deptId: ''}"
		:reader="{value: 'id', label: 'text'}"
		@on-select-change="handleSelectChange"
		@on-data-load="callBack"></fb-tree>
		-->
		<fb-page-tree title="树">
			<template slot="tree">
				<fb-tree
					style="overflow: auto"
					v-autoheight="152"
					ref="tree"
					:data="treeData"
					:reader="{value: 'id', label: 'text'}"
					@on-select-change="handleSelectChange"></fb-tree>
			</template>

			<template slot="actions">
				<fb-button icon="node" @on-click="addRoot" v-if="deptParam.deptId === '' && treeData.length == 0">添加顶级
				</fb-button>
				<fb-button icon="node" @on-click="addCurrent" v-permission="'SYS_ORG_DEPT_ADD'"
						   :disabled="deptParam.deptId === ''">添加同级
				</fb-button>
				<fb-button icon="node" @on-click="addChild" v-permission="'SYS_ORG_DEPT_ADD'"
						   :disabled="deptParam.deptId === ''">添加下级
				</fb-button>
				<fb-button icon="editor-square" @on-click="update" v-permission="'SYS_ORG_DEPT_UPDATE'"
						   :disabled="deptParam.deptId === ''">修改
				</fb-button>
				<fb-button icon="delete" @on-click="delChild" v-permission="'SYS_ORG_DEPT_DELETE'" danger
						   :disabled="deptParam.deptId === ''">删除本级
				</fb-button>
			</template>
			<template slot="form">
				<fb-form ref="fbform" v-autoheight="152" :label-width="120">
					<fb-fieldset label="机构基本信息"/>
					<fb-row>
						<fb-col span="12">
							<fb-form-item label="机构全称">
								<fb-input v-model="formData.deptFullName" readonly></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="机构简称">
								<fb-input v-model="formData.deptSimpleName" type="text" readonly></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="12">
							<fb-form-item label="机构编号">
								<fb-input v-model="formData.deptNo" readonly></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="机构类型">
								<fb-select v-model="formData.deptType" :service="$svc.sys.dict.select"
										   :param="{'pdicCode': 'SYS05'}" readonly/>
							</fb-form-item>
						</fb-col>
					</fb-row>

					<fb-row>
						<fb-col span="12">
							<fb-form-item label="机构联系人">
								<fb-input type="text" v-model="formData.principalName" readonly></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="机构联系电话">
								<fb-input type="text" v-model="formData.principalTel" readonly></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>

					<fb-row>
						<fb-col span="12">
							<fb-form-item label="排序">
								<fb-input v-model.number="formData.orderIndex" type="text" readonly></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="行政区划">
								<fb-tree-select
									ref="cityTree"
									v-model="formData.cityCode"
									:service="$svc.sys.city.tree"
									:param="{'sync': 1, 'expand': true, 'cityId': '-1'}"
									:reader="{value:'extend02', label: 'text'}"
									readonly>
								</fb-tree-select>
							</fb-form-item>
						</fb-col>
					</fb-row>

					<fb-row>
						<fb-col span="12">
							<fb-form-item label="是否启用">
								<fb-radio-group v-model="formData.enabled"
												:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
												:reader="{label:'name', value:'id'}" disabled></fb-radio-group>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="24">
							<fb-form-item label="机构描述">
								<fb-textarea rows="2" v-model="formData.deptDesc" type="text"
											 readonly></fb-textarea>
							</fb-form-item>
						</fb-col>
					</fb-row>


					<fb-fieldset label="机构扩展信息"/>
					<fb-row>
						<fb-col span="12">
							<fb-form-item label="经度">
								<fb-input v-model="expFormData.longitude" readonly></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="经度">
								<fb-input v-model="expFormData.latitude" readonly></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>

					<fb-row>
						<fb-col span="24">
							<fb-form-item label="地址">
								<fb-input v-model="expFormData.address" readonly>
								</fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>

					<fb-row>
						<fb-col span="12">
							<fb-form-item label="条线">
								<fb-select v-model="expFormData.lineCode"
										   :service="$svc.sys.dict.select"
										   :param="{'pdicCode': 'SYS24'}"
										   readonly/>
							</fb-form-item>
						</fb-col>
						<fb-col span="12">
							<fb-form-item label="Email">
								<fb-input
									v-model="expFormData.email"
									readonly>
								</fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>

				</fb-form>
			</template>
		</fb-page-tree>
		<tp-dialog-tab ref="TpDialogTab" @closeTpDialog="closeDialogTab"></tp-dialog-tab>
	</div>
</template>

<script>
	import Page from '@/util/Page'
	import TpDialogTab from '@/components/tp-dialog-tab/tp-dialog-tab.vue'

	export default {
		name: 'tree',
		mixins: [
			Page
		],
		// 组件
		components: {
			TpDialogTab,
		},
		// 初始化方法
		mounted() {
			// 执行界面初始化方法
			this.initDeptTreeData('', '');
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.dept,
				selectNode: {
					children: false,
				},
				// 部门树参数
				deptParam: {
					pdeptId: '',
					deptId: '',
				},
				// 部门树加载数据
				treeData: [],
				// 部门详情参数
				formData: {
					deptId: '',
					pdeptId: '',
					deptLevelcode: '',
					deptNo: '',
					deptFullName: '',
					deptSimpleName: '',
					deptType: '',
					deptDesc: '',
					category: '',
					principalName: '',
					principalTel: '',
					orderIndex: '',
					enabled: '',
					ascnId: '',
					cityCode: '',
				},
				expFormData: {
					supdeptId: '',
					longitude: '',
					latitude: '',
					address: '',
					email: '',
					direinDustryCode: '',
					lineCode: ''
				},
			}
		},

		// 方法
		methods: {
			// 初始化树数据
			initDeptTreeData(deptId, selectDeptId) {
				this.service.org.tree({deptId: deptId}).then((result) => {
					if (result.code == 1) {
						if (result.data.length > 0) {
							this.treeData = result.data;
							// 默认选中根节点
							this.$nextTick(() => {
								if (selectDeptId) {
									this.$refs.tree.selectNodeByValue(selectDeptId);
								} else {
									this.$refs.tree.selectNodeByValue(result.data[0].id);
								}
							})
						}
					} else {
						// 服务器返回失败
						this.$message.error('部门树加载失败' + result.message)
					}
				})
			},

			// 添加顶级部门
			addRoot() {
				// 不存在任何机构的情况下，添加第一个顶级部门
				this.showAddPage('', '1111111111111111111', '');
			},

			// 新增同级方法
			addCurrent() {
				// 界面跳转
				if (!this.deptParam.pdeptId) {
					this.$message.error('请选择部门')
					return
				}
				this.showAddPage('', this.deptParam.pdeptId, this.formData.ascnId);
			},
			// 新增下级
			addChild() {
				// 界面跳转
				if (!this.deptParam.deptId) {
					this.$message.error('请选择部门')
					return
				}
				this.showAddPage('', this.deptParam.deptId, this.formData.ascnId);
			},

			showAddPage(id, pdeptId, ascnId) {
				var param = {"id": id, "pdeptId": pdeptId, "ascnId": ascnId};
				let tabArry = [{
					url: '/sys/dept/org/add-basicinfo.vue',
					label: '部门基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/dept/org/add-exinfo.vue',
						label: '部门扩展信息',
						icon: "progressbar"
					}];
				this.$refs.TpDialogTab.show(tabArry, param, "新增");
			},

			// 修改
			update() {
				if (!this.deptParam.deptId) {
					this.$message.error('请选择修改部门')
					return
				}
				// 界面跳转
				var param = {"id": this.deptParam.deptId};
				let tabArry = [{
					url: '/sys/dept/org/add-basicinfo.vue',
					label: '部门基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/dept/org/add-exinfo.vue',
						label: '部门扩展信息',
						icon: "progressbar"
					}];
				this.$refs.TpDialogTab.show(tabArry, param, "修改");
			},
			// 删除方法
			delChild() {
				if (!this.deptParam.deptId) {
					this.$message.error('请先选择一条记录！')
				} else {
					if (this.selectNode.children) {
						this.$message.error('所选部门有子部门，不能删除！请先删除子部门。');
						return;
					}
					this.service.selectBindByDeptId({"deptId": this.deptParam.deptId}).then((result) => {
						if (result.code == 1) {
							if (result.data != 0) {
								this.$message.error('该部门下有人员挂职，请先将人员调置其他部门再进行删除！');
								return;
							} else {
								this.$confirm('确定要删除吗？', () => {
									// 删除
									this.service.delete(this.formData).then((result) => {
										if (result.code == 1) {
											this.$message.success('删除成功');
											// 1、刷新树
											this.initDeptTreeData('', '');
										} else {
											this.$message.error('删除失败：' + result.message)
										}
									})
								})
							}
						} else {
							// 服务器返回失败
							this.$message.error('判断部门下是否有人要挂职失败' + result.data.message)
						}
					})
				}
			},

			// 新增 / 修改 回调方法
			handleQuery(deptId) {
				// 1、刷新树
				this.initDeptTreeData('', deptId);
				// 2、查询新增 / 修改 的部门信息
				this.handleView(deptId);
			},
			// 查看方法
			handleView(deptId) {
				let that = this;

				// 清空扩展信息表单
				for (let key in that.expFormData) {
					that.expFormData[key] = '';
				}

				this.service.view({"deptId": deptId}).then((result) => {
					// 判断code
					if (result.code == 1) {
						that.formData = result.data
					} else {
						// 服务器返回失败
						that.$message.error('查询失败')
					}
				})

				this.service.expView({"deptId": deptId}).then((result) => {
					// 判断code
					if (result.code == 1) {
						if (result.data) {
							that.expFormData = result.data
						}
					} else {
						// 服务器返回失败
						that.$message.error('查询失败')
					}
				})
			},

			handleSelectChange(node) {
				if (node) {
					this.deptParam.deptId = node.id
					this.deptParam.pdeptId = node.pid
					if (node.children.length !== 0) {
						this.selectNode.children = true;
					} else {
						this.selectNode.children = false;
					}
					this.handleView(node.id);
				} else {
					this.deptParam.deptId = ''
					this.deptParam.pdeptId = ''
				}
			},

			closeDialogTab(param) {
				// 新增/修改回来，树默认选中新增/修改的数据
				if (param) {
					this.handleQuery(param)
				}
			},

		}
	}
</script>

<style lang="less" scoped>

</style>
