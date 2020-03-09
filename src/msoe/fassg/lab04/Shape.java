/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Shape
 * Name:       fassg
 * Created:    1/8/2020
 */
package msoe.fassg.lab04;

import edu.msoe.winplotterfx.WinPlotterFX;

import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Shape purpose: abstract class of shapes for lab 4
 *
 * @author fassg
 * @version created on 1/8/2020 at 5:12 PM
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
     */
    public Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
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

}
