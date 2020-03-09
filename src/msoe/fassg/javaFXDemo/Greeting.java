/**
 * Greeting.java
 * Dean & Dean
 *
 * When the user presses Enter after typing something into the text box,
 * the text box value displays in the label below.
 */
package msoe.fassg.javaFXDemo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

public class Greeting extends Application {
    //global constant values
    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;

    private TextField nameBox = new TextField();
    private Label greeting = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane pane = new FlowPane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        createContents(pane);
        primaryStage.setTitle("Greetings");
        primaryStage.setScene(scene);
        primaryStage.show();
    } //end start

    private void createContents(FlowPane pane) {
        Label namePrompt = new Label("What's your name? ");

        pane.getChildren().addAll(namePrompt, nameBox, greeting);
        pane.setAlignment(Pos.CENTER);
        namePrompt.setFont(new Font(16));
        greeting.setFont(new Font(16));
        greeting.setMaxWidth(350);
        greeting.setWrapText(true);
        //register event-handling method.
        nameBox.setOnAction(this::respond);
    } // end createContents

    //provide event handling method
    private void respond(ActionEvent e) {
        String message = "Glad to meet you, " + nameBox.getText() + "!";
        nameBox.setText("");
        greeting.setText(message);
    } //end respond
} //end class Greeting
