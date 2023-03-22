public class Task implements Runnable {
    private int executionTime;
    private int number;

    public Task(int number, int executionTime) {
        this.number = number;
        this.executionTime = executionTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {

    }
    public String toString(){
        return "task " + this.number + " execution time " + executionTime + "\n";
    }
}
