package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Manager;

import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Manager】的数据库操作Mapper
 * @createDate 2022-10-17 16:28:59
 * @Entity sale.ljw.backend.pojo.Manager
 */
@Mapper
@Repository
public interface ManagerMapper extends BaseMapper<Manager> {

    Map<String, Object> findUserInformationLogin(Integer managerId);
}




