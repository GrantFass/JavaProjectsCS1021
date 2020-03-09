/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab8
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab8 purpose: the main class for Lab 8 that will start the program
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:20 AM
 */
public class Lab8 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lab8.fxml"));
        loader.load();
        Lab8Controller controller = loader.getController();
        controller.setDefaultValues(stage);
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("Image Manipulator");
        stage.show();
    }
}
