package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.dao.BookshelfMapper;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BookServiceReader;
import sale.ljw.librarySystemReader.backend.service.BookshelfServiceReader;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
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



}
