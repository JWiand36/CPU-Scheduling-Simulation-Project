package sample.display;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller;
import sample.module.MainModule;
import sample.module.SimProcess;

import java.util.ArrayList;

public class DisplayController {

    private SideDisplay side;
    private ProcessEditorDisplay process;
    private ProcessorEditorDisplay processor;
    private MainDisplay main;
    private NavDisplay nav;
    private ChartDataDisplay chart;
    private DataDisplay data = new DataDisplay();
    private MainModule module;
    private BorderPane primary;

    private Stage secondStage;

    private Controller controller;

    public DisplayController(Controller controller, BorderPane primary, MainModule module){

        this.primary = primary;
        this.side = new SideDisplay(controller, this);
        this.process = new ProcessEditorDisplay(this);
        this.processor = new ProcessorEditorDisplay(controller);
        this.main = new MainDisplay(controller);
        this.nav = new NavDisplay(controller, this);
        this.chart = new ChartDataDisplay();
        this.module = module;
        this.controller = controller;

        FlowPane pane = new FlowPane();
        pane.getChildren().add(new Text("Number Format Error: Make sure you input values are numbers!"));
        pane.setPadding(new Insets(5));
        this.secondStage = new Stage();
        this.secondStage.setScene(new Scene(pane,350,50));

        ScrollPane sideScroll = new ScrollPane(side);
        sideScroll.setMinWidth(150);
        sideScroll.setMaxWidth(150);

        ScrollPane dataScroll = new ScrollPane(data);
        dataScroll.setMinWidth(150);
        dataScroll.setMaxWidth(150);

        ScrollPane chartScroll = new ScrollPane(chart);
        chartScroll.setMinHeight(150);
        chartScroll.setMaxHeight(150);

        primary.setTop(nav);
        primary.setLeft(sideScroll);
        primary.setRight(dataScroll);
        primary.setBottom(chartScroll);
        setMainPane();

    }

    public void displayProcessorChange(int processorId, String processName, int time, String type){
        this.chart.displayChange(processorId, processName, time, type);
    }

    //When a simulation completes it displays the data and resets the processes and run button
    public void finishSimulation(ArrayList<SimProcess> processes){
        side.getRunBtn().setText("Run");
        data.displayProcessData(processes);
        module.reset();
    }

    void resetDataDisplay(){
        data.reset();
        chart.reset();
    }

    void setMainPane(){ primary.setCenter(new ScrollPane(main)); }

    void setProcessPane() { primary.setCenter(new ScrollPane(process)); }

    void setProcessorPane() { primary.setCenter(new ScrollPane(processor)); }

    void displayManual(){ process.displayManual(); }

    void displayRandom(){ process.displayRandom(); }

    public String getStrategy(){ return side.getStrategy(); }

    public String getMax() { return process.getMax(); }

    public String getArvRate() { return process.getArvRate(); }

    public String getCpuRate() { return process.getCpuRate(); }

    public String getIoRate() { return process.getIoRate(); }

    public String getProcessRate() { return process.getProcessRate(); }

    public String getPriorityRate() { return process.getPriorityRate(); }

    public String getProcessors() { return side.getProcessors(); }

    public boolean getRandom(){ return side.getRandom(); }

    public String getProcessorRunTime(){ return side.getProcessorRunTime(); }

    public void updateTime(int time){ data.updateTime(time);}

    public ArrayList<TextField> getNames() { return process.getNames(); }

    public ArrayList<TextField> getArrivals() { return process.getArrivals(); }

    public ArrayList<TextField> getCpus() { return process.getCpus(); }

    public ArrayList<TextField> getIos() { return process.getIos(); }

    public ArrayList<TextField> getProcessTimes() { return process.getProcessTimes(); }

    public ArrayList<TextField> getPriorities() { return process.getPriorities(); }

    public void displayError(){ secondStage.show(); }
}
