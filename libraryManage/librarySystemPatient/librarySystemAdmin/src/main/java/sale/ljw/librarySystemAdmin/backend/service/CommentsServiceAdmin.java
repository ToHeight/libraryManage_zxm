package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.DeleteComments;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.backend.pojo.Comments;
import sale.ljw.common.common.http.ResponseResult;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Service
 * @createDate 2022-10-17 16:25:04
 */
public interface CommentsServiceAdmin extends IService<Comments> {

    ResponseResult<PageInfo<Map<String, Object>>> queryAllMessages(QueryMessages queryMessages);

    ResponseResult<String> deleteComments(DeleteComments deleteComments);
}
