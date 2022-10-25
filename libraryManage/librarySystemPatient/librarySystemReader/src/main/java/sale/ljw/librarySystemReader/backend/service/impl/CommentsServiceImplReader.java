package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BorrowMapper;
import sale.ljw.backend.dao.CommentsMapper;
import sale.ljw.backend.form.BookMessage;
import sale.ljw.backend.pojo.Borrow;
import sale.ljw.backend.pojo.Comments;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.IdWorker;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.CommentsServiceReader;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Service实现
 * @createDate 2022-10-17 16:25:04
 */
@Service
public class CommentsServiceImplReader extends ServiceImpl<CommentsMapper, Comments>
        implements CommentsServiceReader {
    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private CommentsMapper commentsMapper;
    @Override
    public ResponseResult<String> bookMessage(BookMessage bookMessage, String token) {
        //检测当前图书是否是归还状态
        String userId = JwtUtils.parseJWT(token);
        QueryWrapper<Borrow> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.eq("borrow_id", bookMessage.getBorrowId()).eq("user_id",userId).eq("borrow_tatus", "BWS02");
        if(borrowMapper.selectCount(queryWrapper_0)==0){
            return ResponseResult.getErrorResult("当前借阅状态不是已归还状态，请归还图书后评论", StatusCode.NOT_FOUND, null);
        }
        //是归还状态检测用户是否评论过
        QueryWrapper<Comments> queryWrapper_1=new QueryWrapper<>();
        queryWrapper_1.eq("book_id", bookMessage.getBookId()).eq("user_id", userId);
        boolean cover= commentsMapper.selectCount(queryWrapper_1) != 0;
        //添加评论
        if(cover && bookMessage.getCover()){
            //更新数值
            UpdateWrapper<Comments> updateWrapper_0=new UpdateWrapper<>();
            updateWrapper_0.eq("book_id", bookMessage.getBookId()).eq("user_id",userId).set("comments_info", bookMessage.getMessage()).set("borrow_delete",0);
            if(commentsMapper.update(null, updateWrapper_0)==0){
                return ResponseResult.getErrorResult("添加评论失败！", StatusCode.NOT_MODIFIED, null);
            }
            return ResponseResult.getSuccessResult(null,"评论成功！");
        }
        //添加
        //生成id
        IdWorker idWorker=new IdWorker();
        Comments comments=new Comments(idWorker.nextId(),Integer.parseInt(userId),bookMessage.getBookId(),bookMessage.getMessage());
        if(commentsMapper.insert(comments)==0){
            return ResponseResult.getErrorResult("添加评论失败！", StatusCode.NOT_MODIFIED, null);
        }
        return ResponseResult.getSuccessResult(null,"评论成功！");
    }
}




