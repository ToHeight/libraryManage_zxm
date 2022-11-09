package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.ChangePasswordForm;
import sale.ljw.backend.form.EmailLoginCredentials;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.UserloginServiceReader;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/readerLogin")
public class RLoginServlet {

    @Autowired
    private UserloginServiceReader userloginServiceReader;

    /**
     * 用户登录
     * @param loginCredentials
     * @param response
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(path = "/login")
    public ResponseResult<Map<String,Object>> login(@RequestBody @Valid LoginCredentials loginCredentials, HttpServletResponse response){
        return userloginServiceReader.login(loginCredentials,response);
    }

    /**
     * 邮件验证码登录
     * @param emailLoginCredentials
     * @return
     */
    @ApiOperation(value = "邮件验证码登录")
    @PostMapping("/emailLogin")
    public ResponseResult<Map<String,Object>> emailLogin(@RequestBody @Valid EmailLoginCredentials emailLoginCredentials, HttpServletResponse response){
        return userloginServiceReader.emailLogin(emailLoginCredentials,response);
    }

    /**
     * 忘记密码
     * @param email
     * @return
     */
    @ApiOperation(value = "忘记密码")
    @GetMapping("/forgotPassword")
    public ResponseResult<String> forgotPassword(@RequestParam(value = "email") String email){
        return userloginServiceReader.forgotPassword(email);
    }


    @ApiOperation(value = "修改密码")
    @PostMapping("/changePasswordByEmail")
    public ResponseResult<String> changePasswordByEmail(@RequestBody @Valid ChangePasswordForm changePassword){
        return userloginServiceReader.changePasswordByEmail(changePassword);
    }
}
