/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Mix
 * Name:       fassg
 * Created:    12/8/2019
 */
package msoe.fassg.lab02;

import java.util.ArrayList;
import java.util.List;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Mix purpose: will mix ingredients for use in Lab2 of CS 1021
 *
 * @author fassg
 * @version created on 12/8/2019 at 10:57 PM
 */
public class Mix implements Ingredient {
    /**
     * the list of all ingredients used to make the final ingredient
     */
    private List<Ingredient> ingredients;

    /**
     * the name of the final ingredient
     */
    private String name;

    /**
     * Constructor
     * @param name name of the final product
     */
    public Mix(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
    }

    /**
     * will add a new ingredient to the list of required ingredients to make the final product
     * @param ingredient the ingredient to be added
     */
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    /**
     * sums the calories of all of the ingredients used to make the final product
     * @return the calories of the final product
     */
    public double getCalories() {
        double sum = 0;
        for(Ingredient x:ingredients) {
            sum += x.getCalories();
        }
        return Math.round(sum);
    }

    /**
     * sums the volumes of all of the ingredients used to make the final product
     * @return the volume of the final product
     */
    public double getCups() {
        double sum = 0;
        for(Ingredient x:ingredients) {
            sum += x.getCups();
        }
        return sum;
    }

    /**
     * will return the name of the final product
     * @return name of the final product
     */
    public String getName() {
        return name;
    }

    /**
     * will check if there is a dry ingredient in the list of ingredients
     * @return true if there is a dry ingredient, false if otherwise
     */
    private boolean hasDryIngredient() {
        boolean hasDry = false;
        for(Ingredient x:ingredients) {
            if(x.isDry()) {
                return true;
            }
        }
        return false;
    }

    /**
     * will check if there is a wet ingredient in the list of ingredients
     * @return true if there is a wet ingredient, false if otherwise
     */
    private boolean hasWetIngredient() {
        boolean hasDry = false;
        for(Ingredient x:ingredients) {
            if(!x.isDry()) {
                return true;
            }
        }
        return false;
    }

    /**
     * will return the status of the final product
     * @return false if the ingredient list contains a wet ingredient, true if otherwise
     */
    public boolean isDry() {
        if(!hasWetIngredient()){
            return true;
        }
        return false;
    }

    /**
     * will print the recipe of the final mixture
     * will print the recipes of all ingredients required to make the final mixture
     */
    public void printRecipe() {
        String equalSignLine = "====================================================";
        System.out.format("%s\n%s\n%s\nDry Ingredients:\n",
                equalSignLine, getName(), equalSignLine);
        if(!hasDryIngredient()){
            System.out.println("  None\n");
        } else {
            for(Ingredient x:ingredients) {
                if(x.isDry()) {
                    System.out.println("  " + x.getName());
                }
            }
        }
        System.out.println("\nWet Ingredients:");
        if(!hasWetIngredient()){
            System.out.println("  None\n");
        } else {
            for(Ingredient x:ingredients) {
                if(!x.isDry()) {
                    System.out.println("  " + x.getName());
                }
            }
        }
        System.out.format("\nCups: %s Cups\nEnergy: %d Calories\n\n",
                CUP_FORMAT.format(getCups()), (int)getCalories());
        for(Ingredient x:ingredients){
            x.printRecipe();
        }
    }
}
