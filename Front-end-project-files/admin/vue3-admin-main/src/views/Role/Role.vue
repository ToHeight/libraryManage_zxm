<template>
  <el-button type="primary" @click="addShow">添加权限</el-button>
  <el-table
      ref="multipleTableRef"
      :data="RoleList"
      style="width: 100%;margin-top: 30px;"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column property="roleInfo" label="职务" width="200" />
    <el-table-column property="name" label="权限" width="700"  >
      <template v-slot="scope">
            <span v-for="(s,index) in scope.row.module" >
              <el-tag type="primary" style="margin-right: 5px">{{s.name}}</el-tag>
<!--              <span v-if="index!=scope.row.module.length-1">,</span>-->
            </span>
      </template>
    </el-table-column>
    <el-table-column property="Tools" label="操作">
      <template #default="scope" >
        <el-button type="warning"  circle @click="updateShow(scope.$index, scope.row)" ><el-icon><Edit /></el-icon></el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--  添加弹窗-->
  <el-dialog v-model="addRoleShow" title="权限添加">
    <el-form :model="roleAddList" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="权限ID" prop="roleId">
        <el-input v-model="roleAddList.roleId" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="权限名称" prop="roleInfo">
        <el-input v-model="roleAddList.roleInfo" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="拥有职务" prop="module">
        <el-select
            v-model="roleAddList.moduleId"
            multiple
            placeholder="Select"
            style="width: 240px"
        >
          <el-option
              v-for="item in ModuleList"
              :key="item.coding"
              :label="item.value"
              :value="item.coding"
          >{{item.value}}</el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addRoleShow = false">取消</el-button>
        <el-button type="primary" @click="roleAdd">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!--  修改弹窗-->
  <el-dialog v-model="updateRoleShow" title="权限修改">
    <el-form :model="updateRoleList" ref="vForm"  label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="权限ID" prop="roleId">
        <el-input v-model="updateRoleList.roleId" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="权限名称" prop="roleInfo">
        <el-input v-model="updateRoleList.roleInfo" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="拥有职务" prop="module">
        <el-select
            v-model="updateRoleList.moduleIds"
            multiple
            placeholder="Select"
            style="width: 240px"
        >
          <el-option
              v-for="item in ModuleList"
              :key="item.coding"
              :label="item.value"
              :value="item.coding"
          >{{item.value}}</el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateRoleShow = false">取消</el-button>
        <el-button type="primary" @click="updateRole">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {addRoleInfo, findAllModule, findAllRole, updateRoleInfo} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Role",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //获取全部权限
      RoleList:[],
      moduleList:[],
      roleId:undefined,
      roleInfo:undefined,

      //获取模块下拉框
      ModuleList:[],

      //权限添加
      addRoleShow:false,
      roleAddList:[],
      // roleId:undefined,
      // roleName:undefined,
      moduleId:[],

      //权限修改
      updateRoleShow:false,
      updateRoleList:{
        roleId:undefined,
        roleInfo:undefined,
        moduleIds:[],
        module:[],
      },
      moduleInfos:[],
      ids:[],
    }
  },
  methods:{
    getAllRole(){
      findAllRole(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.RoleList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //添加权限部分
    //添加弹窗
    addShow(){
      this.addRoleShow = !this.addRoleShow;
    },
    //获取模块下拉框
    getModuleList(){
      findAllModule(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.ModuleList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //添加权限
    roleAdd(){
      let para = {
        roleId:this.roleAddList.roleId===''?undefined:this.roleAddList.roleId,
        roleName:this.roleAddList.roleInfo===''?undefined:this.roleAddList.roleInfo,
        moduleId:this.roleAddList.moduleId.length===0?undefined:this.roleAddList.moduleId,
      }
      addRoleInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllRole();
          this.addRoleShow=false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //权限修改部分
    //权限弹窗
    updateShow(index,row){
      this.updateRoleShow = !this.updateRoleShow;
      this.updateRoleList=row;
      let ids=[];
      let moduleInfos = this.updateRoleList.module;
      for (let i = 0; i <moduleInfos.length; i++) {
        ids.push(moduleInfos[i].moduleId)
      }
      this.updateRoleList.moduleIds=ids;
    },
    //修改
    updateRole(){
      let para = {
        roleId:this.updateRoleList.roleId===''?undefined:this.updateRoleList.roleId,
      roleName:this.updateRoleList.roleInfo===''?undefined:this.updateRoleList.roleInfo,
      moduleId:this.updateRoleList.moduleIds.length===0?undefined:this.updateRoleList.moduleIds,
      }
      updateRoleInfo(para,this.configs).then((res) => {
            if (res.data.statusCode === 'C200') {
              ElMessage.success(res.data.message);
              this.getAllRole();
              this.updateRoleShow=false;
            } else {
              ElMessage.error(res.data.message);
            }
          })
    }
  },
  mounted() {
    this.getAllRole();
    this.getModuleList();
  }
}
</script>

<style scoped>

</style>