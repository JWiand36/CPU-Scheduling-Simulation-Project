package sample.module;

import javafx.application.Platform;
import sample.display.DisplayController;

public class SimProcessor {

    private SimProcess runningProcess = null;
    private DisplayController display;
    private int lastRunningTime = -1;
    private int runTimeNeeded = 0;
    private int contextSwitch = 0;
    private int processTime = 0;
    private int runningClock = 0;
    private int id = 0;
    private int startRunTime = 0;

    public SimProcessor(DisplayController display){
        this.display = display;
    }

    public void setContextSwitch(int value){ this.contextSwitch = value; }

    //The processTime is how much time a process is given to run. The runningClock helps determine if the time is up
    public void setProcessTime(int time){
        this.processTime = time;
        this.runningClock = time;
    }

    //A new process is added to the processor. The time when a process starts iss displayed and the logic used to know when
    //a process's time is up. runTimeNeeded helps determine when a process has finished it's cpu burst if it is lower then
    //the time allowed.
    public void runProcess(SimProcess process, int time){
        Platform.runLater(()->display.displayProcessorChange(this.id, process.getName(), time, "Entered"));
        process.enterProcessor(time);
        runTimeNeeded = process.getTimeNeeded() + time;
        this.runningProcess = process;
        runningClock = time + processTime;
        startRunTime = time;
    }

    //The current running process is removed and displayed.
    public SimProcess removeProcess(int time){
        SimProcess temp = runningProcess;
        Platform.runLater(()-> display.displayProcessorChange(this.id, temp.getName(), time, "Left"));
        temp.exitProcessor(time);
        lastRunningTime = time;
        runningProcess = null;
        return temp;
    }

    //This is to help simulate a context switch, the last time a process has completed is added with the time for
    //a context switch. Then when the time reaches above the added time, the processor is available to take the next
    //process.
    public boolean isAvailable(int time){
            return 0 > lastRunningTime || time >= lastRunningTime + contextSwitch;
    }

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
