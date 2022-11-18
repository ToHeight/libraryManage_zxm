package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.form.EditUserByAdmin;
import sale.ljw.backend.form.QueryAllUser;
import sale.ljw.backend.pojo.User;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.UserServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Service实现
 * @createDate 2022-10-17 16:37:56
 */
@Service
public class UserServiceImplAdmin extends ServiceImpl<UserMapper, User>
        implements UserServiceAdmin {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllUser(QueryAllUser user) {
        PageHelper.startPage(user.getPage(), 10);
        ArrayList<Map<String,Object>> userList=userMapper.findAllUser(user);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(userList);
        return ResponseResult.getSuccessResult(pageInfo, "查找成功");
    }

    @Override
    public ResponseResult<String> editUser(EditUserByAdmin editUserByAdmin) {
        //查询身份证号和电子邮件是否唯一
        QueryWrapper<User> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.notIn("user_id", editUserByAdmin.getUserId())
                .and(q->q.eq("user_idcard", editUserByAdmin.getIdCard()).or().eq("user_email",editUserByAdmin.getEmail()));
        if(userMapper.selectCount(queryWrapper_0)!=0){
            return ResponseResult.getErrorResult("身份证号或电子邮件重复", StatusCode.NO_CONTENT, null);
        }
        //更新数据
        UpdateWrapper<User> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("user_id", editUserByAdmin.getUserId())
                        .set("user_address",editUserByAdmin.getAddress())
                        .set("user_email",editUserByAdmin.getEmail())
                        .set("user_gender",editUserByAdmin.getGender())
                        .set("user_idcard",editUserByAdmin.getIdCard())
                        .set("user_telephone",editUserByAdmin.getTelephone())
                        .set("user_name",editUserByAdmin.getUserName());
        if(userMapper.update(null,updateWrapper_0)==0){
            return ResponseResult.getErrorResult("修改失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null, "修改成功");
    }

    @Override
    public ResponseResult<String> activateAccount(Integer userId) {
        //激活账号
        UpdateWrapper<User> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("user_id", userId)
                .in("status", "ASU02")
                .set("status","ASU01");
        if(userMapper.update(null,updateWrapper_0)==0){
            return ResponseResult.getErrorResult("账号已经激活", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null, "激活成功");
    }
}




