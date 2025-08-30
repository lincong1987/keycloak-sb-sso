<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">

		<fb-page-header
				title=""
				sub-title=""
			>


				<fb-card header="">


					<fb-container class="check-plan-wrapper">
<!--
						<fb-steps current="0" class="check-plan__steps">
							<fb-step label="选择计划类型"></fb-step>
							<fb-step label="填写计划信息"></fb-step>
							<fb-step label="完成"></fb-step>
						</fb-steps>-->

						<fb-container class="check-plan__body">
							<fb-row gutter="16">
								<fb-col span="12">
									<fb-container class="check-plan-card">
										<fb-container class="check-plan-card__icon" :background="colors.primary">
											<fb-icon name="file-text-fill" size="20" :color="colors.white"></fb-icon>
										</fb-container>
										<fb-container class="check-plan-card__label">
											<fb-text :color="colors.grey_9" size="l">年度检查计划</fb-text>
										</fb-container>
									</fb-container>
								</fb-col>

								<fb-col span="12">
									<fb-container class="check-plan-card">
										<fb-container class="check-plan-card__icon" :background="colors.green">
											<fb-icon name="file-text-fill" size="20" :color="colors.white"></fb-icon>
										</fb-container>
										<fb-container class="check-plan-card__label">
											<fb-text :color="colors.grey_9" size="l">月度检查计划</fb-text>
										</fb-container>
									</fb-container>
								</fb-col>
							</fb-row>

						</fb-container>


					</fb-container>


				</fb-card>
			</fb-page-header>
		</div>

		<!--<div class="tp-dialog-bottom">
			<fb-button v-if="parentPage.tabIndex > 0" @on-click="prev">上一步</fb-button>
			<fb-button v-if="parentPage.tabIndex == parentPage.tabSteps.length - 1" type="primary"
					   style="margin-left: 12px" @on-click="$message.success('成功!')">
				完成
			</fb-button>
			<fb-button v-if="parentPage.tabIndex < parentPage.tabSteps.length - 1" type="primary"
					   style="margin-left: 12px" @on-click="next">下一步
			</fb-button>
		</div>-->

		<tp-dialog ref="TpDialog"></tp-dialog>
	</div>
</template>

<script>
	import Page from "@/util/Page"

	export default {
		mixins: [Page],
		components: {},
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
		mounted() {

		},
		data() {
			return {
				disabled: false,
				size: 'm',
				name: 'cc',
			}

		},

		methods: {},

	}
</script>

<style scoped lang="less">

	/**
        检查计划
     */
	.check-plan-wrapper {

		width: 600px;
		margin: 0 auto;

		.check-plan__steps {
		}

		.check-plan__body {
			height: 300px;
			margin-top: 50px;
		}
	}

	.check-plan-card {
		height: 168px;
		text-align: center;
		background: #ffffff;
		border: 1px solid #BFBFBF;
		border-radius: 4px;
		position: relative;
		cursor: pointer;

		&:hover {
			background: #E6F7FF;
			border: 1px solid #0284FE;
		}

		&__icon {
			position: absolute;
			top: 40px;
			left: 50%;
			margin-left: -20px;
			height: 40px;
			width: 40px;
			text-align: center;
			line-height: 40px;
			border-radius: 50%;
		}

		&__label {
			position: absolute;
			top: 100px;
			width: 100%;
			text-align: center;
			margin: 0 auto;;
		}


	}


</style>
