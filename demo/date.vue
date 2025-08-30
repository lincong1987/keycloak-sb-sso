<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :rule="rules" :label-width="180">
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="注册日期" prop="date" :rule="{required: true}">
							value-format：{{formData.date}}
							<tp-datepicker v-model="formData.date" format="YYYY-MM-DD"
										   value-format="YYYYMMDD" clearable></tp-datepicker>
						</fb-form-item>
					</fb-col>
					<!--		<fb-col span="24">
								<fb-form-item label="日期" prop="date1" :rule="{required: true}">
									value-format：{{formData.date2}}
									<tp-datepicker v-model="formData.date2" format="YYYY-MM-DD HH:mm:ss"
												   value-format="YYYYMMDDHHmmss" placeholder="请选择时间日期"
												   max-range="5D" mode="range"
												   clearable/>
								</fb-form-item>
					</fb-col>-->
				</fb-row>
<!--				<fb-row>
					<fb-col span="22">
						<fb-form-item label="日期(范围)">
							<tp-datepicker v-model="formData.date2" format="YYYY-MM-DD HH:mm:ss"
										   value-format="YYYYMMDDHHmmss" placeholder="请选择时间日期"
										   max-range="3D" mode="range"
										   clearable/>
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
					<fb-col span="24">
						<fb-form-item label="富文本">
							<fb-editor v-model="formData.entDesc"></fb-editor>
						</fb-form-item>
					</fb-col>
				</fb-row>-->
			</fb-form>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="set">date</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set2">20210802</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set3">2021-08-02</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set4">2021/08/02</fb-button>

<!--			<fb-button style="margin-right: 12px" type="primary" @on-click="set5">[date]</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set6">[20210802]</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set7">[2021-08-02]</fb-button>
			<fb-button style="margin-right: 12px" type="primary" @on-click="set8">[2021/08/02]</fb-button>-->

			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>
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
		// 初始化方法
		mounted() {

			// Map demo
			// let mapData = new Map();
			// mapData.set('key1', 'value1')
			// mapData.set('key2', 'value2')
			// mapData.set('key1', 'value3')
			// let aaa = this.formatMap(mapData);

			// 执行界面初始化
			this.init(this.param);

		},
		data() {
			return {
				aaa: false,
				// 请求的 service
				service: this.$svc.sys.demo,
				// 附件列表
				file: {
					referType: 'SYS1014',
					fileList: [],
				},
				// 证件号动态校验规则默认值，身份证校验
				idcardRule: [{required: true}, {type: 'idcard'}],
				// 人员多选
				personNames: [
					{
						value: 6,
						label: '张艳林',
					},
				],
				// 行政区划树加载数据
				cityData: [],
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
					xs1: '',
					xs2: '',
					entUnifiedCode: '',
					regulatoryTypeCode: [],
					regulatoryTypeName: [],
					superviseGrade: [],
					entTree1: [],
					entTree2: [],
					type: '',
					deptTree: '',
					allDeptTree: '',
					date: new Date(), // "20210803"
					date1: this.currentDate("YYYYMMDDHHmmss"),
					date2: [],
					regFund: '',
					email: '',
					linkPsnName: '',
					linkPsnTel: '',
					addrCode: '',
					cityCode: '',
					enabled: 1,
					entDesc: '',
				},
				rules: {
					'xs': {
						// 自定义 校验方法，方法名与参数固定不变
						validator: (rule, value, callback, source, options) => {
							// 可通过 _this 获取上下文
							if (value) {
								// 校验未通过，返回错误信息
								let pattern = /^\d+(.\d{1,2})?$/
								if (!pattern.test(value)) {
									return callback('请输入最多两位小数的数字');
								}
							}
							// 校验通过，返回空参数
							return callback();
						}
					}
				}
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

			},
			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			add() {


			},
			// 查询信息
			view() {


			},
			set() {
				this.formData.date = new Date()
			},
			set2(){
				this.formData.date = "20210802"
			},
			set3(){
				this.formData.date = "2021-08-02"
			},
			set4(){
				this.formData.date = "2021/08/02"
			},
			set5(){
				this.formData.date2 = [new Date(), new Date()]
			},
			set6(){
				this.formData.date2 = ['20210801', '20210803']
			},
			set7(){
				this.formData.date2 = ['2021-08-01', '2021-08-03']
			},
			set8(){
				this.formData.date2 = ['2021/08/01', '2021/08/03']
			}


		}
	}
</script>

<style lang="less" scoped>

</style>
