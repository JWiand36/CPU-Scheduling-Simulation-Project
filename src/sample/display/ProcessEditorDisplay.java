package sample.display;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import sample.Controller;
import sample.module.SimProcess;

import java.util.ArrayList;

class ProcessEditorDisplay extends GridPane{

    private ArrayList<TextField> names = new ArrayList<>();
    private ArrayList<TextField> arrivals = new ArrayList<>();
    private ArrayList<TextField> cpus = new ArrayList<>();
    private ArrayList<TextField> ios = new ArrayList<>();
    private ArrayList<TextField> processTimes = new ArrayList<>();
    private ArrayList<TextField> priorities = new ArrayList<>();

    private TextField maxFld = new TextField("5");
    private TextField arvFldR = new TextField("5");
    private TextField cpuFldR = new TextField("5");
    private TextField ioFldR = new TextField("5");
    private TextField prosFldR = new TextField("5");
    private TextField priFldR = new TextField("5");

    private Label infoLbl = new Label("Press Enter above with desired number");
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

    private DisplayController display;


    ProcessEditorDisplay (DisplayController display){

        this.display = display;

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

        maxFld.setOnAction(e->{

            try{
                int max = Integer.parseInt(maxFld.getText());

                while(names.size() < max){
                    names.add(new TextField());
                    arrivals.add(new TextField());
                    cpus.add(new TextField());
                    ios.add(new TextField());
                    processTimes.add(new TextField());
                    priorities.add(new TextField());
                }

                while(names.size() > max){
                    names.remove(names.size()-1);
                    arrivals.remove(arrivals.size()-1);
                    cpus.remove(cpus.size()-1);
                    ios.remove(ios.size()-1);
                    processTimes.remove(processTimes.size()-1);
                    priorities.remove(priorities.size()-1);
                }

                if(!display.getRandom())
                    displayManual();
                else
                    displayRandom();
            }catch (NumberFormatException n){ display.displayError();}
        });

        infoLbl.setWrapText(true);
        infoLbl.setMaxWidth(100);
        maxFld.setPrefWidth(100);
        arvFldR.setPrefWidth(100);
        cpuFldR.setPrefWidth(100);
        ioFldR.setPrefWidth(100);
        prosFldR.setPrefWidth(100);
        priFldR.setPrefWidth(100);
    }

    void displayManual(){
        this.getChildren().clear();

        this.add(amtProsLbl, 0,0);
        this.add(maxFld, 0,1);
        this.add(infoLbl,0,2,1,4);
        this.add(nameLbl, 1, 0);
        this.add(arrivalLbl, 2, 0);
        this.add(cpuLbl, 3, 0);
        this.add(ioLbl, 4, 0);
        this.add(processingLbl, 5, 0);
        this.add(priorityLbl, 6, 0);

        for(int i = 0; i < names.size(); i++){
            this.add(names.get(i),1,i+1);
            this.add(arrivals.get(i),2,i+1);
            this.add(cpus.get(i),3,i+1);
            this.add(ios.get(i),4,i+1);
            this.add(processTimes.get(i),5,i+1);
            this.add(priorities.get(i),6,i+1);
        }
    }

    void displayRandom(){
        this.getChildren().clear();

        this.add(maxLblR, 0,0);
        this.add(maxFld, 0,1);
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

    void setMax(int max) { maxFld.setText(max+"");}

    void setProcesses(ArrayList<SimProcess> processes){
        names.clear();
        arrivals.clear();
        cpus.clear();
        ios.clear();
        processTimes.clear();
        priorities.clear();

        for(SimProcess process: processes){
            names.add(new TextField(process.getName()));
            arrivals.add(new TextField(process.getArrivalTime()+""));
            cpus.add(new TextField(process.getCpuTime()+""));
            ios.add(new TextField(process.getIoTime()+""));
            processTimes.add(new TextField(process.getProcessTime()+""));
            priorities.add(new TextField(process.getPriority()+""));
        }

        displayManual();

    }

    String getMax() { return maxFld.getText(); }

    String getArvRate() { return arvFldR.getText(); }

    String getCpuRate() { return cpuFldR.getText(); }

    String getIoRate() { return ioFldR.getText(); }

    String getProcessRate() { return prosFldR.getText(); }

    String getPriorityRate(){ return priFldR.getText(); }

    ArrayList<TextField> getNames() { return names; }

    ArrayList<TextField> getArrivals() { return arrivals; }

    ArrayList<TextField> getCpus() { return cpus; }

    ArrayList<TextField> getIos() { return ios; }

    ArrayList<TextField> getProcessTimes() { return processTimes; }

    ArrayList<TextField> getPriorities() { return priorities; }

    void reset(){
        names.clear();
        arrivals.clear();
        cpus.clear();
        ios.clear();
        processTimes.clear();
        priorities.clear();

        if(display.getRandom())
            displayRandom();
        else
            displayManual();

    }
}
