package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Userappointments;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Mapper
 * @createDate 2022-10-17 16:39:03
 * @Entity sale.ljw.backend.pojo.Userappointments
 */
@Mapper
@Repository
public interface UserappointmentsMapper extends BaseMapper<Userappointments> {
    @MapKey(value = "appointmentId")
    ArrayList<Map<String, Object>> findAllAppointmentsByUser(int userId);

    Integer updateCancelAppointment(String appointmentId, int userId);
    @MapKey(value = "appointmentId")
    ArrayList<Map<String,Object>> findAllAppointment(String info);
}




