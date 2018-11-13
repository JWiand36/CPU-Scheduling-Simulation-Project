package sample.display;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import sample.Controller;

import java.util.ArrayList;

class ProcessorEditorDisplay extends GridPane{

    private ArrayList<TextField> runTimes = new ArrayList<>();
    private ArrayList<TextField> contexts = new ArrayList<>();

    private TextField maxFld = new TextField("1");

    private Label infoLbl = new Label("Press Enter above with desired number");

    private Label amtProsLbl = new Label("Process Amt");
    private Label runLbl = new Label("Run Time");
    private Label contextLbl = new Label("Context Switch");

    private DisplayController display;


    ProcessorEditorDisplay (DisplayController display){

        this.display = display;

        ColumnConstraints[] cols = new ColumnConstraints[7];

        display();

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

                if(max > 10){
                    max = 10;
                    maxFld.setText(max+"");
                }

                if(max < 0){
                    max = 0;
                    maxFld.setText(max+"");
                }

                while(runTimes.size() < max){
                    runTimes.add(new TextField());
                    contexts.add(new TextField());
                }

                while(runTimes.size() > max){
                    runTimes.remove(runTimes.size()-1);
                    contexts.remove(contexts.size()-1);
                }

                display();
            }catch (NumberFormatException n){ display.displayError();}
        });

        infoLbl.setWrapText(true);
        infoLbl.setMaxWidth(100);
        maxFld.setPrefWidth(100);
    }

    void display(){
        this.getChildren().clear();

        this.add(amtProsLbl, 0,0);
        this.add(maxFld, 0,1);
        this.add(infoLbl,0,2,1,4);
        this.add(runLbl, 1, 0);
        this.add(contextLbl, 2, 0);

        for(int i = 0; i < runTimes.size(); i++){
            this.add(runTimes.get(i),1,i+1);
            this.add(contexts.get(i),2,i+1);
        }
    }

    String getMaxProcessors(){ return maxFld.getText(); }

    ArrayList<TextField> getRunTimes() { return runTimes; }

    ArrayList<TextField> getContexts() { return contexts; }
}
