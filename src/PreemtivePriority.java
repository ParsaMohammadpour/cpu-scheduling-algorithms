import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PreemtivePriority extends Schedular{


    public PreemtivePriority(Scanner scanner, FileWriter writer) {
        super(scanner, writer);
    }


    public void start() throws IOException {
        readInput();
        writeOutput("PR_withPREMP");
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
            while ((current.getPassed() < current.getBurstTime()) && ((available.isEmpty()) || (available.get(0).getPriority() >= current.getPriority()))) {
                time++;
                addArrivedProcesses();
                available.sort(new Comparator<Process>() {
                    @Override
                    public int compare(Process o1, Process o2) {
                        return Integer.compare(o1.getPriority(), o2.getPriority());
                    }
                });
                current.updatePassed(1);
            }
            if (current.getPassed() < current.getBurstTime())
                available.add(current);
            else
                waitingSum += current.findWaitingTime(time);
            addArrivedProcesses();
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }
}