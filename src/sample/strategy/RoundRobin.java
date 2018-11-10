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

        SimProcessor processor = module.getProcessor();

        if(!module.isRandom()){
            module.setMaxProcesses();
        }

        while(module.hasActiveProcesses() || !module.hasMaxProcesses()){

            Platform.runLater(()->display.updateTime(time));

            //If the user wants to use a random simulator, the
            if(module.isRandom() && time >= module.getNextArrival() && !module.hasMaxProcesses()){
                module.createRandomProcess(time);
            }

            //These are the actions to swap a process when it has finished processing
            if(processor.isFinsihedProcessing(time) || processor.isTimeUp(time)){
                SimProcess process = processor.removeProcess(time);
                if(process.isTerminated()){ //This just checks to see if a process is ready to be terminated
                    module.terminateProcess(process);
                }
                lastRunningProcess = process.getProcessId();
            }

            counter = 0;
            //These are the actions to add a process to the processor
            while(processor.isEmpty() && processor.isAvailable(time) && counter < module.getMaxProcesses()-1) {

                SimProcess process = null;
                int highestID = 0;

                //Finds the highest ID number and resets the last running process
                for(int i = 0; i < module.getProcesses().size(); i++){
                    if(highestID < module.getProcesses().get(i).getProcessId())
                        highestID = module.getProcesses().get(i).getProcessId();
                }
                if(highestID <= lastRunningProcess){
                    lastRunningProcess = 0;
                }

                //Gets the next process from the list.
                for (int i = module.getProcesses().size() - 1; i >= 0; i--) {
                    if (module.getProcesses().get(i).getNextRunTime() <= time && module.getProcesses().get(i).getProcessId() > lastRunningProcess)
                        process = module.getProcesses().get(i);
                }

                if (process != null) {
                    processor.runProcess(process, time);
                } else
                    counter++;
            }

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                //This resets all data if there is an interrupt, also it completes the while loop so the thread can end
                module.setMaxProcesses(0);
                for(SimProcess process: module.getProcesses())
                    module.terminateProcess(process);
                time = 0;
                module.clearProcesses();
                if(!processor.isEmpty())
                    processor.removeProcess(time);
            }

            time++;
        }

        module.printProcesses();

    }
}