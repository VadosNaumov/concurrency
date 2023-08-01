package course.concurrency.m5_streams;

import java.util.concurrent.*;

public class ThreadPoolTask {

    private static class LIFOQueue<E> extends LinkedBlockingDeque<E> {
        @Override
        public E take() throws InterruptedException {
            return super.takeLast();
        }
    }

    // Task #1
    public ThreadPoolExecutor getLifoExecutor() {
        int corePoolSize = 6;
        int maximumPoolSize = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 30L;
        BlockingQueue<Runnable> workQueue = new LIFOQueue<>();

        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                workQueue
        );
    }

    // Task #2
    public ThreadPoolExecutor getRejectExecutor() {
        int corePoolSize = 8;
        int maximumPoolSize = 8;
        long keepAliveTime = 30L;
        BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                workQueue,
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }
}
