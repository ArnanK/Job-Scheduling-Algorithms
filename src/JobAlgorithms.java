import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class JobAlgorithms{
    private ArrayList<Job> jobsList = new ArrayList<Job>();

    public JobAlgorithms(){
        initializeJobs();
        FIFO(250);
        printJobs();
        resetAllJobs();
        SJF(250);
        printJobs();
        resetAllJobs();
    }

    /**
     * The `initializeJobs` function creates and adds 25 new Job objects to a jobsList.
     */
    private void initializeJobs(){
        for(int i=0; i<25; i++){
            Job j = new Job();
            jobsList.add(j);
        }
        Collections.sort(jobsList, new Comparator<Job>(){
            @Override
            public int compare(Job j1, Job j2) {
                return Integer.compare(j1.arrivalTime, j2.arrivalTime);
            }
        });
    }
    


    /**
     * The FIFO function simulates job execution in a First-In-First-Out manner, calculating turnaround
     * time, waiting time, and exit time for each job. The algorithm runs in O(n) because the queue is bounded by O(n)
     * and the iteration of the job is in O(n).
     * 
     * @param totalTime This parameter determines the duration of the simulation during which 
     * jobs will be processed based on their arrival times.
     */
    private void FIFO(int totalTime){
        System.out.println("FIFO Algorithm\n");
        
        //initialize queue
        Queue<Job> queue = new LinkedList<Job>();
        
        int remainingCpuBurst=0; //keeps track of the cpu Burst time for each job.
        
        //Simulate time. O(n) ~ O(1) in this case b/c max time is 250.
        for(int t=1; t<=totalTime; t+=1){
            //iterate through each job in jobList. O(m) ~ O(1) in this case b/c max 25 jobs.
            for(Job job: jobsList){
                if(job.arrivalTime == t){
                    //if there are no jobs, keep track of the cpuBurst time of curr Job locally.
                    if(queue.size() == 0){
                        remainingCpuBurst = job.cpuBurst;
                    }
                    queue.add(job);
                }
            }
            
            // This part of the code is responsible for simulating the execution of jobs in a
            // First-In-First-Out (FIFO) manner. Here's a breakdown of what it does:
            //      -if there is a job in the queue, decrement the remaining cpuBurst time of the current job.
            //      -if the job is finished(remainingTime == 0), then remove the job from queue.
            //          -Perform calculations for turnAroundTime, waitingTime, and Exit Time.
            //      -If there are futher jobs in the queue, change the remainingCpuBurst to the next Job.
            if(queue.size() != 0){
                remainingCpuBurst--;
                if(remainingCpuBurst == 0){
                    Job removeJob = queue.remove();
                    removeJob.exitTime = t+1;
                    removeJob.turnAroundTime = removeJob.exitTime - removeJob.arrivalTime;
                    removeJob.waitingTime = removeJob.turnAroundTime - removeJob.cpuBurst;

                    if(queue.size() != 0){
                        remainingCpuBurst = queue.peek().cpuBurst;
                    }
                }
            }
        }
    }




    /**  The `SJF` method in the `JobAlgorithms` class is implementing the Shortest Job First (SJF)
        scheduling algorithm. This algorithm runs in O(n) considering if we treat the time as a constant.
        This algorithm utilizes a heap which at most runs in O(logN) but the iteration of the total jobs makes this algorithm O(n).
        
        Here's a breakdown of what the method is doing:
            -Sets up a priority queue.
            -Keeps track of all active jobs.
            -Run through each instance in time t and peform necesarry calucaltions.
                -decerment remaining time.
                -add any new jobs.
                -remove any jobs that are not active anymore and replace them with any jobs in the heap.
     * 
     @param totalTime This parameter determines the duration of the simulation during which 
     * jobs will be processed based on their arrival times.
     */
    private void SJF(int totalTime){
        System.out.println("SJF Algorithm\n");
        
        PriorityQueue<Job> pq = new PriorityQueue<Job>(25, new Comparator<Job>() {
            @Override
            public int compare(Job j1, Job j2){
                if(j1.cpuBurst != j2.cpuBurst){
                    return Integer.compare(j1.cpuBurst, j2.cpuBurst);
                }else{
                    return Integer.compare(j1.arrivalTime, j2.arrivalTime);
                }
            }
        });
        //Keeps track of any active job and the remainingCPUBurst Time.
        Job activeJob = new Job();
        int remainingCpuBurst=-1;
        
        //Iterate through each time t.
        for(int t=1; t<=totalTime; t+=1){
            //Checks to see if the job arrival time matches the current time.
            for(Job job: jobsList){
                if(job.arrivalTime == t){
                    //If there are no jobs in queue and the active job is not currently running, then add new job.
                    if(pq.size() == 0 && remainingCpuBurst <= 0){
                        activeJob = job;
                        remainingCpuBurst = activeJob.cpuBurst;
                    }else{
                        //add job to heap.
                        pq.add(job);
                    }
                }
            }
            //decerement job.
            remainingCpuBurst--;
            //if the job is finished, perform remaining calulations on exit time, turn around time, and waiting time.
            if(remainingCpuBurst == 0){
                activeJob.exitTime = t+1;
                activeJob.turnAroundTime = activeJob.exitTime - activeJob.arrivalTime;
                activeJob.waitingTime = activeJob.turnAroundTime - activeJob.cpuBurst;
                //if there are jobs in the queue, set this as the active job.
                if(pq.size()>0){
                    activeJob = pq.remove();
                    remainingCpuBurst = activeJob.cpuBurst;
                }
            }
        }
        
    }

    /**
     * The `resetAllJobs` function iterates through a list of jobs and calls the `resetJob` method on
     * each job.
     */
    private void resetAllJobs(){
        for(int i=0; i<jobsList.size(); i++){
            jobsList.get(i).resetJob();
        }
    }

    /**
     * The `printJobs` function prints the details of jobs in a list, including arrival time, CPU
     * burst, priority, exit time, turnaround time, and waiting time.
     */
    public void printJobs(){
        System.out.println("Job\tarrivalTime\tcpuBurst\tpriority\texitTime\tturnAroundTime\twaitingTime");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for(int i=0; i<25; i++){
            System.out.print(" " + (int) (i+1) + "\t");
            System.out.println(jobsList.get(i).toString());
        }
        System.out.println("\n\n");
    }

}
