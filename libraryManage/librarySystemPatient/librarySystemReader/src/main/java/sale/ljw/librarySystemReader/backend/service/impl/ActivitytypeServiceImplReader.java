package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ActivitytypeMapper;
import sale.ljw.backend.pojo.Activitytype;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.ActivitytypeServiceReader;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activityType】的数据库操作Service实现
 * @createDate 2022-10-17 16:09:21
 */
@Service
public class ActivitytypeServiceImplReader extends ServiceImpl<ActivitytypeMapper, Activitytype>
        implements ActivitytypeServiceReader {
    @Autowired
    private ActivitytypeMapper activitytypeMapper;

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getEventCategory() {
        ArrayList<Map<String, Object>> activity = activitytypeMapper.getEventCategory();
        return ResponseResult.getSuccessResult(activity, "查询成功");
    }
}




