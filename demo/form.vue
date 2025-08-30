<template>
    <div>
		<fb-page-header>
            <fb-form ref="fbform" :label-width="180" v-autoheight="186">
                <fb-row>
                    <fb-col span="24">
                        <fb-form-item label="存储地址" prop="storageAddr">
                            <fb-input v-model="formData.storageAddr" placeholder="请输入存储地址"></fb-input>
                        </fb-form-item>
                    </fb-col>

                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="存储方式" prop="storageMode">
                            <fb-select v-model="formData.storageMode"
                                       :placeholder="'请选择存储方式'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'E52'}"
                            />
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="生产方式" prop="productionMode">
                            <fb-select v-model="formData.productionMode"
                                       :placeholder="'请选择生产方式'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'E51'}"
                            />
                        </fb-form-item>
                    </fb-col>
                </fb-row>
                <fb-row>

                    <fb-col span="12">
                        <fb-form-item label="原料" prop="rawMaterial">
                            <fb-input v-model="formData.rawMaterial" placeholder="请输入原料"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="化学品分类" prop="chemicalCategory" :rule="[{required: true}]">
                            <fb-select v-model="formData.chemicalCategory"
                                       :placeholder="'请选择化学品分类'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'E53'}"
                            />
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>

                    <fb-col span="12">
                        <fb-form-item label="危险化学品作业人数" prop="dangerChemicalPersonNum" :rule="[{required: true}, {type: 'integer'}]">
                            <fb-input v-model="formData.dangerChemicalPersonNum" placeholder="请输入危险化学品作业人数"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="库房数量（个）" prop="warehouseNum">
                            <fb-input v-model="formData.warehouseNum" placeholder="请输入库房数量（个）"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>

                    <fb-col span="12">
                        <fb-form-item label="库房总面积（㎡）" prop="warehouseArea">
                            <fb-input v-model="formData.warehouseArea" placeholder="请输入库房总面积（㎡）"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="储存总容积（m³）" prop="storageTotalVolume">
                            <fb-input v-model="formData.storageTotalVolume" placeholder="请输入储存总容积（m³）"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>

                    <fb-col span="12">
                        <fb-form-item label="储存设施存储能力" prop="storageCapacity">
                            <fb-input v-model="formData.storageCapacity" placeholder="请输入储存设施存储能力"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="储罐数量（个）" prop="tankNum">
                            <fb-input v-model="formData.tankNum" placeholder="请输入储罐数量（个）"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>

                    <fb-col span="12">
                        <fb-form-item label="储存设施产权" prop="storageFacilityProperty">
                            <fb-select v-model="formData.storageFacilityProperty"
                                       :placeholder="'请选择储存设施产权'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'E54'}"
                            />
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="经营方式" prop="businessPractice">
                            <fb-select v-model="formData.businessPractice"
                                       :placeholder="'请选择经营方式'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'E30'}"
                            />
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>

                    <fb-col span="24">
                        <fb-form-item label="产品特性及生产能力" prop="featureAndOutput">
                            <fb-input v-model="formData.featureAndOutput" placeholder="请输入产品特性及生产能力"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="是否属于重点监管对象" prop="regulatoryObject" :rule="[{required: true}]">
                            <fb-radio-group v-model="formData.regulatoryObject"
                                            :data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
                                            :reader="{label:'name', value:'id'}"></fb-radio-group>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="是否进入化工园区" prop="industrialPark" :rule="[{required: true}]">
                            <fb-radio-group v-model="formData.industrialPark"
                                            :data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
                                            :reader="{label:'name', value:'id'}"></fb-radio-group>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="是否完成危险化工工艺自动化控制系统改造" prop="autoControlSystem" :rule="[{required: true}]">
                            <fb-radio-group v-model="formData.autoControlSystem"
                                            :data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
                                            :reader="{label:'name', value:'id'}"></fb-radio-group>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="是否完成重点监管危化品的生产装置和设施以及重大危险源的自动化控制系统的改造" prop="reform"
                                      :rule="[{required: true}]">
                            <fb-radio-group v-model="formData.reform"
                                            :data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
                                            :reader="{label:'name', value:'id'}"></fb-radio-group>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

            </fb-form>
		</fb-page-header>


        <div style="box-sizing: border-box;text-align: right;background-color: #FFFFFF;position: relative;bottom: 0;left: 0;right: 0;padding: 16px;border-top: 1px solid #ccc">
            <fb-button style="margin-right: 12px" type="primary" @on-click="add">保存</fb-button>
            <fb-button @on-click="handleClose">关闭</fb-button>
        </div>
    </div>
</template>


<script>

    import Page from "@/util/Page"

    export default {
        name: 'add-basicinfo',
        mixins: [
            Page
        ],
        // 接收父组件的传参
        props: {

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
			this.formData.storageAddr = this.$route.params.storageAddr;
            this.init(this.$datax.get('entId'));
        },
        data() {
            return {
                // 请求的 service
                service: app.$svc.sys.demo,
                // 表单数据
                formData: {
                    dangerId: '',
                    entId: '',
                    storageAddr: '',
                    storageMode: '',
                    productionMode: '',
                    rawMaterial: '',
                    chemicalCategory: '',
                    dangerChemicalPersonNum: '',
                    warehouseNum: '',
                    warehouseArea: '',
                    storageTotalVolume: '',
                    storageCapacity: '',
                    tankNum: '',
                    storageFacilityProperty: '',
                    businessPractice: '',
                    featureAndOutput: '',
                    regulatoryObject: '',
                    industrialPark: '',
                    autoControlSystem: '',
                    reform: '',
                    actived: '',
                    creator: '',
                    createTime: '',
                    updator: '',
                    updateTime: '',
                },
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
                if (param) {
                    this.formData.entId = param;
                    this.view()
                }
            },

            // 取消
            handleClose() {
                this.$store.commit('tabbar/removeNow');
            },

            add() {
                let that = this
                // 界面校验
                this.$refs.fbform.validate((result) => {
                    if (result === true) {
                        if (that.formData.dangerId) {
                            // 调用新增service方法
                            that.service.update(that.formData).then((result) => {
                                // 判断code
                                if (result.code == 1) {
                                    that.$message.success('修改成功');
                                } else {
                                    // 服务器返回失败
                                    that.$message.error('修改失败:' + result.message)
                                }
                                ;
                                that.updateCount = 0;
                            })
                        } else {
                            // 调用新增service方法
                            that.service.add(that.formData).then((result) => {
                                // 判断code
                                if (result.code == 1) {
                                    that.$message.success('新增成功');
                                    let dangerId = result.data;
                                    this.formData.dangerId = dangerId;
                                } else {
                                    // 服务器返回失败
                                    that.$message.error('新增失败:' + result.message)
                                }
                                ;
                                that.updateCount = 0;
                            })
                        }
                    }
                })
            },

            // 查询信息
            view() {
                // 调用新增service方法
                /*this.service.view({"entId": this.formData.entId}).then((result) => {
                    // 判断code
                    if (result.code == 1) {
                        this.formData = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('错误提示:' + result.message)
                    }
                })*/
            },
        }
    }
</script>

<style lang="less" scoped>

</style>
