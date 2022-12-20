package sale.ljw.librarySystemAdmin.common.config.springSecurity;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.filter.AdminUsernamePasswordAuthenticationFilter;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.filter.jwtAuthenticationTokenFilter;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.AccessDeny;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.AuthenticationEnryPoint;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.LoginFailHandle;
import sale.ljw.librarySystemAdmin.common.config.springSecurity.handle.LoginSuccessHandle;

/**
 * <p>application name：librarySystemPatient - SecurityConfigtion</p>
 * <p>application describing： </p>
 * <p>copyright： </p>
 * <p>company： </p>
 * <p>time：2022-12-14 16:07:29</p>
 *
 * @author liujingwen
 * @version ver 1.0
 * @since 1.8
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private LoginSuccessHandle loginSuccessHandle;
    @Autowired
    private LoginFailHandle loginFailHandle;
    @Autowired
    private AccessDeny accessDeny;
    @Autowired
    private AuthenticationEnryPoint authenticationEnryPoint;
    @Autowired
    private RedisTemplate redisTemplate;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
//                .formLogin()
//                //自定义登录拦截器，这个就不能配置
//                .loginProcessingUrl("/loginAdmin/login")
//                .successHandler(loginSuccessHandle)
//                .failureHandler(loginFailHandle)
//                .permitAll()
//                .and()
                // 身份认证请求
                .authorizeHttpRequests()
                .anyRequest()
                //处理登录逻辑
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeny)    //权限不足的时候的逻辑处理
                .authenticationEntryPoint(authenticationEnryPoint);  //未登录是的逻辑处理;
        //配置自定义拦截器
        http.addFilterAt(new AdminUsernamePasswordAuthenticationFilter(authenticationManager(new AuthenticationConfiguration()),loginSuccessHandle,loginFailHandle), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new jwtAuthenticationTokenFilter(authenticationManager(new AuthenticationConfiguration()),redisTemplate), BasicAuthenticationFilter.class);
        // @formatter:on
        return http.build();
    }

}
