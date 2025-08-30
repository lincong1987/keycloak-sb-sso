<template>
	<div>
		<fb-page-search>
			<template slot="query">
				{{ table.checkedColumns }}
				<fb-form ref="query-form" mode="query">
					<fb-row>
						<fb-col span="11" v-if="showInput('userName')" >
							<fb-form-item label="人员名称">
								<fb-input v-model="formData.userName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="11" v-if="showInput('userGander')">
							<fb-form-item label="性别">
								<fb-select v-model="formData.userGander"
										   :data="defaultForm.sex"
										   :placeholder="'请选择'"
										   clearable/>
							</fb-form-item>
						</fb-col>
						<fb-col span="11">
							<fb-form-item label="时间">
								<tp-datepicker mode="range" v-model="formData.date" @rangFormat="rangeDateFormat"
											   format="YYYY-MM-DD HH:mm:ss" value-format="YYYYMMDDHHmmss"
											   clearable></tp-datepicker>
							</fb-form-item>
						</fb-col>
						<fb-col span="11" >
							<fb-form-item label="时间选择" :content-style="{ }" style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date1" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择开始时间'" clearable></tp-datepicker>
							</fb-form-item>
							<fb-form-item  label="-" :label-style="{textAlign: 'center', paddingLeft: '18px'}"  style="display: inline-block; width: 50%">
								<tp-datepicker v-model="formData.date2" format="YYYY-MM-DD HH:mm:ss"
											   value-format="YYYYMMDDHHmmss" :placeholder="'请选择结束时间'" clearable></tp-datepicker>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="buttons">
				<!--<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission = "'SYS_ENTMANAGE_ENT_ADD'" icon="add-circle">add</fb-button> v-permission指令权限控制-->
			</template>

			<template slot="actions">

				<fb-popup-select v-model="showColumnsSelect">
					<fb-button slot="header"
							   icon="jpx-icon-setting-gear-fill"
							   @on-click="showColumnsSelect = !showColumnsSelect">
						列展示
					</fb-button>

					<fb-card slot="picker" header="列展示" style="width: 168px;">
						<fb-container max-height="500px" overflow="auto">
							<fb-checkbox-group
								vertical
								:value="table.checkedColumns"
								:data="table.columns.map(column=>{column.value = column.name ; return  column})"
								@on-change="handleColumnsChange" ></fb-checkbox-group>
						</fb-container>
					</fb-card>
				</fb-popup-select>

				<fb-button type="primary" icon="search" @on-click="handleQuery">查询</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					v-autoheight="120"
					ref="table"
					:service="table.service.search"
					:param="formData"
					:pk="table.primaryKey"
					:columns="table.selectedColumns"
					:auto-load="true"
					:multiple="false"
					style="overflow-x: scroll;"
					@on-row-select="handleTableSelect">

					<template v-slot:actions="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleEdit(row)" label="update" type="primary"></fb-link>
							<fb-link :click="()=>handleTabEdit(row)" label="tab-update" type="primary"></fb-link>
							<fb-link :click="()=>handleDel(row)" label="delete" type="danger"></fb-link>
						</fb-link-group>
					</template>

					<template v-slot:view="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleView(row)" :label="row.userName" type="primary"></fb-link>
						</fb-link-group>
					</template>
				</fb-simple-table>
			</template>
		</fb-page-search>
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
			// 执行界面初始化
			this.createdPage()
			this.initView()
		},
		data() {
			return {
				showColumnsSelect: false,
				formData: {
					userName: '',
					userGander: '',
					date: [null, null],
					date1: '',
					date2: '',
					startDate: '',
					endDate: '',
				},
				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.demo,
					primaryKey: "userId",
					// 默认列的列
					defaultColumns: [
						{
							label: 'id',
							name: 'userId',
							hidden: true,
							width: 1,
						},{
							name: 'userName',
							label: '姓名',
							sortable: false,
							width: 120,
							align: 'left',
						},
					],
					// 所有需要在列展示中给用户勾选的列
					columns: [
						{
							name: 'userGander',
							label: '性别',
							sortable: false,
							width: 100,
							align: 'left',
						},
						{
							name: 'userTel',
							label: '电话电话电话电话电话电话电话电话电话电话电话电话',
							sortable: false,
							align: 'left',
							width: 100,
						},
						{
							name: 'xxx',
							label: '标签',
							sortable: false,
							align: 'center',
							width: 500,
						},{
							name: 'vvv',
							label: '标签2',
							sortable: false,
							align: 'center',
							width: 500,
						},
						{
							name: 'bbb',
							label: '标签3',
							sortable: false,
							align: 'center',
							width: 500,
						},
						{
							name: 'op',
							label: '操作',
							sortable: false,
							align: 'left',
							slot: 'actions',
							disabled:true,
							width: 100,
						},
					],
					// 列展示下拉中已选择的项
					checkedColumns: ['op'],
					// 勾选后显示的列结果
					selectedColumns: [],
				},
			}
		},
		// 方法
		methods: {
			// 区间日期的格式化回调方法
			rangeDateFormat(start, end) {
				this.formData.startDate = start;
				this.formData.endDate = end;
			},
			// 列表方法
			handleQuery() {
				this.$refs.table.doSearch()
			},
			// 初始化方法
			initView(){
				// 显示选中的列
				let checkColumens = this.table.columns.filter(col => this.table.checkedColumns.includes(col.name))
				this.table.selectedColumns = this.table.defaultColumns.concat(checkColumens)
			},
			// 处理列展示点击选中和取消的效果
			handleColumnsChange (chks, value, checked) {
				let inChecked = this.table.checkedColumns.indexOf(value)
				if (checked) {
					if (inChecked === -1) {
						this.table.checkedColumns.push(value)
					}
				} else {
					if (inChecked !== -1) {
						this.table.checkedColumns.splice(inChecked, 1)
					}
				}
			},
			// 控制文本框是否显示
			showInput(column){
				return this.table.checkedColumns.indexOf(column) >= 0 ? true:false
			},
			// 处理列的动态显示方法
			handleColumns(val){
				let checkColumens = this.table.columns.filter(col => val.includes(col.name))
				this.table.selectedColumns = this.table.defaultColumns.concat(checkColumens)
			}
		},
		// 监听
		watch: {
			"table.checkedColumns"(val) {
				// 处理列的动态显示方法
				this.handleColumns(val)
			},
		},
	}
</script>

<style lang="less" scoped>
</style>
