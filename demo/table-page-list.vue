<template>
	<div>
		<fb-page-search>
			<template slot="query">
				<fb-form mode="query" ref="query-form">
					<fb-row>
						<fb-col span="11">
							<fb-form-item label="人员名称">
								<fb-input v-model="formData.userName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col offset="1" span="11">
							<fb-form-item label="性别">
								<fb-select :data="defaultForm.sex"
										   :placeholder="'请选择'"
										   clearable
										   v-model="formData.userGander"/>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</template>

			<template slot="actions">
				<fb-button @on-click="handleQuery" icon="search" type="primary">查询</fb-button>
			</template>

			<template slot="table">
				<fb-simple-table
					:bordered="true"
					:cellSpans="table.cellSpans"
					:columns="table.columns"
					:formatters="table.formatters"
					:multiple="false"
					:param="formData"
					:pk="table.primaryKey"
					:rowGroups="['indexOneName', 'entName', 'end']"
					:rownum="false"
					:service="xxService"
					ref="table"
				>

					<template v-slot:view="props">
						<fb-link-group>
							<fb-link :click="()=>handleView2(props.row)" :label="props.row.indexName" type="primary"></fb-link>
						</fb-link-group>
					</template>


				</fb-simple-table>
			</template>
		</fb-page-search>
		<tp-dialog @closeTpDialog="closeDialog" ref="TpDialog"></tp-dialog>
		<tp-dialog-tab @closeTpDialog="closeDialogTab" ref="TpDialogTab"></tp-dialog-tab>
		<tp-dialog-flow-tab @closeTpDialog="closeDialogFlowTab" ref="TpDialogFlowTab"></tp-dialog-flow-tab>
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
				// 请求的 url
				fileService: app.$svc.sys.file,
				formData: {
					userName: '',
					userGander: '',
					date: [null, null],
					date1: '',
					date2: '',
					date3: '',
					mindate3: '',
					maxdate3: '',
					date4: '',
					mindate4: '',
					maxdate4: '',
					startDate: '',
					endDate: '',
				},
				// Table列
				table: {
					// 请求的 url
					service: app.$svc.sys.demo,

					formatters: {
						allNum(val, row){
							val = val == 1 ? '男' : val
							return val;
						},
					},

					// cellSpans: {
					// 	indexOneName(val){
					// 		return {rowSpan: 2, colSpan: 1}//返回一个对象，包含上面两个属性，用于控制单元格的横向及纵向合并
					// 	},
					// 	entName(val){
					// 		return {rowSpan: 3, colSpan: 1}//返回一个对象，包含上面两个属性，用于控制单元格的横向及纵向合并
					// 	}
					// },

					columns: [
						{
							name: 'indexOneName',
							label: '大项',
							sortable: false,
						}, {
							name: 'entName',
							label: '企业名称',
							sortable: false,
						}, {
							name: 'indexName',
							label: '小项',
							sortable: false,
							slot: 'view',
						},{
							name: 'factScore',
							label: '得分',
							sortable: false,
						},{
							label: '记分项',
							children: [
								{
									name: 'e4302Num',
									label: 'e4302Num',
									sortable: false,
									headColSpan: 2,
								}, {
									name: 'e4303Num',
									label: 'e4303Num',
									sortable: false,
									headColSpan: 0,
								}, {
									name: 'e4304Num',
									label: 'e4304Num',
									sortable: false,
									headColSpan: 1,
								}
							]
						},{
							name: 'end',
							label: '结尾再合并',
							sortable: false,
						}
					],
				},

				xxService: {
					list (param) {
						debugger
						param.current = param.current || 1;
						return new Promise((resolve, reject) => {
							setTimeout(() => {
								resolve(
									{
										'code': 1,
										'message': '成功',
										'data': {
											'records': param.current % 2 == 0 ? [
												{
													"indexOneId": "1334029176012128153",
													"entName": "图讯1",
													"indexOneName": "应急救援到位",
													"indexId": "1334029176012128105",
													"indexName": "应急预案",
													"indexNo": "SE0603",
													"factScore": 0,
													"end": "a"
												},
												{
													"indexOneId": "1334029176012128153",
													"entName": "图讯1",
													"indexOneName": "应急救援到位",
													"indexId": "1334029176012128106",
													"indexName": "应急队伍",
													"indexNo": "SE0602",
													"factScore": 0,
													"end": "a"
												},
												{
													"indexOneId": "1334029176012128153",
													"entName": "图讯1",
													"indexOneName": "应急救援到位",
													"indexId": "1334029176012128107",
													"indexName": "应急物资装备",
													"indexNo": "SE0601",
													"factScore": 0,
													"end": "a"
												},
												{
													"indexOneId": "1334029176012128153",
													"entName": "图讯1",
													"indexOneName": "应急救援到位",
													"indexId": "1402905752699404288",
													"indexName": "应急演练(每年至少一次)",
													"indexNo": "SE0604",
													"factScore": 0,
													"end": "a"
												},
												{
													"indexOneId": "1334029176012128153",
													"entName": "图讯1",
													"indexOneName": "应急救援到位",
													"indexId": "1402905901282623488",
													"indexName": "应急演练(每半年至少开展一次)",
													"indexNo": "SE0605",
													"factScore": 0,
													"end": "a"
												},
												{
													"indexOneId": "1334029176012128154",
													"entName": "图讯2",
													"indexOneName": "安全管理到位",
													"indexId": "1334029176012128125",
													"indexName": "规章制度(教育培训)",
													"indexNo": "SE0504",
													"factScore": 3,
													"end": "b"
												},
												{
													"indexOneId": "1334029176012128154",
													"entName": "图讯2",
													"indexOneName": "安全管理到位",
													"indexId": "1334029176012128126",
													"indexName": "隐患整改率",
													"indexNo": "SE0503",
													"factScore": 2.31,
													"end": "b"
												},
												{
													"indexOneId": "1334029176012128154",
													"entName": "图讯2",
													"indexOneName": "安全管理到位",
													"indexId": "1334029176012128127",
													"indexName": "隐患自查自报",
													"indexNo": "SE0502",
													"factScore": 5,
													"end": "b"
												},
												{
													"indexOneId": "1334029176012128154",
													"entName": "图讯2",
													"indexOneName": "安全管理到位",
													"indexId": "1334029176012128128",
													"indexName": "日常检查记录",
													"indexNo": "SE0501",
													"factScore": 12,
													"end": "b"
												},
												{
													"indexOneId": "1334029176012128154",
													"entName": "图讯2",
													"indexOneName": "安全管理到位",
													"indexId": "1402592633557090305",
													"indexName": "规章制度(隐患排查)",
													"indexNo": "SE0505",
													"factScore": 3,
													"end": "b"
												}
											] : [{
												"indexOneId": "1334029176012128160",
												"entName": "图讯3",
												"indexOneName": "必须依法生产",
												"indexId": "1334029176012128149",
												"indexName": "标准化信息",
												"indexNo": "SE0104",
												"factScore": 10,
												"end": "c"
											},
												{
													"indexOneId": "1334029176012128160",
													"entName": "图讯3",
													"indexOneName": "必须依法生产",
													"indexId": "1334029176012128150",
													"indexName": "许可证信息",
													"indexNo": "SE0103",
													"factScore": 2,
													"end": "c"
												},
												{
													"indexOneId": "1334029176012128160",
													"entName": "图讯3",
													"indexOneName": "必须依法生产",
													"indexId": "1334029176012128151",
													"indexName": "企业基本台账完成率",
													"indexNo": "SE0102",
													"factScore": 2.92,
													"end": "c"
												},
												{
													"indexOneId": "1334029176012128160",
													"entName": "图讯3",
													"indexOneName": "必须依法生产",
													"indexId": "1334029176012128159",
													"indexName": "企业基本信息完成率",
													"indexNo": "SE0101",
													"factScore": 4,
													"end": "c"
												},
												{
													"indexOneId": "1334029176012128157",
													"entName": "图讯4",
													"indexOneName": "安全责任到位",
													"indexId": "1402897516659212288",
													"indexName": "安全责任到位附件(机构文件)",
													"indexNo": "SE0201",
													"factScore": 4,
													"end": "d"
												},
												{
													"indexOneId": "1334029176012128157",
													"entName": "图讯4",
													"indexOneName": "安全责任到位",
													"indexId": "1402897648788176896",
													"indexName": "安全责任到位附件(人员文件)",
													"indexNo": "SE0202",
													"factScore": 0,
													"end": "d"
												},
												{
													"indexOneId": "1334029176012128157",
													"entName": "图讯4",
													"indexOneName": "安全责任到位",
													"indexId": "1402897814303801344",
													"indexName": "安全责任到位附件(网络图)",
													"indexNo": "SE0203",
													"factScore": 0,
													"end": "d"
												},
												{
													"indexOneId": "1334029176012128157",
													"entName": "图讯4",
													"indexOneName": "安全责任到位",
													"indexId": "1402898098954436608",
													"indexName": "安全生产责任书(街镇)",
													"indexNo": "SE0204",
													"factScore": 4,
													"end": "d"
												},
												{
													"indexOneId": "1334029176012128157",
													"entName": "图讯4",
													"indexOneName": "安全责任到位",
													"indexId": "1402898251992006656",
													"indexName": "安全生产责任书(企业内部)",
													"indexNo": "SE0205",
													"factScore": 0,
													"end": "d"
												}],
											'total': 10,
											'size': 2,
											'current': param.current,
											'orders': [],
											'searchCount': true,
											'pages': 5,
										},
										'expand': '',
									},
								)
							}, 1000)
						})
					},
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
			// 列表界面表格的按钮控制
			handleTableSelect(row, selectedRows) {
				// this.buttons.del.disabled = selectedRows.length != 1
				// this.buttons.edit.disabled = selectedRows.length != 1
			},
			// 查看方法
			handleAdd() {
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/add.vue', param, "新增");
			},

			// 新增方法
			handleTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/add.vue',
					label: '基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "tab新增");
			},
			handleView2(row) {
				let id = row.userId
				let param = {"id": id}
				let options = {
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view2.vue', param, "查看", options);
			},
			handleTabbar(row) {
				// 解密 this.$router.decrypt(id)

				this.$router.push({
					path: '/sys/demo/tree-list',
					query: {id: this.$router.encrypt(row.userId), tabLabel: row.userName}
				})
			},
			// 修改方法
			handleEdit(row) {

				let param = {"id": row.userId};

				this.$refs.TpDialog.show('/sys/demo/add.vue', param, "修改");
			},
			// 修改方法
			handleTabEdit(row) {

				let param = {"id": row.userId};
				let tabArry = [{
					url: '/sys/demo/add.vue',
					label: '基本信息',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "修改");
			},
			// 删除方法
			handleDel(row) {
				this.$confirm('确定要删除吗？', () => {
					this.table.service.delete({"id": row.id}).then((result) => {
						if (result.code == 1) {
							this.$message.success('删除成功');
							this.handleQuery();
						} else {
							// 服务器返回失败
							this.$message.error('删除失败: ' + result.message)
						}
					});
				})
			},
			// 查看方法
			handleView(row) {

				let id = row.userId
				let param = {"id": id}
				let options = {
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view.vue', param, "查看", options);
			},

			closeDialog(param) {
				// 单页面弹出框关闭时，回调方法，param是回传的参数
				console.log(param);
			},
			closeDialogTab(param) {
				// tab切换 页面弹出框关闭时，回调方法，param是回传的参数
				if (param) {
					this.handleQuery();
				}
			},
			closeDialogFlowTab(param) {
				console.log(param)
			},

			startOnChangeDate(value) {

			},

			endOnChangeDate(value) {


			},
		}
	}
</script>

<style lang="less" scoped>
</style>
