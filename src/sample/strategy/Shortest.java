package sample.strategy;

import javafx.application.Platform;
import sample.comparators.AvailableComparator;
import sample.comparators.TimeComparator;
import sample.display.DisplayController;
import sample.module.MainModule;
import sample.module.SimProcess;
import sample.module.SimProcessor;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Shortest implements StrategyInterface {

    private MainModule module;
    private DisplayController display;
    private int time;

    public Shortest(MainModule module, DisplayController display){

        this.display = display;
        this.module = module;
    }


    @Override
    public void run() {

        time = 0;

        PriorityQueue<SimProcess> queue = new PriorityQueue<>(new TimeComparator());
        PriorityQueue<SimProcess> busyQueue = new PriorityQueue<>(new AvailableComparator());

        busyQueue.addAll(module.getProcesses());

        while(module.hasActiveProcesses()){

            for(SimProcessor processor: module.getProcessors()) {
                //These are the actions to swap a process when it has finished processing
                if (processor.isFinsihedProcessing(time)) {
                    SimProcess process = processor.removeProcess(time);
                    if (process.isTerminated()) { //This just checks to see if a process is ready to be terminated
                        module.terminateProcess(process);
                    } else {
                        busyQueue.add(process);
                    }
                }

                //This pulls items out of the busy queue and adds them to the ready queue
                while (!busyQueue.isEmpty() && busyQueue.peek().getNextRunTime() <= time) {
                    SimProcess process = busyQueue.poll();
                    queue.add(process);
                    display.moveToReady(process);
                }

                if (!queue.isEmpty()) {
                    //These are the actions to add a process to the processor
                    if (processor.isEmpty()) { //If the processor isn't running anything
                        SimProcess process = queue.poll();
                        processor.addProcess(process);
                    } else if (queue.peek().getProcessTime() < processor.processTime(time)) {
                        busyQueue.add(processor.removeProcess(time));
                        processor.addProcess(queue.poll());
                    }
                }

                if (!processor.isEmpty() && processor.isAvailable(time) && !processor.isRunning()) {
                    processor.runProcess(time);
                }
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
                while(!queue.isEmpty())
                    queue.poll();
                while(!busyQueue.isEmpty())
                    busyQueue.poll();
            }
        }

        module.printProcesses();
    }

}
