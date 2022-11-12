package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Managerlogin;

/**
 * @author 86155
 * @description 针对表【ManagerLogin】的数据库操作Mapper
 * @createDate 2022-10-17 16:30:17
 * @Entity sale.ljw.backend.pojo.Managerlogin
 */
@Mapper
@Repository
public interface ManagerloginMapper extends BaseMapper<Managerlogin> {

}




