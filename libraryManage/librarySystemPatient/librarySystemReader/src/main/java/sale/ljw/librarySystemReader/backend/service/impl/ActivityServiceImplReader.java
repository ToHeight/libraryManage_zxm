package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ActivityMapper;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.librarySystemReader.backend.service.ActivityServiceReader;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service实现
 * @createDate 2022-10-17 16:02:13
 */
@Service
public class ActivityServiceImplReader extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityServiceReader {

}




