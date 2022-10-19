package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.TypeMapper;
import sale.ljw.backend.pojo.Type;
import sale.ljw.librarySystemAdmin.backend.service.TypeServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Type】的数据库操作Service实现
 * @createDate 2022-10-17 16:36:05
 */
@Service
public class TypeServiceImplAdmin extends ServiceImpl<TypeMapper, Type>
        implements TypeServiceAdmin {

}




