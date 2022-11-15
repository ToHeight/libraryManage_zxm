package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.QueryAllBorrow;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.BorrowServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/borrowByAdmin")
public class ABorrowServlet {
    @Autowired
    private BorrowServiceAdmin borrowServiceAdmin;

    /**
     * 查询全部借阅信息
     * @param queryAllBorrow
     * @return
     */
    @ApiOperation(value = "查询全部借阅信息")
    @PostMapping("/queryAllBorrowingInformation")
    public ResponseResult<PageInfo<Map<String,Object>>> queryAllBorrowingInformation(@RequestBody @Valid QueryAllBorrow queryAllBorrow){
        return borrowServiceAdmin.queryAllBorrowingInformation(queryAllBorrow);
    }

    /**
     * 确定归还图书
     * @param borrowId
     * @return
     */
    @ApiOperation(value = "确定归还图书")
    @GetMapping("/confirmReturnBooks/{borrowId}")
    public ResponseResult<String> confirmReturnBooks(@PathVariable Integer borrowId){
        return borrowServiceAdmin.confirmReturnBooks(borrowId);
    }


}
