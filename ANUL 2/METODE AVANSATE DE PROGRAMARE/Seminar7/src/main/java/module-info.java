module ro.ubbcluj.cs.map.seminar7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ro.ubbcluj.cs.map.seminar7 to javafx.fxml;
    exports ro.ubbcluj.cs.map.seminar7;
    exports ro.ubbcluj.cs.map.seminar7.controller;
    opens ro.ubbcluj.cs.map.seminar7.controller;
    opens ro.ubbcluj.cs.map.seminar7.domain to javafx.base;
}