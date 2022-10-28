package sale.ljw.librarySystemReader.backend.api;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.form.SeatReservationOnlineStaff;
import sale.ljw.common.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
//@RequestMapping("/reserveSeatByReader")
public class RReserveSeatServlet {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @MessageMapping("/reserveSeat/modifyDataFormByUser")
    public void modifyForm(@RequestBody SeatReservationOnlineStaff staff, StompHeaderAccessor accessor) {
        //sessionId获取
        String sessionId = (String) accessor.getSessionAttributes().get("sessionId");
        //token获取
        String token = accessor.getNativeHeader("token").get(0);
        //将数据变化存储到redis中
        staff.setUserInformation(userMapper.getInformationById(Integer.parseInt(JwtUtils.parseJWT(token))));
        redisTemplate.boundHashOps("onlineStaff").put(sessionId,staff);
        //将变化的数据发送到前端
        simpMessagingTemplate.convertAndSend("/reserve/modifyForm", staff);
    }

    //十分钟发送一次消息，和初始化才调用此地址
    @Scheduled(cron = "0 10 * * * ?")
    @MessageMapping("/reserveSeat/modifyDataFormByUserInitialization")
    public void pushOnlineReserveSeat() {
        //定时发送数据到前端
        List<SeatReservationOnlineStaff> onlineStaff = redisTemplate.boundHashOps("onlineStaff").values();
        simpMessagingTemplate.convertAndSend("/reserve/modifyForm", onlineStaff == null ? "" : onlineStaff);
    }



}
