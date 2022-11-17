<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="userInput" class="demo-form-inline" >
        <el-form-item label="用户姓名:" style="margin-top: 20px;" >
          <el-input v-model="userInput.userName" placeholder="请输入用户姓名"  clearable style="width: 230px;margin-right: 10px;"/>
        </el-form-item>
        <el-form-item label="用户性别:" style="width: 230px;margin-top: 20px">
          <el-select v-model="userInput.gender" placeholder="请选择用户性别" clearable >
            <el-option
                v-for="item in genderList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户邮箱:" style="margin-top: 20px;" >
          <el-input v-model="userInput.email" placeholder="请输入用户邮箱"  clearable style="width: 230px;margin-right: 10px;"/>
        </el-form-item>
        <el-button type="warning" @click="getAllUser">查询</el-button>
<!--        <el-button type="primary" @click="addActivity">添加</el-button>-->
      </el-form>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="userInfoList"
      style="width: 100%"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
    <el-table-column label="用户头像" width="100">
      <template #default="scope">
        <img :src="scope.row.userImage" alt="scope.row.userInfoList.userName" style="width: 70px;height: 70px">
      </template>
    </el-table-column>
    <el-table-column property="userName" label="用户名" width="80" />
    <el-table-column property="userGender" label="性别" width="70" />
    <el-table-column property="userIdcard" label="身份证号" width="180" />
    <el-table-column property="userTelephone" label="电话号" width="140" />
    <el-table-column property="userEmail" label="电子邮箱" width="180" />
    <el-table-column property="userAddress" label="用户住址" width="90" />
    <el-table-column property="userStatus" label="用户状态" width="80">
      <template #default="scope">
            <el-tag>{{ scope.row.userStatus }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="tools" label="操作" width="100">
      <template #default="scope">
        <el-button type="success" :disabled="scope.row.userStatus==='已激活'"  circle @click="userActive(scope.$index, scope.row)" ><el-icon><Check /></el-icon></el-button>
        <el-button type="warning" circle @click="updateShow(scope.$index, scope.row)"><el-icon><EditPen /></el-icon></el-button>
      </template>
    </el-table-column>

  </el-table>
  <!-- 修改弹窗 -->
  <el-dialog v-model="updateUserShow" title="用户信息修改">
    <el-form :model="updateUserList" ref="vForm" label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="用户姓名:" prop="userName">
        <el-input v-model="updateUserList.userName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="用户性别:" prop="gender">
        <el-select v-model="updateUserList.userGenderCoding" class="full-width-input" clearable>
          <el-option v-for="(item, index) in genderList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号:" prop="userIdcard">
        <el-input v-model="updateUserList.userIdcard" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="电话号码:" prop="userTelephone">
        <el-input v-model="updateUserList.userTelephone" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱:" prop="userEmail">
        <el-input v-model="updateUserList.userEmail" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="用户住址:" prop="userAddress">
        <el-input type="textarea" v-model="updateUserList.userAddress" rows="2"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateUserShow = false">取消</el-button>
        <el-button type="primary" @click="userInfoUpdate">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {
  activeUser,
  findActivityType,
  findAllGender,
  findAllRole,
  findAllUser,
  updateUserInfo
} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "User",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      //获取性别下拉框
      genderList:[],
      //获取所有用户信息
      userInfoList:[],
      userName:undefined,
      page:1,
      total:undefined,
      email:undefined,
      gender:undefined,

      //获取到的信息
      userImage:undefined,
      // userName:undefined,
      userGender:undefined,
      userIdcard:undefined,
      userTelephone:undefined,
      userEmail:undefined,
      userAddress:undefined,
      userStatus:undefined,
      //条件查找
      userInput:[],
      //修改
      updateUserShow:false,
      updateUserList:[],
      userGenderCoding:undefined,
    }
  },
  methods:{
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
    //信息获取
    getAllUser(){
      let para = {
        userName:this.userInput.userName===''?undefined:this.userInput.userName,
        page:this.page,
        email:this.userInput.email===''?undefined:this.userInput.email,
        gender:this.userInput.gender===''?undefined:this.userInput.gender,
      }
      findAllUser(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.userInfoList = res.data.result.list;
          this.total=res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //信息修改部分
    //弹窗显示
    updateShow(index,row){
      this.updateUserShow=!this.updateUserShow;
      this.updateUserList = row;
    },
    userInfoUpdate(){
      let para ={
        userId:this.updateUserList.userId===''?undefined:this.updateUserList.userId,
        userName:this.updateUserList.userName===''?undefined:this.updateUserList.userName,
        idCard:this.updateUserList.userIdcard===''?undefined:this.updateUserList.userIdcard,
        gender:this.updateUserList.userGenderCoding===''?undefined:this.updateUserList.userGenderCoding,
        telephone:this.updateUserList.userTelephone===''?undefined:this.updateUserList.userTelephone,
        address:this.updateUserList.userAddress===''?undefined:this.updateUserList.userAddress,
        email:this.updateUserList.userEmail===''?undefined:this.updateUserList.userEmail,
      }
      updateUserInfo(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllUser();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //用户激活
    userActive(index,row){
      activeUser(row.userId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getAllUser();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    }
  },
  mounted() {
    this.getAllUser();
    this.getGender();
  }
}
</script>

<style scoped>

</style>