<template>
  <div id="app" class="root">
    <transition name="fade-transform">
      <!--			<keep-alive>-->
      <router-view />
      <!--			</keep-alive>-->
    </transition>
  </div>
</template>

<script>
/**
 * DefaultApplicationLayout
 * (c) 2020 lincong1987
 */

export default {

  mounted() {


    document.getElementById("appLoading").style.display = "none"

    // 检查URL参数中是否有SSO token
    this.handleSSOToken()

    // 1、检测本地是否存在 token
    let token = this.$datax.get('token')

    // 2、检查当前的token是否过期
    if (token) {
      this.$svc.platform.checkToken(token).then(data => {
        if (data.code == -1) {
          if (this.$route.path != this.$datax.GLOBAL_CONFIG.loginPath) {
            this.$router.replace(this.$datax.GLOBAL_CONFIG.loginPath)
          }
        } else {
          this.$router.replace(this.$datax.GLOBAL_CONFIG.mainPath)
        }
      })
    }
  },

  methods: {
    // 处理SSO Token
    handleSSOToken() {
      const urlParams = new URLSearchParams(window.location.search)
      const ssoToken = urlParams.get('token')
      
      if (ssoToken) {
        // 保存SSO token到本地存储
        this.$datax.set('token', ssoToken)
        
        // 清除URL中的token参数，避免刷新时重复处理
        const url = new URL(window.location)
        url.searchParams.delete('token')
        window.history.replaceState({}, document.title, url.pathname + url.search)
        
        // 验证token并跳转到主页
        this.$svc.platform.checkToken(ssoToken).then(data => {
          if (data.code == 1) {
            // token有效，跳转到主页
            this.$router.replace(this.$datax.GLOBAL_CONFIG.mainPath)
          } else {
            // token无效，清除并跳转到登录页
            this.$datax.set('token', '')
            this.$router.replace(this.$datax.GLOBAL_CONFIG.loginPath)
          }
        }).catch(() => {
          // 验证失败，清除token并跳转到登录页
          this.$datax.set('token', '')
          this.$router.replace(this.$datax.GLOBAL_CONFIG.loginPath)
        })
      }
    }
  }

}
</script>

<style lang="less">
/*.root {*/
/*	border: 5px solid red;*/
/*}*/

body {
  overflow: hidden;
}
</style>
