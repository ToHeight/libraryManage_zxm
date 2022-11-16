package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserappointmentsMapper;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Userappointments;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.UserappointmentsServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Service实现
 * @createDate 2022-10-17 16:39:03
 */
@Service
public class UserappointmentsServiceImplAdmin extends ServiceImpl<UserappointmentsMapper, Userappointments>
        implements UserappointmentsServiceAdmin {
    @Autowired
    private UserappointmentsMapper userappointmentsMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllAppointment(QueryMessages queryMessages) {
        PageHelper.startPage(queryMessages.getPage(), 10);
        ArrayList<Map<String, Object>> allAppointment = userappointmentsMapper.findAllAppointment(queryMessages.getInfo());
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(allAppointment);
        return ResponseResult.getSuccessResult(pageInfo, "查询全部数据");
    }

    @Override
    public ResponseResult<String> cancelAppointment(Integer appintmentId) {
        //更新
        UpdateWrapper<Userappointments> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("appointment_id", appintmentId).in("appointments_status", "AS001","AS005").set("appointments_status","AS003");
        userappointmentsMapper.update(null, updateWrapper_0);
        return ResponseResult.getSuccessResult(null, "撤回预约成功");
    }
}




