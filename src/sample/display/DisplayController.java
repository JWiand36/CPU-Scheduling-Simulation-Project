package sample.display;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import sample.Controller;
import sample.module.MainModule;
import sample.module.SimProcess;

import java.util.ArrayList;

public class DisplayController {

    private SideDisplay side;
    private EditorDisplay edit;
    private MainDisplay main;
    private NavDisplay nav;
    private DataDisplay data = new DataDisplay();
    private MainModule module;

    public DisplayController(Controller controller, BorderPane primary, MainModule module){

        this.side = new SideDisplay(controller, this);
        this.edit = new EditorDisplay(controller);
        this.main = new MainDisplay(controller);
        this.nav = new NavDisplay(controller);
        this.module = module;

        primary.setLeft(side);
        primary.setCenter(new ScrollPane(data));

    }

    public void displayProcessorChange(int processorId, String processName, int time, String type){
        this.data.displayChange(processorId, processName, time, type);
    }

    //When a simulation completes it displays the data and resets the processes and run button
    public void finishSimulation(ArrayList<SimProcess> processes){
        side.getRunBtn().setText("Run");
        data.displayProcessData(processes);
        module.clearProcesses();
    }

    void resetDataDisplay(){ data.reset(); }

    public String getStrategy(){ return side.getStrategy(); }

    public String getMax() { return side.getMax(); }

    public String getArvRate() { return side.getArvRate(); }

    public String getCpuRate() { return side.getCpuRate(); }

    public String getIoRate() { return side.getIoRate(); }

    public String getProcessRate() { return side.getProcessRate(); }

    public String getPriorityRate() { return side.getPriorityRate(); }

    public boolean getRandom(){ return side.getRandom(); }

    public String getProcessorRunTime(){ return side.getProcessorRunTime(); }

    public void updateTime(int time){ data.updateTime(time);}
}
