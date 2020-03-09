/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab10
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab10 purpose: the main class for Lab10 that will start the program
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:20 AM
 */
public class Lab10 extends Application {
    /**
     * main method of the program
     * @param args ignored
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * the start method to start the program
     * @param stage prebuilt in stage to use as the main stage
     * @throws IOException exception when building loaders
     */
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Next items to work on...
         add keyboard shortcuts
         Play sound based on color of pixel clicked
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lab10.fxml"));
        loader.load();
        //make sure to update the controller name in the FXML file when refactoring.
        Lab10Controller controller = loader.getController();
        controller.setDefaultValues(stage);
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("Image Manipulator");
        stage.show();


        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("KernelUI.fxml"));
        secondaryLoader.load();
        KernelController controllerWindow2 = secondaryLoader.getController();
        secondaryStage.setScene(new Scene(secondaryLoader.getRoot()));
        secondaryStage.setTitle("Filter Kernel");
        secondaryStage.hide();

        controller.setOtherStage(secondaryStage);
        controller.setOtherController(controllerWindow2);

        controllerWindow2.setOtherController(controller);
        controllerWindow2.setOtherStage(stage);
    }
}
