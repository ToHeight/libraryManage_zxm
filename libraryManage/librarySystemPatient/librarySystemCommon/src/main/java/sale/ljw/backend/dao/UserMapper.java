package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.User;

import java.util.HashMap;

/**
 * @author 86155
 * @description 针对表【User】的数据库操作Mapper
 * @createDate 2022-10-17 16:37:56
 * @Entity sale.ljw.backend.pojo.User
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @MapKey(value = "id")
    public HashMap<Integer, Object> getInformationById(Integer userId);
}




