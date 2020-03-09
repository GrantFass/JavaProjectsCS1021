/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class ReferenceHolderDriver
 * Name:       fassg
 * Created:    12/18/2019
 */
package msoe.fassg.lab03;

import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Driver purpose: the driver for the ReferenceHolder which contains the main method
 *
 * @author fassg
 * @version created on 12/18/2019 at 5:34 PM
 */
public class Driver {
    /**
     * prints out the menu of choices for what to do to the user.
     */
    private static void printMenu() {
        System.out.println("Enter 0 to exit the program.");
        System.out.println("Enter 1 to enter a new book into the reference set.");
        System.out.println("Enter 2 to enter a new article into the reference set.");
        System.out.println("Enter 3 to update a reference.");
        System.out.println("Enter 4 to printout the entries in BibteX format");
    }

    /**
     * will get the user choice for what to do from the keyboard
     * @param in the scanner to receive input from the keyboard
     * @return users numerical choice
     */
    private static int getUserChoice(Scanner in) {
        return Integer.parseInt(in.nextLine());
    }

    /**
     * the selector to execute the command that is specified by the user
     * @param choice the command to execute (what to do)
     * @param holder the ReferenceHolder to hold the list of references
     * @param in the scanner to receive input from the keyboard
     */
    private static void runSelection(int choice, ReferenceHolder holder, Scanner in){
        switch (choice) {
            case 1:
                Book book = new Book();
                book.promptToInitialize(System.out, in);
                holder.addReference(book);
                break;
            case 2:
                Article article = new Article();
                article.promptToInitialize(System.out, in);
                holder.addReference(article);
                break;
            case 3:
                System.out.println("Enter the ID of the reference you want to update");
                holder.updateReference(in.nextLine(), System.out, in);
                break;
            case 4:
                System.out.print(holder);
                break;
        }
    }

    /**
     * the method that will loop while the choice is not zero
     * @param holder the ReferenceHolder to hold the list of references
     * @param in the scanner to receive input from the keyboard
     */
    private static void run(ReferenceHolder holder, Scanner in) {
        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = getUserChoice(in);
            runSelection(choice, holder, in);
        }
        System.out.println("Goodbye");
    }

    /**
     * the main method for the program
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ReferenceHolder holder = new ReferenceHolder();
        run(holder, in);
    }
}
