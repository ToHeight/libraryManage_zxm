package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.FindBorrowedBooks;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BorrowServiceReader;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/bookBorrowByReader")
public class RBookBorrowServlet {

    @Autowired
    private BorrowServiceReader borrowServiceReader;

    /**
     * 借阅图书
     *
     * @param bookId
     * @param token
     * @return
     */
    @ApiOperation(value = "借阅图书")
    @GetMapping("/borrowBook/{bookId}")
    public ResponseResult<String> borrowBook(@PathVariable String bookId, @RequestHeader(name = "token") String token) {
        return borrowServiceReader.borrowBook(bookId, token);
    }

    /**
     * 超时未还书
     *
     * @param borrowId
     */
    @RabbitListener(queues = "librarySystemReaderBorrowTimeOut")
    public void ListenerQueue(Integer borrowId) {
        borrowServiceReader.booksNotReturnedOverTime(borrowId);
    }

    /**
     * 查询借阅图书
     * @return
     */
    @ApiOperation(value = "查询借阅图书")
    @PostMapping("/findBorrowedBooks")
    public ResponseResult<PageInfo<Map<String, Object>>> findBorrowedBooks(@RequestBody @Valid FindBorrowedBooks findBorrowedBooks,@RequestHeader(name = "token") String token){
        return borrowServiceReader.findBorrowedBooks(findBorrowedBooks,token);
    }

    /**
     * 归还图书
     * @param borrowId
     * @param token
     * @return
     */
    @ApiOperation(value = "归还图书")
    @GetMapping("/returnBooks/{borrowId}")
    public ResponseResult<String> returnBooks(@PathVariable(value = "borrowId") String borrowId,@RequestHeader(name = "token") String token){
        return borrowServiceReader.returnBooks(borrowId,token);
    }

    /**
     * 删除借阅记录
     * @param borrowId
     * @param token
     * @return
     */
    @ApiOperation(value = "删除记录")
    @DeleteMapping("/deleteBorrowingRecord/{borrowId}")
    public ResponseResult<String> deleteBorrowingRecord(@PathVariable(value = "borrowId") String borrowId,@RequestHeader(name = "token") String token){
        return borrowServiceReader.deleteBorrowingRecord(borrowId,token);
    }
}
