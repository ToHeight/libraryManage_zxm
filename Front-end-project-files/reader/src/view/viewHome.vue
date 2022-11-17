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
        <input class="select-first" v-model="input.bookName" placeholder="&nbsp;&nbsp;请输入图书信息"/>
<!--        <el-icon class="select-icon" ><Search /></el-icon>-->
        <el-button type="primary" @click="employeesdetails(input.bookName)" round>查询</el-button>
      </el-tab-pane>
    </el-tabs>
    </div>
      <div class="recommendBookHome" >
        <div class="homeRecommendTitle"><h2>图书推荐</h2></div>
        <div class="clear"></div>
        <div class="bookShow">
          <div v-infinite-scroll="loadMore"
               infinite-scroll-throttle-delay="500"
               infinite-scroll-disabled="busy"
               immediate-check="false"
               infinite-scroll-distance="50">
          <div class="box" v-for="book in recommendBookList" :key="book.bookId" @click="getBookInfo(book)">
            <div class="img-box" >
              <img :src="book.bookImage" alt="">
            </div>
            <div class="text-box">
              <div>
                <h2>{{book.bookName}}</h2>
                <p>{{book.typeName}}</p>
                <div class="bookStar">
                  <el-rate
                      v-model="book.bookStar"
                      disabled
                      show-score
                      text-color="#ff9900"
                      score-template="{value} 分"
                  />
                </div>
              </div>
            </div>
          </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import {loginByReader, recommendHome} from "@/api/zxmLibrary";
import {ElMessage, ElMessageBox} from "element-plus";
import {publicKsy} from "@/api/keyJSEncrypt";

export default {
  data() {
    return {
      pictureList: [
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
        {url: require("../assets/library3.png")},
      ],
      tabPosition: "left",
      handleOpen: '',
      handleClose: '',
      dataHeight: '450px',
      input: {
        bookName:undefined,
      },
      activeName: 'first',
      recommendBookList: [],
      page: 1,
      pageSize: 10,
      total: undefined,
      busy: false,
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },

      //登录
      dialogVisibleLogin:false,
      loginForm:{},
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
      userLoginName:undefined,
      password:undefined,
    }
  },
  methods: {
    getRecommendBook() {
      recommendHome(this.pageSize, this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.recommendBookList = res.data.result.list;
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //查看详情
    getBookInfo(book) {
      window.open('http://' + window.location.host + '/#/bookInfo/' + book.bookId, '_blank')
    },
    //图书查询
    employeesdetails(bookName){
      this.$router.push(
          {
            //添加需要传值到那个页面的路径
            path:'/book',
            //携带需要传递的参数
            query:{bookName:this.input.bookName}
          })
    },
    //无限下拉
    loadMore: function () {
      this.busy = true
      setTimeout(() => {
        // 当前页数如果小于总页数，则继续请求数据，如果大于总页数，则滚动加载停止
        if (this.pageSize < this.total) {
          //  这里是列表请求数据的接口,在这个接口中更新总页数
          this.pageSize=this.pageSize + 10;
          this.getRecommendBook()
        }
        this.busy = false
      }, 1000)
    },
     },
  mounted() {
    this.getRecommendBook();

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
.select{
  position: absolute;
  /*background-color: #fff;*/
  top: 50px;
  left: 320px;
  height: 200px;
  width: 650px;
  border-radius:15px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.5);
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
.select>p{
  margin-top: 20px;
  text-align: center;
  font-size: 30px;
}

/*图书推荐*/
.recommendBookHome{
  /* 相对定位 */
  position: relative;
  /* 弹性布局 横向排列 允许换行 */
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.recommendBookHome .box{
  position: relative;
  width: 210px;
  height: 330px;
  /* 溢出隐藏 */
  overflow: hidden;
  /* 动画过渡 */
  transition: 0.5s;
  /*float: left;*/
  float: left;
  margin-left: 33px;
  margin-bottom: 20px;
  border-radius: 10px;
}
/* 鼠标移入，盒子变大 */
.recommendBookHome .box:hover{
  transform: scale(1.25);
  box-shadow: 0 25px 40px rgba(0,0,0,0.5);
  z-index: 1;
}
.recommendBookHome .box .img-box{
  /* 绝对定位 */
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
/* 渐变遮罩（黑色——透明） 默认隐藏 */
.recommendBookHome .box .img-box::before{
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to top,#222,transparent);
  z-index: 1;
  opacity: 0;
  transition: 0.5s;
}
/* 鼠标移入，渐变遮罩显示 */
.recommendBookHome .box:hover .img-box::before{
  opacity: 1;
}
.recommendBookHome .box .img-box img{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  /* 保持原有尺寸比例，裁切长边 */
  object-fit: cover;
}
.recommendBookHome .box .text-box{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  padding: 20px;
  /* 弹性布局 元素位于容器的结尾 */
  display: flex;
  align-items: flex-end;
  color: #fff;
  z-index: 2;
}
.recommendBookHome .box .text-box h2{
  font-size: 20px;
  margin-bottom: 10px;
  /* 默认移出可视范围 */
  transform: translateY(200px);
  transition: 0.5s;
}
.recommendBookHome .box:hover .text-box h2{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.3s;
}
.recommendBookHome .box .text-box p{
  font-size: 13px;
  line-height: 20px;
  /* 默认移出可视范围 */
  transform: translateY(200px);
  transition: 0.5s;
}
.recommendBookHome .box:hover .text-box p{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.4s;
}
.recommendBookHome .box .bookStar{
  font-size: 13px;
  line-height: 20px;
  /* 默认移出可视范围 */
  transform: translateY(200px);
  transition: 0.5s;
}
.recommendBookHome .box:hover .bookStar{
  /* 鼠标移入，移入可视范围 */
  transform: translateY(0);
  /* 设置动画延迟时间 */
  transition-delay: 0.4s;
}
.recommendBookHome .bookStar{
  margin-bottom: 30px;
}
.homeRecommendTitle{
  /*float: left;*/
  margin-left: 550px;
  margin-bottom: 30px;
}
.clear{
  clear: both;
}
html,
body {
  margin: 0;
}




</style>