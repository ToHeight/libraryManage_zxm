package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ActivityMapper;
import sale.ljw.backend.dao.ApplicationMapper;
import sale.ljw.backend.form.AddActivity;
import sale.ljw.backend.form.QueryAllActivity;
import sale.ljw.backend.pojo.Activity;
import sale.ljw.backend.pojo.Application;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.ActivityServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Service实现
 * @createDate 2022-10-17 16:02:13
 */
@Service
public class ActivityServiceImplAdmin extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityServiceAdmin {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllActivity(QueryAllActivity queryAllActivity) {
        PageHelper.startPage(queryAllActivity.getPage(),10);
        //查询
        ArrayList<Map<String, Object>> allActivity = activityMapper.findAllActivity(queryAllActivity);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(allActivity);
        return ResponseResult.getSuccessResult(pageInfo,"查询全部活动");
    }

    @Override
    public ResponseResult<String> addActivity(AddActivity addActivity) {
        Integer integer = activityMapper.addActivity(addActivity);
        if(integer ==0){
            return ResponseResult.getErrorResult("添加失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(addActivity.getActivityId()+"","添加成功");
    }

    @Override
    public ResponseResult<String> deleteActivity(Integer activityId) {
        //查询是否有人报名活动了
        QueryWrapper<Application> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.eq("activityId", activityId);
        if(applicationMapper.selectCount(queryWrapper_0)!=0){
            return ResponseResult.getErrorResult("当前活动不可删除，用户已经报名", StatusCode.NO_CONTENT, null);
        }
        activityMapper.deleteActivity(activityId);
        return ResponseResult.getSuccessResult(null, "删除成功");
    }
}




