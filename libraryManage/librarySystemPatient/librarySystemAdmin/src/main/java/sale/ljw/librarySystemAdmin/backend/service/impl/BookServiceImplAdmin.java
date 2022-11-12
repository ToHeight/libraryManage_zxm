package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.dao.BorrowMapper;
import sale.ljw.backend.form.AddBook;
import sale.ljw.backend.form.EditBookInformationByAdmin;
import sale.ljw.backend.form.QueryAllBooksByAdmin;
import sale.ljw.backend.pojo.Book;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.librarySystemAdmin.backend.service.BookServiceAdmin;
import sale.ljw.librarySystemAdmin.common.sercurity.utils.ObsUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service实现
 * @createDate 2022-10-17 16:14:59
 */
@Service
public class BookServiceImplAdmin extends ServiceImpl<BookMapper, Book>
        implements BookServiceAdmin {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ObsUtil obsUtil;
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> queryAllBooks(QueryAllBooksByAdmin booksByAdmin) {
        //分页
        PageHelper.startPage(booksByAdmin.getPage(), 10);
        ArrayList<Map<String, Object>> bookList = bookMapper.queryAllBooksByAdmin(booksByAdmin);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(bookList);
        return ResponseResult.getSuccessResult(pageInfo, "查询成功");
    }

    @Override
    public ResponseResult<String> editBookInformation(EditBookInformationByAdmin editBookInformation) {
        //修改图书信息
        Integer result = bookMapper.editBookInformation(editBookInformation);
        if (result == 0) {
            return ResponseResult.getErrorResult("修改图书信息失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null, "修改成功");
    }

    @Override
    public String uploadCoverAddress(MultipartFile file) {
        IdWorker idWorker = new IdWorker();
        long fileName = idWorker.nextId();
        try {
            return obsUtil.uploadFile(file, "librarySystem/book/" + fileName + ".jpg");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult<String> addBook(AddBook addBook) {
        //查询当前书籍是否存在
        QueryWrapper<Book> queryWrapper_book = new QueryWrapper<>();
        queryWrapper_book.eq("book_name", addBook.getBookName()).eq("book_address", addBook.getBookAddress());
        if (bookMapper.selectCount(queryWrapper_book) != 0) {
            return ResponseResult.getErrorResult("图书名称或所在位置重复", StatusCode.Not_Acceptable, null);
        }
        String bookId = new IdWorker().nextId() + "";
        Integer result = bookMapper.addBook(addBook, bookId);
        if (result == 0) {
            return ResponseResult.getErrorResult("添加图书失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null, "添加图书成功");
    }

    @Override
    public ResponseResult<String> deleteBookById(String bookId) {
        //查询图书是否借阅中
        QueryWrapper<Borrow> queryWrapper_book=new QueryWrapper<>();
        queryWrapper_book.eq("book_id", bookId).notIn("borrow_tatus", "BWS02");
        if(borrowMapper.selectCount(queryWrapper_book)!=0){
            return ResponseResult.getErrorResult("当前图书正在借阅中",StatusCode.NOT_FOUND,null);
        }
        //删除图书
        if(bookMapper.deleteById(bookId)==0){
            return ResponseResult.getErrorResult("图书删除失败",StatusCode.NO_CONTENT,null);
        }
        return ResponseResult.getSuccessResult(null,"删除成功");
    }

}




