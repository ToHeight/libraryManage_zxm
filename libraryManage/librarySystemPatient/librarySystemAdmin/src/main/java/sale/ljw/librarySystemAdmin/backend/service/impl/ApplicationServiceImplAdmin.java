package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ApplicationMapper;
import sale.ljw.backend.pojo.Application;
import sale.ljw.librarySystemAdmin.backend.service.ApplicationServiceAdmin;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service实现
 * @createDate 2022-10-17 16:11:50
 */
@Service
public class ApplicationServiceImplAdmin extends ServiceImpl<ApplicationMapper, Application>
        implements ApplicationServiceAdmin {

}




