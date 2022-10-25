package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ActivityMapper;
import sale.ljw.backend.dao.ApplicationMapper;
import sale.ljw.backend.form.FindActivity;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.ActivityServiceReader;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service实现
 * @createDate 2022-10-17 16:02:13
 */
@Service
public class ActivityServiceImplReader extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityServiceReader {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> queryActivity(FindActivity findActivity,String token) {
        String userId = JwtUtils.parseJWT(token);
        PageHelper.startPage(findActivity.getPage(), findActivity.getPageSize());
        ArrayList<Map<String, Object>> arrayList = activityMapper.queryActivity(findActivity,Integer.parseInt(userId));
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(arrayList);
        return ResponseResult.getSuccessResult(pageInfo,"查询成功");
    }

    @Override
    public void checkActivityExpired() {
        //检测活动是否到期
        activityMapper.checkActivityExpired();
        //检测用户参加活动到期
        applicationMapper.updateUserActivityStatus();
    }

    @Override
    public ResponseResult<String> participateInRegistration(Integer activityId, String token) {
        //检测当前用户的年龄是否达标
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        if(activityMapper.detectAge(activityId,userId)==0){
            return ResponseResult.getErrorResult("不满足报名条件", StatusCode.NOT_FOUND, null);
        }
        IdWorker idWorker=new IdWorker();
        Application application=new Application();
        application.setApplicationid(idWorker.nextId());
        application.setActivityid(activityId);
        application.setUserid(userId);
        if(applicationMapper.insert(application)==0){
            return ResponseResult.getErrorResult("报名失败", StatusCode.NOT_MODIFIED, null);
        }
        return ResponseResult.getSuccessResult(null, "报名成功！");
    }


}




