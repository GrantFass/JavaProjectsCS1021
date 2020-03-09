/*
 * Course: CS 1021 - 021
 * Winter 2019
 * File header contains class Measure
 * Name:       fassg
 * Created:    12/8/2019
 */
package msoe.fassg.lab02;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Measure purpose: will measure ingredients for use in Lab2 of CS 1021
 *
 * @author fassg
 * @version created on 12/8/2019 at 10:57 PM
 */
public class Measure implements Ingredient {
    /**
     * the numerator for the volume in cups of the ingredient that is measured out
     */
    private int numerator;

    /**
     * the denominator for the volume in cups of the ingredient that is measured out.
     */
    private int denominator;

    /**
     * the ingredient that is being measured
     */
    private Ingredient measuredIngredient;

    /**
     * constructor to create a measured ingredient
     * @param numerator the numerator of the volume to measure out
     * @param denominator the denominator of the volume to measure out
     * @param measuredIngredient the ingredient to measure out
     */
    public Measure(int numerator, int denominator, Ingredient measuredIngredient) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.measuredIngredient = measuredIngredient;
    }

    /**
     * constructor to create a measured ingredient
     * denominator is defaulted to one in this version
     * @param numerator the volume in cups to measure out
     * @param measuredIngredient the ingredient to measure out
     */
    public Measure(int numerator, Ingredient measuredIngredient) {
        this(numerator, 1, measuredIngredient);
    }

    /**
     * the calories of the ingredient after it is measured out
     * only gives the calories for the amount of the ingredient that is measured out
     * @return calories of the measured ingredient
     */
    public double getCalories() {
        return getCups() * getEnergyDensity();
    }

    /**
     * the volume in cups of the ingredient after it is measured
     * @return volume of the measured ingredient
     */
    public double getCups() {
        return (double) numerator / denominator;
    }

    /**
     * the name of the ingredient after it is measured
     * @return name of the measured ingredient
     */
    public String getName() {
        return formatQuantity() + " " + measuredIngredient.getName();
    }

    /**
     * the status of the ingredient after it is measured
     * status does not change from the incoming ingredient
     * @return status of the measured ingredient
     */
    public boolean isDry() {
        return measuredIngredient.isDry();
    }

    /**
     * will print out the recipe for the measured ingredient
     * will print out the recipes for all ingredients used to make the measured ingredient
     */
    public void printRecipe() {
        String equalSignLine = "====================================================";
        System.out.format("%s\n%s\n%s\nMeasured ingredient: %s\nQuantity: %s (%s Cups)" +
                        "\nEnergy: %d Calories\n\n",
                equalSignLine, getName(), equalSignLine, measuredIngredient.getName(),
                formatQuantity(), CUP_FORMAT.format(getCups()), Math.round(getCalories()));
        measuredIngredient.printRecipe();
    }

    /**
     * will format the volume of the measured ingredient to display in fractional form
     * @return fractional volume of the measured ingredient
     */
    private String formatQuantity() {
        if (denominator != 1 && getCups() > 1) {
            return numerator + "/" + denominator + " Cups";
        } else if (denominator != 1 && getCups() < 1) {
            return numerator + "/" + denominator + " Cup";
        } else if (numerator == 1) {
            return numerator + " Cup";
        }
        return numerator + " Cups";
    }

    /**
     * will calculate the energy density of the given ingredient
     * @return energy density of the ingredient
     */
    private double getEnergyDensity(){
        return measuredIngredient.getCalories() / measuredIngredient.getCups();
    }
}
