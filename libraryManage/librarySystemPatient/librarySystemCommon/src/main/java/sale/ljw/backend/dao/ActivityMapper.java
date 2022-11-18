package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.AddActivity;
import sale.ljw.backend.form.FindActivity;
import sale.ljw.backend.form.ModifyActivity;
import sale.ljw.backend.form.QueryAllActivity;
import sale.ljw.backend.pojo.Activity;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【activity】的数据库操作Mapper
 * @createDate 2022-10-17 16:02:13
 * @Entity sale.ljw.backend.pojo.Activity
 */
@Mapper
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {
    @MapKey(value = "activityId")
    ArrayList<Map<String, Object>> queryActivity(FindActivity findActivity,Integer userId);

    Integer checkActivityExpired();

    Integer detectAge(Integer activityId, int userId);
    @MapKey(value = "activityId")
    ArrayList<Map<String,Object>> findAllActivity(QueryAllActivity queryAllActivity);

    Integer addActivity(AddActivity addActivity);

    Integer deleteActivity(Integer activityId);

    Integer modifyActivity(ModifyActivity modifyActivity);
}




