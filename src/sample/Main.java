package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane border = new BorderPane();

        controller = new Controller(border);

        primaryStage.setTitle("CPU Scheduler Sim");
        primaryStage.setScene(new Scene(border, 800, 475));
        primaryStage.show();
    }

    @Override
    public void stop(){
        controller.pauseSimulation();
    }

    public static void main(String[] args) { launch(args); }
}
