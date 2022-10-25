package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.FindActivity;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.ActivityServiceReader;
import sale.ljw.librarySystemReader.backend.service.ApplicationServiceReader;
import sale.ljw.librarySystemReader.common.config.RabbitQueuesConfig;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/activityByReader")
public class RActivityServlet {
    @Autowired
    private ActivityServiceReader activityServiceReader;
    @Autowired
    private ApplicationServiceReader applicationServiceReader;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 查询活动是否过期
     */
    //@Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "0 0 12 * * ?")
    public void checkActivityExpiredTiming(){
        rabbitTemplate.convertAndSend("", RabbitQueuesConfig.activity,"-");
    }

    /**
     * 接收检测活动过期
     * @param message
     */
    @RabbitListener(queues = RabbitQueuesConfig.activity)
    public void checkActivityExpired(String message) {
        activityServiceReader.checkActivityExpired();
    }
    /**
     * 查询活动
     * @param findActivity
     * @return
     */
    @ApiOperation(value = "查询活动")
    @PostMapping("/queryActivity")
    public ResponseResult<PageInfo<Map<String, Object>>> queryActivity(@RequestBody @Valid FindActivity findActivity,@RequestHeader(name = "token") String token) {
        return activityServiceReader.queryActivity(findActivity,token);
    }

    /**
     * 参加报名
     * @param activityId
     * @param token
     * @return
     */
    @ApiOperation(value = "参加报名")
    @GetMapping("/participateInRegistration/{activityId}")
    public ResponseResult<String> participateInRegistration(@PathVariable(value = "activityId") Integer activityId,@RequestHeader(name = "token") String token){
        return activityServiceReader.participateInRegistration(activityId,token);
    }


    /**
     * 撤销报名
     * @param applicationId
     * @param token
     * @return
     */
    @ApiOperation(value = "撤销报名")
    @GetMapping("/cancellationApplication/{applicationId}")
    public ResponseResult<String> cancellationApplication(@PathVariable Long applicationId,@RequestHeader(name = "token") String token){
        return applicationServiceReader.cancellationApplication(applicationId,token);
    }
}
