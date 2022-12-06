package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.LostbookMapper;
import sale.ljw.backend.pojo.Lostbook;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.LostbookServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【lostBook】的数据库操作Service实现
 * @createDate 2022-12-05 18:22:23
 */
@Service
public class LostbookServiceImplAdmin extends ServiceImpl<LostbookMapper, Lostbook>
        implements LostbookServiceAdmin {
    @Autowired
    private LostbookMapper lostbookMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllLostBookList(Integer page) {
        PageHelper.startPage(page,10);
        ArrayList<Map<String,Object>> lostBookList=lostbookMapper.findAllLostBookList();
        PageInfo<Map<String,Object>> lostBookPage=new PageInfo<>(lostBookList);
        return ResponseResult.getSuccessResult(lostBookPage,"查询成功");
    }

    @Override
    public ResponseResult<String> confirmReturn(Integer lostBookId) {
        UpdateWrapper<Lostbook> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", lostBookId).set("lostStatus", "PB003");
        if(update(updateWrapper)){
            return ResponseResult.getSuccessResult(null,"归还成功");
        }
        return ResponseResult.getErrorResult("归还失败", StatusCode.ERROR, null);
    }
}




