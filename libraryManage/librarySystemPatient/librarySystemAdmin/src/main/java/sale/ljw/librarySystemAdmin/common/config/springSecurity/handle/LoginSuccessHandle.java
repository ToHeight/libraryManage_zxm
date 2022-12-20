package sale.ljw.librarySystemAdmin.common.config.springSecurity.handle;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sale.ljw.backend.dao.ManagerMapper;
import sale.ljw.backend.dao.ManagerloginMapper;
import sale.ljw.backend.form.LoginAdminInformation;
import sale.ljw.backend.pojo.Managerlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.utils.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>application name：librarySystemPatient - LoginSuccessHandle</p>
 * <p>application describing： </p>
 * <p>copyright： </p>
 * <p>company： </p>
 * <p>time：2022-12-14 16:09:35</p>
 *
 * @author liujingwen
 * @version ver 1.0
 * @since 1.8
 */
@Component
public class LoginSuccessHandle implements AuthenticationSuccessHandler {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ManagerloginMapper managerloginMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //得到用户的用户名
        String username = authentication.getName();
        //存入到redis中 username:权限
        redisTemplate.opsForValue().set(username,authentication.getAuthorities());
        synchronized (this) {
            QueryWrapper<Managerlogin> queryWrappper_0 = new QueryWrapper<>();
            queryWrappper_0.eq("manager_login", username).select("permission", "manager_passwd", "manager_id");
            Managerlogin adminLogin = managerloginMapper.selectOne(queryWrappper_0);
            LoginAdminInformation userInformationLogin = managerMapper.findUserInformationLogin(adminLogin.getManagerId());
            //给requesy中传输token
            String token = JwtUtils.signAdmin(adminLogin.getPermission() + "", String.valueOf(adminLogin.getManagerId()),username);
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            Map<String,String> msg=new HashMap<>();
            msg.put("sessionId", request.getSession().getId());
            response.getWriter().write(JSON.toJSONString(ResponseResult.getSuccessResult(userInformationLogin, "welcome to login this system",msg)));
        }
    }
}
