<template>
	<div>
		<fb-page-search>
			<fb-fieldset label="典型界面"/>
			<template>
				<!--<fb-button ref="buttonAdd" @on-click="handleAdd" v-permission = "'SYS_ENTMANAGE_ENT_ADD'" icon="add-circle">add</fb-button> v-permission指令权限控制-->
				<fb-button ref="buttonList" @on-click="handleList">

					<fb-tooltip placement="top-start">
						<template slot="content">
							你想干嘛？
						</template>
						<fb-icon name="information"></fb-icon>
					</fb-tooltip>

					list(列表)</fb-button>
				&nbsp;
				<fb-button ref="buttonList" @on-click="handleNopageList" icon="add-circle">nopage-list(列表)</fb-button>
				&nbsp;
				<fb-button @on-click="handleTableList" icon="add-circle" ref="buttonTableList">table-list(表格合并)
				</fb-button>

				<fb-button @on-click="handleTablePageList" icon="add-circle" ref="buttonTablePageList">table-list(表格合并-分页)
				</fb-button>

				<fb-button ref="buttonAdd" @on-click="handleAdd" icon="add-circle">add(单表)</fb-button>

				<fb-button ref="buttonAddSlave" @on-click="handleAddSlave" icon="add-circle">add(主从)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonTabAdd" @on-click="handleTabAdd" icon="add-circle">tab-add(隐患查看)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonAdd" @on-click="handleView2" icon="add-circle">form-list-view</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonTabAdd" @on-click="handleFlowTabAdd" icon="add-circle">flow-tab-add(检查计划)
				</fb-button>
				&nbsp;
				<fb-button ref="buttonTabAdd" @on-click="handleStepTabAdd" icon="add-circle">setp-tab-add</fb-button>
				&nbsp;&nbsp;<br/>


				<fb-button ref="buttonQySingleSelect" @on-click="handleQySingleSelect" icon="add-circle">人员单选(企业)
				</fb-button>
				<fb-button ref="buttonQyMultipleSelect" @on-click="handleQyMultipleSelect" icon="add-circle">人员多选(企业)
				</fb-button>
				<fb-button ref="buttonQyAllSingleSelect" @on-click="handleQyAllSingleSelect" icon="add-circle">
					人员单选(企业全部)
				</fb-button>
				<fb-button ref="buttonQyAllMultipleSelect" @on-click="handleQyAllMultipleSelect" icon="add-circle">
					人员多选(企业全部)
				</fb-button>
				&nbsp;&nbsp;<br/>


				<fb-button ref="buttonSelect" @on-click="handleUserSelect" icon="add-circle">人员多选(政府)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonSingleSelect" @on-click="handleSingleSelect" icon="add-circle">人员单选(政府)
				</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonEntSelect" @on-click="handleEntSelect" icon="add-circle">企业多选(政府)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonEntSingleSelect" @on-click="handleEntSingleSelect" icon="add-circle">企业单选(政府)
				</fb-button>
				&nbsp;&nbsp;<br/>


				<fb-button ref="buttonDynamic" @on-click="handleDynamic" icon="add-circle">动态列(增加tabbr跳转)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="handleForm" @on-click="handleForm" icon="add-circle">form(增加tabbr跳转)</fb-button>
				&nbsp;&nbsp;
				<fb-button ref="buttonTabList" @on-click="handleTabList" icon="add-circle">tab-list(页面内跳转)</fb-button>
				&nbsp;
				<!--<fb-button ref="buttonTabListView" @on-click="handleTabListView" icon="add-circle">tab-list-view(页面内跳转)</fb-button>-->
				&nbsp;&nbsp;
				<fb-button ref="buttonHandYqyd" @on-click="handleYqyd" icon="add-circle">一企一档典型界面</fb-button>
				&nbsp;&nbsp;<br/>

				<fb-button ref="buttonHandImport" @on-click="handleImport" icon="add-circle">导入（模版下载）</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handlePromise" icon="add-circle">企业承诺书</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handleAnswerQues" icon="add-circle">企业答题</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handleViewQues" icon="add-circle">政府查看答题</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handleDocument" icon="add-circle">文书生成及转换</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handleViewPdf" icon="add-circle">查看pdf文书</fb-button>


				<fb-button @on-click="handleIframe" icon="add-circle" ref="buttonHandlePromise">文档在线编辑（第三方嵌套）</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button ref="buttonHandlePromise" @on-click="handleRichTextView" icon="add-circle">富文本查看(通知公告)</fb-button>
				&nbsp;
				&nbsp;&nbsp;
				<fb-button  @on-click="sm" icon="add-circle">加密解密</fb-button>


				<fb-button  @on-click="sso" icon="add-circle">单点登录sso</fb-button>
			</template>

			<br/>
			<br/>
			<br/>
			<fb-fieldset label="测试用例"/>
			<template>
				<div>
					<fb-button @on-click="handleClickAdd" icon="add-circle">原生dialog打开页面</fb-button>&nbsp;
					<fb-dialog ref="dialog" can-full-screen no-padding></fb-dialog>
					<fb-button  @on-click="handleDate" icon="add-circle">日期测试</fb-button>&nbsp;
					<fb-button  @on-click="handleIfream" icon="add-circle">测试ifream</fb-button> &nbsp;
				</div>
			</template>

		</fb-page-search>
		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
		<tp-dialog-tab ref="TpDialogTab" @closeTpDialog="closeDialogTab"></tp-dialog-tab>
		<tp-dialog-flow-tab ref="TpDialogFlowTab" @closeTpDialog="closeDialogFlowTab"></tp-dialog-flow-tab>
		<tp-dialog-step-tab ref="TpDialogStepTab"></tp-dialog-step-tab>
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
					columns: [
						{
							name: 'userId',
							hidden: true,
							width: 1,
						}, {
							name: 'userName',
							label: '人员名称',
							sortable: false,
							slot: 'view',
							align: 'left',
						}, {
							name: 'userGander',
							label: '性别',
							sortable: false,
							align: 'left',
							formatter: function (val, row) {
								val = val == 1 ? '男' : val
								return val;
							},
						}, {
							name: 'userTel',
							label: '手机号码',
							hidden: true,
							sortable: false,
							align: 'left',
						}, {
							name: 'desc',
							label: '描述',
							sortable: false,
							align: 'left',
							width: '450px'
						},
						{
							name: '',
							label: '操作',
							sortable: false,
							align: 'right',
							slot: 'actions',
							width: '25%'
						},

					],
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

			handleList() {
				this.$router.push({
					path: '/sys/demo/list', // 配置的路由
					query: {
						site: "demo-list"  // 传递的参数
					}
				})
			},

			handleNopageList() {
				this.$router.push({
					path: '/sys/demo/nopage-list', // 配置的路由
					query: {
						site: "demo-list"  // 传递的参数
					}
				})
			},

			handleTableList() {
				this.$router.push({
					path: '/sys/demo/table-list', // 配置的路由
//					query: {
//						site: "table-list"  // 传递的参数
//					}
				})
			},

			handleTablePageList() {
				this.$router.push({
					path: '/sys/demo/table-page-list', // 配置的路由
//					query: {
//						site: "table-list"  // 传递的参数
//					}
				})
			},

			// 查看方法
			handleAdd() {
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/add.vue', param, "新增");
			},

			handleClickAdd () {
				// 假设当前文件为： @/view/user/list.vue
				this.$refs.dialog.show({
					// url：指定要打开的页面路径，文件以.vue为后双缀名，且文件存放在`@/view`根目录下
					// 打开目标文件：@/view/user/edit.vue
					url: "./add",
					// data：传递至页面中的数据，需要打开的页面，可通过data属性获取数据，
					data: {aaa:'aaa'},
					title: '原生dialog新增',
					close: (data) => {
						if (data) {
							this.$message.success('操作成功...', {time: 1000})
						}
					},
				})
			},

			handleAddSlave() {
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/add-slave.vue', param, "主从新增");
			},

			// 新增方法
			handleTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/update-check-list.vue',
					label: '隐患检查查看',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}];

				let options = {
					tabChangeConfirm: false,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialogTab.show(tabArry, param, "tab新增", options);
			},

			// 新增方法
			handleFlowTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/plan.vue',
					label: '检查计划',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/add-tab.vue',
						label: '扩展信息',
						icon: "progressbar"
					}, {
						url: '/sys/demo/plan.vue',
						label: '完成',
						icon: "progressbar"
					},];

				this.$refs.TpDialogFlowTab.show(tabArry, param, "新增检查计划");
			},

			// 新增方法
			handleStepTabAdd() {
				let param = {};

				let tabArry = [{
					url: '/sys/demo/add-check-list.vue',
					label: '检查',
					icon: "chart-line"
				}, {
					url: '/sys/demo/step1.vue',
					label: '第一步',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/step2.vue',
						label: '第二步',
						icon: "chart-line"
					}, {
						url: '/sys/demo/step3.vue',
						label: '第三步',
						icon: "chart-line"
					},];

				this.$refs.TpDialogStepTab.show(tabArry, param, "step-tab新增");
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
							//this.handleQuery();
						} else {
							// 服务器返回失败
							this.$message.error('删除失败: ' + result.message)
						}
					});
				})
			},
			handleView2(row) {
				/*let id = row.userId
				let param = {"id": id}
				let options = {
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/form-list-view.vue', param, "查看", options);*/

				let param = {"id": row.userId};
				let tabArry = [{
					url: '/sys/demo/form-list-view.vue',
					label: '查看form-list-view',
					icon: "chart-line"
				},
					{
						url: '/sys/demo/list.vue',
						label: 'list信息',
						icon: "progressbar"
					}];

				this.$refs.TpDialogTab.show(tabArry, param, "查看");
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
				console.log(param)
			},
			closeDialogFlowTab(param) {
				console.log(param)
			},

			handleQySingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/ent/select-person-single.vue', param, "人员选择", options);
			},

			handleQyMultipleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/ent/select-person-multiple.vue', param, "人员选择", options);
			},


			handleQyAllSingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/ent/select-all-person-single.vue', param, "人员选择", options);
			},

			handleQyAllMultipleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/ent/select-all-person-multiple.vue', param, "人员选择", options);
			},

			handleUserSelect() {
				let param = {
					"persons": [{
						deptId: "1364853202066866176",
						personId: "1366575600608739328",
						personName: "ge先生",
					}]
				}

				let options = {
					callBack: function (result) {
						console.log("=============回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-person-multiple.vue', param, "人员选择", options);
			},
			handleSingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-person-single.vue', param, "人员单选", options);
			},
			handleEntSelect() {
				let param = {"ents": [{"entId": "1366270340598071296", "entFullName": "测试"}]}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-ent-multiple.vue', param, "企业选择", options);
			},
			handleEntSingleSelect() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/common/org/select-ent-single.vue', param, "企业单选", options);
			},
			handleDynamic() {
				this.$router.push({
					path: '/sys/demo/dynamic', // 配置的路由
					query: {
						site: "aaa"  // 传递的参数
					}
				})
			},

			handleForm() {
				this.$router.push({
					path: '/sys/demo/form', // 配置的路由
					query: {
						site: "tabbar-form"  // 传递的参数
					}
				})
			},

			handleTabList() {
				this.$router.push({
					path: '/sys/demo/tab-page', // 配置的路由
					query: {
						id: "100001"  // 传递的参数
					}
				})
			},

			handleTabListView() {

			},

			handleYqyd() {
				this.$router.push({
					path: '/sys/demo/yqyd-switch', // 配置的路由
					query: {
						id: "100001"  // 传递的参数
					}
				})
			},

			handleImport() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleEntSelect回调" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/import.vue', param, "风险导入", options);
			},

			handlePromise() {
				this.$router.push({
					path: '/sys/demo/promise', // 配置的路由
					query: {
						id: "100001"  // 传递的参数
					}
				})
			},
			handleAnswerQues() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleAnswerQues回调" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/answer-question.vue', param, "每日答题", options);
			},
			handleViewQues() {
				let param = {}
				let options = {
					callBack: function (result) {
						console.log("=============handleAnswerQues回调" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view-question.vue', param, "查看答题", options);
			},
			handleDocument() {
				let param = {}
				let options = {
					callBack: function (result) {
						// console.log("=============handleDocument回调" + result)
						// 展示pdf
						// this.handleViewPdf(result);
					}
				}
				this.$refs.TpDialog.show('/sys/demo/safe-document.vue', param, "文书", options);
			},
			handleViewPdf(url) {
				if (url) {
					url = "/static/wps/执法文书.pdf";
				}
				let param = {"url": url}
				let options = {
					callBack: function (result) {
						console.log("=============viewPdf回调" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/view-pdf.vue', param, "查看文书", options);
			},

			handleIframe(url) {
				if (url) {
					url = "/static/wps/iframe.pdf";
				}
				let param = {"url": url}
				let options = {
					callBack: function (result) {
						console.log("=============viewPdf回调" + result)
					}
				}
				// 测试cors-iframe this.$refs.TpDialog.show('/sys/demo/cors-iframe.vue', param, "iframe", options);
				// 测试 iframe 嵌套 word在线编辑
				this.$refs.TpDialog.show('/sys/demo/editor-office.vue', param, "iframe", options);
			},

			handleRichTextView(){
				let param = {}
				let options = {
					callBack: function (result) {
						// console.log("=============handleDocument回调" + result)
						// 展示pdf
						// this.handleViewPdf(result);
					}
				}
				this.$refs.TpDialog.show('/sys/demo/rich-text-view.vue', param, "富文本查看", options);
			},
			sm(){
				let param = {}
				let options = {
					callBack: function (result) {
						// console.log("=============handleDocument回调" + result)
						// 展示pdf
						// this.handleViewPdf(result);
					}
				}
				this.$refs.TpDialog.show('/sys/demo/sm.vue', param, "sm加密算法", options);
			},
			sso(){
				// 根据token重新获取时效5分钟的token
				let token = this.$datax.get("token");
				app.$svc.sys.demo.getSsoToken({"jt": token}).then((result) => {

					if (result.code == 1) {
						let jt = result.data;
						// TODO Cookie，jt
						window.open("http://192.168.90.141:8081/topinfo-sso/platform/sso/client/oauth?jt=" + jt +"&sp=" + encodeURIComponent("/sys/menu/tree"));
					} else {
						// 服务器返回失败
						this.$message.error('删除失败: ' + result.message)
					}
				})
			},
			handleDate() {
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/date.vue', param, "日期测试");
			},
			handleIfream(){
				let param = {}
				this.$refs.TpDialog.show('/sys/demo/ifream.vue', param, "测试ifream");
			}
		}
	}
</script>

<style lang="less" scoped>
</style>
