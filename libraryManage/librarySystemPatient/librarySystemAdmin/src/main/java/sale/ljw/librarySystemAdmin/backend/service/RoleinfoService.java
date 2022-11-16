package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.form.EditPermission;
import sale.ljw.backend.form.RoleInfoData;
import sale.ljw.backend.pojo.Roleinfo;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【roleInfo】的数据库操作Service
* @createDate 2022-11-11 15:32:10
*/
public interface RoleinfoService extends IService<Roleinfo> {

    ResponseResult<ArrayList<RoleInfoData>> findAllRoleInfo();

    ResponseResult<String> editPermission(EditPermission editPermission);

    ResponseResult<ArrayList<Map<String, Object>>> findAllModule();

    ResponseResult<String> addRole(EditPermission editPermission);

}
