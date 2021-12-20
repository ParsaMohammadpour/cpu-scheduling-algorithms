import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Schedular {
    protected Scanner scanner;

    protected FileWriter writer;

    protected ArrayList<Process> processes;

    protected ArrayList<Process> available;

    protected int time;


    public Schedular(Scanner scanner, FileWriter writer){
        this.scanner = scanner;
        this.writer = writer;
        this.processes = new ArrayList<>();
        this.available = new ArrayList<>();
    }

    protected void addArrivedProcesses () {
        while ((!processes.isEmpty()) && (time >= processes.get(0).getArivalTime()))
            available.add(processes.remove(0));
    }

    protected void readInput() throws IOException {
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


    protected void writeOutput(String text) throws IOException {
        writer.write(text + "\n");
        writer.flush();
    }


    public abstract void start() throws IOException ;
}
