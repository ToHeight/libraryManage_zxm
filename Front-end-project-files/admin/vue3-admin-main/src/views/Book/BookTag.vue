<template>
  <div class="header">
    <div class="header-main">
      <el-form :inline="true" :model="input" class="demo-form-inline"  style="margin-left: 90px">
        <el-form-item label="标签内容:" style="width: 230px" >
          <el-input v-model="input.tagValue" placeholder="请输入标签内容"  clearable/>
        </el-form-item>
      </el-form>
      <div class="bookTools">
        <el-button type="warning" @click="getTag">查询</el-button>
        <el-button type="primary" @click="tagAddShow">添加</el-button>
      </div>
    </div>
  </div>
  <el-table
      ref="multipleTableRef"
      :data="tagList"
      style="width: 100%"
      :header-cell-style="{textAlign: 'center'}"
      :cell-style="{ textAlign: 'center' }"
  >

    <el-table-column property="tagId" label="书签ID" width="140" />
    <el-table-column property="tagValue" label="书签内容" width="100" />
    <el-table-column property="tapList" label="对应图书" width="500" >
      <template #default="scope" >
        <el-popover effect="light" trigger="hover" placement="top" width="auto" v-for="item in scope.row.tapList" :key="item">
          <template #default v-if="scope.row.tapList.length!==0">
            {{item.bookName}}
          </template>
          <template #reference >
            <el-tag v-if="scope.row.tapList.length!==0" style="margin-left: 5px">
              {{item.bookName}}
            </el-tag>
          </template>
        </el-popover>
        <el-tag v-if="scope.row.tapList.length===0">暂无图书</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="userName" label="用户" width="100" />
    <el-table-column property="tools" label="操作" width="100">
      <template #default="scope">
        <el-button type="primary" circle @click="editShow(scope.$index, scope.row)"><el-icon><EditPen /></el-icon></el-button>
        <el-button type="danger"  circle @click="tagDelete(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
      </template>
    </el-table-column>

  </el-table>
  <el-pagination layout="prev, pager, next" :total="total" @current-change="pageChange" style="margin-left: 400px" />

  <!-- 修改弹窗 -->
  <el-dialog v-model="editTagDateShow" title="图书书签修改">
    <el-form :model="editTagData" ref="vForm" label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="标签名称" prop="tagValue">
        <el-input v-model="editTagData.tagValue" type="text" clearable style="width: 200px"></el-input>
      </el-form-item>
    </el-form>
    <el-table :data="tagBookData" style="width: 100%;margin-bottom: 5px;"
              :header-cell-style="{textAlign: 'center'}"
              :cell-style="{ textAlign: 'center' }">
      <el-table-column prop="bookName" label="图书名称" />
      <el-table-column prop="tagTool" label="操作" width="300">
        <template #default="scope">
        <el-button type="danger"  circle @click="bookDelete(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-divider>
     <span>添加图书界面</span>
    </el-divider>
    <el-input v-model="bookName" placeholder="请输入标签内容"  clearable style="width: 300px;margin-right: 10px;margin-top: 10px"/>
    <el-button type="warning" @click="farSelectBook" style="margin-top: 10px">查询</el-button>
    <el-table
        ref="multipleTableRef"
        :data="bookSelectList"
        style="width: 100%"
        :header-cell-style="{textAlign: 'center'}"
        :cell-style="{ textAlign: 'center' }"
    >
      <el-table-column property="bookName" label="图书名称" />
      <el-table-column property="tools" label="操作" width="300">
        <template #default="scope">
          <el-button type="primary" circle @click="addBook(scope.$index, scope.row)"><el-icon><Plus /></el-icon></el-button>
        </template>
      </el-table-column>

    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editTagDateShow = false">取消</el-button>
        <el-button type="primary" @click="tagEdit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  添加书签-->
  <el-dialog v-model="addTagDataShow" title="图书书签添加">
    <el-form :model="addTagData" ref="vForm" label-position="left" label-width="80px"
             size="default" @submit.native.prevent>
      <el-form-item label="书签ID" prop="tagValue">
        <el-input v-model="addTagData.tagId" type="text" clearable style="width: 200px"></el-input>
        <!--        <el-button type="primary" @click="addTagBook" style="margin-left: 20px">添加图书</el-button>-->
      </el-form-item>
      <el-form-item label="标签名称" prop="tagValue">
        <el-input v-model="addTagData.tagValue" type="text" clearable style="width: 200px;"></el-input>
      </el-form-item>
    </el-form>
    <el-table :data="tagBookData" style="width: 100%"
              :header-cell-style="{textAlign: 'center'}"
              :cell-style="{ textAlign: 'center' }">
      <el-table-column prop="bookName" label="图书名称"  />
      <el-table-column prop="tagTool" label="操作" width="300">
        <template #default="scope">
          <el-button type="danger"  circle @click="bookDelete(scope.$index, scope.row)" ><el-icon><Delete /></el-icon></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-input v-model="bookName" placeholder="请输入标签内容"  clearable style="width: 300px;margin-right: 10px;margin-top: 10px"/>
    <el-button type="warning" @click="farSelectBook" style="margin-top: 10px;">查询</el-button>
    <el-table
        ref="multipleTableRef"
        :data="bookSelectList"
        style="width: 100%"
        :header-cell-style="{textAlign: 'center'}"
        :cell-style="{ textAlign: 'center' }"
    >
      <el-table-column property="bookName" label="图书名称" />
      <el-table-column property="tools" label="操作" width="300">
        <template #default="scope">
          <el-button type="primary" circle @click="addBook(scope.$index, scope.row)"><el-icon><Plus /></el-icon></el-button>
        </template>
      </el-table-column>

    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editTagDateShow = false">取消</el-button>
        <el-button type="primary" @click="tagAdd">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {addTag, deleteBook, deleteTag, editTag, findTagList, selectBook} from "@/api/library_admin";
import {ElMessage} from "element-plus";

export default {
  name: "BookTag",
  data(){
    return{
      configs: {
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      },
      input:[],
      //分页
      total:undefined,
      //查询书签
      tagList:[],
      page:1,
      tapvalue:undefined,
      tapList:[],
      bookList:[],
      bookNameList:[],

      //编辑书签
      editTagDateShow:false,
      editTagData:[],
      bookIds:[],
      tagBookData:[],
      bookName:undefined,
      farSelectList:[],
      addBookInfo:[],
      bookSelectList:[],
      book_name:undefined,
      rowbookIds:[],

      //添加书签
      addTagData:[],
      addTagBook:[],
      addTagDataShow:false,

    }
  },
  methods:{
    //分页
    pageChange(val) {
      this.page = val;
      this.getTag();
    },
    //查询书签
    getTag(){
      let para = {
        page:this.page,
        tapvalue:this.tapvalue,
      }
      findTagList(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.tagList = res.data.result.list;
          this.total=res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })

    },

    //修改书签弹窗显示
    editShow(index,row){
      this.editTagDateShow = !this.editTagDateShow;
      this.editTagData = row;
      this.tagBookData=row.tapList;
    },
    //修改书签
    tagEdit(){
      let para ={
        tagId:this.editTagData.tagId,
        tagValue:this.editTagData.tagValue,
        bookId:this.bookIds.length === 0 ? [] : this.bookIds,
      }
      editTag(para, this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.editTagDateShow = false;
          this.getTag();
        } else {
          ElMessage.error(res.data.message);
        }
      })
    },
    //删除图书
    bookDelete(index,row){
      let removeId = row.bookId;
      if (this.tagBookData.length===0){
        this.bookIds = [];
      }else{
        this.bookIds = [];
        this.tagBookData.forEach(e => {
          this.bookIds.push(e.bookId)
        })
      }
      for(let i=0;i<this.tagBookData.length;i++){
        if(this.bookIds[i]===removeId){
          this.tagBookData.splice(i,1);
          this.bookIds.splice(i,1);
        }
      }
    },
    //添加图书
    addBook(index,row){
      this.addBookInfo = row;
      this.tagBookData=this.tagBookData.concat(this.addBookInfo);
      if (this.tagBookData.length===0){
        this.bookIds = undefined;
      }else{
        this.bookIds = [];
        this.tagBookData.forEach(e => {
          this.bookIds.push(e.bookId)
        })
      }
    },
    //添加书签
    //添加弹窗显示
    tagAddShow(){
      this.addTagDataShow = !this.addTagDataShow;
      this.tagBookData=[];
    },

    //远程搜索
    farSelectBook(){
      selectBook(this.bookName,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          this.bookSelectList = res.data.result;
          this.bookSelectList.bookName=this.bookSelectList.book_name;
          // this.total=res.data.result.total;
        } else {
          ElMessage.error(res.data.message);
        }
      })
      },
    //添加
    tagAdd(){
      let para ={
        tagId:this.addTagData.tagId,
        tagValue:this.addTagData.tagValue,
        bookId:this.bookIds,
      }
      addTag(para,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.addTagDataShow = false;
          this.getTag();
        } else {
          ElMessage.error(res.data.message);

        }
      })
    },
    //删除书签
    tagDelete(index,row){
      deleteTag(row.tagId,this.configs).then((res) => {
        if (res.data.statusCode === 'C200') {
          ElMessage.success(res.data.message);
          this.getTag();
        } else {
          ElMessage.error(res.data.message);

        }
      })
    }
  },
  mounted() {
    this.getTag();
  }
}
</script>

<style scoped>

</style>