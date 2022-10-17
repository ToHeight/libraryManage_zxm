package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ActivityMapper;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.librarySystemAdmin.backend.service.ActivityServiceAdmin;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service实现
 * @createDate 2022-10-17 16:02:13
 */
@Service
public class ActivityServiceImplAdmin extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityServiceAdmin {

}




