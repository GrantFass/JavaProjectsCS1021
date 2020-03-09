/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Triangle
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Triangle purpose: creates triangles for lab 4
 *
 * @author fassg
 * @version created on 1/26/2020 at 3:42 PM
 */
public class Triangle extends Shape {
    /**
     * the base length of the triangle
     */
    protected final double base;
    /**
     * the length of the height of the triangle
     */
    protected final double height;

    /**
     * constructor for the triangle object
     * @param x the lower left corner x value of the triangle
     * @param y the lower left corner y value of the triangle
     * @param base the length of the base of the triangle
     * @param height the height of the triangle
     * @param color the color of the triangle
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public Triangle(double x, double y, double base, double height, Color color)
            throws IllegalArgumentException {
        super(x, y, color);
        this.base = checkForUnrealisticDimensions(base);
        this.height = checkForUnrealisticDimensions(height);
    }

    /**
     * a method that draws the triangle using the WinPlotterFX
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        setPenColor(plotter);
        plotter.moveTo(x, y);
        plotter.drawTo(x + base, y);
        plotter.drawTo(((base + x + x) / 2), y + height);
        //System.out.println(x + "\n" + base + "\n" + (base + x) /2);
        plotter.drawTo(x, y);
    }
}
