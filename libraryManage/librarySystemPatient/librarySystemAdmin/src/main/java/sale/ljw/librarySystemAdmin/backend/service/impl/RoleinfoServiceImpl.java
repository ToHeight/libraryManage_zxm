package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.backend.dao.RoleinfoMapper;
import sale.ljw.backend.dao.RolemoduleMapper;
import sale.ljw.backend.form.EditPermission;
import sale.ljw.backend.form.RoleInfoData;
import sale.ljw.backend.pojo.Roleinfo;
import sale.ljw.backend.pojo.Rolemodule;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.RoleinfoService;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【roleInfo】的数据库操作Service实现
 * @createDate 2022-11-11 15:32:10
 */
@Service
public class RoleinfoServiceImpl extends ServiceImpl<RoleinfoMapper, Roleinfo>
        implements RoleinfoService {
    @Autowired
    private RoleinfoMapper roleinfoMapper;
    @Autowired
    private RolemoduleMapper rolemoduleMapper;
    @Override
    public ResponseResult<ArrayList<RoleInfoData>> findAllRoleInfo() {
        ArrayList<RoleInfoData> allRoleInfo = roleinfoMapper.findAllRoleInfo();
        return ResponseResult.getSuccessResult(allRoleInfo, "查询成功");
    }

    @Override
    public ResponseResult<String> editPermission(EditPermission editPermission) {
        QueryWrapper<Rolemodule> deleteWrapper_0=new QueryWrapper<>();
        deleteWrapper_0.eq("role_id", editPermission.getRoleId());
        rolemoduleMapper.delete(deleteWrapper_0);
        roleinfoMapper.addModule(editPermission.getRoleId(),editPermission.getModuleId());
        return ResponseResult.getSuccessResult(null, "修改成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> findAllModule() {
        return ResponseResult.getSuccessResult(rolemoduleMapper.findAllModule(), "查询成功");
    }

    @Transactional
    @Override
    public ResponseResult<String> addRole(EditPermission editPermission) {
        Roleinfo roleInfo=new Roleinfo();
        roleInfo.setName(editPermission.getRoleName());
        roleinfoMapper.insert(roleInfo);
        roleinfoMapper.addModule(roleInfo.getRoleId(),editPermission.getModuleId());
        return ResponseResult.getSuccessResult(null, "添加成功");
    }
}




