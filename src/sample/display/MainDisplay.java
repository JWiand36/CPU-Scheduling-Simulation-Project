package sample.display;


import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.Controller;

class MainDisplay extends Pane {

    MainDisplay(Controller controller){
        reset();
    }

    void reset(){
        this.getChildren().clear();

        Text busy = new Text("Busy");
        Text ready = new Text("Ready");
        Text context = new Text("Context Switch");
        Text processor = new Text("Processors");
        Text terminated = new Text("Terminated");

        busy.setX(55);
        busy.setY(20);
        ready.setX(200);
        ready.setY(20);
        context.setX(325);
        context.setY(20);
        processor.setX(420);
        processor.setY(20);
        terminated.setX(545);
        terminated.setY(20);

        this.getChildren().addAll(busy, ready, context, processor, terminated);

    }
}
