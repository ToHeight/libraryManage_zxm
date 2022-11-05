package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.form.ReaderInformation;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.UserServiceReader;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/userInformationByReader")
public class RUserInformationServlet {
    @Autowired
    private UserServiceReader userServiceReader;

    /**
     * 获取用户基本信息
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/getUserInformation")
    public ResponseResult<Map<String, Object>> getUserInformation(@RequestHeader(name = "token") String token) {
        return userServiceReader.getUserInformation(token);
    }

    /**
     * 上传头像地址
     * @param file
     * @return
     */
    @ApiOperation(value = "上传头像地址")
    @PostMapping("/uploadAvatarByReader")
    public String uploadAvatar(@RequestBody MultipartFile file) {
        return userServiceReader.uploadAvatar(file);
    }

    /**
     * 修改个人信息
     * @param readerInformation
     * @param token
     * @return
     */
    @ApiOperation(value = "修改个人信息")
    @PostMapping("/modifyReaderInformation")
    public ResponseResult<String> modifyReaderInformation(@RequestBody ReaderInformation readerInformation, @RequestHeader(name = "token") String token){
        return userServiceReader.modifyReaderInformation(readerInformation,token);
    }

    /**
     * 邮箱验证码
     * @param email
     * @return
     */
    @ApiOperation(value = "邮箱验证码")
    @GetMapping("/emailVerificationCode")
    public ResponseResult<String> emailVerificationCode(@RequestParam(value = "email") @Valid @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "电子邮件格式错误")  String email){
        return userServiceReader.emailVerificationCode(email);
    }

}
