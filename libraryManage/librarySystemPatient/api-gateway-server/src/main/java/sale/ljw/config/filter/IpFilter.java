package sale.ljw.config.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sale.ljw.config.utils.HttpUtils;

import java.util.List;

@Component
public class IpFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> strings = exchange.getRequest().getHeaders().get("X-Real-Ip");
        System.out.println("环境客户端IP：" + ((strings != null && strings.size() != 0) ? strings.get(0) + " —内网穿透(Ngrok)环境" : HttpUtils.getIpAddress(exchange.getRequest()) + "—真实客户端IP"));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
