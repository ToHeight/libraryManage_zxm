package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.backend.form.ReaderInformation;
import sale.ljw.backend.pojo.User;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service
 * @createDate 2022-10-17 16:37:56
 */
public interface UserServiceReader extends IService<User> {

    ResponseResult<Map<String, Object>> getUserInformation(String token);

    String uploadAvatar(MultipartFile file);

    ResponseResult<String> modifyReaderInformation(ReaderInformation readerInformation, String token);

    ResponseResult<String> emailVerificationCode(String email);
}
