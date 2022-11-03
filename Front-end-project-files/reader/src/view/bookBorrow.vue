<template>
 <section>
   <div class="borrow-tools">
     <el-form :inline="true" :model="input" class="demo-form-inline">
       <el-form-item label="图书名称：">
         <el-input v-model="input.bookName" placeholder="Approved by" />
       </el-form-item>
       <el-form-item label="Activity zone">
         <el-select v-model="input.borrowStatus" placeholder="Activity zone">
           <el-option v-for="item in borrowList" :key="item.coding" :label="item.value" :value="item.coding" />
         </el-select>
       </el-form-item>
       <el-form-item>
         <el-button type="primary" @click="borrowFind">Query</el-button>
       </el-form-item>
     </el-form>
   </div>
    <div class="borrow-show">
      <el-table :data="borrowData" style="width: 100%">
        <el-table-column fixed prop="bookImage" label="图书图片" width="150">
          <template #default="scope">
            <el-image  style="width: 80px; height: 100px" :src="scope.row.bookImage" alt="" :fit="fill" ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="bookName" label="图书名称" width="150" />
        <el-table-column prop="borrowTime" label="借阅时间" width="200" />
        <el-table-column prop="returnTime" label="预计归还时间" width="200" />
        <el-table-column prop="borrowStatus" label="借阅状态" width="150">
          <template #default="scope">
            <el-tag class="ml-2" type="success" v-if="scope.borrowStatus==='正在借阅中'" size="small">正在借阅中</el-tag>
            <el-tag class="ml-2" type="danger" v-if="scope.borrowStatus==='已逾期'" size="small">已逾期</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="addMessage(scope.$index, scope.row)">留言</el-button>
            <el-button link type="primary" size="small" @click="returnBorrowBook(scope.$index, scope.row)">归还</el-button>
            <el-button link type="primary" size="small" @click="deleteBorrowBook(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
 </section>
</template>

<script>
import {addBookTag, borrowStatusList, deleteBorrow, findBorrow, leaveBookMessage, returnBook} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
  return{
    borrowData:[],
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
   }
  },
  methods:{
    //借阅状态
    getborrowStatus(){
      borrowStatusList(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.borrowList = res.data.result;
          // alert(11111);
        } else {
          ElMessage.error(res.data.msgId);
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
        } else {
          ElMessage.error(res.data.msgId);
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
          alert("归还成功")
          ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
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
          alert("删除成功")
          ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
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
        cover:this.cover
      }
      leaveBookMessage(para,this.configs).then((res) =>{
        this.listLoading = true;
        // alert(this.bookId)
        if (res.data.statusCode === 'C200') {
          alert(111);
          ElMessage.success(res.data.msgId);
          this.leaveMessage=false;
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    }

  },
  mounted() {
    this.getborrowStatus();
    this.borrowFind();
  }
}
</script>

<style scoped>

</style>