import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<JobObject> jobsList = new ArrayList<JobObject>();
        int totalTime = 250;
        JobAlgorithms run = new JobAlgorithms(jobsList, totalTime);


        run.FIFO(jobsList, totalTime);
        run.SJF(jobsList, totalTime);
        run.SRT(jobsList, totalTime);
        run.HighestPriority(jobsList, totalTime);
        run.RoundRobin(jobsList, totalTime, 5, 0);

    }

}
