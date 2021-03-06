/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class ImageIO
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab10;

import edu.msoe.cs1021.ImageUtil;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * ImageIO purpose: the IOImage class for lab 10
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:19 AM
 */
public class ImageIO {

    /**
     * the extension on the file for use with error checking
     */
    private static String extension;

    /**
     * the input scanner to read files from the drive
     */
    private static Scanner in;

    /**
     * the output writer to send files to the drive
     */
    private static PrintWriter out;

    /**
     * reads in images of the '.png', '.jpg', or '.tiff' file types
     * @param path the location of the image to read
     * @return the image
     * @throws InputMismatchException if one of the hex triplets is incorrectly formatted
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if there are file issues
     * @throws IllegalArgumentException if a value or extension is incorrect
     */
    public static Image read(Path path) throws InputMismatchException, FileNotFoundException,
            IOException, IllegalArgumentException {
        checkNullFile(path.toFile());
        extension = path.toString().substring(path.toString().indexOf('.'));
        switch (checkFileExtension(extension)) {
            case "msoe":
                return readMSOE(path);
            case "bmsoe":
                return readBMSOE(path);
            default:
                return ImageUtil.readImage(path);
        }
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
        if(!in.nextLine().equals("MSOE")) {
            throw new InputMismatchException("Invalid file type. Expected \'BMSOE\'");
        }
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
     * Reads files with the '.bmsoe' extension as images
     * builds pictures based on their width, height, and the color of each pixel
     * @param path the location of the '.bmsoe' image file
     * @return the constructed image
     * @throws IOException if there are file issues
     * @throws InputMismatchException if one of the hex triplets is incorrectly formatted
     */
    private static Image readBMSOE(Path path) throws IOException, InputMismatchException {
        DataInputStream dataIn = new DataInputStream(new BufferedInputStream(
                new FileInputStream(path.toFile())));
        //the chars B, M, S, O, E each written as byte.
        // the image width written as an int.
        // the image height written as an int
        // each pixel stored as a single integer value with bits 24-31 representing alpha
        // bits 16-23 representing the red channel
        // bits 8-15 representing the green channel
        // bits 0-7 representing the blue channel

        final int expectedBMSOELength = 5;
        char[] expectedBMSOE = new char[expectedBMSOELength];
        for (int byteIndex = 0; byteIndex < expectedBMSOELength; byteIndex++) {
            expectedBMSOE[byteIndex] = (char) dataIn.readByte();
        }
        String expectedBMSOEString = new String(expectedBMSOE);
        if (!expectedBMSOEString.equals("BMSOE")) {
            dataIn.close();
            throw new InputMismatchException("Invalid file type. Expected \'BMSOE\', " +
                    "Found: " + expectedBMSOEString);
        }
        int imageWidth = dataIn.readInt();
        int imageHeight = dataIn.readInt();
        Color tempColor;
        //create a new writable image of the specified width and height
        WritableImage image = new WritableImage(imageWidth, imageHeight);
        PixelWriter writer = image.getPixelWriter();
        for (int height = 0; height < imageHeight; height++) {
            for (int width = 0; width < imageWidth; width++) {
                tempColor = intToColor(dataIn.readInt());
                writer.setColor(width, height, tempColor);
            }
        }
        dataIn.close();
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
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if there are file issues
     * @throws IllegalArgumentException if a value or extension is incorrect
     */
    public static void write(Image image, Path path) throws FileNotFoundException,
            IOException, IllegalArgumentException {
        checkNullFile(path.toFile());
        extension = path.toString().substring(path.toString().indexOf('.'));
        switch (checkFileExtension(extension)) {
            case "msoe":
                writeMSOE(image, path);
                break;
            case "bmsoe":
                writeBMSOE(image, path);
                break;
            default:
                ImageUtil.writeImage(path, image);
                break;
        }
        out.close();
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
     * method to write '.bmsoe' files
     * @param image the image to be written
     * @param path where to write the image to
     * @throws IOException exception for file error
     */
    private static void writeBMSOE(Image image, Path path) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(path.toFile())));
        /*
        potentially use Color.getOpacity() to determine if an image has an alpha channel
         */
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
        PixelReader reader = image.getPixelReader();
        dataOut.writeBytes("BMSOE");
        dataOut.writeInt(imageWidth);
        dataOut.writeInt(imageHeight);
        for (int height = 0; height < imageHeight; height++) {
            for (int width = 0; width < imageWidth; width++) {
                dataOut.writeInt(colorToInt(reader.getColor(width, height)));
            }
        }
        dataOut.close();
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
     * method to convert integers to colors provided by https://msoe.us/taylor/cs1021/Lab9
     * @param color the integer to convert to a color
     * @return the color version of the integer
     */
    private static Color intToColor(int color) {
        final int conversionFactorFF = 0x000000FF;
        final double conversionFactor255 = 255.0;
        final int conversionFactor8 = 8;
        final int conversionFactor16 = 16;
        final int conversionFactor24 = 24;
        double red = ((color >> conversionFactor16) & conversionFactorFF)/conversionFactor255;
        double green = ((color >> conversionFactor8) & conversionFactorFF)/conversionFactor255;
        double blue = (color & conversionFactorFF)/conversionFactor255;
        double alpha = ((color >> conversionFactor24) & conversionFactorFF)/conversionFactor255;
        return new Color(red, green, blue, alpha);
    }

    /**
     * method to convert colors to integers provided by https://msoe.us/taylor/cs1021/Lab9
     * @param color the color to convert to an integer
     * @return the integer version of the color
     */
    private static int colorToInt(Color color) {
        final int conversionFactorFF = 0x000000FF;
        final double conversionFactor255 = 255.0;
        final int conversionFactor8 = 8;
        final int conversionFactor16 = 16;
        final int conversionFactor24 = 24;
        int red = ((int)(color.getRed()*conversionFactor255)) & conversionFactorFF;
        int green = ((int)(color.getGreen()*conversionFactor255)) & conversionFactorFF;
        int blue = ((int)(color.getBlue()*conversionFactor255)) & conversionFactorFF;
        int alpha = ((int)(color.getOpacity()*conversionFactor255)) & conversionFactorFF;
        return (alpha << conversionFactor24) +
                (red << conversionFactor16) +
                (green << conversionFactor8) + blue;
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
     * @return the extension on the file
     * @throws IllegalArgumentException if extension is not one of the listed ones.
     */
    private static String checkFileExtension(String extension)
            throws IllegalArgumentException {
        extension = extension.toLowerCase();
        switch (extension) {
            case ".msoe":
                return "msoe";
            case ".bmsoe":
                return "bmsoe";
            case ".jpg":
            case ".png":
            case ".tiff":
                return "standard";
            default:
                throw new IllegalArgumentException("The file of type "
                        + extension + " is not supported." +
                        "Supported file types are \'.bmsoe\', \'.msoe\', " +
                        "\'.jpg\', \'.png\', or \'.tiff\'.");
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
