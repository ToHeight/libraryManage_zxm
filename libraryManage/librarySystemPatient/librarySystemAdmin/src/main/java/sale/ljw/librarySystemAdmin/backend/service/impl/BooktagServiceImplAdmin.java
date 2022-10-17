package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.BooktagMapper;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.librarySystemAdmin.backend.service.BooktagServiceAdmin;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service实现
 * @createDate 2022-10-17 16:19:03
 */
@Service
public class BooktagServiceImplAdmin extends ServiceImpl<BooktagMapper, Booktag>
        implements BooktagServiceAdmin {

}




