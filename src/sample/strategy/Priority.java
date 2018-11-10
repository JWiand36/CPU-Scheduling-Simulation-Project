package sample.strategy;

import javafx.application.Platform;
import sample.comparators.AvailableComparator;
import sample.comparators.PriorityComparator;
import sample.display.DisplayController;
import sample.module.MainModule;
import sample.module.SimProcess;
import sample.module.SimProcessor;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Priority implements StrategyInterface {

    private MainModule module;
    private DisplayController display;
    private int time;

    public Priority(MainModule module, DisplayController display){

        this.display = display;
        this.module = module;
    }


    @Override
    public void run() {

        time = 0;

        PriorityQueue<SimProcess> queue = new PriorityQueue<>(new PriorityComparator());
        PriorityQueue<SimProcess> busyQueue = new PriorityQueue<>(new AvailableComparator());

        SimProcessor processor = module.getProcessor();

        if(!module.isRandom()){
            busyQueue.addAll(module.getProcesses());
            module.setMaxProcesses();
        }

        while(module.hasActiveProcesses() || !module.hasMaxProcesses()){

            Platform.runLater(()-> display.updateTime(time));

            //If the user wants to use a random simulator, the
            if(module.isRandom() && time >= module.getNextArrival() && !module.hasMaxProcesses()){
                busyQueue.add(module.createRandomProcess(time));
            }

            //These are the actions to swap a process when it has finished processing
            if(processor.isFinsihedProcessing(time)){
                SimProcess process = processor.removeProcess(time);
                if(process.isTerminated()){ //This just checks to see if a process is ready to be terminated
                    module.terminateProcess(process);
                }else{
                    busyQueue.add(process);
                }
            }

            //This pulls items out of the busy queue and adds them to the ready queue
            while(!busyQueue.isEmpty() && busyQueue.peek().getNextRunTime() <= time) {
                queue.add(busyQueue.poll());
            }

            if(!queue.isEmpty()){
                //These are the actions to add a process to the processor
                if(processor.isEmpty() && processor.isAvailable(time)){ //If the processor isn't running anything
                    processor.runProcess(queue.poll(), time);
                }else if(!processor.isEmpty() && queue.peek().getPriority() < processor.getPriority()){
                    busyQueue.add(processor.removeProcess(time));
                    if(processor.isAvailable(time))
                        processor.runProcess(queue.poll(), time);
                }
            }

            time++;

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
                while(!queue.isEmpty())
                    queue.poll();
                while(!busyQueue.isEmpty())
                    busyQueue.poll();
            }
        }

        module.printProcesses();
    }

}
