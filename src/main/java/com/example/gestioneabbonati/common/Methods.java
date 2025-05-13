package com.example.gestioneabbonati.common;

import com.example.gestioneabbonati.HomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class Methods {
    public static void changePageToHome(Class<?> caller, ActionEvent actionEvent, String pageResource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(caller.getResource(pageResource));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);

        HomeController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public static void sendNotification(String message) {
        Notifications notification = Notifications.create()
                .title("Errore")
                .text(message)
                .position(Pos.TOP_RIGHT);

        notification.showError();
    }
}
