package sale.ljw.librarySystemReader.common.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import sale.ljw.backend.dao.UserMapper;
import sale.ljw.backend.form.SeatReservationOnlineStaff;
import sale.ljw.common.utils.JwtUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 在事件出发的时候调用这个方法
     * StompHeaderAccessor 简单消息传递协议中处理消息头基类
     * 通过这个类。可以获取消息类型(例如：发布订阅，建立连接断开连接)，会话id等
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
        //用户订阅事件，记录在线订阅人数onlineStaff
        BoundHashOperations onlineStaff = redisTemplate.boundHashOps("onlineStaff");
        if (Objects.equals(header.getDestination(), "/reserve/modifyForm")) {
            //sessionId:header.getSessionAttributes().get("sessionId") token:header.getNativeHeader("token")
            //根据token获取用户id获取用户基本数据
            Integer userId = Integer.parseInt(JwtUtils.parseJWT(header.getNativeHeader("token").toString()));
            SeatReservationOnlineStaff seatReservationOnlineStaff = new SeatReservationOnlineStaff();
            seatReservationOnlineStaff.setUserInformation(userMapper.getInformationById(userId));
            seatReservationOnlineStaff.setAppointmentSuccessful(false);
            //存在数据将数据提取出来，从redis中拿取数据,设置过期事件10分钟
            onlineStaff.expire(10, TimeUnit.MINUTES);
            onlineStaff.put(header.getSessionAttributes().get("sessionId"), seatReservationOnlineStaff);
        }
        //检测拦截器中登出的集合是否有数据
        LinkedList<String> sessionIds = SocketChannelInterceptor.sessionIds;
        if (sessionIds != null && sessionIds.size() != 0) {
            //防止并发异常出现使用迭代器 ConcurrentModificationException
            Iterator<String> sessionId = sessionIds.iterator();
            while (sessionId.hasNext()) {
                try {
                    String nextId = sessionId.next();
                    onlineStaff.delete(nextId);
                }catch (NullPointerException ignored){
                }
            }
            SocketChannelInterceptor.sessionIds=new LinkedList<>();
        }
    }
}