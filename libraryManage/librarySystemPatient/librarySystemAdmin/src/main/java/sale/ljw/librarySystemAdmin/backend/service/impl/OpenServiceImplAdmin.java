package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.OpenMapper;
import sale.ljw.backend.form.ModifyFloorInformation;
import sale.ljw.backend.pojo.Open;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.OpenServiceAdmin;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Open】的数据库操作Service实现
 * @createDate 2022-10-17 16:32:43
 */
@Service
public class OpenServiceImplAdmin extends ServiceImpl<OpenMapper, Open>
        implements OpenServiceAdmin {
    @Autowired
    private OpenMapper openMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllFloor(Integer page) {
        PageHelper.startPage(page, 10);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(openMapper.findAllFloor());
        return ResponseResult.getSuccessResult(pageInfo, "查询全部楼层信息");
    }

    @Override
    public ResponseResult<String> modifyFloorInformation(ModifyFloorInformation modifyFloorInformation) {
        UpdateWrapper<Open> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("floor_id", modifyFloorInformation.getFloorId())
                .set("floor_status", modifyFloorInformation.getFloorStatus())
                .set("time_id", modifyFloorInformation.getTimeId());
        openMapper.update(null, updateWrapper);
        return ResponseResult.getSuccessResult(null, "修改成功");
    }


}




