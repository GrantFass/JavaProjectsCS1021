/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class IOImage
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab08;

import edu.msoe.cs1021.ImageUtil;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * IOImage purpose: the IOImage class for lab 8
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:19 AM
 */
public class IOImage {

    /**
     * the extension on the file for use with error checking
     */
    private static String extension;

    /**
     * the input stream to read files from the drive
     */
    private static Scanner in;

    /**
     * the output stream to send files to the drive
     */
    private static PrintWriter out;

    /**
     * reads in images of the '.png', '.jpg', or '.tiff' file types
     * @param path the location of the image to read
     * @return the image
     */
    public static Image read(Path path) {
        try {
            checkNullFile(path.toFile());
            extension = path.toString().substring(path.toString().indexOf('.'));
            switch (checkFileExtension(extension)) {
                case "msoe":
                    return readMSOE(path);
                case "standard":
                    return ImageUtil.readImage(path);
            }
        } catch (InputMismatchException e) {
            errorAlert("InputMismatchException", e.getMessage());
        } catch (FileNotFoundException e) {
            errorAlert("FileNotFoundException", e.getMessage() + " or operation was canceled");
        } catch (IOException e) {
            errorAlert("IOError", e.getMessage());
        } catch (IllegalArgumentException e) {
            errorAlert("IllegalArgumentException", e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

    /**
     * Reads files with the '.msoe' extension as images
     * builds pictures based on their width, height, and the color of each pixel
     * @param path the location of the '.msoe' image file
     * @return the constructed image
     * @throws IOException if there are file issues
     * @throws InputMismatchException if one of the hex triplets is incorrectly formatted
     */
    private static Image readMSOE(Path path) throws IOException, InputMismatchException {
        in = new Scanner(path);
        in.nextLine(); // specifies the image is of MSOE format
        String temp = in.nextLine();
        int imageWidth = Integer.parseInt(temp.substring(0, temp.indexOf(' ')));
        int imageHeight = Integer.parseInt(temp.substring(temp.indexOf(' ') + 1));
        //create a new writable image of the specified width and height
        WritableImage image = new WritableImage(imageWidth, imageHeight);
        PixelWriter writer = image.getPixelWriter();
        for (int height = 0; height < imageHeight; height++) {
            //read line
            temp = in.nextLine();
            //loop sideways to read hex triplets
            for (int width = 0; width < imageWidth; width++) {
                while (temp.indexOf(' ') == 0) {
                    temp = temp.substring(1);
                }
                if (temp.indexOf(' ') != -1) {
                    writer.setColor(width, height, stringToColor(temp.substring(0,
                            temp.indexOf(' '))));
                    temp = temp.substring(temp.indexOf(' ') + 1);
                } else {
                    writer.setColor(width, height, stringToColor(temp));
                }
            }
        }
        in.close();
        return image;
    }

    /**
     * method to close any open connections
     */
    public static void close() {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    /**
     * writes images of the '.png', '.jpg', or '.tiff' file types
     * @param image the image to write
     * @param path where to write the file to
     */
    public static void write(Image image, Path path) {
        try {
            checkNullFile(path.toFile());
            extension = path.toString().substring(path.toString().indexOf('.'));
            switch (checkFileExtension(extension)) {
                case "msoe":
                    writeMSOE(image, path);
                    break;
                case "standard":
                    ImageUtil.writeImage(path, image);
                    break;
            }
        } catch (FileNotFoundException e) {
            errorAlert("FileNotFoundException", e.getMessage() + " or operation was canceled");
        } catch (IOException e) {
            errorAlert("IOError", e.getMessage());
        } catch (IllegalArgumentException e) {
            errorAlert("InvalidImageFileTypeException", e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * method to write '.msoe' files
     * @param image the image to be written
     * @param path where to write the image to
     * @throws IOException exception for file error
     */
    private static void writeMSOE(Image image, Path path) throws IOException {
        out = new PrintWriter(path.toFile());
        int imageWidth = (int)image.getWidth();
        int imageHeight = (int)image.getHeight();
        PixelReader reader = image.getPixelReader();
        out.println("MSOE");
        out.println(imageWidth + " " + imageHeight);
        for (int height = 0; height < imageHeight; height++) {
            for (int width = 0; width < imageWidth; width++) {
                out.print(colorToString(reader.getColor(width, height)) + "  ");
            }
            out.print("\n");
        }
        out.close();
    }

    /**
     * method to turn colors back into hex triplets
     * @param color the color to convert
     * @return hex triplet version of color
     */
    public static String colorToString(Color color) {
        final int colorModifier = 255;
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * colorModifier),
                (int)(color.getGreen() * colorModifier),
                (int)(color.getBlue() * colorModifier));
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
     * Class that checks to see if the input string is valid
     * @param input the string to be checked
     * @return input for ease of use
     * @throws InputMismatchException the exception to be thrown
     */
    private static String checkForValidHexTriplet(String input)
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
     * method to check the extension of a file and make sure it matches one of the valid formats
     * @param extension the extension to check
     * @return 1 if file is '.msoe' or 2 if file is '.jpg', '.png', or '.tiff'
     * @throws IllegalArgumentException if extension is not one of the listed ones.
     */
    private static String checkFileExtension(String extension)
            throws IllegalArgumentException {
        extension = extension.toLowerCase();
        if (extension.equals(".msoe")) {
            return "msoe";
        } else if (extension.equals(".jpg")
                || extension.equals(".png")
                || extension.equals(".tiff")) {
            return "standard";
        } else {
            throw new IllegalArgumentException("The file of type "
                    + extension + " is not supported." +
                    "Supported file types are \'.msoe\', \'.jpg\', \'.png\', or \'.tiff\'.");
        }
    }

    /**
     * error if no file is loaded or file is null
     * @param file the file to check
     * @throws FileNotFoundException null file
     */
    private static void checkNullFile(File file) throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException("File was not found at specified address");
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
