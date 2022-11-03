<template>
  <div class="header-wrap">
    <el-menu :default-active="activeIndex2" active-text-color="#ffd04b" background-color="#545c64"
             class="el-menu-demo" mode="horizontal" text-color="#fff" @select="handleSelect" :router="true">
      <el-menu-item>
        <img alt="logo" class="logo-image" src="../assets/logo.png">
      </el-menu-item>
      <el-menu-item>
        <h2 class="title">城北图书馆</h2>
      </el-menu-item>
      <el-menu-item index="/home" ><h3>首页</h3></el-menu-item>
      <el-sub-menu index="2">
        <template #title><h3>藏书馆</h3></template>
        <el-menu-item index="/book"><h4>图书大全</h4></el-menu-item>
        <el-menu-item index="/bookReservation"><h4>图书预定</h4></el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/chooseSeat"><h3>入馆预约</h3></el-menu-item>
      <el-menu-item index="/bookShelf"><h3>个人书架</h3></el-menu-item>
      <el-menu-item index="5"><h3>读者论坛</h3></el-menu-item>
      <!-- 消息 -->
      <div class="btn-bell">
        <!--            <router-link to="/currentAppointment">-->
        <i class="el-icon-bell"></i>
        <!--            </router-link>-->
      </div>
      <div class="user-image">
        <span @click="activityShowView"><el-icon class="activityIcon" size="40px" color="#fff"><StarFilled /></el-icon></span>
        <span @click="infoShowView"><el-icon class="infoIcon" size="40px" color="#fff"><Postcard /></el-icon></span>
<!--        <span class="userInfoName">{{userList.userName}}</span>-->
        <el-avatar :size="50" :src="circleUrl" />
      </div>
      <el-sub-menu class="user" index="7">
        <template #title><h3>个人中心</h3></template>
        <el-menu-item index="/bookBorrow"><h4>借阅信息</h4></el-menu-item>
        <el-menu-item index="7-4"><h4>退出登录</h4></el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
<!--  活动弹窗-->
  <el-dialog v-model="activityDisplay" title="活动中心" class="activityPlace" width="70%" >
    <div class="activityTools">
      <el-button type="success" @click="this.itselfByUser=true;this.activityFind()" v-if="!this.itselfByUser">已报名活动</el-button>
      <el-button type="primary" @click="this.itselfByUser=false;this.activityFind()" v-if="this.itselfByUser">所有活动</el-button>
      <el-button type="danger" @click="deleteActivity">删除</el-button>
    </div>
    <el-tabs type="border-card">
      <el-tab-pane label="活动">
        <el-row :gutter="15">
          <el-col :span="6">
            <el-table :data="activityList" v-loading="loading" :show-header=false :highlight-current-row=true @selection-change="selsChange">
              <el-table-column type="selection" />
              <el-table-column prop="activityName">
                <template #default="scope">
                  <div @click="haveRead(scope.row)">
                    {{scope.row.activityName}}
                    <el-icon class="haveRead" v-if="scope.row.read"><CircleCheck /></el-icon>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="9">
            <!--      卡片-->
            <div class="activityInfo">
              <div class="showOrganizer">
                {{activityInfo.activityOrganizer}}
              </div>
              <div class="showInfo">
                {{activityInfo.activityInfo}}
              </div>
              <div class="showType">
                {{activityInfo.activityTypeName}}
              </div>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="activityDisplay = false">Cancel</el-button>
      </div>
    </template>
  </el-dialog>

<!--  个人信息和预约信息弹窗-->
  <el-dialog v-model="InfoDisplay" title="信息" class="InfoPlace" width="70%" >
    <el-tabs type="border-card">
      <el-tab-pane label="个人信息">
        <el-table :data="userList" :highlight-current-row=true  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
          <el-table-column type="selection" />
          <el-table-column prop="userName" label="用户姓名" width="100px"/>
          <el-table-column prop="appointmentsStatus" label="用户年龄" width="100px" />
          <el-table-column prop="appointmentTime" label="用户性别" width="200px"/>
          <el-table-column prop="appointmentsCreateTime" label="居住地址" width="200px"/>
          <el-table-column prop="tools"  label="操作" width="100px">
            <template #default="scope">
              <el-button type="primary" size="small" @click="canelSeat(scope.$index, scope.row)" :disabled="canelDisable(scope.$index, scope.row)">撤销预约</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="历史预约">
        <el-table :data="historyList" :highlight-current-row=true  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
          <el-table-column type="selection" />
          <el-table-column prop="floorName" label="预约楼层" width="100px"/>
          <el-table-column prop="appointmentsStatus" label="当前状态" width="100px" />
          <el-table-column prop="appointmentTime" label="预约时间" width="200px"/>
          <el-table-column prop="appointmentsCreateTime" label="预约创建时间" width="200px"/>
          <el-table-column prop="tools"  label="操作" width="100px">
            <template #default="scope">
              <el-button type="primary" size="small" @click="canelSeat(scope.$index, scope.row)" :disabled="canelDisable(scope.$index, scope.row)">撤销预约</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="InfoDisplay = false">Cancel</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import {cancelAppointment, findActivity, historyAppointment, updateBookReservation} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";
import {privateKey} from "@/api/keyJSEncrypt";

export default {
  data() {
    return {
      activityInfo:'',
      activityDisplay:false,
      activeIndex2:'',
      handleSelect:'',
      circleUrl:'',
      configs:{
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      },
      timeActivity:true,
      activityList:[],
      loading:true,
      haveActivityList:[],
      haveActivityName:undefined,
      page:1,
      pageSize:5,
      activityName:undefined,
      activityOrganizer: undefined,
      activityTypeId:undefined,
      activityStatus:undefined,
      itself:true,
      notShowActivityId:undefined,
      activityId:undefined,
      readId:[],
      sels:[],
      //信息
      InfoDisplay:false,
      historyList:[],
      appointmentId:'',
      floorName:'',
      appointmentsStatus:'',
      appointmentTime:'',
      appointmentsCreateTime:'',
      statusApponitment:false,
      itselfByUser:false,
      userList:[],
      userName:undefined,

    }
  },
  methods: {
    home(){
      this.$router.push('/home')
    },
    //活动
    //活动弹窗
    activityShowView(){
      this.activityDisplay = !this.activityDisplay;
      this.activityFind();
    },
    //已读未读功能实现
    haveRead(row){
      this.activityInfo=row;
      // this.organizerList=JSON.parse(this.activityInfo.organizer);
      //记录当前i
      for (let i = 0; i < this.readId.length; i++) {
        if(this.readId[i]===row.activityId){
          return;
        }
      }
      this.readId.push((row.activityId))
      row.read=true;
      localStorage.setItem("activityReadId",JSON.stringify(this.readId));
    },
    readActivity(activityList){
      if(this.readId===undefined || this.readId==null){
        this.readId=[];
        return;
      }
      for (let i = 0; i <activityList.length; i++) {
        let dis=false;
        for (let j = 0; j < this.readId.length; j++) {
          if(activityList[i].activityId===this.readId[j]){
            dis=true;
            break;
          }
        }
        activityList[i].read = dis;
      }
    },
    //所有活动查询
    activityFind(){
      let para = {
        page:this.page,
        pageSize:this.pageSize,
        activityName:this.activityName,
        activityOrganizer:this.activityOrganizer,
        activityTypeId:this.activityTypeId,
        activityStatus:this.activityStatus,
        itself:this.itselfByUser,
        notShowActivityId:eval(localStorage.getItem("activityDeleteId"))
      };
      findActivity(para,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.activityList = res.data.result.list;
          this.readActivity(this.activityList);
          // this.registrationTime(this.activityStatus);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //活动信息删除
    //批量删除
    selsChange(sels) {
      this.sels = sels;
    },
    deleteActivity(){
      // if(this.notShowActivityId===undefined || this.notShowActivityId==null){
      //   this.notShowActivityId=[];
      //   return;
      // }
      this.notShowActivityId=this.sels.map(item => item.activityId);
      localStorage.setItem("activityDeleteId",JSON.stringify(this.notShowActivityId));
      this.activityFind();
    },
    // notShowActivityId(不显示活动id 类型：数组)
    // var ids = this.sels.map(item => item.cost_id);


//信息弹窗
    infoShowView(){
      this.InfoDisplay = !this.InfoDisplay;
      this.apponitmentHistory();
    },
    //用户信息获取
    userListGet(){
      this.userList=JSON.parse(privateKey(localStorage.getItem("userInformation")));
    },
    //预约历史
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
    },
    //撤销按钮
    canelDisable(index,row){
      this.statusApponitment = row.appointmentsStatus === '预约已结束'||row.appointmentsStatus === '预约撤回';
      return this.statusApponitment;
    },

    //座位撤销
    canelSeat(index,row){
        // let bookName = encodeURI(encodeURI(this.updateInput.bookName));
        this.appointmentId = row.appointmentId
        cancelAppointment(this.appointmentId,this.configs).then((res) => {
          if (res.data.statusCode === 'C200') {
            // alert(111);
            this.apponitmentHistory();
          } else {
            ElMessage.error(res.data.message);
          }
        })
    },

  },
  mounted() {
    // this.haveActivityFind();
    this.readId=JSON.parse(localStorage.getItem("activityReadId"));
    this.userListGet();
  }
}
</script>

<style scoped>
.header-wrap {
  position: relative;
}

.logo-image {
  height: 50px;
  width: 50px;
  margin-top: 3px;
  margin-left: 20px;
  margin-right: 10px;
}

.title {
  margin-right: 80px;
}

.user-image {
  position: absolute;
  right: 180px;
  margin-top: 3px;
}

.user {
  position: absolute;
  right: 50px;
}
.activityInfo{
  height: 100%;
  float: left;
}
.activityIcon{
  right: 15px;
  box-shadow: 0 10px 10px #484747;
  bottom: 5px;
}
.infoIcon{
  right: 10px;
  box-shadow: 0 10px 10px #484747;
  bottom: 5px;
}
.activityTools{
  margin-bottom: 5px;
}
</style>
