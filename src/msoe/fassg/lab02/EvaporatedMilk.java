/*
 * Course: CS1021
 * Winter 2019-20
 * Lab 2 - Interfaces
 * Name: Dr. Yoder
 * Created: 20181204
 */
package msoe.fassg.lab02; // replace with your username.

/**
 * Driver class that illustrates making evaporated milk by baking milk.
 */
public class EvaporatedMilk {
    /** The milk to be baked */
    private static final Ingredient MILK =
            new SimpleIngredient("Milk", 103.0, 1.0, false);

    /** Ratio of volume after evaporating to before */
    public static final double DEHYDRATION_FACTOR = 0.2;

    public static void main(String[] args) {
        Ingredient milk = MILK;
        Ingredient evaporatedMilk = new BakedIngredient(milk, DEHYDRATION_FACTOR);
        evaporatedMilk.printRecipe();
    }
}
