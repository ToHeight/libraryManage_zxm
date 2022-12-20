package sale.ljw.librarySystemAdmin.common.config.springSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.LoginFailHandle;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.LoginSuccessHandle;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>application name：librarySystemPatient - AdminUsernamePasswordAuthenticationFilter</p>
 * <p>application describing： </p>
 * <p>copyright： </p>
 * <p>company： </p>
 * <p>time：2022-12-15 08:53:13</p>
 *
 * @author liujingwen
 * @version ver 1.0
 * @since 1.8
 */
public class AdminUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AdminUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, LoginSuccessHandle loginSuccessHandle, LoginFailHandle loginFailHandle) {
        //只允许post请求,不是post请求也能经过filter
        this.setPostOnly(false);
        //设置登陆的路径和请求方式
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(loginSuccessHandle);
        this.setAuthenticationFailureHandler(loginFailHandle);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/loginAdmin/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = map.get("username");
                username = username != null ? username.trim() : "";
                String password = map.get("password");
                password = password != null ? password.trim() : "";
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
                setDetails(request, authRequest);
                return getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new AuthenticationServiceException("User input error");
    }


}
