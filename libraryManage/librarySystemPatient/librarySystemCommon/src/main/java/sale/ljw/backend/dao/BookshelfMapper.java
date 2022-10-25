package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.BookshelvesBooks;
import sale.ljw.backend.form.QueryBookshelves;
import sale.ljw.backend.pojo.Bookshelf;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【BookShelf】的数据库操作Mapper
 * @createDate 2022-10-17 16:17:30
 * @Entity sale.ljw.backend.pojo.Bookshelf
 */
@Mapper
@Repository
public interface BookshelfMapper extends BaseMapper<Bookshelf> {
    @MapKey(value = "bookId")
    List<BookshelvesBooks> queryBookshelvesBooks(QueryBookshelves bookshelves);
}




