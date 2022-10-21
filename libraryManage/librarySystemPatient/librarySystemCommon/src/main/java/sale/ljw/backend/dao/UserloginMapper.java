package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Userlogin;

/**
 * @author 86155
 * @description 针对表【UserLogin】的数据库操作Mapper
 * @createDate 2022-10-17 16:40:14
 * @Entity sale.ljw.backend.pojo.Userlogin
 */
@Mapper
@Repository
public interface UserloginMapper extends BaseMapper<Userlogin> {

}




