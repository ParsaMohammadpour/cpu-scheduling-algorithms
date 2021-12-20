import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PreemtivePriority {
    private Scanner scanner;

    private FileWriter writer;

    private ArrayList<Process> processes;

    private int time;


    public PreemtivePriority(Scanner scanner, FileWriter writer) {
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
        writeOutput("PR_withPREMP");
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
            while ((current.getPassed() < current.getBurstTime()) && ((available.isEmpty()) || (available.get(0).getPriority() >= current.getPriority()))) {
                time++;
                addArrivedProcesses(available);
                available.sort(new Comparator<Process>() {
                    @Override
                    public int compare(Process o1, Process o2) {
                        return Integer.compare(o1.getPriority(), o2.getPriority());
                    }
                });
                current.updatePassed(1);
                /*System.out.println("Infinitie Loop :");
                System.out.println(current.getProcessNumber() + "\t" + current.getBurstTime() + "\t" + current.getPassed() + "\t" + current.getPriority());
                System.out.println("List :");
                if (!available.isEmpty())
                    System.out.println(available.get(0).getProcessNumber() + "\t" + available.get(0).getPriority());*/
            }
            if (current.getPassed() < current.getBurstTime())
                available.add(current);
            else
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
