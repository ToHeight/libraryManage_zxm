package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.form.AddBook;
import sale.ljw.backend.form.EditBookInformationByAdmin;
import sale.ljw.backend.form.QueryAllBooksByAdmin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.BookServiceAdmin;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookByAdmin")
public class ABookServlet {
    @Autowired
    private BookServiceAdmin bookServiceAdmin;

    /**
     * 查询全部图书
     *
     * @param booksByAdmin
     * @return
     */
    @ApiOperation(value = "查询全部图书")
    @PostMapping("/queryAllBooks")
    public ResponseResult<PageInfo<Map<String, Object>>> queryAllBooks(@RequestBody @Valid QueryAllBooksByAdmin booksByAdmin) {
        return bookServiceAdmin.queryAllBooks(booksByAdmin);
    }

    /**
     * 修改图书信息
     *
     * @param editBookInformation
     * @return
     */
    @ApiOperation(value = "修改图书信息")
    @PostMapping("/editBookInformation")
    public ResponseResult<String> editBookInformation(@RequestBody @Valid EditBookInformationByAdmin editBookInformation) {
        return bookServiceAdmin.editBookInformation(editBookInformation);
    }

    /**
     * 上传封面地址
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传封面地址")
    @PostMapping("/uploadCoverAddress")
    public String uploadCoverAddress(@RequestBody MultipartFile file) {
        return bookServiceAdmin.uploadCoverAddress(file);
    }

    /**
     * 新增书籍
     *
     * @param addBook
     * @return
     */
    @ApiOperation(value = "新增书籍")
    @PostMapping("/addBook")
    public ResponseResult<String> addBook(@RequestBody @Valid AddBook addBook) {
        return bookServiceAdmin.addBook(addBook);
    }

    /**
     * 删除图书
     *
     * @param bookId
     * @return
     */
    @ApiOperation(value = "删除图书")
    @DeleteMapping("/deleteBookById/{bookId}")
    public ResponseResult<String> deleteBookById(@PathVariable String bookId) {
        return bookServiceAdmin.deleteBookById(bookId);
    }

    /**
     * 远程搜索书名
     * @param bookName
     * @return
     */
    @ApiOperation(value = "远程搜索书名")
    @PostMapping("/searchBookTitlesRemotely")
    public ResponseResult<List<Map<String, Object>>> searchBookTitlesRemotely(@RequestBody String bookName){
        return bookServiceAdmin.searchBookTitlesRemotely(bookName);
    }
}
