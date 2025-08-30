<template>
	<fb-page-edit :dialog="dialog">
		<template slot="form">
			<div v-autoheight="{height:200}">
				<fb-form ref="form">
					<fb-row>
						<fb-col span="7">
							<fb-form-item label="用户名" prop="用户名"
										  :rule="{required: true}">
								<fb-input v-model="form.userName"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="7">
							<fb-form-item label="性别" prop="性别"
										  :rule="[{required: true},{type:'integer'}]">
								<fb-input v-model="form.userGander"></fb-input>
							</fb-form-item>
						</fb-col>
						<fb-col span="7">
							<fb-form-item label="电话" prop="电话"
										  :rule="{required: true}">
								<fb-input v-model="form.userTel"></fb-input>
							</fb-form-item>
						</fb-col>
					</fb-row>
				</fb-form>
			</div>
		</template>

		<template slot="footer-right">
			<fb-button @on-click="handleClose">取消</fb-button>
			<fb-button type="primary" @on-click="handleReset">重置</fb-button>
			<fb-button type="primary" @on-click="handleSave">保存并关闭</fb-button>
		</template>
	</fb-page-edit>
</template>

<script>
	import Page from "@/util/Page"

	export default {
		mixins: [Page],
		data() {
			return {
				form: {
					userId: "",
					userName: "",
					userGander: "",
					userTel: "",
				},
				actions: {}
			}
		},
		methods: {},
		mounted() {
		},
	}
</script>

<style scoped lang="less">
</style>
