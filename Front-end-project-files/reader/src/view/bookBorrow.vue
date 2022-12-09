<template>
 <section>
   <div class="borrowWrap"  v-if="loadingShow">
   <div class="borrow-tools">
     <el-form :inline="true" :model="input" class="demo-form-inline">
       <el-form-item label="图书名称:">
         <el-input v-model="input.bookName" placeholder="请输入借阅图书名称" />
       </el-form-item>
       <el-form-item label="借阅状态:">
         <el-select v-model="input.borrowStatus" placeholder=请选择借阅状态>
           <el-option v-for="item in borrowList" :key="item.coding" :label="item.value" :value="item.coding"/>
         </el-select>
       </el-form-item>
       <el-form-item>
         <el-button type="primary" @click="borrowFind" style="width: 80px">查找</el-button>
       </el-form-item>
     </el-form>
   </div>
    <div class="borrow-show">
      <el-table :data="borrowData" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}" style="width: 100%">
        <el-table-column fixed prop="bookImage" label="图书图片" width="130">
          <template #default="scope">
            <el-image  style="width: 80px; height: 100px" :src="scope.row.bookImage" alt="" :fit="fill" ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="bookName" label="图书名称" width="120" />
        <el-table-column prop="borrowTime" label="借阅时间" width="200" />
        <el-table-column prop="returnTime" label="预计归还时间" width="200" />
        <el-table-column prop="borrowStatus" label="借阅状态" width="130"/>
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-tooltip :visible="scope.visible1">
              <template #content>
                <span>留言</span>
              </template>
              <el-button type="warning" @mouseenter="scope.visible1 = true" @mouseleave="scope.visible1 = false" circle @click="addMessage(scope.$index, scope.row)" :disabled="(scope.row.borrowStatus==='归还未确认'||scope.row.borrowStatus==='正在借阅中'||scope.row.borrowStatus==='已逾期')">
                <el-icon><Message /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip :visible="scope.visible2">
              <template #content>
                <span>归还</span>
              </template>
              <el-button type="primary" @mouseenter="scope.visible2 = true" @mouseleave="scope.visible2 = false" circle @click="returnBorrowBook(scope.$index, scope.row)" :disabled="(scope.row.borrowStatus==='已归还'||scope.row.borrowStatus==='归还未确认'||scope.row.borrowStatus==='已逾期')">
                <el-icon><Finished /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip :visible="scope.visible3">
              <template #content>
                <span>删除</span>
              </template>
              <el-button type="danger" @mouseenter="scope.visible3 = true" @mouseleave="scope.visible3 = false" circle @click="deleteBorrowBook(scope.$index, scope.row)" :disabled="(scope.row.borrowStatus==='正在借阅中'||scope.row.borrowStatus==='归还未确认'||scope.row.borrowStatus==='已逾期')">
                <el-icon><Delete /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip :visible="scope.visible3">
              <template #content>
                <span>续租</span>
              </template>
              <el-button type="info" @mouseenter="scope.visible3 = true" @mouseleave="scope.visible3 = false" circle @click="addTimeBorrow(scope.$index, scope.row)" :disabled="!(scope.row.borrowStatus==='已逾期')">
                <el-icon><Pointer /></el-icon>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="bookPage"
          v-model:currentPage="page"
          v-model:page-size="pageSize"
          :page-sizes="[2, 4, 6]"
          small="false"
          @current-change="pageChange"
          background="false"
          layout="sizes, prev, pager, next"
          :total="total"
          @size-change="handleSizeChange"
      />
    </div>
   <el-dialog v-model="leaveMessage" title="留言添加" width="30%" >
     <el-input v-model="messagesInput.message" :rows="4" type="textarea" placeholder="Please input"/>
     <template #footer>
        <span class="dialog-footer">
        <el-button @click="leaveMessage = false">Cancel</el-button>
        <el-button type="primary" @click="messageAdd">提交</el-button>
       </span>
     </template>
   </el-dialog>
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
import {
  addBookTag,
  borrowStatusList,
  deleteBorrow,
  findBorrow,
  leaveBookMessage,
  renewBook,
  returnBook
} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
  return{
    borrowData:[],
    borrowStatus:undefined,
    borrowList:[{
      coding:'',
      value:'',
    }],
    configs:{
      headers:{
        token: sessionStorage.getItem('AppointToken')
      }
    },
    page:1,
    total:0,
    pageSize:10,
    bookId:undefined,
    borrowId:undefined,
    input:[],
    messageLeave:undefined,
    leaveMessage:false,
    messageList:[],
    cover:false,
    messagesInput:[],
    visible1:undefined,
    visible2:undefined,
    visible3:undefined,
    loadingShow:false,
   }
  },
  methods:{
    //分页
    handleSizeChange(val){
      this.pageSize=val;
      this.findReservationBook();
    },
    pageChange(val) {
      this.page = val;
      this.findReservationBook();
    },
    //借阅状态
    getborrowStatus(){
      borrowStatusList(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.borrowList = res.data.result;
          // alert(11111);
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    borrowFind(){
      let para={
        page:this.page,
        pageSize:this.pageSize,
        bookName:this.input.bookName==='' ? undefined : this.input.bookName,
        borrowStatus:this.input.borrowStatus==='' ? undefined : this.input.borrowStatus,
      }
      findBorrow(para,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.borrowData = res.data.result.list;
          this.loadingShow=true;
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //归还图书(成功)
    returnBorrowBook(index, row){
      let borrowId = row.borrowId
      returnBook(borrowId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          // this.total = res.data.result.total;
          // this.bookShelfData = res.data.result.list;
          // alert("归还成功")
          ElMessage.success(res.data.message);
          this.borrowFind();
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //删除借阅信息
    deleteBorrowBook(index, row){
      let id = row.borrowId
      deleteBorrow(id,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.borrowFind();
          // alert("删除成功")
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //图书详情页面
    getBookInfo(index,row){
      window.open('http://' + window.location.host + '/#/bookInfo/'+row.bookId,'_blank')
    },
    //留言弹窗
    addMessage(index, row){
      this.messageList={};
      this.bookId = row.bookId;
      this.borrowId = row.borrowId;
      this.leaveMessage=!this.leaveMessage;
    },
    //留言
    messageAdd(){
      let para = {
        bookId:this.bookId,
        borrowId:this.borrowId,
        message:this.messagesInput.message,
        cover:true,
      }
      leaveBookMessage(para,this.configs).then((res) =>{
        this.listLoading = true;
        // alert(this.bookId)
        if (res.data.statusCode === 'C200') {
          // alert(111);
          ElMessage.success(res.data.message);
          this.leaveMessage=false;
        } else{
          ElMessage.error(res.data.message);
        }
      })
    },
    //续租
    addTimeBorrow(index, row){
      renewBook(row.borrowId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.borrowFind();
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    }

  },
  mounted() {
    this.getborrowStatus();
    this.borrowFind();
    this.loadingShow=false;
  }
}
</script>

<style scoped>
.borrow-tools{
  margin-left: 270px;
  margin-top: 30px;
}

.borrow-show{
  margin-top: 30px;
  margin-left: 120px;
  width: 1000px;
  /*border: 1px solid red;*/
}
.bookPage{
  margin-left: 400px;
  margin-top: 10px;
}
/*加载*/

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


</style>