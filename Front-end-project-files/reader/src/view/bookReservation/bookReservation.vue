<template>
<section>
  <div class="reservationTool">
    <el-form :inline="true" :model="reservationInput" class="demo-form-inline">
      <el-form-item label="图书名称">
        <el-input v-model="reservationInput.bookName" placeholder="请输入图书名称" clearable/>
      </el-form-item>
      <el-form-item label="预定状态">
        <el-select v-model="reservationInput.bookStatus"  placeholder="请输入选择状态" clearable>
          <el-option v-for="item in bookReservationStatusList" :label="item.value" :value="item.coding" :key="item.coding"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="findReservationBook">查找</el-button>
        <el-button type="primary" @click="addBookShow">添加</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="reservationShow">
    <el-table :data="bookReservationList" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
      <el-table-column prop="scheduledId" label="预定图书编号" width="280" />
      <el-table-column prop="bookName" label="预定图书名称" width="200" />
      <el-table-column prop="bookStatus" label="预定图书状态"  />
      <el-table-column prop="bookStatus" label="操作" >
        <template #default="scope">
        <el-button type="warning" @click="updateBookShow(scope.$index, scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
<!--  添加弹窗-->
  <el-dialog
      v-model="addReservation"
      title="图书预定"
      width="30%"
  >
    <el-input v-model="addInput.bookName" placeholder="请输入图书名称" clearable/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addReservation = false">取消</el-button>
        <el-button type="primary" @click="addReservationBook">
          预定
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  修改弹窗-->
  <el-dialog
      v-model="updateReservation"
      title="图书预定"
      width="30%"
  >
    <el-input v-model="updateInput.scheduledId" placeholder="请输入图书Id" clearable/>
    <el-input v-model="updateInput.bookName" placeholder="请输入图书名称" clearable/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateReservation = false">取消</el-button>
        <el-button type="primary" @click="updateReservationBook">
          修改
        </el-button>
      </span>
    </template>
  </el-dialog>
</section>
</template>

<script>
import {addReservation, bookScheduled, findReservation, GetTypeList, updateBookReservation} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      configs:{
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      },
      addReservation:false,
      reservationInput:[{
        bookName:'',
        bookStatus:'',
      }],
      bookReservationList:[],
      bookReservationStatusList:[
        {
          coding:'',
          value:'',
        }
      ],
      addInput:[],
      scheduledId:undefined,
      bookName:'',
      bookStatus:'',

      updateInput:[],
      updateReservation:false,

    }
  },
  methods:{
    //获取预定状态
    getStatusList(){
      bookScheduled(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookReservationStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //查找预定图书信息
    findReservationBook(){
      let bookName = this.reservationInput.bookName===undefined ? '' : this.reservationInput.bookName;
      let bookStatus = encodeURI(encodeURI(this.reservationInput.bookStatus===undefined ? '' : this.reservationInput.bookStatus));
      findReservation(bookName,bookStatus,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookReservationList = res.data.result;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })

    },

    //添加弹窗
    addBookShow(){
      this.addReservation=!this.addReservation;
    },
    //添加预定信息
    addReservationBook(){
        this.bookName = this.addInput.bookName;
        addReservation(this.bookName,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          // alert(111);
          this.findReservationBook();
          this.addReservation = false;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    },


    //修改弹窗
    updateBookShow(index,row){
      this.updateReservation=!this.updateReservation;
      this.updateInput.scheduledId=row.scheduledId;
      this.updateInput.bookName=row.bookName;
      this.updateInput.bookStatus=row.bookStatus;
    },
    updateReservationBook(){
      let bookId = this.updateInput.scheduledId;
      // let bookName = encodeURI(encodeURI(this.updateInput.bookName));
      let bookName = this.updateInput.bookName;
      updateBookReservation(bookId,bookName,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          alert(111);
          this.findReservationBook();
          this.updateReservation = false;
        } else {
          ElMessage.error(res.data.msgId);
        }
      })
    }
  },
  mounted() {
    this.getStatusList();
    this.findReservationBook();
  }
}
</script>

<style scoped>
.reservationShow{
  width: 800px;
  margin: 0 auto;
}
</style>