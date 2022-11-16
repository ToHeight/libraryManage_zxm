package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.ModifyFloorInformation;
import sale.ljw.backend.pojo.Open;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Open】的数据库操作Service
 * @createDate 2022-10-17 16:32:43
 */
public interface OpenServiceAdmin extends IService<Open> {

    ResponseResult<PageInfo<Map<String, Object>>> findAllFloor(Integer page);

    ResponseResult<String> modifyFloorInformation(ModifyFloorInformation modifyFloorInformation);
}
