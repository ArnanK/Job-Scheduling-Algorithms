public class Job {
    //Job Object Attributes
    protected int arrivalTime; //random 1-250
    protected int cpuBurst; //random 2-15
    protected int priority; //random 1-5(5 is highest)
    protected int exitTime; //algorithm result
    protected int turnAroundTime; //algorithm result
    protected int waitingTime; //algorithm result

    // This code snippet is the constructor of the `Job` class in Java. When a new `Job` object is
    // created, this constructor is called automatically. Inside the constructor, it initializes the
    // `arrTime`, `cpuBurst`, and `priority` attributes of the `Job` object with random values.
    public Job(){
        this.arrivalTime = (int) ((Math.random() * (250-1)) + 1);
        this.cpuBurst = (int) ((Math.random() * (15-2)) + 2);
        this.priority = (int) ((Math.random() * (5-1)) + 1);
    }

    public String toString(){
        String str = String.format("    %d\t\t  %d\t\t    %d\t\t  %d\t\t     %d\t\t    %d", this.arrivalTime, this.cpuBurst, this.priority, this.exitTime, this.turnAroundTime, this.waitingTime);
        return str;
    }

    public int compareTo(Job j1){
        return Integer.compare(this.arrivalTime, j1.arrivalTime);
    }

    /**
     * The resetJob function resets the exit time, turnaround time, and remaining time of a job to
     * zero. This is used when checking multiple algorithms for a job.
     */
    public void resetJob(){
        this.arrivalTime = 0;
        this.cpuBurst = 0;
        this.priority = 0;
        this.exitTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
    }


}

