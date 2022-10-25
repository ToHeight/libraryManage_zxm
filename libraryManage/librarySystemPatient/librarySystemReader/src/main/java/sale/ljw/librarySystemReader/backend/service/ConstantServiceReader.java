package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Constant;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Service
 * @createDate 2022-10-17 16:27:38
 */
public interface ConstantServiceReader extends IService<Constant> {

    ResponseResult<ArrayList<Map<String, Object>>> getAuthorCountry();

    ResponseResult<ArrayList<Map<String, Object>>> getBookLanguage();

    ResponseResult<ArrayList<Map<String, Object>>> getBorrowStatus();

    ResponseResult<ArrayList<Map<String, Object>>> getPreOrderBookStatus();

    ResponseResult<ArrayList<Map<String, Object>>> getActivityStatus();
}
