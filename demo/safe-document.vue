<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform" :label-width="180">
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="文书号" prop="docNo" :rule="{required: true}">
							<fb-input v-model="formData.docNo"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>


				<fb-row>
					<fb-col span="12">
						<fb-form-item label="企业名称" prop="enterpriseName" :rule="{required: true}">
							<fb-input v-model="formData.enterpriseName"></fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="检查日期" prop="checkDate" :rule="{required: true}">
							<fb-input v-model="formData.checkDate"></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<!-- 动态校验规则，方法写法 -->
						<fb-form-item label="隐患内容" prop="hdSummary">
							<fb-input
								v-model="formData.hdSummary"
								placeholder="请输入隐患内容">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="规定" prop="accordRule"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.accordRule" placeholder="请输入规定">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="处理决定" prop="localDispose"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.localDispose" placeholder="请输入处理决定">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="省份" prop="districtName"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.districtName" placeholder="请输入省份">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="管理部门" prop="deptFullname"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.deptFullname" placeholder="请输入管理部门">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="市" prop="courtName"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.courtName" placeholder="请输入市">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="检查人1" prop="undertaker1"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.undertaker1" placeholder="请输入检查人1">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="编号" prop="num1"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.num1" placeholder="请输入编号">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="检查人2" prop="undertaker2"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.undertaker2" placeholder="请输入检查人2">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="编号" prop="num2"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.num2" placeholder="请输入编号">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="年份" prop="year"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.year" placeholder="请输入年份">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="月份" prop="month"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.month" placeholder="请输入月份">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="12">
						<fb-form-item label="日" prop="day"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.day" placeholder="请输入日">
							</fb-input>
						</fb-form-item>
					</fb-col>
					<fb-col span="12">
						<fb-form-item label="签字人" prop="name"
									  :rule="[{required: true}]">
							<fb-input v-model="formData.name" placeholder="请输入name">
							</fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>


			</fb-form>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">生成文书</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
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
		// 初始化方法
		mounted() {

		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,

				// 表单数据
				formData: {
					docNo: '（浔）应急立[2020]0002-石淙镇安监中心001号',
					enterpriseName: '湖州鼎旺玻璃有限公司',
					checkDate: '2020年09月28日',
					hdSummary: '安全生产规章制度类违法阿迪个撒定时发送的听歌舞台上的噶是一个让更多发生变化的是符合人特温柔又玩儿也让他也如图二胎月u克哦怕人言可畏片可以喝哦收款人哟呃客人提供肯定是；莲花峰克哦虽然烤红薯的反馈工棚开始肉配上柔软收到了发个快乐圣诞狂欢揉耳廓喝哦是看过呃开始认购额上课然后饿开始肉配好看是；了客人黑色热裤和平时人东方噶是东方噶收到发噶舒服的噶收到国外儿童噶都是噶是的噶是东方噶我如果他是东方噶是撒个特瓦股份阿斯顿国外儿童噶多少分',
					accordRule: '上级阿斯顿该咋的噶收到发个办给娃饿噶收到各方',
					localDispose: '阿哥让对方是个如何呀东方好的风格设计儿童设计的是符合手机号娃儿噶收到发',
					districtName: '浙江省',
					deptFullname: '应急管理局管理部',
					courtName: '杭州市',
					undertaker1: '欧阳凌飞',
					num1: '45892-349222',
					undertaker2: '张琦',
					num2: '45892-349223',
					year: '2021',
					month: '04',
					day: '01',
					name: '张三',
				},

			}
		},

		// 方法
		methods: {

			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},

			add() {
				let that = this;

				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						// 新增，调用新增service方法
						this.service.safeDocument2(that.formData).then((result) => {
							app.download(result)
							// 判断code
							// if (result.code == 1) {
							// 	// 关闭并弹出查看文书页面
							// 	this.closeTpDialog(result.data);
							// 	this.$message.success('文书生成成功');
							// } else {
							// 	// 服务器返回失败
							// 	this.$message.error('文书生成失败')
							// }
						})
					}
				})
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
