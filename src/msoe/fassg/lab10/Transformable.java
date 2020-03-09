/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Transformable
 * Name:       fassg
 * Created:    2/11/2020
 */
package msoe.fassg.lab10;

import javafx.scene.paint.Color;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Transformable purpose: Functional interface to apply transformations
 *
 * @author fassg
 * @version created on 2/11/2020 at 4:27 PM
 */
@FunctionalInterface
public interface Transformable {
    /**
     * method to apply a transformation from one color to a new color.
     *
     * @param pixelLocationY the y location of the pixel
     * @param color          the color that the pixel should be
     * @return the color for the pixel after applying the transformation
     */
    Color apply(int pixelLocationY, Color color);
}
