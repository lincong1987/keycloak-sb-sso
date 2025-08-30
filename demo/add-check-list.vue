<template>
    <div class="tp-dialog">
        <div class="tp-dialog-top">

            <fb-page-header title="">

                <fb-container v-for="(item, index) in data" :key="index">
                    <fb-container>
                        <fb-text>{{ index + 1 }}、{{ item.text }}</fb-text>
                        <fb-link :click="()=>handlePip(item.pipId)" label="参考标准>>" type="primary"></fb-link>
                    </fb-container>

                    <fb-table-layout>
                        <fb-form :ref="`fbform_${index}`">
                            <fb-table-layout-cell>
                                <fb-form-item prop="thisRadio" :rule="[{required: true, message:'请选择'}]">
                                    <fb-radio-group v-model="item.thisRadio"
                                                    :data="[{label:'通过', value: '1'}, {label:'不通过', value: '0'}]"></fb-radio-group>
                                </fb-form-item>
                            </fb-table-layout-cell>
                        </fb-form>
                        <fb-table-layout-cell align="right">
                            <fb-button type="link" icon="photo" @on-click="handleAddImg(item)">照片：{{
                                item.fileList.fileList.length }}
                            </fb-button>
                        </fb-table-layout-cell>
                    </fb-table-layout>

                    <fb-divider></fb-divider>
                </fb-container>
            </fb-page-header>
        </div>

        <div class="tp-dialog-bottom">
            <fb-button v-if="parentPage.tabIndex > 0" @on-click="prev">上一步</fb-button>
            <fb-button v-if="parentPage.tabIndex == parentPage.tabSteps.length - 1" type="primary"
                       style="margin-left: 12px" @on-click="$message.success('成功!')">
                完成
            </fb-button>
            <fb-button v-if="parentPage.tabIndex < parentPage.tabSteps.length - 1" type="primary"
                       style="margin-left: 12px" @on-click="next">下一步
            </fb-button>
        </div>

        <tp-dialog ref="TpDialog"></tp-dialog>
    </div>
</template>

<script>
    import Page from "@/util/Page"
    import {each} from "lodash-es"

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
                    "thisRadio": "",
                    "fileList": {
                        "referType": 'SYS1014',
                        "fileList": [],
                    }
                },
                    {
                        "text": "大家阿公i啊十几个陌生地方理解阿斯哦购买皮革为毛戈平码送屁股没挖个坑妈平时都没法怕的是父母，哦爬山。",
                        "pipId": "222222",
                        "thisRadio": "",
                        "fileList": {
                            "referType": 'SYS1014',
                            "fileList": [],
                        }
                    }],

                num: 1,
            }
        },
        methods: {
            handlePhotoButtonClick() {
                this.$refs.photoUploader.show()
            },

            handlePip(id) {
                alert(id)
            },

            next() {
                console.log(this.data)

                each(this.$refs, (item, key) => {
                    if (key.indexOf("fbform_") >= 0) {
                        // 遍历form
                        item[0].validate((result) => {
                            if (result === true) {
                                // 成功之后跳转下一步
                                this.stepNext();
                            }
                        })
                    }

                });
            },
            prev() {
                alert("上 yi bu le 1111")
            },

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

        },

    }
</script>

<style scoped lang="less">
</style>
