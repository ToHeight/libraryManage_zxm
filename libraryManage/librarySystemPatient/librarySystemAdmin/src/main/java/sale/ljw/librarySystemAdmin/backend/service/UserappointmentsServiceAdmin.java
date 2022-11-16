package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Userappointments;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Service
 * @createDate 2022-10-17 16:39:03
 */
public interface UserappointmentsServiceAdmin extends IService<Userappointments> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllAppointment(QueryMessages queryMessages);

    ResponseResult<String> cancelAppointment(Integer appintmentId);
}
