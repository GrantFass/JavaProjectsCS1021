/**
 * for class CS 1021
 */
package msoe.fassg.javaFXDemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

/**
 * demo for a simple JavaFX program to print a window with a label
 */
public class LeahTheLabel extends Application {
    //global vars for constants
    private static final int WIDTH = 300;
    private static final int HEIGHT = 100;

    //Java main for when running without JavaFX launcher
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create a label
        Label label = new Label("Hi! I'm Leah the label!");
        //create a scene
        Scene scene = new Scene(label, WIDTH, HEIGHT);

        //modify label attributes
        label.setAlignment(Pos.TOP_CENTER);
        label.setFont(new Font(16));
        //add the scene to the stage
        primaryStage.setTitle("Simple Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    } //end start
} //end class LeahTheLabel
