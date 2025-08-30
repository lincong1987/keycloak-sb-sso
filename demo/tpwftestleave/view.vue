<template>
	<div class="tp-dialog">
		<div class="tp-dialog-top">
			<fb-property bordered label-width="130px" mode="form">


				<fb-row>
					<fb-col span="12">
						<fb-property-item label="请假单主键">
							{{formData.levId}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="请假类型">
							{{formData.levType}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="请假时间开始">
							{{formData.levTimeStart}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="请假时间结束">
							{{formData.levTimeEnd}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="时长（天）">
							{{formData.levDay}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="请假事由">
							{{formData.levReason}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="所在部门">
							{{formData.levDept}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="有效标记">
							{{formData.actived}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="创建人">
							{{formData.creator}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="创建时间">
							{{formData.createTime}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="修改人">
							{{formData.updator}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="修改时间">
							{{formData.updateTime}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="备用">
							{{formData.extend01}}
						</fb-property-item>
					</fb-col>
					<fb-col span="12">
						<fb-property-item label="备用">
							{{formData.extend02}}
						</fb-property-item>
					</fb-col>
				</fb-row>

				<fb-row>
					<fb-col span="12">
						<fb-property-item label="备用">
							{{formData.extend03}}
						</fb-property-item>
					</fb-col>
				</fb-row>

			</fb-property>
		</div>
		<div class="tp-dialog-bottom">
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>
</template>

<script>

	import Page from "@/util/Page"

	export default {
		name: 'demo_view',
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
		components: {},
		// 创建方法
		created() {
			// 记录原来的默认值，用于表单重置
		},
		// 初始化方法
		mounted() {
			this.init(this.param);
		},
		data() {
			return {
				// 请求的 service
				service: app.$svc.sys.demo.tpwftestleave,

				formData: {
					levId: '',
					levType: '',
					levTimeStart: '',
					levTimeEnd: '',
					levDay: '',
					levReason: '',
					levDept: '',
					actived: '',
					creator: '',
					createTime: '',
					updator: '',
					updateTime: '',
					extend01: '',
					extend02: '',
					extend03: '',
				},
			}
		},

		// 方法
		methods: {
			init(param) {
				this.formData.levId = param.id;
				this.view();
			},
			// 取消
			handleClose() {
				let param = {};
				this.closeTpDialog(param);
			},
			// 查看
			view() {
				// 调用新增service方法
				let that = this;
				this.service.view({"levId": that.formData.levId}).then((result) => {
					// 判断code
					if (result.code == 1) {
						that.formData = result.data
					} else {
						// 服务器返回失败
						this.$message.error('错误提示:' + result.message)
					}
				})
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
