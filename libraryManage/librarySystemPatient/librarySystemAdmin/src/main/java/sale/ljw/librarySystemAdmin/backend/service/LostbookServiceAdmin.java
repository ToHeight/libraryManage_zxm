package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.pojo.Lostbook;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
* @author 86155
* @description 针对表【lostBook】的数据库操作Service
* @createDate 2022-12-05 18:22:23
*/
public interface LostbookServiceAdmin extends IService<Lostbook> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllLostBookList(Integer page);

    ResponseResult<String> confirmReturn(Integer lostBookId);
}
