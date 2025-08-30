<template>
	<div>


		<fb-fieldset label="模板下载"></fb-fieldset>

		<fb-container mt="16px" mb="16px">
			<fb-alert type="warning" message="请严格按照导入模板的格式进行导入，每一列的数量和顺序均不允许变动，否则将会导入失败。"></fb-alert>
		</fb-container>
		<fb-container mt="16px" mb="16px">
			<fb-row gutter="16">
				<fb-col>
					<fb-container valign="center" height="48px" :background="colors.blue_1" radius="4px">
						<fb-table-layout>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="file-text" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
							<fb-table-layout-cell ellipsis>
								<fb-link :color="colors.primary" :href="$svc.platform.dwonloadExcel({'path':'/exceltemplat', 'fileName': '导入测试.xlsx'})"
										 label="导入模板下载（LEC）11111"
										 type="primary"></fb-link>
							</fb-table-layout-cell>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="download" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
						</fb-table-layout>
					</fb-container>
				</fb-col>
				<fb-col>
					<fb-container valign="center" height="48px" :background="colors.blue_1" radius="4px">
						<fb-table-layout>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="file-text" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
							<fb-table-layout-cell ellipsis>
								<fb-link :color="colors.primary" label="导入模板下载（LS）"
										 :href="$svc.platform.dwonloadExcel({'path':'/exceltemplat', 'fileName': '导入测试2.xlsx'})"
										 type="primary"></fb-link>
							</fb-table-layout-cell>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="download" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
						</fb-table-layout>
					</fb-container>
				</fb-col>
				<fb-col>
					<fb-container valign="center" height="48px" :background="colors.blue_1" radius="4px">
						<fb-table-layout>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="file-text" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
							<fb-table-layout-cell ellipsis>
								<fb-link :color="colors.primary" label="导入模板下载（MES）"
										 :href="$svc.platform.dwonloadExcel({'path':'/exceltemplat', 'fileName': '导入测试2.xlsx'})"
										 type="primary"></fb-link>
							</fb-table-layout-cell>
							<fb-table-layout-cell width="40px" align="center">
								<fb-icon name="download" :color="colors.primary"></fb-icon>
							</fb-table-layout-cell>
						</fb-table-layout>
					</fb-container>
				</fb-col>
			</fb-row>
		</fb-container>

		<fb-fieldset label="文件上传"></fb-fieldset>

		<fb-form>
			<fb-form-item label="辨识方法">
				<fb-radio-group v-model="radio">
					<fb-radio value="LEC" label="LEC"></fb-radio>
					<fb-radio value="LS" label="LS"></fb-radio>
					<fb-radio value="MES" label="MES"></fb-radio>
				</fb-radio-group>
			</fb-form-item>
			<fb-form-item label="数据导入">
				<tp-upload
					:service="$svc.sys.demo.import"
					:param="{'type': radio}"
					:accept="''"
					:button-text="'导入'"
					@on-success="onSuccess"
					@on-error="onError"
					multiple
				></tp-upload>
			</fb-form-item>
		</fb-form>


		<tp-dialog ref="TpDialog" @closeTpDialog="closeDialog"></tp-dialog>
	</div>
</template>

<script>

	import Page from "@/util/Page"

	export default {
		mixins: [
			Page
		],
		components: {},
		mounted() {

		},
		data() {
			return {
				radio: 'LEC',
				fileList: {
					fileList: [],
				},
			}

		},

		methods: {
			onSuccess(value) {
				let param = {'result': value.data}
				let options = {
					"height": 400,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/success.vue', param, "成功", options);
			},
			onError(value) {
				let param = {'result': value.data}
				let options = {
					"height": 400,
					callBack: function (result) {
						console.log("=============回调查看" + result)
					}
				}
				this.$refs.TpDialog.show('/sys/demo/error.vue', param, "失败", options);
			},

			closeDialog(param) {

			}
		},

	}
</script>

<style scoped lang="less">


</style>
