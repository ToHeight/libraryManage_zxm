package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ScheduledMapper;
import sale.ljw.backend.form.ModifyReservationStatus;
import sale.ljw.backend.form.QueryPreOrderedBook;
import sale.ljw.backend.pojo.Scheduled;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.ScheduledServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Service实现
 * @createDate 2022-10-17 16:34:24
 */
@Service
public class ScheduledServiceImplAdmin extends ServiceImpl<ScheduledMapper, Scheduled>
        implements ScheduledServiceAdmin {
    @Autowired
    private ScheduledMapper scheduledMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> getAllPreOrderedBook(QueryPreOrderedBook book) {
        PageHelper.startPage(book.getPage(),10);
        ArrayList<Map<String,Object>> schedule=scheduledMapper.getAllPreOrderedBook(book);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(schedule);
        return ResponseResult.getSuccessResult(pageInfo,"查询全部预定图书");
    }

    @Override
    public ResponseResult<String> modifyReservationStatus(ModifyReservationStatus modifyReservationStatus) {
        //更新数据
        UpdateWrapper<Scheduled> updateWrapper_scheduled=new UpdateWrapper<>();
        updateWrapper_scheduled.eq("scheduled_id", modifyReservationStatus.getScheduledId()).notIn("scheduled_status","RS004","RS003").set("scheduled_status", modifyReservationStatus.getReserveStatus());
        if(scheduledMapper.update(null, updateWrapper_scheduled)==0){
            return ResponseResult.getErrorResult("更新成功", StatusCode.NO_CONTENT,null);
        }
        return ResponseResult.getSuccessResult(null,"更新失败");
    }
}




