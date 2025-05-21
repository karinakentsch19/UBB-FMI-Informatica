module com.example.comenzirestaurant {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.comenzirestaurant to javafx.fxml;
    exports com.example.comenzirestaurant;

    opens com.example.comenzirestaurant.Domain to javafx.base;
    exports com.example.comenzirestaurant.Domain;

    opens com.example.comenzirestaurant.Domain.DTO to javafx.base;
    exports com.example.comenzirestaurant.Domain.DTO;

    opens com.example.comenzirestaurant.Controller to javafx.fxml;
    exports com.example.comenzirestaurant.Controller;
}