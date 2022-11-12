package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ConstantMapper;
import sale.ljw.backend.pojo.Constant;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ConstantServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Service实现
 * @createDate 2022-10-17 16:27:38
 */
@Service
public class ConstantServiceImplAdmin extends ServiceImpl<ConstantMapper, Constant>
        implements ConstantServiceAdmin {
    @Autowired
    private ConstantMapper constantMapper;
    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getBookStatus() {
        ArrayList<Map<String,Object>> bookStatus=constantMapper.getBookStatus();
        return ResponseResult.getSuccessResult(bookStatus,"查询成功");
    }
}




