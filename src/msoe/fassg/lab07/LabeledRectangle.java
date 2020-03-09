/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class LabeledRectangle
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * LabeledRectangle purpose: creates labeled rectangles for lab 7
 *
 * @author fassg
 * @version created on 1/26/2020 at 3:42 PM
 */
public class LabeledRectangle extends Rectangle {
    /**
     * the name of the rectangle object
     */
    private final String name;

    /**
     * constructor for the labeled rectangle object
     * @param x the lower left corner x value of the rectangle
     * @param y the lower left corner y value of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of the rectangle
     * @param name the text assigned to the label
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public LabeledRectangle(double x, double y, double width, double height,
                            Color color, String name) throws IllegalArgumentException {
        super(x, y, width, height, color);
        this.name = name;
    }

    /**
     * a method that draws the labeled rectangle using the WinPlotterFX
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        super.draw(plotter);
        plotter.printAt(x, y, name);
    }
}
