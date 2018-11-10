package sample.display;

import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.module.SimProcess;

import java.util.ArrayList;

class DataDisplay extends GridPane {

    private int position = 2;
    private Text time = new Text("Time: ");

    DataDisplay(){
        this.add(time,0,0);
        this.add(new Text("Processor Activity"),0,1);

        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);
    }

    //When a change occurs in the processor, this method displays the info
    void displayChange(int id, String name, int time, String type){

        this.add(new Text(name), position, 1 + 3 * id);
        this.add(new Text(type), position, 2 + 3 * id);
        this.add(new Text(time+""), position,  3 + 3 * id);

        position++;
    }

    //When a simulation has finished all the processes will display their information
    void displayProcessData(ArrayList<SimProcess> processes){
        for(int i = 0; i < processes.size(); i++){
            this.add(new Text(processes.get(i).toString()),i*2,4);
        }
    }

    //This resets the board when the user clicks the reset button
    void reset(){
        this.getChildren().clear();
        time.setText("Time: ");
        this.add(time,0,0);
        this.add(new Text("Processor Activity"),0,1);
        position = 2;
    }

    void updateTime(int time){ this.time.setText("Time: " + time);}

}
