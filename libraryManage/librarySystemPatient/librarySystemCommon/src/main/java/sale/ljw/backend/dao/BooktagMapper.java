package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.BookTagList;
import sale.ljw.backend.pojo.Booktag;

import java.util.ArrayList;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Mapper
 * @createDate 2022-10-17 16:19:03
 * @Entity sale.ljw.backend.pojo.Booktag
 */
@Mapper
@Repository
public interface BooktagMapper extends BaseMapper<Booktag> {

    ArrayList<BookTagList> findAllBookTag(String tagValue);
}




