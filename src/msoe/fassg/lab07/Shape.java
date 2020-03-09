/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Shape
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Shape purpose: abstract class of shapes for lab 7
 *
 * @author fassg
 * @version created on 1/26/2020 at 3:42 PM
 */
public abstract class Shape {
    /**
     * the color of the shape
     */
    private Color color;
    /**
     * the x coordinate value for the shape
     */
    protected final double x;
    /**
     * the y coordinate value for the shape
     */
    protected final double y;

    /**
     * the constructor for the Shape class
     * @param x the x coordinate value for the shape
     * @param y the y coordinate value for the shape
     * @param color the color of the shape
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public Shape(double x, double y, Color color) throws IllegalArgumentException {
        this.x = checkForUnrealisticDimensions(x);
        this.y = checkForUnrealisticDimensions(y);
        setColor(color);
    }

    /**
     * the draw method to be implemented within each child class
     * @param plotter plotter to be used to draw the shape
     */
    public abstract void draw(WinPlotterFX plotter);

    /**
     * sets the pen color that the shape is printed with
     * @param plotter the plotter to be used to draw the shape
     */
    public void setPenColor(WinPlotterFX plotter){
        plotter.setPenColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * will set the color of the the shape to a specified color
     * @param color the color to set the shape to
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Class that checks to see if a dimension is less than 0.
     * @param dimension the dimension to be checked
     * @return dimension to be checked for ease of use
     * @throws IllegalArgumentException the exception to be thrown
     */
    protected static double checkForUnrealisticDimensions(double dimension)
            throws IllegalArgumentException {
        if (dimension < 0) {
            throw new IllegalArgumentException("Dimension entered had a non-positive value!");
        }
        return dimension;
    }
}
