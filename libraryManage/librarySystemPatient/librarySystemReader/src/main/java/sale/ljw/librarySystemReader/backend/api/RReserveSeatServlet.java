package sale.ljw.librarySystemReader.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/reserveSeatByReader")
public class RReserveSeatServlet {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/reserveSeat/modifyForm")
    public void modifyForm(){
        simpMessagingTemplate.convertAndSend("/reserve/modifyForm", "啥");
    }
}
