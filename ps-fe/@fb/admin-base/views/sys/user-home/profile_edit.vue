<template>
    <div class="tp-dialog">
        <div class="tp-dialog-top">
            <fb-form ref="editForm" :model="formData" :rules="formRules">
                <!-- 基本信息和头像 -->
                <fb-row>
                    <fb-col span="12">
                        <fb-row>
                            <fb-col span="24">
                                <fb-form-item label="姓名" prop="realName">
                                    <fb-input v-model="formData.realName" placeholder="请输入姓名"></fb-input>
                                </fb-form-item>
                            </fb-col>
                        </fb-row>
                        <fb-row>
                            <fb-col span="24">
                                <fb-form-item label="性别" prop="sex">
                            <fb-select v-model="formData.sex"
                                       :data="defaultForm.sex"
                                       :placeholder="'请选择'"
                                       clearable/>
                        </fb-form-item>
                            </fb-col>
                        </fb-row>
                        <fb-row>
                            <fb-col span="24">
                                <fb-form-item label="证件类型" prop="idtype">
                            <fb-select v-model="formData.idtype"
                                       :placeholder="'请选择证件类型'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'Y24'}"
                                       @on-change="idtypeChange"/>
                        </fb-form-item>
                            </fb-col>
                        </fb-row>
                        <fb-row>
                            <fb-col span="24">
                                <fb-form-item label="证件号" prop="idcard">
                                    <fb-input v-model="formData.idcard" placeholder="请输入证件号" @on-blur="birthdayFormat"></fb-input>
                                </fb-form-item>
                            </fb-col>
                        </fb-row>
                    </fb-col>
                    <fb-col span="12">
                        <fb-row>
                            <fb-col span="17">
                                <fb-form-item label="头像">
                                    <tp-upload
                                        view="avatar"
                                        v-model="file.fileList"
                                        :service="$svc.sys.file"
                                        :param="{'referType': file.referType}"
                                        :referid="formData.personId"
                                        :accept="'.png,.jpeg,.jpg'"
                                    ></tp-upload>
                                </fb-form-item>
                            </fb-col>
                        </fb-row>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="出生日期" prop="birthday">
                            <tp-datepicker v-model="formData.birthday" format="YYYY-MM-DD" value-format="YYYYMMDD" clearable></tp-datepicker>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="民族" prop="safeprinNation">
                            <fb-select v-model="formData.safeprinNation"
                                       :placeholder="'请选择'"
                                       :service="$svc.sys.dict.select"
                                       :param="{'pdicCode': 'Y26'}"
                                       filterable
                                       clearable/>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="手机号" prop="phone">
                            <fb-input v-model="formData.phone" placeholder="请输入手机号"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="联系电话" :rule="{required: false, type: 'telmobile'}">
                            <fb-input v-model="formData.tel" placeholder="请输入联系电话"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="24">
                        <fb-form-item label="邮箱" prop="email">
                            <fb-input v-model="formData.email" placeholder="请输入邮箱"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="职位">
                            <fb-input
                                type="text"
                                v-model="formData.office"
                                placeholder="请输入职位">
                            </fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="人员编号" prop="personNo">
                            <fb-input v-model="formData.personNo" placeholder="请输入人员编号"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="参加工作时间" prop="partWorkDate">
                            <tp-datepicker v-model="formData.partWorkDate" format="YYYY-MM-DD" value-format="YYYYMMDD" clearable></tp-datepicker>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="通信地址" prop="maddress">
                            <fb-input v-model="formData.maddress" placeholder="请输入通信地址"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="毕业学校" prop="school">
                            <fb-input v-model="formData.school" placeholder="请输入毕业学校"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="所学专业" prop="sepcSubject">
                            <fb-input v-model="formData.sepcSubject" placeholder="请输入所学专业"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="12">
                        <fb-form-item label="最高学位" prop="degree">
                            <fb-input v-model="formData.degree" placeholder="请输入最高学位"></fb-input>
                        </fb-form-item>
                    </fb-col>
                    <fb-col span="12">
                        <fb-form-item label="执法证号" prop="checkcardNo">
                            <fb-input v-model="formData.checkcardNo" placeholder="请输入执法证号"></fb-input>
                        </fb-form-item>
                    </fb-col>
                </fb-row>

                <fb-row>
                    <fb-col span="24">
                        <fb-form-item label="个人简介" prop="resume">
                            <fb-textarea v-model="formData.resume" rows="2" placeholder="请输入个人简介" :maxlength="200"></fb-textarea>
                        </fb-form-item>
                    </fb-col>
                </fb-row>
            </fb-form>
        </div>

        <div class="tp-dialog-bottom">
            <fb-button style="margin-right: 12px" type="primary" @on-click="saveProfile">保存</fb-button>
            <fb-button @on-click="handleClose">关闭</fb-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ProfileEdit',
    props: {
        param: {
            type: Object,
            default: () => ({})
        },
        userInfo: {
            type: Object,
            default: () => ({})
        },
        dialogParams: {
            type: Object,
            default: () => ({})
        }
    },
    data() {
        return {
            service: this.$svc.sys.person,
            saving: false,
            file: {
                referType: 'SYS1014',
                fileList: []
            },
            formData: {
                personId: '',
                realName: '',
                sex: '',
                idtype: 'Y2401',
                idcard: '',
                birthday: '',
                safeprinNation: '',
                phone: '',
                tel: '',
                email: '',
                maddress: '',
                position: '',
                office: '',
                personNo: '',
                partWorkDate: '',
                school: '',
                sepcSubject: '',
                degree: '',
                checkcardNo: '',
                resume: ''
            },
            defaultForm: {
                sex: [
                    { value: '1', label: '男' },
                    { value: '2', label: '女' }
                ]
            },
            formRules: {
                realName: [
                    { required: true, message: '请输入姓名', trigger: 'blur' }
                ],
                email: [
                    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
                ],
                phone: [
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
                ],
                sex: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ],
                idtype: [
                    { required: true, message: '请选择证件类型', trigger: 'change' }
                ],
                idcard: [
                    { required: true, message: '请输入证件号', trigger: 'blur' }
                ]
            }
        }
    },
    mounted() {
        this.initFormData()
    },
    watch: {
        userInfo: {
            handler(newVal) {
                if (newVal) {
                    this.initFormData()
                }
            },
            deep: true,
            immediate: true
        },
        dialogParams: {
            handler(newVal) {
                if (newVal && newVal.userInfo) {
                    this.initFormData(newVal.userInfo)
                }
            },
            deep: true,
            immediate: true
        }
    },
    methods: {
        // 初始化表单数据
        initFormData(userData) {
            // 优先使用TpDialog传递的param参数，然后是传入的用户数据，再是组件的userInfo，最后是dialogParams中的userInfo
            const userInfo = (this.param && this.param.userInfo) || userData || this.userInfo || (this.dialogParams && this.dialogParams.userInfo)
            if (userInfo && Object.keys(userInfo).length > 0) {
                this.formData = {
                    personId: userInfo.personId || '',
                    realName: userInfo.personName || userInfo.realName || '',
                    sex: userInfo.sex || '',
                    idtype: userInfo.idtype || '',
                    idcard: userInfo.idcard || '',
                    birthday: userInfo.birthday || '',
                    safeprinNation: userInfo.safeprinNation || '',
                    phone: userInfo.phone || '',
                    tel: userInfo.tel || '',
                    email: userInfo.email || '',
                    maddress: userInfo.maddress || '',
                    position: userInfo.position || '',
                    office: userInfo.office || '',
                    personNo: userInfo.personNo || '',
                    partWorkDate: userInfo.partWorkDate || '',
                    school: userInfo.school || '',
                    sepcSubject: userInfo.sepcSubject || '',
                    degree: userInfo.degree || '',
                    checkcardNo: userInfo.checkcardNo || '',
                    resume: userInfo.resume || ''
                }

                // 设置头像文件列表
                if (userInfo.profilePhoto) {
                    this.file.fileList = [{
                        url: userInfo.profilePhoto,
                        name: 'avatar'
                    }]
                }
            }
        },

        // 身份证号自动提取生日
        birthdayFormat() {
            let idcard = this.formData.idcard
            if (this.formData.idtype === 'Y2401' && idcard && (idcard.length === 15 || idcard.length === 18)) {
                this.formData.birthday = idcard.substring(6, 14)
            }
        },

        // 保存个人信息
        saveProfile() {
            this.$refs.editForm.validate((valid) => {
                if (valid) {
                    this.saving = true

                    // 准备提交数据
                    const submitData = {
                        ...this.formData,
                        file: this.file
                    }

                    this.service.update(submitData).then((result) => {
                        this.saving = false
                        if (result.code === 1) {
                            this.$message.success('保存成功')
                            // 关闭弹框
                            if (this.$parent && this.$parent.$parent && this.$parent.$parent.close) {
                                this.$parent.$parent.close()
                            }
                            // 通知父组件刷新数据
                            this.$emit('save-success', result.data)
                        } else {
                            this.$message.error('保存失败：' + (result.message || '未知错误'))
                        }
                    }).catch((error) => {
                        this.saving = false
                        this.$message.error('保存失败：' + error.message)
                    })
                } else {
                    this.$message.error('请检查表单填写是否正确')
                }
            })
        },

        // 证件类型改变时清空证件号
        idtypeChange() {
            this.formData.idcard = ''
        },

        // 取消编辑
        cancelEdit() {
            this.$emit('cancel-edit')
            this.handleClose()
        },

        // 关闭对话框
        handleClose() {
				// 关闭，并传递参数
				this.closeTpDialog("param");
			},
    }
}
</script>

<style lang="less" scoped>
.profile-edit {
    padding: 20px;
    background: #fff;
    border-radius: 8px;

    .edit-container {
        max-width: 800px;
        margin: 0 auto;
    }

    .avatar-upload-section {
        text-align: center;
        margin-bottom: 30px;
        padding: 20px;
        background: #fafafa;
        border-radius: 8px;

        .avatar-upload-wrapper {
            display: inline-block;
        }
    }

    .form-section {
        margin-bottom: 30px;

        .section-title {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #e8e8e8;
            font-size: 16px;
            font-weight: 600;
            color: #333;

            i {
                margin-right: 8px;
                font-size: 18px;
                color: #1890ff;
            }
        }
    }

    .form-actions {
        text-align: center;
        margin-top: 40px;
        padding-top: 20px;
        border-top: 1px solid #e8e8e8;

        .fb-button {
            margin: 0 10px;
            min-width: 100px;

            .fb-icon {
                margin-right: 5px;
            }
        }
    }
}

// 表单样式优化
.fb-form {
    .fb-form-item {
        margin-bottom: 20px;

        .fb-form-item-label {
            font-weight: 500;
            color: #333;
        }
    }

    .fb-input,
    .fb-select,
    .fb-date-picker,
    .fb-textarea {
        width: 100%;
    }

    .fb-textarea {
        resize: vertical;
    }
}

// 响应式设计
@media (max-width: 768px) {
    .profile-edit {
        padding: 15px;

        .edit-container {
            max-width: 100%;
        }

        .fb-col {
            span: 24 !important;
        }
    }
}
</style>