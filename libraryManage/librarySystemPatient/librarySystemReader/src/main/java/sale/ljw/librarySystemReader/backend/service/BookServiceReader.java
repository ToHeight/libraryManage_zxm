package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.BookDetailsInformation;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.backend.pojo.Book;
import sale.ljw.common.common.http.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service
 * @createDate 2022-10-17 16:14:59
 */
public interface BookServiceReader extends IService<Book> {

    ResponseResult<PageInfo<Map<String,Object>>> findBookByCriteria(FindBookByCriteria findBookByCriteria);

    ResponseResult<String> addBookshelves(String bookId, String token);

    ResponseResult<BookDetailsInformation> getBookDetails(String bookId);

    ResponseResult<PageInfo<Map<String, Object>>> recommendedBooks(String bookId, Integer page);
}
