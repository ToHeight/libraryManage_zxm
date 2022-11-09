package sale.ljw.librarySystemReader.backend.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.dao.UserloginMapper;
import sale.ljw.backend.form.ChangePasswordForm;
import sale.ljw.backend.form.EmailLoginCredentials;
import sale.ljw.backend.form.LoginCredentials;
import sale.ljw.backend.pojo.User;
import sale.ljw.backend.pojo.Userlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.UserloginServiceReader;
import sale.ljw.librarySystemReader.common.sercurity.utils.EmailUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ResponseResult<Map<String, Object>> login(LoginCredentials loginCredentials, HttpServletResponse response) {
        //查询
        QueryWrapper<Userlogin> queryWrappper_0 = new QueryWrapper<>();
        queryWrappper_0.eq("user_login", loginCredentials.getUserLoginName()).select("user_passwd", "user_id");
        Userlogin userlogin = userloginMapper.selectOne(queryWrappper_0);
        if (userlogin == null) {
            return ResponseResult.getErrorResult("登录失败,用户名不存在或账号未激活", StatusCode.ACCEPTED, null);
        }
        if (BCrypt.checkpw(loginCredentials.getPassword(), userlogin.getUserPasswd())) {
            //给requesy中传输token
            String token = JwtUtils.sign("reader", String.valueOf(userlogin.getUserId()));
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
            Map<String, Object> userInformation = userMapper.findUserInformationLogin(userlogin.getUserId());
            if (userInformation == null) {
                return ResponseResult.getErrorResult("用户当前账号未激活！", StatusCode.NOT_FOUND, null);
            }
            return ResponseResult.getSuccessResult(userInformation, "登录成功！");
        }
        return ResponseResult.getErrorResult("登录失败", StatusCode.ACCEPTED, null);
    }

    @Override
    public ResponseResult<Map<String, Object>> emailLogin(EmailLoginCredentials emailLoginCredentials, HttpServletResponse response) {
        String code = (String) redisTemplate.boundValueOps(emailLoginCredentials.getEmail()).get();
        if (code == null) {
            return ResponseResult.getErrorResult("验证码超时", StatusCode.ACCEPTED, null);
        }
        if (code.equalsIgnoreCase(emailLoginCredentials.getCode())) {
            //查询信息
            HashMap<String, Object> userInformation = userMapper.findUserInformationLoginByEnail(emailLoginCredentials.getEmail());
            if (userInformation == null) {
                return ResponseResult.getErrorResult("用户不存在，请注册账号", StatusCode.NOT_FOUND, null);
            }
            String token = JwtUtils.sign("reader", String.valueOf(userInformation.get("id")));
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", token);
            return ResponseResult.getSuccessResult(userInformation, "登录成功！");
        }
        return ResponseResult.getErrorResult("验证码错误，登陆失败", StatusCode.Requested_range_not_satisfiable, null);
    }

    @Override
    public ResponseResult<String> forgotPassword(String email) {
        //获取用户id绑定关系
        QueryWrapper<User> queryWrapper_user = new QueryWrapper<>();
        queryWrapper_user.eq("user_email", email).select("user_id", "user_name");
        User user = userMapper.selectOne(queryWrapper_user);
        if (user == null) {
            return ResponseResult.getErrorResult("用户不存在，请注册账号", StatusCode.NOT_FOUND, null);
        }
        //发送电子邮件
        String code = new IdWorker().nextId() + "";
        //绑定
        redisTemplate.boundValueOps(code).set(user.getUserId(),10, TimeUnit.MINUTES);
        try {
            emailUtils.forgotPasswordEmail(user.getUserName(), code, email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.getSuccessResult(null, "电子邮件发送成功");
    }

    @Override
    public ResponseResult<String> changePasswordByEmail(ChangePasswordForm changePassword) {
        //获取id
        Integer userId = (Integer) redisTemplate.boundValueOps(changePassword.getCode()).get();
        //检测过期
        if(userId==null){
            return ResponseResult.getErrorResult("验证码超时", StatusCode.ACCEPTED, null);
        }
        //修改密码
        UpdateWrapper<Userlogin> updateWrapper_login=new UpdateWrapper<>();
        updateWrapper_login.eq("user_id",userId).set("user_login", changePassword.getLoginName()).set("user_passwd", BCrypt.hashpw(changePassword.getPassword(), BCrypt.gensalt()));
       if( userloginMapper.update(null,updateWrapper_login)==0){
           return ResponseResult.getErrorResult("修改失败", StatusCode.NO_CONTENT,null);
       }
        return ResponseResult.getSuccessResult(null, "修改成功！");
    }
}




