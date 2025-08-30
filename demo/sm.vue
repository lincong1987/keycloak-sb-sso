<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-form ref="fbform"  :label-width="180">
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="文本" prop="content" >
							<fb-input v-model="formData.content" ></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="加密文本" prop="encContent" >
							<fb-input v-model="formData.encContent" ></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-form-item label="解密文本" prop="decContent" >
							<fb-input v-model="formData.decContent" ></fb-input>
						</fb-form-item>
					</fb-col>
				</fb-row>
				<fb-row>
					<fb-col span="24">
						<fb-button  @on-click="sm2Encrypt" icon="add-circle">sm2加密</fb-button>
						<fb-button  @on-click="sm2Decrypt" icon="add-circle">sm2解密</fb-button>
					</fb-col>
				</fb-row>
			</fb-form>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>
	</div>
</template>


<script>

	import Page from "@/util/Page"

	export default {
		name: 'sm',
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
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: this.$svc.sys.demo,
				// 表单数据
				formData: {
					content: '',
					encContent: '',
					decContent: '',
				},
				// 公钥Q
				publicKey:'04be3415fd3a7231fa23e4cfdf6f857b0f3137e75692f7b5011d459afc0cdd7741676dca32ca6489cfe0f0fd43b5e9f9f0f77c7997630ba1142c725178a9181558',
				// 私钥D (测试才放前端)
				privateKey: '74b24bfa8a55127c0fcbe87d7a112e398fd291fd72314e1c56e8d575664a1148',
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
				// 有值表示是修改方法
				if (param.id) { // 传ID表示修改
					this.formData.id = param.id;
				}
			},

			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			// 加密
			sm2Encrypt() {

				const sm2 = require('sm-crypto').sm2

				//let keypair = sm2.generateKeyPairHex()

				//this.publicKey = keypair.publicKey // 公钥
				//this.privateKey = keypair.privateKey // 私钥

				const cipherMode = 1  // 1 - C1C3C2，0 - C1C2C3，默认为 1

				//let publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d03420004efddac6873492c460eac87b0b12bec1258f181a5093d62bbb622a7192d58c6c7278f45880dfc226bb0b645268e33a3a8de9d12b7397e05ab729fb35ead70dded";

				let encContent = sm2.doEncrypt(this.formData.content, this.publicKey, cipherMode) // sm2加密
				this.formData.encContent = encContent;
			},
			// 解密
			sm2Decrypt() {
				const sm2 = require('sm-crypto').sm2
				let encryptData = this.formData.encContent;
				const cipherMode = 1  // 1 - C1C3C2，0 - C1C2C3，默认为 1

				let decryptData = sm2.doDecrypt(encryptData, this.privateKey, cipherMode) // 解密结果sm2

				this.formData.decContent = decryptData;
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
