/*
 * Course: CS 1021 - 021
 * Winter 2019
 * File header contains class Ingredient
 * Name:       fassg
 * Created:    12/8/2019
 */
package msoe.fassg.lab02;

import java.text.DecimalFormat;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Ingredient purpose: to create an interface for all ingredients
 *
 * @author fassg
 * @version created on 12/8/2019 at 10:56 PM
 */
public interface Ingredient {
    /**
     * Decimal Formatter to format the display output for cups.
     */
    static final DecimalFormat CUP_FORMAT = new DecimalFormat("###,###.##");

    /**
     * will return the number of calories of an ingredient
     * @return the number of calories in an ingredient in kcal
     */
    public double getCalories();

    /**
     * will return the volume of the ingredient
     * @return the volume of the ingredient in cups
     */
    public double getCups();

    /**
     * will return the name of the ingredient
     * @return the ingredients name
     */
    public String getName();

    /**
     * will return the status of an ingredient
     * will return true if the ingredient is dry
     * will return false if the ingredient is not dry
     * @return the status of an ingredient
     */
    public boolean isDry();

    /**
     * will print out the recipe for preparing an ingredient
     * preparation techniques use existing ingredients to create new ones
     */
    public void printRecipe();
}
