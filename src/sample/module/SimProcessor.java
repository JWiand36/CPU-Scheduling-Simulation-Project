package sample.module;

import javafx.application.Platform;
import sample.display.DisplayController;

public class SimProcessor {

    private SimProcess runningProcess = null;
    private DisplayController display;
    private int lastRunningTime = -1;
    private int runTimeNeeded = 0;
    private int contextSwitch = 0;

    //The processTime is how much time a process is given to run. The runningClock helps determine if the time is up
    //This is used for the Round Robin strategy.
    private int processTime = 0;
    private int runningClock = 0;
    private int id = 0;
    private boolean running = false;
    private boolean firstTime = true;

    public SimProcessor(DisplayController display, int id, int runTime, int contextSwitch){
        this.id = id;
        this.display = display;
        this.contextSwitch = contextSwitch;
        this.processTime = runTime;
    }


    public void addProcess(SimProcess process, int time) {
        runningProcess = process;
    }

    //A new process is added to the processor. The time when a process starts iss displayed and the logic used to know when
    //a process's time is up. runTimeNeeded helps determine when a process has finished it's cpu burst if it is lower then
    //the time allowed.
    public void runProcess(int time){
        Platform.runLater(()->display.displayProcessorChange(this.id, runningProcess.getName(), time, "Entered"));
        runningProcess.enterProcessor(time);
        runTimeNeeded = runningProcess.getTimeNeeded() + time;
        runningClock = time + processTime;
        running = true;
        firstTime = false;
    }

    //The current running process is removed and displayed.
    public SimProcess removeProcess(int time){

        SimProcess temp = runningProcess;

        if(running) {
            if (temp != null) {
                Platform.runLater(() -> display.displayProcessorChange(this.id, temp.getName(), time, "Left"));
                temp.exitProcessor(time);
                lastRunningTime = time;
                runningProcess = null;
            }
            running = false;
        }
        return temp;
    }

    //This is to help simulate a context switch, the last time a process has completed is added with the time for
    //a context switch. Then when the time reaches above the added time, the processor is available to take the next
    //process.
    public boolean isAvailable(int time){
            return 0 > lastRunningTime || time >= lastRunningTime + contextSwitch;
    }

    public boolean isRunning(){ return running; }

    //This is used when a processor allows a certain amount of time.
    public boolean isTimeUp(int time){
        return time >= runningClock && runningProcess != null;
    }

    public boolean isEmpty(){ return runningProcess == null; }

    //This lets the simulation know that the running process has completed it's processing time and may be terminated
    //or sent to IO.
    public boolean isFinsihedProcessing(int time){ return time >= runTimeNeeded && runningProcess != null; }

    public int processTime(int time){ return runningProcess.getProcessTime() - time; }

    public void resetLastRunningTime(){ lastRunningTime = 0; }

    public int getPriority() { return runningProcess.getPriority(); }
}
