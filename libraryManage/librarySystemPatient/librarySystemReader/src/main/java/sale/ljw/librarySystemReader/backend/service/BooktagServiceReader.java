package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.common.common.http.ResponseResult;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service
 * @createDate 2022-10-17 16:19:03
 */
public interface BooktagServiceReader extends IService<Booktag> {

    ResponseResult<String> deleteBookTag(Integer tagId, String bookId,String token);
}
