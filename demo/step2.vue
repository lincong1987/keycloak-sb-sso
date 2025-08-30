<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="180">
				<fb-row>
					<fb-col span="12">
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="名称2" prop="entFullName" :rule="{required: true}">
									<fb-input v-model="formData.name"></fb-input>
								</fb-form-item>
							</fb-col>
						</fb-row>
						<fb-row>
							<fb-col span="24">
								<fb-form-item label="性别2" prop="sex" :rule="{required: true}">
									<fb-select v-model="formData.sex"
											   :data="defaultForm.sex"
											   :placeholder="'请选择'"
											   @on-change="sexChange"
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
							<tp-upload
								view="avatar"
								v-model="file.fileList"
								:service="$svc.sys.file"
								:param="{'referType': file.referType}"
								:referid="formData.id"
								:accept="'.png,.jpeg,.jpg'"
							></tp-upload>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="证件类型" prop="idtype1" :rule='{required:true}'>
							<fb-select v-model="formData.idtype1"
									   :placeholder="'请选择证件类型'"
									   :service="$svc.sys.dict.select"
									   :param="{'pdicCode': 'Y24'}"
									   @on-change="idtypeChange"/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<!-- 动态校验规则，方法写法 -->
						<fb-form-item label="证件号（方法）" prop="idcard1" :rule="idcardRule">
							<fb-input
								v-model="formData.idcard1"
								placeholder="请输入证件号">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="统一社会信用代码" prop="entUnifiedCode"
									  :rule="[{required: true}, {type: 'unicode'}]">
							<fb-input v-model="formData.entUnifiedCode" placeholder="请输入统一社会信用代码">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="监管分类1">
							<fb-tree-select
								ref="regulatoryTypeCodeTree"
								v-model="formData.regulatoryTypeCode"
								:service="$svc.sys.dict.tree"
								:param="{'dicCode': 'E10'}"
								:reader="{value: 'id', label: 'text'}"
								:do-check="'s'"
								:do-un-check="'s'"
								placeholder="请选择"
								@on-change="regulatoryChange"
								multiple
								clearable></fb-tree-select>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="监管等级">
							<fb-select
								v-model="formData.superviseGrade"
								:service="$svc.sys.dict.select"
								:param="{'pdicCode': 'E09'}"
								placeholder="请选择"
								@on-change="regulatoryChange"
								multiple
								clearable/>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="日期" prop="date1" :rule="{required: true}">
							<tp-datepicker v-model="formData.date1" format="YYYY-MM-DD HH:mm:ss"
										   value-format="YYYYMMDDHHmmss" placeholder="请选择时间日期"
										   :min-date="new Date('2021-03-01')" :max-date="new Date('2021-03-15')"
										   clearable/>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="注册日期" prop="date" :rule="{required: true}">
							<tp-datepicker v-model="formData.date" format="YYYY-MM"
										   value-format="YYYYMMDDHHmmss" clearable></tp-datepicker>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="星期" prop="week" :rule="{required: true}">
							<fb-select v-model="formData.week"
									   :data="defaultForm.week"
									   :placeholder="'请选择'"
									   clearable/>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="注册资金" prop="regFund" :rule="[{required: true}, {type: 'number'}]">
							<fb-input placeholder="请输入内容" v-model="formData.regFund">
								<template slot="append">万元</template>
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="联系人姓名" prop="linkPsnName" :rule="[{required: true}]">
							<fb-input v-model="formData.linkPsnName"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="联系手机" prop="linkPsnTel" :rule='[{required: true}, {type: "mobile"}]'>
							<fb-input v-model="formData.linkPsnTel"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="机构树（完整）">
							<fb-tree-select v-model="formData.allDeptTree"
											:service="$svc.sys.dept.org.allTree"
											:param="{}"
											:placeholder="'请选择'"
											:reader="{value:'id', label: 'text'}"
											clearable>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="机构树（本级及下级）">
							<fb-tree-select v-model="formData.deptTree"
											:service="$svc.sys.dept.org.tree"
											:param="{}"
											:placeholder="'请选择'"
											:reader="{value:'id', label: 'text'}"
											clearable>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="企业部门树（完整）">
							<fb-tree-select
								ref="entTree1"
								v-model="formData.entTree1"
								:service="$svc.sys.dept.ent.allTree"
								:param="{}"
								:reader="{value: 'id', label: 'text'}"
								:do-check="'s'"
								:do-un-check="'s'"
								placeholder="请选择"
								@on-change="regulatoryChange"
								multiple
								clearable></fb-tree-select>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="企业部门树（本级及下级）">
							<fb-tree-select
								ref="entTree2"
								v-model="formData.entTree2"
								:service="$svc.sys.dept.ent.tree"
								:param="{}"
								:reader="{value: 'id', label: 'text'}"
								:do-check="'s'"
								:do-un-check="'s'"
								placeholder="请选择"
								multiple
								clearable></fb-tree-select>
						</fb-form-item>
					</fb-col>
				</fb-row>


				<fb-row>
					<fb-col span="12">
						<fb-form-item label="行政区划（完整）" prop="addrCode" :rule="[{required: true}]">
							<fb-tree-select
								ref="addrCodeTree"
								v-model="formData.addrCode"
								:service="$svc.sys.city.tree"
								:param="{'sync': 1, 'expand': true, 'cityId': '-1'}"
								:reader="{value:'extend02', label: 'text'}"
								:placeholder="'请选择'"
								:header-format="cityTreeHeaderFormat"
								clearable>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="行政区划（本级及下级）" prop="cityCode" :rule="{required: true}">
							<fb-tree-select
								ref="cityTree"
								v-model="formData.cityCode"
								:service="$svc.sys.dept.deptCityTree"
								:param="{'sync': 1, 'expand': true}"
								:reader="{value:'extend02', label: 'text'}"
								:placeholder="'请选择'"
								clearable>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="行政区划（两级）" prop="addrCode" :rule="[{required: true}]">
							<fb-tree-select
								ref="addrCodeTree"
								v-model="formData.addrCode"
								:service="$svc.sys.city.selectTwoLevel"
								:param="{'sync': 1, 'expand': true, 'cityId': '-1'}"
								:reader="{value:'extend02', label: 'text'}"
								:placeholder="'请选择'"
								:header-format="cityTreeHeaderFormat"
								clearable>
							</fb-tree-select>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-form-item label="email" prop="email" :rule="[{required:true}, {type: 'email'}]">
							<fb-input v-model="formData.email"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="是否启用">
							<fb-radio-group v-model="formData.enabled"
											:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
											:reader="{label:'name', value:'id'}"></fb-radio-group>
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
		</div>

		<div class="tp-dialog-bottom">
			<fb-button v-if="parentPage.tabIndex > 0" @on-click="prev">上一步</fb-button>
			<fb-button v-if="parentPage.tabIndex == parentPage.tabSteps.length - 1" type="primary" style="margin-left: 12px" @on-click="$message.success('成功!')" >
				完成
			</fb-button>
			<fb-button v-if="parentPage.tabIndex < parentPage.tabSteps.length - 1" type="primary" style="margin-left: 12px" @on-click="next">下一步</fb-button>
		</div>

	</div>
</template>


<script>

	import Page from "@/util/Page"

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

		},
		// 创建方法
		created() {
			// 记录原来的默认值，用于表单重置
		},
		activated() {
			console.log('我这个页面显示就会执行');
		},
		// 初始化方法
		mounted() {

			// Map demo
			// let mapData = new Map();
			// mapData.set('key1', 'value1')
			// mapData.set('key2', 'value2')
			// mapData.set('key1', 'value3')
			// let aaa = this.formatMap(mapData);

			// 执行界面初始化
			// this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				// 附件列表
				file: {
					referType: 'SYS1014',
					fileList: [],
				},
				// 证件号动态校验规则默认值，身份证校验
				idcardRule: [{required: true}, {type: 'idcard'}],
				// 表单数据
				formData: {
					id: '',
					name: '',
					sex: '',
					week: '',
					idtype: 'Y2401',
					idcard: '',
					idtype1: 'Y2401',
					idcard1: '',
					entUnifiedCode: '',
					regulatoryTypeCode: [],
					regulatoryTypeName: [],
					superviseGrade: [],
					entTree1: [],
					entTree2: [],
					type: '',
					deptTree: '',
					allDeptTree: '',
					date: '',
					date1: this.currentDate("YYYYMMDDHHmmss"),
					regFund: '',
					email: '',
					linkPsnName: '',
					linkPsnTel: '',
					addrCode: '',
					cityCode: '',
					enabled: 1,
					entDesc: '',
				},
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

			next() {
				let that = this;

				// 图片处理，后端list接收
				that.formData.file = that.file;
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

			prev(){
				this.stepPrev();
			},

			// 查询信息
			view() {
				// 调用新增service方法
				this.service.view({"id": this.formData.id}).then((result) => {
					// 判断code
					if (result.code == 1) {
						this.formData = result.data
						this.formData.superviseGrade = result.data.superviseGrade.split(",");
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})
			},

			sexChange(value) {
				console.log(value)
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

			regulatoryChange(value, node) {
				console.log(value, node)
			},


		},
		watch:{
			/**
			 * 监听父组件的值的变化
			 * @param newValue
			 * @param oldValue
			 */
			'parentPage.tabIndex': {
				handler(newValue, oldValue) {
					console.log("执行界面的手动调用函数操作")
				},
				// 立即生效
				immediate: true
			}
		}
	}
</script>

<style lang="less" scoped>

</style>
