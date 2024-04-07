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
        //FIFO(250);
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
 * The FIFO function sorts a list of jobs based on arrival time, simulates queue behavior, and
 * calculates turnaround time and waiting time for each job.
 * O(m * n).
 */
    private void FIFO(){
        //Sorts the jobs based on the arrival time. O(nlog(n)). This allows to mimic queue like behavior without queue.
        Collections.sort(jobsList, new Comparator<Job>(){
            @Override
            public int compare(Job j1, Job j2) {
                return Integer.compare(j1.arrivalTime, j2.arrivalTime);
            }
        });
        
        //initialize the first time t where the program runs.
        int t = jobsList.get(0).arrivalTime;
        
        //iterate through each job from earliest arrival time to latest. This mimics queue like behavoir without queue.
        for(int i=0; i<jobsList.size(); i++){
            //if the job has not arrived yet, initialize the exit time with the arrival time and cpu burst
            if(jobsList.get(i).arrivalTime > t){
                jobsList.get(i).exitTime = jobsList.get(i).arrivalTime + jobsList.get(i).cpuBurst;
                t = jobsList.get(i).exitTime;

            }else{
                //else, keep track of the current time, t.
                jobsList.get(i).exitTime = t + jobsList.get(i).cpuBurst;
                t = jobsList.get(i).exitTime;
            }
            
            //calculates the rest of the attributes.
            jobsList.get(i).turnAroundTime = jobsList.get(i).exitTime - jobsList.get(i).arrivalTime;
            jobsList.get(i).waitingTime = jobsList.get(i).turnAroundTime - jobsList.get(i).cpuBurst;

        }
    }

    private void tFIFO(int totalTime){
        //Sorts the jobs based on the arrival time. O(nlog(n)). This allows to mimic queue like behavior without queue.
        
        //initialize queue
        Queue<Job> queue = new LinkedList<Job>();
        
        //Simulate time. O(n) ~ O(1) in this case b/c max time is 250.
        for(int t=1; t<=totalTime; t+=1){
            
            //iterate through each job in jobList. O(m) ~ O(1) in this case b/c max 25 jobs.
            for(Job job: jobsList){
                if(job.arrivalTime == t){
                    queue.add(job);
                }
            }

        }

    }


    public void printJobs(){
        System.out.println("Job\tarrivalTime\tcpuBurst\tpriority\texitTime\tturnAroundTime\twaitingTime");
        for(int i=0; i<25; i++){
            System.out.print(" " + (int) (i+1) + "\t");
            System.out.println(jobsList.get(i).toString());
        }
    }

}
