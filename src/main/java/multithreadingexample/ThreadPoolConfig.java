package multithreadingexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
class ThreadPoolConfig {

    private LinkedBlockingQueue<Runnable> taskQueue;

    @Autowired
    ThreadPoolConfig(LinkedBlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Bean
    ThreadPoolExecutor createThreadPool() {
        return new ThreadPoolExecutor(
                10,
                20,
                1,
                TimeUnit.HOURS,
                taskQueue
        );
    }

}
