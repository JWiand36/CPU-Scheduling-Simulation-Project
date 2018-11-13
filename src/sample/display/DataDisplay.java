package sample.display;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.module.SimProcess;

import java.util.ArrayList;

class DataDisplay extends GridPane {

    DataDisplay(){
        reset();

        this.setPadding(new Insets(5));
        this.setPrefWidth(150);
        this.setHgap(5);
        this.setVgap(5);
    }

    //When a simulation has finished all the processes will display their information
    void displayProcessData(ArrayList<SimProcess> processes){
        for(int i = 0; i < processes.size(); i++){
            this.add(new Text(processes.get(i).toString()),0,i+1);
        }
    }

    //This resets the board when the user clicks the reset button
    void reset(){
        this.getChildren().clear();
    }
}
