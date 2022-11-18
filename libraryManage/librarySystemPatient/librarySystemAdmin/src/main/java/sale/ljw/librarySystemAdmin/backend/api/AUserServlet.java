package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.EditUserByAdmin;
import sale.ljw.backend.form.QueryAllUser;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.UserServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/userByAdmin")
public class AUserServlet {
    @Autowired
    private UserServiceAdmin userServiceAdmin;

    /**
     * 获取全部用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "获取全部用户")
    @PostMapping("/findAllUser")
    public ResponseResult<PageInfo<Map<String, Object>>> findAllUser(@RequestBody @Valid QueryAllUser user) {
        return userServiceAdmin.findAllUser(user);
    }

    /**
     * 编辑用户
     *
     * @param editUserByAdmin
     * @return
     */
    @ApiOperation(value = "编辑用户")
    @PostMapping("/editUser")
    public ResponseResult<String> editUser(@RequestBody @Valid EditUserByAdmin editUserByAdmin) {
        return userServiceAdmin.editUser(editUserByAdmin);
    }

    /**
     * 激活账号
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "激活账号")
    @GetMapping("/activateAccount/{userId}")
    public ResponseResult<String> activateAccount(@PathVariable Integer userId) {
        return userServiceAdmin.activateAccount(userId);
    }
}
