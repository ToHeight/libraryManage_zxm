package sale.ljw.librarySystemAdmin.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.backend.form.LoginAdminInformation;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ManagerServiceAdmin;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/loginAdmin")
public class ALoginServlet {
    @Autowired
    private ManagerServiceAdmin managerServiceAdmin;

//    @ApiOperation(value = "管理员登录")
//    @PostMapping(path = "/login")
    public ResponseResult<LoginAdminInformation> login(@RequestBody @Valid LoginCredentials loginCredentials, HttpServletResponse response) {
        return managerServiceAdmin.login(loginCredentials, response);
    }
}
