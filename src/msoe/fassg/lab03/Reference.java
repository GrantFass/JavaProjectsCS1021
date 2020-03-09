/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Reference
 * Name:       fassg
 * Created:    12/16/2019
 */
package msoe.fassg.lab03;


import java.io.PrintStream;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Reference purpose: the parent class for both Book and Article which shares some common code
 *
 * @author fassg
 * @version created on 12/16/2019 at 9:43 AM
 */
public abstract class Reference {
    /**
     * Keeps track of how many instances of this type have been created.
     * This class variable is used to ensure all reference objects have a unique id.
     */

    private static int instanceCount = 0;
    /**
     * the person who wrote the reference
     */
    protected String author;

    /**
     * The unique ID assigned to this instance of the reference
     */
    protected final String myUniqueID;

    /**
     * Year of publication
     */
    protected int publicationYear;

    /**
     * The title for the reference
     */
    protected String title;

    /**
     * creates a reference and assigns a unique ID to it
     */
    public Reference(){
        myUniqueID = "REF" + instanceCount;
        instanceCount++;
    }

    /**
     * Prompts the user for initial attribute values of the reference object.
     * @param out Output stream to prompt the user for input. Usually System.out
     * @param in Input stream to read user input.
     */
    public abstract void promptToInitialize(PrintStream out, Scanner in);

    /**
     * Prompts the user to update the attributes of the reference object.
     * @param out Output stream to prompt the user for input. Usually System.out
     * @param in Input stream to read user input.
     */
    public void promptForUpdate(PrintStream out, Scanner in){
        out.println("Enter the updated author of the reference");
        author = in.nextLine();
        out.println("Enter the updated title of the reference");
        title = in.nextLine();
        out.println("Enter the updated copyright year for the reference.");
        publicationYear = Integer.parseInt(in.nextLine());
    }

    /**
     * Get the author
     * @return author of this reference
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Get the unique ID associated with this reference
     * @return the unique ID
     */
    public String getMyUniqueID(){
        return myUniqueID;
    }

    /**
     * Get publication year
     * @return publication year of this reference
     */
    public int getPublicationYear(){
        return publicationYear;
    }

    /**
     * Get title
     * @return title of this reference
     */
    public String getTitle(){
        return title;
    }
}
