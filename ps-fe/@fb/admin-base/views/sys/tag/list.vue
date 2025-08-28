<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form ref="query-form" mode="query">
					<fb-row>
						<fb-col span="8">
							<fb-form-item label="标签名称">
								<fb-input v-model="formData.tagName"></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<fb-button ref="buttonAdd" @on-click="handleAdd" icon="add-circle">
					新增
				</fb-button>
			</template>

			<template slot="actions">
				<fb-button type="primary" icon="search" @on-click="handleQuery">查询</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					ref="table"
					:service="table.service.list"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.columns"
					:multiple="false"
					auto-load
					:formatters="formatters"
					:scroll="{x:1200, y: 330, autoHeight: true}"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="props">
						<fb-space>
							<fb-button @on-click="handleEdit(props.row)" 
									   editor size="s">修改</fb-button>
							<fb-button @on-click="handleDel(props.row)"  
									   danger size="s">删除</fb-button>
						</fb-space>
					</template>

					<template v-slot:view="props">
						<fb-link-group>
							<fb-link :click="()=>handleView(props.row)" :label="props.row.tagName" type="primary"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:tagColor="props">
						<fb-text radius="4px" padding="0px 4px" :bg="props.row.tagColor" color="#fff">
							{{ props.row.tagName }}
						</fb-text>
					</template>

					<template v-slot:isValid="props">
						<span :class="props.row.isValid === 1 ? 'status-valid' : 'status-invalid'">
							{{ props.row.isValid === 1 ? '有效' : '无效' }}
						</span>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-search>

		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
	</div>
</template>

<script>
	export default {
		name: 'list',
		mixins: [],

		// 初始化方法
		mounted() {
			// 执行界面初始化方法
		},
		data() {
			return {
				formData: {
					tagName: '',
				},

				formatters: {
					isValid(val) {
						return val === 1 ? '有效' : '无效';
					}
				},

				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.tag,
					primaryKey: "tagId",
					columns: [
						{
							name: 'tagName',
							label: '标签名称',
							slot: 'view',
							sortable: false,
							width: 150,
						}, {
							name: 'tagColor',
							label: '标签颜色',
							slot: 'tagColor',
							sortable: false,
							width: 100,
						}, {
							name: 'tagDesc',
							label: '标签描述',
							sortable: false,
							width: 200,
						}, {
							name: 'orderIndex',
							label: '排序',
							sortable: false,
							width: 80,
						}, {
							name: 'isValid',
							label: '状态',
							slot: 'isValid',
							sortable: false,
							width: 80,
						}, {
							name: 'createTime',
							label: '创建时间',
							sortable: false,
							width: 150,
						},
						{
							freeze: "right",
							name: '',
							label: '操作',
							sortable: false,
							slot: 'actions',
							width: 150,
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
				let param = {};
				let options = {"height": 400};
				// 打开新增界面弹出窗
				this.$refs.TpDialog.show(import('./add.vue'), param, "新增标签", options);
			},
			// 修改方法
			handleEdit(row) {
				let param = {"id": row.tagId, "passKey": row.passKey};
				let options = {"height": 400};

				this.$refs.TpDialog.show(import('./add.vue'), param, "修改标签", options);
			},
			// 删除方法
			handleDel(row) {
				this.$confirm('确定要删除该标签吗？', () => {
					this.delete(row.tagId, row.passKey);
				})
			},
			delete(tagId, passKey) {
				// 真正执行删除操作
				app.service.request('/sys/tag/delete', {
					method: 'get',
					params: {"tagId": tagId, "passKey": passKey},
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					responseType: 'json',
					timeout: 5000,
				}).then((result) => {
					if (result.code == 1) {
						this.$message.success('删除成功');
						this.handleQuery();
					} else {
						// 服务器返回失败
						this.$message.error('删除失败: ' + result.message)
					}
				})
			},

			// 查看方法
			handleView(row) {
				let param = {"id": row.tagId, "passKey": row.passKey}
				let options = {"height": 400};
				this.$refs.TpDialog.show(import('./view.vue'), param, "查看标签", options);
			},
			// 下拉回调
			onSelectChange(e) {
				console.log("下拉选择：" + e);
			},
			closeDialog(param) {
				this.handleQuery();
			},
			handleTableSelect(row) {
				// 表格行选择事件
			}
		}
	}
</script>

<style lang="less" scoped>
	.status-valid {
		color: #52c41a;
		font-weight: bold;
	}

	.status-invalid {
		color: #ff4d4f;
		font-weight: bold;
	}
</style>