<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="info" class="demo-form-inline"  style="margin-left: 270px">
        <el-form-item label="图书名称或留言内容:" >
          <el-input v-model="info" placeholder="请输入图书名称"  clearable style="width: 300px;margin-right: 10px"/>
          <el-button type="warning" @click="getMessage">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="messageList"
      style="width: 100%"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column label="图书图片" width="110">
      <template #default="scope">
        <img :src="scope.row.bookImage" style="width: 70px;height: 90px">
      </template>
    </el-table-column>
    <el-table-column property="bookName" label="书名" width="140" />
<!--    <el-table-column property="commentsId" label="留言ID" width="100" />-->
    <el-table-column property="commentsInfo" label="留言内容"  />
<!--    <el-table-column property="userId" label="用户ID" width="100" />-->
    <el-table-column property="userName" label="用户姓名" width="90" />
    <el-table-column property="tools" label="操作" width="100">
      <template #default="scope">
        <el-button type="danger" circle @click="messageDelete(scope.index,scope.row)"><el-icon><Delete /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
  <!-- 删除弹窗 -->
  <el-dialog v-model="deleteMessageShow" title="留言删除理由">
    <el-input type="textarea" v-model="messageToUser" rows="3"></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteMessageShow = false">取消</el-button>
        <el-button type="primary" @click="messageInfoDelete">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {deleteMessage, findMessageList} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "BookMessage",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //留言查询
      info:undefined,
      messageInfo:undefined,
      page:1,
      total:undefined,
      bookImage:undefined,
      bookName:undefined,
      commentsId:undefined,
      commentsInfo:undefined,
      userId:undefined,
      userName:undefined,
      messageList:[],

      //留言删除
      deleteMessageShow:false,
      messageToUser:undefined,
      rowMessage:[],

    }
  },
  methods:{
    pageChange(val) {
      this.page = val;
      this.getMessage();
    },
    //查询留言
    getMessage(){
      let para ={
        page:this.page,
        info:this.info === '' ?undefined:this.info,

      }
      findMessageList(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.messageList=res.data.result.list;
          this.total=res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //删除弹窗
    messageDelete(index,row){
      this.deleteMessageShow = !this.deleteMessageShow;
      this.rowMessage = row;
    },
    //删除
    messageInfoDelete(){
      let para = {
        commentsId:this.rowMessage.commentsId,
        info:this.messageToUser
      }
      deleteMessage(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.deleteMessageShow = false;
          this.getMessage();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }
  },
  mounted() {
    this.getMessage();
  }
}
</script>

<style scoped>

</style>