package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BookshelfServiceReader;

import javax.validation.Valid;
import java.util.Map;

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
    public ResponseResult<PageInfo<Map<String, Object>>> queryBookshelvesBooks(@RequestBody @Valid QueryBookshelves bookshelves, @RequestHeader(name = "token") String token) {
        return bookshelfServiceReader.queryBookshelvesBooks(bookshelves, token);
    }


}
