
<template>
<!--  <section></section>-->
<!--  <div class="bookShelf-wrap">-->
<!--     <div class="bookShelf-book"></div>-->
<!--     <div class="bookShelf-shelfFoot"></div>-->
<!--  </div>-->
   <section>
<!--  最终界面   -->
     <div class="container">
       <div class="card">
         <div class="img">
           <img src="../assets/dmbj.png" alt="">
         </div>
         <div class="content">
           <div class="title">三体</div>
           <div class="text">波雅·汉库克，日本漫画《航海王》及其衍生作品中的角色。人称：海贼女帝，是位于无风带女儿岛的海贼国家亚马逊·百合王国的现任皇帝，同时也是九蛇海贼团的船长，其绝世的容颜被世人评价为“世界第一美女”。</div>
           <button class="button1">删除</button>
           <button class="button2">借阅图书</button>
         </div>
       </div>
     </div>
<!--初始界面-->
<!--     <div class="bookShelf-tools">-->
<!--       <el-button type="danger" size="small" @click="deleteMore">批量删除</el-button>-->
<!--     </div>-->
<!--     <div class="bookShelf-show">-->
<!--        <el-table :data="bookShelfData" style="width: 100%" @selection-change="selsChange" class="bookShelf-show"  max-height="1200" :header-cell-style="{background:'#e7a234',color:'#ffffff'}">-->
<!--          <el-table-column type="selection" width="55" />-->
<!--              <el-table-column fixed prop="bookStatus" label="图书状态" width="120">-->
<!--                <template #default="scope">-->
<!--                  <el-tag class="ml-2" type="success" v-if="scope.bookStatus==='可借阅'" size="small">可借阅</el-tag>-->
<!--                  <el-tag class="ml-2" type="danger" v-if="scope.bookStatus==='不可借阅'" size="small">不可借阅</el-tag>-->
<!--                </template>-->
<!--              </el-table-column>-->
<!--              <el-table-column fixed prop="bookImage" label="图书图片" width="150">-->
<!--                <template #default="scope">-->
<!--                  <el-image  style="width: 80px; height: 100px" :src="scope.row.bookImage" alt="" :fit="fill" ></el-image>-->
<!--                </template>-->
<!--              </el-table-column>-->
<!--              <el-table-column prop="bookName" label="图书名称" width="120" />-->
<!--              <el-table-column prop="bookAuthor" label="图书作者" width="120" />-->
<!--              <el-table-column prop="bookType" label="图书种类" width="110" />-->
<!--              <el-table-column prop="bookLanguage" label="图书语言" width="110" />-->
<!--              <el-table-column prop="bookCountry" label="图书国家" width="150" />-->
<!--              <el-table-column prop="bookTags" label="图书标签" width="150">-->
<!--                <template #default="scope">-->
<!--                  <div v-if="scope.row.bookTags.length!=0">-->
<!--                  <el-tag class="ml-2" type="warning" v-for="tag in scope.row.bookTags" :key="tag.tagId" closable @close="deleteTag(tag)">-->
<!--                    {{tag.tagValue}}-->
<!--                  </el-tag>-->
<!--                  </div>-->
<!--                  <el-button link type="primary" size="small" @click="addTagPage(scope.$index, scope.row)">添加</el-button>-->
<!--                  &lt;!&ndash;                    @click="addTags(scope.$index, scope.row)"&ndash;&gt;-->
<!--&lt;!&ndash;                  <el-button link type="primary" size="small" @click="deleteTag(scope.$index, scope.row)">删除</el-button>&ndash;&gt;-->
<!--                </template>-->
<!--              </el-table-column>-->
<!--              <el-table-column prop="bookInfo" label="图书信息" width="600" />-->
<!--              <el-table-column fixed="right" label="功能" width="150">-->
<!--                <template #default="scope">-->
<!--&lt;!&ndash;                  <el-button link type="primary" size="small" @click="leaveMessage(scope.$index, scope.row)">留言</el-button>&ndash;&gt;-->
<!--                  <el-button link type="primary" size="small" @click="deleteBookShelf(scope.$index, scope.row)">删除</el-button>-->
<!--                  <el-button link type="primary" size="small" @click="borrowBook(scope.$index, scope.row)">借阅</el-button>-->
<!--                </template>-->
<!--              </el-table-column>-->
<!--            </el-table>-->
<!--     </div>-->
<!--     标签添加弹窗-->
     <el-dialog v-model="addTagShow" title="标签添加" width="30%" >
<!--       <div>{{addTagInfo.bookId}}</div>-->
       <el-tag class="ml-2" type="warning" v-for="item in addTagInfo.bookTags" :key="item.tagId">
         {{item.tagValue}}
       </el-tag>
         <el-input v-model="tagInput.tagValue" placeholder="输入标签名称" label="添加标签:"/>
       <template #footer>
        <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
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
    addTagPage(index,row) {
      this.addTagInfo={};
      this.addTagInfo=row;
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
    deleteBookShelf(index, row){
      let ids = [row.bookId];
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
    borrowBook(index, row){
      let bookId = row.bookId;
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
  max-width: 500px;
  height: 250px;
  background-color: #fff;
  border-radius: 25px;
  box-shadow: 0 10px 50px rgba(0,0,0,0.3);
  position: relative;
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
  left: 5px;
  /* 文本内容默认隐藏 */
  /*opacity: 0;*/
  /* 过渡效果 */
  transition: 0.6s;
}
.card .title{
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 20px;
}
.card .text{
  font-size: 12px;
  color: #555;
  text-align: justify;
  margin-bottom: 25px;
}
.button1{
  padding: 13px 20px;
  background-color: #000;
  color: #fff;
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 50px rgba(0,0,0,0.2);
  float: right;
}
.button2{
  padding: 13px 20px;
  background-color: #5b84b7;
  color: #fff;
  text-decoration: none;
  border-radius: 50px;
  letter-spacing: 1px;
  font-weight: 600;
  box-shadow: 0 10px 50px rgba(0,0,0,0.2);
  float: right;
  margin-right: 10px;
}
</style>