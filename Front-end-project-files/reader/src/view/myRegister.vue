<template>
<section>
  <div class="registerWrap">
   <div class="registerCard">
     <div class="returnLoginPage" @click="returnLogin"><el-icon><ArrowLeftBold /></el-icon></div>
     <div class="registerTitle"><h1>用户注册</h1></div>
     <el-form :model="registerList" ref="vForm" :rules="rules" label-position="left" label-width="107px"
              size="default" style="margin-left: 150px;font-weight: bolder" @submit.prevent >
       <el-row>
         <el-col :span="10">
           <el-form-item label="登录名：" prop="userLoginName" class="required label-right-align" label-width="80px" style="margin-top: 10px;margin-left: -15px">
             <el-input v-model="registerList.userLoginName" type="text" clearable style="width: 140px" />
           </el-form-item>
         </el-col>
           <el-form-item label="姓名：" prop="userRealName" class="required label-right-align" label-width="70px" style="margin-top: 10px;">
             <el-input v-model="registerList.userRealName" type="text" clearable style="width: 140px"></el-input>
           </el-form-item>
       </el-row>
       <el-row>
         <el-col :span="10">
           <el-form-item label="密码：" prop="password" class="label-right-align" label-width="67px" style="margin-left:-3px;margin-top: 10px;">
             <el-input v-model="registerList.password" type="password" clearable style="width: 140px"></el-input>
           </el-form-item>
         </el-col>
         <el-form-item label="性别：" prop="gender" class="label-right-align" label-width="70px" style="margin-top: 10px;">
           <el-radio-group v-model="registerList.gender">
             <el-radio v-for="(item, index) in genderList" :key="index" :label="item.coding" style="{display: inline}">
               {{ item.value }}
             </el-radio>
           </el-radio-group>
         </el-form-item>
       </el-row>
       <el-row>
         <el-col :span="9">
           <el-form-item label="身份证号码：" prop="idCard" class="required label-right-align" label-width="110px" style="margin-left: -45px;margin-top: 10px;">
             <el-input v-model="registerList.idCard" type="text" clearable style="width: 200px"></el-input>
           </el-form-item>
         </el-col>
         <el-form-item label="生日：" prop="birthDay" class="label-right-align" label-width="68px" style="margin-left: 28px;margin-top: 10px;">
           <el-date-picker v-model="registerList.birthDay" type="date" class="full-width-input" format="YYYY-MM-DD"
                           value-format="YYYY-MM-DD" clearable style="width: 150px"></el-date-picker>
         </el-form-item>

       </el-row>
       <el-row>
         <el-col :span="9">
           <el-form-item label="电话号码：" prop="telephone" class="required label-right-align" label-width="92px" style="margin-left: -27px;margin-top: 10px;">
             <el-input v-model="registerList.telephone" type="text" clearable style="width: 170px"></el-input>
           </el-form-item>
         </el-col>
         <el-form-item label="邮箱：" prop="email" class="label-right-align" label-width="66px"
                       style="margin-left: 30px;margin-top: 10px;">
           <el-input v-model="registerList.email" type="text" clearable style="width: 220px"></el-input>
         </el-form-item>
       </el-row>
       <el-form-item label="地址：" prop="address" class="label-right-align" label-width="55px"
                     style="margin-left:10px;margin-top: 10px;">
         <el-input v-model="registerList.address" type="textarea" clearable style="width: 480px;"></el-input>
       </el-form-item>
       <el-button type="primary" @click="userRegister" style="margin-left: 200px;margin-top: 10px; width: 100px">注册</el-button>
     </el-form>

   </div>
  </div>
</section>
</template>

<script>
import {getUserGender, registerUser, sureLoginName, sureLoginNameTest} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      registerList:{
        userLoginName:undefined,
        password:undefined,
        userRealName:undefined,
        idCard:undefined,
        gender:undefined,
        telephone:undefined,
        address:undefined,
        email:undefined,
        birthDay:undefined,
      },
      sureMessage:undefined,
      rules: {
        userLoginName: [{
          required: true,
          message: '登录名不得为空',
          trigger: "blur",
        },
          {
            validator:this.loginNameSure,
            trigger: 'blur',
            message: this.sureMessage,
          }
        ],
        password: [{
          required: true,
          message: '密码不得为空',
          trigger: "blur",
        }],
        userName: [{
          required: true,
          message: '用户名不得为空',
          trigger: "blur",
        }],
        idCard: [{
          required: true,
          message: '用户身份证号不得为空',
          trigger: "blur",
        }, {
          //正则不对
          pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
          trigger: ['blur', 'change'],
          message: '身份证号码错误'
        }],
        gender: [{
          required: true,
          message: '性别不得为空',
        }, {
          //正则不对
          pattern: /^GM[0-9]{3}$/,
          trigger: ['blur', 'change'],
          message: '性别编码错误'
        }],
        birthday: [{
          required: true,
          message: '生日不可为空',
          trigger: "blur",
        }],
        telephone: [{
          required: true,
          message: '电话号码不可为空',
          trigger: "blur",
        }, {
          pattern: /^1[3456789]\d{9}$/,
          trigger: ['blur', 'change'],
          message: '格式有误'
        }],
        email: [{
          required: true,
          message: '电子邮件不得为空',
          trigger: "blur",
        } ,{
          //正则不对
          pattern: /^([a-z0-9_.-]+)@([\da-z.-]+)\.([a-z.]{2,6})$/,
          trigger: ['blur', 'change'],
          message: '电子邮件格式错误'
        }],
        verify: [{
          required: true,
          message: '字段值不可为空',
          trigger: "blur",
        }],
      },
      genderList:[
          {
            coding:'GM001',
            value:'男'
           },
        {
          coding:'GM002',
          value:'女'
        }
      ],
    }
  },
  methods:{
    //用户名是否存在
    loginNameSure(){
      let loginName = this.registerList.userLoginName;
      let config={
        headers: {
          'Content-Type': 'application/text'
        }
      }
      sureLoginName(loginName,config).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.sureMessage=res.data.message;
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //用户注册
    userRegister(){
      this.$refs['vForm'].validate(valid => {
        if (valid) {
          let params ={
            loginName: this.registerList.userLoginName,
            password: this.registerList.password,
            userName: this.registerList.userRealName,
            idCard: this.registerList.idCard,
            gender: this.registerList.gender,
            telephone: this.registerList.telephone,
            address: this.registerList.address,
            email: this.registerList.email,
            birthDay: this.registerList.birthDay,
          }
          registerUser(params).then((res) => {
            this.listLoading = false;
            if (res.data.statusCode === 'C200') {
              // this.sureMessage=res.data.message;
              ElMessage.success(res.data.message);
            } else {
              ElMessage.error(res.data.message);
            }
          });
        }
      })

    },
    //返回登录
    returnLogin(){
      this.$router.replace('/login');
    }
  },
  mounted() {
    // this.loginNameSure();
  }
}
</script>

<style scoped>
.registerWrap{
  width: 100%;
  height: 575px;
  /*border-radius: 20px;*/
  /*box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);*/
  background-image: url("../assets/library4.png");
  position: relative;
  float: left;
}
.registerCard{
  width: 800px;
  height: 500px;
  /*border: 1px solid black;*/
  border-radius: 20px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  background-color: rgb(239, 239, 239 ,0.7);
  margin-left: 240px;
  margin-top: 40px;
  position: relative;
  float: left;
}
.registerTitle{
  height: 50px;
  width: 100px;
  margin-left: 350px;
  margin-top: 30px;
  margin-bottom: 20px;
}
.returnLoginPage{
   margin-top: 25px;
   margin-left: 40px;
   float: left;
  font-size: 28px;
}
</style>