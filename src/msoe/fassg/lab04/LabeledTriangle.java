/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class LabeledTriangle
 * Name:       fassg
 * Created:    1/8/2020
 */
package msoe.fassg.lab04;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * LabeledTriangle purpose: creates labeled triangles for lab 4
 *
 * @author fassg
 * @version created on 1/8/2020 at 5:14 PM
 */
public class LabeledTriangle extends Triangle {
    /**
     * the name of the triangle
     */
    private final String name;

    /**
     * constructor for the labeled triangle object
     * @param x the lower left corner x value of the triangle
     * @param y the lower left corner y value of the triangle
     * @param base the length of the base of the triangle
     * @param height the height of the triangle
     * @param color the color of the triangle
     * @param name the text assigned to the label
     */
    public LabeledTriangle(double x, double y, double base,
                           double height, Color color, String name) {
        super(x, y, base, height, color);
        this.name = name;
    }

    /**
     * a method that draws the labeled triangle using the WinPlotterFX
     * @param plotter plotter to be used to draw the shape
     */
    public void draw(WinPlotterFX plotter) {
        super.draw(plotter);
        plotter.printAt(x, y, name);
    }
}
