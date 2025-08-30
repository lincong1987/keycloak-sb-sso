<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">

		<fb-card header="每日答题查看 未提交答案">
			<fb-container>
				<fb-text :color="colors.grey_9" size="xl">浙江汇金商贸有限公司
					<fb-tag type="danger" effect="light">未提交答案</fb-tag>
				</fb-text>

				<fb-container mt="16px" mb="24px" padding="16px" :background="colors.blue_1" radius="4px">
					<fb-property colon :split="2">
						<fb-property-item label="行政区划" span="2">asdas</fb-property-item>
						<fb-property-item label="日期" span="2">2019-06-06</fb-property-item>
						<fb-property-item label="答题人">企业负责人</fb-property-item>
						<fb-property-item label="提交时间">：2019-06-06</fb-property-item>
					</fb-property>
				</fb-container>

				<fb-form>
					<fb-fieldset label="以下做法中错误的是？"></fb-fieldset>
					<fb-form-item label="">
						<template slot="label-extra">
							<fb-tag type="warn" effect="light">多选</fb-tag>
						</template>
						<fb-checkbox-group v-model="formData.answer" vertical :data="[
							{value: 'A', label: 'A：与员工签订责任制自负的生死合同阿斯顿发的是噶但是噶尔沟通的方式噶帅哥哈大哥阿什顿个无辜的方式噶舒服噶多'},
							{value: 'B', label: 'B：与员工签订安全目标责任书萨嘎的方式噶大河然后对法国大使馆的风格'},
							{value: 'C', label: 'C：从事有限空间作业未进行安全培训'},
							{value: 'D', label: 'D：员工因个人违章导致伤亡，不享受工伤保险待遇'},
						]" readonly></fb-checkbox-group>
					</fb-form-item>
					<fb-table-layout>
						<fb-table-layout-cell>
							<fb-text :color="colors.grey_9" size="l">已选答案：{{ formatAnswer(formData.answer) || '--' }}</fb-text>
						</fb-table-layout-cell>
						<fb-table-layout-cell align="right"></fb-table-layout-cell>
					</fb-table-layout>
				</fb-form>
			</fb-container>
		</fb-card>


		<fb-card header="每日答题查看 对">
			<fb-container relative class="daily-question daily-question--right">
				<fb-container absolute class="daily-question__mark"></fb-container>
				<fb-text :color="colors.grey_9" size="xl">浙江汇金商贸有限公司</fb-text>
				<fb-container mt="16px" mb="24px" padding="16px" :background="colors.blue_1" radius="4px">
					<fb-property colon :split="2">
						<fb-property-item label="行政区划" span="2">asdas</fb-property-item>
						<fb-property-item label="日期" span="2">2019-06-06</fb-property-item>
						<fb-property-item label="答题人">企业负责人</fb-property-item>
						<fb-property-item label="提交时间">：2019-06-06</fb-property-item>
					</fb-property>
				</fb-container>

				<fb-form>
					<fb-fieldset label="以下做法中错误的是？"></fb-fieldset>
					<fb-form-item label="">
						<template slot="label-extra">
							<fb-tag type="warn" effect="light">多选</fb-tag>
						</template>
						<fb-checkbox-group v-model="formData.answer" vertical :data="[
							{value: 'A', label: 'A：与员工签订责任制自负的生死合同'},
							{value: 'B', label: 'B：与员工签订安全目标责任书'},
							{value: 'C', label: 'C：从事有限空间作业未进行安全培训'},
							{value: 'D', label: 'D：员工因个人违章导致伤亡，不享受工伤保险待遇'},
						]" readonly></fb-checkbox-group>
					</fb-form-item>

					<fb-table-layout>
						<fb-table-layout-cell>
							<fb-text :color="colors.grey_9" size="l">已选答案：{{ formatAnswer(formData.answer) || '--' }}</fb-text>
						</fb-table-layout-cell>
						<fb-table-layout-cell align="right"></fb-table-layout-cell>
					</fb-table-layout>
				</fb-form>
			</fb-container>
		</fb-card>


		<fb-card header="每日答题查看 错">
			<fb-container relative class="daily-question daily-question--wrong">
				<fb-container absolute class="daily-question__mark"></fb-container>
				<fb-text :color="colors.grey_9" size="xl">浙江汇金商贸有限公司</fb-text>
				<fb-container mt="16px" mb="24px" padding="16px" :background="colors.blue_1" radius="4px">
					<fb-property colon :split="2">
						<fb-property-item label="行政区划" span="2">asdas</fb-property-item>
						<fb-property-item label="日期" span="2">2019-06-06</fb-property-item>
						<fb-property-item label="答题人">企业负责人</fb-property-item>
						<fb-property-item label="提交时间">：2019-06-06</fb-property-item>
					</fb-property>
				</fb-container>

				<fb-form>
					<fb-fieldset label="以下做法中错误的是？"></fb-fieldset>
					<fb-form-item label="">
						<template slot="label-extra">
							<fb-tag type="warn" effect="light">多选</fb-tag>
						</template>
						<fb-checkbox-group v-model="formData.answer" vertical :data="[
							{value: 'A', label: 'A：与员工签订责任制自负的生死合同'},
							{value: 'B', label: 'B：与员工签订安全目标责任书'},
							{value: 'C', label: 'C：从事有限空间作业未进行安全培训'},
							{value: 'D', label: 'D：员工因个人违章导致伤亡，不享受工伤保险待遇'},
						]" readonly></fb-checkbox-group>
					</fb-form-item>

					<fb-table-layout>
						<fb-table-layout-cell>
							<fb-text :color="colors.grey_9" size="l">已选答案：{{ formatAnswer(formData.answer) || '--' }}</fb-text>
						</fb-table-layout-cell>
						<fb-table-layout-cell align="right">
							<fb-text :color="colors.red_5" size="l">正确答案：{{ formatAnswer(formData.answer) || '--' }}</fb-text>
						</fb-table-layout-cell>
					</fb-table-layout>
				</fb-form>
			</fb-container>
		</fb-card>


		</div>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">取消</fb-button>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>
	</div>

</template>

<script>

	import Page from "@/util/Page"
	import {isArray} from 'lodash-es'

	export default {
		name: 'add-basicinfo',
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
			// 执行界面初始化

		},
		data() {
			return {
				// 请求的 service
				service: app.$svc.sys.demo,
				// 表单数据
				formData: {
					name: '',
					answer: [],
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
				if (param) {
					this.formData.entId = param;
					this.view()
				}
			},

			// 取消
			handleClose() {
				this.$store.commit('tabbar/removeNow');
			},

			// 查询信息
			view() {
				// 调用新增service方法
				/*this.service.view({"entId": this.formData.entId}).then((result) => {
                    // 判断code
                    if (result.code == 1) {
                        this.formData = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('错误提示:' + result.message)
                    }
                })*/
			},

			// 查询信息
			formatAnswer(val) {
				if (isArray(val)) {
					return val.join(', ')
				}
				return val
			},
		}
	}
</script>


<style scoped lang="less">


	.daily-question {
		.daily-question__mark {
			right:  16px;
			top:    0;
			height: 77px;
			width:  88px;
		}

		&--right {
			.daily-question__mark {
				background: url("../../../assets/img/question/icon_daily_question_right.png") no-repeat center;
			}
		}

		&--wrong {
			.daily-question__mark {
				background: url("../../../assets/img/question/icon_daily_question_wrong.png") no-repeat center;
			}
		}
	}

</style>

