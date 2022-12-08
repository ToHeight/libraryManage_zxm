package sale.ljw.librarySystemReader.common.config.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;
import sale.ljw.common.utils.JwtUtils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 功能：频道拦截器，类似管道，可以获取消息的一些meta数据
 */
@Component
public class SocketChannelInterceptor extends ChannelInterceptorAdapter {
    //拦截器无法注入所以采用记录方式
    public static LinkedList<String> sessionIds = new LinkedList<>();
    public static HashMap<Integer, String> connectionBindId = new HashMap<>();

    /**
     * 消息被实际发送到频道之前进行调用
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return super.preSend(message, channel);
    }

    /**
     * 频道发送之后立刻调用该方法
     *
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器
        if (headerAccessor.getCommand() == null) return;//避免非stomp消息类型，例如心跳检测
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        switch (headerAccessor.getCommand()) {
            //连接状态
            case CONNECT:
                if(headerAccessor.getNativeHeader("token")!=null){
                    Integer userId = Integer.parseInt(JwtUtils.parseJWT(headerAccessor.getNativeHeader("token").toString()));
                    //绑定id和界面id
                    connection(userId, sessionId);
                }
                System.out.println("connect sessionId = " + sessionId);
                break;
            //断开连接
            case DISCONNECT:
                disConnection(sessionId);
                break;
            default:
                break;
        }
    }

    /**
     * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源的清理
     *
     * @param message
     * @param channel
     * @param ex
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        super.afterReceiveCompletion(message, channel, ex);
    }

    public void disConnection(String sessionId) {
        sessionIds.add(sessionId);
    }

    public void connection(Integer userId, String sessionId) {
        //绑定用户id和页面id
        //查询当前是否存此用户sessionId
        if (connectionBindId.containsKey(userId)) {
            //包含存进sessiIds中
            sessionIds.add(connectionBindId.get(userId));
        }
        connectionBindId.put(userId, sessionId);

    }
}
