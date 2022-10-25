package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Scheduled;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Service
 * @createDate 2022-10-17 16:34:24
 */
public interface ScheduledServiceReader extends IService<Scheduled> {

    ResponseResult<String> bookReservationByBookName(String bookName, String token);

    ResponseResult<ArrayList<Map<String,Object>>> findBookReservation(String bookName, String bookType, String token);

    ResponseResult<String> amendBookReservations(Integer bookId, String bookName, String token);
}
