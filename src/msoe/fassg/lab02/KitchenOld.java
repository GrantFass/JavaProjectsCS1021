/*
 * Course: CS1021
 * Winter 2018-2019
 * Lab 2 - Kitchen
 * Name: Dr. Yoder
 * Created: 5 Dec 2018
 */
package msoe.fassg.lab02; // replace with your username

import java.util.Scanner;

/**
 * The driver class: Builds recipes out of ingredients
 */
public class KitchenOld {
    private static final Ingredient BUTTER =
            new SimpleIngredient("Butter", 810.0, 0.5, false);
    private static final Ingredient CREAM =
            new SimpleIngredient("Cream", 104.0, 1/8.0, false);
    private static final Ingredient MILK =
            new SimpleIngredient("Milk", 103.0, 1.0, false);
    private static final Ingredient SUGAR =
            new SimpleIngredient("Sugar", 773.0, 1.0, true);
    private static final Ingredient FLOUR =
            new SimpleIngredient("Flour", 455.0, 1.0, true);
    /** The ratio of the dried milk to the original milk's volume */
    private static final double DEHYDRATION_FACTOR = 0.2;
    /** The multiplier indicating how the cake's volume expands while baking */
    private static final double CAKE_EXPANSION_FACTOR = 1.2;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String command = solicitCommand(in);
        while(!command.equals("quit")) {
            if(command.equals("dry milk")) {
                makeDryMilk();
            } else if(command.equals("ice cream")) {
                makeIceCream();
            } else if(command.equals("cookies")) {
                makeCookies();
            } else if(command.equals("cake")) {
                makeCake();
            } else {
                System.out.println("Unrecognized command: "+command);
            }
            command = solicitCommand(in);
        }

    }

    /**
     * Prints out the main menu and allows the user to choose an
     * option from that menu
     * @param in A scanner which should point to System.in
     * @return The keyword the user typed
     */
    private static String solicitCommand(Scanner in) {
        System.out.println("Please enter an option:");
        System.out.println(" dry milk - Make dehydrate milk");
        System.out.println(" ice cream - Make ice cream");
        System.out.println(" cookies  - Make cookies");
        System.out.println(" cake  - Make a cake");
        System.out.println(" quit - exit the program");
        return in.nextLine();
    }

    private static void makeDryMilk() {
        Ingredient milk = MILK;
        Ingredient evaporatedMilk = new BakedIngredient(milk, DEHYDRATION_FACTOR);
        evaporatedMilk.printRecipe();
    }

    private static void makeIceCream() {
        Ingredient milk = MILK;
        Ingredient sugar = SUGAR;
        Ingredient cream = CREAM;

        // https://cooking.nytimes.com/recipes/1016605-the-only-ice-cream-recipe-youll-ever-need
//        2  cups heavy cream
//        1  cup whole milk
//        2/3  cup sugar
//        1/8  teaspoon fine sea salt
//        6  large egg yolks
        Mix iceCream = new Mix("Ice Cream");
        iceCream.addIngredient(cream);
        iceCream.addIngredient(milk);
        iceCream.addIngredient(sugar);
        iceCream.printRecipe();
    }

    private static void makeCookies() {
        Ingredient butter = BUTTER;
        Ingredient flour = FLOUR;
        Ingredient sugar = SUGAR;

        Mix cookies = new Mix("Cookies");
        // https://joyfoodsunshine.com/the-most-amazing-chocolate-chip-cookies/
        cookies.addIngredient(new Measure(1, butter));
        cookies.addIngredient(new Measure(3, sugar));
        cookies.addIngredient(new Measure(3, flour));

        cookies.printRecipe();
    }

    private static void makeCake() {
        Ingredient butter = BUTTER;
        Ingredient flour = FLOUR;
        Ingredient sugar = SUGAR;
        Ingredient milk = MILK;

        // https://www.allrecipes.com/recipe/17481/simple-white-cake/
        Mix batter = new Mix("Batter");
        batter.addIngredient(new Measure(1, sugar));
        batter.addIngredient(new Measure(1, 2, butter));
        batter.addIngredient(new Measure(3, 2, flour));
        batter.addIngredient(new Measure(1, 2, milk));

        Mix frosting = new Mix("Frosting");
        frosting.addIngredient(new Measure(4, sugar));
        frosting.addIngredient(BUTTER); // two sticks of butter...
        frosting.addIngredient(BUTTER);

        Mix cake = new Mix("Cake");
        cake.addIngredient(new BakedIngredient(batter, CAKE_EXPANSION_FACTOR));
        cake.addIngredient(new Measure(3, 2, frosting));

        cake.printRecipe();
    }
}
