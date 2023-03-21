import java.util.concurrent.LinkedBlockingQueue;
public class ThreadPool {
    private int numThreads;
    private ThreadWorker[] threadWorkers;
    private BlockingQueue blockingQueue;

    public ThreadPool(int numberThreads) {
        this.numThreads = numberThreads;
        blockingQueue = new BlockingQueue();
        threadWorkers = new ThreadWorker[numberThreads];

        for (int i = 0; i < numberThreads; i++) {
            threadWorkers[i] = new ThreadWorker(blockingQueue);
            threadWorkers[i].start();
        }
    }

    public void execute(Task task) {
        synchronized (blockingQueue) {
            blockingQueue.put(task);
            blockingQueue.notifyAll();
        }
    }
    public void stop(){
        for(int i = 0; i < numThreads; i++){
            threadWorkers[i].stopPoolImmediately();
            System.out.println(threadWorkers[i].isInterrupted());
        }
    }


}
