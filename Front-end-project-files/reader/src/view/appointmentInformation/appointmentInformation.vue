<template>
   <section>
     <div class="AppointmentHistory">
       <div class="Appointmenttitle"><h1>历史预约</h1></div>
       <div class="AppointmentInfo">
         <el-table :data="historyList" style="width: 100%" :header-cell-style="{'text-align':'center'}">
           <el-table-column prop="appointmentId" label="预约编号" width="100" align="center"/>
           <el-table-column prop="floorName" label="楼层名称" width="100" align="center"/>
           <el-table-column prop="appointmentsStatus" label="预约当前状态" width="180" align="center">
           </el-table-column>
           <el-table-column prop="appointmentTime" label="预约时间" width="250" align="center"/>
           <el-table-column prop="appointmentsCreateTime" label="预约创建时间" width="250" align="center"/>
         </el-table>
       </div>
     </div>
   </section>
</template>

<script>
import {historyAppointment} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      configs:{
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      },
      historyList:[],
      appointmentId:undefined,
      floorName:undefined,
      appointmentsStatus:undefined,
      appointmentTime:undefined,
      appointmentsCreateTime:undefined,
      page:1,

    }
  },
  methods:{
    apponitmentHistory(){
      // alert(this.page)
      historyAppointment(this.page,this.configs).then((res) => {
        if (res.data.statusCode == 'C404') {
          ElMessage.error(res.data.message);
        } else if (res.data.statusCode == 'C200') {
          this.historyList = res.data.result.list;
          ElMessage.success(res.data.message);
        }
      })
    }
  },
  mounted() {
    this.apponitmentHistory();
  }
}
</script>

<style scoped>

</style>