package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.LostbookServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/lostBookAdmin")
public class ALostBookServlet {
    @Autowired
    private LostbookServiceAdmin lostbookServiceAdmin;
    @Autowired
    private sale.ljw.librarySystemAdmin.common.config.librarySystemReaderFeignClient librarySystemReaderFeignClient;

    /**
     * 查询全部丢失图书
     * @param page
     * @return
     */
    @ApiOperation(value = "查询全部丢失书籍")
    @GetMapping("/findAllLostBook/{page}")
    public ResponseResult<PageInfo<Map<String ,Object>>> findAllLostBook(@PathVariable(name = "page") Integer page){
        return lostbookServiceAdmin.findAllLostBookList(page);
    }

    /**
     * 支付图片
     * @param lostBookId
     * @return
     */
    @ApiOperation(value = "支付图片")
    @PostMapping(value = "/paymentConfirmation")
    public ResponseResult<String> paymentConfirmation(@RequestBody ArrayList<Integer> lostBookId){
        return librarySystemReaderFeignClient.paymentConfirmation(lostBookId);
    }

    /**
     * 确认归还
     * @param lostBookId
     * @return
     */
    @ApiOperation(value = "确认归还")
    @GetMapping(value = "/confirmReturn/{lostBookId}")
    public ResponseResult<String> confirmReturn(@PathVariable Integer lostBookId){
        return lostbookServiceAdmin.confirmReturn(lostBookId);
    }
}
