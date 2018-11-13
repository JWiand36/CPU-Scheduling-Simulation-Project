package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import sample.display.DisplayController;
import sample.module.MainModule;
import sample.module.SimProcess;
import sample.strategy.First;
import sample.strategy.Priority;
import sample.strategy.RoundRobin;
import sample.strategy.Shortest;

import java.util.ArrayList;

public class Controller {

    private Thread thread;
    private Shortest shortest;
    private RoundRobin roundRobin;
    private First first;
    private Priority priority;
    private MainModule module;
    private IO io;
    private DisplayController display;

    Controller(BorderPane primary){

        this.module = new MainModule();
        this.display = new DisplayController(this, primary, module);
        module.setDisplay(display);

        this.roundRobin = new RoundRobin(module, display);
        this.shortest = new Shortest(module, display);
        this.first = new First(module, display);
        this.priority = new Priority(module, display);
        this.io = new IO(display);
        this.thread = new Thread(shortest);

    }

    public void runSimulation(){
        setSettings();
        setProcessors();
        setProcesses();

        //Adjusts the settings and the strategy selected.
        setStrategy();
        thread.start();
    }

    public void stopSimulation(){
        //The if prevents a bug when the user closes the system. If the stop button is pushed and the user closes the
        //program it will cause a NullPointerException.
        if(thread != null)
            thread.interrupt();
        thread = null;
        module.reset();
    }

    //The user can select which strategy they wish to use and this sets the strategy in the thread.
    private void setStrategy() {
        String strategy = display.getStrategy();

        if(strategy.equals("Shortest"))
            this.thread = new Thread(shortest);
        else if(strategy.equals("Round Robin"))
            this.thread = new Thread(roundRobin);
        else if(strategy.equals("First"))
            this.thread = new Thread(first);
        else if(strategy.equals("Priority"))
            this.thread = new Thread(priority);
    }

    private void setSettings(){

        int max = 5;
        int arv = 5;
        int cpu = 5;
        int io = 5;
        int pros = 5;
        int pri = 5;
        int spe = 1000;

        try {
            //Checks to make sure everything is a number, otherwise use the values above.
            max = Integer.parseInt(display.getMax());
            arv = Integer.parseInt(display.getArvRate());
            cpu = Integer.parseInt(display.getCpuRate());
            io = Integer.parseInt(display.getIoRate());
            pros = Integer.parseInt(display.getProcessRate());
            pri = Integer.parseInt(display.getPriorityRate());
            spe = Integer.parseInt(display.getSpeed());
        }catch (NumberFormatException e){ display.displayError();}

        if(max > 20) {
            max = 20;
            display.setMax(max);
        }else if(max < 0) {
            max = 1;
            display.setMax(max);
        }


        module.editSettings(max, arv, cpu, io, pros, pri, spe, display.getRandom());
    }

    public void setProcesses(){
        if(!display.getRandom()) {
            ArrayList<TextField> names = display.getNames();
            ArrayList<TextField> arrivals = display.getArrivals();
            ArrayList<TextField> cpus = display.getCpus();
            ArrayList<TextField> ios = display.getIos();
            ArrayList<TextField> processTimes = display.getProcessTimes();
            ArrayList<TextField> priorities = display.getPriorities();

            for (int i = 0; i < names.size(); i++) {

                try {
                    String name = names.get(i).getText();
                    int arrival = Integer.parseInt(arrivals.get(i).getText());
                    int cpu = Integer.parseInt(cpus.get(i).getText());
                    int io = Integer.parseInt(ios.get(i).getText());
                    int process = Integer.parseInt(processTimes.get(i).getText());
                    int priority = Integer.parseInt(priorities.get(i).getText());
                    module.addProcess(name, arrival, cpu, io, process, priority);
                    display.addProcess(module.getProcesses().get(i));
                } catch (NumberFormatException e) {
                    display.displayError();
                }
            }
        }else{
            for(int i = 0; i < module.getMaxProcesses(); i++){
                module.createRandomProcess();
            }
        }
    }

    public void setProcessors(){

        ArrayList<TextField> runTimes = display.getRunTimes();

        for (int i = 0; i < runTimes.size(); i++) {
            try {
                int runTime = Integer.parseInt(display.getRunTimes().get(i).getText());
                int context = Integer.parseInt(display.getContexts().get(i).getText());

                module.createProcessor(runTime, context);
                display.addProcessor(module.getProcessors().get(i).getId());

            }catch(NumberFormatException n){
                display.displayError();
            }
        }

        if(module.isProcessorsEmpty()){
            module.createProcessor(2,0);
            display.addProcessor(module.getProcessors().get(0).getId());
        }

    }

    public void getIOData(){ io.inputData(); }

    public void saveDataIO(){
        ArrayList<SimProcess> processes = new ArrayList<>();

        ArrayList<TextField> names = display.getNames();
        ArrayList<TextField> arrivals = display.getArrivals();
        ArrayList<TextField> cpus = display.getCpus();
        ArrayList<TextField> ios = display.getIos();
        ArrayList<TextField> processTimes = display.getProcessTimes();
        ArrayList<TextField> priorities = display.getPriorities();

        for (int i = 0; i < names.size(); i++) {

            String name = names.get(i).getText();
            int arrival = Integer.parseInt(arrivals.get(i).getText());
            int cpu = Integer.parseInt(cpus.get(i).getText());
            int io = Integer.parseInt(ios.get(i).getText());
            int process = Integer.parseInt(processTimes.get(i).getText());
            int priority = Integer.parseInt(priorities.get(i).getText());

            processes.add(new SimProcess(processes.size()+1, name, arrival, cpu, io, process, priority));
        }

        io.saveData(processes);
    }
}
