package sample.display;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sample.Controller;

class NavDisplay extends MenuBar{

    NavDisplay(Controller controller, DisplayController display){
        Menu file = new Menu("File");
        Menu main = new Menu();
        Menu process = new Menu();
        Menu processor = new Menu();

        Label mainLbl = new Label("Main Display");
        Label processLbl = new Label("Process Editor");
        Label processorLbl = new Label("Processor Editor");

        main.setGraphic(mainLbl);
        process.setGraphic(processLbl);
        processor.setGraphic(processorLbl);

        MenuItem reset = new MenuItem("New");
        MenuItem input = new MenuItem("Import from File");
        MenuItem output = new MenuItem("Save to File");
        MenuItem exit = new MenuItem("Exit");

        file.getItems().addAll(reset, input, output, exit);


        mainLbl.setOnMouseClicked(e->display.setMainPane());
        processLbl.setOnMouseClicked(e->display.setProcessPane());
        processorLbl.setOnMouseClicked(e->display.setProcessorPane());

        reset.setOnAction(e->{
            display.resetDataDisplay();
            display.resetData();
            controller.stopSimulation();
        });
        input.setOnAction(e->controller.getIOData());
        output.setOnAction(e->controller.saveDataIO());
        exit.setOnAction(e->System.exit(0));

        this.getMenus().addAll(file, main, process, processor);
    }
}
