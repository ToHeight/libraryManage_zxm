<template>
<section>
  <div class="forgetWrap">
     <div class="passwordChange">
       <div class="passwordCard">
       <el-form ref="ruleFormRef" :model="changeInfoList" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
         <el-form-item label="登录名:" prop="loginName">
           <el-input v-model.number="changeInfoList.loginName" style="width: 200px;margin-bottom: 10px;" />
         </el-form-item>
         <el-form-item label="新密码:" prop="password">
           <el-input v-model="changeInfoList.password" type="password" autocomplete="off" style="width: 250px;margin-bottom: 10px;" />
         </el-form-item>
         <el-form-item label="确认密码:" prop="checkPassword">
           <el-input v-model="changeInfoList.checkPassword" type="password" autocomplete="off" style="width: 250px;margin-bottom: 10px;" />
         </el-form-item>
         <el-form-item>
           <el-button type="primary" @click="changeInfo" style="margin-left: 40px">提交</el-button>
           <el-button @click="resetList">重置</el-button>
         </el-form-item>
       </el-form>
       </div>
     </div>
  </div>
</section>
</template>

<script>
import {passwordForget} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
 data(){
   return{
     rules:{
       loginName:[{
         required: true,
         message: "用户名不得为空",
         trigger: "blur",
       }],
       password:[
         {
           required: true,
           message: "密码不得为空",
           trigger: "blur",
         }],
       checkPassword:[
         {
           required: true,
           message: "确认密码不得为空",
           trigger: "blur",
         },
         {
         validator: this.checkPsd,
         message: "请输入一致的密码",
         trigger: 'blur'
       }]
     },
     changeInfoList:[],
     code:undefined,
   }
 },
  methods:{
   //确认密码
    checkPsd(){
      if(this.changeInfoList.checkPassword===this.changeInfoList.password){
         return true;
      }else {
        return false;
      }
    },
    //提交修改
    changeInfo(){
      let para={
        loginName:this.changeInfoList.loginName,
        password:this.changeInfoList.password,
        code:this.code,
      }
      passwordForget(para).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.$router.replace('/login');
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //重置表单
    resetList(){

    }
  },
  mounted() {
    let that = this;
    this.code = that.$route.params.code;
  }
}
</script>

<style scoped>
.forgetWrap{
  width: 100%;
  height: 577px;
  background-image: url("../../assets/forgetBack.png");
  float: left;
}
.passwordChange{
  width: 400px;
  height: 300px;
  border: 1px solid red;
  margin-left: 700px;
  margin-top: 150px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  border: #758a99 1px solid;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.8);
}
.passwordCard{
  margin-top: 40px;
  margin-left: -15px;
}
</style>