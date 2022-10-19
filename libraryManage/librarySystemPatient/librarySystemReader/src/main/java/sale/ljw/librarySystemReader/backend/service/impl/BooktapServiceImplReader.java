package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BooktapMapper;
import sale.ljw.backend.pojo.Booktap;
import sale.ljw.librarySystemReader.backend.service.BooktapServiceReader;

/**
 * @author 86155
 * @description 针对表【bookTap】的数据库操作Service实现
 * @createDate 2022-10-17 16:22:46
 */
@Service
public class BooktapServiceImplReader extends ServiceImpl<BooktapMapper, Booktap>
        implements BooktapServiceReader {

}




