
<template>
   <section>
<!--  最终界面   -->
     <div class="bookShelfWrap" v-if="loadingShow&&bookShelfData.length!==0">
       <div class="container" v-for="item in bookShelfData" :key="item.bookName" @selection-change="selsChange">
         <div class="card" >
           <div class="img">
             <img :src="item.bookImage" :alt="item.bookName">
           </div>
           <div class="content">
             <div class="title" >
               <span @click="getBookInfo(item)">{{ item.bookName }}</span>&nbsp;&nbsp;<span class="author">作者:{{item.bookAuthor}}</span>
               <div class="status">
                 <el-tag class="ml-2" type="success" v-if="item.bookStatus === '可借阅'" size="small">可借阅</el-tag>
                 <el-tag class="ml-2" type="danger" v-if="item.bookStatus === '不可借阅'" size="small">不可借阅</el-tag>
               </div>
               <div class="tags">
                 <div class="tagInfo">
                   <el-tag v-for="tag in item.bookTags" :key="tag" class="mx-1" closable :disable-transitions="false" @close="deleteTag(tag,item.bookId,item.bookTags);">
                     {{ tag.tagValue }}
                   </el-tag>
                   <el-input v-if="addTagShow===item.bookId"
                             v-model="tagInput.tagValue"
                             @keyup.enter="this.addTags(item);"
                             style="width: 50px"
                             size="small"
                             @blur="this.addTagShow=!''?'':item.bookId"
                   />
                   <el-button v-else class="button-new-tag ml-1" size="small" @click="addTagPage(item)">
                     + 新标签
                   </el-button>
                 </div>
               </div>
             </div>
             <div class="text-wrap">
               <div class="text">{{item.bookInfo}}</div>
             </div>
             <div class="more">
               <span>[图书语言]:{{item.bookLanguage}}</span><br/>
               <span>[作者国家]:{{item.bookCountry}}</span><br/>
               <span>[图书种类]:{{item.bookType}}</span><br/>
             </div>
             <div class="tools">
               <el-button type="primary" plain class="button1" :disabled="item.bookStatus==='不可借阅'" @click="borrowBook(item)">借阅图书</el-button>
               <el-button type="danger" class="button2" plain  @click="deleteBookShelf(item)">删除</el-button>
             </div>
           </div>
         </div>
       </div>
       <!--     分页-->
       <div class="bookShelfPage">
         <el-pagination
             v-model:currentPage="page"
             v-model:page-size="pageSize"
             :page-sizes="[10, 20, 30]"
             small="false"
             @current-change="pageChange"
             background="false"
             layout="sizes, prev, pager, next"
             :total="total"
             @size-change="handleSizeChange"
         />
       </div>
     </div>
     <div class="bookShelfNull" v-if="loadingShow&&bookShelfData.length===0">
       <div class="emoji">📚</div>
       <p class="Nulltitle">书架暂无图书</p>
       <p class="Nulltext">请点击<el-button  link type="primary" @click="returnBook">图书大全</el-button>,多多添加图书！</p>
     </div>
     <!--  加载界面-->
     <div class="loading" v-if="!loadingShow">
       <!-- 【--x是自定义属性，在后面样式中会用到】 -->
       <div style="--x:0"></div>
       <div style="--x:1"></div>
       <div style="--x:2"></div>
       <div style="--x:3"></div>
       <div style="--x:4"></div>
       <div style="--x:5"></div>
     </div>
   </section>
</template>

<script>
import {addBookTag, bookBorrow, deleteBookTag, deleteFromBookShelf, findBookShelf} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      addTagShow:'',
      bookShelfData:[],
      bookId:undefined,
      bookName:undefined,
      bookAuthor:undefined,
      bookTypeId:undefined,
      bookLanuage:undefined,
      bookCountry:undefined,
      bookStarMin:undefined,
      borrowNumber:undefined,
      bookInfo:undefined,
      bookImage:undefined,
      total:0,
      page:1,
      pageSize:10,
      bookTags:[],
      tagId:undefined,
      tagValue:undefined,
      tapId:undefined,
      ids:[],
      testId:'test',
      dialogVisible:'',
      configs:{
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      },
      addTagInfo:[],
      tagNull:[
        {
          tagId:0,
          tagValue:'无'
        }
      ],
      tagInput:[],
      value:undefined,
      loadingShow:false,
      shelfId:undefined,
    }
  },
  methods:{
    //书架查询
     getBookShelf(){
       let para={
         page:this.page,
         pageSize:this.pageSize,
         bookName: this.bookName,
         authorName:this.bookAuthor,
         bookType:this.bookType,
       }
       findBookShelf(para,this.configs).then((res) => {
         this.listLoading = false;
         if (res.data.statusCode === 'C200') {
           this.total = res.data.result.total;
           this.bookShelfData = res.data.result.list;
           this.loadingShow = true;
           // alert(this.bookTags);
         } else {
           ElMessage.error(res.data.message);
         }
       });
     },
    //删除
    deleteBookShelf(item){
      let ids = [item.bookId];
      let bookShelf = item.bookId;
      deleteFromBookShelf(ids,this.configs).then((res) =>{
        this.listLoading = true;
        if (res.data.statusCode === 'C200') {
          //懒加载删除图书
          let a=0;
          for (let i = 0; i < this.bookShelfData.length; i++) {
            if(this.bookShelfData[i].bookId===bookShelf){
              a=i;
            }
          }
          this.bookShelfData.splice(a, 1);
          ElMessage.success(res.data.message);
          // this.getBookShelf();
        } else{
          ElMessage.error(res.data.message);
        }
      })
    },
    //借阅图书
    borrowBook(item){
      // let bookId = item.bookId;
      bookBorrow(item.bookId,item.shelfId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          location.reload();
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //图书详情
    getBookInfo(item){
      window.open('http://' + window.location.host + '/#/bookInfo/'+item.bookId,'_blank')
    },
    //分页
    handleSizeChange(val){
      this.pageSize=val;
      this.getBookShelf();
    },
    pageChange(val) {
      this.page = val;
      this.getBookShelf();
    },
    //弹窗
    addTagPage(item) {
      this.addTagInfo={};
      this.addTagInfo=item;
      this.addTagShow = item.bookId;
    },
    //添加图书标签
    addTags(row){
      this.value = encodeURI(encodeURI(this.tagInput.tagValue));
      addBookTag(this.addTagInfo.bookId,this.value,this.configs).then((res) =>{
        if (res.data.statusCode === 'C200') {
          // this.getBookShelf();
          let tagValue={
            tagValue:this.tagInput.tagValue,
            tagId:res.data.result
          }
          row.bookTags.push(tagValue);
          this.tagInput.tagValue='';
          ElMessage.success(res.data.message);
        } else{
          ElMessage.error(res.data.message);
        }
      })

    },
    //标签删除
    deleteTag(tag,bookId,tagsList){
       let tagId = tag.tagId;
      deleteBookTag(tagId,bookId,this.configs).then((res) =>{
        if (res.data.statusCode === 'C200') {
          //懒加载删除标签
          let a=0;
          for (let i = 0; i < tagsList.length; i++) {
            if(tagsList[i].tagId===tagId){
              a=i;
            }
          }
          tagsList.splice(a, 1);
          ElMessage.success(res.data.message);
        } else{
          ElMessage.error(res.data.message);
        }
      })

    },
    returnBook(){
      this.$router.replace('/book');
    }
  },
  mounted() {
    this.getBookShelf();
  }
}
</script>

<style scoped>
/*.bookShelf-wrap{*/
/*  background-color: #f1cf69;*/
/*}*/
*{
  /* 初始化 */
  margin: 0;
  /*padding: 0;*/
  box-sizing: border-box;
}
body{
  height: 100vh;
  /* 弹性布局 居中显示 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #efefef;
}
.container{
  margin-top: 20px;
  margin-left: 20px;
  width: 1000px;
  max-width: 600px;
  height: 250px;
  background-color: #fff;
  border-radius: 25px;
  box-shadow: 0 10px 50px rgba(0,0,0,0.3);
  position: relative;
  float: left;
  margin-left: 20px;
}
.card{
  /* 弹性布局 垂直居中 */
  display: flex;
  /*margin-top: 20px;*/
  align-items: center;
  width: 100%;
  height: 350px;
  padding-left: 30px;
  /* 绝对定位 */
  position: absolute;
  /* 溢出隐藏 */
  overflow: hidden;
  top: -50px;
}
.card .img{
  width: 160px;
  height: 200px;
  border-radius: 20px;
  overflow: hidden;
  /* 防止被挤压 */
  flex-shrink: 0;
  box-shadow: 0 10px 50px rgba(0,0,0,0.2);
}
.card .img img{
  width: 100%;
  height: 100%;
  /* 图片默认隐藏 */
  /*opacity: 0;*/
  /* 过渡效果 */
  transition: 0.6s;
}
.card .content{
  flex: 1;
  padding: 0 30px 0 35px;
  position: relative;
  /* 默认偏右一点 */
  /*left: 3px;*/
  /* 文本内容默认隐藏 */
  /*opacity: 0;*/
  /* 过渡效果 */
  transition: 0.6s;
}
.card .title{
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 20px;
  /*float: left;*/
}
.card .text-wrap{
  height: 100px;
  width: 350px;
  overflow: hidden;
  margin-top: -10px;
}
.card .text-wrap .text{
  font-size: 12px;
  color: #555;
  text-align: justify;
  margin-bottom: 25px;
  overflow: scroll;
  height: 117px;
  width: 367px;
}
.button1{
  padding: 10px 17px;
  /*background-color: #000;*/
  /*color: #fff;*/
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 10px rgba(0,0,0,0.2);
  float: right;
  margin-top: 10px;
}
.button2{
  padding: 10px 17px;
  /*background-color: #5b84b7;*/
  /*color: #fff;*/
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 10px rgba(0,0,0,0.2);
  float: right;
  margin-top: 10px;
  margin-right: 10px;
}
.more{
  font-size: 8px;
  margin-top: 10px;
  margin-left: -10px;
  float: left;
}
.author{
  font-size: 10px;
  font-family: "楷体", "楷体_GB2312";
}
.status{
  float: right;
}
.tags{
  font-family: "楷体", "楷体_GB2312";
  display: block;
}
.tagInfo{
  margin-top: 3px;
  display:inline-block;
}
.addTags{
  color: rgba(126, 146, 161, 0.91);
  display:inline-block;
  /*position: absolute;*/
  top: 5px;
}
.tagAddInput{
  margin-top: 20px;
}
.bookShelfPage{
  float: right;
  margin-top: 15px;
  margin-right: 500px;
  margin-bottom: 15px;
}
.tagInfo{
  margin-right: 3px;
}
.mx-1{
  margin-right: 3px;
}
.dialog-footer{
  width: 200px;
}
/*加载*/

*{
  /* 初始化 取消页面内外边距 */
  margin: 0;
  padding: 0;
  /* 这个是告诉浏览器：你想要设置的边框喝内边距的值是包含在总宽高内的 */
  box-sizing: border-box;
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平、垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #eef1f5;
}
.loading{
  display: flex;
  /* 水平显示 */
  flex-direction: row;
  margin-left: 400px;
  margin-top: 100px;
}
.loading div{
  position: relative;
  width: 40px;
  height: 200px;
  /* 渐变背景 */
  background: linear-gradient(to bottom,rgba(0,0,0,0.05),#eef1f5);
  margin: 20px;
  border-radius: 20px;
  border: 2px solid #eef1f5;
  /* 阴影 */
  box-shadow: 15px 15px 20px rgba(0,0,0,0.1),
  -15px -15px 20px #fff,
  inset -5px -5px 5px rgba(255,255,255,0.5),
  inset 5px 5px 5px rgba(0,0,0,0.05);
  /* 溢出隐藏 */
  overflow: hidden;
}
.loading div::before{
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  box-shadow: inset -5px -5px 5px rgba(0,0,0,0.1),
  0 420px 0 400px lightskyblue;
  /* 执行动画：动画名称 时长 加速后减速 无限次播放 */
  animation: animate 2s ease-in-out infinite;
  /* 动画延迟 通过var函数获取自定义属性--x，再由calc函数计算得出每个元素的动画延迟时间 */
  animation-delay: calc(var(--x) * -0.3s);
  /* 初始化时先向上移动160px */
  transform: translateY(160px);
}
/* 定义动画 */
@keyframes animate{
  0%{
    transform: translateY(160px);
    /* hue-rotate是颜色滤镜，可以设置不同的度数来改变颜色 */
    filter: hue-rotate(0deg);
  }
  50%{
    transform: translateY(0px);
    filter: hue-rotate(180deg);
  }
  100%{
    transform: translateY(160px);
    filter: hue-rotate(360deg);
  }
}
.bookShelfNull {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.emoji {
  font-size: 9em;
  text-align: center;
}
.Nulltitle {
  font-size: 2em;
  text-align: center;
  line-height: 0em;
  color: black;
  margin-top: 30px;
}
.Nulltext {
  text-align: center;
  font-size: 18px;
  margin-top: 30px;
}

</style>