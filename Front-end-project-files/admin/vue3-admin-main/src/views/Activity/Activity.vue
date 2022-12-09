<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="activityInput" class="demo-form-inline" >
        <el-form-item label="活动名称:" style="margin-top: 20px;" >
          <el-input v-model="activityInput.activityName" placeholder="请输入用户姓名"  clearable style="width: 230px;margin-right: 10px;"/>
        </el-form-item>
        <el-form-item label="活动类别:" style="width: 230px;margin-top: 20px">
          <el-select v-model="activityInput.activityType" placeholder="请选择活动类别" clearable >
            <el-option
                v-for="item in activityTypeList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动状态:" style="width: 230px;margin-top: 20px">
          <el-select v-model="activityInput.activityStatus" placeholder="请选择活动状态" clearable >
            <el-option
                v-for="item in activityStatusList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
        <el-button type="warning" @click="getActivityInfo">查询</el-button>
        <el-button type="primary" @click="addActivity">添加</el-button>
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="activityList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"

  >
    <el-table-column property="activityName" label="活动名称" width="100" />
    <el-table-column property="activityTypeName" label="活动种类" width="90" />
    <el-table-column property="activityOrganizer" label="举办方" width="100"  >
      <template #default="scope" >
        <span  v-for="organizer in JSON.parse(scope.row.activityOrganizer)" :key="organizer">{{organizer}}&nbsp;&nbsp;</span>
<!--        <span v-show="!JSON.parse(scope.row.activityOrganizer).isArray">{{scope.row.activityOrganizer}}&nbsp;&nbsp;</span>-->
      </template>
    </el-table-column>
    <el-table-column property="activityStatus" label="活动状态" width="120">
      <template #default="scope" >
        <el-tag>{{scope.row.activityStatus}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="activityInfo" label="活动信息" width="200" />
    <el-table-column property="activityAge" label="年龄" width="70" />
    <el-table-column property="activityApplication" label="报名时间" width="105" />
    <el-table-column property="activityDate" label="活动时间" width="105" />
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="warning"  circle @click="updateActivity(scope.$index, scope.row)" ><el-icon><Edit /></el-icon></el-button>
    <el-button type="danger"  circle @click="activityDelete(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
<!--  添加弹窗-->
  <el-dialog v-model="addActivityShow" title="活动添加">
    <el-form :model="addactivityInput" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="活动名称" prop="activityName">
        <el-input v-model="addactivityInput.activityName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="举办方" prop="activityOrganizer">
        <el-input v-model="addactivityInput.addActivityOrganizer" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="活动种类" prop="activityTypeId">
        <el-select v-model="addactivityInput.activityTypeId" class="full-width-input" clearable>
          <el-option v-for="(item, index) in activityTypeList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动信息" prop="activityInfo">
        <el-input v-model="addactivityInput.activityInfo" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="活动时间" prop="activityDate">
        <el-date-picker
            v-model="addactivityInput.activityDate"
            type="datetime"
            placeholder="请选择时间"
        />
      </el-form-item>
      <el-form-item label="报名时间" prop="ac">
        <el-date-picker
            v-model="addactivityInput.activityApplication"
            type="datetime"
            placeholder="请选择时间"
        />
      </el-form-item>
      <el-form-item label="限制年龄" prop="activityAge">
        <el-input-number v-model="addactivityInput.activityAge" class="full-width-input" controls-position="right"
                         :min="0" :max="120" :precision="0" :step="1"></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addActivityShow = false">取消</el-button>
        <el-button type="primary" @click="addActivityInfo">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  修改弹窗-->
  <el-dialog v-model="updateActivityShow" title="活动修改">
    <el-form :model="updateActivityInfoList" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="活动名称" prop="activityName">
        <el-input v-model="updateActivityInfoList.activityName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="活动种类" prop="activityTypeId">
        <el-select v-model="updateActivityInfoList.activityTypeId" class="full-width-input" clearable>
          <el-option v-for="(item, index) in activityTypeList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动状态" prop="activityStatus">
        <el-select v-model="updateActivityInfoList.activityStatus" class="full-width-input" clearable>
          <el-option v-for="(item, index) in activityStatusList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动时间" prop="activityDate">
        <el-date-picker
            v-model="updateActivityInfoList.activityDate"
            type="datetime"
            placeholder="请选择时间"
        />
      </el-form-item>
      <el-form-item label="报名时间" prop="ac">
        <el-date-picker
            v-model="updateActivityInfoList.activityApplication"
            type="datetime"
            placeholder="请选择时间"
        />
      </el-form-item>
      <el-form-item label="限制年龄" prop="activityAge">
        <el-input-number v-model="updateActivityInfoList.activityAge" class="full-width-input" controls-position="right"
                         :min="0" :max="120" :precision="0" :step="1"></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateActivityShow = false">取消</el-button>
        <el-button type="primary" @click="updateActivityInfo">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import {
  addActivity,
  deleteActivity,
  findActivityInfo,
  findActivityStatus,
  findActivityType,
  findBorrowInfo, updateActivity, updateCodeInfo
} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Activity",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //条件查询
      activityInput:[],
      activityName:undefined,
      activityStatus:undefined,
      activityType:undefined,
      //活动类别获取
      activityTypeList:[],
      //活动状态获取
      activityStatusList:[],
      //活动查询
      page:1,
      total:undefined,
      activityList:[],
      activityOrganizer:undefined,
      activityOrganizerList:[],
      activityId:undefined,
      //活动新增
      activityTypeId:undefined,
      activityInfo:undefined,
      activityDate:undefined,
      activityApplication:undefined,
      activityAge:undefined,
      addactivityInput:[],
      addActivityShow:false,
      addActivityOrganizer:undefined,
      addActivityOrganizerList:[],

      //活动修改
      updateActivityShow:false,
      updateActivityInfoList:[],
    }
  },
  methods:{
    //活动类型获取
    getActivityType(){
      findActivityType(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityTypeList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //活动状态获取
    getActivityStatus(){
      findActivityStatus(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //分页
    pageChange(val) {
      this.page = val;
      this.getActivityInfo();
    },
    //获取所有活动
    getActivityInfo(){
      let para = {
        activityName:this.activityInput.activityName===''?undefined:this.activityInput.activityName,
        typeId:this.activityInput.activityType===''?undefined:this.activityInput.activityType,
        activityStatus:this.activityInput.activityStatus===''?undefined:this.activityInput.activityStatus,
        page:this.page,
      }
      findActivityInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityList = res.data.result.list;
          this.total = res.data.result.total;
          // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //添加活动部分
    //添加弹窗
    addActivity(){
      this.addActivityShow = !this.addActivityShow;
      this.addActivityOrganizerList=[];
    },
    addActivityInfo(){
      (this.addActivityOrganizerList).push(this.addactivityInput.addActivityOrganizer)
      let para = {
        activityName:this.addactivityInput.activityName===''?undefined:this.addactivityInput.activityName,
        activityTypeId:this.addactivityInput.activityTypeId===''?undefined:this.addactivityInput.activityTypeId,
        activityInfo:this.addactivityInput.activityInfo===''?undefined:this.addactivityInput.activityInfo,
        activityOrganizer:this.addactivityInput.addActivityOrganizer===''?undefined:JSON.stringify(this.addActivityOrganizerList),
        activityDate:this.addactivityInput.activityDate===''?undefined:this.addactivityInput.activityDate,
        activityApplication:this.addactivityInput.activityApplication===''?undefined:this.addactivityInput.activityApplication,
        activityAge:this.addactivityInput.activityAge===''?undefined:this.addactivityInput.activityAge,
      }
      addActivity(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
          this.getActivityInfo();
          this.addActivityShow = false;
        } else {
          ElMessage.error(res.data.message);
        }
      })

    },
    //删除活动
    activityDelete(index,row){
      deleteActivity(row.activityId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getActivityInfo();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //修改活动
    //修改弹窗
    updateActivity(index,row){
      this.updateActivityShow=!this.updateActivityShow;
      this.updateActivityInfoList=row;
    },
    //修改
    updateActivityInfo(){
      let para = {
        activityName:this.updateActivityInfoList.activityName===''?undefined:this.updateActivityInfoList.activityName,
        activityTypeId:this.updateActivityInfoList.activityTypeId===''?undefined:this.updateActivityInfoList.activityTypeId,
        activityStatus:this.updateActivityInfoList.activityStatus===''?undefined:this.updateActivityInfoList.activityStatus,
        activityDate:this.updateActivityInfoList.activityDate===''?undefined:this.updateActivityInfoList.activityDate,
        activityApplication:this.updateActivityInfoList.activityApplication===''?undefined:this.updateActivityInfoList.activityApplication,
        activityAge:this.updateActivityInfoList.activityAge===''?undefined:this.updateActivityInfoList.activityAge,
      }
      updateCodeInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
          this.getActivityInfo();
        } else {
          ElMessage.error(res.data.message);
        }
      })

    },
    // updateActivity
  },
  mounted() {
    this.getActivityType();
    this.getActivityStatus();
    this.getActivityInfo();
  }
}
</script>

<style scoped>

</style>