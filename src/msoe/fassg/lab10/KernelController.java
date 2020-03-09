/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class KernelController
 * Name:       fassg
 * Created:    2/4/2020
 */
package msoe.fassg.lab10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * KernelController purpose: the secondary controller class for Lab10
 *
 * @author fassg
 * @version created on 2/4/2020 at 11:21 AM
 */
public class KernelController {
    /**
     * top left text field used for kernel value
     *  1 0 0
     *  0 0 0
     *  0 0 0
     */
    @FXML
    private TextField topLeftTextField;
    /**
     * top middle text field used for kernel value
     *  0 1 0
     *  0 0 0
     *  0 0 0
     */
    @FXML
    private TextField topMiddleTextField;
    /**
     * top right text field used for kernel value
     *  0 0 1
     *  0 0 0
     *  0 0 0
     */
    @FXML
    private TextField topRightTextField;
    /**
     * middle left text field used for kernel value
     *  0 0 0
     *  1 0 0
     *  0 0 0
     */
    @FXML
    private TextField middleLeftTextField;
    /**
     * middle middle text field used for kernel value
     *  0 0 0
     *  0 1 0
     *  0 0 0
     */
    @FXML
    private TextField middleMiddleTextField;
    /**
     * middle right text field used for kernel value
     *  0 0 0
     *  0 0 1
     *  0 0 0
     */
    @FXML
    private TextField middleRightTextField;
    /**
     * bottom left text field used for kernel value
     *  0 0 0
     *  0 0 0
     *  1 0 0
     */
    @FXML
    private TextField bottomLeftTextField;
    /**
     * bottom middle field used for kernel value
     *  0 0 0
     *  0 0 0
     *  0 1 0
     */
    @FXML
    private TextField bottomMiddleTextField;
    /**
     * bottom right field used for kernel value
     *  0 0 0
     *  0 0 0
     *  0 0 1
     */
    @FXML
    private TextField bottomRightTextField;
    /**
     * the other stage
     * aka window2 used for filter kernel values
     */
    private Stage otherStage;
    /**
     * the controller used for filter kernel values
     */
    private Lab10Controller otherController;
    /**
     * the length of the kernel data array
     */
    private static final int KERNEL_LENGTH = 9;
    /**
     * array to store data values for the kernel
     */
    private double[] kernel = new double[KERNEL_LENGTH];

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
    public void setOtherController(Lab10Controller otherController) {
        this.otherController = otherController;
    }

    /**
     * method to apply the kernel values to the image in
     *  the imageView in Lab9Controller
     * @param event the event that was triggered.
     */
    @FXML
    public void apply(ActionEvent event) {
        try {
            verifyKernel();
            otherController.apply(event);
        } catch (NumberFormatException e) {
            ImageIO.errorAlert("NumberFormatException",
                    "Illegal characters encountered in textField");
        } catch (IllegalArgumentException e) {
            ImageIO.errorAlert("IllegalArgumentException", e.getMessage());
        }
    }

    /**
     * method to get access to the kernel from the Lab9Controller file
     * @return the kernel array.
     */
    public double[] getKernel() {
        return kernel;
    }

    /**
     * updates the text boxes used to generate the kernel
     * data values:
     * 0 1 0
     * 1 5 1
     * 0 1 0
     * @param event the event that triggered the method
     */
    @FXML
    private void blur(ActionEvent event) {
        //http://www.jhlabs.com/ip/blurring.html
        //update kernel values in text boxes
        final double total = 9.0;
        final double middleValue = 5.0 / total;
        topLeftTextField.setText(Double.toString(0));
        topMiddleTextField.setText(Double.toString(1.0 / total));
        topRightTextField.setText(Double.toString(0));
        middleLeftTextField.setText(Double.toString(1.0 / total));
        middleMiddleTextField.setText(Double.toString(middleValue));
        middleRightTextField.setText(Double.toString(1.0 / total));
        bottomLeftTextField.setText(Double.toString(0));
        bottomMiddleTextField.setText(Double.toString(1.0 / total));
        bottomRightTextField.setText(Double.toString(0));
    }

    /**
     * updates the text boxes used to generate the kernel
     * data values:
     *  0 -1  0
     * -1  5 -1
     *  0 -1  0
     * @param event the event that triggered the method
     */
    @FXML
    private void sharpen(ActionEvent event) {
        //http://www.jhlabs.com/ip/blurring.html
        //update kernel values in text boxes
        final double middleValue = 5.0;
        topLeftTextField.setText(Double.toString(0));
        topMiddleTextField.setText(Double.toString(-1.0));
        topRightTextField.setText(Double.toString(0));
        middleLeftTextField.setText(Double.toString(-1.0));
        middleMiddleTextField.setText(Double.toString(middleValue));
        middleRightTextField.setText(Double.toString(-1.0));
        bottomLeftTextField.setText(Double.toString(0));
        bottomMiddleTextField.setText(Double.toString(-1.0));
        bottomRightTextField.setText(Double.toString(0));
    }

    /**
     * method to reset all text boxes to default values
     * @param event the event that triggered the method
     */
    @FXML
    public void reloadWindow2(ActionEvent event) {
        topLeftTextField.setText("0");
        topMiddleTextField.setText("0");
        topRightTextField.setText("0");
        middleLeftTextField.setText("0");
        middleMiddleTextField.setText("0");
        middleRightTextField.setText("0");
        bottomLeftTextField.setText("0");
        bottomMiddleTextField.setText("0");
        bottomRightTextField.setText("0");
    }

    /**
     * method to parse the text in a textField to a double
     * @param textField the text field to parse
     * @return the resultant double
     * @throws NumberFormatException if invalid input in textField
     */
    private double textFieldToDouble(TextField textField) throws NumberFormatException {
        return Double.parseDouble(textField.getText());
    }

    /**
     * method to update kernel values based on text boxes
     * @throws NumberFormatException passed from textFieldToDouble
     */
    private void updateKernel() throws NumberFormatException{
        //update kernel values from text boxes
        kernel[0] = textFieldToDouble(topLeftTextField);
        kernel[1] = textFieldToDouble(topMiddleTextField);
        kernel[2] = textFieldToDouble(topRightTextField);
        kernel[3] = textFieldToDouble(middleLeftTextField);
        kernel[4] = textFieldToDouble(middleMiddleTextField);
        kernel[KERNEL_LENGTH - 4] = textFieldToDouble(middleRightTextField);
        kernel[KERNEL_LENGTH - 3] = textFieldToDouble(bottomLeftTextField);
        kernel[KERNEL_LENGTH - 2] = textFieldToDouble(bottomMiddleTextField);
        kernel[KERNEL_LENGTH - 1] = textFieldToDouble(bottomRightTextField);
    }

    /**
     * method to verify that the kernel is valid
     *  and does not violate any of the expected rules
     * @throws IllegalArgumentException if sum of kernel values is incorrect
     * @throws NumberFormatException passed from updateKernel
     */
    private void verifyKernel() throws IllegalArgumentException, NumberFormatException {
        updateKernel();
        double sum = 0;
        final double threshold = 0.002;
        for (double x:kernel) {
            sum += x;
        }
        if (sum == 0) {
            throw new IllegalArgumentException("All values in kernel are 0");
        } else if (sum < 1 - threshold || sum > 1 + threshold) {
            throw new IllegalArgumentException("Invalid sum of values in kernel. " +
                    "Expected: 1.0. Found: " + sum);
        }
    }
}
