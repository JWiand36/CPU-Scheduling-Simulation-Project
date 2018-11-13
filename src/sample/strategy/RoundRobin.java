package sample.strategy;

import javafx.application.Platform;
import sample.display.DisplayController;
import sample.module.MainModule;
import sample.module.SimProcess;
import sample.module.SimProcessor;

public class RoundRobin implements StrategyInterface {

    private MainModule module;
    private DisplayController display;

    private int time;

    public RoundRobin(MainModule module, DisplayController display){
        this.display = display;
        this.module = module;
    }

    @Override
    public void run(){

        int lastRunningProcess = 0;
        int counter;

        time = 0;

        while(module.hasActiveProcesses()){


            for(SimProcessor processor: module.getProcessors()) {
                //These are the actions to swap a process when it has finished processing
                if (processor.isFinsihedProcessing(time) || processor.isTimeUp(time)) {
                    SimProcess process = processor.removeProcess(time);
                    if (process.isTerminated()) { //This just checks to see if a process is ready to be terminated
                        module.terminateProcess(process);
                    }
                }

                counter = 0;
                //These are the actions to add a process to the processor
                while (processor.isEmpty() && counter < module.getMaxProcesses()) {
                    SimProcess process = null;
                    int highestID = 0;

                    //Finds the highest ID number and resets the last running process
                    for (int i = 0; i < module.getProcesses().size(); i++) {
                        if (highestID < module.getProcesses().get(i).getProcessId())
                            highestID = module.getProcesses().get(i).getProcessId();

                        if(module.getProcesses().get(i).getNextRunTime() <= time)
                            display.moveToReady(module.getProcesses().get(i));
                    }
                    if (highestID <= lastRunningProcess) {
                        lastRunningProcess = 0;
                    }

                    //Gets the next process from the list.
                    for (int i = module.getProcesses().size() - 1; i >= 0; i--) {
                        if (module.getProcesses().get(i).getNextRunTime() <= time &&
                                module.getProcesses().get(i).getProcessId() > lastRunningProcess &&
                                !module.getProcesses().get(i).isRunning())
                            process = module.getProcesses().get(i);
                    }
                    if (process != null && processor.getRunningProcess() != process) {
                        processor.addProcess(process);
                        lastRunningProcess = process.getProcessId();
                    } else
                        counter++;
                }

                if(!processor.isEmpty() && processor.isAvailable(time) && !processor.isRunning())
                    processor.runProcess(time);

            }

            time++;

            try{
                Thread.sleep(module.getSpeed());
            }catch(InterruptedException e){
                //This resets all data if there is an interrupt, also it completes the while loop so the thread can end
                for(SimProcess process: module.getProcesses())
                    module.terminateProcess(process);
                time = 0;
                module.reset();
            }
        }

        module.printProcesses();

    }
}
