<template>
  <section>
    <div class="seatShowPage" v-if="loadingShow">
      <header>
        <div class="floor-tools">
          <el-form :model="choose" label-width="120px" :inline="true">
            <el-form-item label="请选择楼层:">
              <el-select v-model="choose.floor" placeholder="请选择楼层" :disabled="choose.appTime!='' || !determineReservationDisplay" clearable>
                <el-option v-for="item in floors" :key="item.coding" :label="item.value" :value="item.coding"
                           @click="appTime=item.timeStart,endTime=item.timeEnd"/>
              </el-select>
            </el-form-item>
            <el-form-item label="请选择区域:">
              <el-select v-model="choose.areaId" placeholder="请选择区域"
                         :disabled="choose.appTime!='' ||choose.floor==''" clearable>
                <el-option v-for="item in areaList" :key="item.areaId" :label="item.areaId" :value="item.areaId"/>
              </el-select>
            </el-form-item>
            <el-form-item label="请选择时间:">
              <el-time-select :disabled="choose.floor==''||displayTimeapp ||choose.area==''"
                              v-model="choose.appTime"
                              :start=startTime
                              step="1:00"
                              :end=endTime
                              placeholder="请选择预约时间"
              />
            </el-form-item>
            <el-button class="chooseBtn" type="primary" :disabled="choose.floor==''||choose.appTime==''||displayTimeapp"
                       @click="getSeat()">确认
            </el-button>
          </el-form>
        </div>
      </header>
      <div v-if="determineReservationDisplay">
        <div class="seat" v-if="seatShow">
          <div class="screenArea"><h1>图书馆<span>{{ choose.floor }}</span>层<span>{{ choose.areaId }}</span>区</h1>
            <div class="userList">
              <div class="onlineNumber">
                在线人数:<span>{{userInformationList.length}}</span>人
              </div>
              <div class="userImageList" v-for="(item,index) in userInformationList" :key="index" >
                <img :src="item.userImage" v-if="index<5"/>
                <span v-if="index>=5" class="moreImage">...</span>
              </div>
            </div>
            <el-divider class="line" />
            <el-button type="primary" :disabled="!displaySeat" @click="seatBooking()">确认选座</el-button>
          </div>

          <span class="images" v-for="item in dataLists" :key="item.data">
        <img src="../assets/seat/unselected1.png" v-show="item.isSole==0 && !displaySeat" @click="choose1(item)">
        <img src="../assets/seat/selected.png" v-show="item.isSole==1 && displaySeat" @click="choose1(item)">
        <img src="../assets/seat/Appointmented.png" v-show="item.isSole==2">
        <img src="../assets/seat/sure.jpg" v-show="item.isSole==3 || (item.isSole==0 && displaySeat)">
        <img src="../assets/seat/hasPerson.jpg" v-show="item.isSole==4" class="imghasPerson">
            <!--         <div class="user">-->

         <img :src="item.userInformation.userImage" class="userImage" v-show="item.isSole==4"/>
        <span class="userName" v-show="item.isSole==4">{{ item.userInformation.userName }}</span>
            <!--         </div>-->
        <div v-if="item.x==xmax"><br/></div>
      </span>
        </div>
        <div class="seatMessage" v-if="!seatShow">
          <div class="InfoMessageTitle">
            <h2>选座提示</h2><br/>
          </div>
          <div class="InfoMessage">
            <span>如果想要选择座位，请先选择楼层区域和预约时间</span><br/>
          </div>
          <br/>
          <div class="messageImg">
            <img src="https://java-ljw.obs.cn-north-4.myhuaweicloud.com/librarySystem/error-email/borrow-email.gif">
          </div>
        </div>
      </div>
      <div v-if="!determineReservationDisplay" class="AppointmentHistory">
        <div class="Appointmenttitle"><h1>历史预约</h1></div>
        <div class="AppointmentInfo">
          <el-table :data="historyList" style="width: 100%" :header-cell-style="{'text-align':'center'}">
            <el-table-column prop="appointmentId" label="预约编号" width="100" align="center"/>
            <el-table-column prop="floorName" label="楼层名称" width="100" align="center"/>
            <el-table-column prop="appointmentsStatus" label="预约当前状态" width="150" align="center">
            </el-table-column>
            <el-table-column prop="appointmentTime" label="预约时间" width="205" align="center"/>
            <el-table-column prop="appointmentsCreateTime" label="预约创建时间" width="205" align="center"/>
            <el-table-column prop="tools"  label="操作" width="100px">
              <template #default="scope">
                <el-button type="primary" size="small" @click="canelSeat(scope.$index, scope.row)" :disabled="canelDisable(scope.$index, scope.row)">撤销预约</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination style="margin-left: 350px" layout="prev, pager, next" :total="total" :page-size="pageSize" v-model:currentPage="page"  @current-change="pageChange" />
        </div>
      </div>
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
  </section>
</template>
<script src="./chooseSeat/chooseSeatJs.js">
</script>

<style scoped>
.images {
  margin-left: 20px;
  position: relative;
}

.seat {
  margin-left: 250px;
  margin-top: 60px;
  z-index: 10;
  height: 800px;
}

.screenArea {
  margin-left: 350px;
  margin-top: 30px;
  margin-bottom: 20px;
}
.floor-tools{
  margin-top: 50px;
}
.userName {
  height: 5px;
  width: 48px;
  font-size: 2px;
  text-align: center;
  /*z-index: 7;*/
  position: absolute;
  left: 3px;
  top: -8px;
  display: none;
  color: #0c03ee;
}

.userImage {
  height: 15px;
  width: 15px;
  /*z-index: 7;*/
  position: absolute;
  top: -25px;
  left: 15px;
  display: none;
}

.seat:hover .userName {
  display: inline;
}

.seat:hover .userImage {
  display: inline;
}

.screenArea h1 {
  /*position: absolute;*/
  margin-left: -20px;
  font-family: "楷体","楷体_GB2312";
}
.screenArea span{
  color: crimson;
}
.line{
  margin-left: -300px;
}
.chooseBtn{
  margin-top: -22px;
}
.userList{
  margin-left: 420px;
  margin-top: -40px;
  width: 200px;
  height: 100px;
  /*background-color: #b5b9bb;*/
  float: right;
  position: absolute;
}
.onlineNumber{
  font-family: "楷体","楷体_GB2312";
}
.onlineNumber span{
  font-size: 30px;
  color: crimson;
}
.userImageList img{
  height: 40px;
  width: 40px;
  border-radius: 50%;
  float: left;
  margin-left: -10px;
  margin-top: 5px;
}
.moreImage{
  font-size: 20px;
  margin-bottom: 10px;
  margin-left: 20px;
}
.AppointmentHistory{
  margin-left: 180px;
  margin-right: 220px;
  margin-top: 30px;
  /*border: 1px red solid;*/
}
.Appointmenttitle{
  margin-left: 350px;
}
.AppointmentInfo{
  text-align: center;
  margin-top: 20px;
}
.seatMessage{
  /*height: 300px;*/
  width: 620px;
  margin-left: 300px;
  text-align: center;
  margin-top: 30px;
}
.seatMessage .InfoMessageTitle{
  color: #fb6b6b;
}
/*加载*/

*{
  /* 初始化 取消页面内外边距 */
  margin: 0;
  /*padding: 0;*/
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
  margin-left: 400px;
  margin-top: 100px;
}
.loading div{
  position: relative;
  width: 40px;
  height: 200px;
  /* 渐变背景 */
  background: linear-gradient(to bottom,rgba(0,0,0,0.05),#eef1f5);
  margin: 20px;
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
</style>