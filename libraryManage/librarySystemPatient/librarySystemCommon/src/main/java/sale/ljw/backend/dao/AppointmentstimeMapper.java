package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.InitializationReserveSeatTime;
import sale.ljw.backend.form.ReserveSeatForm;
import sale.ljw.backend.pojo.Appointmentstime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【AppointmentsTime】的数据库操作Mapper
 * @createDate 2022-10-17 16:13:27
 * @Entity sale.ljw.backend.pojo.Appointmentstime
 */
@Mapper
@Repository
public interface AppointmentstimeMapper extends BaseMapper<Appointmentstime> {

    Integer updateUserAppointments();

    Integer updateFloor();
    @MapKey(value = "appointmentId")
    ArrayList<Map<String, Object>> initializeFixedSeat(InitializationReserveSeatTime time);

    Integer reserveSeat(ReserveSeatForm reserveSeat, Integer userId);
}




