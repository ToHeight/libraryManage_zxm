<template>
  <el-table
      ref="multipleTableRef"
      :data="floorList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column property="floorName" label="楼层名称" width="160" />
    <el-table-column property="floorStatus" label="楼层状态" width="160" />
    <el-table-column property="peopleCount" label="楼层人数" width="160" />
    <el-table-column property="timeStart" label="开始开放时间" width="160" />
    <el-table-column property="timeEnd" label="结束开放时间" width="250" />
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="warning"   circle @click="updateFloor(scope.$index, scope.row)" ><el-icon><Edit /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--  修改弹窗-->
  <el-dialog v-model="updateFloorShow" title="楼层信息">
    <el-form :model="updateFloorInfoList" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="楼层ID" prop="floorId">
        <el-input :disabled="disableText" v-model="updateFloorInfoList.floorId" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="楼层名称" prop="floorName">
        <el-input :disabled="disableText" v-model="updateFloorInfoList.floorName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="楼层人数" prop="peopleCount">
        <el-input :disabled="disableText" v-model="updateFloorInfoList.peopleCount" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="楼层状态" prop="floorStatusCoding">
        <el-select v-model="updateFloorInfoList.floorStatusCoding" class="full-width-input" clearable>
          <el-option v-for="(item, index) in statusList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开放时间" prop="timeId">
        <el-select v-model="updateFloorInfoList.timeId" class="full-width-input" clearable>
          <el-option v-for="(item, index) in timeList" :key="index" :label="item.timeStart+'~'+item.timeEnd"
                     :value="item.timeId">{{item.timeStart}}~{{item.timeEnd}}</el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateFloorShow = false">取消</el-button>
        <el-button type="primary" @click="floorUpdate">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import {findFloor, findStatusList, findTimeList, updatefloorInfo} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Floor",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      page:1,
      floorList:[],
      //获取到的参数
      floorId:undefined,
      floorName:undefined,
      peopleCount:undefined,
      floorStatus:undefined,
      timeEnd:undefined,
      timeId:undefined,
      timeStart:undefined,
      floorStatusCoding:undefined,
      //获取时间下拉框
      timeList:[],
      openTime:undefined,
      //获取状态下拉框
      statusList:[],
      //修改楼层
      disableText:true,
      updateFloorShow:false,
      updateFloorInfoList:[],
    }
  },
  methods:{
    //获取楼层信息
     getFloorInfo(){
       findFloor(this.page,this.configs).then((res) => {
         if (res.data.statusCode === 'C200') {
           this.floorList = res.data.result.list;
         } else {
           ElMessage.error(res.data.message);
         }
       })
     },

    //修改
    //弹窗
    updateFloor(index,row){
       this.updateFloorShow = !this.updateFloorShow;

       this.updateFloorInfoList = row;
       this.openTime = row.timeStart+'~'+row.timeEnd;
    },
    //获取时间下拉
    findAllTime(){
      findTimeList(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.timeList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //获取状态下拉
    findAllStatus(){
      findStatusList(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.statusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //修改楼层信息
    floorUpdate(){
       let para={
         floorId:this.updateFloorInfoList.floorId===''?undefined:this.updateFloorInfoList.floorId,
         floorStatus:this.updateFloorInfoList.floorStatusCoding===''?undefined:this.updateFloorInfoList.floorStatusCoding,
         timeId:this.updateFloorInfoList.timeId===''?undefined:this.updateFloorInfoList.timeId,
       }
      updatefloorInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getFloorInfo();
          this.updateFloorShow=false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }
    // updatefloorInfo
  },
  mounted() {
    this.getFloorInfo();
    this.findAllTime();
    this.findAllStatus();
  }
}
</script>

<style scoped>

</style>