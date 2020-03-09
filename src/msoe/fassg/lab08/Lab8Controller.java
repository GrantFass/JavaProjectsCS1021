/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Lab8Controller
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab08;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Lab8Controller purpose: the controller class for Lab 8
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:21 AM
 */
public class Lab8Controller {
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
     * the last effect applied to the image
     */
    private String lastEffect;
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
     * method to set the default values of variables
     * @param stage stage pulled in from the start method
     */
    public void setDefaultValues(Stage stage) {
        progressBar.setVisible(false);
        progressBar = new ProgressBar(0);
        isOriginalImage = false;
        this.stage = stage;
        fileChooser.getExtensionFilters().addAll(
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
                        IOImage.colorToString(temporaryColor));
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
    }

    /**
     * method to set the progress on the progressBar
     * @param progress the level of progress to set
     */
    public void setProgressBar(double progress) {
        progressBar.setProgress(progress);
    }

    /**
     * method to toggle the visibility of the progress bar
     */
    public void toggleProgressBarVisibility() {
        progressBar.setVisible(!progressBar.isVisible());
    }

    /**
     * changes the displayed image to grayscale
     * @param event the event that triggered the method
     */
    @FXML
    private void grayscale(ActionEvent event) {
        lastImage = imageView.getImage();
        applyEffect("grayscale");
        lastEffect = "grayscale";
    }

    /**
     * changes the displayed image to negative
     * @param event the event that triggered the method
     */
    @FXML
    private void negative(ActionEvent event) {
        lastImage = imageView.getImage();
        applyEffect("negative");
        lastEffect = "negative";
    }

    /**
     * decreases the saturation of the displayed image
     */
    @FXML
    private void decreaseSaturation() {
        lastImage = imageView.getImage();
        applyEffect("decreaseSaturation");
        lastEffect = "decreaseSaturation";
    }

    /**
     * increases the saturation of the displayed image
     */
    @FXML
    private void increaseSaturation() {
        lastImage = imageView.getImage();
        applyEffect("increaseSaturation");
        lastEffect = "increaseSaturation";
    }

    /**
     * darkens the displayed image
     */
    @FXML
    private void darken() {
        lastImage = imageView.getImage();
        applyEffect("darken");
        lastEffect = "darken";
    }

    /**
     * brightens the displayed image
     */
    @FXML
    private void brighten() {
        lastImage = imageView.getImage();
        applyEffect("brighten");
        lastEffect = "brighten";
    }

    /**
     * applies an effect based on the input string
     * grayscale, negative, darken, brighten, increaseSaturation, and decreaseSaturation
     * are excepted values of effect
     * @param effect the effect to apply
     */
    private void applyEffect(String effect) {
        try {
            Image image = imageView.getImage();
            checkNullImage(image);
            int imageWidth = (int)image.getWidth();
            int imageHeight = (int)image.getHeight();
            WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
            PixelReader reader = image.getPixelReader();
            PixelWriter writer = writableImage.getPixelWriter();
            Color temporaryColor;
            for (int height = 0; height < imageHeight; height++) {
                for (int width = 0; width < imageWidth; width++) {
                    temporaryColor = reader.getColor(width, height);
                    if (effect.equals("grayscale")) {
                        writer.setColor(width, height, temporaryColor.grayscale());
                    } else if (effect.equals("negative")) {
                        writer.setColor(width, height, temporaryColor.invert());
                    } else if (effect.equals("decreaseSaturation")) {
                        writer.setColor(width, height, temporaryColor.desaturate());
                    } else if (effect.equals("increaseSaturation")) {
                        writer.setColor(width, height, temporaryColor.saturate());
                    } else if (effect.equals("brighten")) {
                        writer.setColor(width, height, temporaryColor.brighter());
                    } else if (effect.equals("darken")) {
                        writer.setColor(width, height, temporaryColor.darker());
                    }
                }
            }
            imageView.setImage(writableImage);
        } catch (NullPointerException e) {
            IOImage.errorAlert("NullPointerException", "No image yet loaded");
        }
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
        imageView.setImage(IOImage.read(file.toPath()));
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
            Image tempImage = IOImage.read(file.toPath());
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
            IOImage.write(imageView.getImage(), file.toPath());
        }
    }

    /**
     * closes any connections that may be open and exits
     */
    @FXML
    private void close() {
        IOImage.close();
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
