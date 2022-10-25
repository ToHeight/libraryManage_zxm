package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.FindBorrowedBooks;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Service
 * @createDate 2022-10-17 16:24:02
 */
public interface BorrowServiceReader extends IService<Borrow> {

    ResponseResult<String> borrowBook(String bookId, String token);

    void booksNotReturnedOverTime(Integer borrowId);

    ResponseResult<PageInfo<Map<String, Object>>> findBorrowedBooks(FindBorrowedBooks findBorrowedBooks, String token);

    ResponseResult<String> returnBooks(String borrowId, String token);

    ResponseResult<String> deleteBorrowingRecord(String borrowId,String token);

    void booksNotReturnedOver();
}
