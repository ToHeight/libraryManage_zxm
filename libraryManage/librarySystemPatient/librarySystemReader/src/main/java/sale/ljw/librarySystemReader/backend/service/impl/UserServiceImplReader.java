package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.form.ReaderInformation;
import sale.ljw.backend.pojo.User;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.UserServiceReader;
import sale.ljw.librarySystemReader.common.sercurity.utils.EmailUtils;
import sale.ljw.librarySystemReader.common.sercurity.utils.ObsUtil;

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
    private ObsUtil obsUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailUtils emailUtils;

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
        //验证电子邮件是否通过
        if (!readerInformation.getEmailCode().equals(redisTemplate.boundValueOps(readerInformation.getEmail()).get() == null ? "" : redisTemplate.boundValueOps(readerInformation.getEmail()).get())) {
            return ResponseResult.getErrorResult("邮箱验证失败", StatusCode.FORBIDDEN, null);
        }
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
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
        StringBuilder code = new StringBuilder(" ");
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
        redisTemplate.boundValueOps(email).set(code.toString(), 2, TimeUnit.MINUTES);
        return ResponseResult.getSuccessResult(null, "邮件发送成功");
    }
}




