<template>
  <div class="header">
    <div class="left">
      <el-icon class="back" v-if="state.hasBack" @click="back"><Back /></el-icon>
      <span style="font-size: 20px">{{ state.name }}</span>
    </div>
    <div class="right">
        <div class="nickname">
          <p>登录名：
            {{state.managerName}}
          </p>
          <el-tag size="small" effect="dark" class="updatePassword" @click="updatePasswordShow">修改密码</el-tag>
          <el-tag size="small" effect="dark" class="logout" @click="logout">退出</el-tag>
        </div>
    </div>
  </div>
  <!--  密码弹窗-->
    <el-dialog v-model="state.passwordUpdateShow"  title="密码修改" style="width: 500px;">
      <el-form :model="state.passwordUpdateList" ref="vForm"  label-position="right" label-width="80px"
               size="default" @submit.native.prevent>
        <el-form-item label="用户名:" prop="userLoginName">
          <el-input v-model="state.passwordUpdateList.userLoginName" type="text" clearable style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password">
          <el-input v-model="state.passwordUpdateList.password" type="password" clearable style="width: 300px"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="state.passwordUpdateShow = false">取消</el-button>
          <el-button type="primary" @click="updatePassword">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script setup>
import {onMounted, reactive} from 'vue'
import {useRouter} from 'vue-router'
import axios from '@/utils/axios'
import {localRemove, pathMap} from '@/utils'
import {updateAdminPassword} from "@/api/library_admin";
import {ElMessage} from "element-plus";

// const passwordUpdateShow = false
const router = useRouter()
const state = reactive({
  name: 'dashboard',
  managerName:JSON.parse(localStorage.getItem("admin")).managerName,
  userInfo:JSON.parse(localStorage.getItem("admin")), // 用户信息变量
  hasBack: false, // 是否展示返回icon
  passwordUpdateShow:false,
  passwordUpdateList:{
    userLoginName:undefined,
    password:undefined,
  },
  configs:{
    headers: {
      token: sessionStorage.getItem('AppointToken')
    }
  }
})


//弹窗
const updatePasswordShow= async () => {
  state.passwordUpdateShow = !state.passwordUpdateShow;
}
//修改自己密码
const updatePassword=()=>{
  let para = {
    userLoginName:state.passwordUpdateList.userLoginName===''?undefined:state.passwordUpdateList.userLoginName,
    password:state.passwordUpdateList.password===''?undefined:state.passwordUpdateList.password,
  }
  updateAdminPassword(para,state.configs).then((res) => {
    if (res.data.statusCode === 'C200') {
      ElMessage.success(res.data.message);
      state.passwordUpdateShow = false;
      logout();
    } else {
      ElMessage.error(res.data.message);
    }
  })
}
// 退出登录
const logout =()=> {
    // 退出之后，将本地保存的 token  清理掉
    sessionStorage.clear();
    localStorage.clear();
    // 回到登录页
    this.$router.push('/login')

}
router.afterEach((to) => {
  const { id } = to.query
  state.name = pathMap[to.name]
  // level2 和 level3 需要展示返回icon
  console.log('to.name', to.name)
  state.hasBack = ['level2', 'level3'].includes(to.name)
})
// 返回方法
const back = () => {
  router.back()
}
</script>

<style scoped>
  .header {
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
  }
  .header .left .back {
    border: 1px solid #e9e9e9;
    padding: 5px;
    border-radius: 50%;
    margin-right: 5px;
    cursor: pointer;
  }
  .right > div > .icon{
    font-size: 18px;
    margin-right: 6px;
  }
  .author {
    margin-left: 10px;
    cursor: pointer;
  }
</style>

<style>
  .nickname {
    position: relative;
  }
  .nickname .logout {
    position: absolute;
    right: 0;
    top: 0;
    cursor: pointer;
    color: #ffffff;
  }
  .nickname .updatePassword{
    position: absolute;
    right: 50px;
    top: 0;
    cursor: pointer;
    color: #ffffff;
  }
  .nickname p{
    margin-right: 150px;
    color:#0C0C0CFF;
  }
</style>