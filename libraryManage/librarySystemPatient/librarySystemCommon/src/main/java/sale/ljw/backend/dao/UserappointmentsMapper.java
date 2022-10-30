package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Userappointments;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Mapper
 * @createDate 2022-10-17 16:39:03
 * @Entity sale.ljw.backend.pojo.Userappointments
 */
@Mapper
@Repository
public interface UserappointmentsMapper extends BaseMapper<Userappointments> {

}




