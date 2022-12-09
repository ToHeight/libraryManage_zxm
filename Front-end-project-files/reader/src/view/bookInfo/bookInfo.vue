<template>
  <section>
    <div class="allWrap">
      <div class="bookInfoWrap">
        <div class="bookInfoImage">
          <img :src="bookInfo.bookImage" :alt="bookInfo.bookName">
        </div>
        <div class="bookInfoTitle">
          <div class="show-icon1" v-if="bookInfo.bookStatus==='可借阅'">{{ bookInfo.bookStatus }}</div>
          <div class="show-icon2" v-if="bookInfo.bookStatus==='不可借阅'">{{ bookInfo.bookStatus }}</div>
          <div class="show-signal"></div>
          <div class="bookInfoName">
            <span>图书名称:</span>{{ bookInfo.bookName }}
          </div>
          <div class="bookInfoAuthor">
            <span>图书作者:</span>{{ bookInfo.author }}<span>(</span>{{ bookInfo.country }}<span>)</span>
          </div>
        </div>
        <div class="bookInfoMore">
          <div class="bookInfoType">
            <span>图书种类:</span>&nbsp;
            <el-tag class="mx-1" type="success" :disable-transitions="false" style="margin-right: 5px;">
              {{ bookInfo.typeName }}
            </el-tag>
          </div>
          <div class="bookInfoLanguage">
            <span>图书语言:</span>&nbsp;
            <el-tag class="mx-1" type="success" :disable-transitions="false" style="margin-right: 5px;">
              {{ bookInfo.bookLanguage }}
            </el-tag>
          </div>
        </div>
        <div class="bookInfoTags">
          <span>图书标签:</span>&nbsp;
          <el-tag v-for="tag in tagList " :key="tag.tagId" class="mx-1" :disable-transitions="false"
                  style="margin-right: 5px">
            {{ tag.tagName }}
          </el-tag>
        </div>
        <div class="bookInfoAddress">
          <span>图书位置:</span>&nbsp;
          {{ bookInfo.address }}
        </div>
        <div class="bookInfoStar">
          <el-rate
              v-model="bookInfo.bookStar"
              disabled
              show-score
              size="large"
              text-color="#ff9900"
              score-template="{value} 分"
          />
        </div>
        <div class="bookInfoContent">
          {{ bookInfo.bookInfo }}
        </div>
        <div class="booInfoTools">
          <el-button type="primary" plain @click="addBookShelf(bookInfo.bookId)" :disabled="loginDisplay">加入书架
          </el-button>
          <el-button type="warning" plain :disabled="bookInfo.bookStatus==='不可借阅' ||loginDisplay"
                     @click="borrowBook(bookInfo)">借阅
          </el-button>
          <el-popover placement="bottom" :width="250" trigger="click">
            <template #reference>
              <el-button class="bookInfoShare" @click="infoShare"
                         style="background-color: #b9b8b8;height: 37px;width: 37px" circle>
                <el-icon size="23px">
                  <Share/>
                </el-icon>
              </el-button>
            </template>
            <div class="shareShow">
              <div id="qrcode" class="qrcode" ref="qrcode"></div>
              <div class="link">
                <div style="font-weight: bold;font-size: 18px">复制链接:</div>
                {{shareLink}}
              </div>
            </div>
          </el-popover>
          <!--        <el-icon circle class="bookShare" size="30px"><Share /></el-icon>-->
        </div>
      </div>
      <div class="commentWrap" v-if="commentList.length!==0">
        <div class="commentTitle">
          <h2 style="margin-left: 520px;margin-top: 10px">图书评论</h2>
        </div>
        <div class="commentBox">
          <div class="commentShow" v-for="comment in commentList" :key="comment.userName">
            <div class="commentUser">
              <div class="commentImg">
                <el-avatar :size="50" :src="comment.userImage"/>
              </div>
              <div class="commentUserName">
                {{ comment.userName }}
              </div>
            </div>
            <div class="clear"></div>
            <div class="commentContent">
              {{ comment.commentsInfo }}
            </div>
            <el-divider style="width: 950px"></el-divider>
          </div>
          <el-pagination layout="prev, pager, next"
                         :total="commentTotal"
                         :page-size="3"
                         class="commentPage"
                         :current-page="commentPage"
                         @current-change="commentPageChange"/>
        </div>
      </div>
      <div class="recommendWrap">
        <h2 style="margin-left: 520px;margin-top: 10px">图书推荐</h2>
        <div class="recommendBox">
          <div class="recommendShow" v-for="item in recommendBookList" :key="item.bookId" @click="goToBook(item)">
            <div class="recommendImg">
              <img :src="item.bookImage" :alt="item.bookName">
            </div>
            <div class="recommendName">
              {{ item.bookName }}
            </div>
            <div class="recommendType">
              <span>图书种类:</span>{{ item.typeName }}
            </div>
            <div class="recommendStar">
              <el-rate
                  v-model="item.bookStar"
                  disabled
                  show-score
                  score-template="{value}分"
              />
            </div>
          </div>
          <div class="recommendPage">
            <el-pagination
                layout="prev, pager, next"
                :total="bookTotal"
                class="commentPage"
                v-model:currentPage="page"
                @current-change="recommendPageChange"/>
          </div>
        </div>
      </div>
    </div>
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
  </section>
</template>

<script>
import {addToShelf, bookBorrow, getBookInfo, getComments, loginByReader, recommendBook} from "@/api/zxmLibrary";
import {ElMessage, ElMessageBox} from "element-plus";
import QRCode from 'qrcodejs2'
import {publicKsy} from "@/api/keyJSEncrypt";

export default {
  name: "bookInfo",
  data() {
    return {
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
      loginForm: {},
      dialogVisibleLogin: false,
      loginDisplay: true,
      qrcodeNumber: 0,
      bookId: undefined,
      configs: null,
      bookInfo: [],
      tagList: [],
      total: 0,
      page: 1,
      commentList: [],
      commentsInfo: undefined,
      userImage: undefined,
      recommendBookList: [],
      bookTotal: undefined,
      commentTotal: undefined,
      commentPage: 1,
      shareLink:undefined,
      country:undefined,
    }
  },
  methods: {
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
    infoShare() {
      if (this.qrcodeNumber !== 0) return
      this.qrcodeNumber++;
      let qrcode = new QRCode('qrcode', {
        width: 100,  // 二维码宽度
        height: 100, // 二维码高度
        text: location.href
      })

      this.shareLink=location.href;

    },
    //获取对应图书信息
    getThisBookInfo() {
      getBookInfo(this.bookId).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookInfo = res.data.result;
          this.tagList = res.data.result.tagList;
          this.commentsGet();
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //获取评论信息
    commentsGet() {
      getComments(this.bookId, this.commentPage).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.commentList = res.data.result.list;
          this.commentTotal = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //评论分页
    commentPageChange(val) {
      this.commentPage = val;
      this.loadingShow = true;
      this.commentsGet();
    },
    //获取好书推荐
    goodBookGet() {
      recommendBook(this.page, this.bookId).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.recommendBookList = res.data.result.list;
          this.bookTotal = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },
    //好书分页
    recommendPageChange(val) {
      this.page = val;
      this.loadingShow = true;
      this.goodBookGet();
    },
    //书架添加
    addBookShelf(bookId) {
      addToShelf(bookId, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //借阅
    borrowBook(bookInfo) {
      let bookId = bookInfo.bookId;
      bookBorrow(bookId, this.configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    },

    //注册
    registerUser(){
      this.$router.replace('/register')
    },

    //详情跳转
    goToBook(item){
      // window.open('http://' + window.location.host + '/#/bookInfo/'+item.bookId,'_blank')
      this.$router.replace('/bookInfo/'+item.bookId);
      // location.reload();
    }
  },
  mounted() {
    let thst = this;
    this.bookId = thst.$route.params.bookId;
    // alert(this.bookId);
    this.getThisBookInfo();
    this.goodBookGet();
    //登陆提示
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
              message: '当前无法加入书架和借阅~',
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
.bookInfoWrap {
  width: 1200px;
  height: 500px;
  /*border: 1px red solid;*/
  margin-left: 35px;
  border-radius: 20px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  position: relative;
  float: left;
  margin-top: 10px;
}

.bookInfoImage {
  width: 300px;
  height: 400px;
  background-color: #507ea2;
  margin-top: 30px;
  margin-left: 20px;
  margin-right: 40px;
  /*border-radius: 20px;*/
  overflow: hidden;
  float: left;
  /* 防止被挤压 */
  flex-shrink: 0;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.2);
}

.bookInfoTitle {
  margin-top: 30px;
  font-family: "楷体", "楷体_GB2312";
}

.bookInfoTitle .bookInfoName {
  font-size: 35px;
  margin-bottom: 10px;
  font-weight: bolder;
}

.bookInfoTitle .bookInfoAuthor {
  font-size: 20px;
  margin-bottom: 10px;
}

.bookInfoMore {
  font-family: "楷体", "楷体_GB2312";
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.bookInfoType {
  margin-bottom: 5px;
}

.bookInfoTags {
  font-family: "楷体", "楷体_GB2312";
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.bookInfoAddress {
  font-family: "楷体", "楷体_GB2312";
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.bookInfoStar {
  margin-bottom: 5px;
}

.bookInfoContent {
  margin-right: 40px;
  margin-bottom: 20px;
  font-size: 14px;
}

.bookInfoShare {
  color: white;
  margin-left: 40px;
}

.qrcode {
  width: 100px;
  height: 100px;
  float: left;
  margin-right: 20px;
}

.link {
  width: 100px;
  height: 100px;
  /*background-color: aquamarine;*/
  float: left;
  font-size: 14px;
  text-decoration: solid deepskyblue 1px;
}

/*状态*/
.show-icon1 {
  text-align: center;
  background-color: #5aaafa;
  color: #fff;
  float: right;
  font-size: 28px;
  margin-right: -18px;
  padding: 2px 5px 2px 5px;
  width: 220px;
}

.show-icon2 {
  text-align: center;
  background-color: #c82350;
  color: #fff;
  float: right;
  font-size: 28px;
  margin-right: -18px;
  padding: 2px 5px 2px 5px;
  width: 220px;
}

.show-signal {
  border: 10px solid #939393;
  border-right: 9px solid transparent;
  border-bottom: 9px solid transparent;
  float: right;
  margin-right: -230px;
  margin-top: 35px;
  width: 0;
  height: 0;
}

/*评论*/
.commentWrap {
  width: 1200px;
  /*height: 300px;*/
  /*border: 1px red solid;*/
  margin-left: 35px;
  border-radius: 20px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  position: relative;
  float: left;
  margin-top: 20px;
}

.commentBox {
  margin-top: 20px;
}

.commentShow {
  margin-left: 100px;
}

.commentImg {
  float: left;
}

.commentUserName {
  margin-left: 10px;
  margin-top: 20px;
  float: left;
}

.clear {
  clear: both;
}

.commentContent {
  margin-top: 15px;
  margin-left: 50px;
  width: 850px;
}

.commentPage {
  margin-left: 540px;
  margin-top: 10px;
}

/*图书推荐*/
.recommendWrap {
  width: 1200px;
  /*height: 800px;*/
  /*border: 1px red solid;*/
  margin-left: 35px;
  border-radius: 20px;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  position: relative;
  float: left;
  margin-top: 20px;
}

.recommendBox {
  margin-top: 30px;
}

.recommendShow {
  /* 相对定位 */
  position: relative;
  width: 200px;
  height: 330px;
  margin-left: 33px;
  margin-bottom: 25px;
  background-color: #758a99;
  border-radius: 20px;
  /* 溢出隐藏 */
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  /* 阴影 */
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
  color: #fff;
  float: left;
}

.recommendImg {
  /* 绝对定位 */
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  border-radius: 0%;
  overflow: hidden;
  /* 动画过渡 */
  transition: 0.5s;
  z-index: 8;
}

/*.recommendShow:hover .recommendImg{*/
/*  top: 30px;*/
/*  width: 0px;*/
/*  height: 0px;*/
/*  border-radius: 50%;*/
/*  box-shadow: 0 0 20px rgba(0,0,0,0.8);*/
/*}*/
.recommendShow .recommendImg::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, transparent 50%, #222);
}

.recommendImg img {
  width: 100%;
  height: 100%;
  /* 保持原有尺寸比例，裁切长边 */
  object-fit: cover;
}

.recommendName {
  position: absolute;
  top: 225px;
  transition: 0.5s;
  font-size: 16px;
  z-index: 8;
}

.recommendType {
  margin-top: 270px;
  z-index: 8;
}

.recommendStar {
  margin-top: 5px;
  color: white;
  z-index: 8;
}

.recommendPage {
  float: right;
  margin-top: 15px;
  margin-right: 520px;
  margin-bottom: 15px;
}
</style>