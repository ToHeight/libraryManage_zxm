package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BooktagServiceReader;
import sale.ljw.librarySystemReader.backend.service.BooktapServiceReader;

@RestController
@RequestMapping("/bookTapByReader")
public class RBookTapServlet {
    @Autowired
    private BooktapServiceReader booktapServiceReader;

    @Autowired
    private BooktagServiceReader booktagServiceReader;

    /**
     * 为图书添加书签
     * @param bookId
     * @param value
     * @param token
     * @return
     */
    @ApiOperation(value = "为图书添加书签")
    @GetMapping("/addBookmark/{bookId}")
    public ResponseResult<String> addBookmark(@PathVariable(value = "bookId",required = true) String bookId, @RequestParam(value = "value",required = true) String value, @RequestHeader(name = "token") String token){
       return booktapServiceReader.addBookmark(bookId,value,token);
    }

    /**
     * 删除书签
     * @param tagId
     * @param token
     * @return
     */
    @ApiOperation(value = "删除书签")
    @DeleteMapping("/deleteBookTag/{tagId}")
    public ResponseResult<String> deleteBookTag(@PathVariable(value = "tagId",required = true) Integer tagId,@RequestParam(value = "bookId") String bookId, @RequestHeader(name = "token") String token){
        return booktagServiceReader.deleteBookTag(tagId,bookId,token);
    }

}
