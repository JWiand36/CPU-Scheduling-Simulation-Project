package sample.display;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import sample.Controller;

class SideDisplay extends FlowPane{

    private Button runBtn = new Button("Run");
    private ComboBox<String> stratBox = new ComboBox<>();
    private TextField rrRunFld = new TextField("2");
    private TextField amtProsFld = new TextField("1");
    private CheckBox ranBox = new CheckBox();

    SideDisplay(Controller controller, DisplayController display){

        String[] strats = {"Shortest", "Round Robin", "First", "Priority"};

        Button reset = new Button("Reset Display");

        stratBox.setValue(strats[0]);
        stratBox.getItems().addAll(FXCollections.observableArrayList(strats));

        Label ranLbl = new Label("Random");
        Label rrRunLbl = new Label("Round Robin Time");
        Label amtProsLbl = new Label("Processors: ");

        stratBox.setPrefWidth(100);
        rrRunFld.setPrefWidth(100);
        amtProsFld.setPrefWidth(100);

        this.getChildren().addAll(stratBox, ranLbl, ranBox);
        this.getChildren().addAll(amtProsLbl, amtProsFld, rrRunLbl, rrRunFld, reset, runBtn);

        this.setPadding(new Insets(5));
        this.setMaxWidth(125);
        this.setHgap(5);
        this.setVgap(5);

        //Allows the user to run and stop the simulation using the same button
        runBtn.setOnAction(e->{
            if(runBtn.getText().equals("Run")){
                controller.runSimulation();
                runBtn.setText("Stop");
            }else{
                controller.stopSimulation();
                runBtn.setText("Run");
            }
        });

        ranBox.setOnAction(e->{
            if(ranBox.isSelected()) {
                display.displayRandom();
            }else {
                display.displayManual();
            }
        });

        reset.setOnAction(e-> display.resetDataDisplay());
    }

    Button getRunBtn(){ return runBtn; }

    String getStrategy(){ return stratBox.getSelectionModel().getSelectedItem();}

    String getProcessors(){ return amtProsFld.getText(); }

    boolean getRandom(){ return ranBox.isSelected(); }

    String getProcessorRunTime(){ return rrRunFld.getText(); }
}
