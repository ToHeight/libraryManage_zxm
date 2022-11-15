package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.QueryAllBorrow;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Service
 * @createDate 2022-10-17 16:24:02
 */
public interface BorrowServiceAdmin extends IService<Borrow> {

    ResponseResult<PageInfo<Map<String, Object>>> queryAllBorrowingInformation(QueryAllBorrow queryAllBorrow);

    ResponseResult<String> confirmReturnBooks(Integer borrowId);
}
