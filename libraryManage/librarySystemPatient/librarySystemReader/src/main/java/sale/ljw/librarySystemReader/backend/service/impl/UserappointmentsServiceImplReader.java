package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;
import sale.ljw.backend.dao.AppointmentstimeMapper;
import sale.ljw.backend.dao.OpenMapper;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.dao.UserappointmentsMapper;
import sale.ljw.backend.form.InitializationReserveSeatTime;
import sale.ljw.backend.form.ReserveSeatForm;
import sale.ljw.backend.form.SeatReservationOnlineStaff;
import sale.ljw.backend.pojo.Userappointments;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.UserappointmentsServiceReader;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【UserAppointments】的数据库操作Service实现
 * @createDate 2022-10-17 16:39:03
 */
@Service
public class UserappointmentsServiceImplReader extends ServiceImpl<UserappointmentsMapper, Userappointments>
        implements UserappointmentsServiceReader {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AppointmentstimeMapper appointmentstimeMapper;
    @Autowired
    private UserappointmentsMapper userappointmentsMapper;

    @Override
    public void modifyForm(SeatReservationOnlineStaff staff, StompHeaderAccessor accessor) {
        //sessionId获取
        String sessionId = (String) accessor.getSessionAttributes().get("sessionId");
        //token获取
        String token = accessor.getNativeHeader("token").get(0);
        //将数据变化存储到redis中
        staff.setUserInformation(userMapper.getInformationById(Integer.parseInt(JwtUtils.parseJWT(token))));
        staff.setAppointmentSuccessful(false);
        redisTemplate.boundHashOps("onlineStaff").put(sessionId, staff);
        //将变化的数据发送到前端
        simpMessagingTemplate.convertAndSend("/reserve/modifyForm", staff);
    }

    @Override
    public void pushOnlineReserveSeat() {
        List<SeatReservationOnlineStaff> onlineStaff = redisTemplate.boundHashOps("onlineStaff").values();
        simpMessagingTemplate.convertAndSend("/reserve/modifyForm", onlineStaff == null ? "" : onlineStaff);
    }

    @Override
    public void updateUserAppointments() {
        appointmentstimeMapper.updateFloor();
        appointmentstimeMapper.updateUserAppointments();
    }

    @Override
    public ResponseResult<ArrayList<Map<String, Object>>> initializeFixedSeat(InitializationReserveSeatTime time) {
        ArrayList<Map<String,Object>> times=appointmentstimeMapper.initializeFixedSeat(time);
        return ResponseResult.getSuccessResult(times,"查询成功！");
    }

    @Autowired
    private OpenMapper openMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    @Transactional
    public ResponseResult<String> reserveSeat(ReserveSeatForm reserveSeat,String token) {
        //修改楼层数据
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        //查询当前用户是否有预约
        QueryWrapper<Userappointments> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.eq("user_id", userId).in("appointments_status", "AS001","AS005").select("floor_id","seatName");
        Userappointments userappointments = userappointmentsMapper.selectOne(queryWrapper_0);
        if(userappointments!=null){
            return ResponseResult.getErrorResult("预约失败，请检查当前已经存在生效预约，预约座位为"+userappointments.getFloorId()+"-"+userappointments.getSeatname(), StatusCode.NOT_FOUND, null);
        }
        ResponseResult<String> responseResult = transactionTemplate.execute(transactionStatus -> {
            Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            if (openMapper.reservePeoperCount(reserveSeat.getFloorId(), 1) == 0) {
                return ResponseResult.getErrorResult("修改楼层当前人数失败", StatusCode.NOT_MODIFIED, null);
            }
              //添加新的数据
            if (appointmentstimeMapper.reserveSeat(reserveSeat, userId) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResponseResult.getErrorResult("预约失败", StatusCode.NOT_MODIFIED, null);
            }
            //预约成功，将信息通过websocket推送
            SeatReservationOnlineStaff staff=new SeatReservationOnlineStaff();
            staff.setNewSeatNumber(reserveSeat.getFloorId()+"-"+reserveSeat.getSeatNumber());
            staff.setAppointmentSuccessful(true);
            simpMessagingTemplate.convertAndSend("/reserve/modifyForm", staff);
//            redisTemplate.boundHashOps("onlineStaff").delete()

            return ResponseResult.getSuccessResult(null, "预约成功");
        });
        return responseResult;
    }

    @Override
    public void updateFloorPeopleCount() {
        //更新
        openMapper.updateFloorPeopleCount(100);
    }

    @Override
    public ResponseResult<String> determineReservationConditions(String token) {
        //判断当前是否有预约条件
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        QueryWrapper<Userappointments> queryWrapper_0=new QueryWrapper<>();
        queryWrapper_0.in("appointments_status", "AS001","AS005").eq("user_id", userId);
        if(userappointmentsMapper.selectCount(queryWrapper_0)!=0){
            return ResponseResult.getErrorResult("当前不可预约,显示预约详情界面",StatusCode.NOT_FOUND,null);
        }
        return ResponseResult.getSuccessResult(null,"可预约");
    }

    @Override
    public ResponseResult<PageInfo<Map<String, Object>>> findAllAppointmentsByUser(Integer page, String token) {
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        PageHelper.startPage(page,5);
        ArrayList<Map<String,Object>> userappointment=userappointmentsMapper.findAllAppointmentsByUser(userId);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(userappointment);
        return ResponseResult.getSuccessResult(pageInfo,"查询成功");
    }

    @Override
    public ResponseResult<String> cancelAppointment(String appointmentId,String token) {
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        Integer cancelAppointment=userappointmentsMapper.updateCancelAppointment(appointmentId,userId);
        if(cancelAppointment==0){
            return ResponseResult.getErrorResult("撤销失败", StatusCode.NOT_FOUND, null);
        }
        return ResponseResult.getSuccessResult(null,"撤销成功");
    }
}
