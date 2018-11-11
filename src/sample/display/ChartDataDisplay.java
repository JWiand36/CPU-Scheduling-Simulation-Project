package sample.display;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

class ChartDataDisplay extends GridPane {

    private int position = 1;

    ChartDataDisplay(){
        reset();

        this.setPadding(new Insets(5));
        this.setPrefHeight(150);
        this.setHgap(5);
        this.setVgap(5);
    }

    //When a change occurs in the processor, this method displays the info
    void displayChange(int id, String name, int time, String type){

        this.add(new Text(name), position, 2 + 3 * id);
        this.add(new Text(type), position, 3 + 3 * id);
        this.add(new Text(time+""), position,  4 + 3 * id);

        position++;
    }

    //This resets the board when the user clicks the reset button
    void reset(){
        this.getChildren().clear();
        this.add(new Text("Processor Activity"),0,0);
        position = 1;
    }
}
