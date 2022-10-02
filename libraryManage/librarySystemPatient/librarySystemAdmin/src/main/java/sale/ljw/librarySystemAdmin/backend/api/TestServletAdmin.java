package sale.ljw.librarySystemAdmin.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.librarySystemAdmin.common.config.librarySystemReaderFeignClient;

@RestController
@RequestMapping("/testServletAdmin")
public class TestServletAdmin {
    @Autowired
    private sale.ljw.librarySystemAdmin.common.config.librarySystemReaderFeignClient librarySystemReaderFeignClient;

    @GetMapping("/testGetById/{id}")
    public String testGetById(@PathVariable int id){
        return librarySystemReaderFeignClient.testGetById(id);
    }
}
