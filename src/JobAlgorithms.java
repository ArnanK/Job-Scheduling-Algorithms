import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class JobAlgorithms{

    public JobAlgorithms(ArrayList<JobObject> jobsList, int totalTime){
        initializeJobs(jobsList);
        
    }

    
    /*------------------------------------------Job Scheduling Algorithms-------------------------------------------- */

    /**
     * The FIFO function simulates job execution in a First-In-First-Out manner, calculating turnaround 
     * time, waiting time, and exit time for each job. (non-preemtive)
     * 
     * The algorithm runs in O(n) because the queue is bounded by O(n) and the iteration of the job is in O(n).
     * 
     * @param jobList arrayList of the JobObject 
     * @param totalTime duration of the simulation.
     */
    protected void FIFO(ArrayList<JobObject> jobsList, int totalTime){
        System.out.println("FIFO Algorithm\n");
        
        //initialize queue
        Queue<JobObject> queue = new LinkedList<JobObject>();
        //keep track of earliest arrival time
        Queue<JobObject> allJobs = new LinkedList<JobObject>(jobsList);

        int t=1;
        
        
        while(t<=totalTime || !queue.isEmpty()){
            //when the current time = arrival time, add them into the queue.
            while(!allJobs.isEmpty() && t == allJobs.peek().arrivalTime){
                queue.add(allJobs.poll());
            }

            //decrement remaining CPU Burst
            if(!queue.isEmpty())queue.peek().remainingCpuBurst--;

            //if there is a job and the job is finished, then remove the job from the queue.
            if(!queue.isEmpty() && queue.peek().remainingCpuBurst == 0){
                JobObject exitJob = queue.poll();
                exitJob.exitTime = t + 1;
                exitJob.turnAroundTime = exitJob.exitTime - exitJob.arrivalTime;
                exitJob.waitingTime = exitJob.turnAroundTime - exitJob.cpuBurst;
            }

            //increment time
            t++;
        }

        //print the job and reset all the calculated values.
        printJobs(jobsList);
        
        resetAllJobs(jobsList);
    }


    /**  The `SJF` method in the `JobAlgorithms` class is implementing the Shortest Job First (SJF) 
        scheduling algorithm. (non-preemtive)
        
        This algorithm runs in O(n) considering if we treat the time as a constant.
        This algorithm utilizes a heap which at most runs in O(logN) but the iteration of the total jobs makes this algorithm O(n).
        
        @param jobList arrayList of the JobObject
        @param totalTime duration of the simulation.
     */
    protected void SJF(ArrayList<JobObject> jobsList, int totalTime){
        System.out.println("SJF Algorithm\n");
        
        PriorityQueue<JobObject> pq = new PriorityQueue<JobObject>(25, shortestJobComparator);
        
        //keep track of earliest arrival time
        Queue<JobObject> allJobs = new LinkedList<JobObject>(jobsList);

        //Keeps track of any active job and the remainingCPUBurst Time.
        JobObject activeJob = new JobObject(-1,-1,-1);
        
        int t = 1; //time
        //increment through time or until pq is empty or active job is running.
        while(t<=totalTime || !pq.isEmpty() || activeJob.remainingCpuBurst > 0){
            //Checks to see if the job arrival time matches the current time.
            while(!allJobs.isEmpty() && t == allJobs.peek().arrivalTime){
                //If there are no jobs in queue and the active job is not currently running, then add new job.
                if(pq.size() == 0 && activeJob.remainingCpuBurst <= 0){
                    activeJob = allJobs.poll();
                }else{
                    //else add into the heap
                    //add job to heap.
                    pq.add(allJobs.poll());
                }
            }
        
            //decerement job.
            activeJob.remainingCpuBurst--;

            //if the job is finished, set exit time.
            if(activeJob.remainingCpuBurst == 0){
                activeJob.exitTime = t+1;
                activeJob.turnAroundTime = activeJob.exitTime - activeJob.arrivalTime;
                activeJob.waitingTime = activeJob.turnAroundTime - activeJob.cpuBurst;
                //if there are jobs in the queue, set this as the active job.
                if(pq.size()>0){
                    activeJob = pq.poll();
                }
            }
            
            //increment time
            t++;
            
        }

        //print the job and reset all the calculated values.
        printJobs(jobsList);
        resetAllJobs(jobsList);
    }

    /**  The `SRT` method in is implementing the Shortest Remaining Job scheduling algorithm. (preemptive)
        
        This algorithm runs in O(n) considering if we treat the time as a constant.
        This algorithm utilizes a heap which at most runs in O(logN) but the iteration of the total jobs makes this algorithm O(n).
        
        @param jobList arrayList of the JobObject
        @param totalTime duration of the simulation.
     */
    protected void SRT(ArrayList<JobObject> jobsList, int totalTime){
        System.out.println("SRT Algorithm\n");
        PriorityQueue<JobObject> pq = new PriorityQueue<JobObject>(25, shortestJobComparator);
        
        //keep track of earliest arrival time
        Queue<JobObject> allJobs = new LinkedList<JobObject>(jobsList);

        int t = 1; //time
        //increment through time or until pq is empty.
        while(t<=totalTime || !pq.isEmpty()){

            //add any new jobs which are available at the current time.
            while(!allJobs.isEmpty() && t == allJobs.peek().arrivalTime){
                pq.add(allJobs.poll());
            }

            //if there is a job in the pq, decrement the active job.
            if(!pq.isEmpty()) pq.peek().remainingCpuBurst--;

            //if there is a job and the job is finished, then remove the job from the heap.
            if(!pq.isEmpty() && pq.peek().remainingCpuBurst == 0){
                JobObject exitJob = pq.poll();
                exitJob.exitTime = t + 1;
                exitJob.turnAroundTime = exitJob.exitTime - exitJob.arrivalTime;
                exitJob.waitingTime = exitJob.turnAroundTime - exitJob.cpuBurst;
            }
            
            //increment time
            t++;

        }

        //print the job and reset all the calculated values.
        printJobs(jobsList);
        resetAllJobs(jobsList);
    }

    /**  The `HighestPriority` method in is implementing the Highest Priority scheduling algorithm. (preemptive)
        
        This algorithm runs in O(n) considering if we treat the time as a constant.
        This algorithm utilizes a heap which at most runs in O(logN) but the iteration of the total jobs makes this algorithm O(n).
        
        @param jobList arrayList of the JobObject
        @param totalTime duration of the simulation.
     */
    protected void HighestPriority(ArrayList<JobObject> jobsList, int totalTime){
        System.out.println("Highest Priority Algorithm\n");
        PriorityQueue<JobObject> pq = new PriorityQueue<JobObject>(25, priorityComparator);
        
        //keep track of earliest arrival time
        Queue<JobObject> allJobs = new LinkedList<JobObject>(jobsList);

        int t = 1; //time
        //increment through time or until pq is empty.
        while(t<=totalTime || !pq.isEmpty()){

            //add any new jobs which are available at the current time.
            while(!allJobs.isEmpty() && t == allJobs.peek().arrivalTime){
                pq.add(allJobs.poll());
            }

            //if there is a job in the pq, decrement the active job.
            if(!pq.isEmpty()) pq.peek().remainingCpuBurst--;

            //if there is a job and the job is finished, then remove the job from the heap.
            if(!pq.isEmpty() && pq.peek().remainingCpuBurst == 0){
                JobObject exitJob = pq.poll();
                exitJob.exitTime = t + 1;
                exitJob.turnAroundTime = exitJob.exitTime - exitJob.arrivalTime;
                exitJob.waitingTime = exitJob.turnAroundTime - exitJob.cpuBurst;
            }
            
            //increment time
            t++;

        }

        //print the job and reset all the calculated values.
        printJobs(jobsList);
        resetAllJobs(jobsList);

    }

    /**  The `RoundRobin` method in is implementing the Round Robin scheduling algorithm. (non-preemtive)
        
        This algorithm runs in O(n) considering if we treat the time as a constant.
        This algorithm utilizes a heap which at most runs in O(logN) but the iteration of the total jobs makes this algorithm O(n).
        
        @param jobList arrayList of the JobObject
        @param totalTime duration of the simulation.
     */
    protected void RoundRobin(ArrayList<JobObject> jobsList, int totalTime, int quantumTime, int contextSwitch){
        System.out.println("Round Robin Algorithm\n");
        
        //initialize queue
        Queue<JobObject> queue = new LinkedList<JobObject>();
        
        //keep track of earliest arrival time
        Queue<JobObject> allJobs = new LinkedList<JobObject>(jobsList);

        //keep track for context switch
        Boolean activeContextSwitch = false;
        int remainingContextSwitch = contextSwitch;

        int quantumCounter = 0;
        int t=1;

        while(t<=totalTime || !queue.isEmpty()){
            //add any new jobs which are available at the current time.
            while(!allJobs.isEmpty() && t == allJobs.peek().arrivalTime){
                JobObject e = allJobs.poll();
                queue.add(e);
            }
            //if curr state is context switch state, perform only context switch calculations.
            if(activeContextSwitch){
                remainingContextSwitch--;
                if(remainingContextSwitch <= 0){
                    activeContextSwitch = false; 
                    t--;
                }
            //else, decrement active job burst.
            }else{
            
                if(!queue.isEmpty()) {
                    queue.peek().remainingCpuBurst--;
                    quantumCounter++;
                }
                if(!queue.isEmpty() && queue.peek().remainingCpuBurst == 0){
                    JobObject exitJob = queue.poll();
                    exitJob.exitTime = t + 1;
                    exitJob.turnAroundTime = exitJob.exitTime - exitJob.arrivalTime;
                    exitJob.waitingTime = exitJob.turnAroundTime - exitJob.cpuBurst;
                    quantumCounter = 0;
                }
                //if we exceeded the quantumTime, send the job into the back of the queue.
                if(quantumCounter == quantumTime){
                    JobObject e = queue.poll();
                    queue.add(e);
                    quantumCounter = 0;
                    activeContextSwitch = true;
                    remainingContextSwitch = contextSwitch;
                }
            }
            t++;
        }

        //print the job and reset all the calculated values.
        printJobs(jobsList);
        resetAllJobs(jobsList);

    }


    
    /*------------------------------------------------Job Methods------------------------------------------------------ */

    /**
     * The `initializeJobs` function creates and adds 25 new Job objects to a jobsList.
     */
    protected void initializeJobs(ArrayList<JobObject> jobsList){
        jobsList.clear();
        for(int i=0; i<25; i++){
            JobObject j = new JobObject();
            jobsList.add(j);
        }
        Collections.sort(jobsList, initialJobComparator);
    }

    /**
     * The `resetAllJobs` function iterates through a list of jobs and calls the `resetJob` method on
     * each job.
     */
    private void resetAllJobs(ArrayList<JobObject> jobsList){
        for(int i=0; i<jobsList.size(); i++){
            jobsList.get(i).resetJob();
        }
    }

    /**
     * The `printJobs` function prints the details of jobs in a list, including arrival time, CPU
     * burst, priority, exit time, turnaround time, and waiting time.
     */
    public void printJobs(ArrayList<JobObject> jobsList){
        System.out.println("Job\tarrivalTime\tcpuBurst\tpriority\texitTime\tturnAroundTime\twaitingTime");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for(int i=0; i<25; i++){
            System.out.print(" " + (int) (i+1) + "\t");
            System.out.println(jobsList.get(i).toString());
        }
        turnAroundTimeAverage(jobsList);
        calcualteThroughput(70, jobsList);
        System.out.println("\n\n");
        
    }

    /**
     * The function calculates the average turn around time for a list of jobs and prints the result.
     * 
     * @param jobsList entire jobList Array with JobObject
     */
    private void turnAroundTimeAverage(ArrayList<JobObject> jobsList){
        double avg = 0;
        for(JobObject job:jobsList){
            avg += job.turnAroundTime;
        }

        avg /= jobsList.size();
        System.out.println("\nThe Turn Around Time Average is: " + avg);
    }

    /**
     * The function calculates the throughput of jobs in a list that have exited before a specified
     * time.
     * 
     * @param fixedTime specify fixed time of throughput
     * @param jobsList entire jobList Array with JobObject
     */
    private void calcualteThroughput(int fixedTime, ArrayList<JobObject> jobsList){
        int throughput = 0;
        for(JobObject job:jobsList){
            if(job.exitTime <= fixedTime)throughput++;
        }
        System.out.println("The Throughput at time "+ fixedTime + " is: " + throughput);
    }



    /*------------------------------------------------Job Comparators-------------------------------------------------- */
    
    /*  Comparator based on their `arrivalTime` attribute.  */
    Comparator<JobObject> initialJobComparator = new Comparator<JobObject>() {
        @Override
        public int compare(JobObject j1, JobObject j2) {
            return Integer.compare(j1.arrivalTime, j2.arrivalTime);
        }
    };


    /*  Comparator for both SJF and SRT algorithms */
    Comparator<JobObject> shortestJobComparator = new Comparator<JobObject>() {
        @Override
        public int compare(JobObject j1, JobObject j2){
            if(j1.remainingCpuBurst != j2.remainingCpuBurst){
                return Integer.compare(j1.remainingCpuBurst, j2.remainingCpuBurst);
            }else{
                return Integer.compare(j1.arrivalTime, j2.arrivalTime);
            }
        }
    };

    /*  Comparator for Highest Priority algorithm */
    Comparator<JobObject> priorityComparator = new Comparator<JobObject>() {
        @Override
        public int compare(JobObject j1, JobObject j2){
            if(j1.priority != j2.priority){
                return Integer.compare(j2.priority, j1.priority);
            }else if(j1.remainingCpuBurst != j2.remainingCpuBurst){
                return Integer.compare(j1.remainingCpuBurst, j2.remainingCpuBurst);
            }else{
                return Integer.compare(j1.arrivalTime, j2.arrivalTime);
            }
        }
    };

}
