module com.example.ofertedevacanta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.ofertedevacanta to javafx.fxml;
    exports com.example.ofertedevacanta;

    opens com.example.ofertedevacanta.Controller to javafx.fxml;
    exports com.example.ofertedevacanta.Controller;

    opens com.example.ofertedevacanta.Domain to javafx.base;
    exports com.example.ofertedevacanta.Domain;

    opens com.example.ofertedevacanta.Domain.DTO to javafx.base;
    exports com.example.ofertedevacanta.Domain.DTO;
}