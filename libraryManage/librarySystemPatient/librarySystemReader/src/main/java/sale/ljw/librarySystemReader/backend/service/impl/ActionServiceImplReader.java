package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.ActionMapper;
import sale.ljw.backend.pojo.Action;
import sale.ljw.librarySystemReader.backend.service.ActionServiceReader;

/**
 * @author 86155
 * @description 针对表【Action】的数据库操作Service实现
 * @createDate 2022-10-17 15:29:15
 */
@Service
public class ActionServiceImplReader extends ServiceImpl<ActionMapper, Action>
        implements ActionServiceReader {

}




