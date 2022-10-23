package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BookshelfMapper;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.backend.pojo.Bookshelf;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.BookshelfServiceReader;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【BookShelf】的数据库操作Service实现
 * @createDate 2022-10-17 16:17:30
 */
@Service
public class BookshelfServiceImplReader extends ServiceImpl<BookshelfMapper, Bookshelf>
        implements BookshelfServiceReader {
    @Autowired
    private BookshelfMapper bookshelfMapper;
    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> queryBookshelvesBooks(QueryBookshelves bookshelves,String token) {
        //获取用户id，封装
        bookshelves.setUserId(JwtUtils.parseJWT(token));
        PageHelper.startPage(bookshelves.getPage(), bookshelves.getPageSize());
        List<Map<String,Object>> bookList=bookshelfMapper.queryBookshelvesBooks(bookshelves);
        PageInfo<Map<String, Object>> pageInfo=new PageInfo<>(bookList);
        return ResponseResult.getSuccessResult(pageInfo,"查询书架图书成功");
    }

    @Override
    public ResponseResult<String> deleteBookshelvesBooks(ArrayList<String> bookIds, String token) {
        //更新书架，涉及更新表字段
        String userId = JwtUtils.parseJWT(token);
        //更新书架信息
        for (String bookId : bookIds) {
            UpdateWrapper<Bookshelf> updateWrapper_0=new UpdateWrapper<>();
            updateWrapper_0.eq("user_id", userId)
                           .eq("book_id", bookId)
                           .set("book_delete", 1);
            if(!update(updateWrapper_0)){
                return ResponseResult.getErrorResult("删除图书失败，请稍后重试", StatusCode.NOT_MODIFIED, null);
            }
        }
        return ResponseResult.getSuccessResult(null,"删除图书成功！");
    }
}




