public class ThreadPool {
    private int numThreads;
    private ThreadWorker[] threadWorkers;
    private BlockingQueue blockingQueue;
    private boolean isStoppedGradually = false;

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
    public synchronized void stopImmediately(){
        System.out.println("ThreadPool is being stopped");
        blockingQueue.setBlocked(true);
        blockingQueue.printAll();
        blockingQueue.setEmptyQueue();
        for(int i = 0; i < numThreads; i++){
            threadWorkers[i].stopPoolImmediately();
        }
    }
    public synchronized void stopGradually(){
        System.out.println("ThreadPool is being stopped gradually");
        blockingQueue.printAll();
        blockingQueue.setBlocked(true);
    }
    public synchronized void pause(){
        System.out.println("paused");
        blockingQueue.setBlocked(true);
        blockingQueue.printAll();
        ThreadWorker.pausePool();
    }
    public synchronized void unpause(){
        System.out.println("unpaused");
        blockingQueue.printAll();
        blockingQueue.setBlocked(false);
        ThreadWorker.resumePool();
    }
}
