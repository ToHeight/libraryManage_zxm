package sale.ljw.librarySystemReader.common.config.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "library-system-admin", configuration = FeignLogConfig.class)
public interface librarySystemAdminFeignClient {
}