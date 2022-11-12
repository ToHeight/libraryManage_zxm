package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Action;

/**
 * @author 86155
 * @description 针对表【Action】的数据库操作Service
 * @createDate 2022-10-17 15:29:15
 */
public interface ActionServiceAdmin extends IService<Action> {

    int checkPermissions(String requestURI, String permission);
}
