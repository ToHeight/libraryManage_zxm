package sale.ljw.librarySystemReader.common.config.websocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpHandShakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) throws Exception {
      if(response instanceof ServletServerHttpResponse){
            ServletServerHttpResponse resp = (ServletServerHttpResponse) response;
        }
        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest req=(ServletServerHttpRequest) request;
            HttpSession session = req.getServletRequest().getSession();
            String sessionId = session.getId();
            attributes.put("sessionId", sessionId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}