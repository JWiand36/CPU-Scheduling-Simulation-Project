package sample.display;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        this.processor = new ProcessorEditorDisplay(this);
        this.main = new MainDisplay(controller);
        this.chart = new ChartDataDisplay();
        this.module = module;
        this.controller = controller;

        NavDisplay nav = new NavDisplay(controller, this);

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
        main.reset();
    }

    void resetData(){
        process.reset();
        processor.reset();
    }

    void setMainPane(){ primary.setCenter(new ScrollPane(main)); }

    void setProcessPane() { primary.setCenter(new ScrollPane(process)); }

    void setProcessorPane() { primary.setCenter(new ScrollPane(processor)); }

    void displayManual(){ process.displayManual(); }

    void displayRandom(){ process.displayRandom(); }

    public void displayProcessor(int id){ chart.displayProcessor(id);}

    public String getStrategy(){ return side.getStrategy(); }

    public String getMax() { return process.getMax(); }

    public void setMax(int max) { process.setMax(max); }

    public String getArvRate() { return process.getArvRate(); }

    public String getCpuRate() { return process.getCpuRate(); }

    public String getIoRate() { return process.getIoRate(); }

    public String getProcessRate() { return process.getProcessRate(); }

    public String getPriorityRate() { return process.getPriorityRate(); }

    public String getSpeed() { return side.getSpeed(); }

    public boolean getRandom(){ return side.getRandom(); }

    public void setProcesses(ArrayList<SimProcess> processes) { process.setProcesses(processes); }

    public ArrayList<TextField> getNames() { return process.getNames(); }

    public ArrayList<TextField> getArrivals() { return process.getArrivals(); }

    public ArrayList<TextField> getCpus() { return process.getCpus(); }

    public ArrayList<TextField> getIos() { return process.getIos(); }

    public ArrayList<TextField> getProcessTimes() { return process.getProcessTimes(); }

    public ArrayList<TextField> getPriorities() { return process.getPriorities(); }

    public ArrayList<TextField> getRunTimes() { return processor.getRunTimes(); }

    public ArrayList<TextField> getContexts() { return processor.getContexts(); }

    public String getMaxProcessors(){ return processor.getMaxProcessors(); }

    public void displayError(){ secondStage.show(); }

    public void addProcess(SimProcess process){
        moveToBusy(process);
        main.getChildren().add(process);
    }

    public void moveToReady(SimProcess process){
        if(process.getProcessId() < 11)
            process.setCenterX(180);
        else
            process.setCenterX(235);

        process.setFill(Color.RED);
        process.setCenterY(((process.getProcessId()-1)%10)*55+50);
    }

    public void moveToBusy(SimProcess process){
        if(process.getProcessId() < 11)
            process.setCenterX(30);
        else
            process.setCenterX(85);

        process.setFill(Color.GOLD);
        process.setCenterY(((process.getProcessId()-1)%10)*55+50);
    }

    public void moveToContext(int id, SimProcess process){
        process.setFill(Color.YELLOWGREEN);
        process.setCenterY(id*55);
        process.setCenterX(355);
    }

    public void moveToProcessor(int id, SimProcess process){
        process.setFill(Color.GREEN);
        process.setCenterY(id*55);
        process.setCenterX(450);
    }

    public void moveToTerminate(SimProcess process){
        if(process.getProcessId() < 11)
            process.setCenterX(545);
        else
            process.setCenterX(600);

        process.setFill(Color.GREEN);
        process.setCenterY(((process.getProcessId()-1)%10)*55+50);
    }

    public void addProcessor(int id){
        Rectangle rect = new Rectangle(50,50);
        rect.setY(id*55 - 25);
        rect.setX(425);
        rect.setFill(Color.BLACK);
        main.getChildren().add(rect);
    }
}
