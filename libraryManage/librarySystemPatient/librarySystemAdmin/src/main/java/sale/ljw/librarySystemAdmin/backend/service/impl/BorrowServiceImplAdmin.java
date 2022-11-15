package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BorrowMapper;
import sale.ljw.backend.form.QueryAllBorrow;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.BorrowServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Service实现
 * @createDate 2022-10-17 16:24:02
 */
@Service
public class BorrowServiceImplAdmin extends ServiceImpl<BorrowMapper, Borrow>
        implements BorrowServiceAdmin {

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> queryAllBorrowingInformation(QueryAllBorrow queryAllBorrow) {
        PageHelper.startPage(queryAllBorrow.getPage(), 10);
        ArrayList<Map<String, Object>> borrowList = borrowMapper.queryAllBorrowingInformation(queryAllBorrow);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(borrowList);
        return ResponseResult.getSuccessResult(pageInfo, "查询成功");
    }

    @Override
    public ResponseResult<String> confirmReturnBooks(Integer borrowId) {
        //归还图书
        UpdateWrapper<Borrow> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("borrow_id",borrowId).eq("borrow_tatus", "BWS05").set("borrow_tatus","BWS02");
        if(borrowMapper.update(null,updateWrapper_0)==0){
            return ResponseResult.getErrorResult("确认失败", StatusCode.NO_CONTENT, null);
        }
        return ResponseResult.getSuccessResult(null,"确认成功");
    }
}




