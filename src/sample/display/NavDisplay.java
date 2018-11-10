package sample.display;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sample.Controller;

class NavDisplay extends MenuBar{

    NavDisplay(Controller controller){
        Menu file = new Menu("File");
        Menu main = new Menu("Main Display");
        Menu process = new Menu("Process Editor");
        Menu processor = new Menu("Processor Editor");

        MenuItem reset = new MenuItem("New");
        MenuItem input = new MenuItem("Import from File");
        MenuItem output = new MenuItem("Save to File");
        MenuItem exit = new MenuItem("Exit");

        file.getItems().addAll(reset, input, output, exit);

        reset.setOnAction(e->controller.stopSimulation());
        exit.setOnAction(e->System.exit(0));

        this.getMenus().addAll(file, main, process, processor);
    }
}
