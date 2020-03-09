/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class OldStringRounding
 * Name:       fassg
 * Created:    12/11/2019
 */
package msoe.fassg.lab02;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * OldStringRounding purpose: round doubles using strings
 *
 * @author fassg
 * @version created on 12/11/2019 at 8:44 PM
 */
public class OldStringRounding {
    /**
     * will remove all trailing zeroes from a string
     * will remove the decimal place if it is the last character in the string
     * @param str the string to remove all trailing zeroes from
     * @return the string without the trailing zeroes
     */
    private String removeTrailingZeroes(String str) {
        int decimalIndex = str.indexOf('.');
        if (decimalIndex > 0) {
            for (int i = str.length() - 1; i > decimalIndex; i--) {
                if (str.charAt(str.length() - 1) == '0') {
                    str = str.substring(0, str.length() - 1);
                }
            }
            if (str.indexOf('.') == str.length() - 1) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    /**
     * will remove all trailing zeroes from a double
     * @param d the double to remove all trailing zeroes from
     * @return a string without the trailing zeroes
     */
    private String removeTrailingZeroes(double d) {
        String str = Double.toString(d);
        return removeTrailingZeroes(str);
    }

    /**
     * will remove all decimal places after the specified amount
     * @param str the string to remove the decimal places from
     * @param removeAfter the amount of decimal places to remove after
     * @return string with the decimal places after the amount removed
     */
    private String removeDecimalPlaces(String str, int removeAfter) {
        int decimalIndex = str.indexOf('.');
        if (decimalIndex > 0) {
            for (int i = str.length() - 1; i > str.indexOf('.') + removeAfter; i--) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    /**
     * will round a string at x decimal places while still leaving all places after intact
     * @param str the string to round
     * @param x the place to round to
     * @return a string rounded to the specified number of decimal places
     */
    private String roundToXDecimalPlaces(String str, int x) {
        final int roundingPoint = str.indexOf('.') + x + 1;
        if (str.indexOf('.') > -1 && str.length() -1 >= roundingPoint) {
            final int decimalPlace
                    = Integer.parseInt(Character.toString(str.charAt(roundingPoint)));
            final int roundingThreshold = 5;
            final double hundredthsIncrement = 0.01;
            if (decimalPlace >= roundingThreshold) {
                double outputAsDouble = Double.parseDouble(str);
                outputAsDouble += hundredthsIncrement;
                str = Double.toString(outputAsDouble);
            }
        }
        return str;
    }

    /**
     * will round the volume of an ingredient to two decimal places and remove all trailing zeroes
     * @param d the double to be rounded
     * @return string in the correct display format
     */
    private String round(double d) {
        String output = removeTrailingZeroes(d);
        output = roundToXDecimalPlaces(output, 2);
        output = removeDecimalPlaces(output, 2);
        output = removeTrailingZeroes(output);
        return output;
        //return removeTrailingZeroes(removeDecimalPlaces
        // (roundToXDecimalPlaces(removeTrailingZeroes(getCups()), 2), 2));
    }



    /**
     * faster version of the rounding method
     * does not involve strings unlike the other ones
     * @param d the double to round
     * @return string formatted correctly
     */
    static String fastRound(double d) {
        int i = (int) d;
        final double roundingThreshold = 0.005;
        if (d - i >= roundingThreshold) {
            String str = String.format("%.2f", d);
            if (str.charAt(str.length() - 1) == '0') {
                str = str.substring(0, str.length() -1);
            }
            return str;
        } else {
            return String.format("%d", i);
        }
    }
}
