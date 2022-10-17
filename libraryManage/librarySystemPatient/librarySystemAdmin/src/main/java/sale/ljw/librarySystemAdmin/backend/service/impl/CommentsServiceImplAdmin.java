package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.mapper.CommentsMapper;
import sale.ljw.backend.pojo.Comments;
import sale.ljw.librarySystemAdmin.backend.service.CommentsServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Service实现
 * @createDate 2022-10-17 16:25:04
 */
@Service
public class CommentsServiceImplAdmin extends ServiceImpl<CommentsMapper, Comments>
        implements CommentsServiceAdmin {

}




