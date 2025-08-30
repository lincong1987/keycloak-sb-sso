<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-page-header title="">
				<fb-card header="">

					<fb-result
						type="success"
						message="导入成功"
					>
						<template v-slot:describe>
							<p>导入报错数据展示；</p>
							<p v-for="(value, index) in data">
								<fb-link :click="()=>outExcel(value)" label="下载" type="primary"></fb-link>
							</p>
						</template>
					</fb-result>
				</fb-card>

			</fb-page-header>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>

	</div>
</template>

<script>

	import Page from "@/util/Page"

	export default {
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
		computed: {},
		mounted() {
			// 执行界面初始化
			this.init(this.param);
		},
		data() {
			return {
				data: [],
			}

		},

		methods: {
			init(param) {
				if (param.result) {
					this.data = param.result
				}

			},

			outExcel(val) {
				alert(val);
			},

			handleClose() {
				this.closeTpDialog();
			}
		},

	}
</script>

<style scoped lang="less">

	.fb-card {
		margin-top: 12px;
	}

	.fb-row {
		margin-bottom: 12px;
	}


</style>
