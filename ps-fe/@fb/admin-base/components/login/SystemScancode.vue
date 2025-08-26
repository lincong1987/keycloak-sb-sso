<template>
	<div class="login-scan-code">
		<div class="login-scan-code--img" :style="getStyle"></div>
		<div v-show="isMask" class="login-scan-code--mask">
			<span>二维码已失效</span>
			<fb-button type="primary" size="l" @on-click="handleClick" :disabled="disabled" :loading="loading">点击刷新</fb-button>
		</div>
	</div>
</template>

<script>
	/**
	 * SystemCapture
	 * (c) 2020 lincong1987
	 */

	export default {
		name: 'SystemScancode',

		props: {
			api: {
				type: String,
				default: 'http://admin.dlszywz.cn/include/captcha/captcha.php?',
			},
		},

		data () {
			return {
				disabled: false,
				loading: false,
				isMask: false,
				backgroundImage: '',
			}
		},

		computed: {
			getStyle () {
				return {
					backgroundImage: this.loading ? 'none' : `url(${this.backgroundImage})`,
				}
			},
		},

		methods: {

			getCapture () {
				this.disabled = true
				this.loading = true
				this.backgroundImage = this.api + new Date().getTime()

				this.$nextTick(() => {
					setTimeout(() => {
						this.disabled = false
						this.loading = false
						this.isMask = false
					}, 1000)
				})
			},
			handleClick () {
				this.getCapture()
			},
			overtimeCode () {
				this.isMask = true
			}
		},

		mounted () {
			this.getCapture()

			// 模拟超时
			setTimeout(() => {
				this.overtimeCode()
			},2000)
		},

	}
</script>

<style lang="less" scoped>

	.login-scan-code {
		position:         relative;
		width: 160px;
		height: 160px;
		border-radius: 2px;
		border: 1px solid #D3D3D3;
		padding: 8px;


		.login-scan-code--img {
			width: 100%;
			height: 100%;
			background-size: 100% 100%;
		}

		.login-scan-code--mask {
			position: absolute;
			top: 0;
			left: 0;
			width: 160px;
			height: 160px;
			background: rgba(255, 255, 255, 0.7);
			border-radius: 2px;
			text-align: center;
			z-index: 999;

			>span {
				margin-top: 53px;
				margin-bottom: 17px;
				display: block;
				font-size: 14px;
				font-family: MicrosoftYaHei;
				color: #313C47;
				line-height: 22px;
			}
		}

	}

</style>
