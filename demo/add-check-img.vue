<template>
	<div class="tp-dialog">

		<div class="tp-dialog-top">

			<fb-page-header title="">

				<fb-container>
					<fb-form>
						<fb-form-item label="照片">
							<fb-upload
								view="image"
								v-model="imgList.fileList"
								:file-list="imgList.fileList"
								:service="$svc.sys.file"
								:param="{'referType': imgList.referType}"
								@on-change="fileListChange"
								:accept="'.png,.jpeg,.jpg'">
							</fb-upload>
						</fb-form-item>
					</fb-form>
				</fb-container>
			</fb-page-header>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">确定</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>
	</div>
</template>

<script>
	import Page from "@/util/Page"

	export default {
		mixins: [Page],
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
		components: {},
		mounted() {
			this.imgList.fileList = this.param.fileList;
		},
		data() {
			return {
				imgList: {
					referType: 'SYS1021',
					fileList: [],
				},
			}
		},
		methods: {
			add() {
				let fileArr = this.imgList.fileList.map(item => {
					return {'attachId': item.attachId, 'attachName': item.attachName, 'attachSize': item.attachSize, 'name': item.name, 'referId': item.referId, 'referType': item.referType, 'savePath': item.savePath}
				});
				this.closeTpDialog(fileArr);
			},

			// 取消
			handleClose() {
				this.closeTpDialog();
			},

			fileListChange(info) {
				this.imgList.fileList = info.fileList;
			},

		},

	}
</script>

<style scoped lang="less">
</style>
