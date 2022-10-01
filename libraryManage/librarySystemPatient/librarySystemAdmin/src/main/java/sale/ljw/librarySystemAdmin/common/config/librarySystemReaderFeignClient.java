package sale.ljw.librarySystemAdmin.common.config;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "library-system-reader",configuration = FeignLogConfig.class)
public interface librarySystemReaderFeignClient {

}
