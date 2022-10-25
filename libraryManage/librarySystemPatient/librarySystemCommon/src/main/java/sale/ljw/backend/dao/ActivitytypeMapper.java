package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Activitytype;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activityType】的数据库操作Mapper
 * @createDate 2022-10-17 16:09:21
 * @Entity sale.ljw.backend.pojo.Activitytype
 */
@Mapper
@Repository
public interface ActivitytypeMapper extends BaseMapper<Activitytype> {
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getEventCategory();
}




