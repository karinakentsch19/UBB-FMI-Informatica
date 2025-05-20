module ro.iss.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens ro.iss.biblioteca to javafx.fxml;
    exports ro.iss.biblioteca;

    opens ro.iss.biblioteca.Domain to javafx.base;
    exports ro.iss.biblioteca.Domain;

    opens ro.iss.biblioteca.Controller to javafx.fxml;
    exports ro.iss.biblioteca.Controller;

    requires java.naming;
    requires javafx.graphics;
}