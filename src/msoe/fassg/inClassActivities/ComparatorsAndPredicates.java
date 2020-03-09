/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class ComparatorsAndPredicates
 * Name:       fassg
 * Created:    2/7/2020
 */
package msoe.fassg.inClassActivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * ComparatorsAndPredicates purpose: learn how to use
 * comparators and predicates with lambda expressions
 *
 * @author fassg
 * @version created on 2/7/2020 at 9:16 AM
 */
public class ComparatorsAndPredicates {
    /**
     * static method to filter based on predicates
     * @param list the list to filter
     * @param pred the predicate to filter with
     * @return the resultant list of filtered strings
     */
    public static List<String> filter(List<String> list,
                                      Predicate<String> pred) {
        List<String> kept = new ArrayList<>();
        for(String s : list) {
            if(pred.test(s)) {
                kept.add(s);
            }
        }
        return kept;
    }

    public static void main(String[] args) {
        Comparator<String> firstLetterCompare = (string1, string2) -> {
            char char1 = string1.toLowerCase().charAt(0);
            char char2 = string2.toLowerCase().charAt(0);
            if (char1 > char2) {
                return 1;
            } else if (char1 < char2) {
                return -1;
            } else {
                return 0;
            }
        };
        Comparator<String> lastLetterCompare = (string1, string2) -> {
            char char1 = string1.toLowerCase().charAt(string1.length() - 1);
            char char2 = string2.toLowerCase().charAt(string2.length() - 1);
            return Character.compare(char1, char2);
        };
        Comparator<String> capitalLetterCompare = (string1, string2) -> {
            char char1 = string1.charAt(0);
            char char2 = string2.charAt(0);
            return Character.compare(char1, char2);
        };
        Predicate<String> even = s -> s.length() % 2 == 0;
        Predicate<String> odd = s -> s.length() % 2 == 1;


        List<String> words = Arrays.asList("The", "quick", "brown",
                "fox", "jumped", "over", "the", "lazy", "dog");
        List<String> evenWords = filter(words, even);
        List<String> oddWords = filter(words, odd);

        List<String> firstLetter = new ArrayList<>(words);
        List<String> lastLetter = new ArrayList<>(words);
        List<String> capitalLetter = new ArrayList<>(words);
        Collections.sort(firstLetter, firstLetterCompare);
        Collections.sort(lastLetter, lastLetterCompare);
        Collections.sort(capitalLetter, capitalLetterCompare);

        System.out.println("Words: " + words);
        System.out.println("Even Words: " + evenWords);
        System.out.println("Odd Words: " + oddWords);
        System.out.println("First Letter Sorted: " + firstLetter);
        System.out.println("Last Letter Sorted: " + lastLetter);
        System.out.println("Capital Letter Sorted: " + capitalLetter);
    }
}
