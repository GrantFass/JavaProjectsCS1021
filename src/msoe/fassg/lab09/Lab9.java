/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab9
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab09;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab9 purpose: the main class for Lab 9 that will start the program
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:20 AM
 */
public class Lab9 extends Application {

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lab9.fxml"));
        loader.load();
        //make sure to update the controller name in the FXML file when refactoring.
        Lab9Controller controller = loader.getController();
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
