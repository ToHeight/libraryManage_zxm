package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.pojo.Book;
import sale.ljw.librarySystemAdmin.backend.service.BookServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service实现
 * @createDate 2022-10-17 16:14:59
 */
@Service
public class BookServiceImplAdmin extends ServiceImpl<BookMapper, Book>
        implements BookServiceAdmin {

}




