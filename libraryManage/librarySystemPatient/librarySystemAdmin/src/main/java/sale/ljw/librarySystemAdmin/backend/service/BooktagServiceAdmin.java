package sale.ljw.librarySystemAdmin.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.BookTagList;
import sale.ljw.backend.form.EditBookTag;
import sale.ljw.backend.form.FindAllBookTag;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.common.common.http.ResponseResult;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service
 * @createDate 2022-10-17 16:19:03
 */
public interface BooktagServiceAdmin extends IService<Booktag> {

    ResponseResult<PageInfo<BookTagList>> findAllBookTag(FindAllBookTag bookTag);

    ResponseResult<String> editBookTag(EditBookTag bookTag);

    ResponseResult<String> deleteTagById(Integer tagId);

    ResponseResult<String> addBookTag(EditBookTag bookTag);
}
