<template>
  <section>
    <div class="login-wrap">
      <div class="login-card">
        <h1 class="wraptitle">城北图书系统</h1>
        <div class="login-content" v-if="loginShowMessage">
          <div class="login-main">
            <el-form ref="ruleFormRef" :model="loginForm" status-icon :rules="rules" label-width="120px"
                     class="demo-ruleForm">
              <el-form-item label="登录名:" prop="userLoginName" class="userLoginName">
                <el-input v-model="loginForm.userLoginName" type="text" autocomplete="off"  clearable/>
              </el-form-item>
              <el-form-item label="密码:" prop="password" class="loginPassword">
                <el-input v-model="loginForm.password" type="password" autocomplete="off" clearable/>
              </el-form-item>
              <el-button link type="primary"  @click="forgetShow" style="margin-left: 290px;margin-top: -40px">忘记密码</el-button>

              <el-form-item class="login-button">
                <el-button type="primary" @click="submitLogin" style="width: 100px">登录</el-button>
              </el-form-item>
              <div class="visitor">
                <el-button  link type="primary" @click="visitLogin">游客登录</el-button>
              </div>
              <div class="registerShow">
                <span>没有账号?</span>
                <el-button  link type="primary" @click="registerShow">注册</el-button>
              </div>
              <div class="emailShowContent">
                <span>可以选择</span>
                <el-button  link type="primary" @click="emailShow">邮箱登录</el-button>
              </div>
            </el-form>
          </div>
        </div>
        <div class="email-content" v-if="emailShowMesaage">
          <div class="email-main">
            <el-form ref="ruleFormRef" :model="emailForm" status-icon :rules="emailRules" label-width="120px"
                     class="demo-ruleForm">
              <el-form-item label="登陆邮箱:" prop="email" class="email">
                <el-input v-model="emailForm.email" type="text" autocomplete="off"  clearable/>
              </el-form-item>
              <el-form-item label="验证码:" prop="emailCode" class="emailCode">
                <el-input v-model="emailForm.emailCode" type="text" autocomplete="off" style="width: 100px"  clearable/>
                <el-button type="primary" @click="updateEmail()" style="margin-left: 5px;width: 80px" :disabled="codeTimeoutDisable">{{totalTime}}</el-button>
              </el-form-item>
              <el-form-item class="login-button">
                <el-button type="primary" @click="emailLogin" style="width: 100px">登录</el-button>
              </el-form-item>
            </el-form>
            <div class="loginShowContent">
              <span>可以选择</span>
              <el-button  link type="primary" @click="loginShow">用户名密码登录</el-button>
            </div>
          </div>
        </div>
        <div class="forget-content" v-if="resetPasswordMesaage">
          <div class="forget-main">
            <el-form ref="ruleFormRef" :model="forgetForm" status-icon :rules="forgetRules" label-width="120px"
                     class="demo-ruleForm">
              <el-form-item label="邮箱地址:" prop="forgetEmail" class="userForgetEmail">
                <el-input v-model="forgetForm.forgetEmail" type="text" autocomplete="off" style="width: 200px"  clearable/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="forgetPassword" style="width: 100px;margin-top: 10px;margin-left: 40px">提交</el-button>
              </el-form-item>
            </el-form>
            <div class="forgetShowContent">
              <span>点击返回</span>
              <el-button  link type="primary" @click="loginShow">登录界面</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import {emailSend, loginByReader, userEmailLogin, userResetPassword} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";
import {publicKsy} from '@/api/keyJSEncrypt';

export default {
  data() {
    return {
      loginForm: {},
      userLoginName: '',
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
      userList: [],
      userName: undefined,
      loginShowMessage:true,
      emailShowMesaage:false,
      resetPasswordMesaage:false,

      //邮箱登录
      emailForm:[],
      email:undefined,
      emailCode:undefined,
      totalTime:'发送验证码',
      emailRules:{
        email:[
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
          {
            pattern: '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
            trigger: ['blur', 'change'],
            message: '格式有误'
          }
        ],
        emailCode:[
            {
          required: true,
          message: "请输入用户名",
          trigger: "blur",
        }
        ],
      },

      //忘记密码
      forgetForm:[],
      forgetEmail:undefined,
      codeTimeoutDisable:false,
      forgetRules:{
        forgetEmail:[
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
          {
            pattern: '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
            trigger: ['blur', 'change'],
            message: '格式有误'
          }
        ]
      }
    }
  },
  methods: {
    //用户名密码
    loginShow(){
      this.emailShowMesaage=false;
      this.loginShowMessage=true;
      this.resetPasswordMesaage=false;
    },
    submitLogin() {
      this.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
          // alert(this.loginForm)
          sessionStorage.removeItem('AppointToken');
          loginByReader(this.loginForm).then((res) => {
            if (res.data.statusCode === 'C200') {
              // alert(res.headers['token'])
              //将token存入页面会话域中
              sessionStorage.setItem("AppointToken", res.headers['token'])
              // sessionStorage.setItem("showView",JSON.stringify(res.data.result.showView))
              ElMessage.success(res.data.message);
              // localStorage.setItem("admin", JSON.stringify(res.data.result.adminInformation));
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              //存储用户信息
              let PRdata = publicKsy(JSON.stringify(res.data.result))
              let userImage = res.data.result.userImage;
              localStorage.setItem("userInformationImg", userImage)
              res.data.result.userImage = null;
              //  解密
              // let PUdata=privateKey(PRdata)
              localStorage.setItem("userInformation", publicKsy(JSON.stringify(res.data.result)))
              // 前端页面不要写绝对地址，上线很容易出错，更改1
              window.location.href = 'http://' + window.location.host + '/#/home';
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
    registerShow(){
      this.$router.replace('/register')
    },
    //忘记密码
    // userResetPassword
    forgetShow(){
      this.emailShowMesaage=false;
      this.loginShowMessage=false;
      this.resetPasswordMesaage=true;
    },
    forgetPassword(){
      userResetPassword(this.forgetForm.forgetEmail).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });

    },
    //邮箱登录
    emailShow(){
      this.emailShowMesaage=true;
      this.loginShowMessage=false;
      this.resetPasswordMesaage=false
    },
    updateEmail() {
      let email = this.emailForm.email;
      emailSend(email).then((res) => {
        if (res.data.statusCode === 'C200') {
          // alert("发送成功")
          this.codeTimeoutDisable=true;
          this.codeTimeout();
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    codeTimeout(){
      if(!this.codeTimeoutDisable) return
      //邮箱倒计时实现
      //获取当前时间
      let content = 80;
      let clock = window.setInterval(() => {
        content--;
        this.totalTime = content+' 秒';
        if (content < 0) {
          window.clearInterval(clock)
          this.totalTime = '重新发送'
          this.codeTimeoutDisable = false
        }
      }, 1000)
    },
    emailLogin(){
      this.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
          // alert(this.loginForm)
          sessionStorage.removeItem('AppointToken');
          let para ={
            email:this.emailForm.email,
            code:this.emailForm.emailCode,
          }
          userEmailLogin(para).then((res) => {
            if (res.data.statusCode === 'C200') {
              // alert(res.headers['token'])
              //将token存入页面会话域中
              sessionStorage.setItem("AppointToken", res.headers['token'])
              // sessionStorage.setItem("showView",JSON.stringify(res.data.result.showView))
              ElMessage.success(res.data.message);
              // localStorage.setItem("admin", JSON.stringify(res.data.result.adminInformation));
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              //存储用户信息
              let PRdata = publicKsy(JSON.stringify(res.data.result))
              let userImage = res.data.result.userImage;
              localStorage.setItem("userInformationImg", userImage)
              res.data.result.userImage = null;
              //  解密
              // let PUdata=privateKey(PRdata)
              localStorage.setItem("userInformation", publicKsy(JSON.stringify(res.data.result)))
              // 前端页面不要写绝对地址，上线很容易出错，更改1
              window.location.href = 'http://' + window.location.host + '/#/home';
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

    //游客登录
    visitLogin(){
      this.$router.replace('/home')
    }

  },
  mounted() {
  }
}
</script>

<style scoped>
.wraptitle {
  margin-left: 140px;
  margin-top: 30px;
}

.login-wrap {
  width: 100%;
  height: 575px;
  background-image: url("../assets/library4.png");
  position: relative;
}

.login-card {
  width: 400px;
  height: 350px;
  background-color: rgba(255, 255, 255, 0.5);
  position: absolute;
  top: 130px;
  left: 70px;
  border-radius: 5%;
  border: #758a99 1px solid;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.8);
}

.login-content {
  margin-top: 70px;
  position: relative;
}

.login-main {
  position: absolute;
  top: -30px;
  right: 70px;
  /*font-size: 16px;*/
}

.userLoginName {
  margin-top: 20px;
  width: 350px;
  margin-bottom: 30px;
}

.login-button {
  position: absolute;
  left: 50px;
  top: 150px;
}
.registerShow{
  font-size: 14px;
  /*font-family: "楷体", "楷体_GB2312";*/
  margin-left: 120px;
  margin-top: 10px;
  float: left;
  margin-right: 10px;
}
.emailShowContent{
  font-size: 14px;
  /*font-family: "楷体", "楷体_GB2312";*/
  margin-left: 190px;
  margin-top: 10px;
}

/*邮箱登录*/
.email-content {
  margin-top: 70px;
  position: relative;
}

.email-main {
  position: absolute;
  top: -30px;
  right: 70px;
  /*font-size: 16px;*/
}

.email {
  margin-top: 20px;
  width: 350px;
  margin-bottom: 30px;
}
.loginShowContent{
  margin-top: 90px;
  font-size: 14px;
  margin-left: 150px;
}

/*忘记密码*/
.userForgetEmail{
  margin-top: 80px;
  margin-left: 10px;
}
.forgetShowContent{
  margin-top: 50px;
  font-size: 14px;
  margin-left: 150px;
}

/*游客登陆*/
.visitor{
  margin-left: 190px;
  margin-top: 40px;
}
</style>