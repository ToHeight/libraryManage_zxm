package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

//    @ApiOperation(value = "修改登录名和密码")
//    @PostMapping("")

}
