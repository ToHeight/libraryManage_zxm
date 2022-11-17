<template>
  <div class="layout">
    <el-container v-if="state.showMenu" class="container">
      <el-aside class="aside">
        <div class="head">
          <div>
            <img src="../public/logo.ico" alt="logo" style="width: 40px;height: 40px">
            <span style="font-size: 15px;font-weight: bolder">城南图书馆管理系统</span>
          </div>
        </div>
        <div class="line" />
        <el-menu
          background-color="#222832"
          text-color="#fff"
          :router="true"
           :default-openeds="state.defaultOpen"
           :default-active='state.currentPath'
        >
          <el-sub-menu index="1">
            <template #title>
              <span>首页</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/"><el-icon><Odometer /></el-icon>首页界面</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
           <el-sub-menu index="2">
            <template #title>
              <span>用户及管理员管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/userManage"><el-icon><Picture /></el-icon>用户信息</el-menu-item>
              <el-menu-item index="/adminManage"><el-icon><Picture /></el-icon>管理员信息</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <span>图书管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/bookManage"><el-icon><Menu/></el-icon>图书信息管理</el-menu-item>
              <el-menu-item index="/bookTagManage"><el-icon><Menu/></el-icon>图书书签管理</el-menu-item>
              <el-menu-item index="/bookMessageManage"><el-icon><Menu/></el-icon>图书留言管理</el-menu-item>
              <el-menu-item index="/bookScheduledManage"><el-icon><Goods/></el-icon>图书预定管理</el-menu-item>
              <el-menu-item index="/bookBorrowManage"><el-icon><Collection /></el-icon>图书借阅管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <span>活动管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/activityManage"><el-icon><Picture /></el-icon>活动信息</el-menu-item>
              <el-menu-item index="/applicationManage"><el-icon><Picture /></el-icon>报名信息</el-menu-item>

            </el-menu-item-group>


          </el-sub-menu>
          <el-sub-menu index="5">
            <template #title>
              <span>预约管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/floorManage"><el-icon><Picture /></el-icon>楼层信息</el-menu-item>
              <el-menu-item index="/appointmentManage"><el-icon><Picture /></el-icon>预约信息</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="6">
            <template #title>
              <span>权限信息</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/roleManage"><el-icon><Picture /></el-icon>权限管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="7">
            <template #title>
              <span>编码管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/codeManage"><el-icon><Picture /></el-icon>编码信息</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container class="content">
        <Header />
        <div class="main">
          <router-view />
        </div>
        <Footer />
      </el-container>
    </el-container>
    <el-container v-else class="container">
      <router-view />
    </el-container>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
import { localGet, pathMap } from '@/utils'

const noMenu = ['/login']
const router = useRouter()
const state = reactive({
  showMenu: true,
  defaultOpen: ['1', '2', '3', '4'],
  currentPath: '/',
})

router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    // 如果路径是 /login 则正常执行
    next()
  } else {
    // 如果不是 /login，判断是否有 token
 if (sessionStorage.getItem('AppointToken')===null) {
      // 如果没有，则跳至登录页面
     window.location.href = 'http://' + window.location.host + '/#/login'
      next({ path: '/login' })
    } else {
      // 否则继续执行
      next()
    }
  }
  state.showMenu = !noMenu.includes(to.path)
  state.currentPath = to.path
  document.title = pathMap[to.name]
})
</script>

<style scoped>
.layout {
  min-height: 100vh;
  background-color: #ffffff;
}
.container {
  height: 100vh;
}
.aside {
  width: 200px!important;
  background-color: #222832;
}
.head {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
}
.head > div {
  display: flex;
  align-items: center;
}

.head img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}
.head span {
  font-size: 20px;
  color: #ffffff;
}
.line {
  border-top: 1px solid hsla(0,0%,100%,.05);
  border-bottom: 1px solid rgba(0,0,0,.2);
}
.content {
  display: flex;
  flex-direction: column;
  max-height: 100vh;
  overflow: hidden;
}
.main {
  height: calc(100vh - 100px);
  overflow: auto;
  padding: 10px;
}
</style>

<style>
  body {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }
  .el-menu {
    border-right: none!important;
  }
  .el-submenu {
    border-top: 1px solid hsla(0, 0%, 100%, .05);
    border-bottom: 1px solid rgba(0, 0, 0, .2);
  }
  .el-submenu:first-child {
    border-top: none;
  }
  .el-submenu [class^="el-icon-"] {
    vertical-align: -1px!important;
  }
  a {
    color: #409eff;
    text-decoration: none;
  }
  .el-pagination {
    text-align: center;
    margin-top: 20px;
  }
  .el-popper__arrow {
    display: none;
  }
</style>