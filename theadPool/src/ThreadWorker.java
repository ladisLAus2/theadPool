import java.util.concurrent.LinkedBlockingQueue;

public class ThreadWorker extends Thread {
    private BlockingQueue queue;

    private static boolean running = true;
    private static boolean paused = false;
    private static Object pauseObject = new Object();
    Thread thread;
    public ThreadWorker(BlockingQueue queue) {
        this.queue = queue;
    }
    public static void pausePool(){
        paused = true;
    }
    public static void stopPoolGradually(){
        running = false;
        resumePool();
    }
    public void stopPoolImmediately(){
        thread.interrupt();
    }
    public static void resumePool(){
        synchronized (pauseObject){
            paused = false;
            pauseObject.notifyAll();
        }
    }

    public void run() {
        thread = currentThread();
        Task task;
        while (running) {
            if(thread.isInterrupted()){
                System.out.println(thread.getName() + " was interrupted");
            }
            synchronized (pauseObject){
                if(!running){
                    break;
                }
                if(paused){
                    try{
                        System.out.println(thread.getName() + " waiting");
                        pauseObject.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(!running){
                        break;
                    }
                }
            }

            synchronized (queue) {
                while (queue.isEmpty()) {
                    if (queue.getIsBlocked())
                        break;
                    if (!running) {
                        break;
                    }
                    try {
                        System.out.println(thread.getName() + " waits");
                        queue.wait();
                    } catch (Exception e) {
                        if (queue.getIsBlocked() && queue.isEmpty()) {
                            System.out.println("threadpool was stopped and is empty");
                            System.out.println(thread.getName() + " out");
                            return;
                        }
                        System.out.println("error while waiting");
                    }
                }
            }
                try {
                    if (queue.isEmpty() && queue.getIsBlocked())
                        break;
                    synchronized (queue) {
                        task = (Task) queue.take();
                    }
                    try {
                        try {
                            Thread.sleep(task.getNumber() * 1000);
                        } catch (InterruptedException e) {
                            System.out.println("thread " + currentThread().getName() + " was interrupted");
                            return;
                        }
                        System.out.println("TASK " + task.getNumber() + " WAS executed by " + currentThread().getName() + " with time " + task.getExecutionTime() + " and execution time is " + queue.getTimeOfExecutionQueue());
                    } catch (Exception e) {
                        System.out.println("Error trying to execute task " + task.getNumber());
                    }
                } catch (Exception e) {
                    System.out.println("Error trying to take task");
                }

        }
        System.out.println(thread.getName() + " out");
    }
}
