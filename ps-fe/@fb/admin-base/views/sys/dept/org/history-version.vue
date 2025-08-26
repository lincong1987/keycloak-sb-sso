<template>
	<div class="history-version-dialog">
		<fb-simple-table
			:service="service"
			:columns="columns"  
		 
		>

        <template #operationType="props">
			{{getOperationTypeName(props.row.operationType)}}
		</template>

			 
		</fb-simple-table>
		
		 
	</div>
</template>

<script>
import { filter } from 'vue/types/umd'

export default {
	name: 'HistoryVersion',
	props: {
		deptId: {
			type: [String, Number],
			required: true
		}
	},
	data() {
		return {
			// 操作类型映射
			operationTypeMap: {
				'CREATE': '创建',
				'UPDATE': '更新',
				'DELETE': '删除',
				'QUERY': '查询'
			},
			// 查询参数
			queryData: {
				recordId: this.deptId
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
					label: '版本号',
					name: 'version',
					key: 'version',
					width: 120
				},
				{
					label: '操作类型',
					name: 'operationType',
					key: 'operationType',
					width: 100,
					//formatter: (text) => this.getOperationTypeName(text)
				},
				{
					label: '操作时间',
					name: 'operationTime',
					key: 'operationTime',
					width: 160,
				//	formatter: (text) => this.formatTime(text)
				},
				{
					label: '操作用户ID',
					name: 'operatorUserId',
					key: 'operatorUserId',
					width: 120
				},
				// {
				// 	label: '操作',
				// 	name: 'operation',
				// 	key: 'operation',
				// 	width: 100,
				// 	slot: 'operation'
				// }
			] 
		}
	},
	computed: {
		// 服务配置
		service() {
			return this.$svc.sys.dept.org.history.list
		}
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
		}
	}
}
</script>

<style scoped>
.history-version-dialog {
	padding: 16px;
}

pre {
	background-color: #f5f5f5;
	padding: 12px;
	border-radius: 4px;
	max-height: 400px;
	overflow: auto;
	font-family: 'Courier New', monospace;
	font-size: 12px;
	line-height: 1.4;
}
</style>