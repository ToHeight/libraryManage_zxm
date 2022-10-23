package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.dao.BorrowMapper;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.form.FindBorrowedBooks;
import sale.ljw.backend.pojo.Book;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.backend.pojo.User;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.BorrowServiceReader;
import sale.ljw.librarySystemReader.common.sercurity.utils.EmailUtils;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Service实现
 * @createDate 2022-10-17 16:24:02
 */
@Service
public class BorrowServiceImplReader extends ServiceImpl<BorrowMapper, Borrow>
        implements BorrowServiceReader {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    @Transactional
    public ResponseResult<String> borrowBook(String bookId, String token) {
        String userId = JwtUtils.parseJWT(token);
        //查询读者租赁次数是否为0
        QueryWrapper<User> queryWrapper_borrowNumber=new QueryWrapper<>();
        queryWrapper_borrowNumber.eq("user_id", Integer.valueOf(userId)).eq("status", "ASU01").select("borrowing_number");
        User user = userMapper.selectOne(queryWrapper_borrowNumber);
        if(user==null || user.getBorrowingNumber()==0){
            return ResponseResult.getErrorResult("当前无借阅次数或当前帐号状态异常", StatusCode.NOT_FOUND,null);
        }
        //查询查询当前图书是否有库存
        QueryWrapper<Book> queryWrapper_bookNumber=new QueryWrapper<>();
        queryWrapper_bookNumber.eq("book_id", bookId).eq("book_status", "BS002").select("book_count","borrow_number");
        Book book = bookMapper.selectOne(queryWrapper_bookNumber);
        if(book==null || book.getBookCount()==0){
            return ResponseResult.getErrorResult("图书当前暂无库存", StatusCode.NOT_FOUND,null);
        }
        //创建回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //图书库存减一
        UpdateWrapper<Book> updateWrapper_newBook=new UpdateWrapper<>();
        updateWrapper_newBook.eq("book_id", bookId).set("book_count", book.getBookCount()-1).set("borrow_number", book.getBorrowNumber()+1);
        if(bookMapper.update(null,updateWrapper_newBook)==0){
            return ResponseResult.getErrorResult("图书数量更新失败，请稍后重试", StatusCode.NOT_MODIFIED,null);
        }
        //借阅
        Borrow borrow=new Borrow();
        borrow.setUserId(Integer.valueOf(userId));
        borrow.setBookId(bookId);
        borrow.setBorrowTime(new Date());
        borrow.setReturnTime(new Date((new Date().getTime() + (long) 30 * 24 * 60 * 60 * 1000 )));
        if(borrowMapper.insert(borrow)==0){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResponseResult.getErrorResult("借阅失败，请稍后重试", StatusCode.NOT_MODIFIED,null);
        }
        //可借阅数量减一
        UpdateWrapper<User> updateWrapper_userBorrowNumber=new UpdateWrapper<>();
        updateWrapper_userBorrowNumber.eq("user_id", userId).set("borrowing_number", user.getBorrowingNumber()-1);
        if(userMapper.update(null, updateWrapper_userBorrowNumber)==0){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResponseResult.getErrorResult("读者借阅次数更新失败，请稍后重试", StatusCode.NOT_MODIFIED,null);
        }
        //向rabbitmq发送借阅消息
        //librarySystemReaderBorrowDev ：时间一天
        //librarySystemReaderBorrow ：时间十秒测试使用
        //librarySystemReaderBorrowCallable ：事件回滚失败，重新回滚
        //librarySystemReader-exchange-timeOut ：死信交换机
        //librarySystemReaderBorrowTimeOut ：死信队列
        rabbitTemplate.convertAndSend("","librarySystemReaderBorrowDev",borrow.getBorrowId());
        return ResponseResult.getSuccessResult(null,"借阅成功！");
    }

    /**
     * 用户超时未归还图书
     * @param borrowId
     */
    @Override
    @Transactional
    public void booksNotReturnedOverTime(Integer borrowId) {
        //查询借阅图书信息
        QueryWrapper<Borrow> queryWrapper_borrow=new QueryWrapper<>();
        queryWrapper_borrow.eq("borrow_id", borrowId).in("borrow_tatus", "BWS01","BWS04").select("borrow_id","user_id","book_id","borrow_time");
        Borrow borrow = borrowMapper.selectOne(queryWrapper_borrow);
        if(borrow==null){
            //当前图书状态已归还
            return;
        }
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //将用户状态修改，用户借阅次数修改
        UpdateWrapper<User> updateWrapper_user=new UpdateWrapper<>();
        updateWrapper_user.eq("user_id",borrow.getUserId()).set("status","ASU02");
        if(userMapper.update(null,updateWrapper_user)==0){
            //回滚
            rabbitTemplate.convertAndSend("","librarySystemReaderBorrowCallable",borrow.getBorrowId());
        }
        //当前借阅图书状态修改
        UpdateWrapper<Borrow> updateWrapper_borrow=new UpdateWrapper<>();
        updateWrapper_borrow.eq("borrow_id", borrowId).set("borrow_tatus", "BWS03");
        if(borrowMapper.update(null,updateWrapper_borrow)==0){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            rabbitTemplate.convertAndSend("","librarySystemReaderBorrowCallable",borrow.getBorrowId());
        }
        //发送邮件
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            QueryWrapper<Book> queryWrapper_book=new QueryWrapper<>();
            queryWrapper_book.eq("book_id", borrow.getBookId()).select("book_name");
            Book book = bookMapper.selectOne(queryWrapper_book);
            emailUtils.unpaidBookEmail(simpleDateFormat.format(borrow.getBorrowTime()),book.getBookName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findBorrowedBooks(FindBorrowedBooks findBorrowedBooks, String token) {
        PageHelper.startPage(findBorrowedBooks.getPage(), findBorrowedBooks.getPageSize());
        String userId = JwtUtils.parseJWT(token);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(borrowMapper.findBorrowedBooks(findBorrowedBooks,userId));
        return ResponseResult.getSuccessResult(pageInfo, "查找图书成功！");
    }

    @Override
    public ResponseResult<String> returnBooks(String borrowId, String token) {
        //确定当前图书状态为未归还状态
        String userId = JwtUtils.parseJWT(token);
        QueryWrapper<Borrow> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.eq("borrow_id", borrowId).eq("user_id", userId).in("borrow_tatus", "BWS01","BWS04");
        if(borrowMapper.selectCount(queryWrapper_0)==0){
            return ResponseResult.getErrorResult("借阅信息异常！", StatusCode.NOT_FOUND,null);
        }
        //更新信息
        UpdateWrapper<Borrow> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("borrow_id", borrowId).eq("user_id", userId).set("borrow_tatus", "BWS05");
        if(borrowMapper.update(null, updateWrapper_0)==0){
            return ResponseResult.getErrorResult("修改失败！", StatusCode.NOT_MODIFIED,null);
        }
        return ResponseResult.getSuccessResult(null, "图书归还成功，管理员确认中！");
    }

    @Override
    public ResponseResult<String> deleteBorrowingRecord(String borrowId,String token) {
        //确定状态是否是归还了的书
        String userId = JwtUtils.parseJWT(token);
        QueryWrapper<Borrow> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.eq("borrow_id", borrowId).eq("borrow_tatus", "BWS02").eq("user_id", userId).eq("borrow_delete", 0);
        if(borrowMapper.selectCount(queryWrapper_0)==0){
            return ResponseResult.getErrorResult("当前借阅状态异常！请归还后才可删除", StatusCode.NOT_FOUND,null);
        }
        //修改状态
        if(borrowMapper.deleteById(borrowId)==0){
            return ResponseResult.getErrorResult("删除失败！", StatusCode.NOT_MODIFIED,null);
        }
        return ResponseResult.getSuccessResult(null, "删除成功！");
    }


}




