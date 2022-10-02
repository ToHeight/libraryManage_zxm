package sale.ljw.librarySystemReader.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testServletReader")
public class TestServletReader {
    @GetMapping("/testGetById/{id}")
    public String testGetById(@PathVariable int id){
        return "id:"+id;
    }
}
