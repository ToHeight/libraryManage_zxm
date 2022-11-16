package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Application;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【application】的数据库操作Mapper
 * @createDate 2022-10-17 16:11:50
 * @Entity sale.ljw.backend.pojo.Application
 */
@Mapper
@Repository
public interface ApplicationMapper extends BaseMapper<Application> {

    Integer updateUserActivityStatus();

    Integer cancellationApplication(Long applicationId, int userId);

    @MapKey(value = "applicationId")
    ArrayList<Map<String,Object>> findAllApplication(String info);
}




