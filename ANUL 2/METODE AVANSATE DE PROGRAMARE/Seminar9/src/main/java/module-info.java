module com.example.sem7.seminar9 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.sem7.seminar9 to javafx.fxml;
    exports com.example.sem7.seminar9;

    opens com.example.sem7.seminar9.controllers to javafx.fxml;
    opens com.example.sem7.seminar9.domain to javafx.fxml;

    exports com.example.sem7.seminar9.domain;
    exports  com.example.sem7.seminar9.controllers;
}