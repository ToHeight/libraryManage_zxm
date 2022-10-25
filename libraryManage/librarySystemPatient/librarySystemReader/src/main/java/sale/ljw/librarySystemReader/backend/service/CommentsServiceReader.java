package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.form.BookMessage;
import sale.ljw.backend.pojo.Comments;
import sale.ljw.common.common.http.ResponseResult;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Service
 * @createDate 2022-10-17 16:25:04
 */
public interface CommentsServiceReader extends IService<Comments> {

    ResponseResult<String> bookMessage(BookMessage bookMessage, String token);
}
