package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.backend.dao.ManagerMapper;
import sale.ljw.backend.dao.ManagerloginMapper;
import sale.ljw.backend.form.*;
import sale.ljw.backend.pojo.Manager;
import sale.ljw.backend.pojo.Managerlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemAdmin.backend.service.ManagerServiceAdmin;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Manager】的数据库操作Service实现
 * @createDate 2022-10-17 16:28:59
 */
@Service
public class ManagerServiceImplAdmin extends ServiceImpl<ManagerMapper, Manager>
        implements ManagerServiceAdmin {
    @Autowired
    private ManagerloginMapper managerloginMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public ResponseResult<LoginAdminInformation> login(LoginCredentials loginCredentials, HttpServletResponse response) {
        //查询
        QueryWrapper<Managerlogin> queryWrappper_0 = new QueryWrapper<>();
        queryWrappper_0.eq("manager_login", loginCredentials.getUserLoginName()).select("permission", "manager_passwd", "manager_id");
        Managerlogin adminLogin = managerloginMapper.selectOne(queryWrappper_0);
        if (adminLogin == null) {
            return ResponseResult.getErrorResult("登录失败,账号不存在或账号删除", StatusCode.ACCEPTED, null);
        }
        if (BCrypt.checkpw(loginCredentials.getPassword(), adminLogin.getManagerPasswd())) {
            LoginAdminInformation userInformationLogin = managerMapper.findUserInformationLogin(adminLogin.getManagerId());
            if (userInformationLogin == null) {
                return ResponseResult.getErrorResult("账号已被删除！", StatusCode.NOT_FOUND, null);
            }
            //给requesy中传输token
            String token = JwtUtils.sign(adminLogin.getPermission() + "", String.valueOf(adminLogin.getManagerId()));
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
            return ResponseResult.getSuccessResult(userInformationLogin, "登录成功！");
        }
        return ResponseResult.getErrorResult("登录失败", StatusCode.ACCEPTED, null);
    }

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllManager(QueryMessages queryMessages) {
        PageHelper.startPage(queryMessages.getPage(), 10);
        ArrayList<Map<String, Object>> managerList = managerMapper.findAllManager(queryMessages);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(managerList);
        return ResponseResult.getSuccessResult(pageInfo, "获取全部数据");
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ResponseResult<String> editManagerInformation(EditManagerInformation managerInformation) {
        //更新字段
        UpdateWrapper<Manager> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("manager_id", managerInformation.getManagerId())
                .set("manager_gender", managerInformation.getGender())
                .set("manager_name", managerInformation.getManagerName())
                .set("manager_telephone", managerInformation.getTelephone());
        managerMapper.update(null, updateWrapper_0);
        UpdateWrapper<Managerlogin> updateWrapper_1 = new UpdateWrapper<>();
        updateWrapper_1.eq("manager_id", managerInformation.getManagerId()).set("permission", managerInformation.getPermissionId());
        managerloginMapper.update(null, updateWrapper_1);
        return ResponseResult.getSuccessResult(null, "修改成功");
    }

    @Transactional
    @Override
    public ResponseResult<String> addManager(AddManager addManager) {
        //添加账号
        Managerlogin managerLogin = new Managerlogin();
        managerLogin.setManagerLogin(addManager.getTelephone());
        managerLogin.setPermission(addManager.getPermissionId());
        managerloginMapper.insert(managerLogin);
        Manager manager = new Manager();
        manager.setManagerAddress(addManager.getAddress());
        manager.setManagerId(managerLogin.getManagerId());
        manager.setManagerAge(addManager.getAge());
        manager.setManagerGender(addManager.getGender());
        manager.setManagerName(addManager.getManagerName());
        manager.setManagerTelephone(addManager.getTelephone());
        managerMapper.insert(manager);
        return ResponseResult.getSuccessResult(null, "添加成功");
    }

    @Override
    public ResponseResult<String> editPassword(LoginCredentials loginCredentials, String token) {
        Integer managerId = Integer.parseInt(JwtUtils.parseJWT(token));
        UpdateWrapper<Managerlogin> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("manager_id", managerId)
                .set("manager_login", loginCredentials.getUserLoginName())
                .set("manager_passwd", BCrypt.hashpw(loginCredentials.getPassword(), BCrypt.gensalt()));
        managerloginMapper.update(null, updateWrapper_0);
        return ResponseResult.getSuccessResult(null, "修改成功");
    }
}




