package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.FindActivity;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service
 * @createDate 2022-10-17 16:02:13
 */
public interface ActivityServiceReader extends IService<Activity> {

    ResponseResult<PageInfo<Map<String, Object>>> queryActivity(FindActivity findActivity,String token);

    void checkActivityExpired();

    ResponseResult<String> participateInRegistration(Integer activityId, String token);
}
