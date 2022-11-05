package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.InitializationReserveSeatTime;
import sale.ljw.backend.form.ReserveSeatForm;
import sale.ljw.backend.form.SeatReservationOnlineStaff;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.UserappointmentsServiceReader;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/reserveSeatByReader")
public class RReserveSeatServlet {


    @Autowired
    private UserappointmentsServiceReader userappointmentsServiceReader;

    /**
     * 修改选择状态
     *
     * @param staff
     * @param accessor
     */
    @MessageMapping("/reserveSeat/modifyDataFormByUser")
    public void modifyForm(@RequestBody SeatReservationOnlineStaff staff, StompHeaderAccessor accessor) {
        userappointmentsServiceReader.modifyForm(staff, accessor);
    }

    /**
     * 十分钟发送一次消息，和初始化才调用此地址
     */
    @Scheduled(cron = "0 10 * * * ?")
    @MessageMapping("/reserveSeat/modifyDataFormByUserInitialization")
    public void pushOnlineReserveSeat() {
        //定时发送数据到前端
        userappointmentsServiceReader.pushOnlineReserveSeat();
    }

    /**
     * 更新用户预约表
     */
    @Scheduled(cron = "0 0 * * *  ?")
    public void updateUserAppointments() {
        userappointmentsServiceReader.updateUserAppointments();
    }

    /**
     * 每天凌晨一点更新楼层预约数量
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateFloorPeopleCount() {
        userappointmentsServiceReader.updateFloorPeopleCount();
    }

    /**
     * 初始化当前预约的
     *
     * @param time
     * @return
     */
    @ApiOperation(value = "初始化当前预约的")
    @PostMapping("/initializeFixedSeat")
    public ResponseResult<ArrayList<Map<String, Object>>> initializeFixedSeat(@RequestBody @Valid InitializationReserveSeatTime time) {
        return userappointmentsServiceReader.initializeFixedSeat(time);
    }

    /**
     * 预约座位
     *
     * @param reserveSeat
     * @param token
     * @return
     */
    @ApiOperation(value = "预约座位")
    @PostMapping("/reserveSeat")
    public ResponseResult<String> reserveSeat(@Valid @RequestBody ReserveSeatForm reserveSeat, @RequestHeader(name = "token") String token) {
        return userappointmentsServiceReader.reserveSeat(reserveSeat, token);
    }

    @ApiOperation(value = "判断是否具备预约条件")
    @GetMapping("/determineReservationConditions")
    public ResponseResult<String> determineReservationConditions(@RequestHeader(name = "token") String token) {
        return userappointmentsServiceReader.determineReservationConditions(token);
    }

    /**
     * 查询当前预约记录
     *
     * @param page
     * @param token
     * @return
     */
    @ApiOperation(value = "查询当前预约记录")
    @GetMapping("/findAllAppointmentsByUser")
    public ResponseResult<PageInfo<Map<String, Object>>> findAllAppointmentsByUser(@RequestParam(value = "page") Integer page, @RequestHeader(name = "token") String token) {
        return userappointmentsServiceReader.findAllAppointmentsByUser(page, token);
    }

    @ApiOperation(value = "撤销预约")
    @GetMapping("/cancelAppointment/{appointmentId}")
    public ResponseResult<String> cancelAppointment(@PathVariable(value = "appointmentId") String appointmentId, @RequestHeader(name = "token") String token) {
        return userappointmentsServiceReader.cancelAppointment(appointmentId, token);
    }
}
