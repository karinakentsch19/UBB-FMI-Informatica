module com.example.zboruri {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.zboruri to javafx.fxml;
    exports com.example.zboruri;

    opens com.example.zboruri.Controller to javafx.fxml;
    exports com.example.zboruri.Controller;

    opens com.example.zboruri.Domain to javafx.base;
    exports com.example.zboruri.Domain;
}