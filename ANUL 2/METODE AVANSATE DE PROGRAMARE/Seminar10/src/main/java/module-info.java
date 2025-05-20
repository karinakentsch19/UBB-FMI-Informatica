module com.example.seminar10 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.seminar10 to javafx.fxml;
    exports com.example.seminar10;
}