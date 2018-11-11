package sample.display;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import sample.Controller;

import java.util.ArrayList;

class ProcessEditorDisplay extends GridPane{

    private TextField maxFldR = new TextField("5");
    private TextField arvFldR = new TextField("5");
    private TextField cpuFldR = new TextField("5");
    private TextField ioFldR = new TextField("5");
    private TextField prosFldR = new TextField("5");
    private TextField priFldR = new TextField("5");

    private Label maxLblR = new Label("Max Processes: ");
    private Label arvLblR = new Label("Arrival Rate: ");
    private Label cpuLblR = new Label("CPU Rate: ");
    private Label ioLblR = new Label("IO Rate: ");
    private Label prosLblR = new Label("Process Rate: ");
    private Label priLblR = new Label("Priority Rate: ");

    private Label amtProsLbl = new Label("Process Amt");
    private Label nameLbl = new Label("Name");
    private Label arrivalLbl = new Label("Arrival");
    private Label cpuLbl = new Label("CPU");
    private Label ioLbl = new Label("IO");
    private Label processingLbl = new Label("Processing");
    private Label priorityLbl = new Label("Priority");


    ProcessEditorDisplay (Controller controller){
        ColumnConstraints[] cols = new ColumnConstraints[7];

        displayManual();

        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        for(int i = 0; i < cols.length; i++) {
            cols[i] = new ColumnConstraints();
            cols[i].setMaxWidth(100);
            cols[i].setMinWidth(100);
            this.getColumnConstraints().add(cols[i]);
        }
    }

    void displayManual(){
        this.getChildren().clear();

        this.add(amtProsLbl, 0,0);
        this.add(nameLbl, 1, 0);
        this.add(arrivalLbl, 2, 0);
        this.add(cpuLbl, 3, 0);
        this.add(ioLbl, 4, 0);
        this.add(processingLbl, 5, 0);
        this.add(priorityLbl, 6, 0);
    }

    void displayRandom(){
        this.getChildren().clear();

        maxFldR.setPrefWidth(100);
        arvFldR.setPrefWidth(100);
        cpuFldR.setPrefWidth(100);
        ioFldR.setPrefWidth(100);
        prosFldR.setPrefWidth(100);
        priFldR.setPrefWidth(100);

        this.add(maxLblR, 0,0);
        this.add(maxFldR, 0,1);
        this.add(arvLblR, 1,0);
        this.add(arvFldR, 1,1);
        this.add(cpuLblR, 2,0);
        this.add(cpuFldR, 2,1);
        this.add(ioLblR, 3,0);
        this.add(ioFldR, 3,1);
        this.add(prosLblR, 4,0);
        this.add(prosFldR, 4,1);
        this.add(priLblR, 5,0);
        this.add(priFldR, 5,1);
    }

    void populateProcesses(ArrayList processes){}

    String getMax() { return maxFldR.getText(); }

    String getArvRate() { return arvFldR.getText(); }

    String getCpuRate() { return cpuFldR.getText(); }

    String getIoRate() { return ioFldR.getText(); }

    String getProcessRate() { return prosFldR.getText(); }

    String getPriorityRate(){ return priFldR.getText(); }

}
