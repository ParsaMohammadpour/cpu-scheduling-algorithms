import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class SJF {
    private Scanner scanner;

    private FileWriter writer;

    private ArrayList<Process> processes;

    private int time;


    public SJF(Scanner scanner, FileWriter writer){
        this.scanner = scanner;
        this.writer = writer;
        this.processes = new ArrayList<>();
    }


    public void readInput() {
        int numberOfProcess = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfProcess; i++) {
            String[] line = scanner.nextLine().split(" ");
            int processNumber = Integer.parseInt(line[0]),
                    arrivalTime = Integer.parseInt(line[1]),
                    CPUburst = Integer.parseInt(line[2]),
                    priority = Integer.parseInt(line[3]);
            this.processes.add(new Process(arrivalTime, priority, processNumber, CPUburst));
        }
    }


    public void writeOutput(String text) throws IOException {
        writer.write(text + "\n");
        writer.flush();
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
        ArrayList<Process> available = new ArrayList<>();
        addArrivedProcesses(available);
        long waitingSum = 0;
        while (!((processes.isEmpty()) && (available.isEmpty()))){
            if (available.isEmpty()){
                time++;
                addArrivedProcesses(available);
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
            addArrivedProcesses(available);
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }


    private void addArrivedProcesses(ArrayList<Process> available) {
        while ((!processes.isEmpty()) && (time >= processes.get(0).getArivalTime()))
            available.add(processes.remove(0));
    }
}
