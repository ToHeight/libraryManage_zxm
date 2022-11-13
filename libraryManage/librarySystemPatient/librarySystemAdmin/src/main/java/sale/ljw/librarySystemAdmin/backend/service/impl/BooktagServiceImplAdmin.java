package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.backend.dao.BooktagMapper;
import sale.ljw.backend.dao.BooktapMapper;
import sale.ljw.backend.form.BookTagList;
import sale.ljw.backend.form.EditBookTag;
import sale.ljw.backend.form.FindAllBookTag;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.backend.pojo.Booktap;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.BooktagServiceAdmin;

import java.util.ArrayList;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service实现
 * @createDate 2022-10-17 16:19:03
 */
@Service
public class BooktagServiceImplAdmin extends ServiceImpl<BooktagMapper, Booktag>
        implements BooktagServiceAdmin {
    @Autowired
    private BooktagMapper booktagMapper;
    @Autowired
    private BooktapMapper booktapMapper;
    @Override
    public ResponseResult<PageInfo<BookTagList>> findAllBookTag(FindAllBookTag bookTag) {
        PageHelper.startPage(bookTag.getPage(),10);
        ArrayList<BookTagList> bookTagLists=booktagMapper.findAllBookTag(bookTag.getTagValue());
        PageInfo<BookTagList> bookTagListPageInfo=new PageInfo<>(bookTagLists);
        return ResponseResult.getSuccessResult(bookTagListPageInfo, "查询成功");
    }
    @Transactional
    @Override
    public ResponseResult<String> editBookTag(EditBookTag bookTag) {
        //修改标签内容
        UpdateWrapper<Booktag> updateWrapper_bookTag=new UpdateWrapper<>();
        updateWrapper_bookTag.eq("tag_id", bookTag.getTagId()).set("tag_value", bookTag.getTagValue());
        if(booktagMapper.update(null,updateWrapper_bookTag)==0){
            return ResponseResult.getErrorResult("修改书签失败", StatusCode.NO_CONTENT, null);
        }
        //添加标签内容
        QueryWrapper<Booktap> queryWrapper_deleteTap=new QueryWrapper<>();
        queryWrapper_deleteTap.eq("tapId", bookTag.getTagId());
        if(booktapMapper.delete(queryWrapper_deleteTap)==0){
            return ResponseResult.getErrorResult("删除书签失败", StatusCode.NO_CONTENT, null);
        }
        //添加新的书签
        Integer result=booktapMapper.addTapByBookList(bookTag.getTagId(),bookTag.getBookId());
        if(result!= bookTag.getBookId().size()){
            return ResponseResult.getErrorResult("图书添加失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null,"修改成功");
    }

    @Override
    public ResponseResult<String> deleteTagById(Integer tagId) {
        if(booktagMapper.deleteById(tagId)==0){
            return ResponseResult.getErrorResult("删除失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null,"删除成功");
    }
    @Transactional
    @Override
    public ResponseResult<String> addBookTag(EditBookTag bookTag) {
        synchronized (this){
            //新增书签
            Booktag booktag=new Booktag();
            booktag.setTagValue(bookTag.getTagValue());
            if(booktagMapper.insert(booktag)==0){
                return ResponseResult.getErrorResult("添加书签失败", StatusCode.NO_CONTENT, null);
            }
            //添加图书绑定
            Integer result=booktapMapper.addTapByBookList(booktag.getTagId(),bookTag.getBookId());
            if(result!= bookTag.getBookId().size()){
                return ResponseResult.getErrorResult("图书添加失败", StatusCode.NO_CONTENT, null);
            }
        }
        return ResponseResult.getSuccessResult(null,"书签添加成功");
    }
}




