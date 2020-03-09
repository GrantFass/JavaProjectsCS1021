/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class ShapeLoaderApp
 * Name:       fassg
 * Created:    1/26/2020
 */
package msoe.fassg.lab07;

import edu.msoe.winplotterfx.WinPlotterFX;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * ShapeLoaderApp purpose: Reads files with picture data and draws the specified shapes
 *
 * @author fassg
 * @version created on 1/26/2020 at 5:26 PM
 */
public class ShapeLoaderApp extends Application {

    /**
     * the ArrayList to store shapes
     */
    private ArrayList<Shape> shapeList = new ArrayList<>();

    /**
     * the plotter to draw shapes
     */
    WinPlotterFX plotter = new WinPlotterFX();


    /**
     * Class Method
     * the main method for the program
     * just calls launch
     * @param args ignored
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Class Method
     * the inputLine should represent one of the known shapes in the correct format.
     * if the format does not match one of the specified shapes then an exception is thrown
     * the exception will be handled by the readShapes method
     * @param inputLine the line of text that the shape will be parsed from
     * @return an instance of the appropriate shape
     * @throws ShapeFormatException exception if the line is formatted wrong
     * @throws InputMismatchException exception if the hexTriple string is wrongly formatted
     * @throws IllegalArgumentException exception if one of the dimensions < 0
     */
    public static Shape parseShape(String inputLine) throws
            ShapeFormatException, InputMismatchException, IllegalArgumentException {
        checkForValidShapeFormat(inputLine);
        //create a temporary string to help get values
        String temp = inputLine.substring(inputLine.indexOf(' ')).trim();
        //ArrayList of strings to store variable values
        ArrayList<String> vars = new ArrayList<>();
        final int xIndex = 0;
        final int yIndex = 1;
        final int colorIndex = 2;
        final int widthBaseOrRadiusIndex = 3;
        final int heightIndex = 4;
        final int labelIndex = 5;
        while (temp.length() > 0) {
            if (vars.size() == labelIndex) {
                vars.add(temp);
                temp = "";
            } else if (temp.indexOf(' ') != -1) {
                vars.add(temp.substring(0, temp.indexOf(' ')));
                temp = temp.substring(temp.indexOf(' ')).trim();
            } else {
                vars.add(temp);
                temp = "";
            }
        }
        switch (Character.toUpperCase(inputLine.charAt(0))) {
            case 'P':
                return new Point(Double.parseDouble(vars.get(xIndex)),
                        Double.parseDouble(vars.get(yIndex)),
                        stringToColor(vars.get(colorIndex)));
            case 'C':
                return new Circle(Double.parseDouble(vars.get(xIndex)),
                        Double.parseDouble(vars.get(yIndex)),
                        Double.parseDouble(vars.get(widthBaseOrRadiusIndex)),
                        stringToColor(vars.get(colorIndex)));
            case 'T':
                return new Triangle(Double.parseDouble(vars.get(xIndex)),
                        Double.parseDouble(vars.get(yIndex)),
                        Double.parseDouble(vars.get(widthBaseOrRadiusIndex)),
                        Double.parseDouble(vars.get(heightIndex)),
                        stringToColor(vars.get(colorIndex)));
            case 'R':
                return new Rectangle(Double.parseDouble(vars.get(xIndex)),
                        Double.parseDouble(vars.get(yIndex)),
                        Double.parseDouble(vars.get(widthBaseOrRadiusIndex)),
                        Double.parseDouble(vars.get(heightIndex)),
                        stringToColor(vars.get(colorIndex)));
            case 'L':
                switch (Character.toUpperCase(inputLine.charAt(1))) {
                    case 'T':
                        return new LabeledTriangle(Double.parseDouble(vars.get(xIndex)),
                                Double.parseDouble(vars.get(yIndex)),
                                Double.parseDouble(vars.get(widthBaseOrRadiusIndex)),
                                Double.parseDouble(vars.get(heightIndex)),
                                stringToColor(vars.get(colorIndex)),
                                vars.get(labelIndex));
                    case 'R':
                        return new LabeledRectangle(Double.parseDouble(vars.get(xIndex)),
                                Double.parseDouble(vars.get(yIndex)),
                                Double.parseDouble(vars.get(widthBaseOrRadiusIndex)),
                                Double.parseDouble(vars.get(heightIndex)),
                                stringToColor(vars.get(colorIndex)),
                                vars.get(labelIndex));
                }
        }
        return null;
    }

    /**
     * Class Method
     * if the argument is not a hexTriplet
     *  (does not start with # or is not 7 chars long, or if last 6 chars are not hex numbers)
     *  then an InputMismatchException will be thrown
     * @param hexTriplet string that should contain a hexTriplet
     * @return a Color instance of the appropriate color
     * @throws InputMismatchException exception if the string did not match hexTriplet format
     */
    public static Color stringToColor(String hexTriplet) throws InputMismatchException {
        return Color.web(checkForValidHexTriplet(hexTriplet));
    }

    /**
     * Instance Method
     * calls queryFileLocation go obtain the filename from the user
     * reads the header information from the file (aka first 3 lines)
     * creates a WinPlotterFX with the appropriate characteristics
     * @param primaryStage the stage to use
     */
    @Override
    public void start(Stage primaryStage) {
        readShapes();
        drawShapes();
        plotter.showPlotter();
    }

    /**
     * Instance Method
     * reads all lines after the header information from the file
     * stores each shape in a List of shapes
     * Uses the parseShape() method to create the appropriate shape
     * If parseShape() throws an exception then no shape is added for that line of the input file
     */
    public void readShapes() {
        File fileLocation;
        Scanner in;
        Shape temp;
        String tempString;
        try {
            fileLocation = new File(queryFileLocation());
            in = new Scanner(fileLocation);
            plotter.setWindowTitle(in.nextLine());
            String temporarySize = in.nextLine();
            plotter.setWindowSize(Double.parseDouble(temporarySize.substring(0,
                    temporarySize.indexOf(' '))),
                    Double.parseDouble(temporarySize.substring(temporarySize.indexOf(' '))));
            Color tempColor = stringToColor(in.nextLine());
            plotter.setBackgroundColor(tempColor.getRed(), tempColor.getBlue(),
                    tempColor.getGreen());
            while (in.hasNext()) {
                tempString = in.nextLine();
                //System.out.println(tempString);
                temp = parseShape(tempString);
                if (temp != null) {
                    shapeList.add(temp);
                }
            }
        } catch (ShapeFormatException e) {
            //errorAlert("ShapeFormatException", e.getMessage());
            System.out.println("ShapeFormatException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            errorAlert("IllegalArgumentException encountered",
                    "Non-Zero value for dimension was entered");
        } catch (InputMismatchException e) {
            errorAlert("InputMismatchException", e.getMessage());
        } catch (FileNotFoundException e) {
            errorAlert("FileNotFoundException", "File was not found at the specified path");
            System.exit(0);
        }
    }

    /**
     * Instance Method
     * Draws all of the shapes in the shape list
     */
    public void drawShapes() {
        for(Shape x: shapeList) {
            x.draw(plotter);
        }
    }



    /**
     * method to get the user's input for the location of the file to be used to create the shapes
     * @return the file location as a string
     */
    private String queryFileLocation() {
        //Can use a TextInputDialog or FileChooser to get the filename from the user
        TextInputDialog dialog = new TextInputDialog("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents\\\\Lab7Data.txt");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Please enter the location of the file to be used: ");
        dialog.setContentText("File Location:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return "";
    }

    /**
     * Class that checks to see if the input string is valid
     * @param input the string to be checked
     * @return input for ease of use
     * @throws InputMismatchException the exception to be thrown
     */
    protected static String checkForValidHexTriplet(String input)
            throws InputMismatchException {
        final int hexTripletLength = 7;
        final int hexadecimalBase = 16;
        if (input.length() != hexTripletLength) {
            throw new InputMismatchException("Input string was of the wrong length for" +
                    "Hex Triplet. Expected length = 7, Found length = " + input.length());
        } else if (input.charAt(0) != '#') {
            throw new InputMismatchException("Input string had the wrong first character." +
                    "Expected \'#\', Found \'" + input.charAt(0) + "\'");
        }
        for (int i = 1; i < input.length(); i++) {
            if (Character.digit(input.charAt(i), hexadecimalBase) == -1) {
                throw new InputMismatchException("Input string contains non-hexadecimal " +
                        "character. Character at index " + i + "is not a hexadecimal character");
            }
        }
        return input;
    }

    /**
     * Class to create custom Exceptions
     */
    public static class InputMismatchException extends Exception {
        /**
         * the constructor for the InputMismatchException class
         * calls the constructor for the Exception class
         * @param errorMessage the error message
         */
        public InputMismatchException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * method uses various regular expressions to compare a given string to proper shape format
     * @param input the string to be compared
     * @throws ShapeFormatException exception for if the line is not formatted correctly
     */
    protected static void checkForValidShapeFormat(String input)
            throws ShapeFormatException {
        //regex completed with the help of https://regex101.com/
        Pattern pointRegex = Pattern.compile("[Pp]:\\s*\\d*\\s*\\d*\\s*#.{6}");
        Matcher pointMatcher = pointRegex.matcher(input);
        Pattern circleRegex = Pattern.compile("[Cc]:\\s*\\d*\\s*\\d*\\s*#.{6}\\s*\\d*");
        Matcher circleMatcher = circleRegex.matcher(input);
        Pattern triangleRegex = Pattern.compile("[Tt]:\\s*\\d*\\s*\\d*\\s*#.{6}\\s*\\d*\\s*\\d*");
        Matcher triangleMatcher = triangleRegex.matcher(input);
        Pattern rectangleRegex = Pattern.compile("[Rr]:\\s*\\d*\\s*\\d*\\s*#.{6}\\s*\\d*\\s*\\d*");
        Matcher rectangleMatcher = rectangleRegex.matcher(input);
        Pattern labeledRegex =
                Pattern.compile("[Ll][RrTt]:\\s*\\d*\\s*\\d*\\s*#.{6}\\s*\\d*\\s*\\d*\\s*.*");
        Matcher labeledMatcher = labeledRegex.matcher(input);
        if (!(pointMatcher.find() || circleMatcher.find() ||
                triangleMatcher.find() || rectangleMatcher.find() || labeledMatcher.find())) {
            throw new ShapeFormatException("Input line did not match any " +
                    "of the expected line formats");
        }
    }

    /**
     * Class to create custom Exceptions
     */
    public static class ShapeFormatException extends Exception {
        /**
         * the constructor for the InputMismatchException class
         * calls the constructor for the Exception class
         * @param errorMessage the error message
         */
        public ShapeFormatException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * method to display an alert with the error format
     * @param header the header text to be displayed
     * @param content the content text to be displayed
     */
    public static void errorAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
