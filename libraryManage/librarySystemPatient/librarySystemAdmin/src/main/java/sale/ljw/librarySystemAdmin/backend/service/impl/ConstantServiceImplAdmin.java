package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ConstantMapper;
import sale.ljw.backend.form.EditConstant;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Constant;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.ConstantServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Service实现
 * @createDate 2022-10-17 16:27:38
 */
@Service
public class ConstantServiceImplAdmin extends ServiceImpl<ConstantMapper, Constant>
        implements ConstantServiceAdmin {
    @Autowired
    private ConstantMapper constantMapper;
    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> getBookStatus() {
        ArrayList<Map<String,Object>> bookStatus=constantMapper.getBookStatus();
        return ResponseResult.getSuccessResult(bookStatus,"查询成功");
    }

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllConstant(QueryMessages page) {
        PageHelper.startPage(page.getPage(),10);
        ArrayList<Map<String,Object>> constantList=constantMapper.findAllConstant(page.getInfo());
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(constantList);
        return ResponseResult.getSuccessResult(pageInfo,"查询成功");
    }

    @Override
    public ResponseResult<String> editConstant(EditConstant editConstant) {
        //更新
        UpdateWrapper<Constant> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("coding", editConstant.getCoding()).set("value", editConstant.getValue());
       if( constantMapper.update(null,updateWrapper_0)==0){
           return ResponseResult.getErrorResult("修改失败", StatusCode.NO_CONTENT, null);
       }
        return ResponseResult.getSuccessResult(null,"修改成功");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> findfloorStatus() {
        ArrayList<Map<String,Object>> bookStatus=constantMapper.findfloorStatus();
        return ResponseResult.getSuccessResult(bookStatus,"查询成功");
    }
}




