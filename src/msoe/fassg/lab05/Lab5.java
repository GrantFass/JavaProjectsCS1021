/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab5
 * Name:       fassg
 * Created:    1/12/2020
 */
package msoe.fassg.lab05;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab5 purpose: The primary class for lab 5. This class controls most of the GUI implementation.
 *
 * @author fassg
 * @version created on 1/12/2020 at 1:32 PM
 */
public class Lab5 extends Application {
    //global vars for constants
    /**
     * the height of the window
     */
    private static final int WINDOW_HEIGHT = 750;
    /**
     * the width of the window
     */
    private static final int WINDOW_WIDTH = 750;
    /**
     * the amount of spacing between each object in a HBox
     */
    private static final int H_BOX_SPACING = 10;
    /**
     * the amount of spacing between each object in a VBox
     */
    private static final int V_BOX_SPACING = 5;
    /**
     * the amount of padding added around the VBox and edge of the window
     */
    private static final int PADDING = 10;
    /**
     * the width of buttons
     */
    private static final int BUTTON_WIDTH = (WINDOW_WIDTH - (H_BOX_SPACING / 2) -
            (PADDING * 2)) / 2;
    /**
     * the width of labels
     */
    private static final int LABEL_WIDTH = (WINDOW_WIDTH - (H_BOX_SPACING / 2) - (PADDING * 2)) / 2;
    /**
     * the height of buttons
     */
    private static final int BUTTON_HEIGHT = WINDOW_HEIGHT / 25;
    /**
     * the height of labels
     */
    private static final int LABEL_HEIGHT = WINDOW_HEIGHT / 25;
    /**
     * the height of the life grid to generate
     */
    private static final int GAME_HEIGHT = WINDOW_HEIGHT - (BUTTON_HEIGHT * 2) -
            LABEL_HEIGHT - (V_BOX_SPACING * 3);
    /**
     * the width of the life grid to generate
     */
    private static final int GAME_WIDTH = WINDOW_WIDTH - (PADDING * 2);
    /**
     * The amount of time between iterations in the animation
     */
    private static final Duration ITERATE_FREQUENCY = Duration.seconds(1);
    /**
     * the button to randomize the entire life grid
     */
    private Button randomize = new Button();
    /**
     * the button to iterate once in the life grid
     */
    private Button iterate = new Button();
    /**
     * the button to start the timeline animation
     */
    private Button start = new Button();
    /**
     * the button to stop the animation
     */
    private Button stop = new Button();
    /**
     * label to display how many cells are alive on the board
     */
    private Label aliveCells = new Label();
    /**
     * label to display how many cells are dead on the board
     */
    private Label deadCells = new Label();
    /**
     * the pane that the life grid is created in
     */
    private FlowPane gamePane = new FlowPane();
    /**
     * the lifeGrid displayed to the user
     */
    private LifeGrid lifeGrid = new LifeGrid(gamePane, GAME_WIDTH, GAME_HEIGHT);
    /**
     * the timeline to be used to automatically iterate on a set interval
     */
    private Timeline timeline = new Timeline();


    /**
     * Java main for when running without JavaFX launcher
     * @param args ignored
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //randomize and iterate buttons
        randomize.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        randomize.setText("Randomize");
        iterate.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        iterate.setText("Iterate");
        HBox buttonHBox = new HBox(H_BOX_SPACING, randomize, iterate);
        buttonHBox.setPrefSize(WINDOW_WIDTH, BUTTON_HEIGHT);
        buttonHBox.setAlignment(Pos.BASELINE_CENTER);
        //labels
        aliveCells.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        deadCells.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        HBox detailHBox = new HBox(H_BOX_SPACING, aliveCells, deadCells);
        detailHBox.setPrefSize(WINDOW_WIDTH, LABEL_HEIGHT);
        detailHBox.setAlignment(Pos.BASELINE_CENTER);
        //stop and start buttons
        start.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        start.setText("Start");
        stop.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        stop.setText("Stop");
        HBox buttonHBox2 = new HBox(H_BOX_SPACING, start, stop);
        buttonHBox2.setPrefSize(WINDOW_WIDTH, BUTTON_HEIGHT);
        buttonHBox2.setAlignment(Pos.BASELINE_CENTER);
        //game pane
        lifeGrid.randomize();
        updateLabels();
        //put button, label, and game into VBox
        VBox vBox = new VBox(V_BOX_SPACING, buttonHBox, detailHBox, buttonHBox2, gamePane);
        vBox.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        vBox.setPadding(new Insets(PADDING));
        Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
        randomize.setOnAction(e -> {
            lifeGrid.randomize();
            updateLabels();
        });
        iterate.setOnAction(e -> {
            lifeGrid.iterate();
            updateLabels();
        });
        start.setOnAction(this::timelapse);
        stop.setOnAction(this::timelapse);
        timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                lifeGrid.iterate();
                                updateLabels();
                            }
                        }
                ),
                new KeyFrame(
                        ITERATE_FREQUENCY
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        gamePane.setOnMouseClicked(e -> {
            System.out.println("["+e.getX()+", "+e.getY()+"]");
            System.out.println(lifeGrid.click(e.getX(), e.getY()));
        });
        //add the scene to the stage
        primaryStage.setTitle("The Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    } //end start

    /**
     * method to update the number of alive cells and the number of dead cells in the display labels
     */
    private void updateLabels(){
        int[] aliveAndDeadCells = lifeGrid.cellsAliveAndDead();
        aliveCells.setText("Alive Cells: " + aliveAndDeadCells[0]);
        deadCells.setText("Dead Cells: " + aliveAndDeadCells[1]);
    }

    /**
     * method to control the timelapse iteration
     * if the start button is clicked the grid will iterate once per-second
     * if the stop button is clicked iteration will stop
     * @param e button clicked
     */
    private void timelapse(ActionEvent e){
        if(e.getSource() == start){
            timeline.play();
        } else {
            timeline.pause();
        }
    }
}
