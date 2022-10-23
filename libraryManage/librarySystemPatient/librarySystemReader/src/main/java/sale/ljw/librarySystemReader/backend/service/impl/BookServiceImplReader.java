package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kotlin.jvm.Volatile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BookshelfMapper;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.pojo.Book;
import sale.ljw.backend.pojo.Bookshelf;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.BookServiceReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service实现
 * @createDate 2022-10-17 16:14:59
 */
@Service
public class BookServiceImplReader extends ServiceImpl<BookMapper, Book>
        implements BookServiceReader {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookshelfMapper bookshelfMapper;

    /**
     * 根据条件查询图书信息
     * @param findBookByCriteria
     * @return
     */
    @Override
    public ResponseResult<PageInfo<Map<String,Object>>> findBookByCriteria(FindBookByCriteria findBookByCriteria) {
        //分页
        PageHelper.startPage(findBookByCriteria.getPage(), findBookByCriteria.getPageSize());
        //数据查询出来进行封装
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(bookMapper.findBookByCriteria(findBookByCriteria));
        return ResponseResult.getSuccessResult(pageInfo, "查找图书成功！");
    }

    @Override
    public ResponseResult<String> addBookshelves(String bookId, String token) {
        //查询当前图书库存是否不为0
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("book_id",bookId).eq("book_delete",0).select("book_count");
        Book book = bookMapper.selectOne(queryWrapper);
        if(book == null || book.getBookCount() == 0){
            return ResponseResult.getErrorResult("图书数量异常！",StatusCode.NOT_MODIFIED, null);
        }
        //将图书添加到书架中
        synchronized (this){
            //获取用户id
            String userId = JwtUtils.parseJWT(token);
            //表中添加图书
            Bookshelf bookShelf=new Bookshelf();
            bookShelf.setAddTime(new Date());
            bookShelf.setBookId(bookId);
            bookShelf.setUserId(Integer.parseInt(userId));
            if(bookshelfMapper.insert(bookShelf)!=0){
                return ResponseResult.getSuccessResult(null, "图书添加书架成功！");
            }
        }
        return ResponseResult.getErrorResult("图书添加书架失败", StatusCode.NO_CONTENT, null);
    }


}




