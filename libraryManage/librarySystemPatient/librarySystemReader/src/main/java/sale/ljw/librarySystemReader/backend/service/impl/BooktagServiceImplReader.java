package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BooktagMapper;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.librarySystemReader.backend.service.BooktagServiceReader;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service实现
 * @createDate 2022-10-17 16:19:03
 */
@Service
public class BooktagServiceImplReader extends ServiceImpl<BooktagMapper, Booktag>
        implements BooktagServiceReader {

}




