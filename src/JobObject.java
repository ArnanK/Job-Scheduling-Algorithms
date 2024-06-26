public class JobObject {
    //Job Object Attributes
    protected int arrivalTime; //random 1-250
    protected int cpuBurst; //random 2-15
    protected int priority; //random 1-5(5 is highest)
    protected int exitTime; //algorithm result
    protected int turnAroundTime; //algorithm result
    protected int waitingTime; //algorithm result
    protected int remainingCpuBurst; //keeps track of cpuBurst

    // This code snippet is the constructor of the `Job` class in Java. When a new `JobObject` is
    // created, this constructor is called automatically. Inside the constructor, it initializes the
    // `arrTime`, `cpuBurst`, and `priority` attributes of the `JobObject` object with random values.
    public JobObject(){
        this.arrivalTime = (int) ((Math.random() * (250-1)) + 1);
        this.cpuBurst = (int) ((Math.random() * (15-2)) + 2);
        this.priority = (int) ((Math.random() * (5-1)) + 1);
        this.remainingCpuBurst = this.cpuBurst;
    }

    public JobObject(int arrivalTime, int cpuBurst, int priority){
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.priority = priority;
        this.remainingCpuBurst = this.cpuBurst;
    }

    public String toString(){
        String str = String.format("    %d\t\t  %d\t\t    %d\t\t  %d\t\t     %d\t\t    %d", this.arrivalTime, this.cpuBurst, this.priority, this.exitTime, this.turnAroundTime, this.waitingTime);
        return str;
    }

    public int compareTo(JobObject j1){
        return Integer.compare(this.arrivalTime, j1.arrivalTime);
    }

    /**
     * The resetJob function resets the exit time, turnaround time, and remaining time of a job to
     * zero. This is used when checking multiple algorithms for a job.
     */
    public void resetJob(){
        this.exitTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.remainingCpuBurst = this.cpuBurst;

    }


}

