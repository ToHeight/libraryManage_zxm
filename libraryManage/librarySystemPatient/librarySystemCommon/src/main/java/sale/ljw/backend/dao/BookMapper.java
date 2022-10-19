package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.backend.pojo.Book;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Mapper
 * @createDate 2022-10-17 16:14:59
 * @Entity sale.ljw.backend.pojo.Book
 */
@Mapper
@Repository
public interface BookMapper extends BaseMapper<Book> {
    @MapKey(value = "bookId")
    public List<Map<String,Object>> findBookByCriteria(FindBookByCriteria bookByCriteria);

}




