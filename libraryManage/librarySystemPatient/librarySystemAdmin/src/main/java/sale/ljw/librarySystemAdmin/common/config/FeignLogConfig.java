package sale.ljw.librarySystemAdmin.common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignLogConfig {
    /**
     * 日志记录
     * @return
     */
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
