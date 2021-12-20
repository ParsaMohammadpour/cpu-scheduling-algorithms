import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class SJF extends Schedular{

    public SJF(Scanner scanner, FileWriter writer) {
        super(scanner, writer);
    }

    public void start() throws IOException {
        readInput();
        writeOutput("SJF");
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o1.getArivalTime(), o2.getArivalTime());
            }
        });
        int processCounter = processes.size();
        addArrivedProcesses();
        long waitingSum = 0;
        while (!((processes.isEmpty()) && (available.isEmpty()))){
            if (available.isEmpty()){
                time++;
                addArrivedProcesses();
                continue;
            }
            available.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return Integer.compare(o1.getBurstTime(), o2.getBurstTime());
                }
            });
            Process current = available.remove(0);
            writeOutput(time + " " + current.getProcessNumber());
            time += current.getBurstTime();
            waitingSum += current.findWaitingTime(time);
            addArrivedProcesses();
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }
}
