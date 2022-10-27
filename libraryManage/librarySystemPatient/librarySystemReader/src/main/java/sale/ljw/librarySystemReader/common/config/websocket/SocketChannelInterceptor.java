package sale.ljw.librarySystemReader.common.config.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * 功能：频道拦截器，类似管道，可以获取消息的一些meta数据
 *
 */
public class SocketChannelInterceptor extends ChannelInterceptorAdapter {
    /**
     * 消息被实际发送到频道之前进行调用
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
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(message);//消息头访问器
        if(headerAccessor.getCommand()==null) return;//避免非stomp消息类型，例如心跳检测
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        switch (headerAccessor.getCommand()){
            //连接状态
            case CONNECT:
                System.out.println("connect sessionId = "+sessionId);
                break;
            //断开连接
            case DISCONNECT:
                System.out.println("disconnect sessionId = "+sessionId);
                break;
            default:
                break;
        }
    }

    /**
     * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源的清理
     * @param message
     * @param channel
     * @param ex
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        super.afterReceiveCompletion(message, channel, ex);
    }
}
