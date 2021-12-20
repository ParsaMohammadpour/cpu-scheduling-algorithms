import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class RoundRobin {
    private Scanner scanner;

    private FileWriter writer;

    private ArrayList<Process> processes;

    private int time;

    private int timeQuantum;


    public RoundRobin(Scanner scanner, FileWriter writer, int timeQuantum) {
        this.scanner = scanner;
        this.writer = writer;
        this.timeQuantum = timeQuantum;
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
        writeOutput("RR " + timeQuantum);
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
//                System.out.println(":////");
                continue;
            }
            Process current = available.remove(0);
            writeOutput(time + " " + current.getProcessNumber());
//            System.out.println("Here!");
//            System.out.println("Time: " + time);
            if (current.getPassed() + timeQuantum > current.getBurstTime()) {
                time += current.getBurstTime() - current.getPassed();
                addArrivedProcesses(available);
                waitingSum += current.findWaitingTime(time);
//                System.out.println("1");
            } else if (current.getPassed() + timeQuantum == current.getBurstTime()) {
                time += timeQuantum;
                addArrivedProcesses(available);
                waitingSum += current.findWaitingTime(time);
//                System.out.println("2");
            } else {
                time += timeQuantum;
                current.updatePassed(timeQuantum);
                addArrivedProcesses(available);
                available.add(current);
//                System.out.println("3");
            }
        }
        double averageWaitingTime = ((double) waitingSum) / ((double) processCounter);
        writeOutput("AVG Waiting Time: " + averageWaitingTime);
    }

    private void addArrivedProcesses(ArrayList<Process> available) {
        while ((!processes.isEmpty()) && (time >= processes.get(0).getArivalTime()))
            available.add(processes.remove(0));
    }
}
