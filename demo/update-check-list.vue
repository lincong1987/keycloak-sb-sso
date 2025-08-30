<template>
	<div class="tp-dialog">

		<div class="tp-dialog-top">

			<fb-page-header title="">


				<fb-container v-for="(item, index) in data" :key="index" pt="16">
					<fb-container>
						<fb-text>{{ index + 1 }}、{{ item.text }}</fb-text>
						<fb-link label="参考标准>>" type="primary"></fb-link>
					</fb-container>

					<fb-table-layout>
						<fb-table-layout-cell>
							<fb-text :color="colors.text_secondary">结果：</fb-text>
							<fb-text v-if="item.thisRadio === '0'" :color="colors.success">通过</fb-text>
							<fb-text v-if="item.thisRadio === '1'" :color="colors.danger">未通过</fb-text>
							<fb-text v-if="item.thisRadio === '2'" :color="colors.text_emphasize">不适用</fb-text>
						</fb-table-layout-cell>
						<fb-table-layout-cell align="right">
							<fb-button type="link" icon="photo" @on-click="handleAddImg(item)">照片：{{
								item.fileList.fileList.length }}
							</fb-button>
						</fb-table-layout-cell>
					</fb-table-layout>

					<fb-container>
						<fb-textarea v-if="item.thisRadio === '1'" rows="3" v-model="item.desc"
									 type="text"
									 maxlength="200">
						</fb-textarea>
					</fb-container>

					<fb-divider></fb-divider>
				</fb-container>

			</fb-page-header>
		</div>

		<div class="tp-dialog-bottom">
			<fb-button style="margin-right: 12px" type="primary" @on-click="add">保存</fb-button>
			<fb-button @on-click="handleClose">关闭</fb-button>
		</div>

		<tp-dialog ref="TpDialog"></tp-dialog>

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
		},
		data() {
			return {
				data: [{
					"text": "需停机调整圆锥破碎机排矿口时，应先用铅锤或其他工具测定，然后停车和切断电源，方可进行调整。若须进入机内测定排矿口，应有必要的安全防护措施。",
					"pipId": "1111111",
					"thisRadio": "0",
					"fileList": {
						"referType": 'SYS1014',
						"fileList": [{
							"attachId": "1379714062258012160",
							"attachName": "1617784440862.png",
							"attachSize": 199094,
							"createTime": "20210407163400",
							"referId": "",
							"referType": "SYS1021",
							"savePath": "2021/4/2021_04_07_16_34_00862.png"
						}],
					}
				},
					{
						"text": "大家阿公i啊十几个陌生地方理解阿斯哦购买皮革为毛戈平码送屁股没挖个坑妈平时都没法怕的是父母，哦爬山。",
						"pipId": "222222",
						"thisRadio": "1",
						"fileList": {
							"referType": 'SYS1014',
							"fileList": [],
						},
						"desc": ""
					},
					{
						"text": "大家阿公i啊十几个陌生地方理解阿斯哦购买皮革为毛戈平码阿迪工伤事故萨特问题 时都没阿斯顿尴尬的四个我好噶地方，哦爬山。",
						"pipId": "33333333",
						"thisRadio": "2",
						"fileList": {
							"referType": 'SYS1014',
							"fileList": [],
						}
					}],
			}
		},
		methods: {

			handleAddImg(item) {
				let param = {"fileList": item.fileList.fileList}
				let options = {
					callBack: function (result) {
						if (result) {
							item.fileList.fileList = result;
						}
					}
				}
				this.$refs.TpDialog.show('/sys/demo/add-check-img.vue', param, "检查图片", options);
			},

			// 取消
			handleClose() {
				this.closeTpDialog();
			},

			add() {
				alert("上 yi bu le 1111")
			},

		},

	}
</script>

<style scoped lang="less">
</style>
