package multithreadingexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
class QuersummenChecker {
    private TaskProducer taskProducer;
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    QuersummenChecker(TaskProducer taskProducer, ThreadPoolExecutor threadPoolExecutor){
        this.taskProducer = taskProducer;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 1000)
    void checkNextBatchOfQuersummen() throws InterruptedException {
        taskProducer.produceTasksForNext1000Numbers();
        Thread.sleep(3000L);
        threadPoolExecutor.prestartAllCoreThreads();
    }
}
