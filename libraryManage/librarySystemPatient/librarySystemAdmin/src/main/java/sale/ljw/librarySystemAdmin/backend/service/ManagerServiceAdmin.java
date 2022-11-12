package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.pojo.Manager;
import sale.ljw.common.common.http.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Manager】的数据库操作Service
 * @createDate 2022-10-17 16:28:59
 */
public interface ManagerServiceAdmin extends IService<Manager> {

    ResponseResult<Map<String, Object>> login(LoginCredentials loginCredentials, HttpServletResponse response);

}
