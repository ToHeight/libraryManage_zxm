package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.AddActivity;
import sale.ljw.backend.form.ModifyActivity;
import sale.ljw.backend.form.QueryAllActivity;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service
 * @createDate 2022-10-17 16:02:13
 */
public interface ActivityServiceAdmin extends IService<Activity> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllActivity(QueryAllActivity queryAllActivity);

    ResponseResult<String> addActivity(AddActivity addActivity);

    ResponseResult<String> deleteActivity(Integer activityId);

    ResponseResult<String> modifyActivity(ModifyActivity modifyActivity);
}
