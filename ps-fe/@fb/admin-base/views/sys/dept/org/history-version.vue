<template>
	<div class="history-version-dialog">
		<fb-simple-table :service="service" :columns="columns">

		 	 
			<template #operation="props">
				<fb-button editor type="primary" size="small" 
				@on-click="handleCompare(props.row)">{{ props.row.operationTypeName}}</fb-button>
			</template>


		</fb-simple-table>

		<fb-dialog ref="compareDialog">
			<fb-flex grid cols2 >
				<fb-flex ai-start  >
					<pre>{{ formatJsonData(beforeData) }}</pre>
					<!-- <fb-tree :data="afterData" expand
						style="overflow: auto"
						:reader="{value: 'id', label: 'text'}"
					></fb-tree> -->
				</fb-flex>
				
				<fb-flex ai-start>
					<pre>{{ formatJsonData(afterData) }}</pre>
					<!-- <fb-tree :data="afterData"
					style="overflow: auto"
				
					:reader="{value: 'id', label: 'text'}"></fb-tree> -->
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
					label: '类型',
					name: 'operation',
					key: 'operation',
					width: 120,
					slot: 'operation'
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
			afterData: {},
			beforeData: {},
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


			// this.beforeData =[ JSON.parse(row.beforeData)]

			// this.afterData = [JSON.parse(row.afterData)]

			
			this.beforeData = JSON.parse(row.beforeData)
			this.afterData = JSON.parse(row.afterData)
// if (row.beforeFullTree) {
// 			this.beforeData =JSON.parse(row.beforeFullTree)}
// 			if (row.afterFullTree) {
// 			this.afterData = JSON.parse(row.afterFullTree)
// 			}

 
		 
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