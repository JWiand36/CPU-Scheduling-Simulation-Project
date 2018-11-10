package sample.module;

import javafx.scene.shape.Circle;

public class SimProcess extends Circle{


    private String name;
    private int id = 0;
    private int arrival;
    private int cpu;
    private int io;
    private int process;
    private int terminate = -1;
    private int wait = 0;
    private int start = 0;
    private int next;
    private int requireCpu;
    private int initProcess;
    private int priority;

    public SimProcess(int id, String name, int arrival, int cpu, int io, int process, int priority) {
        this.id = id;
        this.name = name;
        this.arrival = arrival;
        this.next = arrival;

        if (cpu == 0 || cpu > process) {
            this.cpu = process;
            this.requireCpu = process;
        }else {
            this.cpu = cpu;
            this.requireCpu = cpu;
        }

        this.io = io;
        this.process = process;
        this.initProcess = process;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrival;
    }

    public int getProcessId(){ return id;}

    public int getProcessTime() {
        return process + start;
    }

    public int getNextRunTime() {
        return next;
    }

    int getTimeNeeded(){ return requireCpu; }

    //When a process enters the processor
    void enterProcessor(int time){
        this.start = time;
        this.wait += time - this.next;
    }

    void exitProcessor(int time){
        this.process -= time - start;

        if(process > 0) {
            this.requireCpu -= time - start;

            /*
            This calculates the next time the process is available
            If it requires IO time and the process ran enough of the CPU
            that it needs the IO, the IO time is added to the next time
            the process will be available.
            */
            if (requireCpu <= 0) {
                this.requireCpu = cpu;
                this.next = time + io;
            } else {
                this.next = time;
            }

            if(requireCpu > process){
                requireCpu = process;
            }
        }else {

            //IO has to be added because the process isn't truly done until it has
            //completed both CPU and IO
            this.terminate = time + io - arrival;
        }
    }

    public boolean isTerminated(){ return terminate >= 0; }

    //Allows the user to edit the process information
    public void editProcess(String name, int arrival, int cpu, int io, int process, int priority){
        this.name = name;
        this.arrival = arrival;
        this.next = arrival;
        this.cpu = cpu;
        this.requireCpu = cpu;
        this.io = io;
        this.process = process;
        this.priority = priority;
    }

    public String toString(){
        return "ID: "+this.id +
                "\nName: " + this.name +
                "\nArrival Time: " + this.arrival +
                "\nWait Time: " + this.wait +
                "\nCPU Time: " + this.cpu +
                "\nIO Time: " + this.io +
                "\nPriority: " + this.priority +
                "\nProcess Time: " + this.initProcess +
                "\nCompletion Time: " + this.terminate;
    }

    public int getPriority(){ return priority; }
}
