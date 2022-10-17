package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.BorrowMapper;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.librarySystemAdmin.backend.service.BorrowServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Service实现
 * @createDate 2022-10-17 16:24:02
 */
@Service
public class BorrowServiceImplAdmin extends ServiceImpl<BorrowMapper, Borrow>
        implements BorrowServiceAdmin {

}




