module com.example.gestioneabbonati {
    requires javafx.fxml;
    requires java.management;
    requires org.controlsfx.controls;
    requires java.desktop;


    opens com.example.gestioneabbonati to javafx.fxml;
    exports com.example.gestioneabbonati;
}