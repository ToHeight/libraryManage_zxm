package sale.ljw.librarySystemReader.common.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//表示为webSocket代理类
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //创建端点：相当于基站
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册端点，任何发布和订阅消息都需要先连接该端点
        registry.addEndpoint("/librarySystemReader-websocket").setAllowedOriginPatterns("*").addInterceptors(new HttpHandShakeInterceptor()).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置消息代理,表示中介转发，客户端订阅前缀，就是推送的路径前缀
        registry.enableSimpleBroker("/reserve", "/chat","/pay");
        //添加头信息，客户端连接服务端的前缀，客户端发送数据的前缀
        registry.setApplicationDestinationPrefixes("/app");
    }
    /**
     * 自定义Channel拦截器，连接拦截器
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelInterceptor());
    }
    /**
     * 自定义拦截器，断开拦截器
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelInterceptor());
    }
}
