package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.BookDetailsInformation;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BookServiceReader;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/bookByReader")
public class RBookServlet {
    @Autowired
    private BookServiceReader bookServiceReader;

    /**
     * 根据条件查询图书
     *
     * @param findBookByCriteria
     * @return
     */
    @ApiOperation(value = "根据条件查找图书")
    @PostMapping(path = "/findBookByCriteria")
    public ResponseResult<PageInfo<Map<String, Object>>> findBookByCriteria(@RequestBody @Valid FindBookByCriteria findBookByCriteria) {
        return bookServiceReader.findBookByCriteria(findBookByCriteria);
    }

    /**
     * 图书添加书架
     *
     * @param bookId
     * @return
     */
    @ApiOperation(value = "添加书架")
    @GetMapping(path = "/addBookshelves/{bookId}")
    public ResponseResult<String> addBookshelves(@PathVariable String bookId, @RequestHeader(name = "token") String token) {
        return bookServiceReader.addBookshelves(bookId, token);
    }

    /**
     * 图书详情信息
     *
     * @param bookId
     * @return
     */
    @ApiOperation(value = "图书详情信息")
    @GetMapping("/getBookDetails")
    public ResponseResult<BookDetailsInformation> getBookDetails(@RequestParam(value = "bookId") String bookId) {
        return bookServiceReader.getBookDetails(bookId);
    }

    /**
     * 推荐图书获取
     *
     * @param bookId
     * @param page
     * @return
     */
    @ApiOperation(value = "推荐图书")
    @GetMapping("/recommendedBooks")
    public ResponseResult<PageInfo<Map<String, Object>>> recommendedBooks(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "page") Integer page) {
        return bookServiceReader.recommendedBooks(bookId, page);
    }

    /**
     * 首页推荐图书
     * @param pageSize
     * @param token
     * @return
     */
    @ApiOperation(value = "首页推荐图书")
    @GetMapping("/homeRecommendedBooks/{pageSize}")
    public ResponseResult<PageInfo<Map<String, Object>>> homeRecommendedBooks(@PathVariable(value = "pageSize") Integer pageSize, @RequestHeader(name = "token") String token) {
        return bookServiceReader.homeRecommendedBooks(pageSize, token);
    }

    /**
     * 图书详情界面图书评论获取
     *
     * @param page
     * @param bookId
     * @return
     */
    @ApiOperation(value = "图书详情界面图书评论获取")
    @GetMapping("/getBookReviews")
    public ResponseResult<PageInfo<Map<String, Object>>> getBookReviews(@RequestParam(value = "page") Integer page, @RequestParam(value = "bookId") String bookId) {
        return bookServiceReader.getBookReviews(page, bookId);
    }
}
