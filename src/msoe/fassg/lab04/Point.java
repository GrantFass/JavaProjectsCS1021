/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Point
 * Name:       fassg
 * Created:    1/8/2020
 */
package msoe.fassg.lab04;

import edu.msoe.winplotterfx.WinPlotterFX;

import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Point purpose: creates points for lab 4
 *
 * @author fassg
 * @version created on 1/8/2020 at 5:12 PM
 */
public class Point extends Shape {
    /**
     * constructor for the point class
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param color the color of the point
     */
    public Point(double x, double y, Color color) {
        super(x, y, color);
    }

    /**
     * draws a point
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        setPenColor(plotter);
        plotter.drawPoint(x, y);
    }
}
