package sale.ljw.config.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TokenFilter implements GlobalFilter, Ordered {
    private static final ResponseResult<String> result=ResponseResult.getErrorResult("token验证未通过", StatusCode.Not_Acceptable,null);

    private static final List<String> jumpOverPath=new ArrayList<>();
    static {
        Collections.addAll(jumpOverPath, "/librarySystemReader/readerLogin/login");
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        for (String jumpPath : jumpOverPath) {
            if(path.equals(jumpPath)){
                return chain.filter(exchange);
            }
        }
        List<String> token = exchange.getRequest().getHeaders().get("token");
        if((token != null && StringUtils.isNotBlank(token.get(0))) && JwtUtils.verify(token.get(0))){
            return chain.filter(exchange);
        }
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer wrap = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
        return response.writeWith(Mono.just(wrap));
    }

    //过滤器排序作用，返回的数值越小越先执行
    @Override
    public int getOrder() {
        return 2;
    }
}
