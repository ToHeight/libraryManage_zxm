package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserappointmentsMapper;
import sale.ljw.backend.pojo.Userappointments;
import sale.ljw.librarySystemAdmin.backend.service.UserappointmentsServiceAdmin;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Service实现
 * @createDate 2022-10-17 16:39:03
 */
@Service
public class UserappointmentsServiceImplAdmin extends ServiceImpl<UserappointmentsMapper, Userappointments>
        implements UserappointmentsServiceAdmin {

}



