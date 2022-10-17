package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.UserMapper;
import sale.ljw.backend.pojo.User;
import sale.ljw.librarySystemReader.backend.service.UserServiceReader;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service实现
 * @createDate 2022-10-17 16:37:56
 */
@Service
public class UserServiceImplReader extends ServiceImpl<UserMapper, User>
        implements UserServiceReader {

}




