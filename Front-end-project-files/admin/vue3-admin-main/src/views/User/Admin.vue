<template>
  <div class="header" style="margin-left: 320px;margin-bottom: 20px">
    <div class="header-main">
          <el-input v-model="info" placeholder="请输入管理员信息"  clearable style="width: 230px;margin-right: 10px;"/>
          <el-button type="warning" @click="getAllAdmin">查询</el-button>
          <el-button type="primary" @click="addAdminShow">添加</el-button>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="adminList"
      style="width: 85%;margin-left: 80px"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column property="managerName" label="管理员姓名" width="120" />
    <el-table-column property="managerGender" label="性别" width="120" />
    <el-table-column property="age" label="年龄" width="120" />
    <el-table-column property="managerTelephone" label="电话" width="160" />
    <el-table-column property="name" label="职务" width="180">
      <template #default="scope">
        <el-tag>{{ scope.row.name }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="tools" label="操作" width="200">
      <template #default="scope">
        <el-button type="warning" circle @click="updateAdminShow(scope.$index, scope.row)"><el-icon><EditPen /></el-icon></el-button>
        <el-button type="primary" @click="updatePasswordShow(scope.$index, scope.row)">修改密码</el-button>
      </template>
    </el-table-column>

  </el-table>
  <!--  添加弹窗-->
  <el-dialog v-model="adminAddShow" title="管理员添加">
    <el-form :model="adminAddList" ref="vForm"  label-position="right" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="管理员ID:" prop="managerId">
        <el-input v-model="adminAddList.managerId" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="姓名:" prop="managerName">
        <el-input v-model="adminAddList.managerName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="年龄:" prop="age">
        <el-input v-model="adminAddList.age" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别:" prop="gender">
        <el-select v-model="adminAddList.gender" class="full-width-input" clearable>
          <el-option v-for="(item, index) in genderList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职务:" prop="permissionId">
        <el-select v-model="adminAddList.permissionId" class="full-width-input" clearable>
          <el-option v-for="(item, index) in roleList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话:" prop="telephone">
        <el-input v-model="adminAddList.telephone" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="地址:" prop="address">
        <el-input type="textarea" v-model="adminAddList.address" rows="2"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="adminAddShow = false">取消</el-button>
        <el-button type="primary" @click="addAdminInfo">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!--  修改弹窗-->
  <el-dialog v-model="adminUpdateShow" title="管理员修改">
    <el-form :model="adminUpdateList" ref="vForm"  label-position="right" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="姓名:" prop="managerName">
        <el-input v-model="adminUpdateList.managerName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别:" prop="managerGenderCoding">
        <el-select v-model="adminUpdateList.managerGenderCoding" class="full-width-input" clearable>
          <el-option v-for="(item, index) in genderList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职务:" prop="permissionCoding">
        <el-select v-model="adminUpdateList.permissionCoding" class="full-width-input" clearable>
          <el-option v-for="(item, index) in roleList" :key="index" :label="item.value"
                     :value="item.coding">{{item.value}}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话:" prop="managerTelephone">
        <el-input v-model="adminUpdateList.managerTelephone" type="text" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="adminUpdateShow = false">取消</el-button>
        <el-button type="primary" @click="updateAdminInfo">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {
  addAdmin,
  findAllAdmin,
  findAllGender,
  findRoleList,
  updateAdmin,
  updateAdminPassword
} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "Admin",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //管理员信息获取
      page:1,
      total:undefined,
      adminList:[],
      //条件查询
      info:undefined,
      //获取到的参数
      managerName:undefined,
      managerGender:undefined,
      age:undefined,
      managerTelephone:undefined,
      name:undefined,
      //添加
      //下拉列表
      roleList:[],
      genderList:[],
      //弹窗
      adminAddShow:false,
      //所需参数
      adminAddList:[],
      managerId:undefined,
      genderCoding:undefined,
      telephone:undefined,
      permissionId:undefined,
      address:undefined,

      //修改
      //弹窗
      adminUpdateShow:false,
      //所需参数
      adminUpdateList:[],
      managerGenderCoding:undefined,
      permissionCoding:undefined,

      //修改自己密码
      //弹窗
      // passwordUpdateShow:false,
      //所需参数
      // passwordUpdateList:[],

    }
  },
  methods:{
    getAllAdmin(){
      let para = {
        page:this.page,
        info:this.info===''?undefined:this.info,
      }
      findAllAdmin(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.adminList = res.data.result.list;
          this.total = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //添加部分
    //弹窗
    addAdminShow(){
     this.adminAddShow = !this.adminAddShow;
    },
    //获取性别下拉框
    getGender(){
      findAllGender(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.genderList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //权限下拉列表
    getRoleList(){
      findRoleList(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.roleList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //添加
    addAdminInfo(){
      let para = {
        managerId:this.adminAddList.managerId===''?undefined:this.adminAddList.managerId,
        managerName:this.adminAddList.managerName===''?undefined:this.adminAddList.managerName,
        gender:this.adminAddList.gender===''?undefined:this.adminAddList.gender,
        telephone:this.adminAddList.telephone===''?undefined:this.adminAddList.telephone,
        permissionId:this.adminAddList.permissionId===''?undefined:this.adminAddList.permissionId,
        age:this.adminAddList.age===''?undefined:this.adminAddList.age,
        address:this.adminAddList.address===''?undefined:this.adminAddList.address,
      }
      addAdmin(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllAdmin();
          this.adminAddShow = false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //修改部分
    updateAdminShow(index,row){
      this.adminUpdateShow = !this.adminUpdateShow;
      this.adminUpdateList = row;
    },
    //修改
    updateAdminInfo(){
      let para = {
        managerId:this.adminUpdateList.managerId===''?undefined:this.adminUpdateList.managerId,
        managerName:this.adminUpdateList.managerName===''?undefined:this.adminUpdateList.managerName,
        gender:this.adminUpdateList.managerGenderCoding===''?undefined:this.adminUpdateList.managerGenderCoding,
        telephone:this.adminUpdateList.managerTelephone===''?undefined:this.adminUpdateList.managerTelephone,
        permissionId:this.adminUpdateList.permissionCoding===''?undefined:this.adminUpdateList.permissionCoding,
      }
      updateAdmin(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllAdmin();
          this.adminUpdateShow = false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //修改自己密码
    // updatePassword(){
    //   let para = {
    //     userLoginName:this.passwordUpdateList.userLoginName===''?undefined:this.passwordUpdateList.userLoginName,
    //     password:this.passwordUpdateList.password===''?undefined:this.passwordUpdateList.password,
    //   }
    //   updateAdminPassword(para,this.configs).then((res) => {
    //     if (res.data.statusCode === 'C200') {
    //       ElMessage.success(res.data.message);
    //       this.getAllAdmin();
    //       this.passwordUpdateShow = false;
    //     } else {
    //       ElMessage.error(res.data.message);
    //     }
    //   })
    // }
    // updateAdminPassword

  },
  mounted() {
    this.getAllAdmin();
    this.getRoleList();
    this.getGender();
  }
}
</script>

<style scoped>

</style>