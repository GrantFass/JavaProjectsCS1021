/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab6
 * Name:       fassg
 * Created:    1/20/2020
 */
package msoe.fassg.lab06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab6 purpose: the main program for lab6 which is about error handling
 *
 * @author fassg
 * @version created on 1/20/2020 at 8:47 PM
 */
public class Lab6 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lab6.fxml"));
        loader.load();
        Lab6Controller controller = loader.getController();
        controller.setDefaultValues();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("Website Tester");
        stage.show();
    }
}
