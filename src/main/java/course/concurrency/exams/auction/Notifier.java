package course.concurrency.exams.auction;

import java.util.concurrent.*;

public class Notifier {
    private ExecutorService executor;
    private final int poolSize = Runtime.getRuntime().availableProcessors();
    public Notifier() {
        executor = Executors.newFixedThreadPool(poolSize); // Создаем пул потоков для выполнения задач
    }
    public void sendOutdatedMessage(Bid bid) {
        CompletableFuture.runAsync(this::imitateSending, executor);
    }

    private void imitateSending() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
