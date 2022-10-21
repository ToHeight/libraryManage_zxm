package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ScheduledMapper;
import sale.ljw.backend.pojo.Scheduled;
import sale.ljw.librarySystemAdmin.backend.service.ScheduledServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Service实现
 * @createDate 2022-10-17 16:34:24
 */
@Service
public class ScheduledServiceImplAdmin extends ServiceImpl<ScheduledMapper, Scheduled>
        implements ScheduledServiceAdmin {

}



