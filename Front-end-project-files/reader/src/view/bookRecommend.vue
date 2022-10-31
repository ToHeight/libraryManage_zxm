<template>
  <section>
    <div class="goodBookTitile">
      <h1>本期好书推荐</h1>
    </div>
    <template v-for="(item,index) in bookRecommendList" :key="index">
      <div class="goodBook-Type" v-for="typeItem in item.typeList" :key="typeItem.coding">
        <span>{{typeItem.value}}</span>
      </div>
      <div class="goodBookShow">
        <div class="book-show" v-for="bookItem in item.bookList" :key="bookItem.bookName">
          <div class="book" >
            <div class="book-image">
              <img :src="bookItem.bookImage" :alt="bookItem.bookName">
            </div>
            <div class="book-borrow1">
              <el-icon :disabled="bookItem.bookStatus"><SuccessFilled /></el-icon>
              <el-icon :disabled="bookItem.bookStatusF"><CircleCloseFilled /></el-icon>
            </div>
            <h1>{{bookItem.bookName}}</h1>
            <h2>作者：{{bookItem.bookAuthor}}</h2>
            <div class="book-info-wrap">
              <div class="book-info">
                <p>{{bookItem.bookInfo}}</p>
              </div>
            </div>
            <div class="book-show-tool">
              <el-button  plain class="book-show-tools1">了解更多</el-button>
              <el-button  plain class="book-show-tools2">加入书架</el-button>
            </div>
            <div class="star-wrap">
              <div class="star"></div>
              <el-rate
                  v-model="bookItem.bookStar"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}">
              </el-rate>
            </div>
            <div class="book-more">
              [图书语言]:{{bookItem.bookLanguage}}<br>
              [作者国家]:{{bookItem.bookCountry}}<br>
              [图书种类]:{{bookItem.bookType}}<br>
            </div>
          </div>
        </div>
      </div>
    </template>
  </section>
</template>

<script>
import {findBook, GetTypeList} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      recommendBookData:{
        bookName:undefined,
        bookAuthor:undefined,
        bookTypeId:undefined,
        bookLanguageCoding:undefined,
        authorCountry:undefined,
        bookStarMin:undefined,
        borrowNumber:undefined,
        bookInfo:undefined,
        bookStar:'',
        bookStatus:'',
        bookImage:'',
      },
      page:1,
      pageSize:'5',
      recommendBooks:'true',
      bookRecommendList:[],
      bookTypelist:[{
        coding:'',
        value:'',
      }],
    }
  },
  methods:{
    findRecommendByTypeId(){
      //请求获取推荐图书  value: T, index: number, array: T[]
     for (let i=0;i<this.bookTypelist.length;i++){
       let params={
         page:1,
         pageSize:5,
         bookTypeId:this.bookTypelist[i].coding,
         recommendBooks:'true'
       }
       let configs={
         headers:{
           token: sessionStorage.getItem('AppointToken')
         }
       };
       //请求
       findBook(params,configs).then((res)=>{
         if (res.data.statusCode === 'C200') {
           if(res.data.result.list.length!=0){
             //当前存在五行图书，记录信息
             let result={
               typeList:this.bookTypelist[i],
               bookList:res.data.result.list
             }
             // this.bookRecommendList.
             this.bookRecommendList.push(result);
             console.log(JSON.stringify(this.bookRecommendList))
           }
         } else {
           ElMessage.error(res.data.msgId);
         }
       })
     }
    },
    //图书种类
    bookTypeList(){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      //记录当前的type
      GetTypeList(configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookTypelist = res.data.result;
          this.findRecommendByTypeId()
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    },
  },
  mounted() {
     this.bookTypeList();
  }
}
</script>

<style scoped>
.goodBookTitile{
  margin-left: 550px;
  margin-top: 7px;
  margin-bottom: 7px;
}
.goodBook-Type{
  width: 100%;
  height: 30px;
  background-color: #758a99;
  margin-top: 10px;
  margin-top: 20px;
}
.goodBookShow{
  height: 300px;
  width: 100%;
}

/*图书*/
*{
  /* 初始化 */
  margin: 0;
  padding: 0;
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg,#517fa4,#243949);
}
.book-show{
  margin-left: 73px;
  position: relative;

}
.book{
  /* 相对定位 */
  position: relative;
  width: 200px;
  height: 330px;
  margin-left: 25px;
  margin-bottom: 60px;
  background-color: #758a99;
  border-radius: 20px;
  /* 溢出隐藏 */
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  /* 阴影 */
  box-shadow: 0 0 30px rgba(0,0,0,0.5);
  color: #fff;
  float: left;
}
/* 默认大图 */
.book .book-image{
  /* 绝对定位 */
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  border-radius: 0%;
  overflow: hidden;
  /* 动画过渡 */
  transition: 0.5s;
  z-index: 8;
}
/* 鼠标移入变小图 */
.book:hover .book-image{
  top: 30px;
  width: 0px;
  height: 0px;
  border-radius: 50%;
  box-shadow: 0 0 20px rgba(0,0,0,0.8);
}
/* 黑色到透明的渐变背景,可以更好的看清楚名字 */
.book .book-image::before{
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom,transparent 50%,#222);
}
/* 鼠标移入背景消失 */
.book:hover .book-image::before{
  display: none;
}
.book .book-image img{
  width: 100%;
  height: 100%;
  /* 保持原有尺寸比例，裁切长边 */
  object-fit: cover;
}
.book h1{
  position: absolute;
  top: 225px;
  transition: 0.5s;
  font-size: 16px;
  z-index: 8;
}
.book:hover h1{
  top: 20px;
}
.book h2{
  margin-top: 40px;
  width: 80%;
  border-bottom: 1px solid rgba(255,255,255,0.3);
  font-size: 12px;
  text-align: center;
  margin-bottom: 10px;
  padding-bottom: 20px;
}
.book-info-wrap{
  width: 110%;
  margin: 0 auto;
  position: relative;
  overflow: hidden;
  height: 165px;
  z-index: 7;
  margin-bottom: 10px;
}
.book .book-info-wrap .book-info{
  width: 99%;
  text-indent: 30px;
  font-size: 13px;
  /*text-align: center;*/
  /*overflow: auto;*/
  position: absolute;
  left: -1px;
  top: 5px;
  right: 1px;
  bottom: 0;
  overflow-x: hidden;
  overflow-y: scroll;
  margin-left: 1px;
  margin-right: 2px;

}
.book-info p{
  margin-left: 10px;
  margin-right: 10px;
}
.book-show-tool{
  width: 100%;
  height: 30px;
  text-align: center;
}
book-show-tools1{
  font-size: 14px;
  color: rgba(255,255,255,0.8);
  border: 1px solid rgba(255,255,255,0.5);
  padding: 8px 32px;
  border-radius: 8px;
  margin-bottom: 3px;
  background-color:transparent;
}
book-show-tools2{
  position: absolute;
  font-size: 14px;
  color: rgba(255,255,255,0.8);
  border: 1px solid rgba(255,255,255,0.5);
  padding: 8px 32px;
  border-radius: 8px;
  margin-bottom: 3px;
  background-color: rgba(255, 255, 255, 0.1);
  float: left;
}
.book-show-tools1:hover{
  color: #fff;
  background-color: rgba(255, 255, 255, 0.2);
}
.book-show-tools2:hover{
  color: #fff;
  background-color: rgba(255, 255, 255, 0.2);
}
.book-more{
  position: absolute;
  top: 260px;
  color: #ffffff;
  font-size: 4px;
  z-index: 8;
  left:10px;
  transition-delay:10s;
}
.book:hover .book-more{
  display: none;
}
.bookBorrowYes{
  color: #409EFF;
  z-index: 7;
  height: 30px;
  width: 30px;
}
.bookBorrowNo{
  color: #409EFF;
  z-index: 7;
  height: 30px;
  width: 30px;
}
</style>