package com.example.gestioneabbonati;

import com.example.gestioneabbonati.common.Methods;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField tf_code;
    @FXML
    private TextField tf_magazineName;
    @FXML
    private TextField tf_secondName;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_address;
    @FXML
    private ToggleGroup tg_gender;
    @FXML
    private ComboBox<String> cb_city;
    private final String[] cities = {
            "--- Non Selezionato ---",
            "Bari",
            "BAT",
            "Brindisi",
            "Foggia",
            "Lecce",
            "Taranto"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ComboBox loading
        cb_city.getItems().addAll(cities);
        cb_city.getSelectionModel().selectFirst();

        Platform.runLater(() -> tf_code.deselect());
    }

    public void setSubscriberCode(String code) {
        tf_code.setText(code);
    }

    public void onGoBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Methods.changePageToHome(RegisterController.class, actionEvent, "GUIHome.fxml", "Home");
    }

    public void onRegisterButtonClicked(ActionEvent actionEvent) throws IOException {
        boolean isValidMagazineName = checkTextField(tf_magazineName, "nome della rivista");
        boolean isValidSecondName = checkUserInfoTextField(tf_secondName, "cognome");
        boolean isValidName = checkUserInfoTextField(tf_name, "nome");
        boolean isValidAddress = checkAddress(tf_address);
        boolean isValidGender = checkRadioButton(tg_gender, "sesso");
        boolean isValidCity = checkComboBox(cb_city, "città");

        boolean isRegistrationValid = isValidMagazineName &&
                isValidSecondName &&
                isValidName &&
                isValidAddress &&
                isValidGender &&
                isValidCity;

        if (isRegistrationValid) {
            Subscriber subscriber = new Subscriber(
                    tf_code.getText(),
                    tf_magazineName.getText(),
                    tf_secondName.getText(),
                    tf_name.getText(),
                    tf_address.getText(),
                    ((RadioButton) tg_gender.getSelectedToggle()).getText(),
                    cb_city.getValue()
            );

            FileWriter fileWriter = new FileWriter(HomeController.FILE_NAME, true);
            String record = subscriber.toString();
            fileWriter.write(record);
            fileWriter.close();

            Notifications.create()
                        .title("Successo")
                        .text("Abbonato salvato correttamente nel file " + HomeController.FILE_NAME)
                        .position(Pos.TOP_RIGHT)
                        .showConfirm();
            Methods.changePageToHome(RegisterController.class, actionEvent, "GUIHome.fxml", "Home");
        }
    }

    private boolean checkComboBox(ComboBox<String> comboBox, String fieldName) {
        // Check if is selected the first element in cities, that is a selection call
        if (comboBox.getValue().equals(cities[0])) {
            Methods.sendNotification("Devi selezionare una " + fieldName + "!");
            return false;
        }
        return true;
    }

    private boolean checkRadioButton(ToggleGroup toggleGroup, String fieldName) {
        // Check if there's nothing selected in the toggle group
        if (toggleGroup.getSelectedToggle() == null) {
            Methods.sendNotification("Devi inserire il " + fieldName + "!");
            return false;
        }
        return true;
    }

    private boolean checkTextField(TextField textField, String fieldName) {
        // Check if text field is not empty
        if (textField.getText().isEmpty()) {
            Methods.sendNotification("Il " + fieldName + " non può essere vuoto!");
            return false;
        }
        return true;
    }

    private boolean checkUserInfoTextField(TextField textField, String fieldName) {
        checkTextField(textField, fieldName);

        // Check if text field is not a number
        for(char c : textField.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                Methods.sendNotification("Il " + fieldName + " non deve contenere numeri!");
                return false;
            }
        }
        return true;
    }

    private boolean checkAddress(TextField textField) {
        // Check if address is not empty
        if (textField.getText().isEmpty()) {
            Methods.sendNotification("L'indirizzo e-mail non può essere vuoto!");
            return false;
        } else {
            // Check if address contains @
            if (!textField.getText().contains("@")) {
                Methods.sendNotification("L'indirizzo e-mail deve contenere la @");
                return false;
                // If the address doesn't contain @ do:
            } else {
                // Check that it contains only one @
                int atCounter = 0;
                for (int i = 0; i < textField.getText().length(); i++) {
                    if (textField.getText().charAt(i) == '@') atCounter++;
                }

                // If there're more @ do:
                if (atCounter > 1) {
                    Methods.sendNotification("L'indirizzo e-mail deve contenere solo una @");
                    return false;
                }

                // If address contains @ do:
                String beforeAt = textField.getText().substring(0, textField.getText().indexOf('@'));
                String afterAt = textField.getText().substring(textField.getText().indexOf('@') + 1);
                // Check if there's something before @
                if (beforeAt.isEmpty()) {
                    Methods.sendNotification("Indirizzo e-mail non valido, inserisci qualcosa prima della @");
                    return false;
                }

                // Check if there's something after @
                if (afterAt.isEmpty()) {
                    Methods.sendNotification("Indirizzo e-mail non valido, inserisci il dominio dopo la @!");
                    return false;
                    // If there's something after @ do:
                } else {
                    // Check if there's the '.' after the @
                    if (!afterAt.contains(".")) {
                        Methods.sendNotification("L'indirizzo e-mail deve contenere un '.'");
                        return false;
                        // If there's the '.' do:
                    } else {
                        String afterDot = textField.getText().substring(textField.getText().lastIndexOf('.') + 1);

                        String[] domainExtensionList = {"it", "com", "org", "net", "edu"};

                        // Check if there's something before '.'
                        if (tf_address.getText().charAt(tf_address.getText().indexOf('.') - 1) == '@') {
                            Methods.sendNotification("Indirizzo e-mail non valido, inserisci qualcosa prima del '.'");
                            return false;
                        }

                        // Check if there's something after the '.'
                        if (afterDot.isEmpty()) {
                            Methods.sendNotification("Indirizzo e-mail non valido, inserisci un'estensione del dominio");
                            return false;
                            // If there's something after the '.' do:
                        } else {
                            for (int i = 0; i < domainExtensionList.length; i++) {
                                // Check if the extension exists
                                if (afterDot.equalsIgnoreCase(domainExtensionList[i])) {
                                    return true;
                                    // If the extension doesn't exist do:
                                }
                            }
                            Methods.sendNotification("L'estensione del dominio inserita non esiste!");
                            return false;
                        }
                    }
                }
            }
        }
    }
}
