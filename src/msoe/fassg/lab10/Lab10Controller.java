/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab10Controller
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab10;

import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab9Controller purpose: the primary controller class for Lab10
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:21 AM
 */
public class Lab10Controller {
    /**
     * stack of images used for undo functionality
     */
    Stack<Image> stack = new Stack<>();
    /**
     * Hbox the color picker is embedded in
     * used to toggle the entire color picker and label
     * visibility at same time.
     */
    @FXML
    private HBox colorPickerHBox;
    /**
     * color picker to let the user pick colors
     */
    @FXML
    private ColorPicker colorPicker;
    /**
     * the drawButton menu item used to toggle drawing on the image
     */
    @FXML
    private MenuItem drawButton;
    /**
     * boolean to track if the program should be drawing on the image right now
     * boolean to disable toggling between original and modified image when drawing
     */
    private boolean isDrawingEnabled = false;
    /**
     * Color to track the pen color for drawing
     */
    private Color penColor;
    /**
     * the overall VBox the main window is built in
     */
    @FXML
    private VBox vBox;
    /**
     * the filterButton menu item used to open the filter kernel window.
     */
    @FXML
    private MenuItem filterButton;
    /**
     * the top label in the program to display before/after
     */
    @FXML
    private Label topLabel;
    /**
     * the imageView used to display the image
     */
    @FXML
    private ImageView imageView;
    /**
     * the label used to display the color of the image pixel under the mouse
     */
    @FXML
    private Label mousePixelColorLabel;
    /**
     * the stage passed in from lab 8 for use with FileChooser
     */
    private Stage stage;
    /**
     * the FileChooser used to select files
     */
    private final FileChooser fileChooser = new FileChooser();
    /**
     * the file to be located
     */
    private File file;
    /**
     * the original image that was imported.
     */
    private Image originalImage;
    /**
     * current image only for use with toggling images
     */
    private Image currentImage;
    /**
     * boolean to track which image is displayed currently
     */
    private boolean isOriginalImage;
    /**
     * the other stage
     * aka window2 used for filter kernel values
     */
    private Stage otherStage;
    /**
     * the controller used for filter kernel values
     */
    private KernelController otherController;

    /**
     * method to set the default values of variables
     * @param stage stage pulled in from the start method
     */
    public void setDefaultValues(Stage stage) {
        penColor = Color.BLACK;
        isOriginalImage = false;
        this.stage = stage;
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BMSOE", "*.bmsoe"),
                new FileChooser.ExtensionFilter("MSOE", "*.msoe"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("TIFF", "*.tiff"),
                new FileChooser.ExtensionFilter("All Images", "*.*"));
        imageView.setOnMouseMoved(e -> {
            if (imageView.getImage() != null) {
                PixelReader reader = imageView.getImage().getPixelReader();
                int imageHeight = (int) imageView.getImage().getHeight();
                int imageWidth = (int) imageView.getImage().getWidth();
                int x = (int) ((e.getX() / imageView.getFitWidth()) * (imageWidth));
                int y = (int) ((e.getY() / imageView.getFitWidth()) * (imageHeight));
                Color temporaryColor = reader.getColor(x, y);
                mousePixelColorLabel.setText("Color Under Cursor: " +
                        ImageIO.colorToString(temporaryColor) +
                        "\nMouse Coordinates: (X = " + e.getX() + ", Y = " + e.getY() + ")");
            } else {
                mousePixelColorLabel.setText("No Image Loaded");
            }
        });
        imageView.setOnMouseClicked(e -> {
            if (!isOriginalImage && !isDrawingEnabled) {
                currentImage = imageView.getImage();
                imageView.setImage(originalImage);
                isOriginalImage = true;
                topLabel.setText("Before");
            } else if (isDrawingEnabled) {
                isOriginalImage = false;
            } else {
                imageView.setImage(currentImage);
                isOriginalImage = false;
                topLabel.setText("After");
            }
        });
        vBox.setOnMouseMoved(e -> updateFilterButtonText());
    }

    /**
     * method to set the other stage
     * @param otherStage the other stage of the kernel
     */
    public void setOtherStage(Stage otherStage) {
        this.otherStage = otherStage;
    }

    /**
     * method to set the other controller
     * @param otherController the other controller of the kernel
     */
    public void setOtherController(KernelController otherController) {
        this.otherController = otherController;
    }

    /**
     * changes the displayed image to grayscale
     * @param event the event that triggered the method
     */
    @FXML
    private void grayscale(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.grayscale();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * changes the displayed image to negative
     * @param event the event that triggered the method
     */
    @FXML
    private void negative(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.invert();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * changes the displayed image to red shifted
     * This transformation acts as a red filter.
     * The green and blue components of each pixel are set to zero.
     * @param event the event that triggered the method
     */
    @FXML
    private void red(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) ->
                Color.color(color.getRed(), 0, 0);
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * changes the displayed image to redGray
     * Transformation based on experiment from Edwin Land.
     * Based on Retinex Theory of Color Vision.
     * Performs a Grayscale transformation on the pixels in alternating rows,
     * and performs a Red Only transformation on the pixels in the other rows.
     * @param event the event that triggered the method
     */
    @FXML
    private void redGray(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> {
            if (pixelLocationY % 2 == 0) {
                return Color.color(color.getRed(), 0, 0);
            } else {
                return color.grayscale();
            }
        };
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * decreases the saturation of the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void decreaseSaturation(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.desaturate();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * increases the saturation of the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void increaseSaturation(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.saturate();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * darkens the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void darken(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.darker();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * brightens the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void brighten(ActionEvent event) {
        stack.add(imageView.getImage());
        Transformable transformable = (pixelLocationY, color) -> color.brighter();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * applies a filter operation to an image aka blur / sharpen
     * If the Apply button is pressed when an unsupported set of weights
     *  are entered in the window, an exception should be thrown
     * @param event the event that triggered the method
     */
    @FXML
    public void apply(ActionEvent event) {
        stack.add(imageView.getImage());
        if (stack.peek() != null) {
            final double[] kernel = otherController.getKernel();
            stack.add(imageView.getImage());
            imageView.setImage(ImageUtil.convolve(imageView.getImage(), kernel));
        } else {
            ImageIO.errorAlert("NullPointerException",
                    "No image has been loaded yet");
        }
    }

    /**
     * When clicked, the Show Filter button reveals a second window
     *  and changes the text on the button to Hide Filter.
     * When Hide Filter is clicked, the second window is hidden,
     *  and the text on the button changes back to Show Filter.
     * @param event the event that triggered the method
     */
    @FXML
    public void toggleWindow2(ActionEvent event) {
        if (otherStage.isShowing()){
            otherStage.hide();
            filterButton.setText("Show Filter");
        } else {
            otherStage.setX(stage.getX() + stage.getWidth());
            otherStage.setY(stage.getY());
            otherStage.show();
            filterButton.setText("Hide Filter");
        }
    }

    /**
     * method to update the Filter Button Text to the correct value
     *  in case the user manually closes the other scene.
     */
    private void updateFilterButtonText(){
        if (otherStage.isShowing()) {
            filterButton.setText("Hide Filter");
        } else {
            filterButton.setText("Show Filter");
        }
    }

    /**
     * creates implementations of the Transformable interface for
     *  image transformations as lambda expressions
     * @param image the image that the transformation will be applied to
     * @param transform the transformation to apply / specifies behavior of transformation
     * @return the transformed image
     */
    private static Image transformImage(Image image, Transformable transform) {
        try {
            checkNullImage(image); //checks if a null image was passed in
            int imageWidth = (int)image.getWidth();
            int imageHeight = (int)image.getHeight();
            WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
            PixelReader reader = image.getPixelReader();
            PixelWriter writer = writableImage.getPixelWriter();
            for (int height = 0; height < imageHeight; height++) {
                for (int width = 0; width < imageWidth; width++) {
                    writer.setColor(width, height, transform.apply(height,
                            reader.getColor(width, height)));
                }
            }
            return writableImage;
        } catch (NullPointerException e) {
            ImageIO.errorAlert("NullPointerException", "No image yet loaded");
        }
        return null;
    }

    /**
     * method to toggle the visibility of the mouse pixel color label
     */
    @FXML
    private void toggleViewMousePixelColor() {
        mousePixelColorLabel.setVisible(!mousePixelColorLabel.isVisible());
    }

    /**
     * method to display information about how the program functions and its commands
     */
    @FXML
    private void displayHelp() {
        String aboutInfo = "Author: Grant Fass\n" +
                "Date: 11 February 2020\n" +
                "Created for MSOE class CS 1021 - 21 ";
        String introInfo = "This program is designed to allow the user to manipulate images.\n" +
                "This program allows the user to open and save images in a variety of formats.\n" +
                "This program also allows the user to perform select modifications to images.\n";
        String acceptedFileTypes = "\'.tiff\', \'.jpg\', \'.png\', \'.msoe\', and \'.bmsoe\'" +
                " image formats are accepted\n";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help Dialog");
        alert.setHeaderText(null);
        alert.setContentText(introInfo + acceptedFileTypes + aboutInfo);
        alert.showAndWait();
    }

    /**
     * method to reload an image from the previously specified location
     * @param event the event that triggered the method
     */
    @FXML
    private void reload(ActionEvent event) {
        try {
            imageView.setImage(ImageIO.read(file.toPath()));
        } catch (InputMismatchException e) {
            ImageIO.errorAlert("InputMismatchException", e.getMessage());
        } catch (FileNotFoundException e) {
            ImageIO.errorAlert("FileNotFoundException",
                    e.getMessage() + " or operation was canceled");
        } catch (IOException e) {
            ImageIO.errorAlert("IOError", e.getMessage());
        } catch (IllegalArgumentException e) {
            ImageIO.errorAlert("IllegalArgumentException", e.getMessage());
        }
    }

    /**
     * method to undo last change
     */
    public void undo() {
        try {
            imageView.setImage(stack.pop());
        } catch (EmptyStackException e) {
            ImageIO.errorAlert("EmptyStackException",
                    "No operations have been done to the image yet " +
                            "or there are no more changes to undo");
        }
    }

    /**
     * Method to open up a FileChooser dialog to let the user select the location of a file
     * a new file will then be opened from the specified location
     * creates a Path and passes off to the Read method or ReadMSOE method.
     * @param event the event that triggered the method
     */
    @FXML
    private void load(ActionEvent event) {
        fileChooser.setTitle("Open Image");
        fileChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents"));
        file = fileChooser.showOpenDialog(stage);
        try {
            if (file != null) {
                Image tempImage = ImageIO.read(file.toPath());
                imageView.setImage(tempImage);
                originalImage = tempImage;
            }
        } catch (InputMismatchException e) {
            ImageIO.errorAlert("InputMismatchException", e.getMessage());
        } catch (FileNotFoundException e) {
            ImageIO.errorAlert("FileNotFoundException",
                    e.getMessage() + " or operation was canceled");
        } catch (IOException e) {
            ImageIO.errorAlert("IOError", e.getMessage());
        } catch (IllegalArgumentException e) {
            ImageIO.errorAlert("IllegalArgumentException", e.getMessage());
        }
    }

    /**
     * saves files to the drive at the specified location
     * @param event the event that triggered the method
     */
    @FXML
    private void save(ActionEvent event) {
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents"));
        file = fileChooser.showSaveDialog(stage);
        try {
            if (file != null) {
                ImageIO.write(imageView.getImage(), file.toPath());
            }
        } catch (FileNotFoundException e) {
            ImageIO.errorAlert("FileNotFoundException",
                    e.getMessage() + " or operation was canceled");
        } catch (IOException e) {
            ImageIO.errorAlert("IOException", e.getMessage());
        } catch (IllegalArgumentException e) {
            ImageIO.errorAlert("InvalidImageFileTypeException", e.getMessage());
        }
    }

    /**
     * closes any connections that may be open and exits
     */
    @FXML
    private void close() {
        ImageIO.close();
        System.exit(0);
    }

    /**
     * error if no image has been loaded yet
     * @param image the image to check
     * @throws NullPointerException no image loaded yet
     */
    private static void checkNullImage(Image image) throws NullPointerException {
        if (image == null) {
            throw new NullPointerException("No image is loaded yet");
        }
    }

    /**
     * method to toggle being able to draw on the image
     * @param event the event that triggered the method
     */
    @FXML
    private void draw(ActionEvent event) {
        if (drawButton.getText().equals("Draw")) {
            drawButton.setText("Stop Drawing");
            isDrawingEnabled = true;
            //enable drawing
            imageView.setOnMouseDragged(e -> {
                //create writeable image and modify pixels at mouse location
                if (imageView.getImage() != null) {
                    int imageHeight = (int) imageView.getImage().getHeight();
                    int imageWidth = (int) imageView.getImage().getWidth();
                    int mouseX = (int) ((e.getX() / imageView.getFitWidth()) * (imageWidth));
                    int mouseY = (int) ((e.getY() / imageView.getFitWidth()) * (imageHeight));
                    WritableImage image = new WritableImage(imageWidth, imageHeight);
                    PixelWriter writer = image.getPixelWriter();
                    PixelReader reader = imageView.getImage().getPixelReader();
                    //copy all color values from original image then replace color at mouse coords.
                    for (int height = 0; height < imageHeight; height++) {
                        for (int width = 0; width < imageWidth; width++) {
                            if (width == mouseX && height == mouseY) {
                                writer.setColor(width, height, penColor);
                            } else {
                                writer.setColor(width, height, reader.getColor(width, height));
                            }
                        }
                    }
                    imageView.setImage(image);
                }
            });
            //change text to stop drawing
        } else {
            //disable drawing
            //change text to draw
            drawButton.setText("Draw");
            isDrawingEnabled = false;
        }
    }

    /**
     * method to allow the user to pick a pen color to draw on the image with
     * @param event the event that triggered the method.
     */
    @FXML
    private void pickPenColor(ActionEvent event) {
        colorPickerHBox.setVisible(true);
        colorPicker.setOnAction(e -> {
            penColor = colorPicker.getValue();
            colorPickerHBox.setVisible(false);
        });
    }
}
