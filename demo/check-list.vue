<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">

			<fb-page-header title="安全检查">

				<fb-card>
					<fb-button @on-click="handleCheckList">新增安全检查</fb-button>
					<fb-button @on-click="handleViewCheckList">查看安全检查</fb-button>
				</fb-card>


				<fb-dialog ref="addCheckList" title="新增安全检查" width="820">

					<fb-steps current="1">
						<fb-step label="检查基本信息"></fb-step>
						<fb-step label="清单详情"></fb-step>
						<fb-step label="检查清单隐患"></fb-step>
						<fb-step label="隐患列表"></fb-step>
						<fb-step label="检查情况"></fb-step>
					</fb-steps>

					<fb-divider></fb-divider>


					<fb-container>
						<fb-container>
							<fb-text>1、需停机调整圆锥破碎机排矿口时，应先用铅锤或其他工具测定，然后停车和切断电源，方可进行调整。若须进入机内测定排矿口，应有必要的安全防护措施。</fb-text>
							<fb-link label="参考标准>>" type="primary"></fb-link>
						</fb-container>

						<fb-table-layout>
							<fb-table-layout-cell>
								<fb-radio-group
									:data="[{label:'通过', value: '1'}, {label:'不通过', value: '0'}]"></fb-radio-group>
							</fb-table-layout-cell>
							<fb-table-layout-cell align="right">
								<fb-button type="link" icon="photo" @on-click="handlePhotoButtonClick">照片：{{ num }}
								</fb-button>
							</fb-table-layout-cell>
						</fb-table-layout>

						<fb-divider></fb-divider>
					</fb-container>

					<fb-container>
						<fb-container>
							<fb-text>1、需停机调整圆锥破碎机排矿口时，应先用铅锤或其他工具测定，然后停车和切断电源，方可进行调整。若须进入机内测定排矿口，应有必要的安全防护措施。</fb-text>
							<fb-link label="参考标准>>" type="primary"></fb-link>
						</fb-container>

						<fb-table-layout>
							<fb-table-layout-cell>
								<fb-radio-group
									:data="[{label:'通过', value: '1'}, {label:'不通过', value: '0'}]"></fb-radio-group>
							</fb-table-layout-cell>
							<fb-table-layout-cell align="right">
								<fb-button type="link" icon="photo" @on-click="handlePhotoButtonClick">照片：{{ num }}
								</fb-button>
							</fb-table-layout-cell>
						</fb-table-layout>

						<fb-divider></fb-divider>
					</fb-container>


				</fb-dialog>


				<fb-dialog ref="viewCheckList" title="查看安全检查">

					<fb-container>

						<fb-tabs type="card">
							<fb-tab label="检查基本信息">
								<fb-container pt="16">
									<fb-container>
										<fb-text>1、需停机调整圆锥破碎机排矿口时，应先用铅锤或其他工具测定，然后停车和切断电源，方可进行调整。若须进入机内测定排矿口，应有必要的安全防护措施。
										</fb-text>
										<fb-link label="参考标准>>" type="primary"></fb-link>
									</fb-container>

									<fb-table-layout>
										<fb-table-layout-cell>
											<fb-text :color="colors.text_secondary">结果：</fb-text>
											<fb-text :color="colors.success">通过</fb-text>
										</fb-table-layout-cell>
										<fb-table-layout-cell align="right">
											<fb-button type="link" icon="photo" @on-click="handlePhotoButtonClick">照片：{{
												num
												}}
											</fb-button>
										</fb-table-layout-cell>
									</fb-table-layout>

									<fb-divider></fb-divider>
								</fb-container>
							</fb-tab>


							<fb-tab label="清单详情">
								<fb-container pt="16">
									<fb-container>
										<fb-text>1、需停机调整圆锥破碎机排矿口时，应先用铅锤或其他工具测定，然后停车和切断电源，方可进行调整。若须进入机内测定排矿口，应有必要的安全防护措施。
										</fb-text>
										<fb-link label="参考标准>>" type="primary"></fb-link>
									</fb-container>

									<fb-table-layout>
										<fb-table-layout-cell>
											<fb-text :color="colors.text_secondary">结果：</fb-text>
											<fb-text :color="colors.danger">通过</fb-text>
											<fb-text :color="colors.text_emphasize">不适用</fb-text>
										</fb-table-layout-cell>
										<fb-table-layout-cell align="right">
											<fb-button type="link" icon="photo" @on-click="handlePhotoButtonClick">照片：{{
												num
												}}
											</fb-button>
										</fb-table-layout-cell>
									</fb-table-layout>

									<fb-divider></fb-divider>
								</fb-container>
							</fb-tab>

							<fb-tab label="隐患列表"></fb-tab>
							<fb-tab label="检查情况"></fb-tab>
						</fb-tabs>


					</fb-container>

				</fb-dialog>


				<fb-dialog ref="photoUploader" title="照片">

					<fb-container>

						<fb-fieldset label="照片"></fb-fieldset>

						<fb-form>
							<fb-form-item label="照片">
								<fb-upload view="image"></fb-upload>
							</fb-form-item>
						</fb-form>

					</fb-container>

				</fb-dialog>

			</fb-page-header>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button v-if="parentPage.tabIndex > 0" @on-click="prev">上一步</fb-button>
			<fb-button v-if="parentPage.tabIndex == parentPage.tabSteps.length - 1" type="primary"
					   style="margin-left: 12px" @on-click="$message.success('成功!')">
				完成
			</fb-button>
			<fb-button v-if="parentPage.tabIndex < parentPage.tabSteps.length - 1" type="primary"
					   style="margin-left: 12px" @on-click="next">下一步
			</fb-button>
		</div>

	</div>
</template>

<script>
	import Page from "@/util/Page"

	export default {
		mixins: [Page],
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
		components: {},
		mounted() {
		},
		data() {
			return {
				form: {
					userId: '',
					userName: '',
					userGander: '',
					userTel: '',
				},
				actions: {},

				num: 1,
			}
		},
		methods: {

			handleCheckList() {
				this.$refs.addCheckList.show()
			},

			handlePhotoButtonClick() {
				this.$refs.photoUploader.show()
			},

			handleViewCheckList() {
				this.$refs.viewCheckList.show()
			},
			next() {
				alert("上 yi bu le 1111")
			},
			prev() {
				alert("上 yi bu le 1111")
			},

		},

	}
</script>

<style scoped lang="less">
</style>
