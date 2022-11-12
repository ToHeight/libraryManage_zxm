package sale.ljw.librarySystemAdmin.common.config.basicConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sale.ljw.common.utils.IdWorker;

@Configuration
public class snowflakeConfig {
    @Value(value = "${snowflake.workerId}")
    private Integer workerId;

    @Value(value = "${snowflake.datacenterId}")
    private Integer datacenterId;

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workerId, datacenterId);
    }

}
