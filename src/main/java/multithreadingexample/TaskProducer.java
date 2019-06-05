package multithreadingexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
class TaskProducer {
    @Autowired
    private LinkedBlockingQueue<Runnable> taskQueue;

    private int currentNumber = 1;

    void produceTasksForNext1000Numbers() {
        for (int index = 0; index < 1000; index++) {
            int effectivelyFinalNumber = this.currentNumber;
            taskQueue.add(() -> {
                checkQuersummenRegel(effectivelyFinalNumber);
            });
            currentNumber++;
        }
    }

    private void checkQuersummenRegel(int number) {
        if (quersummenRegelIsIncorrectFor(number)) {
            throw new QuersummenRegelFailedException(number);
        }
        printNumberWithoutException(number);
    }

    private boolean quersummenRegelIsIncorrectFor(int number) {
        return (!isDivisibleBy3(number) && isQuersummeDivisibleBy3(number)) ||
                (isDivisibleBy3(number) && !isQuersummeDivisibleBy3(number));
    }

    private boolean isQuersummeDivisibleBy3(int number) {
        return isDivisibleBy3(calculateQuersumme(number));
    }

    private void printNumberWithoutException(int number) {
        System.out.println("Wieder nix: " + number);
    }

    private boolean isDivisibleBy3(int number) {
        int dividedNumber = number / 3;
        return dividedNumber * 3 == number;
    }

    private int calculateQuersumme(int number) {
        int quersumme = 0;
        while (number > 0) {
            quersumme = quersumme + number % 10;
            number = number / 10;
        }
        return quersumme;
    }

}
