/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab9Controller
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab09;

import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab9Controller purpose: the primary controller class for Lab 9
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:21 AM
 */
public class Lab9Controller {
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
     * the progress bar to track progress loading files
     */
    @FXML
    private ProgressBar progressBar;
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
     * the previous image for use in the undo function
     */
    private Image lastImage;
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
        progressBar.setVisible(false);
        progressBar = new ProgressBar(0);
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
                        ImageIO.colorToString(temporaryColor));
            } else {
                mousePixelColorLabel.setText("No Image Loaded");
            }
        });
        imageView.setOnMouseClicked(e -> {
            if (!isOriginalImage) {
                currentImage = imageView.getImage();
                imageView.setImage(originalImage);
                isOriginalImage = true;
                topLabel.setText("Before");
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
        lastImage = imageView.getImage();
        Transformable transformable = (pixelLocationY, color) -> color.grayscale();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * changes the displayed image to negative
     * @param event the event that triggered the method
     */
    @FXML
    private void negative(ActionEvent event) {
        lastImage = imageView.getImage();
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
        lastImage = imageView.getImage();
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
        lastImage = imageView.getImage();
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
        lastImage = imageView.getImage();
        Transformable transformable = (pixelLocationY, color) -> color.desaturate();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * increases the saturation of the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void increaseSaturation(ActionEvent event) {
        lastImage = imageView.getImage();
        Transformable transformable = (pixelLocationY, color) -> color.saturate();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * darkens the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void darken(ActionEvent event) {
        lastImage = imageView.getImage();
        Transformable transformable = (pixelLocationY, color) -> color.darker();
        imageView.setImage(transformImage(imageView.getImage(), transformable));
    }

    /**
     * brightens the displayed image
     * @param event the event that triggered the method
     */
    @FXML
    private void brighten(ActionEvent event) {
        lastImage = imageView.getImage();
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
        lastImage = imageView.getImage();
        if (lastImage != null) {
            final double[] kernel = otherController.getKernel();
            lastImage = imageView.getImage();
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
        String information = "This program is designed to allow the user to manipulate images." +
                "\nFiles can be loaded with the \'open\' command." +
                "\nFiles can be saved to a different format or location with the \'save\' " +
                "command.\nImages can be manipulated with the options under the \'edit\' tab." +
                "\nIf a mistake is made while editing the file can be refreshed from scratch " +
                "with the \'reload\' command.\nThe program can be closed with the \'close\'" +
                "command.\nPlease enjoy the program";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help Dialog");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

    /**
     * method to reload an image from the previously specified location
     * @param event the event that triggered the method
     */
    @FXML
    private void reload(ActionEvent event) {
        imageView.setImage(ImageIO.read(file.toPath()));
    }

    /**
     * method to undo last change
     */
    public void undo() {
        imageView.setImage(lastImage);
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
        if (file != null) {
            Image tempImage = ImageIO.read(file.toPath());
            imageView.setImage(tempImage);
            originalImage = tempImage;
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
        if (file != null) {
            ImageIO.write(imageView.getImage(), file.toPath());
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
}
