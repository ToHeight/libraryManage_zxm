<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="input" class="demo-form-inline"  style="margin-left: 100px">
        <el-form-item label="图书名称:" style="margin-top: 20px;" >
          <el-input v-model="input.bookName" placeholder="请输入图书名称"  clearable style="width: 300px;margin-right: 10px;"/>
        </el-form-item>
        <el-form-item label="预定状态:" style="width: 230px;margin-top: 20px">
          <el-select v-model="input.reserveStatus" placeholder="请选择预定状态" clearable >
            <el-option
                v-for="item in reserveStatusList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            />
          </el-select>
        </el-form-item>
        <el-button type="warning" @click="getScheduled">查询</el-button>
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="scheduledList"
      style="width: 60%;margin-left: 150px;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"

  >
    <el-table-column property="book_name" label="书名" width="140" />
    <el-table-column property="scheduledStatus" label="预约状态">
      <template #default="scope" >
        <el-tag  v-show="scope.row.scheduledStatus!=='未找到'&&scope.row.scheduledStatus!=='取消预定'" @click="statusUpdate(scope.index,scope.row)">{{scope.row.scheduledStatus}}</el-tag>
        <el-tag  v-show="scope.row.scheduledStatus==='未找到'||scope.row.scheduledStatus==='取消预定'" type="warning">{{scope.row.scheduledStatus}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="userName" label="用户姓名" width="100" />
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
  <!-- 修改状态弹窗 -->
  <el-dialog v-model="statusUpdateShow" title="图书信息修改">
    <el-radio-group v-model="scheduledStatusCoding" @change="onRadioChange">
      <el-radio :label="item.coding" :value="item.value" :key="item.coding" v-for="item in reserveStatusList">{{item.value}}</el-radio>
    </el-radio-group>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="statusUpdateShow=false">取消</el-button>
        <el-button type="primary" @click="updateStatus">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {findScheduledInfo, findScheduledStatus, scheduledStatusUpdate} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "BookScheduled",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //查询
      scheduledList:[],
      input:[],
      reserveStatusList:[],
      reserveStatus:undefined,
      page:1,
      total:undefined,
      bookName:undefined,
      book_name:undefined,
      scheduledId:undefined,
      scheduledStatus:undefined,
      userName:undefined,

      //修改状态
      statusUpdateShow:false,
      scheduledStatusCoding:undefined,
    }
  },
  methods: {
    //查询
    //图书预定状态
    getScheduledStatus() {
      findScheduledStatus(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.reserveStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //分页
    pageChange(val) {
      this.page = val;
      this.selectBook();
    },
    //图书预定信息
    getScheduled() {
      let para = {
        page: this.page,
        reserveStatus: this.input.reserveStatus === '' ? undefined : this.input.reserveStatus,
        bookName: this.input.bookName === '' ? undefined : this.input.bookName,
      }
      findScheduledInfo(para, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.scheduledList = res.data.result.list;
          this.total = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //图书状态修改
    statusUpdate(index,row){
      this.statusUpdateShow=!this.statusUpdateShow;
      this.scheduledStatus=row.scheduledStatus;
      this.scheduledId=row.scheduledId
      this.scheduledStatusCoding = row.scheduledStatusCoding;
    },
    onRadioChange(item) {
      this.scheduledStatusCoding = item;
      // alert(this.reserveStatus)
    },
    updateStatus(){
      let para ={
        scheduledId:this.scheduledId,
        reserveStatus:this.scheduledStatusCoding
      }
      scheduledStatusUpdate(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.statusUpdateShow=false;
          this.getScheduled();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }

  },mounted() {
    this.getScheduledStatus();
    this.getScheduled();
  }
}
</script>

<style scoped>

</style>