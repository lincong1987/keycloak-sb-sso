<template>
	<div class="jpx-admin-tabbar" :style="getLayoutTabbarStyle">
		<fb-tabs v-model="current" closable style="width: calc(100% - 60px);"
				 ref="tabbar"
				 @on-tab-remove="handleTabRemove"
				 @on-tab-choose="handleTabChoose"
				 @on-tab-change="handleTabChange"
				 @on-tab-dblclick="handleTabDblclick"
		>
			<fb-tab v-for="(tab, ) in  tabs" :label="tab.tabLabel" :name="tab.tabName"
					:key="tab.tabName"></fb-tab>
		</fb-tabs>

		<div class="jpx-admin-tabbar-tool">
			<fb-button @on-click="toolShow = !toolShow" icon="more" size="s"></fb-button>
			<fb-popup-picker v-model="toolShow" position="bottomRight" style="top: 100%;">
				<div style="width: 80px; text-align: center; padding: 8px">
					<div class="jpx-admin-tabbar-tool_item" @click="handleToolItemClick(item.func)"
						 v-for="(item, index) in toolList" :key="`toolbar_${index}`">
						{{ item.label }}
						<fb-icon :name="item.icon" size="12"></fb-icon>
					</div>
				</div>
			</fb-popup-picker>
		</div>
	</div>
</template>

<script>
	/**
	 * AdminTabbar
	 * (c) 2020 lincong1987
	 */
	import { mapState, mapMutations, mapGetters } from 'vuex'
	import { findIndex } from 'lodash-es'
	import { find } from '../../../util/componentUtils'

	export default {
		name: 'AdminTabbar',

		props: {
			routerViewKey: [String, Number],
			height: [String, Number],
			width: [String, Number],
			menuWidth: [String, Number],
			showMenu: true,

		},

		data () {
			return {
				scroll: false,
				toolShow: false,
				currentTabIndex: 0,
				toolList: [
					{label: '刷新', icon: 'refresh', func: 'refresh'},
					{label: '关闭左侧', icon: 'left-arrow', func: 'removeLeft'},
					{label: '关闭右侧', icon: 'right-arrow', func: 'removeRight'},
					{label: '关闭其他', icon: 'exchange-arrow', func: 'removeOthers'},
					{label: '关闭全部', icon: 'replace', func: 'removeAll'},
				]
			}
		},

		computed: {

			...mapGetters({
				tabs: 'tabbar/getTabs',
				flatMenus: 'menu/getFlatMenus'
			}),

			current: {
				get () {
					return this.$store.state.tabbar.current
				},
				set (newVal) {
					this.$store.commit('tabbar/focus', newVal)
				}
			},

			getLayoutTabbarStyle () {
				return {
					// height: this.height,
					left: this.showMenu ? `${this.menuWidth}px` : `0px`,
					width: this.showMenu ? `${this.width - this.menuWidth}px` : `${this.width}px`,
				}
			},

			getContainerClass () {

				var arr = ['fb-admin-tabbar__container']

				if (this.scroll) {
					arr.push('fb-admin-tabbar__container--scroll')
				}

				return arr

			},

			getTabbarStyle () {
				let arr = []
				return arr

			},

		},

		watch: {
			$route: {
				handler(route) {
					if (this.tabs.length === 0) {
						this.initTabs()
						// 注释后可以实现刷新仍在当前界面
						// return false
					}
					this.addTab(route)

					let tIndex = this.currentTabIndex = findIndex(this.tabs, (tab) => {
						return tab.fullPath === route.fullPath
					})

					if (tIndex >= 0) {
						// this.current = this.tabs[tIndex].path
						this.current = this.tabs[tIndex].tabName
					}
				},
				immediate: true,
			},
			current (val, oldVal) {
				let tIndex = findIndex(this.tabs, (tab) => {
					return tab.tabName === val
				})
				if (tIndex !== -1) {
					this.$datax.set('TABBAR_ID', this.tabs[tIndex].tabName)
				}
			},

			tabs (tabs) {
				this.tabSysComps(tabs);
			}
		},

		methods: {

			initTabs () {
				let indexPath = this.$datax.get('ADMIN_INDEX_PATH')
				let index = findIndex(this.flatMenus, (f) => {
					return f.path === indexPath
				})
				let menu = this.flatMenus[index]
				if (menu && menu.path) {
					if (menu.path === indexPath) {

						this.$store.commit('tabbar/add', Object.assign({}, menu))
					} else {
						// this.$store.commit('tabbar/add', Object.assign({}, this.$route, menu))
					}
				}
			},

			addTab (route) {
				let index = findIndex(this.flatMenus, (f) => {
					return f.path === route.path
				})
				if (index !== -1) {
					let menu = this.flatMenus[index]
					this.$store.commit('tabbar/add', Object.assign({}, route, menu))
				} else if (route.meta && route.meta.title && route.path !== app.projectConfig.router.mainPath) {
					this.$store.commit('tabbar/add', Object.assign({}, route))
				} else if ((route.query.tabLabel || route.params.tabLabel) && route.path !== app.projectConfig.router.mainPath) {
					this.$store.commit('tabbar/add', Object.assign({}, route))
				}
			},

			handleTabRemove (tab, index) {
				// const AdminMain = find(this.$parent, 'AdminMain')
				// if (AdminMain.$children) {
				// 	this.deleteKeepAliveComp(AdminMain.$children[index])
				// }
				this.$store.dispatch('tabbar/remove', tab.tabName)
			},

			handleTabChoose (tab, index) {
				if (this.tabs.length > 0) {
					if (this.$route.fullPath == tab.fullPath) {
						return
					}
					let route = this.tabs[index]
					if (route && route.fullPath) {
						this.$router.push(route.fullPath)
					}
				}
			},

			handleTabChange () {
				// this.$refs.tabbar.scrollNext()

				let index = findIndex(this.tabs, (tab) => {
					return tab.tabName === this.current
				})
				if (this.$route.fullPath !== this.tabs[index].fullPath) {
					// 监听到 tabs 数组的变化
					// 首次进来会追加首页
					this.$router.push(this.tabs[index].fullPath)
				}
			},

			refresh (tab, index) {
				const AdminMain = find(this.$parent, 'AdminMain')
				if (AdminMain.$children && AdminMain.$children[index]) {
					const AdminMainChild = AdminMain.$children
					for (let i = 0; i < AdminMainChild.length; i++) {
						let comp = AdminMainChild[i]
						let key = comp.$vnode.key == null
							? comp.$vnode.componentOptions.Ctor.cid + (comp.$vnode.componentOptions.tag ? `::${comp.$vnode.componentOptions.tag}` : '')
							: comp.$vnode.key;
						let sear = key.slice(key.indexOf('/'))
						if (sear === this.tabs[index].fullPath) {
							this.deleteKeepAliveComp(comp)
						}
					}
					// this.deleteKeepAliveComp(AdminMain.$children[index])
				}
				if (Object.keys(this.$route.params).length) {
					this.$router.replace({
						name: 'refresh',
						params: this.$route.params
					})
				} else {
					this.$router.replace({
						path: '/refresh',
						query: this.$route.query
					})
				}
				// this.$router.push(tab.path + '?_t=' + new Date().getTime())
			},

			handleTabDblclick (tab, index) {
				this.refresh(tab, index)
			},

			tabSysComps (tabs) {
				// let tabs = this.tabs
				const AdminMain = find(this.$parent, 'AdminMain')
				const AdminMainChild = AdminMain.$children
				let isDel = true
				if (AdminMainChild && tabs.length !== AdminMainChild.length) {
					for (let i = 0; i < AdminMainChild.length; i++) {
						let comp = AdminMainChild[i]
						let key = comp.$vnode.key == null
							? comp.$vnode.componentOptions.Ctor.cid + (comp.$vnode.componentOptions.tag ? `::${comp.$vnode.componentOptions.tag}` : '')
							: comp.$vnode.key;

						isDel = true

						if (key) {
							let sear = key.slice(key.indexOf('/'))
							for (let j = 0; j < tabs.length; j++) {
								let tab = tabs[j]
								if (sear === tab.fullPath) {
									isDel = false
								}
							}

							if (isDel) {
								this.deleteKeepAliveComp(comp)
							}
						}
					}
				}
			},

			deleteKeepAliveComp (comp) {
				if (comp && comp.$vnode && comp.$vnode.data.keepAlive) {
					if (comp.$vnode.parent && comp.$vnode.parent.componentInstance && comp.$vnode.parent.componentInstance.cache)
					{
						if (comp.$vnode.componentOptions)
						{
							var key = comp.$vnode.key == null
								? comp.$vnode.componentOptions.Ctor.cid + (comp.$vnode.componentOptions.tag ? `::${comp.$vnode.componentOptions.tag}` : '')
								: comp.$vnode.key;
							var cache = comp.$vnode.parent.componentInstance.cache;
							var keys  = comp.$vnode.parent.componentInstance.keys;
							if (cache[key])
							{
								if (keys.length) {
									var index = keys.indexOf(key);
									if (index > -1) {
										keys.splice(index, 1);
									}
								}
								delete cache[key];
							}
						}
					}
				}
				if (comp && comp.$destroy) {
					comp.$destroy();
				}
			},


			handleToolItemClick(func) {
				if (func === 'refresh') {
					this.refresh(null, this.currentTabIndex)
					return
				}
				this.$store.commit(`tabbar/${func}`)
			}
		},

	}
</script>

<style lang="less">
@import "../../../assets/styles/layout/tabbar.less";

</style>
