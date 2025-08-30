<template>
  <div class="tp-dialog">
    <div class="tp-dialog-top">
      <fb-form ref="fbform" :rule="rules" :label-width="180">
        <fb-row>
          <fb-col span="12">
            <fb-row>
              <fb-col span="24">
                <fb-form-item label="名称" prop="entFullName" :rule="{required: true}">
                  <fb-input v-model="formData.name" :readonly="aaa"></fb-input>
                </fb-form-item>
              </fb-col>
            </fb-row>
            <fb-row>
              <fb-col span="24">
                <fb-form-item label="性别" prop="sex" :rule="{required: true}">
                  <fb-select v-model="formData.sex"
                             :data="defaultForm.sex"
                             :placeholder="'请选择'"
                             @on-change="sexChange"
                             clearable/>
                </fb-form-item>
              </fb-col>
            </fb-row>
            <fb-row>
              <fb-col span="24">
                <fb-form-item label="证件类型" prop="idtype" :rule='{required:true}'>
                  <fb-select v-model="formData.idtype"
                             :placeholder="'请选择证件类型'"
                             :service="$svc.sys.dict.select"
                             :param="{'pdicCode': 'Y24'}"/>
                </fb-form-item>
              </fb-col>
            </fb-row>
            <fb-row>
              <fb-col span="24">
                <!-- 动态校验规则，三元表达式写法 -->
                <fb-form-item label="证件号（三元表达式）" prop="idcard"
                              :rule="formData.idtype === 'Y2401' ? [{required: true}, {type: 'idcard'}] : {}">
                  <fb-input
                      v-model="formData.idcard"
                      placeholder="请输入证件号">
                  </fb-input>
                </fb-form-item>
              </fb-col>
            </fb-row>
          </fb-col>

          <fb-col span="12">
            <fb-form-item label="头像">
              <tp-upload
                  view="avatar"
                  v-model="file.fileList"
                  :service="$svc.sys.file"
                  :param="{'referType': file.referType}"
                  :referid="formData.id"
                  :accept="'.png,.jpeg,.jpg'"
              ></tp-upload>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="附件">
              <tp-upload
                  :param="{'referType': file.referType}"
                  :referid="formData.id"
                  :service="$svc.sys.file"
                  v-model="file.fileList"
              ></tp-upload>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="根据路径回显图片">
              <tp-upload-path
                  :accept="'.png,.jpeg,.jpg'"
                  :service="$svc.sys.file"
                  v-model="file2.fileList"
                  view="image"
              ></tp-upload-path>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="新框架开发" :rule="{required: true}">
              <fb-tooltip slot="label-extra" placement="top">
                <fb-container slot="content" width="80px">你想干嘛？</fb-container>
                <fb-icon name="information"/>
              </fb-tooltip>
              <fb-input/>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="小数1" prop="xs" :rule="{required: true}">
              <fb-input v-model="formData.xs1" :readonly="aaa"></fb-input>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="小数2" prop="xs" :rule="{required: true}">
              <fb-input v-model="formData.xs2" :readonly="aaa"></fb-input>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="证件类型" prop="idtype1" :rule='{required:true}'>
              <fb-select v-model="formData.idtype1"
                         :placeholder="'请选择证件类型'"
                         :service="$svc.sys.dict.select"
                         :param="{'pdicCode': 'Y24'}"
                         @on-change="idtypeChange"/>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <!-- 动态校验规则，方法写法 -->
            <fb-form-item label="证件号（方法）" prop="idcard1" :rule="idcardRule">
              <fb-input
                  v-model="formData.idcard1"
                  placeholder="请输入证件号">
              </fb-input>
            </fb-form-item>
          </fb-col>
        </fb-row>
        <fb-row>
          <fb-col span="12">
            <fb-form-item label="统一社会信用代码" prop="entUnifiedCode"
                          :rule="[{required: true}, {type: 'unicode'}]">
              <fb-input v-model="formData.entUnifiedCode" placeholder="请输入统一社会信用代码">
              </fb-input>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="监管分类1">
              <fb-tree-select
                  ref="regulatoryTypeCodeTree"
                  v-model="formData.regulatoryTypeCode"
                  :service="$svc.sys.dict.tree"
                  :param="{'pdicCode': 'E10'}"
                  :reader="{value: 'id', label: 'text'}"
                  :do-check="'s'"
                  :do-un-check="'s'"
                  placeholder="请选择"
                  @on-change="regulatoryChange"
                  multiple
                  clearable></fb-tree-select>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="监管等级">
              <fb-tree-select
                  v-model="formData.superviseGrade"
                  :service="$svc.sys.dict.tree"
                  :param="{'dicCode': 'E09'}"
                  placeholder="请选择"
                  @on-change="regulatoryChange"
                  multiple
                  clearable/>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="日期" prop="date1" :rule="{required: true}">
              <tp-datepicker v-model="formData.date1" format="YYYY-MM-DD HH:mm:ss"
                             value-format="YYYYMMDDHHmmss" placeholder="请选择时间日期"
                             :min-date="new Date('2021-03-01')" :max-date="new Date('2031-03-15')"
                             :showFootLeftBtns="['yesterday']"
                             clearable/>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="注册日期" prop="date" :rule="{required: true}">
              <tp-datepicker v-model="formData.date" format="YYYY-MM"
                             value-format="YYYYMMDDHHmmss" clearable></tp-datepicker>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="22">
            <fb-form-item label="日期(范围)">
              <tp-datepicker v-model="formData.date2" format="YYYY-MM-DD HH:mm:ss"
                             value-format="YYYYMMDDHHmmss" placeholder="请选择时间日期"
                             max-range="3D" mode="range"
                             clearable/>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="星期" prop="week" :rule="{required: true}">
              <fb-select v-model="formData.week"
                         :data="defaultForm.week"
                         :placeholder="'请选择'"
                         clearable/>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="注册资金" prop="regFund" :rule="[{required: true}, {type: 'number'}]">
              <fb-input placeholder="请输入内容" v-model="formData.regFund">
                <template slot="append">万元</template>
              </fb-input>
            </fb-form-item>
          </fb-col>
        </fb-row>
        <fb-row>
          <fb-col span="12">
            <fb-form-item label="联系人姓名" prop="personNames" :rule="{required: true}">
              <fb-popup-header multiple
                               clearable
                               icon="more"
                               @on-icon-click="selectPersonMultiple"
                               style="width: 100%;">
                <fb-badge :count="personNames.length" overflowCount="9">
                  <fb-tags style="overflow:hidden;" type="primary" effect="dark"
                           v-model="personNames"></fb-tags>
                </fb-badge>
              </fb-popup-header>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="联系手机" prop="linkPsnTel"
                          :rule='[{required: true, message: "请选择联系手机"}, {type: "mobile"}]'>
              <fb-input v-model="formData.linkPsnTel"></fb-input>
            </fb-form-item>
          </fb-col>
        </fb-row>
        <fb-row>
          <fb-col span="12">
            <fb-form-item label="机构树（完整）">
              <!-- 同步加载所有机构，直接调service-->

              <!--<fb-tree-select v-model="formData.allDeptTree"
                      :service="$svc.sys.dept.org.allTree"
                      :param="{'sync': 1}"
                      :placeholder="'请选择'"
                      :reader="{value:'id', label: 'text'}"
                      clearable>
              </fb-tree-select>-->

              <!-- 异步加载机构 -->
              <fb-tree-select
                  :data="allDeptData"
                  :load-data="loadDeptTreeData"
                  :placeholder="'请选择'"
                  :reader="{value:'id', label: 'text'}"
                  clearable
                  only-leaf
                  ref="allDeptTree"
                  v-model="formData.allDeptTree">
              </fb-tree-select>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="机构树（本级及下级）">
              <fb-tree-select v-model="formData.deptTree"
                              :service="$svc.sys.dept.org.tree"
                              :param="{}"
                              :placeholder="'请选择'"
                              :reader="{value:'id', label: 'text'}"
                              clearable>
              </fb-tree-select>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="企业部门树（完整）">
              <fb-tree-select
                  ref="entTree1"
                  v-model="formData.entTree1"
                  :service="$svc.sys.dept.ent.allTree"
                  :param="{'sync': 0}"
                  :reader="{value: 'id', label: 'text'}"
                  :do-check="'s'"
                  :do-un-check="'s'"
                  placeholder="请选择"
                  @on-change="regulatoryChange"
                  multiple
                  only-leaf
                  clearable></fb-tree-select>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="企业部门树（本级及下级）">
              <fb-tree-select
                  ref="entTree2"
                  v-model="formData.entTree2"
                  :service="$svc.sys.dept.ent.tree"
                  :param="{}"
                  :reader="{value: 'id', label: 'text'}"
                  :do-check="'s'"
                  :do-un-check="'s'"
                  placeholder="请选择"
                  multiple
                  clearable></fb-tree-select>
            </fb-form-item>
          </fb-col>
        </fb-row>


        <fb-row>
          <fb-col span="12">
            <fb-form-item label="行政区划（完整）" prop="addrCode" :rule="[{required: true}]">
              <!--:service="$svc.sys.city.tree"
              :param="{'sync': 1, 'expand': true, 'cityId': '-1', showTop: 'off'}"-->
              <fb-tree-select
                  ref="addrCodeTree"
                  v-model="formData.addrCode"
                  :data="cityData"
                  :reader="{value:'extend02', label: 'text'}"
                  :placeholder="'请选择'"
                  :header-format="cityTreeHeaderFormat"
                  :load-data="loadCityTreeData"
                  clearable
                  only-leaf>
              </fb-tree-select>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="行政区划（本级及下级）" prop="cityCode" :rule="{required: true}">
              <fb-tree-select
                  ref="cityTree"
                  v-model="formData.cityCode"
                  :service="$svc.sys.dept.deptCityTree"
                  :param="{'sync': 1, 'expand': true, 'showTop': 'on'}"
                  :reader="{value:'extend02', label: 'text'}"
                  :placeholder="'请选择'"
                  :header-format="cityTreeHeaderFormat"
                  clearable>
              </fb-tree-select>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="行政区划（两级）" prop="addrCode" :rule="[{required: true}]">
              <fb-tree-select
                  ref="addrCodeTree"
                  v-model="formData.addrCode"
                  :service="$svc.sys.city.selectTwoLevel"
                  :param="{'sync': 1, 'expand': true, 'cityId': '-1'}"
                  :reader="{value:'extend02', label: 'text'}"
                  :placeholder="'请选择'"
                  :header-format="cityTreeHeaderFormat"
                  clearable
                  only-leaf>
              </fb-tree-select>
            </fb-form-item>
          </fb-col>

          <fb-col span="12">
            <fb-form-item label="行政区划byCode">
              <!--<fb-tree-select
                ref="treeByCode"
                v-model="formData.addrCode"
                :service="$svc.sys.city.treeByCode"
                :param="{'sync': 1, 'expand': true, 'showTop': 'on'}"
                :reader="{value:'extend02', label: 'text'}"
                :placeholder="'请选择'"
                :header-format="cityTreeHeaderFormat"
                clearable
                only-leaf>
              </fb-tree-select>-->

              <fb-tree-select
                  :data="cityDataByCode"
                  :header-format="cityTreeHeaderFormat"
                  :load-data="loadCityTreeByCodeData"
                  :placeholder="'请选择'"
                  :reader="{value:'extend02', label: 'text'}"
                  clearable
                  only-leaf
                  ref="treeByCode"
                  v-model="formData.addrCode">
              </fb-tree-select>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="12">
            <fb-form-item label="email" prop="email" :rule="[{required:true}, {type: 'email'}]">
              <fb-input v-model="formData.email"></fb-input>
            </fb-form-item>
          </fb-col>
          <fb-col span="12">
            <fb-form-item label="是否启用">
              <fb-radio-group v-model="formData.enabled"
                              :data="[{id: 1, name: '是',}, {id: 0, name: '否',}]"
                              :reader="{label:'name', value:'id'}"></fb-radio-group>
            </fb-form-item>
          </fb-col>
        </fb-row>

        <fb-row>
          <fb-col span="24">
            <fb-form-item label="简介">
              <fb-textarea rows="5" v-model="formData.entDesc"
                           type="text"
                           placeholder="请输入内容"
                           maxlength="200">
              </fb-textarea>
            </fb-form-item>
          </fb-col>
        </fb-row>
        <fb-row>
          <fb-col span="24">
            <fb-form-item label="富文本">
              <fb-editor v-model="formData.entDesc"></fb-editor>
            </fb-form-item>
          </fb-col>
        </fb-row>
      </fb-form>
    </div>

    <div class="tp-dialog-bottom">
      <fb-button style="margin-right: 12px" type="primary" @on-click="add">保存基本信息</fb-button>
      <fb-button @on-click="handleClose">关闭</fb-button>
    </div>

    <tp-dialog ref="TpDialog"></tp-dialog>
  </div>
</template>


<script>

import Page from "@/util/Page"

export default {
  name: 'add',
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

    // Map demo
    // let mapData = new Map();
    // mapData.set('key1', 'value1')
    // mapData.set('key2', 'value2')
    // mapData.set('key1', 'value3')
    // let aaa = this.formatMap(mapData);

    // 执行界面初始化
    this.init(this.param);
  },
  data() {
    return {
      aaa: false,
      // 请求的 service
      service: this.$svc.sys.demo,
      // 附件列表
      file: {
        referType: 'SYS1014',
        fileList: [],
      },
      file2: {
        fileList: [{
          attachId: '/D1/93/71/251635838648.jpg',
          attachName: '测试 (1).jpg',
          savePath: '/D1/93/71/251635838648.jpg',
          downloagFlag: 'path'
        }],
      },
      // 证件号动态校验规则默认值，身份证校验
      idcardRule: [{required: true}, {type: 'idcard'}],
      // 人员多选
      personNames: [
        {
          value: 6,
          label: '张艳林',
        },
      ],
      allDeptData: [],
      // 行政区划树加载数据
      cityData: [],
      cityDataByCode: [],
      // 表单数据
      formData: {
        id: '',
        name: '',
        sex: '',
        week: '',
        idtype: 'Y2401',
        idcard: '',
        idtype1: 'Y2401',
        idcard1: '',
        xs1: '',
        xs2: '',
        entUnifiedCode: '',
        regulatoryTypeCode: [],
        regulatoryTypeName: [],
        superviseGrade: [],
        entTree1: [],
        entTree2: [],
        type: '',
        deptTree: '',
        allDeptTree: '',
        date: '',
        date1: this.currentDate("YYYYMMDDHHmmss"),
        regFund: '',
        email: '',
        linkPsnName: '',
        linkPsnTel: '',
        addrCode: '',
        cityCode: '',
        enabled: 1,
        entDesc: '',
      },
      rules: {
        'xs': {
          // 自定义 校验方法，方法名与参数固定不变
          validator: (rule, value, callback, source, options) => {
            // 可通过 _this 获取上下文
            if (value) {
              // 校验未通过，返回错误信息
              let pattern = /^\d+(.\d{1,2})?$/
              if (!pattern.test(value)) {
                return callback('请输入最多两位小数的数字');
              }
            }
            // 校验通过，返回空参数
            return callback();
          }
        }
      }
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
        this.view()
      }

      // utils/page.js的初始化方法
      // 下拉框行政区划树异步加载初始化方法
      this.$svc.sys.city.tree({cityId: '-1', sync: 0, expand: true, showTop: 'on'}).then((result) => {
        if (result.code == 1) {
          if (result.data.length > 0) {
            this.cityData = result.data;
          }
        } else {
          // 服务器返回失败
          this.$message.error('行政区划树加载失败' + result.message)
        }
      })

      // 初始化树数据
      this.initDeptTreeData("", "");
      this.initCityByCodeTreeData("", "");
    },

    // 初始化树数据
    initDeptTreeData(deptId, selectDeptId) {
      this.$svc.sys.dept.org.tree({deptId: deptId, 'sync': 0}).then((result) => {
        if (result.code == 1) {
          if (result.data.length > 0) {
            this.allDeptData = result.data;
            // 默认选中根节点
            this.$nextTick(() => {
              if (selectDeptId) {
                this.$refs.allDeptTree.selectNodeByValue(selectDeptId);
              } else {
                this.$refs.allDeptTree.selectNodeByValue(result.data[0].id);
              }
            })
          }
        } else {
          // 服务器返回失败
          this.$message.error('部门树加载失败' + result.message)
        }
      })
    },

    // 初始化树数据
    initCityByCodeTreeData(cityId, selectCityId) {
      this.$svc.sys.city.treeByCode({cityId: cityId, 'sync': 0, 'showTop': 'on'}).then((result) => {
        if (result.code == 1) {
          if (result.data.length > 0) {
            this.cityDataByCode = result.data;
            // 默认选中根节点
            this.$nextTick(() => {
              if (selectCityId) {
                this.$refs.treeByCode.selectNodeByValue(selectCityId);
              } else {
                this.$refs.treeByCode.selectNodeByValue(result.data[0].id);
              }
            })
          }
        } else {
          // 服务器返回失败
          this.$message.error('部门树加载失败' + result.message)
        }
      })
    },

    // 取消
    handleClose() {
      let param = {};
      this.closeTpDialog(param);
    },

    selectPersonMultiple() {
      let that = this;
      let param = {}
      let options = {
        callBack: function (result) {
          console.log("=============回调" + result)

          that.personNames = result
        }
      }
      this.$refs.TpDialog.show('/common/ent/select-person-multiple.vue', param, "人员选择", options);
    },

    add() {

      let that = this;
      // 图片处理，后端list接收
      that.formData.file = that.file;
      // 界面校验
      this.$refs.fbform.validate((result) => {
        if (result === true) {
          if (that.formData.personId) {
            // 修改
            this.service.update(that.formData).then((result) => {
              // 判断code
              if (result.code == 1) {
                this.$message.success('修改成功');
              } else {
                // 服务器返回失败
                this.$message.error('修改失败:' + result.data.message)
              }
            })
          } else {
            // 新增，调用新增service方法
            this.service.org.add(that.formData).then((result) => {
              // 判断code
              if (result.code == 1) {
                this.$message.success('新增成功');

                // 1 单弹出框新增成功，关闭弹出框，param是回传的参数
                let param = {};
                this.closeTpDialog(param);

                // 2 tab 新增，不用关闭弹出框，将返回的主键放入到缓存，可以在tab切换时，获取到主表的主键
                // let id = result.data.id;
                // this.formData.id = id;
                // this.setPageParam(id);
              } else {
                // 服务器返回失败
                this.$message.error('新增失败')
              }
            })
          }
        }
      })
    },
    // 查询信息
    view() {

      // 调用新增service方法
      this.service.view({"id": this.formData.id}).then((result) => {
        // 判断code
        if (result.code == 1) {
          this.formData = result.data
          this.formData.superviseGrade = result.data.superviseGrade.split(",");
        } else {
          // 服务器返回失败
          this.$message.error('错误提示:' + result.message)
        }
      })
    },

    sexChange(value, value1) {
      console.log(value)
    },

    idtypeChange(value) {
      let rule = []
      switch (value) {
        case 'Y2401':
          rule = [{required: true}, {type: 'idcard'}]
          break;
        case 'Y2402':
          rule = [{required: true}]
          break;
        default :
          rule = {}
          break;
      }
      this.idcardRule = rule
      this.formData.idcard1 = '';
    },

    regulatoryChange(value, node) {
      console.log(value, node)
    },

  }
}
</script>

<style lang="less" scoped>

</style>
