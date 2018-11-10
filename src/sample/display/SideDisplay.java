package sample.display;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import sample.Controller;

class SideDisplay extends FlowPane{

    private Button runBtn = new Button("Run");
    private ComboBox<String> stratBox = new ComboBox<>();
    private TextField maxFld = new TextField("5");
    private TextField arvFld = new TextField("5");
    private TextField cpuFld = new TextField("5");
    private TextField ioFld = new TextField("5");
    private TextField prosFld = new TextField("5");
    private TextField priFld = new TextField("5");
    private TextField prsrRunFld = new TextField("2");
    private CheckBox ranBox = new CheckBox();

    SideDisplay(Controller controller, DisplayController display){

        String[] strats = {"Shortest", "Round Robin", "First", "Priority"};

        Button reset = new Button("Reset Display");

        stratBox.setValue(strats[0]);
        stratBox.getItems().addAll(FXCollections.observableArrayList(strats));

        Label infoLbl = new Label("Parameters for Random Simulations");
        Label maxLbl = new Label("Max Processes: ");
        Label arvLbl = new Label("Arrival Rate: ");
        Label cpuLbl = new Label("CPU Rate: ");
        Label ioLbl = new Label("IO Rate: ");
        Label prosLbl = new Label("Process Rate: ");
        Label priLbl = new Label("Priority Rate: ");
        Label ranLbl = new Label("Random");
        Label rrLbl = new Label("Parameters for Round Robin");
        Label prsRunLbl = new Label("Processor Run Time");

        this.getChildren().add(stratBox);
        this.getChildren().addAll(infoLbl, maxLbl, maxFld, arvLbl, arvFld, cpuLbl, cpuFld);
        this.getChildren().addAll(ioLbl, ioFld, prosLbl, prosFld, priLbl, priFld, ranLbl, ranBox);
        this.getChildren().addAll(rrLbl, prsRunLbl, prsrRunFld, reset, runBtn);

        this.setMaxWidth(100);
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        //Allows the user to run and stop the simulation using the same button
        runBtn.setOnAction(e->{
            if(runBtn.getText().equals("Run")){
                controller.runSimulation();
                runBtn.setText("Stop");
            }else{
                controller.pauseSimulation();
                runBtn.setText("Run");
            }
        });

        reset.setOnAction(e-> display.resetDataDisplay());
    }

    Button getRunBtn(){ return runBtn; }

    String getStrategy(){ return stratBox.getSelectionModel().getSelectedItem();}

    String getMax() { return maxFld.getText(); }

    String getArvRate() { return arvFld.getText(); }

    String getCpuRate() { return cpuFld.getText(); }

    String getIoRate() { return ioFld.getText(); }

    String getProcessRate() { return prosFld.getText(); }

    String getPriorityRate(){ return priFld.getText(); }

    boolean getRandom(){ return ranBox.isSelected(); }

    String getProcessorRunTime(){ return prsrRunFld.getText(); }
}
