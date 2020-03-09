/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class ReferenceHolder
 * Name:       fassg
 * Created:    12/16/2019
 */
package msoe.fassg.lab03;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * ReferenceHolder purpose: program to contain and manage the list of references.
 *
 * @author fassg
 * @version created on 12/16/2019 at 9:44 AM
 */
public class ReferenceHolder {
    /**
     * List to store the references
     */
    private final List<Reference> references;

    /**
     * Constructor for ReferenceHolder object
     */
    public ReferenceHolder(){
        references = new ArrayList<>();
    }

    /**
     * Add a book reference to the bibliography
     * @param book the book reference to be added
     */
    public void addReference(Book book){
        references.add(book);
    }

    /**
     * Add an article reference to the bibliography
     * @param article the article reference to be added
     */
    public void addReference(Article article){
        references.add(article);
    }

    /**
     * Removes the reference with the specified ID
     * @param uniqueID the id for the reference to be removed
     * @return true if the desired reference was removed
     */
    public boolean removeReference(String uniqueID){
        for(Reference x:references){
            if(x.getMyUniqueID().equals(uniqueID)) {
                references.remove(x);
                return true;
            }
        }
        return false;
    }

    /**
     * Asks the reference corresponding to the uniqueID to update itself.
     * If no reference with uniqueID is found, no update is performed and the method returns false.
     * @param uniqueID the ID for the reference to be updated
     * @param out Output stream to prompt the user for input
     * @param in Input stream to read user input
     * @return true if the desired reference was updated
     */
    public boolean updateReference(String uniqueID, PrintStream out, Scanner in){
        for(Reference x:references){
            if(x.getMyUniqueID().equals(uniqueID)) {
                x.promptForUpdate(out, in);
                return true;
            }
        }
        return false;
    }

    /**
     * Return a string containing all BibTeX entries in the reference list.
     * Overrides toString in class java.lang.Object
     * @return a string containing all BibTeX entries
     */
    @Override
    public String toString() {
        String out = "";
        for(Reference x:references){
            out += x;
        }
        return out;
    }
}
