package sale.ljw.librarySystemAdmin.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.EditPermission;
import sale.ljw.backend.form.RoleInfoData;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.RoleinfoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/roleByAdmin")
public class ARoleInfoServlet {
    @Autowired
    private RoleinfoService roleinfoService;

    /**
     * 获取全部权限
     *
     * @return
     */
    @ApiOperation(value = "获取全部权限")
    @GetMapping("/findAllRoleInfo")
    public ResponseResult<ArrayList<RoleInfoData>> findAllRoleInfo() {
        return roleinfoService.findAllRoleInfo();
    }

    /**
     * 修改权限
     *
     * @param editPermission
     * @return
     */
    @ApiOperation(value = "修改权限")
    @PostMapping("/editPermission")
    public ResponseResult<String> editPermission(@RequestBody @Valid EditPermission editPermission) {
        return roleinfoService.editPermission(editPermission);
    }

    /**
     * 模块下拉列表
     * @return
     */
    @ApiOperation(value = "模块下拉列表")
    @GetMapping("/findAllModule")
    public ResponseResult<ArrayList<Map<String, Object>>> findAllModule() {
        return roleinfoService.findAllModule();
    }

    /**增加权限
     *
     * @param editPermission
     * @return
     */
    @ApiOperation(value = "增加权限")
    @PostMapping("/addRole")
    public ResponseResult<String> addRole(@RequestBody @Valid EditPermission editPermission){
        return roleinfoService.addRole(editPermission);
    }
}