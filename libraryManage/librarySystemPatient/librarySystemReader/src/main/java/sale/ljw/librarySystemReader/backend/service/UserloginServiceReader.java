package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.form.ChangePasswordForm;
import sale.ljw.backend.form.EmailLoginCredentials;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.pojo.Userlogin;
import sale.ljw.common.common.http.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserLogin】的数据库操作Service
 * @createDate 2022-10-17 16:40:14
 */
public interface UserloginServiceReader extends IService<Userlogin> {

    ResponseResult<Map<String,Object>> login(LoginCredentials loginCredentials, HttpServletResponse response);

    ResponseResult<Map<String, Object>> emailLogin(EmailLoginCredentials emailLoginCredentials, HttpServletResponse response);

    ResponseResult<String> forgotPassword(String email);

    ResponseResult<String> changePasswordByEmail(ChangePasswordForm changePassword);
}
