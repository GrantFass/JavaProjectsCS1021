/*
 * Course: CS 1021 - 021
 * Winter 2019
 * File header contains class BakedIngredient
 * Name:       fassg
 * Created:    12/8/2019
 */
package msoe.fassg.lab02;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * BakedIngredient purpose: will bake ingredients for use in Lab2 of CS 1021
 *
 * @author fassg
 * @version created on 12/8/2019 at 10:57 PM
 */
public class BakedIngredient implements Ingredient {
    /**
     * The ingredient to be baked
     */
    private Ingredient bakedIngredient;

    /**
     * the expansion factor of the ingredient when it is baked
     * used to calculate the new volume of the ingredient
     */
    private double expansionFactor;

    /**
     * the constructor for BakedIngredient
     * @param ingredient the ingredient to be baked
     * @param expansionFactor the expansion factor of the ingredient when it is baked
     */
    public BakedIngredient(Ingredient ingredient, double expansionFactor) {
        bakedIngredient = ingredient;
        this.expansionFactor = expansionFactor;
    }

    /**
     * will get the new calories of the ingredient when it is baked
     * @return calories of the baked ingredient
     */
    public double getCalories() {
        return bakedIngredient.getCalories();
    }

    /**
     * will get the new volume of the ingredient when it is baked.
     * the volume will increase by the expansion factor.
     * @return volume of the baked ingredient
     */
    public double getCups() {
        return bakedIngredient.getCups() * expansionFactor;
    }

    /**
     * will return the new name of the ingredient when it is baked
     * @return name of the baked ingredient
     */
    public String getName() {
        return "Baked " + bakedIngredient.getName();
    }

    /**
     * will return the status of all baked ingredients
     * will always return true because all baked ingredients become dry
     * @return true
     */
    public boolean isDry() {
        return true;
    }

    /**
     * will print out the recipe for the baked ingredient.
     * will print out the recipes for all ingredients needed to make the baked ingredient.
     */
    public void printRecipe() {
        String equalSignLine = "====================================================";
        System.out.format("%s\n%s\n%s\nIngredient to be baked: %s\n" +
                        "Cups: %s Cups\nEnergy: %d Calories\n\n",
                equalSignLine, getName(), equalSignLine, bakedIngredient.getName(),
                CUP_FORMAT.format(getCups()), (int)getCalories());
        bakedIngredient.printRecipe();
    }
}
