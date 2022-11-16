package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service
 * @createDate 2022-10-17 16:11:50
 */
public interface ApplicationServiceAdmin extends IService<Application> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllApplication(QueryMessages queryMessages);

    ResponseResult<String> signUpSuccessfully(Integer applicationId);

    ResponseResult<String> cancelRegistration(Integer applicationId);
}
