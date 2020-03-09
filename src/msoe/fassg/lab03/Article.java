/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Article
 * Name:       fassg
 * Created:    12/16/2019
 */
package msoe.fassg.lab03;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Article purpose: to create article objects for lab 3
 *
 * @author fassg
 * @version created on 12/16/2019 at 9:43 AM
 */
public class Article extends Reference {
    /**
     * The page number that the article ends on
     */
    private int endingPage;

    /**
     * The page number that the article starts on
     */
    private int startingPage;

    /**
     * The name of the journal the article was published in
     */
    private String journal;

    /**
     * Prompts the user for initial attribute values of the article object.
     * @param out Output stream to prompt the user for input. Usually System.out
     * @param in Input stream to read user input.
     */
    public void promptToInitialize(PrintStream out, Scanner in){
        out.println("Enter the author of the article");
        author = in.nextLine();
        out.println("Enter the title of the article");
        title = in.nextLine();
        out.println("Enter the title of the journal.");
        journal = in.nextLine();
        out.println("Enter the first page of the article.");
        startingPage = Integer.parseInt(in.nextLine());
        out.println("Enter the last page of the article.");
        endingPage = Integer.parseInt(in.nextLine());
        out.println("Enter the copyright year for the article.");
        publicationYear = Integer.parseInt(in.nextLine());
    }

    /**
     * Prompts the user to update the attributes of the article object.
     * Overrides promptForUpdate in class Reference
     * @param out Output stream to prompt the user for input. Usually System.out
     * @param in Input stream to read user input.
     */
    @Override
    public void promptForUpdate(PrintStream out, Scanner in){
        super.promptForUpdate(out, in);
        out.println("Enter the updated title of the journal.");
        journal = in.nextLine();
        out.println("Enter the updated first page of the article.");
        setStartingPage(Integer.parseInt(in.nextLine()));
        out.println("Enter the updated last page of the article.");
        setEndingPage(Integer.parseInt(in.nextLine()));
    }

    /**
     * Gets the last page of an article
     * @return page number for last page
     */
    public int getEndingPage() {
        return endingPage;
    }

    /**
     * Gets first page of article
     * @return page number for first page
     */
    public int getStartingPage() {
        return startingPage;
    }

    /**
     * Gets name of journal
     * @return name of the journal article was published in
     */
    public String getJournal() {
        return journal;
    }

    /**
     * Returns a String of information in BibTeX format.
     * Overrides toString in class java.lang.Object
     * @return String of BibTeX information
     */
    @Override
    public String toString() {
        return String.format("@ARTICLE { %s,\nauthor = \"%s\",\ntitle = \"%s\",\n" +
                "journal = \"%s\",\npages = \"%d-%d\",\nyear = \"%d\"\n}\n",
                getMyUniqueID(), getAuthor(), getTitle(), getJournal(),
                getStartingPage(), getEndingPage(), getPublicationYear());
    }

    /**
     * Sets the ending page of the article.
     * If the desired ending page is less than the starting page, no change is made.
     * @param endingPage Page number of the last page of the article
     * @return true if the page was changed.
     */
    protected boolean setEndingPage(int endingPage){
        if (endingPage >= startingPage){
            this.endingPage = endingPage;
            return true;
        }
        return false;
    }

    /**
     * Sets first page of the article.
     * If the desired starting page is not positive or
     * is greater than the current ending page, no change is made.
     * @param startingPage Page number of the first page of the article.
     * @return true if the page was changed
     */
    protected boolean setStartingPage(int startingPage){
        if (startingPage >= 0 && startingPage <= endingPage){
            this.startingPage = startingPage;
            return true;
        }
        return false;
    }
}
