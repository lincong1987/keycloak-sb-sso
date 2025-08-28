<template>
	<!--  只进行登录相关操作 - 为后续拆分做准备 -->
	<div class="login-wrapper">

		<div class="login-logo">
			<span class="logo"></span>
			{{ loginLogoTitle }}
		</div>

		<div class="login-content">
			<div class="content">
				<login-card-scan-code-verify :inLoginProcess="inLoginProcess" captcha-type="slider"
					@doLogin="doLoginNew" @toAppDownload="toAppDownload" @toForget="toForget" @toRegister="toRegister"
					ref="loginCard" :show-register-link="true" v-show="!seletDepartment && !cardCode" />

				<login-card-department-list v-show="seletDepartment" :fieldObj="{ label: 'deptFullName', id: 'deptId' }"
					:departmentList="departmentList" @confirmDepartment="confirmDepartment" />

				<transition name="fade-transform">
					<router-view></router-view>
				</transition>

				<!-- <div v-show="cardCode == 3" class="login-forget">
					<appdown-card @toLogin="toLogin" qrcode-url="https://alilaoba.cn/"></appdown-card>
				</div> -->

				<div v-show="cardCode == 4">
					<force-update-pwd ref="forceUpdatePwd" @toLogin="toLogin"
						@doUpdatePwd="doUpdatePwd"></force-update-pwd>
				</div>

			</div>
		</div>

		<div class="login-footer">
			<p v-html="$xss(copyright)"></p>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>
	</div>
</template>

<script>
/**
 * DefaultApplicationLogin
 * (c) 2020 lincong1987
 */
import loginCardCodeVerify from '../../components/login/LoginCardCodeVerify'
import loginCardDragVerify from '../../components/login/LoginCardDragVerify'
import loginCardPhoneVerify from '../../components/login/LoginCardPhoneVerify'
import loginCardScanCodeVerify from '../../components/login/LoginCardScanCodeVerify'
import LoginCardDepartmentList from '../../components/login/LoginCardDepartmentList'
import appdownCard from '../../components/login/AppdownCard'
import forceUpdatePwd from '../../components/login/ForceUpdatePwd'
import { startRefreshToken } from '../../util/tokenUtil'
export default {
	components: {
		LoginCardDepartmentList,
		loginCardDragVerify,
		loginCardCodeVerify,
		loginCardPhoneVerify,
		loginCardScanCodeVerify,
		appdownCard,
		forceUpdatePwd,
	},

	data() {
		let loginLogoTitle = app.projectConfig.login.title || app.projectConfig.loginLogoTitle
		let copyright = this.$route.meta.copyright || app.projectConfig.router.copyright || `Copyright © 2001-${app.$dayjs().format('YYYY')} JPX.保留所有权利`
		let loginPath = this.$route.meta.rootPath || app.projectConfig.router.loginPath
		if (window._publicConfig) {
			if (window._publicConfig.loginLogoTitle) {
				loginLogoTitle = window._publicConfig.loginLogoTitle
			}
			if (window._publicConfig.copyright) {
				copyright = window._publicConfig.copyright
			}
			if (window._publicConfig.loginPath) {
				loginPath = window._publicConfig.loginPath
			}
		}


		return {
			loginLogoTitle,
			copyright,
			loginPath,
			// 0 = 登录  1 = 注册  2 = 忘记密码  3 = App下载  4 = 强制修改密码
			cardCode: -1,
			inLoginProcess: false, // 登录 loading
			seletDepartment: false,
			departmentList: [],
		}
	},

	watch: {
		'$route': {
			handler() {
				this.initCardCode()
			},
			deep: true
		}
	},

	mounted() {
		this.initCardCode()
	},

	methods: {
		// 判断初始化 路由 是否展示登录卡片
		initCardCode() {
			let { path } = this.$route
			let loginPathChild = this.loginPath + '/'
			if (path.indexOf(loginPathChild) !== -1) {
				this.cardCode = -1
			} else {
				this.cardCode = 0
			}
		},
		// 子 =》 父 处理登录
		doLoginNew(loginForm) {


			let formData = {
				"clientUuid": loginForm.clientUuid,
				"x": loginForm.x,
				"type": loginForm.type,
				"startSlidingTime": loginForm.startSlidingTime,
				"entSlidingTime": loginForm.entSlidingTime,
			}

			app.service.request({
				url: '/platform/captcha/check-captcha',
				method: 'post', // 请求方式 post,get, 默认是 get,
				// `params` 是即将与请求一起发送的 URL 参数, get 采用
				transformRequest: [
					// 把json数据序列化成xxx=?&xx=?的格式
					function (data) {
						let ret = ''
						for (let it in data) {
							ret += encodeURIComponent(it) + '=' +
								encodeURIComponent(data[it]) + '&'
						}
						ret = ret.substring(0, ret.lastIndexOf('&'))
						return ret
					},
				],
				// `data` 是作为请求主体被发送的数据， post 采用
				data: formData,
				// `headers` 是即将被发送的自定义请求头
				headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
				// `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob',
				// 'document', 'json', 'text', 'stream'
				// responseType: 'json', // 默认的
				// `timeout` 指定请求超时的毫秒数(0 表示无超时时间)
				// 如果请求话费了超过 `timeout` 的时间，请求将被中断
				timeout: 5000,
			}).then((res) => {

				if (res) {

					this.ticket = res.data
					// 获取票据
					loginForm.ticket = res.data
					// 执行登录
					this.doLogin(loginForm)
				} else {
					this.$store.commit('login/setDragImgStatus', 'fail')
				}
			})
		},
		// 子 =》 父 处理登录
		doLoginOld_Long(loginForm) {
			// console.log(loginForm)
			// app.service.post('platform/captcha/check-captcha', loginForm).then((res) => {
			// 	console.log(res)
			// })
			app.service.request({
				url: 'captcha/check-captcha',
				method: 'post', // 请求方式 post,get, 默认是 get,
				// `params` 是即将与请求一起发送的 URL 参数, get 采用
				transformRequest: [
					// 把json数据序列化成xxx=?&xx=?的格式
					function (data) {
						let ret = ''
						for (let it in data) {
							ret += encodeURIComponent(it) + '=' +
								encodeURIComponent(data[it]) + '&'
						}
						ret = ret.substring(0, ret.lastIndexOf('&'))
						return ret
					},
				],
				// `data` 是作为请求主体被发送的数据， post 采用
				data: loginForm,
				// `headers` 是即将被发送的自定义请求头
				headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
				// `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob',
				// 'document', 'json', 'text', 'stream'
				// responseType: 'json', // 默认的
				// `timeout` 指定请求超时的毫秒数(0 表示无超时时间)
				// 如果请求话费了超过 `timeout` 的时间，请求将被中断
				timeout: 5000,
			}).then((res) => {
				if (res) {
					this.$message.success(res.message)
					this.$store.commit('login/setDragImgStatus', 'success')
				} else {
					this.$store.commit('login/setDragImgStatus', 'fail')
				}
			})
		},
		doLogin(loginForm) {

			console.log(loginForm)
			// 表单验证在子组件中处理，登录只需要获取子组件数据值，进行异步请求处理
			this.inLoginProcess = true
			this.departmentList = []
			// 图片验证码登陆
			this.$svc.platform.captChaLogin(loginForm).then(json => {
				app.trigger('monitor-login', json)

				// 登录按钮 loading
				this.inLoginProcess = false
				if (json && json.code === 403) {
					// 许可证不可用
					this.$message.error(json.message)
					this.$store.commit('login/setDragImgStatus', 'fail')
					this.handleLicence()
					return;
				}

				if (!json) {
					this.$store.commit('login/setDragImgStatus', 'fail')
					return;
				}
				// this.goToMain();

				// 								// 启动刷新token
				// 								startRefreshToken();

				// 				return
				// 判断密码是否需要强制修改
				if (json.data && json.data.restPwd === "1") {
					// 强制修改密码
					let token = this.$datax.get('token');
					this.$datax.remove('token');
					// 防止用户不修改密码直接刷新浏览器，所以这里将登陆系统的token删除，重新缓存一个临时token
					this.$datax.set('temporary', token)
					this.$store.commit('login/setDragImgStatus', 'fail')
					this.cardCode = 4;
					// 将密码框清空
					loginForm.userpwd = null;

				} else {
					if (json.data && json.data.deptVOList && json.data.deptVOList.length > 1) {
						this.$store.commit('login/setDragImgStatus', 'success')
						this.$datax.set('token', json.data.token)
						// 在多个部门下
						this.departmentList = json.data.deptVOList
						// 切换部门，选择部门登陆
						this.seletDepartment = true
					} else {
						this.$store.commit('login/setDragImgStatus', 'success')
						this.$datax.set('token', json.data.token)
						this.$datax.set('user', json.data)
						this.$store.dispatch('admin/setToken', json.data.token)

						// 获取登陆人用户信息
						this.$svc.platform.getUserInfo().then(res => {

							this.$datax.set('userInfo', (res && res.data) || {})
							// 跳转页面
							let that = this;
							if (!this.seletDepartment) {
								that.goToMain();

								// 启动刷新token
								startRefreshToken();

								// this.$router.replace('/register')
								that.reportLogin();
							}
						});
					}
				}
			})
		},
		// 上传许可证
		handleLicence() {
			let that = this;
			let param = {};

			let options = {
				tabChangeConfirm: false,
				callBack: function (result) {

				}
			}
			// 打开新增界面弹出窗
			this.$refs.TpDialog.show("/login/upload-licence.vue", param, "更新许可证", options);
		},
		confirmDepartment(val) {
			if (!val) {
				this.$message.warn('请点选部门登录')
			}
			// console.log(val)
			this.$svc.platform.selectDeptLogin({ 'deptId': val }).then(res => {
				this.$datax.set('token', res.data.token)
				this.$datax.set('user', res.data)
				this.$store.dispatch('admin/setToken', res.data.token)
				// 切换部门标识恢复
				this.seletDepartment = false
				// 获取登陆人用户信息
				let that = this;
				this.$svc.platform.getUserInfo().then(res => {
					this.$datax.set('userInfo', res.data || {})
					that.goToMain();
					that.reportLogin();
				});
			})
		},
		goToMain() {

			this.$router.replace(this.$datax.get('GLOBAL_CONFIG').mainPath)

// 日志埋点
			this.$logger && this.$logger.send({
				// 模块编码
				moduleCode: 'login',
				// 模块名称
				moduleName: '登录',
				// 操作类型： login/logout/add/delete/edit/query/pass/unpass, 可以自己定义
				operateType: 'login',
				// 操作人id
				operterId:  app.$datax.get('userInfo').personId,
			});
			
			return
			const { hijack } = this.$route.meta || app.projectConfig.login
			// 获取当前登录用户信息
			let userInfo = app.$datax.get('userInfo')

			// 日志埋点
			let data = {
				// 模块编码
				moduleCode: 'login',
				// 模块名称
				moduleName: '登录',
				// 操作类型： login/logout/add/delete/edit/query/pass/unpass, 可以自己定义
				operateType: 'login',
				// 操作人id
				operterId: userInfo.personId,
			}
			this.$logger && this.$logger.send(data);

			if (hijack) {
				hijack(userInfo)
				return
			}

			this.licenceWarn(() => {

				// 跳转页面
				this.$router.replace(this.$datax.get('GLOBAL_CONFIG').mainPath)

				// 日志埋点
				this.$logger && this.$logger.send(data);
			});


		},
		// 许可证临期提醒
		licenceWarn(callback) {
			return
			app.service.get('/sys/licence/warn', {}).then((result) => {
				if (result.code === 1) {
					if (result.data) {
						this.$msgbox.confirm("系统将于" + result.data + "天后试用到期，请尽快联系系统供应商。", () => {
							callback();
						})
					} else {
						callback();
					}
				}
			})
		},
		reportLogin() {
			// 是否需要单点登录报表系统
			if (!app.projectConfig.sysconfig.reportSwitch) {
				return;
			}
			// 单点登录报表系统
			this.$svc.platform.reportLogin({}).then((result) => {
				if (result.code == 1) {

				} else {
					// 服务器返回失败
					this.$message.error('报表登陆失败: ' + result.message)
				}
			})

		},
		toLogin() {
			this.cardCode = 0
		},
		toRegister() {
			this.cardCode = 1
			this.$router.push(this.loginPath + '/register')
		},
		toForget() {
			this.cardCode = 2
			this.$router.push(this.loginPath + '/forget-pass')
		},
		toAppDownload() {
			this.cardCode = 3
			this.$message.success('请打开手机，扫描二维码')
		},
		toForceUpdatePwd() {
			this.cardCode = 4
		},
		// 强制修改密码逻辑
		doUpdatePwd(param) {
			//	获取临时token并清空临时token
			let token = this.$datax.get('temporary');
			this.$datax.remove('temporary');
			let newUserpwd = param.userpwd;
			let newConfirmUserPwd = param.confirmUserPwd;
			// 密码传输判断是否需要加密
			let sysconfig = app.projectConfig.sysconfig;
			if (sysconfig.passwordEncryption && sysconfig.passwordEncryption === true) {
				const sm2 = require('sm-crypto').sm2
				// 1 - C1C3C2，0 - C1C2C3，默认为 1
				const cipherMode = 1
				// 公钥
				let publicKey = sysconfig.publicKey;
				// sm2加密
				newUserpwd = sm2.doEncrypt(param.userpwd, publicKey, cipherMode)
				newConfirmUserPwd = sm2.doEncrypt(param.confirmUserPwd, publicKey, cipherMode)
			}

			this.$svc.platform.update({
				"userpwd": newUserpwd,
				"confirmUserPwd": newConfirmUserPwd
			}, token).then((result) => {
				if (result.code == 1) {
					this.$message.success('修改成功！请重新登录！');
					this.cardCode = 0;
				} else {
					// 服务器返回失败
					this.$message.error('修改失败: ' + result.message)
				}
			})
		},
	},

}
</script>

<style lang="less" scoped>
// @import ".";

@import "../../assets/styles/login/login2.less";
</style>
