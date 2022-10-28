package sale.ljw.librarySystemReader.common.config.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Objects;

@Component
public class UnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {


    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
        //用户取消订阅事件，删除在线
        if (Objects.equals(header.getDestination(), "/reserve/modifyForm")) {
            //sessionId:header.getSessionAttributes().get("sessionId") token:header.getNativeHeader("token")
            //根据sessionId删除在线人数
            SocketChannelInterceptor.sessionIds.add(header.getSessionAttributes().get("sessionId").toString());
        }
    }
}
