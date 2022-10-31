<template>
  <div class="header-wrap">
    <el-menu :default-active="activeIndex2" active-text-color="#ffd04b" background-color="#545c64"
             class="el-menu-demo" mode="horizontal" text-color="#fff" @select="handleSelect" :router="true">
      <el-menu-item>
        <img alt="logo" class="logo-image" src="../assets/logo.png">
      </el-menu-item>
      <el-menu-item>
        <h2 class="title">城北图书馆</h2>
      </el-menu-item>
      <el-menu-item index="/home" ><h3>首页</h3></el-menu-item>
      <el-sub-menu index="2">
        <template #title><h3>藏书馆</h3></template>
        <el-menu-item index="/bookRecommend"><h4>推荐好书</h4></el-menu-item>
        <el-menu-item index="/book"><h4>图书大全</h4></el-menu-item>
        <el-menu-item index="2-3"><h4>图书预定</h4></el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/chooseSeat"><h3>入馆预约</h3></el-menu-item>
      <el-menu-item index="/bookShelf"><h3>个人书架</h3></el-menu-item>
      <el-menu-item index="5"><h3>读者论坛</h3></el-menu-item>
      <el-menu-item index="6"><h3>活动中心</h3></el-menu-item>
      <!-- 消息 -->
      <div class="btn-bell">
        <!--            <router-link to="/currentAppointment">-->
        <i class="el-icon-bell"></i>
        <!--            </router-link>-->
      </div>
      <div class="user-image">
        <el-avatar :size="50" :src="circleUrl" @click="openUser"/>
      </div>
      <el-sub-menu class="user" index="7">
        <template #title><h3>个人中心</h3></template>
        <el-menu-item index="7-1"><h4>个人信息</h4></el-menu-item>
        <el-menu-item index="7-2"><h4>预约信息</h4></el-menu-item>
        <el-menu-item index="/bookBorrow"><h4>借阅信息</h4></el-menu-item>
        <el-menu-item index="7-4"><h4>退出登录</h4></el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex2:'',
      handleSelect:'',
      circleUrl:'',
    }
  },
  methods: {
    openUser(){

    },
    home(){
      this.$router.push('/home')
    }
  }
}
</script>

<style scoped>
.header-wrap {
  position: relative;
}

.logo-image {
  height: 50px;
  width: 50px;
  margin-top: 3px;
  margin-left: 20px;
  margin-right: 10px;
}

.title {
  margin-right: 80px;
}

.user-image {
  position: absolute;
  right: 180px;
  margin-top: 3px;
}

.user {
  position: absolute;
  right: 50px;
}
</style>
