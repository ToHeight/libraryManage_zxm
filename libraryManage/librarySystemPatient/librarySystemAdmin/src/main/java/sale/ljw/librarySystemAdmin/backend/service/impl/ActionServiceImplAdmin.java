package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ActionMapper;
import sale.ljw.backend.pojo.Action;
import sale.ljw.librarySystemAdmin.backend.service.ActionServiceAdmin;

/**
 * @author 86155
 * @description 针对表【Action】的数据库操作Service实现
 * @createDate 2022-10-17 15:29:15
 */
@Deprecated
@Service
public class ActionServiceImplAdmin extends ServiceImpl<ActionMapper, Action>
        implements ActionServiceAdmin {
    @Autowired
    private ActionMapper actionMapper;
    @Override
    public int checkPermissions(String requestURI, String permission) {
        return actionMapper.checkPermissions(requestURI,permission);
    }
}




