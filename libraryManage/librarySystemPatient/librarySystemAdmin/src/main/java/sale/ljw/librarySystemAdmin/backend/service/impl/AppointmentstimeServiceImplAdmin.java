package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.AppointmentstimeMapper;
import sale.ljw.backend.pojo.Appointmentstime;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.AppointmentstimeServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【AppointmentsTime】的数据库操作Service实现
 * @createDate 2022-10-17 16:13:27
 */
@Service
public class AppointmentstimeServiceImplAdmin extends ServiceImpl<AppointmentstimeMapper, Appointmentstime>
        implements AppointmentstimeServiceAdmin {
    @Autowired
    private AppointmentstimeMapper appointmentstimeMapper;
    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> queryTimeList() {
        return ResponseResult.getSuccessResult(appointmentstimeMapper.queryTimeList(),"查询成功");
    }
}




