<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form :label-width="160" mode="query" ref="query-form">
					<fb-row>
						<fb-col offset="1" span="11">
							<fb-form-item label="操作类型">
								<fb-tree-select :data="defaultData.operationTypeData"
												clearable
												placeholder="请选择"
												v-model="formData.operationType">
								</fb-tree-select>
							</fb-form-item>
						</fb-col>
						<fb-col offset="1" span="11">
							<fb-form-item label="操作用户ID">
								<fb-input v-model="formData.operatorId" placeholder="请输入操作用户ID"></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col offset="1" span="11">
							<fb-form-item label="版本号">
								<fb-input v-model="formData.version" placeholder="请输入版本号"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col offset="1" span="11">
							<fb-form-item :content-style="{ }" label="操作时间"
										  prop="formData.operationTimeStart" style="display: inline-block; width: 56%">
								<tp-datepicker :maxDate="maxDateForStart" clearable
							   format="YYYY-MM-DD HH:mm:ss" v-model="formData.operationTimeStart" value-format="YYYYMMDDHHmmss"></tp-datepicker>
							</fb-form-item>
							<fb-form-item :content-style="{marginLeft: '30px'}" :label-style="{width:'30px', textAlign: 'center', paddingLeft: '5px'}"
										  label="-"
										  prop="formData.operationTimeEnd"
										  style="display: inline-block; width: 44%">
								<tp-datepicker :minDate="minDateForEnd" clearable
							   format="YYYY-MM-DD HH:mm:ss" v-model="formData.operationTimeEnd" value-format="YYYYMMDDHHmmss"></tp-datepicker>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="actions">
				<fb-button @on-click="handleQuery" icon="search" type="primary">查询</fb-button>
				<fb-button @on-click="handleReset" icon="refresh">重置</fb-button>
				<fb-button @on-click="handleExport" icon="download" type="success">导出</fb-button>
				<fb-button @on-click="handleViewLatest" icon="eye" type="info">查看最新版本</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					:columns="table.columns"
					:formatters="formatters"
					:multiple="false"
					:param="formData"
					:pk="table.primaryKey"
					:scroll="{x:1200, y: 402, autoHeight: true}"
					:service="table.service.list"
					:showTotalInfo="true"
					@on-row-select="handleTableSelect"
					@on-row-dblclick="handleRowDoubleClick"
					auto-load
					ref="table">
				</fb-simple-table>
			</template>
		</fb-page-search>

		<!-- 详情弹窗 -->
		<fb-modal
			v-model="detailModal.visible"
			:title="detailModal.title"
			width="80%"
			:mask-closable="false">
			<div v-if="detailModal.data">
				<fb-fieldset label="基本信息"/>
				<fb-property bordered label-width="120px" mode="form">
					<fb-row>
						<fb-col span="8">
							<fb-property-item label="版本号">
								{{detailModal.data.version}}
							</fb-property-item>
						</fb-col>
						<fb-col span="8">
							<fb-property-item label="操作类型">
								{{getOperationTypeName(detailModal.data.operationType)}}
							</fb-property-item>
						</fb-col>
						<fb-col span="8">
							<fb-property-item label="操作时间">
								{{formatDate(detailModal.data.operationTime, 'YYYY-MM-DD HH:mm:ss')}}
							</fb-property-item>
						</fb-col>
					</fb-row>
					<fb-row>
						<fb-col span="24">
							<fb-property-item label="操作用户ID">
								{{detailModal.data.operatorId}}
							</fb-property-item>
						</fb-col>
					</fb-row>
				</fb-property>

				<fb-fieldset label="变更数据" style="margin-top: 20px;"/>
				<fb-tabs v-model="detailModal.activeTab">
					<fb-tab-pane label="变更前数据" name="before">
						<pre style="background: #f5f5f5; padding: 15px; border-radius: 4px; max-height: 400px; overflow-y: auto;">{{formatJsonData(detailModal.data.beforeData)}}</pre>
					</fb-tab-pane>
					<fb-tab-pane label="变更后数据" name="after">
						<pre style="background: #f5f5f5; padding: 15px; border-radius: 4px; max-height: 400px; overflow-y: auto;">{{formatJsonData(detailModal.data.afterData)}}</pre>
					</fb-tab-pane>
				</fb-tabs>
			</div>
			<div slot="footer">
				<fb-button @on-click="detailModal.visible = false">关闭</fb-button>
			</div>
		</fb-modal>
	</div>
</template>

<script>
export default {
	mixins: [],
	components: {},
	mounted() {
		// 执行界面初始化方法
	},
	computed: {
		maxDateForStart() {
			if (!this.formData.operationTimeEnd) return null;
			return new Date(this.formData.operationTimeEnd);
		},
		minDateForEnd() {
			if (!this.formData.operationTimeStart) return null;
			return new Date(this.formData.operationTimeStart);
		}
	},

	data() {
		let that = this;
		return {
			defaultData: {
				operationTypeData: [
					{value: '', text: '全部'},
					{value: 'CREATE', text: '创建'},
					{value: 'UPDATE', text: '更新'},
					{value: 'DELETE', text: '删除'},
					{value: 'QUERY', text: '查询'}
				]
			},
			formData: {
				operationType: '',
				operatorId: '',
				version: '',
				operationTimeStart: this.formatDate(new Date(new Date().valueOf() - 30 * 24 * 60 * 60 * 1000), "YYYY-MM-DD HH:mm:ss"),
				operationTimeEnd: this.formatDate(new Date(), "YYYY-MM-DD HH:mm:ss")
			},
			formatters: {
				operationType(val) {
					const typeMap = {
						'CREATE': '创建',
						'UPDATE': '更新',
						'DELETE': '删除',
						'QUERY': '查询'
					};
					return typeMap[val] || val;
				},
				operationTime(val) {
					return that.formatDate(val, "YYYY-MM-DD HH:mm:ss");
				}
			},
			// 详情弹窗
			detailModal: {
				visible: false,
				title: '变更历史详情',
				data: null,
				activeTab: 'before'
			},
			// Table列
			table: {
				// 请求的 service
				service: this.$svc.sys.dept.org.history,
				primaryKey: "id",
				columns: [
					{
						name: 'version',
						label: '版本号',
						sortable: true,
						width: 100
					},
					{
						name: 'operationType',
						label: '操作类型',
						sortable: false,
						width: 100
					},
					{
						name: 'operationTime',
						label: '操作时间',
						sortable: true,
						width: 180
					},
					{
						name: 'operatorId',
						label: '操作用户ID',
						sortable: false,
						width: 120
					},
					{
						name: 'id',
						label: '记录ID',
						sortable: false,
						width: 100
					},
					{
						name: 'action',
						label: '操作',
						sortable: false,
						width: 120,
						render: (h, params) => {
							return h('div', [
								h('fb-button', {
									props: {
										type: 'primary',
										size: 'small'
									},
									on: {
										click: () => {
											that.handleViewDetail(params.row);
										}
									}
								}, '查看详情')
							]);
						}
					}
				]
			}
		}
	},

	// 方法
	methods: {
		// 查询方法
		handleQuery() {
			this.$refs.table.doSearch();
		},

		// 重置方法
		handleReset() {
			this.formData = {
				operationType: '',
				operatorId: '',
				version: '',
				operationTimeStart: this.formatDate(new Date(new Date().valueOf() - 30 * 24 * 60 * 60 * 1000), "YYYY-MM-DD HH:mm:ss"),
				operationTimeEnd: this.formatDate(new Date(), "YYYY-MM-DD HH:mm:ss")
			};
			this.$refs.table.doSearch();
		},

		// 导出方法
		handleExport() {
			let columns = this.table.columns
				.filter(item => item.name && item.name !== 'action')
				.map(item => item.name);

			let exportData = {
				...this.formData,
				columns: columns,
				fileName: "组织机构变更历史记录"
			};

			// 调用导出接口
			this.$svc.sys.dept.org.history.export(exportData).then(response => {
				// 处理文件下载
				const blob = new Blob([response.data]);
				const link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = `组织机构变更历史_${this.formatDate(new Date(), 'YYYYMMDD_HHmmss')}.xlsx`;
				link.click();
				window.URL.revokeObjectURL(link.href);
			}).catch(error => {
				this.$Message.error('导出失败：' + error.message);
			});
		},

		// 查看最新版本
		handleViewLatest() {
			this.$svc.sys.dept.org.history.latest().then(response => {
				if (response.data && response.data.data) {
					this.handleViewDetail(response.data.data);
				} else {
					this.$Message.info('暂无版本记录');
				}
			}).catch(error => {
				this.$Message.error('获取最新版本失败：' + error.message);
			});
		},

		// 表格行选择
		handleTableSelect(selection, row) {
			// 处理行选择逻辑
		},

		// 表格行双击
		handleRowDoubleClick(row) {
			this.handleViewDetail(row);
		},

		// 查看详情
		handleViewDetail(row) {
			this.detailModal.data = row;
			this.detailModal.visible = true;
			this.detailModal.activeTab = 'before';
		},

		// 获取操作类型名称
		getOperationTypeName(type) {
			const typeMap = {
				'CREATE': '创建',
				'UPDATE': '更新',
				'DELETE': '删除',
				'QUERY': '查询'
			};
			return typeMap[type] || type;
		},

		// 格式化JSON数据
		formatJsonData(jsonStr) {
			if (!jsonStr) return '无数据';
			try {
				const obj = typeof jsonStr === 'string' ? JSON.parse(jsonStr) : jsonStr;
				return JSON.stringify(obj, null, 2);
			} catch (e) {
				return jsonStr;
			}
		}
	}
}
</script>

<style lang="less" scoped>
.fb-fieldset {
	margin-bottom: 15px;
}

.fb-property {
	margin-bottom: 20px;
}

pre {
	font-family: 'Courier New', Courier, monospace;
	font-size: 12px;
	line-height: 1.4;
	white-space: pre-wrap;
	word-wrap: break-word;
}
</style>