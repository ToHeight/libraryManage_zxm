<template>
  <div class="header">
    <div class="header-main">
          <el-input v-model="info" placeholder="请输入字段值"  clearable style="width: 230px;margin-right: 10px;"/>
          <el-button type="primary" @click="getAllCode">查询</el-button>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="codeList"
      style="width: 60%;margin-top: 30px;margin-left: 180px"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column property="coding" label="编码" width="200" />
    <el-table-column property="value" label="数值" width="200" />
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="warning"  circle @click="updateCode(scope.$index, scope.row)" ><el-icon><EditPen /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 350px" />
  <el-dialog v-model="updateCodeShow" title="编码修改">
    <el-form :model="updateCodeList" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="编码序号" prop="coding">
        <el-input v-model="updateCodeList.coding" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="编码数值" prop="value">
        <el-input v-model="updateCodeList.value" type="text" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateCodeShow = false">取消</el-button>
        <el-button type="primary" @click="codeInfoUpdate">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {findAllCode, updateCodeInfo} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "ConstantCode",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //获取所有编码
      info:undefined,
      page:1,
      total:undefined,
      codeList:[],

      //修改编码
      updateCodeShow:false,
      updateCodeList:[],

    }
  },
  methods:{
    //分页
    pageChange(val) {
      this.page = val;
      this.getAllCode();
    },
     getAllCode(){
        let para={
          page:this.page,
          info:this.info,
        }
       findAllCode(para,this.configs).then((res) => {
         if (res.data.statusCode === 'C200') {
           this.codeList = res.data.result.list;
           this.total = res.data.result.total;
           // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
         } else {
           ElMessage.error(res.data.message);
         }
       })
     },
    //修改部分
    //弹窗
    updateCode(index,row){
       this.updateCodeShow=!this.updateCodeShow;
       this.updateCodeList = row;
    },
    //修改
    codeInfoUpdate(){
      let para ={
        coding:this.updateCodeList.coding===''?undefined:this.updateCodeList.coding,
        value:this.updateCodeList.value===''?undefined:this.updateCodeList.value,
      }
      updateCodeInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllCode();
          this.updateCodeShow=false;
          // this.activityList.activityOrganizerList = JSON.parse(this.activityList.activityOrganizer)
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }

  },
  mounted() {
    this.getAllCode();
  }
}
</script>

<style scoped>
.header-main{
  margin-left: 340px;
  margin-right: 5px;
}
</style>