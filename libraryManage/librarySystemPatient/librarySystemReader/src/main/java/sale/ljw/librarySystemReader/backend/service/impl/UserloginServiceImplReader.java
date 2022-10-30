package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.dao.UserloginMapper;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.pojo.Userlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.UserloginServiceReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserLogin】的数据库操作Service实现
 * @createDate 2022-10-17 16:40:14
 */
@Service
public class UserloginServiceImplReader extends ServiceImpl<UserloginMapper, Userlogin>
        implements UserloginServiceReader {

    @Autowired
    private UserloginMapper userloginMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult<Map<String,Object>> login(LoginCredentials loginCredentials, HttpServletResponse response) {
        //查询
        QueryWrapper<Userlogin> queryWrappper_0 = new QueryWrapper<>();
        queryWrappper_0.eq("user_login", loginCredentials.getUserLoginName()).select("user_passwd","user_id");
        Userlogin userlogin = userloginMapper.selectOne(queryWrappper_0);
        if (userlogin == null) {
            return ResponseResult.getErrorResult("登录失败,用户名不存在", StatusCode.ACCEPTED,null);
        }
        if (BCrypt.checkpw(loginCredentials.getPassword(), userlogin.getUserPasswd())) {
            //给requesy中传输token
            String token = JwtUtils.sign("patient", String.valueOf(userlogin.getUserId()));
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
           Map<String,Object> userInformation= userMapper.findUserInformationLogin(userlogin.getUserId());
            return ResponseResult.getSuccessResult(userInformation,"登录成功！");
        }
        return ResponseResult.getErrorResult("登录失败",StatusCode.ACCEPTED,null);
    }
}




