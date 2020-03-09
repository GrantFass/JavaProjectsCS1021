/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Circle
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Circle purpose: creates circles for lab 7
 *
 * @author fassg
 * @version created on 1/26/2020 at 3:42 PM
 */
public class Circle extends Shape {
    /**
     * the length of the radius of the circle
     */
    private final double radius;

    /**
     * constructor for the Circle class
     *
     * @param x      the x location of the center of the circle
     * @param y      the y location of the center of the circle
     * @param radius the length of the radius of the circle
     * @param color  the color of the circle
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public Circle(double x, double y, double radius, Color color) throws IllegalArgumentException {
        super(x, y, color);
        this.radius = checkForUnrealisticDimensions(radius);
    }

    /**
     * constructor for the circle class
     *
     * @param x      the lower left corner x value of the circle
     * @param y      the lower left corner y value of the circle
     * @param width  the width of the circle
     * @param height the height of the circle
     * @param color  the color of the circle
     * @throws IllegalArgumentException if
     *  non-zero value for dimension was entered
     */
    public Circle(double x, double y, double width, double height, Color color)
            throws IllegalArgumentException {
        //assume that width = height
        this((x + x + width) / 2, (y + y + width) / 2, width / 2, color);
    }

    /**
     * a method that draws the circle using the WinPlotterFX
     *
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        setPenColor(plotter);
        //point p on the unit circle has values P(cos x, sin y)
        //p can be on any circle if multiplied by radius
        double a = Math.cos(Math.toRadians(0)) * radius;
        double b = Math.sin(Math.toRadians(0)) * radius;
        plotter.moveTo(x + a, y + b);
        final int circleDistanceDegrees = 360;
        for (double theta = 1; theta <= circleDistanceDegrees; theta++) {
            a = Math.cos(Math.toRadians(theta)) * radius;
            b = Math.sin(Math.toRadians(theta)) * radius;
            plotter.drawTo(x + a, y + b);
        }
    }
}
