package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ApplicationMapper;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ApplicationServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Service实现
 * @createDate 2022-10-17 16:11:50
 */
@Service
public class ApplicationServiceImplAdmin extends ServiceImpl<ApplicationMapper, Application>
        implements ApplicationServiceAdmin {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllApplication(QueryMessages queryMessages) {
        PageHelper.startPage(queryMessages.getPage(), 10);
        ArrayList<Map<String, Object>> allApplication = applicationMapper.findAllApplication(queryMessages.getInfo());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(allApplication);
        return ResponseResult.getSuccessResult(pageInfo,"查询成功");
    }

    @Override
    public ResponseResult<String> signUpSuccessfully(Integer applicationId) {
        //修改用户状态
        UpdateWrapper<Application> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("applicationId", applicationId).set("applicationStatus", "APS02");
        applicationMapper.update(null, updateWrapper);
        return ResponseResult.getSuccessResult(null,"报名成功");
    }

    @Override
    public ResponseResult<String> cancelRegistration(Integer applicationId) {
        //修改用户状态
        UpdateWrapper<Application> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("applicationId", applicationId).set("applicationStatus", "APS03");
        applicationMapper.update(null, updateWrapper);
        return ResponseResult.getSuccessResult(null,"报名成功");
    }
}




