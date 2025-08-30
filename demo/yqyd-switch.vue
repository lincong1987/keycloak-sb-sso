<template>
	<!--<fb-page-header
		title=""
		sub-title="">-->
	<fb-page-tree-table title="通用设置">
		<template slot="tree">
			<fb-tree
				style="overflow: auto"
				v-autoheight="152"
				ref="menuTree"
				v-model="formData.industryTypeCode"
				:service="$svc.sys.dict.tree"
				:param="{'dicCode': 'SYS16'}"
				:reader="{value: 'id', label: 'text'}"
				placeholder="请选择"
				multiple
				clearable></fb-tree>
		</template>
		<template slot="table">
			<fb-container v-autoheight="152" overflow="auto">
				<!-- 大类 -->
				<fb-container v-for="(witem, windex) in formData.data" :key="windex">
					<fb-fieldset :label="witem.entinfoKindName"></fb-fieldset>
					<!-- 小类 -->
					<fb-switch-accordion-group v-for="(item, index) in witem.list" :key="index">
						<!-- 小类开关 -->
						<fb-switch-accordion :title="item.industryName" v-model="item.zk" @on-change="change">
							<!-- 小类form -->
							<fb-form :ref="`yqyd_fbform_${windex}_${index}`" :label-width="110">
								<fb-row>
									<fb-col span="12">
										<fb-form-item label="必填" prop="mustFilled" :rule="[{required: true}]">
											<fb-radio-group v-model="item.mustFilled"
															:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
															:reader="{label:'name', value:'id'}"></fb-radio-group>
										</fb-form-item>
									</fb-col>
									<fb-col span="12">
										<fb-form-item label="维护对象" prop="maintObject" :rule="[{required: true}]">
											<fb-input v-model.number="item.maintObject"/>
										</fb-form-item>
									</fb-col>
								</fb-row>
								<fb-row>
									<fb-col span="12">
										<fb-form-item label="有频次要求" prop="freqed" :rule="[{required: true}]">
											<fb-radio-group v-model="item.freqed"
															:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
															:reader="{label:'name', value:'id'}"></fb-radio-group>
										</fb-form-item>
									</fb-col>
									<fb-col span="12">
										<fb-form-item label="频次" prop="freq"
													  :rule='[{required: true}, {type: "integer"}]'>
											<fb-input v-model.number="item.freq"/>
										</fb-form-item>
									</fb-col>
								</fb-row>
								<fb-row>
									<fb-col span="12">
										<fb-form-item label="分值" prop="score" :rule="[{required: true}]">
											<fb-radio-group v-model="item.score"
															:data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
															:reader="{label:'name', value:'id'}"></fb-radio-group>
										</fb-form-item>
									</fb-col>
									<fb-col span="12">
										<fb-form-item label="总分">
											<fb-input v-model.number="item.totalScore"/>
										</fb-form-item>
									</fb-col>
								</fb-row>
								<fb-row>
									<fb-col span="12">
										<fb-form-item label="占比">
											<fb-input v-model.number="item.rate"/>
										</fb-form-item>
									</fb-col>
								</fb-row>
							</fb-form>
						</fb-switch-accordion>
					</fb-switch-accordion-group>
				</fb-container>
			</fb-container>
		</template>


		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">保存</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</fb-page-tree-table>

</template>

<script>
	import Page from "@/util/Page"
	import FbContainer from "fb-ui/packages/components/container/src/FbContainer";
	import {each} from "lodash-es"

	export default {
		name: 'add-basicinfo',
		mixins: [
			Page
		],
		// 接收父组件的传参
		props: {},
		// 组件
		components: {
			FbContainer
			// 'component-a': ComponentA,
		},
		// 创建方法
		created() {
			// 记录原来的默认值，用于表单重置
		},
		// 初始化方法
		mounted() {
			// 执行界面初始化
			this.init();
		},
		data() {
			return {
				jbxx: {
					jbxx: true,
					wxhxp: false,
					yhbz: false,
					ks: false,
					jzsg: false,
				},

				auscjg: {
					auscjg1: false,
					auscjg2: false,
					auscjg3: false,
					auscjg4: false,
				},
				// 请求的 service
				service: app.$svc.sys.demo,
				// 表单数据
				formData: {
					data: [],
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
			init() {
				this.view()
			},

			// 取消
			handleClose() {
				this.$store.commit('tabbar/removeNow');
			},

			add() {
				let that = this;
				// 界面校验，全部通过在提交
				each(this.$refs, (item, key) => {
					if (key.indexOf("yqyd_fbform_") >= 0) {
						// 遍历form
						if (item[0].$parent._data.myValue === true) {
							// switch展开，需要校验
							item[0].validate((result) => {
								if (result === true) {
									// todo 业务逻辑
									if (that.formData.dangerId) {
										// 调用新增service方法
										that.service.update(that.formData).then((result) => {
											// 判断code
											if (result.code == 1) {
												that.$message.success('修改成功');
											} else {
												// 服务器返回失败
												that.$message.error('修改失败:' + result.message)
											}
											;
											that.updateCount = 0;
										})
									} else {
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
								}
							})
						} else {
							// switch关闭，不需要校验

						}
					}
				});

			},

			// 查询信息
			view() {
				let that = this;
				// 调用新增service方法
				this.service.yiydview({"id": this.formData.id}).then((result) => {

					// 判断code
					if (result.code == 1) {
						that.formData.data = result.data
					} else {
						// 服务器返回失败
						that.$message.error('错误提示:' + result.message)
					}
				})
			},

			change(val) {
				console.log(val)
			},
		}
	}
</script>

<style scoped lang="less">


</style>
