package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.AddManager;
import sale.ljw.backend.form.EditManagerInformation;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ManagerServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/managerByAdmin")
public class AManagerServlet {
    @Autowired
    private ManagerServiceAdmin managerServiceAdmin;

    /**
     * 获取全部管理员信息
     * @param queryMessages
     * @return
     */
    @ApiOperation(value = "获取全部管理员信息")
    @PreAuthorize("hasAnyAuthority('9') or hasAnyRole('1','4')")
    @PostMapping("/findAllManager")
    public ResponseResult<PageInfo<Map<String,Object>>> findAllManager(@RequestBody @Valid QueryMessages queryMessages){
        return managerServiceAdmin.findAllManager(queryMessages);
    }

    /**
     * 修改账号信息
     * @param managerInformation
     * @return
     */
    @ApiOperation(value = "修改账号信息")
    @PreAuthorize("hasAnyAuthority('9') or hasAnyRole('1','4')")
    @PostMapping("/editManagerInformation")
    public ResponseResult<String> editManagerInformation(@RequestBody @Valid EditManagerInformation managerInformation){
        return managerServiceAdmin.editManagerInformation(managerInformation);
    }

    /**
     * 增加账号
     * @param addManager
     * @return
     */
    @ApiOperation(value = "增加账号")
    @PreAuthorize("hasAnyAuthority('9') or hasAnyRole('1','4')")
    @PostMapping("/addManager")
    public ResponseResult<String> addManager(@RequestBody @Valid AddManager addManager){
        return managerServiceAdmin.addManager(addManager);
    }

    /**
     * 修改密码
     * @param loginCredentials
     * @param token
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAnyAuthority('10') or hasAnyRole('1')")
    @PostMapping("/editPassword")
    public ResponseResult<String> editPassword(@RequestBody @Valid LoginCredentials loginCredentials, @RequestHeader(value = "token") String token){
        return managerServiceAdmin.editPassword(loginCredentials,token);
    }

}
