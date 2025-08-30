<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="160">
				<fb-row>
					<fb-col span="12">
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="名称" prop="entFullName" :rule="{required: true}">
									<fb-input v-model="formData.name"></fb-input>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="性别" prop="sex" :rule="{required: true}">
									<fb-select v-model="formData.sex"
											   :data="defaultForm.sex"
											   :placeholder="'请选择'"
											   clearable/>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="证件类型" prop="idtype" :rule='{required:true}'>
									<fb-select v-model="formData.idtype"
											   :placeholder="'请选择证件类型'"
											   :service="$svc.sys.dict.select"
											   :param="{'pdicCode': 'Y24'}"/>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<!-- 动态校验规则，三元表达式写法 -->
								<fb-form-item label="证件号（三元表达式）" prop="idcard"
											  :rule="formData.idtype === 'Y2401' ? [{required: true}, {type: 'idcard'}] : {}">
									<fb-input
										v-model="formData.idcard"
										placeholder="请输入证件号">
									</fb-input>
								</fb-form-item>
							</fb-col>
						</fb-row>
					</fb-col>

					<fb-col span="12">
						<fb-form-item label="头像">
							<fb-upload
								view="avatar"
								v-model="formData.profilePhoto"
								:service="$svc.sys.file"
								:file-list="fileList"
								:param="{'referType':'SYS1014'}"
								:accept="'.png,.jpeg,.jpg'"
								@on-change="fileListChange">
								<fb-icon name="add-normal" size="30"></fb-icon>
							</fb-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="简介" prop="entDesc" :rule="{required: true}">
							<fb-textarea rows="5" v-model="formData.entDesc"
										 type="text"
										 placeholder="请输入内容"
										 maxlength="200">
							</fb-textarea>
						</fb-form-item>
					</fb-col>
				</fb-row>
			</fb-form>

			<div style="margin-bottom: 10px">
				<template>
					<fb-button ref="buttonAdd" @on-click="handleAdd" style="margin-right: 20px" icon="add-circle">
						新增
					</fb-button>
					<fb-button ref="buttonDel" @on-click="handleDel" icon="reduce-circle">
						删除
					</fb-button>
				</template>
			</div>

			<template>
				<fb-simple-table
					ref="table"
					:data="formData.data"
					:columns="columns"
					:pk="primaryKey"
					:auto-load="false"
					:multiple="true"
					@on-row-select="handleTableSelect">
					<template v-slot:actions="{name, row}">
						<fb-link-group>
							<fb-link :click="()=>handleEdit(row)" label="update" type="primary"></fb-link>
						</fb-link-group>
					</template>
				</fb-simple-table>
			</template>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">保存</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>
</template>


<script>

	import Page from "@/util/Page"
	import TpDatepicker from "@/components/tp-datepicker/tp-datepicker.vue"

	export default {
		name: 'add',
		mixins: [
			Page
		],
		// 接收父组件的传参
		props: {
			param: {
				type: Object,
				require: false
			},
			parentPage: {
				type: Object,
				default: null
			}
		},
		// 组件
		components: {
			// 'component-a': ComponentA,
			TpDatepicker
		},
		// 创建方法
		created() {
			// 记录原来的默认值，用于表单重置
		},
		// 初始化方法
		mounted() {
			// 执行界面初始化
			this.init(this.param);

		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				// 附件列表
				fileList: [],
				// 证件号动态校验规则默认值，身份证校验
				idcardRule: [{required: true}, {type: 'idcard'}],
				// 表单数据
				formData: {
					name: '',
					sex: '',
					profilePhoto: '',
					idtype: 'Y2401',
					idcard: '',
					entDesc: '',
					data: [{
						'id': 1,
						'userName': '张三',
						'userGander': 1,
						'userTel': 13003282345,
						'desc': 'asdtgqwfgasdf'
					},
						{'id': 2, 'userName': '李四', 'userGander': 0, 'userTel': 19993282345}],
					list: []
				},
				primaryKey: "id",
				columns: [
					{
						name: 'id',
						hidden: true,
						width: 1,
					},
					{
						name: 'userName',
						label: '人员名称',
						sortable: false,
						align: 'left',
					}, {
						name: 'userGander',
						label: '性别',
						sortable: false,
						align: 'left',
						formatter: function (val, row) {
							val = val == 1 ? '男' : '女'
							return val;
						},
					}, {
						name: 'userTel',
						label: '手机号码',
						hidden: false,
						sortable: false,
						align: 'left',
					}, {
						name: 'desc',
						label: '描述',
						sortable: false,
						align: 'left',
					}, {
						name: '',
						label: '操作',
						sortable: false,
						align: 'right',
						slot: 'actions',
						width: '25%'
					},
				],
			}
		},

		// 方法
		methods: {
			/**
			 * 显示窗口
			 * param 参数
			 * title 标题
			 */
			init(param) {
				// 有值表示是修改方法
				if (param.id) { // 传ID表示修改
					this.formData.id = param.id;
					this.view()
				}
			},

			// 取消
			handleClose() {

				let param = {};
				this.closeTpDialog(param);
			},
			add() {
				let that = this;
				// 图片处理，后端list接收
				that.formData.list = this.fileList;
				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						if (that.formData.personId) {
							// 修改
							this.service.update(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									this.$message.success('修改成功');
								} else {
									// 服务器返回失败
									this.$message.error('修改失败:' + result.data.message)
								}
							})
						} else {
							// 新增，调用新增service方法
							this.service.org.add(that.formData).then((result) => {
								// 判断code
								if (result.code == 1) {
									this.$message.success('新增成功');

									// 1 单弹出框新增成功，关闭弹出框，param是回传的参数
									let param = {};
									this.closeTpDialog(param);

									// 2 tab 新增，不用关闭弹出框，将返回的主键放入到缓存，可以在tab切换时，获取到主表的主键
									// let id = result.data.id;
									// this.formData.id = id;
									// this.setPageParam(id);
								} else {
									// 服务器返回失败
									this.$message.error('新增失败')
								}
							})
						}
					}
				})
			},
			fileListChange(info) {
				console.log(info)
				if (info.file.res) {
					this.fileList.push(info.file.res.data);
				}
			},
			// 查询信息
			view() {
				// 调用新增service方法
				this.service.view({"id": this.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						this.formData = result.data

						// 图片回显
						this.fileList = result.data.list;
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})
			},

			idtypeChange(value) {
				let rule = []
				switch (value) {
					case 'Y2401':
						rule = [{required: true}, {type: 'idcard'}]
						break;
					case 'Y2402':
						rule = [{required: true}]
						break;
					default :
						rule = {}
						break;
				}
				this.idcardRule = rule
				this.formData.idcard1 = '';
			},

			handleAdd() {
				this.formData.data.push({
					'id': new Date().getTime(),
					'userName': '王' + new Date().getTime(),
					'userGander': 0,
					'userTel': 19993282348
				});
			},

			handleDel() {
				let rows = this.$refs.table.getSelectedRows()
				rows.forEach((val, ind) => {
					this.formData.data.some((item, i) => {
						if (item.id == val.id) {
							this.formData.data.splice(i, 1)
							//在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
							return true
						}
					})
				})
			},

			handleEdit(row) {
				let editData = this.formData.data.filter(item => item.id == row.id)
				editData[0].userName = "zhangsan";
			}

		}
	}
</script>

<style lang="less" scoped>

</style>
