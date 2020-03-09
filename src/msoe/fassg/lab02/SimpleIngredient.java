/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class SimpleIngredient
 * Name:       fassg
 * Created:    12/8/2019
 */
package msoe.fassg.lab02;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * SimpleIngredient purpose: creates simple ingredients with set parameters
 *
 * @author fassg
 * @version created on 12/8/2019 at 10:56 PM
 */
public class SimpleIngredient implements Ingredient {
    /**
     * the number of calories of the ingredient
     */
    private double calories;

    /**
     * the volume of the ingredient in cups
     */
    private double cups;

    /**
     * the status of the ingredient,
     * true if dry, false if not
     */
    private boolean isDry;

    /**
     * the name of the ingredient
     */
    private String name;

    /**
     * the constructor for the SimpleIngredient class
     * creates SimpleIngredients
     * @param name the name of the ingredient to create
     * @param cups the volume of the ingredient to create
     * @param calories the energy of the ingredient to create
     * @param isDry the state of the ingredient to create
     */
    public SimpleIngredient(String name, double calories, double cups, boolean isDry) {
        this.name = name;
        this.cups = cups;
        this.calories = calories;
        this.isDry = isDry;
    }

    /**
     * overloaded constructor for the SimpleIngredient class
     * exists to conform with the updated Kitchen.java from 12/11/19
     * creates SimpleIngredients
     * @param name the name of the ingredient to create
     * @param cups the volume of the ingredient to create
     * @param calories the energy of the ingredient to create
     * @param isDry the state of the ingredient to create
     */
    public SimpleIngredient(double calories, double cups, boolean isDry, String name) {
        this.name = name;
        this.cups = cups;
        this.calories = calories;
        this.isDry = isDry;
    }

    /**
     * will return the amount of calories of the ingredient
     * @return the calories of the ingredient in kcal
     */
    public double getCalories() {
        return calories;
    }

    /**
     * will return the volume of the ingredient
     * @return the volume of the ingredient in cups
     */
    public double getCups() {
        return cups;
    }

    /**
     * will return the name of the ingredient
     * @return the name of the ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * will return the status of the ingredient
     * will return true if the ingredient is dry
     * will return fals if the ingredient is not
     * @return the status of the ingredient
     */
    public boolean isDry() {
        return isDry;
    }

    /**
     * will print out the recipe to make the simple ingredient
     */
    public void printRecipe() {
        String equalSignLine = "====================================================";
        System.out.format("%s\n%s\n%s\nCups: %s Cups\nEnergy: %d Calories\n\n",
                equalSignLine, getName(), equalSignLine, CUP_FORMAT.format(getCups()),
                (int)(getCalories()));
    }
}
