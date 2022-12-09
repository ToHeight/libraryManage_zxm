<template>
  <div class="header">
    <div class="header-main" style="margin-left: 350px">
      <el-input v-model="info" placeholder="请输入活动名称"  clearable style="width: 230px;margin-right: 10px;"/>
      <el-button type="primary" @click="getApplication">查询</el-button>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="applicationList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column property="userName" label="用户姓名" width="160" />
    <el-table-column property="userAge" label="用户年龄" width="160" />
    <el-table-column property="activityName" label="活动名称" width="160" />
    <el-table-column property="activityInfo" label="活动信息" width="250" />
    <el-table-column property="applicationStatus" label="报名状态" width="160" />
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="primary"  :disabled="scope.row.applicationStatus!=='报名中'" circle @click="sureApplication(scope.$index, scope.row)" ><el-icon><Check /></el-icon></el-button>
        <el-button type="danger" :disabled="scope.row.applicationStatus!=='报名成功'" circle @click="applicationCanel(scope.$index, scope.row)" ><el-icon><Close /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
</template>

<script>
import {canelApplication, findApplicationInfo, successApplication} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Application",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //报名查询
      applicationList:[],
      info:undefined,
      page:1,
      total:undefined,
      //获得参数
      activityInfo:undefined,
      activityName:undefined,
      applicationId:undefined,
      applicationStatus:undefined,
      applicationStatusCoding:undefined,
      userAge:undefined,
      userId:undefined,
      userName:undefined
    }
  },
  methods:{
    //分页
    pageChange(val) {
      this.page = val;
      this.getActivityInfo();
    },
       getApplication(){
         let para = {
           info:this.info===''?undefined:this.info,
           page:this.page,
         }
         findApplicationInfo(para,this.configs).then((res) => {
           if (res.data.statusCode === 'C200') {
             this.applicationList = res.data.result.list;
             this.total = res.data.result.total;
             // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
           } else {
             ElMessage.error(res.data.message);
           }
         })
       },
       //报名成功
       sureApplication(index,row){
         successApplication(row.applicationId,this.configs).then((res) => {
           if (res.data.statusCode === 'C200') {
             ElMessage.success(res.data.message);
           } else {
             ElMessage.error(res.data.message);
           }
         })

       },
    //删除报名
    applicationCanel(index,row){
      canelApplication(row.applicationId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }

  },mounted() {
     this.getApplication();
  }
}
</script>

<style scoped>

</style>