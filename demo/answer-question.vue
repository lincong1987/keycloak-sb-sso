<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">

			<fb-card header="每日答题">
				<fb-container>
					<fb-form ref="fbform" >
						<fb-fieldset label="答题人"></fb-fieldset>
						<fb-form-item label="" prop="name" :rule="{required: true}">
							<template slot="label-extra">
								<fb-tag type="warn" effect="light">多选</fb-tag>
							</template>
							<fb-input width="240" v-model="formData.name" :placeholder="'请输入答题人'"></fb-input>
						</fb-form-item>
						<fb-fieldset label="以下做法中错误的是？"></fb-fieldset>
						<fb-form-item label="" prop="answer" :rule="{required: true}">
							<template slot="label-extra">
								<fb-tag type="warn" effect="light">多选</fb-tag>
							</template>
							<fb-checkbox-group v-model="formData.answer" vertical :data="[
							{value: 'A', label: 'A：与员工签订责任制自负的生死合同'},
							{value: 'B', label: 'B：与员工签订安全目标责任书'},
							{value: 'C', label: 'C：从事有限空间作业未进行安全培训'},
							{value: 'D', label: 'D：员工因个人违章导致伤亡，不享受工伤保险待遇'},
						]" :placeholder="'请选择您的答案'"></fb-checkbox-group>
						</fb-form-item>

						<fb-table-layout>
							<fb-table-layout-cell>
								<fb-text :color="colors.grey_9" size="l">已选答案：{{ formatAnswer(formData.answer) || '--'
									}}
								</fb-text>
							</fb-table-layout-cell>
							<fb-table-layout-cell align="right"></fb-table-layout-cell>
						</fb-table-layout>
					</fb-form>
				</fb-container>
			</fb-card>

		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">提交</fb-button>
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

			},

			// 取消
			handleClose() {
				this.closeTpDialog();
			},

			add() {
				let that = this
				// 界面校验
				this.$refs.fbform.validate((result) => {
					if (result === true) {
						// 调用新增service方法
						that.service.add(that.formData).then((result) => {
							// 判断code
							if (result.code == 1) {
								that.$message.success('新增成功');
								let dangerId = result.data;
								this.formData.dangerId = dangerId;
							} else {
								// 服务器返回失败
								that.$message.error('新增失败:' + result.message)
							}
							;
							that.updateCount = 0;
						})
					}
				})
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

<style lang="less" scoped>

</style>
