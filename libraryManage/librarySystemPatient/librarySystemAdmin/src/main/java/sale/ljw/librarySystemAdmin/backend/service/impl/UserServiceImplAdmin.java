package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.pojo.User;
import sale.ljw.librarySystemAdmin.backend.service.UserServiceAdmin;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service实现
 * @createDate 2022-10-17 16:37:56
 */
@Service
public class UserServiceImplAdmin extends ServiceImpl<UserMapper, User>
        implements UserServiceAdmin {

}




