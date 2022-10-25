package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.BookshelvesBooks;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BookshelfServiceReader;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/bookShelfServletByReader")
public class RBookShelfServlet {
    @Autowired
    private BookshelfServiceReader bookshelfServiceReader;

    /**
     * 根据用户id查询书架图书
     *
     * @param bookshelves
     * @param token
     * @return
     */
    @ApiOperation(value = "查询书架内图书")
    @PostMapping(path = "/queryBookshelvesBooks")
    public ResponseResult<PageInfo<BookshelvesBooks>> queryBookshelvesBooks(@RequestBody @Valid QueryBookshelves bookshelves, @RequestHeader(name = "token") String token) {
        return bookshelfServiceReader.queryBookshelvesBooks(bookshelves, token);
    }

    /**
     * &
     * 删除书架内图书
     *
     * @param bookIds
     * @param token
     * @return
     */
    @ApiOperation(value = "删除书架内图书")
    @PostMapping(path = "/deleteBookshelvesBooks")
    public ResponseResult<String> deleteBookshelvesBooks(@RequestBody @Validated @Size(min = 1, message = "请至少添加一条图书id") ArrayList<String> bookIds, @RequestHeader(name = "token") String token) {
        return bookshelfServiceReader.deleteBookshelvesBooks(bookIds, token);
    }


}
