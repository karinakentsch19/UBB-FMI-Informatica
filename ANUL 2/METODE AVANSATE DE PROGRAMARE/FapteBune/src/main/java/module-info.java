module com.example.faptebune {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.faptebune to javafx.fxml;
    exports com.example.faptebune;

    opens com.example.faptebune.Controller to javafx.fxml;
    exports com.example.faptebune.Controller;

    opens com.example.faptebune.Domain to javafx.base;
    exports com.example.faptebune.Domain;
}