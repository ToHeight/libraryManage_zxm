package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.ModifyReservationStatus;
import sale.ljw.backend.form.QueryPreOrderedBook;
import sale.ljw.backend.pojo.Scheduled;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Service
 * @createDate 2022-10-17 16:34:24
 */
public interface ScheduledServiceAdmin extends IService<Scheduled> {

    ResponseResult<PageInfo<Map<String, Object>>> getAllPreOrderedBook(QueryPreOrderedBook book);

    ResponseResult<String> modifyReservationStatus(ModifyReservationStatus modifyReservationStatus);
}
