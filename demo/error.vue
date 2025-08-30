<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-page-header title="">

				<fb-card header="">
					<fb-result type="error" message="失败">
						<template v-slot:describe>
							<p>结果页主要用于展示当前操作的结果，并需要额外展示复杂内容，在下面这个区域展示；</p>
							<p v-for="(value, index) in data">{{value}}</p>
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
