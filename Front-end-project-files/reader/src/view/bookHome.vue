<template>
        <div class="book-tools">
          <div class="book-tools-wrap">
            <div class="book-tools-main">
             <el-form :inline="true" :model="input" class="demo-form-inline">
               <el-form-item label="图书名称:">
                 <el-input v-model="input.bookName" placeholder="请输入图书名称" clearable/>
               </el-form-item>
               <el-form-item label="图书作者:" style="margin-left: 25px">
                 <el-input v-model="input.bookAuthor" placeholder="请输入作者" clearable/>
               </el-form-item>
               <el-form-item>
                 <el-button class="book-tools-button" type="primary" @click="bookFind">查询</el-button>
               </el-form-item>
              </el-form>
            </div>
            <div class="book-tools-more">
              <el-collapse v-model="input" @change="handleChange" >
                <el-collapse-item title="更多筛选条件" name="1" >
                  <el-form :inline="true" :model="input" class="demo-form-inline">
                    <el-form-item label="图书种类:">
                      <el-select v-model="input.bookTypeId" placeholder="请选择图书种类" clearable>
                        <el-option v-for="item in bookTypelist" :key="item.coding" :label="item.value" :value="item.coding" />
                      </el-select>
                    </el-form-item>
                  <el-form-item label="作者国家:">
                    <el-select v-model="input.authorCountry" placeholder="请选择作者国家" clearable>
                      <el-option v-for="item in countryList" :key="item.coding" :label="item.value" :value="item.coding" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="图书语言:">
                    <el-select v-model="input.bookLanguageCoding" placeholder="请选择图书语言" clearable>
                      <el-option v-for="item in languageList" :key="item.coding" :label="item.value" :value="item.coding" />
                    </el-select>
                  </el-form-item>
                  </el-form>
                </el-collapse-item>
              </el-collapse>
            </div>
          </div>
          <div class="book-tools-more">
            <router-view name="star"></router-view>
          </div>
        </div>
         <!--图书展示-->
         <div class="book-show" v-for="item in bookData" :key="item.bookName">
           <div class="book">
             <div class="book-image">
               <img :src="item.bookImage" :alt="item.bookName">
             </div>
             <h1>{{item.bookName}}</h1>
             <div class="book-borrow">
               <el-tag class="ml-2" type="success" v-if="item.bookStatus==='可借阅'" size="small">可借阅</el-tag>
               <el-tag class="ml-2" type="danger" v-if="item.bookStatus==='不可借阅'" size="small">不可借阅</el-tag>
             </div>
             <h2>作者：{{item.bookAuthor}}</h2>
             <div class="book-info-wrap">
               <div class="book-info">
                 <p>{{item.bookInfo}}</p>
               </div>
             </div>
             <div class="book-show-tool">
               <el-button  color="#7895D2FF" plain class="book-show-tools1" size="small" @click="addBookShelf(item.bookId)">加入书架</el-button>
               <el-button  color="#7895D2FF" plain class="book-show-tools2" size="small">了解更多</el-button>
             </div>
             <div class="star-wrap">
               <div class="star"></div>
               <el-rate
                   v-model="item.bookStar"
                   disabled
                   show-score
                   text-color="#ff9900"
                   score-template="{value}">
               </el-rate>
             </div>
             <div class="book-more">
               <span>[图书语言]:{{item.bookLanguage}}</span><br/>
               <span>[作者国家]:{{item.bookCountry}}</span><br/>
               <span>[图书种类]:{{item.bookType}}</span><br/>
             </div>
           </div>
         </div>

      <!--           <el-input-number/>-->
      <el-pagination
          class="bookPage"
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

</template>
<script>
import { addToShelf, findBook, GetCountryList, GetLanguageList, GetTypeList} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      pageSize:10,
      bookData:[],
      bookId:undefined,
      bookName:undefined,
      bookAuthor:undefined,
      bookTypeId:undefined,
      bookLanguageCoding:undefined,
      authorCountry:undefined,
      bookStarMin:undefined,
      borrowNumber:undefined,
      bookInfo:undefined,
      total:0,
      bookStar:'',
      bookStatus:'',
      bookStatusF:'',
      bookImage:'',
      bookLanguage:'',
      bookCountry:'',
      page:1,
      // coding:'',
      bookTypelist:[{
          coding:'',
          value:'',
        }],
      countryList:[{
        coding:'',
        value:'',
      }],
      languageList:[{
        coding:'',
        value:'',
      }],
      listLoading:'',
      isDark:'',
      input:{
        bookName:undefined,
        bookAuthor:undefined,
        bookTypeId:undefined,
        bookLanguageCoding:undefined,
        authorCountry:undefined,
        pageSize:10,
      },
      configs:{
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      }
    }
  },
  methods:{
    handleSizeChange(val){
      this.pageSize=val;
      this.bookFind();
    },
    pageChange(val) {
      this.page = val;
      this.bookFind();
    },
    bookFind(){
      let para={
        page:this.page,
        pageSize:this.pageSize,
        bookName:this.input.bookName==='' ? undefined : this.input.bookName,
        bookAuthor:this.input.bookAuthor==='' ? undefined : this.input.bookAuthor,
        bookTypeId:this.input.bookTypeId==='' ? undefined : this.input.bookTypeId,
        bookLanguageCoding:this.input.bookLanguageCoding==='' ? undefined : this.input.bookLanguageCoding,
        authorCountry:this.input.authorCountry==='' ? undefined : this.input.authorCountry,
        bookStarMin:this.bookStarMin,
        borrowNumber:true,
      }
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
        findBook(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.bookData = res.data.result.list;
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
    },
    //图书种类
    bookTypeList(){
       let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
        GetTypeList(configs).then((res) => {
          if (res.data.statusCode === 'C200') {
            this.bookTypelist = res.data.result;
          } else {
            ElMessage.error(res.data.msgId);
          }
        })
    },
    //作者国家
    authorCountryList(){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      GetCountryList(configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.countryList = res.data.result;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //图书语言
    bookLanguageList(){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      GetLanguageList(configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.languageList = res.data.result;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //图书状态取反
    bookStatusTurn(){
      this.bookStatusF = this.bookStatus;
    },

    //书架添加
    addBookShelf(bookId){
      addToShelf(bookId,this.configs).then((res) => {
        this.listLoading = true;
        if (res.data.statusCode === 'C200') {
          // this.countryList = res.data.result;
          alert("添加成功")
          ElMessage.success(res.data.msgId);

        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    }

  },
  mounted() {
    this.bookFind();
    this.bookTypeList();
    this.authorCountryList();
    this.bookLanguageList();
    this.bookStatusTurn();
  }
}
</script>

<style scoped>

.book-tools{
  margin: 20px 100px;
}
.book-tools-wrap{
  margin-bottom: 10px;
}
.book-tools-button{
  width: 60px;
}
.book-tools-main{
  margin-left: 40px;
  margin-bottom: 10px;
}
.book-tools-more{
  margin-top: 5px;
  margin-left: 40px;
}

*{
  /* 初始化 */
  margin: 0;
  /*padding: 0;*/
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg, #507ea2, rgba(36, 57, 73, 0.99));
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
  margin-bottom: 25px;
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
  margin-top: 70px;
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
  width: 200px;
  height: 100px;
  transition: 0.2s;
}
.book:hover .book-more {
  top: -200px;
}
.book .book-more span{
  top: 20px;
  transition: 0.5s;
  font-size: 2px;
  z-index: 8;
}

.book-borrow{
  z-index: 7;
  position: absolute;
  top: 45px;
  /*font-size: 4px;*/
}

.bookPage{
  width: 200px;
  height: 40px;
  margin-left: 550px;
  /*z-index:8;*/
}



</style>