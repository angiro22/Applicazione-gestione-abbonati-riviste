package com.example.gestioneabbonati;

import com.example.gestioneabbonati.common.Methods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class HomeController implements Initializable {
    @FXML
    public Rectangle topBar;
    private Stage stage;
    double x = 0, y = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        topBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void closeApplication(MouseEvent event) {
        stage.close();
    }

    @FXML
    private TextField tf_code;
   // @FXML
    //private Label lbl_checkCode;

    final public static String FILE_NAME = "abbonati.txt";

    public void goToRegisterPage(ActionEvent actionEvent) throws IOException {
        boolean codeIsNaN = false;

        try {
            Integer.parseInt(tf_code.getText());
        } catch (NumberFormatException e) {
            codeIsNaN = true;
        }

        if (tf_code.getText().isEmpty()) {
            showErrorMessage("Inserire il codice abbonato!");
        } else if (codeIsNaN) {
            showErrorMessage("Il codice abbonato dev'essere numerico!");
        } else if (tf_code.getText().length() != 8) {
            showErrorMessage("La lunghezza del codice abbonato dev'essere di 8 cifre!");
        } else if (codeExists()) {
            showErrorMessage("Il codice abbonato è già esistente, se vuoi visualizzare l'abbonamento clicca su \"Visualizza\".");
        } else {
            //lbl_checkCode.setVisible(false);

            // Setting up page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUIRegister.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Registrati");
            stage.setScene(scene);

            // Setting the subscriber code
            RegisterController controller = fxmlLoader.getController();
            controller.setSubscriberCode(tf_code.getText());

            // Change page
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    public void goToViewPage(ActionEvent actionEvent) throws IOException {
        boolean codeIsNaN = false;

        try {
            Integer.parseInt(tf_code.getText());
        } catch (NumberFormatException e) {
            codeIsNaN = true;
        }

        if (tf_code.getText().isEmpty()) {
            showErrorMessage("Inserire il codice abbonato!");
        } else if (codeIsNaN) {
            showErrorMessage("Il codice abbonato dev'essere numerico!");
        } else if (tf_code.getText().length() != 8) {
            showErrorMessage("La lunghezza del codice abbonato dev'essere di 8 cifre!");
        } else if (!codeExists()) {
            showErrorMessage("Il codice abbonato non esiste, se vuoi registrarti clicca su \"Registrati\".");
        } else {
            //lbl_checkCode.setVisible(false);

            // Setting up page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUIView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Visualizza");
            stage.setScene(scene);

            // Setting the subscriber code
            ViewController controller = fxmlLoader.getController();
            controller.setSubscriberCode(tf_code.getText());
            controller.setSubscriberInfo();

            // Change page
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    private void showErrorMessage(String s) {
        //lbl_checkCode.setText(s);
        //lbl_checkCode.setVisible(true);
        Methods.sendNotification(s);
    }

    String code, magazineName, secondName, name, address, gender, city;

    private boolean codeExists() {
        try {
            // Read file
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;

                readLine(line);

                // Check for code
                if (code.equals(tf_code.getText())) return true;
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    private void readLine(String xline) {
        StringTokenizer stringTokenizer = new StringTokenizer(xline, ";");
        if (stringTokenizer.hasMoreTokens()) {
            code = stringTokenizer.nextToken();
            magazineName = stringTokenizer.nextToken();
            secondName = stringTokenizer.nextToken();
            name = stringTokenizer.nextToken();
            address = stringTokenizer.nextToken();
            gender = stringTokenizer.nextToken();
            city = stringTokenizer.nextToken();
        }
    }
}