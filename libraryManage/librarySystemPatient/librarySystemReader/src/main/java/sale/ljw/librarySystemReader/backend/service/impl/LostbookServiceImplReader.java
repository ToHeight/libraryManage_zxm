package sale.ljw.librarySystemReader.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.backend.dao.BorrowMapper;
import sale.ljw.backend.dao.LostbookMapper;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.backend.pojo.Lostbook;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.common.utils.QRcodeZxingUtil2;
import sale.ljw.librarySystemReader.backend.service.LostbookServiceReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 86155
 * @description 针对表【lostBook】的数据库操作Service实现
 * @createDate 2022-12-05 18:22:23
 */
@Service
public class LostbookServiceImplReader extends ServiceImpl<LostbookMapper, Lostbook>
        implements LostbookServiceReader {
    @Autowired
    private LostbookMapper lostbookMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${front-end-prefix}")
    private String prefix;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    @Override
    public ResponseResult<String> addLostBook(Integer borrowId, String token) {
        //检测借阅id是否和用户关联
        synchronized (this) {
            String userId = JwtUtils.parseJWT(token);
            Integer result = lostbookMapper.associationId(borrowId, userId);
            if (result == 0) {
                return ResponseResult.getErrorResult("请检查当前借阅信息是否和用户绑定", StatusCode.NOT_FOUND, null);
            }
            //更新图书状态
            UpdateWrapper<Borrow> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("borrow_id", borrowMapper).set("borrow_tatus", "BWS06");
            if (borrowMapper.update(null, updateWrapper) == 0) {
                throw new RuntimeException("图书表更新失败");
            }
            //添加丢书信息
            Integer resultLostBook = lostbookMapper.addLostBook(borrowId);
            if (resultLostBook == 0) {
                return ResponseResult.getErrorResult("添加信息失败", StatusCode.NOT_IMPLEMENTED, null);
            }
            return ResponseResult.getSuccessResult(null, "添加丢失图书成功");
        }
    }

    @Override
    public ResponseResult<String> paymentConfirmation(ArrayList<Integer> lostBookId) {
        //生成支付id绑定id
        String payId = idWorker.nextId() + "";
        //redis中保存支付id和id,过期时间10分钟
        redisTemplate.opsForValue().set(payId, JSON.toJSONString(lostBookId), 10, TimeUnit.MINUTES);
        Map<String, Object> payInformation = new HashMap<>();
        payInformation.put("payId", payId);
        payInformation.put("payNumber", 50 * lostBookId.size());
        payInformation.put("lostBookInformation", lostbookMapper.selectLostInformation(lostBookId));
        //返回支付图片和支付编号
        try {
            return ResponseResult.getSuccessResult(QRcodeZxingUtil2.getQRCodeImage(prefix + "/pay/" + payId), JSON.toJSONString(payInformation));
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllLostBook(Integer page, String token) {
        PageHelper.startPage(page, 10);
        String userId = JwtUtils.parseJWT(token);
        ArrayList<Map<String, Object>> allLostBooks = lostbookMapper.findAllLostBook(userId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(allLostBooks);
        return ResponseResult.getSuccessResult(pageInfo, "查询成功");
    }

    @Override
    public void scanPicture(String payId) {
        simpMessagingTemplate.convertAndSend("/pay/payStatus/" + payId, "scan picture");
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResponseResult<String> payLostBook(String payId) {
        //从redis中取出lostBookId
        ArrayList<Integer> lostBookId = JSON.parseObject((String) redisTemplate.opsForValue().get(payId), ArrayList.class);
        if (lostBookId == null) {
            return ResponseResult.getErrorResult("支付失败，订单不存在或超时", StatusCode.ERROR, null);
        }
        //用户支付中，修改丢失图书状态信息
        UpdateWrapper<Lostbook> updateWrapper = new UpdateWrapper<>();
        for (Integer bookId : lostBookId) {
            updateWrapper.eq("id", lostBookId).set("lostStatus", "PB001").eq("lostStatus", "PB002").set("payId", payId).set("payDate", new Date());
            if (lostbookMapper.update(null, updateWrapper) == 0) {
                simpMessagingTemplate.convertAndSend("/pay/payStatus/" + payId, "pay error");
                throw new RuntimeException("当前丢失图书id为" + bookId + "的订单支付失败，可能已经支付成功，请刷新确定付款界面重新支付");
            }
        }
        simpMessagingTemplate.convertAndSend("/pay/payStatus/" + payId, "pay success");
        return ResponseResult.getSuccessResult(null, "支付成功");

    }

    @Override
    public void updateBorrowLost() {

        ArrayList<Integer> borrowList = lostbookMapper.selectLostBook();
        //添加信息
        if (borrowList.size() != 0) {
            lostbookMapper.addBorrowLost(borrowList);
        }
    }
}




