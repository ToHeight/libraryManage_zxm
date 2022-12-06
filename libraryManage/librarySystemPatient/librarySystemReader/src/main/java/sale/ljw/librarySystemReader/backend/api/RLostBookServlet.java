package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.LostbookServiceReader;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/lostBook")
public class RLostBookServlet {
    @Autowired
    private LostbookServiceReader lostbookServiceReader;

    /**
     * 添加丢失图书
     *
     * @return
     */
    @ApiOperation(value = "添加丢失图书")
    @GetMapping("/addLostBook/{borrowId}")
    public ResponseResult<String> addLostBook(@PathVariable Integer borrowId, @RequestHeader(name = "token") String token) {
        return lostbookServiceReader.addLostBook(borrowId, token);
    }

    /**
     * 支付图片
     *
     * @param lostBookId
     *
     * @return
     */
    @ApiOperation(value = "支付图片")
    @PostMapping("/paymentConfirmation")
    public ResponseResult<String> paymentConfirmation(@RequestBody ArrayList<Integer> lostBookId) {
        return lostbookServiceReader.paymentConfirmation(lostBookId);
    }

    /**
     * 查询全部丢失图书信息
     * @param page
     * @return
     */
    @ApiOperation(value = "查询全部丢失图书信息")
    @GetMapping("/findAllLostBook/{page}")
    public ResponseResult<PageInfo<Map<String, Object>>> findAllLostBook(@PathVariable Integer page, @RequestHeader(name = "token") String token) {
        return lostbookServiceReader.findAllLostBook(page, token);
    }

    /**
     * 扫描图片
     * @param payId
     */
    @MessageMapping("/lostBook/scanPicture")
    public void scanPicture(@RequestBody String payId){
        lostbookServiceReader.scanPicture(payId);
    }

    /**
     * 支付订单
     * @param payId
     * @return
     */
    @ApiOperation(value = "支付订单")
    @GetMapping("/pay/{payId}")
    public ResponseResult<String> payLostBook(@PathVariable String payId){
        return lostbookServiceReader.payLostBook(payId);
    }

    //查询逾期是否到达三个星期
    @Scheduled(cron = "0 0 12 * * ?")
    public void updateBorrowLost(){
        lostbookServiceReader.updateBorrowLost();
    }
}
