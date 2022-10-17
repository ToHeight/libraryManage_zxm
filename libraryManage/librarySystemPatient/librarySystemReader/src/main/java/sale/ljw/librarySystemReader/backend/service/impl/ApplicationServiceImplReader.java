package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ApplicationMapper;
import sale.ljw.backend.pojo.Application;
import sale.ljw.librarySystemReader.backend.service.ApplicationServiceReader;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service实现
 * @createDate 2022-10-17 16:11:50
 */
@Service
public class ApplicationServiceImplReader extends ServiceImpl<ApplicationMapper, Application>
        implements ApplicationServiceReader {

}




