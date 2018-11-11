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
        this.io = new IO(module);
        this.thread = new Thread(shortest);

    }

    public void runSimulation(){
        //This allows the user to retrieve data from a file if they don't wish to use a random simulation.
        if(!display.getRandom()){
            setProcesses();
        }
        //Adjusts the settings and the strategy selected.
        setSettings();
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
        int run = 1;
        int proc = 1;

        //Checks to make sure everything is a number, otherwise use the values above.
        if(isNumber(display.getMax()) || isNumber(display.getArvRate()) ||
                isNumber(display.getCpuRate()) || isNumber(display.getIoRate()) ||
                isNumber(display.getProcessRate()) || isNumber(display.getProcessorRunTime()) ||
                isNumber(display.getProcessorRunTime()) || isNumber(display.getProcessors())){
            max = Integer.parseInt(display.getMax());
            arv = Integer.parseInt(display.getArvRate());
            cpu = Integer.parseInt(display.getCpuRate());
            io = Integer.parseInt(display.getIoRate());
            pros = Integer.parseInt(display.getProcessRate());
            pri = Integer.parseInt(display.getPriorityRate());
            run = Integer.parseInt(display.getProcessorRunTime());
            proc = Integer.parseInt(display.getProcessors());
        }

        module.editSettings(max, arv, cpu, io, pros, pri, display.getRandom());

        for(int i = 0; i < proc; i++)
            module.createProcessor();

        editProcessors(run);

    }

    public void setProcesses(){
        ArrayList<TextField> names = display.getNames();
        ArrayList<TextField> arrivals = display.getArrivals();
        ArrayList<TextField> cpus = display.getCpus();
        ArrayList<TextField> ios = display.getIos();
        ArrayList<TextField> processTimes = display.getProcessTimes();
        ArrayList<TextField> priorities = display.getPriorities();

        for(int i = 0; i < names.size(); i++){
            if(isNumber(arrivals.get(i).getText()) || isNumber(cpus.get(i).getText()) ||
                    isNumber(ios.get(i).getText()) || isNumber((processTimes.get(i).getText())) ||
                    isNumber(priorities.get(i).getText())){
                String name = names.get(i).getText();
                int arrival = Integer.parseInt(arrivals.get(i).getText());
                int cpu = Integer.parseInt(cpus.get(i).getText());
                int io = Integer.parseInt(ios.get(i).getText());
                int process = Integer.parseInt(processTimes.get(i).getText());
                int priority = Integer.parseInt(priorities.get(i).getText());
                module.addProcess(name, arrival, cpu, io, process, priority);
            }
        }
    }

    public void editProcessors(int runTime){ module.editProcessors(runTime); }

    public void getIOData(){ io.inputData(); }

    public void saveDataIO(){}

    //Checks to make sure the Strings provided are actually numbers.
    public boolean isNumber(String s){

        char a;
        int c = 0;

        for(int i = 0; i < s.length(); i++){

            a = s.charAt(i);

            if(!Character.isDigit(a))
                c++;
        }

        return c <= 0 && s.length() != 0;
    }
}
