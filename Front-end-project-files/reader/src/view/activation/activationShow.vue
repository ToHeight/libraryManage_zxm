<template>
 <section>
     <div class="activateShow1" v-if="activateContent1">
       <div class="emoji">🧐</div>
       <p class="title">激活成功</p>
       <p class="text">请点击<el-button  link type="primary" @click="returnLogin">登录</el-button>,返回登陆界面！</p>
     </div>
     <div class="activateShow2" v-if="activateContent2">
       <div class="emoji">😭</div>
       <p class="title">激活失败!</p>
       <p class="text">原因:激活超时</p>
     </div>
 </section>
</template>

<script>
import {activateUser} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      code:undefined,
      activateContent1:false,
      activateContent2:false,
    }
  },
  methods:{
    //激活
    getActivate(){
      activateUser(this.code).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.activateContent1=true;
        } else {
          ElMessage.error(res.data.message);
          this.activateContent2=true;
        }
      });
    },
    //返回登录
    returnLogin(){
      this.$router.replace('/login');
    }
  },
  mounted() {
    let that = this;
    this.code = that.$route.params.code;
    this.getActivate();
  }
}
</script>

<style scoped>
html {
  font-size: 62.5%;
}
body {
  background-color: #fff;
  color: #000;
  font-family: helvetica, arial, sans-serif;
  font-size: 1.4em;
  line-height: 1.5;
}
.activateShow1 {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.activateShow2 {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.emoji {
  font-size: 9em;
  text-align: center;
}
.title {
  font-size: 3em;
  text-align: center;
  line-height: 0em;
  color: black;
  margin-top: 30px;
}
.text {
  text-align: center;
  font-size: 16px;
  margin-top: 30px;
}
</style>