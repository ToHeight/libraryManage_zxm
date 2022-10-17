package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ManagerMapper;
import sale.ljw.backend.pojo.Manager;
import sale.ljw.librarySystemReader.backend.service.ManagerServiceReader;

/**
 * @author 86155
 * @description 针对表【Manager】的数据库操作Service实现
 * @createDate 2022-10-17 16:28:59
 */
@Service
public class ManagerServiceImplReader extends ServiceImpl<ManagerMapper, Manager>
        implements ManagerServiceReader {

}




