<template>
  <div class="user-profile">
    <fb-flex direction-column gap="16px">
      <!-- 个人信息卡片 -->
      <div class="card-wrapper">
        <div class="header">
          <fb-icon name="user" size="16" color="#2FA1FE"></fb-icon>
          <span class="title">个人信息</span>
        </div>
        <div class="body">
          <fb-flex gap="24px">
            <!-- 头像区域 -->
            <div class="avatar-section">
              <div class="avatar-wrapper">
                <img :src="userInfo.avatar || defaultAvatar" alt="用户头像" class="avatar" />
                <div class="avatar-overlay" @click="uploadAvatar">
                  <fb-icon name="camera" size="20" color="#fff"></fb-icon>
                </div>
              </div>
              <fb-button type="text" size="small" @click="uploadAvatar">更换头像</fb-button>
            </div>
            
            <!-- 基本信息 -->
            <div class="info-section">
              <fb-flex direction="column" gap="16px">
                <fb-flex gap="32px">
                  <div class="info-item">
                    <label>用户名</label>
                    <span class="value">{{ userInfo.username }}</span>
                  </div>
                  <div class="info-item">
                    <label>姓名</label>
                    <span class="value">{{ userInfo.realName }}</span>
                  </div>
                </fb-flex>
                <fb-flex gap="32px">
                  <div class="info-item">
                    <label>邮箱</label>
                    <span class="value">{{ userInfo.email }}</span>
                  </div>
                  <div class="info-item">
                    <label>手机号</label>
                    <span class="value">{{ userInfo.phone }}</span>
                  </div>
                </fb-flex>
                <fb-flex gap="32px">
                  <div class="info-item">
                    <label>部门</label>
                    <span class="value">{{ userInfo.deptName }}</span>
                  </div>
                  <div class="info-item">
                    <label>职位</label>
                    <span class="value">{{ userInfo.position }}</span>
                  </div>
                </fb-flex>
              </fb-flex>
            </div>
          </fb-flex>
          
          <div class="actions">
            <fb-button type="primary" @click="editProfile">编辑信息</fb-button>
          </div>
        </div>
      </div>

      <!-- 安全设置卡片 -->
      <div class="card-wrapper">
        <div class="header">
          <fb-icon name="shield" size="16" color="#FC9C3B"></fb-icon>
          <span class="title">安全设置</span>
        </div>
        <div class="body">
          <fb-flex direction="column" gap="16px">
            <div class="security-item clickable" @click="changePassword">
              <fb-flex justify="space-between" align="center">
                <div>
                  <fb-flex align="center" gap="12px">
                    <fb-icon name="lock" size="16" color="#666"></fb-icon>
                    <div>
                      <div class="item-title">修改密码</div>
                      <div class="item-desc">定期修改密码，保护账户安全</div>
                    </div>
                  </fb-flex>
                </div>
                <fb-icon name="arrow-right" size="16" color="#999"></fb-icon>
              </fb-flex>
            </div>
            
            <div class="security-item clickable" @click="viewLoginHistory">
              <fb-flex justify="space-between" align="center">
                <div>
                  <fb-flex align="center" gap="12px">
                    <fb-icon name="history" size="16" color="#666"></fb-icon>
                    <div>
                      <div class="item-title">登录记录</div>
                      <div class="item-desc">查看最近的登录活动</div>
                    </div>
                  </fb-flex>
                </div>
                <fb-icon name="arrow-right" size="16" color="#999"></fb-icon>
              </fb-flex>
            </div>
          </fb-flex>
        </div>
      </div>

      <!-- 系统信息卡片 -->
      <div class="card-wrapper">
        <div class="header">
          <fb-icon name="info" size="16" color="#1FB2AF"></fb-icon>
          <span class="title">系统信息</span>
        </div>
        <div class="body">
          <fb-flex direction="column" gap="12px">
            <fb-flex justify="space-between">
              <span class="label">最后登录时间</span>
              <span class="value">{{ userInfo.lastLoginTime }}</span>
            </fb-flex>
            <fb-flex justify="space-between">
              <span class="label">账户创建时间</span>
              <span class="value">{{ userInfo.createTime }}</span>
            </fb-flex>
            <fb-flex justify="space-between">
              <span class="label">账户状态</span>
              <fb-badge :type="userInfo.status === 1 ? 'success' : 'danger'">
                {{ userInfo.status === 1 ? '正常' : '禁用' }}
              </fb-badge>
            </fb-flex>
          </fb-flex>
        </div>
      </div>
    </fb-flex>

    <!-- 编辑信息弹窗 -->
    <fb-dialog v-model="editDialogVisible" title="编辑个人信息" width="600px">
      <fb-form ref="editForm" :model="editForm" :rules="editRules" label-width="80px">
        <fb-form-item label="姓名" prop="realName">
          <fb-input v-model="editForm.realName" placeholder="请输入姓名"></fb-input>
        </fb-form-item>
        <fb-form-item label="邮箱" prop="email">
          <fb-input v-model="editForm.email" placeholder="请输入邮箱"></fb-input>
        </fb-form-item>
        <fb-form-item label="手机号" prop="phone">
          <fb-input v-model="editForm.phone" placeholder="请输入手机号"></fb-input>
        </fb-form-item>
        <fb-form-item label="职位" prop="position">
          <fb-input v-model="editForm.position" placeholder="请输入职位"></fb-input>
        </fb-form-item>
      </fb-form>
      <template #footer>
        <fb-button @click="editDialogVisible = false">取消</fb-button>
        <fb-button type="primary" @click="saveProfile">保存</fb-button>
      </template>
    </fb-dialog>

    <!-- 修改密码弹窗 -->
    <fb-dialog v-model="passwordDialogVisible" title="修改密码" width="500px">
      <fb-form ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="100px">
        <fb-form-item label="当前密码" prop="oldPassword">
          <fb-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入当前密码"></fb-input>
        </fb-form-item>
        <fb-form-item label="新密码" prop="newPassword">
          <fb-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码"></fb-input>
        </fb-form-item>
        <fb-form-item label="确认密码" prop="confirmPassword">
          <fb-input v-model="passwordForm.confirmPassword" type="password" placeholder="请再次输入新密码"></fb-input>
        </fb-form-item>
      </fb-form>
      <template #footer>
        <fb-button @click="passwordDialogVisible = false">取消</fb-button>
        <fb-button type="primary" @click="savePassword">确定</fb-button>
      </template>
    </fb-dialog>
  </div>
</template>

<script>
import app from '@fb/fb-core'

export default {
  name: 'UserProfile',
  data() {
    return {
      userInfo: {
        username: '',
        realName: '',
        email: '',
        phone: '',
        deptName: '',
        position: '',
        avatar: '',
        status: 1,
        lastLoginTime: '',
        createTime: ''
      },
      defaultAvatar: '/assets/images/default-avatar.png',
      editDialogVisible: false,
      passwordDialogVisible: false,
      editForm: {
        realName: '',
        email: '',
        phone: '',
        position: ''
      },
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      editRules: {
        realName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ]
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入当前密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadUserInfo()
  },
  methods: {
    // 加载用户信息
    async loadUserInfo() {
      try {
        const response = await app.api.get('/sys/user/profile')
        if (response.success) {
          this.userInfo = response.data
        }
      } catch (error) {
        this.$message.error('加载用户信息失败')
      }
    },
    
    // 上传头像
    uploadAvatar() {
      // 创建文件输入元素
      const input = document.createElement('input')
      input.type = 'file'
      input.accept = 'image/*'
      input.onchange = (e) => {
        const file = e.target.files[0]
        if (file) {
          this.handleAvatarUpload(file)
        }
      }
      input.click()
    },
    
    // 处理头像上传
    async handleAvatarUpload(file) {
      const formData = new FormData()
      formData.append('file', file)
      
      try {
        const response = await app.api.post('/sys/user/upload-avatar', formData)
        if (response.success) {
          this.userInfo.avatar = response.data.url
          this.$message.success('头像更新成功')
        }
      } catch (error) {
        this.$message.error('头像上传失败')
      }
    },
    
    // 编辑个人信息
    editProfile() {
      this.editForm = {
        realName: this.userInfo.realName,
        email: this.userInfo.email,
        phone: this.userInfo.phone,
        position: this.userInfo.position
      }
      this.editDialogVisible = true
    },
    
    // 保存个人信息
    async saveProfile() {
      try {
        await this.$refs.editForm.validate()
        const response = await app.api.put('/sys/user/profile', this.editForm)
        if (response.success) {
          Object.assign(this.userInfo, this.editForm)
          this.editDialogVisible = false
          this.$message.success('信息更新成功')
        }
      } catch (error) {
        if (error !== false) {
          this.$message.error('信息更新失败')
        }
      }
    },
    
    // 修改密码
    changePassword() {
      this.passwordForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
      this.passwordDialogVisible = true
    },
    
    // 保存密码
    async savePassword() {
      try {
        await this.$refs.passwordForm.validate()
        const response = await app.api.put('/sys/user/change-password', {
          oldPassword: this.passwordForm.oldPassword,
          newPassword: this.passwordForm.newPassword
        })
        if (response.success) {
          this.passwordDialogVisible = false
          this.$message.success('密码修改成功')
        }
      } catch (error) {
        if (error !== false) {
          this.$message.error('密码修改失败')
        }
      }
    },
    
    // 验证确认密码
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    },
    
    // 查看登录记录
    viewLoginHistory() {
      this.$router.push('/sys/user/login-history')
    }
  }
}
</script>

<style scoped lang="less">
@import './profile.less';
</style>