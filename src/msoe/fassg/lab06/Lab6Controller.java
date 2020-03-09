/*
 * Course:     CS 1021 - 021
 * Winter 2019
 * File header contains class Controller
 * Name:       fassg
 * Created:    1/21/2020
 */
package msoe.fassg.lab06;

import edu.msoe.se1021.Lab6.WebsiteTester;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Course: CS 1021 - 021
 * Winter 2019
 * Controller purpose: the controller for the FXML files in lab 6
 *
 * @author fassg
 * @version created on 1/21/2020 at 12:33 AM
 */
public class Lab6Controller {

    //need to alt enter in the FXML file to create these over here.
    /**
     * the TextField where the URL is inputted and displayed
     */
    @FXML
    private TextField urlTextField;
    /**
     * the TextField where the size is displayed
     */
    @FXML
    private TextField sizeTextField;
    /**
     * the TextField where the download time is displayed
     */
    @FXML
    private TextField downloadTimeTextField;
    /**
     * the TextField where the port is displayed
     */
    @FXML
    private TextField portTextField;
    /**
     * the TextField where the host is displayed
     */
    @FXML
    private TextField hostTextField;
    /**
     * the TextField where the timeout is inputted and displayed
     */
    @FXML
    private TextField timeoutTextField;
    /**
     * the TextArea where the output of WebsiteTester.getContent() is shown
     */
    @FXML
    private TextArea textArea;
    /**
     * the WebsiteTester to be used to test the inputted URLs
     */
    private WebsiteTester websiteTester = new WebsiteTester();
    /**
     * the boolean to check if the code should be run again
     */
    private boolean runAgain = true;

    /**
     * runs code on startup
     * used to set the default value of the timeoutTextField
     * also disables editing of certain fields.
     */
    void setDefaultValues() {
        timeoutTextField.setText(websiteTester.getTimeout());
        sizeTextField.setEditable(false);
        hostTextField.setEditable(false);
        portTextField.setEditable(false);
        downloadTimeTextField.setEditable(false);
        textArea.setEditable(false);
    }

    /**
     * method to display an alert with the error format
     * @param header the header text to be displayed
     * @param content the content text to be displayed
     */
    private void errorAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * private method to execute the code for a socketTimeoutException
     */
    private void socketTimeoutEx(){
        //First Alert Window, Used to check if waiting should happen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Timeout Dialog");
        alert.setHeaderText("Wait Longer?");
        alert.setContentText("There has been a timeout reaching the site." +
                " Click OK to extend the timeout period?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //Second Alert Window, Used to get the new timeout
            TextInputDialog dialog = new TextInputDialog("10");
            dialog.setTitle("Set timeout");
            dialog.setHeaderText("Set extended timeout");
            dialog.setContentText("Desired timeout:");
            Optional<String> textExtendResult = dialog.showAndWait();
            if (textExtendResult.isPresent()) {
                try {
                    websiteTester.setTimeout(textExtendResult.get());
                } catch (NumberFormatException error) {
                    errorAlert("Invalid Timeout Error",
                            "Timeout must be greater than or equal to 0.");
                }
            }
            runAgain = true;
        } else {
            runAgain = false;
        }
    }

    /**
     * method to trigger whenever the analyze button is pressed
     * or whenever the enter key is pressed in the UrlTextField
     */
    @FXML
    public void analyze() {
        while (runAgain) {
            try {
                websiteTester.openURL(urlTextField.getText());
                websiteTester.openConnection();
                websiteTester.downloadText();
                hostTextField.setText(websiteTester.getHostname());
                portTextField.setText(Integer.toString(websiteTester.getPort()));
                downloadTimeTextField.setText(Long.toString(websiteTester.getDownloadTime()));
                sizeTextField.setText(Integer.toString(websiteTester.getSize()));
                timeoutTextField.setText(websiteTester.getTimeout());
                textArea.setText(websiteTester.getContent());
                runAgain = false;
            } catch (MalformedURLException e) {
                errorAlert("URL Error", "The URL entered in the text box is invalid");
                runAgain = false;
                urlTextField.setText("");
            } catch (UnknownHostException e) {
                errorAlert("Host Error",
                        "Error: Unable to reach the host " + urlTextField.getText());
                runAgain = false;
            } catch (SocketTimeoutException e) {
                socketTimeoutEx();
            } catch (ConnectException e) {
                errorAlert("Download Error", "Error: Unable to connect to the URL and Download it");
                runAgain = false;
            } catch (FileNotFoundException e) {
                errorAlert("File Error", "Error: File not found on the server, " +
                        urlTextField.getText());
                runAgain = false;
            } catch (IOException e) {
                errorAlert("IO Error", "Error: General IOException encountered");
                runAgain = false;
            }
        }
        runAgain = true;
    }

    /**
     * method to trigger whenever the set button is pressed
     * or the enter key is pressed in the timeoutTextField
     */
    @FXML
    public void setTimeout() {
        try {
            websiteTester.setTimeout(timeoutTextField.getText());
        } catch (NumberFormatException error) {
            errorAlert("Invalid Timeout Error",
                    "Timeout must be greater than or equal to 0.");
        }
    }
}
