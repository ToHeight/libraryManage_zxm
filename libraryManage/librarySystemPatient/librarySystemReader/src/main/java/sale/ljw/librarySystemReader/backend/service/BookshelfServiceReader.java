package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.backend.pojo.Bookshelf;
import sale.ljw.common.common.http.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【BookShelf】的数据库操作Service
 * @createDate 2022-10-17 16:17:30
 */
public interface BookshelfServiceReader extends IService<Bookshelf> {

    ResponseResult<PageInfo<Map<String, Object>>> queryBookshelvesBooks(QueryBookshelves bookshelves, String token);
}
