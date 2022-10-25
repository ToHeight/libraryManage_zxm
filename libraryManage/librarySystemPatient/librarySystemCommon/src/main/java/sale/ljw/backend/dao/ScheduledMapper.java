package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Scheduled;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Scheduled】的数据库操作Mapper
 * @createDate 2022-10-17 16:34:24
 * @Entity sale.ljw.backend.pojo.Scheduled
 */
@Mapper
@Repository
public interface ScheduledMapper extends BaseMapper<Scheduled> {
    @MapKey(value = "scheduledId")
    ArrayList<Map<String, Object>> findBookReservation(String bookName, String bookType, int userId);
}




