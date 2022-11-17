<template>
  <section>
    <div class="personInfoWrap">
      <div class="personInfoContent">
        <el-form :model="userList" ref="vForm" :rules="rules" label-position="left" label-width="107px"
                 size="default" @submit.prevent>
          <el-form-item label="头像上传：" prop="userImage" label-width="85px" style="margin-left: 25px"
                        class="label-right-align">
            <el-image style="width: 145px; height: 145px;margin-right: 5px" :src="userList.image" :fit="fit"/>
            <el-upload list-type="picture-card" :limit="1" class="avatar-uploader"
                       action="http://saleljw.5gzvip.91tunnel.com/library-system-reader/librarySystemReader/userInformationByReader/uploadAvatarByReader"
                       :show-file-list="uploadImg" :before-upload="beforeAvatarUpload"
                       :on-success="successAvatarUpload"
                       :data={userName:this.userList.userName}>
              <template #default>
                <el-icon>
                  <Plus/>
                </el-icon>
              </template>
            </el-upload>
          </el-form-item>
          <el-row>
            <el-col :span="8">
              <el-form-item label="姓名：" prop="userName" class="required label-right-align" label-width="70px"
                            style="margin-left: 40px">
                <el-input v-model="userList.userName" type="text" clearable style="width: 130px"></el-input>
              </el-form-item>
            </el-col>
            <el-form-item label="年龄：" prop="age" class="label-right-align" label-width="55px">
              <el-input v-model="userList.age" type="text" clearable style="width: 50px"></el-input>
            </el-form-item>
          </el-row>
          <el-form-item label="性别：" prop="genderList" class="label-right-align" label-width="60px"
                        style="margin-left: 50px">
            <el-radio-group v-model="userList.gender">
              <el-radio v-for="(item, index) in genderList" :key="index" :label="item.coding" style="{display: inline}">
                {{ item.value }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row>
            <el-col :span="9">
              <el-form-item label="身份证号码：" prop="userIdcard" class="required label-right-align">
                <el-input v-model="userList.userIdcard" type="text" clearable style="width: 190px"></el-input>
              </el-form-item>
            </el-col>
            <el-form-item label="生日：" prop="brithday" class="label-right-align" label-width="68px" style="margin-left: 15px">
              <el-date-picker v-model="userList.brithday" type="date" class="full-width-input" format="YYYY-MM-DD"
                              value-format="YYYY-MM-DD" clearable style="width: 150px"></el-date-picker>
            </el-form-item>
          </el-row>

          <el-form-item label="电话号码：" prop="telephone" class="required label-right-align" label-width="92px"
                        style="margin-left: 15px">
            <el-input v-model="userList.telephone" type="text" clearable style="width: 150px"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="9">
              <el-form-item label="邮箱：" prop="email" class="label-right-align" label-width="64px"
                            style="margin-left: 42px">
                <el-input v-model="userList.email" type="text" clearable style="width: 220px"></el-input>
              </el-form-item>
            </el-col>
            <el-form-item label="邮箱验证码：" prop="verify" class="required label-right-align" label-width="110px"
                          style="margin-left: 15px">
              <el-input v-model="verify" type="text" clearable style="width: 150px"></el-input>
              <el-button type="primary" @click="updateEmail()" style="margin-left: 5px" :disabled="codeTimeoutDisable">{{totalTime}}</el-button>

            </el-form-item>
          </el-row>
          <el-form-item label="地址：" prop="address" class="label-right-align" label-width="55px"
                        style="margin-left: 50px">
            <el-input v-model="userList.address" type="text" clearable style="width: 200px"></el-input>
          </el-form-item>
          <el-button type="primary" @click="userInfoUpdate" style="margin-left: 300px">确认修改</el-button>
        </el-form>
      </div>
    </div>
  </section>
</template>

<script>
import {emailSend, getUserGender, getUserInfo, updateUserInfo, uploadUserImg} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      codeTimeoutDisable:false,
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //个人信息
      userList: [],
      userName: undefined,
      age: undefined,
      brithday: undefined,
      address: undefined,
      email: undefined,
      image: undefined,
      telephone: undefined,
      userIdcard: undefined,
      rules: {
        userName: [{
          required: true,
          message: '字段值不可为空',
        }],
        userIdcard: [{
          required: true,
          message: '字段值不可为空',
        }, {
          pattern: /^\d+(\.\d+)?$/,
          trigger: ['blur', 'change'],
          message: '身份证号码错误'
        }],
        gender: [{
          required: true,
          trigger: ['blur'],
          message: '字段值不可为空',
        }],
        brithday: [{
          required: true,
          trigger: ['blur'],
          message: '字段值不可为空',
        }],
        telephone: [{
          required: true,
          trigger: ['blur'],
          message: '字段值不可为空',
        }, {
          pattern: /^[1][3-9][0-9]{9}$/,
          trigger: ['blur', 'change'],
          message: '格式有误'
        }],
        email: [{
          required: true,
          trigger: ['blur'],
          message: '字段值不可为空',
        }],
        verify: [{
          required: true,
          trigger: ['blur'],
          message: '字段值不可为空',
        }],
      },
      genderList: [{
        coding: undefined,
        value: undefined,
      }],
      totalTime:'发送验证码',
      updateUserInfo: false,
      verify: undefined,
      verifyShow: false,
      uploadImg: undefined,
      isJPG: undefined,

    }
  },
  methods: {
    //获取用户信息
    userInfoGet() {
      getUserInfo(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.userList = res.data.result;
          this.getAge();
          ElMessage.success(res.data.message);

        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //获取用户年龄
    getAge() {
      let date = new Date();
      let nowYear = date.getFullYear();
      let ageStrList = JSON.stringify(this.userList.brithday).split('-');
      this.userList.age = nowYear - parseInt(ageStrList[0].split('"')[1]);
      // alert(this.age);
    },
    //获取性别
    getGender() {
      getUserGender(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.genderList = res.data.result;
          // alert(11111);
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //邮箱验证
    updateEmail() {
      let email = this.userList.email;
      emailSend(email).then((res) => {
        if (res.data.statusCode === 'C200') {
          // alert("发送成功")
          this.codeTimeoutDisable=true;
          this.codeTimeout();
          ElMessage.success(res.data.message);
          this.verifyShow = true;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //修改信息
    userInfoUpdate() {
      let params = {
        userName: this.userList.userName,
        idCard: this.userList.userIdcard,
        gender: this.userList.gender,
        telephone: this.userList.telephone,
        address: this.userList.address,
        email: this.userList.email,
        emailCode: this.verify,
        image: this.userList.image,
        birthDay: this.userList.brithday,
      }
      updateUserInfo(params, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          localStorage.removeItem("AppointToken");
          sessionStorage.clear();
          window.location.href = 'http://' + window.location.host + '/#/login';
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //上传头像
    beforeAvatarUpload(file) {
      this.isJPG = file.type === 'image/jpeg';

      if (!this.isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      return this.isJPG;
    },
    successAvatarUpload(dataImg, uploadFile) {
      this.userList.image=dataImg;
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
    }
  },
  mounted() {
    this.userInfoGet();
    this.getGender();
  }
}
</script>

<style scoped>
.personInfoWrap {
  /*border: 1px black solid;*/
  border-radius: 20px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  width: 1000px;
  height: 550px;
  margin-left: 140px;
  margin-top: 20px;
  position: relative;
  float: left;
}

.personInfoContent {
  width: 750px;
  height: 500px;
  /*border: 1px red solid;*/
  margin-left: 130px;
  box-shadow: #758a99;
  margin-top: 30px;
}


</style>