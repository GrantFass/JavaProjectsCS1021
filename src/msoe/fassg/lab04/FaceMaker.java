/*
 * Course: CS1021 - 021
 * Winter 2019
 * Lab 4 - Inheritance with Shapes
 * Name: FassG
 * Created: 1/9/2020
 */
package msoe.fassg.lab04;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * This class draws a face out of a bunch of rectangles.
 * @author taylor
 * @version 20191216
 */
public class FaceMaker extends Application {
    /**
     * the dimensions of the window created
     */
    public static final int WINDOW_SIZE = 800;
    /**
     * the number of pixels between each grid-line
     */
    public static final int GRID_INCREMENT = WINDOW_SIZE/10;
    /**
     * the size of the head to be drawn
     */
    public static final int HEAD_SIZE = 700;
    /**
     * the users menu selection for which shapes to use to draw the face
     */
    private static int userChoice;

    private static String menuOutput() {
        return String.format("What shape would you like to use to draw the face?\n" +
                "1. Rectangle\n" +
                "2. Circle\n" +
                "3. Triangle\n" +
                "4. Labeled Rectangle\n" +
                "5. Labeled Triangle\n" +
                "6. Random");
    }

    /**
     * Launches the JavaFX application
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(menuOutput());
        userChoice = Integer.parseInt(in.nextLine());
        launch(args);
    }

    /**
     * Use the Shape class and its descendants to draw a face.
     * @param stage Default stage given to a JavaFX program. Unused.
     */
    @Override
    public void start(Stage stage) {
        WinPlotterFX plotter = new WinPlotterFX();
        plotter.setWindowTitle("Face Maker");
        plotter.setWindowSize(WINDOW_SIZE, WINDOW_SIZE);
        plotter.setBackgroundColor(Color.BLACK.getRed(), Color.BLACK.getGreen(),
                Color.BLACK.getBlue());
        final boolean showGrid = true;
        plotter.setGrid(showGrid, GRID_INCREMENT, GRID_INCREMENT, Color.GRAY);
        Shape head = createHead();
        Shape leftEye = createLeftEye();
        Shape rightEye = createRightEye();
        Shape nose = createNose();
        Shape mouth = createMouth();

        head.draw(plotter);
        leftEye.draw(plotter);
        rightEye.draw(plotter);
        nose.draw(plotter);
        mouth.draw(plotter);

        plotter.showPlotter();
    }

    /**
     * method used to select which shape to print out based on user input
     * @param x the lower left x coordinate of the shape
     * @param y the lower left y coordinate of the shape
     * @param width the width of the shape
     * @param height the height of the shape
     * @param color the color of the shape
     * @param name the name of the shape
     * @return a new shape with the specified values
     */
    private Shape selectShape(double x, double y, double width,
                              double height, Color color, String name) {
        final int rectangleSelect = 1;
        final int circleSelect = 2;
        final int triangleSelect = 3;
        final int labeledRectangleSelect = 4;
        final int labeledTriangleSelect = 5;
        switch (userChoice) {
            case rectangleSelect:
                //Rectangle
                return new Rectangle(x, y, width, height, color);
            case circleSelect:
                //Circle
                return new Circle(x, y, width, height, color);
            case triangleSelect:
                //Triangle
                return new Triangle(x, y, width, height, color);
            case labeledRectangleSelect:
                //Labeled Rectangle
                return new LabeledRectangle(x, y, width,
                        height, color, name);
            case labeledTriangleSelect:
                //Labeled Triangle
                return new LabeledTriangle(x, y, width,
                        height, color, name);
            default:
                //random
                int choice = (int) (Math.random() * labeledTriangleSelect) + 1;
                switch (choice) {
                    case rectangleSelect:
                        //Rectangle
                        return new Rectangle(x, y, width, height, color);
                    case circleSelect:
                        //Circle
                        return new Circle(x, y, width, height, color);
                    case triangleSelect:
                        //Triangle
                        return new Triangle(x, y, width, height, color);
                    case labeledRectangleSelect:
                        //Labeled Rectangle
                        return new LabeledRectangle(x, y, width,
                                height, color, name);
                    case labeledTriangleSelect:
                        //Labeled Triangle
                        return new LabeledTriangle(x, y, width,
                                height, color, name);
                }
        }
        return new Rectangle(x, y, width, height, color);
    }

    /**
     * Creates and returns a shape representing the head
     * @return shape representing the head
     */
    private Shape createHead() {
        final int xLeft = (WINDOW_SIZE-HEAD_SIZE)/2;
        final int yBottom = (WINDOW_SIZE-HEAD_SIZE)/2;
        return selectShape(xLeft, yBottom, HEAD_SIZE, HEAD_SIZE, Color.WHITE, "head");
    }

    /**
     * Creates and returns a shape representing the right eye
     * @return shape representing the right eye
     */
    private Shape createRightEye() {
        final double scaleFactor = 0.15;
        final double size = scaleFactor*HEAD_SIZE;
        final double yBottom = WINDOW_SIZE/2 + size*3/2;
        final double xLeft = WINDOW_SIZE/2 + size;
        return selectShape(xLeft, yBottom, size, size, Color.WHITE, "right eye");
    }

    /**
     * Creates and returns a shape representing the left eye
     * @return shape representing the left eye
     */
    private Shape createLeftEye() {
        final double scaleFactor = 0.15;
        final double size = scaleFactor*HEAD_SIZE;
        final double yBottom = WINDOW_SIZE/2 + size*3/2;
        final double xLeft = WINDOW_SIZE/2 - size*2;
        return selectShape(xLeft, yBottom, size, size, Color.WHITE, "left eye");
    }

    /**
     * Creates and returns a shape representing the nose
     * @return shape representing the nose
     */
    private Shape createNose() {
        final double scaleFactor = 0.2;
        final double size = scaleFactor*HEAD_SIZE;
        final double xLeft = WINDOW_SIZE/2 - size/2;
        final double yBottom = (WINDOW_SIZE)/2;
        return selectShape(xLeft, yBottom, size, size, Color.WHITE, "nose");
    }

    /**
     * Creates and returns a shape representing the mouth
     * @return shape representing the mouth
     */
    private Shape createMouth() {
        final double scaleFactor = 0.3;
        final double size = scaleFactor*HEAD_SIZE;
        final double xLeft = WINDOW_SIZE/2 - size/2;
        final double yBottom = (WINDOW_SIZE)/2 - size*3/2;
        return selectShape(xLeft, yBottom, size, size, Color.WHITE, "mouth");
    }

}
