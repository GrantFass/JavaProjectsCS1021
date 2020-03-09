/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Book
 * Name:       fassg
 * Created:    12/16/2019
 */
package msoe.fassg.lab03;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Book purpose: to create book objects for lab 3
 *
 * @author fassg
 * @version created on 12/16/2019 at 9:42 AM
 */
public class Book extends Reference {
    /**
     * Name of the publisher of the book
     */
    private String publisher;

    /**
     * Prompts the user for initial attribute values of the book object.
     * @param out Output stream to prompt the user for input. Usually is System.out
     * @param in Input stream to read user input.
     */
    public void promptToInitialize(PrintStream out, Scanner in){
        out.println("Enter the author of the book");
        author = in.nextLine();
        out.println("Enter the title of the book");
        title = in.nextLine();
        out.println("Enter the publisher for the book.");
        publisher = in.nextLine();
        out.println("Enter the copyright year for the book.");
        publicationYear = Integer.parseInt(in.nextLine());
    }

    /**
     * Prompts the user to update the attributes of the book object.
     * Overrides promptForUpdate in class Reference
     * @param out Output stream to prompt the user for input. Usually System.out
     * @param in Input stream to read user input.
     */
    @Override
    public void promptForUpdate(PrintStream out, Scanner in){
        super.promptForUpdate(out, in);
        out.println("Enter the updated publisher for the book");
        publisher = in.nextLine();
    }

    /**
     * Gets the name of the publisher of the book
     * @return Name of the publisher
     */
    public String getPublisher(){
        return publisher;
    }

    /**
     * Returns a String of information in BibTeX format.
     * Overrides toString in class java.lang.Object
     * @return String of BibTeX information
     */
    @Override
    public String toString(){
        return String.format("@BOOK { %s,\n" + "author = \"%s\",\n" +
                        "title = \"%s\",\n" + "publisher = \"%s\",\n" +
                        "year = \"%d\"\n}\n", getMyUniqueID(), getAuthor(),
                getTitle(), getPublisher(), getPublicationYear());
    }
}
