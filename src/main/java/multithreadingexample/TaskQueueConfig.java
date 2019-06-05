package multithreadingexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TaskQueueConfig {

    @Bean
    public LinkedBlockingQueue<Runnable> createTaskQueue(){
        return new LinkedBlockingQueue<>();
    }
}
