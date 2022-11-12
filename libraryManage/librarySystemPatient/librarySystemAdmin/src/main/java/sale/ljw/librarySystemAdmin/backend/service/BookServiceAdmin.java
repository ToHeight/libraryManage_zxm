package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.form.AddBook;
import sale.ljw.backend.form.EditBookInformationByAdmin;
import sale.ljw.backend.form.QueryAllBooksByAdmin;
import sale.ljw.backend.pojo.Book;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service
 * @createDate 2022-10-17 16:14:59
 */
public interface BookServiceAdmin extends IService<Book> {

    ResponseResult<PageInfo<Map<String, Object>>> queryAllBooks(QueryAllBooksByAdmin booksByAdmin);

    ResponseResult<String> editBookInformation(EditBookInformationByAdmin editBookInformation);

    String uploadCoverAddress(MultipartFile file);

    ResponseResult<String> addBook(AddBook addBook);

    ResponseResult<String> deleteBookById(String bookId);
}
