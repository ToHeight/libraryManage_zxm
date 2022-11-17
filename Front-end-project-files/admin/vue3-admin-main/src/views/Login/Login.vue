<template>
  <div class="login-body">
    <div class="login-container">
      <div class="head">
        <img class="logo" src="../../../public/logo.ico" />
        <div class="name">
          <div class="title">城南图书馆</div>
          <div class="tips">后台管理系统</div>
        </div>
      </div>
      <el-form label-position="top"  :rules="loginRules" :model="ruleForm" ref="adminLogin" class="login-form">
        <el-form-item label="账号" prop="username">
          <el-input type="text" v-model.trim="ruleForm.userLoginName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model.trim="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="color: #333">登录表示您已同意<a>《服务条款》</a></div>
          <el-button style="width: 100%" type="primary" @click="submitForm">立即登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

import {loginByAdmin} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      ruleForm: {
        userLoginName: undefined,
        password: undefined,
      },
      loginRules: {
        userLoginName: [
          {required: 'true', message: '账户不能为空', trigger: 'blur'}
        ],
        password: [
          {required: 'true', message: '密码不能为空', trigger: 'blur'}
        ]
      },
      userImage: undefined,
    }
  },
  methods: {
    submitForm() {
      this.$refs.adminLogin.validate((valid) => {
        if (valid) {
          // alert(this.loginForm)
          sessionStorage.removeItem('AppointToken');
          loginByAdmin(this.ruleForm).then((res) => {
            // alert(res)
            if (res.data.statusCode === 'C200') {
              // alert(res.headers['token'])
              sessionStorage.setItem("AppointToken", res.headers['token'])
              ElMessage.success(res.data.message);
              localStorage.setItem("admin", JSON.stringify(res.data.result));
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              localStorage.setItem("userInformation", JSON.stringify(res.data.result))
              // 前端页面不要写绝对地址，上线很容易出错，更改1
              // alert(11);
              window.location.href = 'http://' + window.location.host + '/#/bookManage';
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
  .login-body {
    display: flex;
    /*justify-content: center;*/
    align-items: center;
    width: 100%;
    background-color: #fff;
    background-image: url("../../assets/loginBg.png");
  }
  .login-container {
    margin-left: 700px;
    width: 420px;
    height: 500px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0px 21px 41px 0px rgba(0, 0, 0, 0.2);
  }
  .head {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 40px 0 20px 0;
  }
  .head img {
    width: 100px;
    height: 100px;
    margin-right: 20px;
  }
  .head .title {
    font-size: 28px;
    color: #35a5ed;
    font-weight: bold;
  }
  .head .tips {
    font-size: 12px;
    color: #999;
  }
  .login-form {
    width: 70%;
    margin: 0 auto;
  }
  .login-form >>> .el-form--label-top .el-form-item__label {
    padding: 0;
  }
  .login-form >>> .el-form-item {
    margin-bottom: 10px;
  }
</style>