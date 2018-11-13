package sample.module;

import javafx.application.Platform;
import sample.comparators.IDComparator;
import sample.display.DisplayController;

import java.util.ArrayList;

public class MainModule {

    private ArrayList<SimProcess> processes = new ArrayList<>();
    private ArrayList<SimProcess> terminatedProcesses = new ArrayList<>();
    private ArrayList<SimProcessor> processors = new ArrayList<>();

    private DisplayController display;

    private int maxProcesses = 0;

    //These are used to help generate processes when using a random generator
    private int arrivalRate = 0;
    private int cpuRate = 0;
    private int ioRate = 0;
    private int processRate = 0;
    private int priorityRate = 0;
    private int newArrival = 0;
    private int speed = 1000;

    private boolean random = false;

    public void editSettings(int maxProcesses, int arrivalRate, int cpuRate, int ioRate, int processRate, int priorityRate, int speed, boolean random){
        this.maxProcesses = maxProcesses;
        this.arrivalRate = arrivalRate;
        this.cpuRate = cpuRate;
        this.ioRate = ioRate;
        this.processRate = processRate;
        this.priorityRate = priorityRate;
        this.speed = speed;
        this.random = random;
    }

    public void setDisplay(DisplayController display){ this.display = display; }

    public boolean isRandom(){
        return random;
    }

    public boolean hasActiveProcesses(){
        return processes.size() > 0; }

    public void addProcess(String name, int arrival, int cpu, int io, int process, int priority){

        if(io < 0)
            io = 0;

        if(arrival < 0)
            arrival = 0;

        if(process < 0)
            process = 1;

        if(cpu < 1 || cpu > process)
            cpu = process;

        if(priority < 0)
            priority = 0;

        SimProcess newProcess = new SimProcess(this.processes.size() + 1, name, arrival, cpu, io, process, priority);
        processes.add(newProcess);
    }

    public ArrayList<SimProcess> getProcesses(){ return new ArrayList<>(processes); }

    //When a user uses the random functionality. It creates a process on spot.
    public void createRandomProcess(){
        String name = "P"+(processes.size() + terminatedProcesses.size() + 1);
        int arrival = (int)(Math.random()*this.arrivalRate)+1;
        int cpu = (int)(Math.random()*this.cpuRate)+1;
        int io = (int)(Math.random()*this.ioRate)+1;
        int process = (int)(Math.random()*this.processRate) + 1;
        int priority = (int)(Math.random()*this.priorityRate) + 1;

        this.newArrival = (int)(Math.random() * this.arrivalRate) + arrival;

        if(cpu < 1)
            cpu = process;

        SimProcess newProcess = new SimProcess(this.processes.size() + terminatedProcesses.size() + 1, name, arrival, cpu, io, process, priority);
        processes.add(newProcess);
        display.addProcess(newProcess);
    }

    //Clears the processes lists and resets the availability of the processor
    public void reset(){
        processes.clear();
        terminatedProcesses.clear();
        processors.clear();
        this.newArrival = 0;
    }

    //If a process has finished it's job, it will be moved to the terminated process list
    public void terminateProcess(SimProcess process){
        processes.remove(process);
        terminatedProcesses.add(process);
    }

    //When a simulation has finished the process data is displayed
    public void printProcesses(){
        terminatedProcesses.sort(new IDComparator());
        Platform.runLater(()->display.finishSimulation(terminatedProcesses));
    }

    public void createProcessor(int runTime, int context){

        if(runTime < 0)
            runTime = 1;

        if(context < 0)
            context = 0;

        display.displayProcessor(processors.size()+1);
        processors.add(new SimProcessor(display, processors.size()+1, runTime, context));
    }

    public ArrayList<SimProcessor> getProcessors() { return processors; }

    public int getMaxProcesses(){ return maxProcesses;}

    public int getNextArrival(){ return newArrival; }

    public int getSpeed() { return this.speed; }

    public boolean isProcessorsEmpty(){ return processors.isEmpty(); }
}
