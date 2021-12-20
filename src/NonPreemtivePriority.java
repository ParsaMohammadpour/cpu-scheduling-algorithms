import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class NonPreemtivePriority {
    private Scanner scanner;

    private FileWriter writer;

    private ArrayList<Process> processes;

    private int time;


    public NonPreemtivePriority(Scanner scanner, FileWriter writer) {
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
        writeOutput("PR_noPREMP");
        ArrayList<Process> available = new ArrayList<>();
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o1.getArivalTime(), o2.getArivalTime());
            }
        });
        int processCounter = processes.size();
        time = processes.get(0).getArivalTime();
        this.addArrivedProcesses(available);
        long waitingSum = 0;
        while (!((processes.isEmpty()) && (available.isEmpty()))) {
            if (available.isEmpty()) {
                time++;
                this.addArrivedProcesses(available);
                continue;
            }
            available.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return Integer.compare(o1.getPriority(), o2.getPriority());
                }
            });
            /*for (Process p :
                    available) {
                System.out.print(p.getProcessNumber() + " " + p.getPriority() + "\t");
            }*/
//            System.out.println();
            Process current = available.remove(0);
            writeOutput(time + " " + current.getProcessNumber());
            time += current.getBurstTime();
            addArrivedProcesses(available);
            waitingSum += current.findWaitingTime(time);
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }

    private void addArrivedProcesses(ArrayList<Process> available) {
        while ((!processes.isEmpty()) && (time >= processes.get(0).getArivalTime()))
            available.add(processes.remove(0));
    }
}
