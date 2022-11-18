package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.EditUserByAdmin;
import sale.ljw.backend.form.QueryAllUser;
import sale.ljw.backend.pojo.User;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service
 * @createDate 2022-10-17 16:37:56
 */
public interface UserServiceAdmin extends IService<User> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllUser(QueryAllUser user);

    ResponseResult<String> editUser(EditUserByAdmin editUserByAdmin);

    ResponseResult<String> activateAccount(Integer userId);
}
