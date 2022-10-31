<template>
  <section>

    <header>
      <div class="floor-tools">
        <el-form :model="choose" label-width="120px" :inline="true">
          <el-form-item label="请选择楼层:">
            <el-select v-model="choose.floor" placeholder="请选择楼层" :disabled="choose.appTime!=''" clearable>
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
        <div class="screenArea"><h1>图书馆{{ choose.floor }}层{{ choose.areaId }}区</h1>
          <div class="userList">
            <div class="onlineNumber">
              在线人数:<span>{{userInformationList.length}}</span>人
            </div>
            <div class="userImageList" v-for="item in userInformationList" :key="item.id" >
              <img :src="item.userImage"/>
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
      <div class="Canvas">
        <canvas id="myCanvas"> 这是一个画布</canvas>
      </div>
      <div id="hint"></div>
    </div>
    <div v-if="!determineReservationDisplay">
      <div class="box">

      </div>
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
.onlineNumber span{
  font-size: 20px;
}
.userImageList img{
  height: 40px;
  width: 40px;
  border-radius: 50%;
  float: right;
}
</style>