<template>
<section>
  <div class="reservationWrap" v-if="loadingShow">
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
        <el-table-column prop="bookStatus" label="预定图书状态" /> >
        <el-table-column prop="bookStatus" label="操作" >
          <template #default="scope">
            <el-button type="warning" plain @click="updateBookShow(scope.$index, scope.row)" :disabled="scope.row.bookStatus==='取消预定' || scope.row.bookStatus==='已加入图书库' ">修改</el-button>
            <el-button type="danger" plain @click="canelReservationBook(scope.$index, scope.row)" :disabled="scope.row.bookStatus==='取消预定' || scope.row.bookStatus==='已加入图书库' ">撤销</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--  添加弹窗-->
    <el-dialog
        v-model="addReservation"
        title="图书预定"
        width="30%"
        :rules="rulesReservation"
    >
      <el-input v-model="ReservationBookName" placeholder="请输入图书名称"  clearable/>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addReservation = false">取消</el-button>
        <el-button type="primary" @click="addReservationBook" :disabled="ReservationBookName===undefined || ReservationBookName===''">
          预定
        </el-button>
      </span>
      </template>
    </el-dialog>
    <!--  修改弹窗-->
    <el-dialog
        v-model="updateReservation"
        title="图书预定信息修改"
        width="30%"
    >
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

<script>
import {
  addReservation,
  bookScheduled,
  canelReservation,
  findReservation,
  GetTypeList,
  updateBookReservation
} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";

export default {
  data(){
    return{
      testReservationList:[
          {
        scheduledId:'2637647',
        bookName:'鬓边不是海棠红',
        bookStatus:'取消预定',
      },{
          scheduledId:'2637347',
          bookName:'轻狂',
          bookStatus:'未加入书架',
        },{
          scheduledId:'2637377',
          bookName:'嚣张',
          bookStatus:'未找到图书',
        },
      ],
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
      ReservationBookName:undefined,
      scheduledId:undefined,
      bookName:'',
      bookStatus:'',

      updateInput:[],
      updateReservation:false,
      rulesReservation: {
        ReservationBookName: [
          {
            required: true,
            message: "请输入图书名称",
            trigger: "blur",
          },
        ],
      },
      loadingShow:false,
    }
  },
  methods:{
    //获取预定状态
    getStatusList(){
      bookScheduled(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookReservationStatusList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
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
          this.loadingShow = true;
        } else {
          ElMessage.error(res.data.message);
        }
      })

    },
    //添加弹窗
    addBookShow(){
      this.addReservation=!this.addReservation;
    },
    //添加预定信息
    addReservationBook(){
        this.bookName = this.ReservationBookName;
        addReservation(this.bookName,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          // alert(111);
          this.findReservationBook();
          this.addReservation = false;
        } else {
          ElMessage.error(res.data.message);
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
          // alert(111);
          this.findReservationBook();
          this.updateReservation = false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //撤销预定
    canelReservationBook(index,row){
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        let reserveId = row.scheduledId;
      canelReservation(reserveId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          // alert(111);
          this.findReservationBook();
          this.updateReservation = false;
        } else {
          ElMessage.error(res.data.message);
        }
      })
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
  height: 600px;
  margin: 0 auto;
}
.reservationTool{
  margin-left: 250px;
  margin-top: 30px;
  margin-bottom: 30px;
  height: 30px;
  width: 800px;
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
@keyframes animate {
  0% {
    transform: translateY(160px);
    /* hue-rotate是颜色滤镜，可以设置不同的度数来改变颜色 */
    filter: hue-rotate(0deg);
  }
  50% {
    transform: translateY(0px);
    filter: hue-rotate(180deg);
  }
  100% {
    transform: translateY(160px);
    filter: hue-rotate(360deg);
  }
}
</style>