import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class NonPreemtivePriority extends Schedular{


    public NonPreemtivePriority(Scanner scanner, FileWriter writer) {
        super(scanner, writer);
    }


    public void start() throws IOException {
        readInput();
        writeOutput("PR_noPREMP");
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o1.getArivalTime(), o2.getArivalTime());
            }
        });
        int processCounter = processes.size();
        time = processes.get(0).getArivalTime();
        this.addArrivedProcesses();
        long waitingSum = 0;
        while (!((processes.isEmpty()) && (available.isEmpty()))) {
            if (available.isEmpty()) {
                time++;
                this.addArrivedProcesses();
                continue;
            }
            available.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return Integer.compare(o1.getPriority(), o2.getPriority());
                }
            });
            Process current = available.remove(0);
            writeOutput(time + " " + current.getProcessNumber());
            time += current.getBurstTime();
            addArrivedProcesses();
            waitingSum += current.findWaitingTime(time);
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }
}
