package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Comments;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Comments】的数据库操作Mapper
 * @createDate 2022-10-17 16:25:04
 * @Entity sale.ljw.backend.pojo.Comments
 */
@Mapper
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {
    @MapKey(value = "commentsId")
    ArrayList<Map<String, Object>> queryAllMessages(String info);

    Map<String, Object> queryUseInformationByCommentsId(String commentsId);
}




