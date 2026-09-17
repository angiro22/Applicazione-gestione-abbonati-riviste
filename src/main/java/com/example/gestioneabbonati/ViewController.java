package com.example.gestioneabbonati;

import com.example.gestioneabbonati.common.DatabaseManager;
import com.example.gestioneabbonati.common.Methods;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    public TextField tf_code;
    @FXML
    public TextField tf_magazineName;
    @FXML
    public TextField tf_secondName;
    @FXML
    public TextField tf_name;
    @FXML
    public TextField tf_address;
    @FXML
    public ToggleGroup tg_gender;
    @FXML
    public TextField tf_city;

    ArrayList<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> tf_code.deselect());
    }

    public void setSubscriberCode(String code) {
        tf_code.setText(code);
    }

    public void setSubscriberInfo() {
        Subscriber subscriber = DatabaseManager.getSubscriber(tf_code.getText());

        if (subscriber != null) {
            setTextField(tf_magazineName, subscriber.getMagazineName());
            setTextField(tf_secondName, subscriber.getSecondName());
            setTextField(tf_name, subscriber.getName());
            setTextField(tf_address, subscriber.getAddress());
            setRadioButton(tg_gender, subscriber.getGender());
            setTextField(tf_city, subscriber.getCity());
        }
    }

    public void onGoBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Methods.changePageToHome(RegisterController.class, actionEvent, "GUIHome.fxml", "Home");
    }

    private void setRadioButton(ToggleGroup toggleGroup, String genderSelected) {
        List<Toggle> genders = toggleGroup.getToggles();
        for (Toggle gender : genders) {
            RadioButton rb_gender = (RadioButton) gender;
            if (rb_gender.getText().equals(genderSelected)) {
                rb_gender.setSelected(true);
                break;
            }
        }
    }

    private void setTextField(TextField textField, String value) {
        textField.setText(value);
    }
}
