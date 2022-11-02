package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.dao.ScheduledMapper;
import sale.ljw.backend.pojo.Book;
import sale.ljw.backend.pojo.Scheduled;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.ScheduledServiceReader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Service实现
 * @createDate 2022-10-17 16:34:24
 */
@Service
public class ScheduledServiceImplReader extends ServiceImpl<ScheduledMapper, Scheduled>
        implements ScheduledServiceReader {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ScheduledMapper scheduledMapper;

    @Override
    public ResponseResult<String> bookReservationByBookName(String bookName, String token) {
        //查询当前书签存在此书
        try {
            bookName = URLDecoder.decode(bookName, "UTF-8").replace("=", "");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        QueryWrapper<Book> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("book_name", bookName);
        if (bookMapper.selectCount(queryWrapper_0) != 0) {
            return ResponseResult.getErrorResult("图书已收入库中", StatusCode.MOVED_PERM, null);
        }
        //资源不存在
        IdWorker idWorker = new IdWorker();
        //生成id
        long id = idWorker.nextId();
        Scheduled scheduled = new Scheduled();
        scheduled.setScheduledId(id + "");
        scheduled.setBookName(bookName);
        scheduled.setUserId(Integer.parseInt(JwtUtils.parseJWT(token)));
        if (scheduledMapper.insert(scheduled) == 0) {
            return ResponseResult.getErrorResult("预定失败", StatusCode.NOT_MODIFIED, null);
        }
        return ResponseResult.getSuccessResult(null, "预定图书成功！");
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> findBookReservation(String bookName, String bookType, String token) {
        try {
            bookName = URLDecoder.decode(bookName, "UTF-8").replace("=", "");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        ArrayList<Map<String, Object>> schedules = scheduledMapper.findBookReservation(bookName, bookType, userId);
        //返回结果
        return ResponseResult.getSuccessResult(schedules, "查询成功");
    }

    @Override
    public ResponseResult<String> amendBookReservations(String bookId, String bookName, String token) {
        try {
            bookName = URLDecoder.decode(bookName, "UTF-8").replace("=", "");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        UpdateWrapper<Scheduled> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("scheduled_id", bookId).eq("user_id", Integer.parseInt(JwtUtils.parseJWT(token))).eq("scheduled_status", "RS001").set("book_name", bookName);
        if (scheduledMapper.update(null, updateWrapper) == 0) {
            return ResponseResult.getErrorResult("预定图书状态异常，修改失败，请确认图书当前未加入库中", StatusCode.NOT_FOUND, null);
        }
        return ResponseResult.getSuccessResult(null, "修改成功");
    }
}




