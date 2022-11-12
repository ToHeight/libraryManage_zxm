package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ManagerMapper;
import sale.ljw.backend.dao.ManagerloginMapper;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.pojo.Manager;
import sale.ljw.backend.pojo.Managerlogin;
import sale.ljw.backend.pojo.Userlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemAdmin.backend.service.ManagerServiceAdmin;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseResult<Map<String, Object>> login(LoginCredentials loginCredentials, HttpServletResponse response) {
        //查询
        QueryWrapper<Managerlogin> queryWrappper_0 = new QueryWrapper<>();
        queryWrappper_0.eq("manager_login", loginCredentials.getUserLoginName()).select("permission", "manager_passwd","manager_id");
        Managerlogin adminLogin = managerloginMapper.selectOne(queryWrappper_0);
        if (adminLogin == null) {
            return ResponseResult.getErrorResult("登录失败,账号不存在或账号删除", StatusCode.ACCEPTED, null);
        }
        if (BCrypt.checkpw(loginCredentials.getPassword(), adminLogin.getManagerPasswd())) {
            Map<String, Object> userInformation = managerMapper.findUserInformationLogin(adminLogin.getManagerId());
            if (userInformation == null) {
                return ResponseResult.getErrorResult("账号已被删除！", StatusCode.NOT_FOUND, null);
            }
            //给requesy中传输token
            String token = JwtUtils.sign(adminLogin.getPermission()+"", String.valueOf(adminLogin.getManagerId()));
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
            return ResponseResult.getSuccessResult(userInformation, "登录成功！");
        }
        return ResponseResult.getErrorResult("登录失败", StatusCode.ACCEPTED, null);
    }
}




