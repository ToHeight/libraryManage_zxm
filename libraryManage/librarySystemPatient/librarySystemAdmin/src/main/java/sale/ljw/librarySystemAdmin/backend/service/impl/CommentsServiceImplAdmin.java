package sale.ljw.librarySystemAdmin.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.CommentsMapper;
import sale.ljw.backend.form.DeleteComments;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Comments;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemAdmin.backend.service.CommentsServiceAdmin;
import sale.ljw.librarySystemAdmin.common.sercurity.utils.EmailUtils;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Service实现
 * @createDate 2022-10-17 16:25:04
 */
@Service
public class CommentsServiceImplAdmin extends ServiceImpl<CommentsMapper, Comments>
        implements CommentsServiceAdmin {
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> queryAllMessages(QueryMessages queryMessages) {
        PageHelper.startPage(queryMessages.getPage(), 10);
        ArrayList<Map<String, Object>> commentsList = commentsMapper.queryAllMessages(queryMessages.getInfo());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(commentsList);
        return ResponseResult.getSuccessResult(pageInfo, "查询成功");
    }

    @Override
    public ResponseResult<String> deleteComments(DeleteComments deleteComments) {
        synchronized (this) {
            //删除留言
            if (commentsMapper.deleteById(deleteComments.getCommentsId()) == 0) {
                return ResponseResult.getErrorResult("删除失败", StatusCode.NO_CONTENT, null);
            }
            //查询留言下用户信息
            Map<String, Object> userInformation = commentsMapper.queryUseInformationByCommentsId(deleteComments.getCommentsId());
            //给用户发送信息
            try {
                emailUtils.deleteComments(userInformation.get("userName").toString(),userInformation.get("bookName").toString(),userInformation.get("userEmail").toString(),userInformation.get("commentsInfo").toString(), deleteComments.getInfo());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseResult.getSuccessResult(null,"删除成功");
    }
}



