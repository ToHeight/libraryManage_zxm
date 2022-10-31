package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import sale.ljw.backend.form.InitializationReserveSeatTime;
import sale.ljw.backend.form.ReserveSeatForm;
import sale.ljw.backend.form.SeatReservationOnlineStaff;
import sale.ljw.backend.pojo.Userappointments;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Service
 * @createDate 2022-10-17 16:39:03
 */
public interface UserappointmentsServiceReader extends IService<Userappointments> {

    void modifyForm(SeatReservationOnlineStaff staff, StompHeaderAccessor accessor);

    void pushOnlineReserveSeat();

    void updateUserAppointments();


    ResponseResult<ArrayList<Map<String, Object>>> initializeFixedSeat(InitializationReserveSeatTime time);

    ResponseResult<String> reserveSeat(ReserveSeatForm reserveSeat,String token);

    void updateFloorPeopleCount();

    ResponseResult<String> determineReservationConditions(String token);

    ResponseResult<PageInfo<Map<String, Object>>> findAllAppointmentsByUser(Integer page, String token);

    ResponseResult<String> cancelAppointment(String appointmentId,String token);
}
