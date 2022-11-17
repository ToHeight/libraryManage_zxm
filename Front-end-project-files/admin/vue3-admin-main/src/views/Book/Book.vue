<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="input" class="demo-form-inline"  style="margin-left: 90px">
        <el-form-item label="图书名称:" style="width: 230px" >
          <el-input v-model="input.bookName" placeholder="请输入图书名称"  clearable/>
        </el-form-item>
        <el-form-item label="图书种类:" style="width: 230px">
          <el-select v-model="input.bookType" placeholder="请选择图书种类" clearable>
            <el-option
                v-for="item in bookTypeList"
                :key="item.value"
                :label="item.value"
                :value="item.coding"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图书状态:">
          <el-select v-model="input.bookStatus" placeholder="请选择图书状态" clearable>
            <el-option v-for="item in stateList" :key="item.coding" :label="item.value"
                       :value="item.coding"/>
          </el-select>
        </el-form-item>
      </el-form>
      <el-collapse>
        <el-collapse-item title="更多筛选条件" name="1">
          <el-form :inline="true" :model="input" class="demo-form-inline" style="margin-left: 80px">
            <el-form-item label="作者国家:" style="margin-left: 50px">
              <el-select v-model="input.authorCountry" placeholder="请选择作者国家" clearable>
                <el-option v-for="item in authorCountryList" :key="item.coding" :label="item.value"
                           :value="item.coding"/>
              </el-select>
            </el-form-item>
            <el-form-item label="图书语言:">
              <el-select v-model="input.bookLanguageCoding" placeholder="请选择图书语言" clearable>
                <el-option v-for="item in bookLanguageList" :key="item.coding" :label="item.value"
                           :value="item.coding"/>
              </el-select>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
      <div class="bookTools">
      <el-button type="warning" @click="selectBook">查询</el-button>
      <el-button type="primary" @click="addShow">添加</el-button>
      </div>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="bookList"
      style="width: 100%"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >
      <el-table-column label="图书图片" width="110">
        <template #default="scope">
          <img :src="scope.row.image" alt="scope.row.bookList.bookName" style="width: 70px;height: 90px">
        </template>
      </el-table-column>
    <el-table-column property="bookName" label="书名" width="140" />
    <el-table-column property="author" label="作者" width="100" />
    <el-table-column property="authorCountry" label="作者国家" width="100" />
    <el-table-column property="typeName" label="图书种类" width="100" />
    <el-table-column property="bookLanguage" label="图书语言" width="90" />
    <el-table-column property="bookStatus" label="图书状态" width="90" />
    <el-table-column property="bookStatus" label="图书信息" width="110">
          <template #default="scope">
            <el-popover effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                {{scope.row.bookInfo}}
              </template>
              <template #reference>
                <el-tag>{{ scope.row.bookInfo }}</el-tag>
              </template>
            </el-popover>
          </template>
    </el-table-column>
<!--    <el-table-column property="bookAddress" label="图书位置" width="70" />-->
    <el-table-column property="bookCount" label="库存" width="70" />
    <el-table-column property="tools" label="操作" width="100">
      <template #default="scope">
      <el-button type="primary" circle @click="editShow(scope.$index, scope.row)"><el-icon><EditPen /></el-icon></el-button>
      <el-button type="danger"  circle @click="bookDelete(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
      </template>
    </el-table-column>

  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />
  <!-- 修改弹窗 -->
  <el-dialog v-model="editDateShow" title="图书信息修改">
    <el-form :model="editData" ref="vForm" label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="头像上传：" prop="bookImage" label-width="85px" style="margin-left: 25px"
                    class="label-right-align">
        <el-image style="width: 145px; height: 145px;margin-right: 5px" :src="editData.image" :fit="fit"/>
        <el-upload list-type="picture-card" :limit="1" class="avatar-uploader"
                   action="http://saleljw.5gzvip.91tunnel.com/library-system-admin/librarySystemAdmin/bookByAdmin/uploadCoverAddress"
                   :show-file-list="uploadImg" :before-upload="beforeAvatarUpload"
                   :on-success="successAvatarUpload"
                   :data={bookName:this.editData.bookName}>
          <template #default>
            <el-icon>
              <Plus/>
            </el-icon>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="editData.bookName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="图书作者" prop="author">
        <el-input v-model="editData.author" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="作者国家" prop="authorCountry">
        <el-select v-model="editData.authorCountryCode" class="full-width-input" clearable>
          <el-option v-for="(item, index) in authorCountryList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书种类" prop="typeName">
        <el-select v-model="editData.typeId" class="full-width-input" clearable>
          <el-option v-for="(item, index) in bookTypeList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书语言" prop="bookLanguage">
        <el-select v-model="editData.bookLanguageCode" class="full-width-input" clearable>
          <el-option v-for="(item, index) in bookLanguageList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书状态" prop="bookStatus">
        <el-select v-model="editData.bookStatusCode" class="full-width-input" clearable>
          <el-option v-for="(item, index) in stateList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书信息" prop="bookInfo">
        <el-input type="textarea" v-model="editData.bookInfo" rows="3"></el-input>
      </el-form-item>
      <el-form-item label="图书位置" prop="address">
        <el-input v-model="editData.address" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="库存" prop="bookCount">
        <el-input-number v-model="editData.bookCount" class="full-width-input" controls-position="right"
                         :min="-100000000000" :max="100000000000" :precision="0" :step="1"></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDateShow = false">取消</el-button>
        <el-button type="primary" @click="bookInfoEdit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 添加弹窗 -->
  <el-dialog v-model="addDateShow" title="Shipping address">
    <el-form :model="addData" ref="vForm" :rules="rules" label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="头像上传：" prop="bookImage" label-width="85px" style="margin-left: 25px"
                    class="label-right-align">
        <el-image style="width: 145px; height: 145px;margin-right: 5px" :src="addData.bookImage" :fit="fit"/>
        <el-upload list-type="picture-card" :limit="1" class="avatar-uploader"
                   action="http://saleljw.5gzvip.91tunnel.com/library-system-admin/librarySystemAdmin/bookByAdmin/uploadCoverAddress"
                   :show-file-list="uploadImg" :before-upload="addBeforeAvatarUpload"
                   :on-success="addSuccessAvatarUpload"
                   :data={bookName:this.addData.bookName}>
          <template #default>
            <el-icon>
              <Plus/>
            </el-icon>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="addData.bookName" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="图书作者" prop="bookAuthor">
        <el-input v-model="addData.bookAuthor" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="作者国家" prop="authorCountry">
        <el-select v-model="addData.authorCountry" class="full-width-input" clearable>
          <el-option v-for="(item, index) in authorCountryList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书种类" prop="bookType">
        <el-select v-model="addData.bookType" class="full-width-input" clearable>
          <el-option v-for="(item, index) in bookTypeList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书语言" prop="bookLanguageCoding">
        <el-select v-model="addData.bookLanguageCoding" class="full-width-input" clearable>
          <el-option v-for="(item, index) in bookLanguageList" :key="index" :label="item.value"
                     :value="item.coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书状态" prop="bookStatus">
        <el-select v-model="addData.bookStatus" class="full-width-input" clearable>
          <el-option v-for="(item, index) in stateList" :key="index" :label="item.value"
                     :value="item.coding" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书信息" prop="bookInfo">
        <el-input type="textarea" v-model="addData.bookInfo" rows="3"></el-input>
      </el-form-item>
      <el-form-item label="图书位置" prop="bookAddress">
        <el-input v-model="addData.bookAddress" type="text" clearable></el-input>
      </el-form-item>
      <el-form-item label="库存" prop="bookCount">
        <el-input-number v-model="addData.bookCount" class="full-width-input" controls-position="right"
                         :min="-100000000000" :max="100000000000" :precision="0" :step="1"></el-input-number>
      </el-form-item>
      <el-form-item label="星级" prop="bookStar">
        <el-input-number v-model="addData.bookStar" class="full-width-input" controls-position="right"
                         :min="-100000000000" :max="100000000000" :precision="0" :step="1"></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDateShow = false">取消</el-button>
        <el-button type="primary" @click="bookAdd">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {ElMessage} from "element-plus";
import {
  addBook, deleteBook,
  editBook,
  findAuthorCountry,
  findBookLanguage,
  findBookList,
  findBookStatus,
  findBookType
} from "@/api/library_admin";

export default {
  // name: "Book",
  data() {
    return {
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      bookTypeList: [],
      input: [],
      bookName: undefined,
      bookType: undefined,
      authorCountryList: [],
      coding: undefined,
      country: undefined,
      bookLanguageList: [],
      bookImage:undefined,

      //查询全部图书
      bookList: [],
      page: 1,
      total:undefined,
      image: undefined,
      bookInfo: undefined,
      typeId: undefined,
      bookStatus: undefined,
      bookLanguage: undefined,
      authorCountry: undefined,
      bookCount: undefined,
      stateList: [],
      //修改图书信息
      editDateShow: false,
      editData: [],
      uploadImg: undefined,
      isJPG: undefined,
      bookStatusCode:undefined,
      bookLanguageCode:undefined,
      authorCountryCode:undefined,
      typeName:undefined,

      //图书添加
      addDateShow: false,
      addData: [],
    }
  },
  methods: {
    //图书种类获取
    getBookType() {
      findBookType(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookTypeList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //作者国家获取
    getAuthorCountry() {
      findAuthorCountry(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.authorCountryList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //图书状态获取
    getBookStatus() {
      findBookStatus(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.stateList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //图书语言获取
    getBooKLanguage() {
      findBookLanguage(this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookLanguageList = res.data.result;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //分页
    pageChange(val) {
      this.page = val;
      this.selectBook();
    },
    //图书全部信息获取
    selectBook() {
      let para = {
        page: this.page,
        info: this.info,
        typeId: this.input.bookType === '' ? undefined : this.input.bookType,
        bookStatus: this.input.bookStatus === '' ? undefined : this.input.bookStatus,
        bookLanguageCoding: this.input.bookLanguageCoding === '' ? undefined : this.input.bookLanguageCoding,
        authorCountry: this.input.authorCountry === '' ? undefined : this.input.authorCountry,
      }
      findBookList(para, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookList = res.data.result.list;
          this.total = res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //信息编辑弹窗
    editShow(index, row) {
      this.editDateShow = !this.editDateShow;
      this.editData = row;
      console.log(this.editData);
    },
    //图片上传
    beforeAvatarUpload(file) {
      this.isJPG = file.type === 'image/jpeg';

      if (!this.isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      return this.isJPG;
    },
    successAvatarUpload(dataImg, uploadFile) {
      this.editData.image = dataImg;
    },
    //图书信息编辑
    bookInfoEdit() {
      let para = {
        bookId: this.editData.bookId,
        typeId: this.editData.typeId,
        bookName: this.editData.bookName,
        bookAuthor: this.editData.author,
        bookInfo: this.editData.bookInfo,
        bookImage: this.editData.image,
        bookAddress: this.editData.address,
        bookStatus: this.editData.bookStatusCode,
        bookLanguageCoding: this.editData.bookLanguageCode,
        authorCountry: this.editData.authorCountryCode,
      }
      editBook(para, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.editDateShow = false;
          this.selectBook();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //图书添加弹窗
    addShow() {
      this.addDateShow = !this.addDateShow;
    },
    //添加图片上传
    addBeforeAvatarUpload(file) {
      this.isJPG = file.type === 'image/jpeg';

      if (!this.isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      return this.isJPG;
    },
    addSuccessAvatarUpload(dataImg, uploadFile) {
      this.addData.bookImage = dataImg;
    },
    //图书添加
    bookAdd() {
      let para = {
        bookId: this.addData.bookId,
        typeId: this.addData.bookType,
        bookName: this.addData.bookName,
        bookAuthor: this.addData.bookAuthor,
        bookInfo: this.addData.bookInfo,
        bookImage: this.addData.bookImage,
        bookAddress: this.addData.bookAddress,
        bookStatus: this.addData.bookStatus,
        bookLanguageCoding: this.addData.bookLanguageCoding,
        authorCountry: this.addData.authorCountry,
        bookCount:this.addData.bookCount,
        bookStar: this.addData.bookStar,
      }
      addBook(para, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.addDateShow = false;
          this.selectBook();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },

    //图书删除
    bookDelete(index,row){
      // this.$confirm('确认删除选中记录吗？', '提示', {
      //   type: 'warning'
      // }).then(() => {
      this.listLoading = true;
      deleteBook(row.bookId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
        } else {
          ElMessage.error(res.data.message);
          this.selectBook();
        }
        //  })
        // })
      })
    },

  },
  mounted() {
    this.getBookType();
    this.getAuthorCountry();
    this.getBooKLanguage();
    this.getBookStatus();
    this.selectBook();
  },
}
</script>
<style scoped>
.header{
  width: 100%;
}
.bookTools{
  margin-top: 10px;
}
</style>