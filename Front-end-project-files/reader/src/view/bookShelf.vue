
<template>
<!--  <section></section>-->
<!--  <div class="bookShelf-wrap">-->
<!--     <div class="bookShelf-book"></div>-->
<!--     <div class="bookShelf-shelfFoot"></div>-->
<!--  </div>-->
   <section>
<!--  最终界面   -->
     <div class="container" v-for="item in bookShelfData" :key="item.bookName" @selection-change="selsChange">
       <div class="card" >
         <div class="img">
           <img :src="item.bookImage" :alt="item.bookName">
         </div>
         <div class="content">
           <div class="title">{{ item.bookName }}&nbsp;&nbsp;<span class="author">作者:{{item.bookAuthor}}</span>
             <div class="status">
               <el-tag class="ml-2" type="success" v-if="item.bookStatus === '可借阅'" size="small">不可借阅</el-tag>
               <el-tag class="ml-2" type="danger" v-if="item.bookStatus === '不可借阅'" size="small">不可借阅</el-tag>
             </div>
             <div class="tags">
               <div v-if="item.bookTags.length!=0">
                <el-tag class="ml-2" type="warning" v-for="tag in item.bookTags" :key="tag.tagId" closable @close="deleteTag(tag)">
                     {{tag.tagValue}}
                </el-tag>
                 <div class="addTags">
                   <el-icon @click="addTagPage(item)"><CirclePlusFilled /></el-icon>
                 </div>
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
           <button class="button1" @click="deleteBookShelf(item)">删除</button>
           <button class="button2" @click="borrowBook(item)">借阅图书</button>
         </div>
       </div>
     </div>
<!--     标签添加弹窗-->
     <el-dialog v-model="addTagShow" title="标签添加" width="30%">
       <div>{{addTagInfo.bookId}}</div>
       <el-tag class="ml-2" type="warning" v-for="item in addTagInfo.bookTags" :key="item.tagId">
         {{item.tagValue}}
       </el-tag>
         <el-input v-model="tagInput.tagValue" placeholder="输入标签名称" label="添加标签:"/>
       <template #footer>
        <span class="dialog-footer">
        <el-button @click="addTagShow = false">Cancel</el-button>
        <el-button type="primary" @click="addTags">提交</el-button>
       </span>
       </template>
     </el-dialog>
     <el-pagination
         class="bookShelfPage"
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
   </section>
</template>

<script>
import {addBookTag, bookBorrow, deleteBookTag, deleteFromBookShelf, findBookShelf} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      addTagShow:false,
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
      tagInput:[],
      value:undefined,
    }
  },
  methods:{
    //弹窗
    addTagPage(item) {
      this.addTagInfo={};
      this.addTagInfo=item.bookTags;
      this.addTagShow = !this.addTagShow;
    },
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
           // alert(this.bookTags);
         } else {
           ElMessage.error(res.data.msgId);
         }
       });
     },
    //删除
    deleteBookShelf(item){
      let ids = [item.bookId];
      deleteFromBookShelf(ids,this.configs).then((res) =>{
        this.listLoading = true;
        if (res.data.statusCode === 'C200') {
          this.getBookShelf();
          ElMessage.success(res.data.msgId);
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //批量删除
    selsChange(sels) {
      this.sels = sels;
    },
    deleteMore(){
      var ids = this.sels.map(item => item.bookId);
      // alert(ids);
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        // let para = {"ids": ids};
        deleteFromBookShelf(ids,this.configs).then((res) => {
          this.listLoading = true;
          if (res.data.statusCode === 'C200') {
            this.getBookShelf();
            ElMessage.success(res.data.msgId);
          } else{
            ElMessage.error(res.data.msgId);
          }
        })
      })
    },

    //借阅图书
    borrowBook(item){
      let bookId = item.bookId;
      bookBorrow(bookId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
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

    //添加图书标签
    addTags(){
      this.value = encodeURI(encodeURI(this.tagInput.tagValue));
      addBookTag(this.addTagInfo.bookId,this.value,this.configs).then((res) =>{
        this.listLoading = true;
        // alert(this.bookId)
        if (res.data.statusCode === 'C200') {
          // this.getBookShelf();
          ElMessage.success(res.data.msgId);
          this.addTagShow=false;
        } else{
          ElMessage.error(res.data.msgId);
        }
      })

    },
    //标签删除
    deleteTag(tag){
       let tagId = tag.tagId;
      deleteBookTag(tagId,this.configs).then((res) =>{
        this.listLoading = true;
        alert(tagId);
        if (res.data.statusCode === 'C200') {
          // this.getBookShelf();
          ElMessage.success(res.data.msgId);
        } else{
          ElMessage.error(res.data.msgId);
        }
      })

    },
  },
  mounted() {
    this.getBookShelf();
  }
}
</script>

<style  scoped>
/*.bookShelf-wrap{*/
/*  background-color: #f1cf69;*/
/*}*/
*{
  /* 初始化 */
  margin: 0;
  padding: 0;
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
  height: 180px;
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
  font-size: 20px;
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
  background-color: #000;
  color: #fff;
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 50px rgba(0,0,0,0.2);
  float: right;
  margin-top: 10px;
}
.button2{
  padding: 10px 17px;
  background-color: #5b84b7;
  color: #fff;
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 50px rgba(0,0,0,0.2);
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
  /*float: left;*/
}
.addTags{
  float: right;
  color: rgba(126, 146, 161, 0.91);
}
</style>