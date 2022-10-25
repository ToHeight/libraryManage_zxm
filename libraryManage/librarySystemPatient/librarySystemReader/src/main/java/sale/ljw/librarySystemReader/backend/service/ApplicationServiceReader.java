package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service
 * @createDate 2022-10-17 16:11:50
 */
public interface ApplicationServiceReader extends IService<Application> {

    ResponseResult<String> cancellationApplication(Long applicationId, String token);
}
