<template>
  <div class="header">
    <div class="header-main" style="margin-left: 280px">
      <el-form :inline="true" :model="appointmentInput" class="demo-form-inline" >
        <el-form-item label="用户名:" style="margin-top: 20px;" >
          <el-input v-model="appointmentInput.info" placeholder="请输入用户姓名"  clearable style="width: 230px;margin-right: 10px;"/>
        </el-form-item>
        <el-button type="warning" @click="getAllAppointment">查询</el-button>
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="appointmentList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"

  >
    <el-table-column property="userName" label="用户名" width="140" />
    <el-table-column property="floorName" label="预约楼层" width="140" />
    <el-table-column property="appointmentsStatus" label="预约状态" width="140">
      <template #default="scope" >
        <el-tag type="primary" v-if="scope.row.appointmentsStatus==='正在预约中'">{{scope.row.appointmentsStatus}}</el-tag>
        <el-tag type="success" v-if="scope.row.appointmentsStatus==='预约成功'">{{scope.row.appointmentsStatus}}</el-tag>
        <el-tag type="warning" v-if="scope.row.appointmentsStatus==='预约撤回'">{{scope.row.appointmentsStatus}}</el-tag>
        <el-tag type="warning" v-if="scope.row.appointmentsStatus==='预约已结束'">{{scope.row.appointmentsStatus}}</el-tag>
        <el-tag type="danger" v-if="scope.row.appointmentsStatus==='预约失败'">{{scope.row.appointmentsStatus}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="appointmentTime" label="预约时间" width="200" />
    <el-table-column property="appointmentsCreateTime" label="预约创建时间" width="200" />
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="danger" :disabled="scope.row.appointmentsStatus!=='正在预约中'&&scope.row.appointmentsStatus!=='预约成功'"  circle @click="seatAppointmentCanel(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
</template>

<script>
import {canelSeatAppointment, findSeatInfo} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Appointment",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //获取所有
      appointmentList:[],
      appointmentId:undefined,
      appointmentTime:undefined,
      appointmentsCreateTime:undefined,
      appointmentsStatus:undefined,
      appointmentsStatusCoding:undefined,
      floorName:undefined,
      userName:undefined,

      //条件查询
      appointmentInput:[],
      info:undefined,
      page:1,
      total:undefined,
      //
    }
  },
  methods:{
    //分页
    pageChange(val) {
      this.page = val;
      this.getAllAppointment();
    },
     //获取所有预约信息
    getAllAppointment(){
      let para ={
        page:this.page,
        info:this.appointmentInput.info===''?undefined:this.appointmentInput.info,
      }
      findSeatInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.appointmentList = res.data.result.list;
          this.total = res.data.result.total;
          // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //撤销预约
    seatAppointmentCanel(index,row){
      canelSeatAppointment(row.appointmentId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllAppointment();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }
    // canelSeatAppointment
  },
  mounted() {
    this.getAllAppointment();
  }
}
</script>

<style scoped>

</style>