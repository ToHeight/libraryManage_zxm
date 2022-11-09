package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.dao.UserloginMapper;
import sale.ljw.backend.form.ReaderInformation;
import sale.ljw.backend.form.RegisteredReader;
import sale.ljw.backend.pojo.User;
import sale.ljw.backend.pojo.Userlogin;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.UserServiceReader;
import sale.ljw.librarySystemReader.common.sercurity.utils.EmailUtils;
import sale.ljw.librarySystemReader.common.sercurity.utils.ObsUtil;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service实现
 * @createDate 2022-10-17 16:37:56
 */
@Service
public class UserServiceImplReader extends ServiceImpl<UserMapper, User>
        implements UserServiceReader {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserloginMapper userloginMapper;
    @Autowired
    private ObsUtil obsUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailUtils emailUtils;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public ResponseResult<Map<String, Object>> getUserInformation(String token) {
        String userId = JwtUtils.parseJWT(token);
        HashMap<String, Object> userInformation = userMapper.getUserInformation(Integer.parseInt(userId));
        return ResponseResult.getSuccessResult(userInformation, "查找个人信息成功");
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        IdWorker idWorker = new IdWorker();
        long fileName = idWorker.nextId();
        try {
            return obsUtil.uploadFile(file, "librarySystem/user_img/" + fileName + ".jpg");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult<String> modifyReaderInformation(ReaderInformation readerInformation, String token) {
        //检测邮箱是否已经存在
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        QueryWrapper<User> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.notIn("user_id", userId).and(q -> {
            q.eq("user_idcard", readerInformation.getIdCard()).or().eq("user_email", readerInformation.getEmail());
        });
        if (userMapper.selectCount(queryWrapper_0) != 0) {
            return ResponseResult.getErrorResult("当前身份证号或邮箱已被注册", StatusCode.NOT_FOUND, null);
        }
        //验证电子邮件是否通过
        String code = (String) redisTemplate.boundValueOps(readerInformation.getEmail()).get();
        try {
            if (!readerInformation.getEmailCode().equalsIgnoreCase(code)) {
                return ResponseResult.getErrorResult("邮箱验证失败", StatusCode.FORBIDDEN, null);
            }
        } catch (NullPointerException e) {
            return ResponseResult.getErrorResult("邮箱验证失败", StatusCode.FORBIDDEN, null);
        }
        //电子邮件通过验证,修改用户信息
        UpdateWrapper<User> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("user_id", userId)
                .set("user_address", readerInformation.getAddress())
                .set("user_birth", readerInformation.getBirthDay())
                .set("user_email", readerInformation.getEmail())
                .set("user_gender", readerInformation.getGender())
                .set("user_idcard", readerInformation.getIdCard())
                .set("user_image", readerInformation.getImage())
                .set("user_telephone", readerInformation.getTelephone())
                .set("user_name", readerInformation.getUserName())
                .set("user_age", new Date().getYear() - readerInformation.getBirthDay().getYear());
        if (userMapper.update(null, updateWrapper_0) == 0) {
            return ResponseResult.getErrorResult("修改个人信息失败", StatusCode.CREATED, null);
        }
        return ResponseResult.getSuccessResult(null, "修改个人信息成功");
    }

    @Override
    public ResponseResult<String> emailVerificationCode(String email) {
        //生成五位验证码,和邮箱一起发送
        String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYZ0123456789";
        Random sc = new Random();
        StringBuilder code = new StringBuilder("");
        for (int i = 0; i < 5; i++) {
            int ax = sc.nextInt(s.length());
            char c = s.charAt(ax);
            code.append(c);
        }
        try {
            //发送邮件
            emailUtils.emailCode(code, email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        //将邮箱和验证码保存至redis中,并设置了两分钟过期
        redisTemplate.boundValueOps(email).set(code, 2, TimeUnit.MINUTES);
        return ResponseResult.getSuccessResult(null, "邮件发送成功");
    }

    @Transactional
    @Override
    public ResponseResult<Map<String, Object>> registeredUser(RegisteredReader registeredReader) {
        synchronized (this) {
            //检测账号是否存在
            QueryWrapper<Userlogin> queryWrapper_0 = new QueryWrapper<>();
            queryWrapper_0.eq("user_login", registeredReader.getLoginName());
            if (userloginMapper.selectCount(queryWrapper_0) != 0) {
                return ResponseResult.getErrorResult("登录账号存在", StatusCode.MOVED_PERM, null);
            }
            //检测email和身份证号是否存在
            QueryWrapper<User> queryWrapper_1 = new QueryWrapper<>();
            queryWrapper_1.eq("user_idcard", registeredReader.getIdCard()).or().eq("user_email", registeredReader.getEmail());
            ;
            if (userMapper.selectCount(queryWrapper_1) != 0) {
                return ResponseResult.getErrorResult("当前身份证号或邮箱已被注册", StatusCode.MOVED_PERM, null);
            }
            return transactionTemplate.execute(transactionStatus -> {
                Userlogin userlogin = new Userlogin();
                userlogin.setUserLogin(registeredReader.getLoginName());
                userlogin.setUserPasswd(BCrypt.hashpw(registeredReader.getPassword(), BCrypt.gensalt()));
                Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                if (userloginMapper.insert(userlogin) == 0) {
                    return ResponseResult.getErrorResult("当前登录账号添加失败", StatusCode.NOT_MODIFIED, null);
                }
                User user = new User();
                user.setUserId(userlogin.getUserId());
                user.setUserAddress(registeredReader.getAddress());
                user.setUserAge(new Date().getYear() - registeredReader.getBirthDay().getYear());
                user.setUserBirth(registeredReader.getBirthDay());
                user.setUserEmail(registeredReader.getEmail());
                user.setUserGender(registeredReader.getGender());
                user.setUserIdcard(registeredReader.getIdCard());
                user.setUserName(registeredReader.getUserName());
                user.setUserTelephone(registeredReader.getTelephone());
                if (userMapper.insert(user) == 0) {
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                    return ResponseResult.getErrorResult("用户资料账号添加失败", StatusCode.NOT_MODIFIED, null);
                }
                //账号添加成功，发送电子邮件
                IdWorker idWorker = new IdWorker();
                String key = idWorker.nextId() + "";
                //将用户名和密钥保存至redis中
                redisTemplate.boundValueOps(key).set(user.getUserId(), 10, TimeUnit.MINUTES);
                try {
                    emailUtils.activationEmail(key, registeredReader.getEmail());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return ResponseResult.getSuccessResult(null, "账号创建成功,请检查邮件激活账号");
            });
        }
    }

    @Override
    public ResponseResult<String> detectUsername(String loginName) {
        QueryWrapper<Userlogin> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("user_login", loginName);
        if (userloginMapper.selectCount(queryWrapper_0) != 0) {
            return ResponseResult.getErrorResult("登录账号存在", StatusCode.MOVED_PERM, null);
        }
        return ResponseResult.getSuccessResult(null, "账号可用");
    }

    @Override
    public ResponseResult<String> activateAccount(String code) {
        //从redis中查找code对应的用户名
        Integer userId = (Integer) redisTemplate.boundValueOps(code).get();
        if (userId == null) {
            return ResponseResult.getErrorResult("激活失败，激活超时", StatusCode.NOT_FOUND, null);
        }
        UpdateWrapper<User> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("user_id", userId).set("status", "ASU01");
        if (userMapper.update(null, updateWrapper_0) == 0) {
            return ResponseResult.getErrorResult("激活失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null, "激活成功");
    }
}




