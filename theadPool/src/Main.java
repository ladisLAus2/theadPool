import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws Exception {
        ThreadPool threadPool = new ThreadPool(4);

        TimerTask t = new TimerTask() {
            public void run() {
                threadPool.stop();
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 3000L;
        timer.schedule(t,delay);

//        TimerTask t2 = new TimerTask() {
//            public void run() {
//                ThreadWorker.resumePool();
//            }
//        };
//        Timer timer2 = new Timer("Timer");
//
//        long delay2 = 30000L;
//        timer2.schedule(t2,delay2);

        for (int i = 0; i < 10000; i++) {
            int time = 5 + (int) (Math.random() * ((12 - 5) + 5));
            //int time = new Random().nextInt(100,200);
            Task task = new Task(i, time);
            threadPool.execute(task);
            Thread.sleep(500);
        }

    }
}
