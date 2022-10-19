package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BookshelfMapper;
import sale.ljw.backend.pojo.Bookshelf;
import sale.ljw.librarySystemAdmin.backend.service.BookshelfServiceAdmin;

/**
 * @author 86155
 * @description 针对表【BookShelf】的数据库操作Service实现
 * @createDate 2022-10-17 16:17:30
 */
@Service
public class BookshelfServiceImplAdmin extends ServiceImpl<BookshelfMapper, Bookshelf>
        implements BookshelfServiceAdmin {

}




