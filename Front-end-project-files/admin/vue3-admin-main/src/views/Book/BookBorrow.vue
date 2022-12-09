<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="input" class="demo-form-inline"  style="margin-left: 100px">
        <el-form-item label="图书名称:" style="margin-top: 20px;" >
          <el-input v-model="input.userName" placeholder="请输入用户姓名"  clearable style="width: 300px;margin-right: 10px;"/>
        </el-form-item>
        <el-form-item label="预定状态:" style="width: 230px;margin-top: 20px">
          <el-select v-model="input.borrowStatus" placeholder="请选择借阅状态" clearable >
            <el-option
                v-for="item in borrowStatusList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            />
          </el-select>
        </el-form-item>
        <el-button type="warning" @click="getBorrowInfo">查询</el-button>
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="borrowList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"

  >
    <el-table-column property="bookName" label="书名" width="200" />
    <el-table-column property="borrowStatus" label="借阅状态">
      <template #default="scope" >
        <el-tag>{{scope.row.borrowStatus}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="userName" label="用户姓名" width="160" />
    <el-table-column property="borrowTime" label="借阅时间" width="220" />
    <el-table-column property="returnTime" label="归还时间" width="220" />
    <el-table-column property="Tools" label="操作" width="100">
      <template #default="scope" >
        <el-button :disabled="scope.row.borrowStatus!=='归还未确认'"  type="success" circle @click="returnBookCheck(scope.index,scope.row)"><el-icon><Check /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
</template>

<script>
import {findBorrowInfo, findBorrowStatus, returnCheck} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "BookBorrow",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //借阅查询
      input:[],
      borrowStatusList:[],
      page:1,
      total:undefined,
      userName:undefined,
      borrowStatus:undefined,
      borrowList:[],
      borrowId:undefined,
    }
  },
  methods:{
    //获取借阅状态
     getBorrowStatus(){
       findBorrowStatus(this.configs).then((res) => {
         if (res.data.statusCode === 'C200') {
           this.borrowStatusList = res.data.result;
         } else {
           ElMessage.error(res.data.message);
         }
       })
     },
    pageChange(val) {
      this.page = val;
      this.getBorrowInfo();
    },
    //查询借阅信息
    getBorrowInfo(){
       let para = {
         page:this.page,
         userName:this.input.userName === '' ? undefined : this.input.userName,
         borrowStatus:this.input.borrowStatus === '' ? undefined : this.input.borrowStatus,
       }
      findBorrowInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.borrowList = res.data.result.list;
          this.total = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //归还确认
    returnBookCheck(index,row){
      returnCheck(row.borrowId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getBorrowInfo();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }

  },
  mounted() {
    this.getBorrowStatus();
    this.getBorrowInfo();
  }
}
</script>

<style scoped>

</style>