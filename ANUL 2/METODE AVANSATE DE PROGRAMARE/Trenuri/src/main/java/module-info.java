module com.example.trenuri {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.trenuri to javafx.fxml;
    exports com.example.trenuri;

    opens com.example.trenuri.Controller to javafx.fxml;
    exports com.example.trenuri.Controller;

    opens com.example.trenuri.Domain to javafx.base;
    exports com.example.trenuri.Domain;
}