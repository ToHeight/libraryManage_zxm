package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ApplicationMapper;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.ApplicationServiceReader;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service实现
 * @createDate 2022-10-17 16:11:50
 */
@Service
public class ApplicationServiceImplReader extends ServiceImpl<ApplicationMapper, Application>
        implements ApplicationServiceReader {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public ResponseResult<String> cancellationApplication(Long applicationId, String token) {
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        Integer result = applicationMapper.cancellationApplication(applicationId, userId);
        if (result == 0) {
            return ResponseResult.getErrorResult("活动取消报名失败，可能活动的已经开始或结束", StatusCode.NOT_FOUND, null);
        }
        return ResponseResult.getSuccessResult(null, "撤销报名成功");
    }
}




