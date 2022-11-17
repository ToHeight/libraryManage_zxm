<template>
  <div class="header-wrap">
    <el-menu :default-active="activeIndex2" active-text-color="#ffd04b" background-color="#545c64"
             class="el-menu-demo" mode="horizontal" text-color="#fff" @select="handleSelect" :router="true">
      <el-menu-item>
        <img alt="logo" class="logo-image" src="../assets/logo.png">
      </el-menu-item>
      <el-menu-item>
        <h2 class="title">城南图书馆</h2>
      </el-menu-item>
      <el-menu-item index="/home" ><h3 class="smallTitle">首页</h3></el-menu-item>
      <el-menu-item index="/book"><h3 class="smallTitle">图书大全</h3></el-menu-item>
      <el-menu-item index="/bookReservation"><h3 class="smallTitle">图书预定</h3></el-menu-item>
      <el-menu-item index="/chooseSeat"><h3 class="smallTitle">入馆预约</h3></el-menu-item>
      <el-menu-item index="/bookShelf"><h3 class="smallTitle">个人书架</h3></el-menu-item>
      <el-menu-item index="/bookBorrow"><h3 class="smallTitle">借阅信息</h3></el-menu-item>
      <div class="user-image" >
        <el-tooltip
            class="box-item"
            effect="dark"
            content="活动中心"
            placement="bottom-start"
        >
        <span @click="this.activityShowView();this.loadingShow=false;"><el-icon  class="activityIcon" size="40px" color="#fff"><StarFilled /></el-icon></span>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="light"
            content="预约历史"
            placement="bottom-start"
        >
        <span @click="this.infoShowView();this.loadingShow=false;"><el-icon class="infoIcon" size="40px" color="#fff"><Postcard /></el-icon></span>
        </el-tooltip>
        <el-avatar :size="50" :src="this.userImageCoding" />
      </div>
      <el-sub-menu class="user">
        <template #title><h3>个人中心</h3></template>
        <el-menu-item index="/personInfo"><h4>个人信息</h4></el-menu-item>
        <el-menu-item v-if="userLogin"><h4 @click="logOut">退出登录</h4></el-menu-item>
        <el-menu-item v-if="!userLogin"><h4 @click="dialogVisibleLogin=true">用户登录</h4></el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
<!--  活动弹窗-->
  <el-dialog v-model="activityDisplay" title="活动中心" class="activityPlace" width="90%" >
    <div class="activityShowPage" v-if="loadingShow">
      <div class="selectActivity">
        <el-form :inline="true" :model="activityInput" class="demo-form-inline">
          <el-form-item label="活动名称:">
            <el-input v-model="activityInput.activityName" placeholder="请输入活动名称" style="width: 150px" clearable/>
          </el-form-item>
          <el-form-item label="举办方:">
            <el-input v-model="activityInput.activityOrganizer" placeholder="请输入活动举办方" style="width: 150px" clearable/>
          </el-form-item>
          <el-form-item label="活动状态:">
            <el-select v-model="activityInput.activityStatus" placeholder="请选择活动状态" style="width: 150px" clearable>
              <el-option
                  v-for="item in activityStatusList"
                  :key="item.value"
                  :label="item.value"
                  :value="item.coding"
              >{{item.value}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="活动类型:">
            <el-select v-model="activityInput.activityType" placeholder="请选择活动种类" style="width: 150px" clearable>
              <el-option
                  v-for="item in activityTypeList"
                  :key="item.value"
                  :label="item.value"
                  :value="item.coding"
                  clearable
              >{{item.value}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="activityFind">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="activityTools">
        <el-button type="warning" @click="this.itselfByUser=true;this.activityFind();this.activityInfo='';this.activityButton=false;this.infoShow=false" v-if="!this.itselfByUser">已报名活动</el-button>
        <el-button type="primary" @click="this.itselfByUser=false;this.activityFind();this.activityInfo='';this.activityButton=false;this.infoShow=false;" v-if="this.itselfByUser">所有活动</el-button>
        <el-button type="danger" @click="deleteActivity;this.infoShow=false" :disabled="this.itselfByUser">删除</el-button>
      </div>
      <el-tabs type="border-card">
        <el-tab-pane label="活动">
          <el-row :gutter="15">
            <el-col :span="6">
              <el-table :data="activityList"  :show-header=false :highlight-current-row=true @selection-change="selsChange">
                <el-table-column type="selection" v-if="!this.itselfByUser"/>
                <el-table-column prop="activityName">
                  <template #default="scope">
                    <div @click="haveRead(scope.row);this.activityButton=true;this.infoShow=true">
                      {{scope.row.activityName}}
                      <el-icon class="haveRead" v-if="scope.row.read"><CircleCheck /></el-icon>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
            <el-col :span="9">
              <!--      卡片-->
              <div class="activityInfo" v-if="infoShow">
                <div class="showOrganizer" style="margin-bottom: 10px">
                  活动举办方:<span v-for="organizer in activityInfo.activityOrganizer" :key="organizer">{{organizer}}&nbsp;&nbsp;</span>
                </div>
                <div class="showInfo" style="margin-bottom: 10px">
                  <span>活动信息:</span>
                  <div>{{activityInfo.activityInfo}}</div>
                </div>
                <div class="showType" style="margin-bottom: 10px">
                  <span>活动种类:</span>{{activityInfo.activityTypeName}}
                </div>
                <div class="applicationTime" style="margin-bottom: 10px">
                  <span>报名时间:</span>{{activityInfo.activityApplication}}
                </div>
                <div class="activityDate" style="margin-bottom: 10px">
                  <span>举办时间:</span>{{activityInfo.activityDate}}
                </div>
                <div class="canelActivity">
                  <el-button type="primary"  v-if="!this.itselfByUser&&canelShow&&this.activityButton" @click="registrationActivity" >报名参与</el-button>
                  <el-button type="danger"  v-if="this.itselfByUser&&canelShow&&this.activityButton" @click="canelApplication" >取消报名</el-button>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
      <template>
        <div class="dialog-footer">
          <el-button @click="activityDisplay = false">Cancel</el-button>
        </div>
      </template>
    </div>
    <!--  加载界面-->
    <div class="loading" v-if="!loadingShow">
      <!-- 【--x是自定义属性，在后面样式中会用到】 -->
      <div style="--x:0"></div>
      <div style="--x:1"></div>
      <div style="--x:2"></div>
      <div style="--x:3"></div>
      <div style="--x:4"></div>
      <div style="--x:5"></div>
    </div>
  </el-dialog>

<!--  预约信息弹窗-->
  <el-dialog v-model="InfoDisplay" title="预约信息" class="InfoPlace" width="70%" >
    <div class="applicationWrap" v-if="loadingShow">
      <el-tabs type="border-card">
        <el-tab-pane label="历史预约">
          <el-table :data="historyList" :highlight-current-row=true  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
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
        <!--  分页-->
        <el-pagination small style="margin-left: 320px" layout="prev, pager, next" :total="total" :page-size="pageSize" v-model:currentPage="page"  @current-change="pageChange"/>
      </el-tabs>
      <template>
        <div class="dialog-footer">
          <el-button @click="InfoDisplay = false">Cancel</el-button>
        </div>
      </template>
    </div>
    <!--  加载界面-->
    <div class="loading" v-if="!loadingShow">
      <!-- 【--x是自定义属性，在后面样式中会用到】 -->
      <div style="--x:0"></div>
      <div style="--x:1"></div>
      <div style="--x:2"></div>
      <div style="--x:3"></div>
      <div style="--x:4"></div>
      <div style="--x:5"></div>
    </div>
  </el-dialog>

  <!--    //登录-->
  <el-dialog
      v-model="dialogVisibleLogin"
      title="登录"
      width="30%"
  >
    <el-form ref="ruleFormRef" :model="loginForm" status-icon :rules="rules" label-width="80px"
             class="demo-ruleForm">
      <el-form-item label="登录名:" prop="userLoginName" class="userLoginName">
        <el-input v-model="loginForm.userLoginName" type="text" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码:" prop="password" class="loginPassword">
        <el-input v-model="loginForm.password" type="password" autocomplete="off"/>
      </el-form-item>
      <el-form-item class="login-button">
        <el-button type="primary" @click="submitLogin">登录</el-button>
        <el-button type="primary" @click="registerUser">注册</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {
  activityRegistration,
  cancelAppointment,
  findActivity, getActivityStatus, getActivityType,
  historyAppointment, loginByReader, registrationCanel,
  updateBookReservation
} from "@/api/zxmLibrary";
import {ElMessage, ElMessageBox} from "element-plus";
import {privateKey, publicKsy} from "@/api/keyJSEncrypt";

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
      //分页
      page:1,
      pageSize:5,
      total:0,
      //活动
      infoShow:false,
      activityName:undefined,
      activityOrganizer: undefined,
      activityTypeId:undefined,
      activityStatus:undefined,
      activityTypeName:undefined,
      activityApplication:undefined,
      activityDate:undefined,
      itself:true,
      notShowActivityId:undefined,
      activityId:undefined,
      readId:[],
      sels:[],
      //活动条件查询
      activityInput:[],
      activityTypeList:[],
      activityStatusList:[],
      activityType:undefined,
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
      userImageCoding:undefined,
      userName:undefined,
      canelShow:false,
      loadingShow:false,
      activityButton:false,

      userLogin:true,
      dialogVisibleLogin:false,
      loginForm:{},
      rules: {
        userLoginName: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur"
          },
        ],
      },
      userLoginName:undefined,
      password:undefined,


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
      this.canelShow=true;
      row.activityOrganizer=JSON.parse(row.activityOrganizer)
      this.activityInfo=row;
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
      let para=null;
      if(this.itselfByUser){
         para = {
          page:this.page,
          pageSize:this.pageSize,
          activityName:this.activityInput.activityName===''?undefined:this.activityInput.activityName,
          activityOrganizer:this.activityInput.activityOrganizer===''?undefined:this.activityInput.activityOrganizer,
          activityTypeId:this.activityInput.activityTypeId===''?undefined:this.activityInput.activityTypeId,
          activityStatus:this.activityInput.activityStatus===''?undefined:this.activityInput.activityStatus,
          itself:this.itselfByUser,
        };
      }else{
         para = {
          page:this.page,
          pageSize:this.pageSize,
           activityName:this.activityInput.activityName===''?undefined:this.activityInput.activityName,
           activityOrganizer:this.activityInput.activityOrganizer===''?undefined:this.activityInput.activityOrganizer,
           activityTypeId:this.activityInput.activityTypeId===''?undefined:this.activityInput.activityTypeId,
           activityStatus:this.activityInput.activityStatus===''?undefined:this.activityInput.activityStatus,
          itself:this.itselfByUser,
          notShowActivityId:eval(localStorage.getItem("activityDeleteId"))
        };
      }
      findActivity(para,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.activityList = res.data.result.list;
          // let OrList = JSON.parse(this.activityList.activityOrganizer);
          // alert(OrList);
          this.readActivity(this.activityList);
          this.loadingShow = true;

        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //活动类型获取
    activityTypeGet(){
      getActivityType(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityTypeList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //活动状态获取
    activityStatusGet(){
      getActivityStatus(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.activityStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //活动信息删除
    //批量删除
    selsChange(sels) {
      this.sels = sels;
    },
    deleteActivity(){
      this.notShowActivityId=this.sels.map(item => item.activityId);
      localStorage.setItem("activityDeleteId",JSON.stringify(this.notShowActivityId));
      this.activityFind();
    },
    //活动报名
    registrationActivity(){
      this.activityId = this.activityInfo.activityId;
        activityRegistration(this.activityId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //活动撤销
    canelApplication(){
      this.applicationId = this.activityInfo.applicationId;
      registrationCanel(this.applicationId,this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },

    //信息弹窗
    infoShowView(){
      this.InfoDisplay = !this.InfoDisplay;
      this.apponitmentHistory();
    },
    //用户信息获取

    //分页
    pageChange(val){
      this.page = val;
      this.apponitmentHistory();
    },
    //预约历史
    apponitmentHistory(){
      // alert(this.page)
      historyAppointment(this.page,this.configs).then((res) => {
        if (res.data.statusCode == 'C404') {
          ElMessage.error(res.data.message);
        } else if (res.data.statusCode == 'C200') {
          this.historyList = res.data.result.list;
          this.total=res.data.result.total;
          this.loadingShow=true;
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
            this.apponitmentHistory();
            location.reload();
          } else {
            ElMessage.error(res.data.message);
          }
        })
    },

    //登出
    logOut(){
      sessionStorage.clear();
      localStorage.clear();
      this.$router.replace('/login');
    },

    //登录
    submitLogin() {
      this.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
          sessionStorage.removeItem('AppointToken');
          loginByReader(this.loginForm).then((res) => {
            if (res.data.statusCode === 'C200') {
              sessionStorage.setItem("AppointToken", res.headers['token'])
              ElMessage.success(res.data.message);
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              let PRdata = publicKsy(JSON.stringify(res.data.result))
              let userImage = res.data.result.userImage;
              localStorage.setItem("userInformationImg", userImage)
              res.data.result.userImage = null;
              localStorage.setItem("userInformation", publicKsy(JSON.stringify(res.data.result)))
              this.loginDisplay=false;
              this.dialogVisibleLogin=false;
              this.configs={
                headers:{
                  token: sessionStorage.getItem('AppointToken')
                }
              };
              location.reload();
            } else {
              ElMessage.error(res.data.message);
            }
          })
        } else {
          ElMessage.error("登录失败");
          return false;
        }
      });
    },

    //注册
    registerUser(){
      this.$router.replace('/register')
    }

  },
  mounted() {
    this.activityTypeGet();
    this.activityStatusGet();
    // this.haveActivityFind();
    this.readId=JSON.parse(localStorage.getItem("activityReadId"));
    this.userImageCoding=localStorage.getItem("userInformationImg");
    this.loadingShow=false;
    if (sessionStorage.getItem('AppointToken') == null){
      this.userLogin=false;
    }else{
      this.userLogin=true;
    }
    if (sessionStorage.getItem('AppointToken') == null) {
      ElMessageBox.confirm(
          '当前并未登录，是否登录?',
          '提示',
          {
            confirmButtonText: '立即登录',
            cancelButtonText: '暂不登录',
            type: 'warning',
            center: true,
          }
      )
          .then(() => {
            //立即登录
            this.dialogVisibleLogin = true;
          })
          .catch(() => {
            this.loginDisplay = true;
            ElMessage({
              type: 'warning',
              message: '无法使用图书馆系统的功能~',
            })
          })
    }else{
      this.loginDisplay=false;
      this.configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
    }
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
  width: 30px;
}
.smallTitle{
  margin-left: 5px;
  text-align: center;
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
  position: relative;
}
.activityIcon{
  right: 15px;
  box-shadow: 0 10px 10px #484747;
  bottom: 5px;
}
.activityIcon:active{
  color: #ffd04b;
}
.infoIcon{
  right: 10px;
  box-shadow: 0 10px 10px #484747;
  bottom: 5px;
}
.infoIcon:active{
  color: #ffd04b;
}
.activityTools{
  margin-bottom: 5px;
}

/*加载*/

*{
  /* 初始化 取消页面内外边距 */
  margin: 0;
  padding: 0;
  /* 这个是告诉浏览器：你想要设置的边框喝内边距的值是包含在总宽高内的 */
  box-sizing: border-box;
}
body{
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平、垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #eef1f5;
}
.loading{
  display: flex;
  /* 水平显示 */
  flex-direction: row;
  margin-left: 300px;
  margin-top: 20px;
}
.loading div{
  position: relative;
  width: 20px;
  height: 50px;
  /* 渐变背景 */
  background: linear-gradient(to bottom,rgba(0,0,0,0.05),#eef1f5);
  margin: 10px;
  border-radius: 20px;
  border: 2px solid #eef1f5;
  /* 阴影 */
  box-shadow: 15px 15px 20px rgba(0,0,0,0.1),
  -15px -15px 20px #fff,
  inset -5px -5px 5px rgba(255,255,255,0.5),
  inset 5px 5px 5px rgba(0,0,0,0.05);
  /* 溢出隐藏 */
  overflow: hidden;
}
.loading div::before{
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  box-shadow: inset -5px -5px 5px rgba(0,0,0,0.1),
  0 420px 0 400px lightskyblue;
  /* 执行动画：动画名称 时长 加速后减速 无限次播放 */
  animation: animate 2s ease-in-out infinite;
  /* 动画延迟 通过var函数获取自定义属性--x，再由calc函数计算得出每个元素的动画延迟时间 */
  animation-delay: calc(var(--x) * -0.3s);
  /* 初始化时先向上移动160px */
  transform: translateY(160px);
}
/* 定义动画 */
@keyframes animate{
  0%{
    transform: translateY(160px);
    /* hue-rotate是颜色滤镜，可以设置不同的度数来改变颜色 */
    filter: hue-rotate(0deg);
  }
  50%{
    transform: translateY(0px);
    filter: hue-rotate(180deg);
  }
  100%{
    transform: translateY(160px);
    filter: hue-rotate(360deg);
  }
}

/*分页*/
.appointmentPage{
  margin-left: 300px;
}
</style>
