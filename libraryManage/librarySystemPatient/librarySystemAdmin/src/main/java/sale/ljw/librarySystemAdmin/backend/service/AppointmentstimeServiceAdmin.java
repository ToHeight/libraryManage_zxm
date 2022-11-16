package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Appointmentstime;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【AppointmentsTime】的数据库操作Service
 * @createDate 2022-10-17 16:13:27
 */
public interface AppointmentstimeServiceAdmin extends IService<Appointmentstime> {

    ResponseResult<ArrayList<Map<String, Object>>> queryTimeList();

}
