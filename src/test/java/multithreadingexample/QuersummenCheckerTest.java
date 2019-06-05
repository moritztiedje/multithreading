package multithreadingexample;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static junit.framework.TestCase.fail;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TaskQueueConfig.class, ThreadPoolConfig.class, TaskProducer.class})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class QuersummenCheckerTest {

    @Autowired private LinkedBlockingQueue<Runnable> taskQueue;
    @Autowired private ThreadPoolExecutor threadPoolExecutor;
    @Autowired private TaskProducer taskProducer;

    private QuersummenChecker checker;

    @Before
    public void setup() {
        checker = new QuersummenChecker(taskProducer, threadPoolExecutor);
    }

    @Test
    public void doesNotFailOnFirstQuersummen() throws InterruptedException {
        checker.checkNextBatchOfQuersummen();

        try {
        await().until(this::oneThousandTasksHaveRun);
        } catch (ConditionTimeoutException exception) {
            fail("Only " + threadPoolExecutor.getCompletedTaskCount() + " tasks were run");
        }

        assertThatNothingHappened();
    }

    private boolean oneThousandTasksHaveRun() {
        return threadPoolExecutor.getCompletedTaskCount() == 1000L;
    }

    @Test
    public void doesNotFailOnFirstQuersummen2() throws InterruptedException {
        checker.checkNextBatchOfQuersummen();

        try {
            await().until(() -> somethingWasDone() && nothingIsBeingDone() && noTasksAreLeftToDo());
        } catch (ConditionTimeoutException exception) {
            if(!somethingWasDone())
                fail("No tasks were completed");
            if(!noTasksAreLeftToDo())
                fail("Not all tasks were completed");
            if(!nothingIsBeingDone())
                fail("Tasks were not completed in time");
        }

        assertThatNothingHappened();
    }

    private boolean noTasksAreLeftToDo() {
        return taskQueue.isEmpty();
    }

    private boolean nothingIsBeingDone() {
        return threadPoolExecutor.getActiveCount() == 0;
    }

    private boolean somethingWasDone() {
        return threadPoolExecutor.getCompletedTaskCount() > 0;
    }

    private void assertThatNothingHappened() {
        // The Program did not crash, so there you go.
    }
}