package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ConstantMapper;
import sale.ljw.backend.pojo.Constant;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemReader.backend.service.ConstantServiceReader;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Service实现
 * @createDate 2022-10-17 16:27:38
 */
@Service
public class ConstantServiceImplReader extends ServiceImpl<ConstantMapper, Constant>
        implements ConstantServiceReader {
    @Autowired
    private ConstantMapper constantMapper;

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getAuthorCountry() {
        ArrayList<Map<String, Object>> authorCountry=constantMapper.getAuthorCountry();
        return ResponseResult.getSuccessResult(authorCountry, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getBookLanguage() {
        ArrayList<Map<String, Object>> bookLanguage=constantMapper.getBookLanguage();
        return ResponseResult.getSuccessResult(bookLanguage, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getBorrowStatus() {
        ArrayList<Map<String, Object>> borrowStatus=constantMapper.getBorrowStatus();
        return ResponseResult.getSuccessResult(borrowStatus, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getPreOrderBookStatus() {
        ArrayList<Map<String, Object>> preOrderBookStatus=constantMapper.getPreOrderBookStatus();
        return ResponseResult.getSuccessResult(preOrderBookStatus, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getActivityStatus() {
        ArrayList<Map<String, Object>> activityStatus=constantMapper.getActivityStatus();
        return ResponseResult.getSuccessResult(activityStatus, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getFloor() {
        ArrayList<Map<String, Object>> floors=constantMapper.getFloor();
        return ResponseResult.getSuccessResult(floors, "获取成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getGender() {
        return ResponseResult.getSuccessResult(constantMapper.getGender(), "获取成功");
    }
}




