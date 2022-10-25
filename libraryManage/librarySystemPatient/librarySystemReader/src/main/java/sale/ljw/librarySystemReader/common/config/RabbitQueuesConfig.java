package sale.ljw.librarySystemReader.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueuesConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //定时任务队列
    public static final String borrowTask="librarySystemReaderBorrowTask";
    //死信队列
    public static final String borrowTimeOut="librarySystemReaderBorrowTimeOut";
    //回滚队列
    public static final String borrow="librarySystemReaderBorrow";
    public static final String activity="librarySystemReaderActivity";
    @Bean
    public Queue borrowTask(){
        return new Queue(borrowTask);
    }
    @Bean
    public Queue borrowTimeOut(){
        return new Queue(borrowTimeOut);
    }
    @Bean
    public Queue activityTask(){
        return new Queue(activity);
    }
}
