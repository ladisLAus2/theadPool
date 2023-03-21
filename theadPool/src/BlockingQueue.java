import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
    private Queue queue = new LinkedList<Task>();
    private int maxTime = 60;
    private int timeOfExecutionQueue = 0;

    public BlockingQueue() {

    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getTimeOfExecutionQueue() {
        return timeOfExecutionQueue;
    }

    public synchronized void put(Task task) {
        if (this.timeOfExecutionQueue + task.getExecutionTime() > maxTime) {
            System.out.println(task.getNumber() + " WAS NOT PUT with time it's execution time " + task.getExecutionTime() + " and queueTime is " + timeOfExecutionQueue);
            return;
        }
        this.timeOfExecutionQueue += task.getExecutionTime();
        queue.add(task);
        System.out.println("the task " + task.getNumber() + " WAS PUT " + " and it's execution time " + task.getExecutionTime() + " and queueTime is " + timeOfExecutionQueue + "   ---");
    }

    public synchronized Task take() throws Exception {
        Task item = (Task) queue.remove();

        this.timeOfExecutionQueue -= item.getExecutionTime();
        return item;
    }
}
