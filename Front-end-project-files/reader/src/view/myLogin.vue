<template>
 <section>
   <div class="login-wrap">
     <div class="login-card">
       <h1 class="login-title">城北图书系统</h1>
       <div class="login-content">
         <div class="user-image">
           <el-avatar :size="70" :src="circleUrl" @click="openUser"/>
         </div>
         <div class="login-main">
           <el-form ref="ruleFormRef" :model="loginForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
             <el-form-item label="登录名:" prop="userLoginName" class="userLoginName">
               <el-input v-model="loginForm.userLoginName" type="text" autocomplete="off"/>
             </el-form-item>
             <el-form-item label="密码:" prop="password" class="loginPassword">
               <el-input v-model="loginForm.password" type="password" autocomplete="off" />
             </el-form-item>
             <el-form-item class="login-button">
               <el-button type="primary" @click="submitLogin">登录</el-button>
             </el-form-item>
           </el-form>
         </div>
       </div>
     </div>
     </div>
 </section>
</template>

<script>
import {loginByReader} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      loginForm:{},
      userLoginName:'',
      rules: {
        userLoginName: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur"
          },
        ],
      },
    }
  },
  methods:{
    submitLogin() {
      this.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
          // alert(this.loginForm)
          sessionStorage.removeItem('AppointToken');
          loginByReader(this.loginForm).then((res) => {
            if (res.data.statusCode === 'C200') {
              // alert(res.headers['token'])
              //将token存入页面会话域中
              sessionStorage.setItem("AppointToken",res.headers['token'])
              // sessionStorage.setItem("showView",JSON.stringify(res.data.result.showView))
              ElMessage.success(res.data.msgId);
              // localStorage.setItem("admin", JSON.stringify(res.data.result.adminInformation));
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              //存储用户信息
              localStorage.setItem("userInformation",JSON.stringify(res.data.result))
              // 前端页面不要写绝对地址，上线很容易出错，更改1
              window.location.href = 'http://'+window.location.host+'/#/home';
            } else {
              ElMessage.error(res.data.message);
            }
          })
        } else {
          ElMessage.error("登录失败");
          return false;
        }
      });
    },
  },
  mounted() {
  }
}
</script>

<style scoped>
.login-title{
  margin-left: 130px;
  margin-top: 10px;
}
.login-wrap{
  width: 100%;
  height: 575px;
  background-image: url("../assets/library4.png");
  position: relative;
}
.login-card{
  width: 400px;
  height: 350px;
  background-color: rgba(255, 255, 255, 0.5);
  position: absolute;
  top: 130px;
  left: 70px;
  border-radius: 5%;
  border: #758a99 1px solid;
  box-shadow: 0 0 20px rgba(0,0,0,0.8);
}
.login-content{
  margin-top: 80px;
}
.user-image {
  position: absolute;
  left: 170px;
  top: 60px;
}
.login-main{
  position: absolute;
  top: 140px;
  right: 70px;
}
.userLoginName{
  margin-top: 20px;
  width: 350px;
  margin-bottom: 30px;
}
.login-button{
  position: absolute;
  left: 80px;
  top: 150px;
}

</style>