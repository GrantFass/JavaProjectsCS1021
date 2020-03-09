/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Sudoku
 * Name:       fassg
 * Created:    1/13/2020
 */
package msoe.fassg.javaFXDemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

import java.util.ArrayList;


/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Sudoku purpose: Create a GUI for a sudoku game
 *
 * @author fassg
 * @version created on 1/13/2020 at 9:10 AM
 */
public class Sudoku extends Application {
    /**
     * the list of cells for the sudoku grid
     */
    private ArrayList<ArrayList<TextField>> cells;
    private GridPane gridPane = new GridPane();
    private Button check = new Button();
    private Label output = new Label();
    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;
    private static final int OFFSET = 5;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeGridPane();
        initializeButtonAndLabel();
        HBox hBox = new HBox(OFFSET, check, output);
        VBox vBox = new VBox(OFFSET, gridPane, hBox);
        //set up window
        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * This method adds all the cell TextFields to the Pane
     */
    private void initializeGridPane() {
        final int numberOfCellsHigh = 9;
        final int numberOfCellsWide = 9;
        final double width = WIDTH / 9;
        final double height = 15;
        cells = new ArrayList<>();

        //initialize the two dimensional ArrayList
        for(int i = 0; i < numberOfCellsHigh; i++) {
            cells.add(new ArrayList<>());
        }

        //create cells
        for(int i = 0; i < numberOfCellsHigh; i++) {     // yPosition
            for(int j = 0; j < numberOfCellsWide; j++) { // xPosition
                cells.get(i).add(new TextField());
                cells.get(i).get(j).setPrefSize(width, height);
                gridPane.add(cells.get(i).get(j), j, i);
            }
        }
    }

    private void initializeButtonAndLabel(){
        final double width = (WIDTH - OFFSET) / 2;
        final double height = 15;
        check.setText("Check");
        check.setPrefSize(width, height);
        check.setAlignment(Pos.CENTER);
        output.setText("Output");
        output.setAlignment(Pos.CENTER);
        output.setPrefSize(width, height);
    }
}
