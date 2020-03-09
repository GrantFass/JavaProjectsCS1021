/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Rectangle
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Rectangle purpose: creates rectangles for lab 7
 *
 * @author fassg
 * @version created on 1/26/2020 at 3:42 PM
 */
public class Rectangle extends Shape {
    /**
     * the height of the rectangle
     */
    protected final double height;
    /**
     * the width of the rectangle
     */
    protected final double width;

    /**
     * constructor for the rectangle object
     * @param x the lower left corner x value of the rectangle
     * @param y the lower left corner y value of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of the rectangle
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public Rectangle(double x, double y, double width, double height, Color color)
            throws IllegalArgumentException {
        super(x, y, color);
        this.height = checkForUnrealisticDimensions(height);
        this.width = checkForUnrealisticDimensions(width);
    }

    /**
     * a method that draws the rectangle using the winplotter fx
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        setPenColor(plotter);
        plotter.moveTo(x, y);
        plotter.drawTo(x + width, y);
        plotter.drawTo(x + width, y + height);
        plotter.drawTo(x, y + height);
        plotter.drawTo(x, y);
    }
}
