public class Process {
    private int burstTime;

    private int priority;

    private int arivalTime;

    private int processNumber;

    private int passed;


    public void setArivalTime(int arivalTime) {
        this.arivalTime = arivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setProcessNumber(int processNumber) {
        this.processNumber = processNumber;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getArivalTime() {
        return arivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getProcessNumber() {
        return processNumber;
    }

    public int getPassed() {
        return passed;
    }

    public Process(int arrivalTime, int priority, int processNumber, int burstTime){
        this.arivalTime = arrivalTime;
        this.priority = priority;
        this.processNumber = processNumber;
        this.burstTime = burstTime;
        this.passed =0;
    }



    public int findWaitingTime(int currentTime){
        return currentTime - arivalTime - burstTime;
    }

    public void updatePassed(int number){
        this.passed += number;
    }
}