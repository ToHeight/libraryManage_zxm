package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Activitytype;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activityType】的数据库操作Service
 * @createDate 2022-10-17 16:09:21
 */
public interface ActivitytypeServiceReader extends IService<Activitytype> {

    ResponseResult<ArrayList<Map<String, Object>>> getEventCategory();
}
