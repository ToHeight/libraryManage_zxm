package sale.ljw.librarySystemAdmin.common.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "library-system-reader",configuration = FeignLogConfig.class)
public interface librarySystemReaderFeignClient {
    @GetMapping("/librarySystemReader/testServletReader/testGetById/{id}")
    public String testGetById(@PathVariable int id);
}
