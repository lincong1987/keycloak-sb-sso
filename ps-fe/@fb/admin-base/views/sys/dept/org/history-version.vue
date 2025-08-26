<template>
	<div class="history-version-dialog">
		<fb-simple-table :service="service" :columns="columns">

		 
			<!-- <template #operation="props">
				<fb-button type="primary" size="small" @click="handleCompare(props.row)">对比</fb-button>
			</template> -->


		</fb-simple-table>

		<fb-dialog ref="compareDialog">
			<fb-flex>
				<fb-flex>
					<fb-tree :data="prevTree"></fb-tree>
				</fb-flex>
				
				<fb-flex>
					<fb-tree :data="nextTree"></fb-tree>
				</fb-flex>
			</fb-flex>
		</fb-dialog>

	</div>
</template>

<script>

export default {
	name: 'HistoryVersion',
	props: {
		 
	},
	computed: {
	},
	data() {
	 
		
		return {
			service: this.$svc.sys.dept.org.history,
			// 操作类型映射
			operationTypeMap: {
				'CREATE': '创建',
				'UPDATE': '更新',
				'DELETE': '删除',
				'QUERY': '查询'
			},
		 
			// 详情弹窗配置
			detailModal: {
				visible: false,
				title: '版本详情',
				data: {},
				activeTab: 'basic'
			},
			// 表格列定义
			columns: [
				{
					label: '记录ID',
					name: 'id',
					key: 'id',
					width: 120
				},
					{
					label: '查看',
					name: 'operation',
					key: 'operation',
					width: 120,
					slot: 'operation'
				},
			 
				{
					label: '操作类型',
					name: 'operationType',
					key: 'operationType',
					width: 100,
					//formatter: (text) => this.getOperationTypeName(text)
				},
			
			 
				{
					label: '操作用户',
					name: 'operatorUserName',
					key: 'operatorUserName',
					width: 120
				},

				 
	{
					label: '操作时间',
					name: 'operationTime',
					key: 'operationTime',
					width: 160,
				//	formatter: (text) => this.formatTime(text)
				},

				// {
				// 	label: '操作',
				// 	name: 'operation',
				// 	key: 'operation',
				// 	width: 100,
				// 	slot: 'operation'
				// }
			] ,
			prevTree: [],
			nextTree: []
		}
	},
	mounted() {
	},
	methods: {
		// 格式化时间
		formatTime(time, format = 'YYYY-MM-DD HH:mm:ss') {
			if (!time) return ''
			return this.$dayjs(time).format(format)
		},
		
		// 查看详情
		handleViewDetail(row) {
			this.detailModal.data = row
			this.detailModal.visible = true
			this.detailModal.activeTab = 'basic'
		},
		
		// 获取操作类型名称
		getOperationTypeName(type) {
			return this.operationTypeMap[type] || type
		},
		
		// 格式化JSON数据
		formatJsonData(data) {
			if (!data) return ''
			try {
				const parsed = typeof data === 'string' ? JSON.parse(data) : data
				return JSON.stringify(parsed, null, 2)
			} catch (e) {
				return data
			}
		},
		handleCompare (row) { 


			//this.prevTree = this.row

			this.$nextTick(() => {
				this.$refs.compareDialog.show()
			})

		},
	}
}
</script>

<style scoped>
.history-version-dialog {
	padding: 16px;
}

</style>