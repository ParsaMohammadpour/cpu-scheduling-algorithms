import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class RoundRobin extends Schedular{
    private int timeQuantum;


    public RoundRobin(Scanner scanner, FileWriter writer, int timeQuantum) {
        super(scanner, writer);
        this.timeQuantum = timeQuantum;
    }


    public void start() throws IOException {
        readInput();
        writeOutput("RR " + timeQuantum);
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
            Process current = available.remove(0);
            writeOutput(time + " " + current.getProcessNumber());
            if (current.getPassed() + timeQuantum > current.getBurstTime()) {
                time += current.getBurstTime() - current.getPassed();
                addArrivedProcesses();
                waitingSum += current.findWaitingTime(time);
            } else if (current.getPassed() + timeQuantum == current.getBurstTime()) {
                time += timeQuantum;
                addArrivedProcesses();
                waitingSum += current.findWaitingTime(time);
            } else {
                time += timeQuantum;
                current.updatePassed(timeQuantum);
                addArrivedProcesses();
                available.add(current);
            }
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }
}