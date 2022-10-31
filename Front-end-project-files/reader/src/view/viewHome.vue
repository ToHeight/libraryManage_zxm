<template>
  <section>
    <div class="wrap">
  <div class="picture-wrap">
    <el-carousel indicator-position="outside" class="picture" :height="dataHeight">
      <el-carousel-item v-for="item in pictureList" :key="item">
        <img :src="item.url" alt=""/>
      </el-carousel-item>
    </el-carousel>
  </div>
  <div class="select">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="书名查询" name="first">
        <input class="select-first" placeholder="&nbsp;&nbsp;请输入图书信息"/>
        <el-icon class="select-icon"><Search /></el-icon>
        <el-button type="primary" round>查询</el-button>
      </el-tab-pane>
      <el-tab-pane label="用户查询" name="second">
        <input class="select-first" placeholder="&nbsp;&nbsp;请输入用户信息"/>
        <el-icon class="select-icon"><Search /></el-icon>
        <el-button type="primary" round>查询</el-button>
      </el-tab-pane>
      <el-tab-pane label="活动查询" name="third">
        <input class="select-first" placeholder="&nbsp;&nbsp;请输入活动名称"/>
        <el-icon class="select-icon"><Search /></el-icon>
        <el-button type="primary" round>查询</el-button>
      </el-tab-pane>
      <el-tab-pane label="预约查询" name="fourth">
        <input class="select-first" placeholder="&nbsp;&nbsp;请输入用户名称"/>
        <el-icon class="select-icon"><Search /></el-icon>
        <el-button type="primary" round>查询</el-button>
      </el-tab-pane>
    </el-tabs>
<!--      <p>图书查询</p>-->
<!--      <input class="select-input">-->
<!--      <el-icon class="select-button" onclick="selectBook"><Search /></el-icon>-->
    </div>
  <div class="show">
      <div class="show-menu">
          <el-menu
              default-active="1"
              class="el-menu-vertical-demo"
              background-color="#545c64"
              text-color="#fff"
          >
            <el-sub-menu>
              <template #title>
                <el-icon><Notebook /></el-icon>
                <span>关于图书</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/home/content1">好书推荐</el-menu-item>
                <el-menu-item index="/home/content2">新书速递</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item index="2">
              <el-icon><MagicStick /></el-icon>
              <template #title>开展活动</template>
            </el-menu-item>
            <el-menu-item index="3" >
              <el-icon><document /></el-icon>
              <template #title>读者留言</template>
            </el-menu-item>
            <el-sub-menu index="4">
              <template #title>
                <el-icon><Notebook /></el-icon>
                <span>关于本馆</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/home/content3">空间分布</el-menu-item>
                <el-menu-item index="/home/content4">馆内概况</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
      </div>
      <div class="show-content" v-show="$route.meta.showTag">
        <router-view ></router-view>
      </div>
    </div>
    </div>
  </section>
</template>

<script>
export default {
  data(){
    return{
      pictureList:[
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
      ],
      tabPosition:"left",
      handleOpen:'',
      handleClose:'',
      dataHeight:'450px',
      input:{
        book_name: '',
      },
      activeName:'first',
    }
  },
  methods:{

  }
}
</script>

<style scoped>
.wrap{
  position: relative;
}
.picture-wrap{
  width: 100%;
  height: 500px;
  /*background-color: #e8fafa;*/
  /*margin-bottom: 2px;*/
  margin-top: 6px;
}
.picture{
  width: 100%;
  height: 600px;
  margin: 0px auto;
  -webkit-filter: blur(4px);
  filter: blur(4px);
}
.show{
  width: 100%;
  height: 800px;
  position: relative;
  margin: auto;
}
.show-menu{
  width: 200px;
  margin-left: 0px;
  height: 400px;
  margin-top: -40px;
  z-index: 3;
}
.show-content{
  height: 450px;
  width: 1062px;
  position: absolute;
  left: 200px;
  top: 0px;
  background: cyan;
  z-index:10;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height:450px;
}
.select{
  position: absolute;
  /*background-color: #fff;*/
  top: 50px;
  left: 320px;
  height: 200px;
  width: 650px;
  border-radius:15px;
  box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(56, 57, 59, 0.4);
  /*background-color: #545c64;*/
  z-index:992;
  color: #7895d2;
  /*background-image: linear-gradient(to bottom right, rgba(97, 144, 232), #A7BFE8);*/
  /*background-repeat: no-repeat;*/
  background:linear-gradient(to bottom, rgba(97,144,232,0.4), rgba(167,191,232,0.4));

}
.demo-tabs{
  padding: 10px 15px;
  font-size: large;
  /*text-align: center;*/
  color: black;
}
.select-first{
  width: 500px;
  height: 40px;
  margin: 25px 10px;
  color: black;
  border-radius:20px;
}
.select-icon{
  position: absolute;
  top:26px;
  left:475px;
  width: 40px;
  height: 40px;
  color: gray;
}
::v-deep .el-tabs__content {
  overflow: visible;
}
::v-deep .el-tabs__item {
  color: white;
}
::v-deep .el-tabs__item.is-active {
  color: #15cbf3;
}
::v-deep .el-icon-arrow-left {
  color: white;
}
::v-deep .el-icon-arrow-right {
  color: white;
}
::v-deep .el-tabs__nav-wrap::after {
  height: 0;
}
::v-deep .el-tabs__active-bar {
  background-color: #15cbf3;
}




/*.select>p{*/
/*  margin-top: 20px;*/
/*  text-align: center;*/
/*  font-size: 30px;*/
/*}*/
/*.select-input{*/
/*  margin-left: 43px;*/
/*  margin-top: 50px;*/
/*  height: 50px;*/
/*  width: 340px;*/
/*  border-radius:40px;*/
/*}*/
/*.select-button{*/
/*  position: absolute;*/
/*  left: 340px;*/
/*  top: 115px;*/
/*  height: 40px;*/
/*  width: 40px;*/
/*}*/





</style>