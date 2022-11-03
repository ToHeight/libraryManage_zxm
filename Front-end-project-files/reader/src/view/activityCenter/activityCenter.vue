<template>
 <section>
   <div class="activityTools">
     <el-form :inline="true" :model="activitySelect" class="demo-form-inline">
       <el-form-item label="活动种类:">
         <el-select v-model="activitySelect.activityTypeId" placeholder="请选择活动种类">
           <el-option v-for="item in activityTypeList" :label="item.value" :value="item.value" :key="item.coding" />
         </el-select>
       </el-form-item>
       <el-form-item label="活动状态:">
         <el-select v-model="activitySelect.activityStatus" placeholder="请选择活动状态">
           <el-option v-for="item in activityStatusList" :label="item.value" :value="item.value" :key="item.coding" />
         </el-select>
       </el-form-item>
       <el-form-item>
         <el-button type="primary" @click="onSubmit">Query</el-button>
       </el-form-item>
     </el-form>
   </div>
   <div class="allActivity">
     <div class="allTitle"><h1>所有活动</h1></div>
   </div>
   <el-table :data="activityList" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
     <el-table-column fixed prop="activityId" label="活动编号" width="120" />
     <el-table-column prop="activityName" label="活动名称" width="120" />
     <el-table-column prop="activityTypeName" label="活动种类" width="120" />
     <el-table-column prop="activityInfo" label="活动信息" width="150" />
     <el-table-column prop="activityOrganizer" label="举办方" width="150" >
       <template #default="scope">
         <span v-for="(organizer,index) in JSON.parse(scope.row.activityOrganizer)" :key="organizer">{{organizer}}<span v-if="index<(JSON.parse(scope.row.activityOrganizer).length-1)">、</span></span>
       </template>
     </el-table-column>
     <el-table-column prop="activityDate" label="活动日期" width="180" />
     <el-table-column prop="activityApplication" label="报名时间" width="200" />
     <el-table-column prop="activityAge" label="活动年龄限制" width="120" />
     <el-table-column prop="activityStatus" label="活动状态" width="120" />
     <el-table-column fixed="right" label="Operations" width="200">
       <template #default="scope">
         <el-button type="primary" size="small" @click="registrationActivity(scope.$index, scope.row)" :disabled="registrationTime(scope.$index, scope.row)">报名</el-button>
         <el-button type="danger" size="small">取消报名</el-button>
       </template>
     </el-table-column>
   </el-table>
 </section>
</template>

<script>
import {
  activityRegistration,
  findActivity,
  findBook, getActivityStatus,
  getActivityType,
  getFloors,
  registrationCanel
} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
 data(){
   return{
     configs:{
       headers:{
         token: sessionStorage.getItem('AppointToken')
       }
     },
     timeActivity:true,
     activityList:[],
     page:1,
     pageSize:5,
     activityName:undefined,
     activityOrganizer: undefined,
     activityTypeId:undefined,
     activityStatus:undefined,
     itself:true,
     notShowActivityId:undefined,
     activityId:undefined,
     activitySelect:[],
     activityTypeList:[],
     activityStatusList:[],


 }
 },
  methods:{
     //所有活动查询
     activityFind(){
       let para = {
         page:this.page,
         pageSize:this.pageSize,
         activityName:this.activityName,
         activityOrganizer:this.activityOrganizer,
         activityTypeId:this.activityTypeId,
         activityStatus:this.activityStatus,
         itself:false,
         notShowActivityId:this.notShowActivityId
       };
       findActivity(para,this.configs).then((res) => {
         this.listLoading = false;
         if (res.data.statusCode === 'C200') {
           this.total = res.data.result.total;
           this.activityList = res.data.result.list;
           this.registrationTime(this.activityStatus);
         } else {
           ElMessage.error(res.data.msgId);
         }
       });
     },
    //报名时间比较
    registrationTime(index,row){
       this.timeActivity = row.activityStatus === '活动已经结束'||row.activityStatus === '报名中';
       return this.timeActivity;
    },
     //活动报名
    registrationActivity(index,row){
       let activityId = row.activityId;
        activityRegistration(activityId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          alert("报名成功")
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },

    //取消报名
    // canelRegistration(index,row){
    //    let applicationId = row.applicationId
    //    registrationCanel
    // },

    //活动种类获取
    activityTypeGet(){
      getActivityType(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityTypeList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //
    //活动种类获取
    activityStatusGet(){
      getActivityStatus(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }
  },
  mounted() {
   this.activityFind();
   this.activityTypeGet();
   this.activityStatusGet();
  }
}
</script>

<style scoped>
.allTitle{
  margin: 0 auto;
}
</style>