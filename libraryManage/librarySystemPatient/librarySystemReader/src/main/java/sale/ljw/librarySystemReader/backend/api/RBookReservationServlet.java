package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.ScheduledServiceReader;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/bookReservationByReader")
public class RBookReservationServlet {
    @Autowired
    private ScheduledServiceReader scheduledServiceReader;

    /**
     * 根据图书姓名预定图书
     *
     * @param bookName
     * @param token
     * @return
     */
    @ApiOperation(value = "根据图书姓名预定图书")
    @GetMapping("/bookReservationByBookName")
    public ResponseResult<String> bookReservationByBookName(@RequestParam(required = true) String bookName, @RequestHeader(name = "token") String token) {
        return scheduledServiceReader.bookReservationByBookName(bookName, token);
    }

    /**
     * 查找预定书籍
     * @param bookName
     * @param bookType
     * @param token
     * @return
     */
    @ApiOperation(value = "查找预定书籍")
    @GetMapping("/findBookReservation")
    public ResponseResult<ArrayList<Map<String,Object>>> findBookReservation(@RequestParam(value = "bookName",required = false) String bookName, @RequestParam(value = "bookType",required = false) String bookType, @RequestHeader(name = "token") String token){
        return scheduledServiceReader.findBookReservation(bookName,bookType,token);
    }

    /**
     * 修改预定书籍
     * @param bookId
     * @param bookName
     * @param token
     * @return
     */
    @ApiOperation(value = "修改预定书籍")
    @GetMapping("/amendBookReservations/{bookId}")
    public ResponseResult<String> amendBookReservations(@PathVariable(value = "bookId") String bookId,@RequestParam(value = "bookName") String bookName, @RequestHeader(name = "token") String token){
        return scheduledServiceReader.amendBookReservations(bookId,bookName,token);
    }

    /**
     * 取消预定图书
     * @param reserveId
     * @param token
     * @return
     */
    @ApiOperation(value = "取消预定图书")
    @GetMapping("/cancelReservation/{reserveId}")
    public ResponseResult<String> cancelReservation(@PathVariable(value = "reserveId") String reserveId,@RequestHeader(name = "token") String token){
        return scheduledServiceReader.cancelReservation(reserveId,token);
    }

}
