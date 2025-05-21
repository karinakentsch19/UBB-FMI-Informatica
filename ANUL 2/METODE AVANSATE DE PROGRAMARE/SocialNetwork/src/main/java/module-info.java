module ubb.ro.socialnetworkgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens ubb.ro.socialnetworkgui to javafx.fxml;
    opens ubb.ro.socialnetworkgui.Controller to javafx.fxml;
    exports ubb.ro.socialnetworkgui;
    opens ubb.ro.socialnetworkgui.Domain to java.base;
    exports ubb.ro.socialnetworkgui.Domain;
    exports ubb.ro.socialnetworkgui.Controller;
    opens ubb.ro.socialnetworkgui.DTO to java.base;
    exports ubb.ro.socialnetworkgui.DTO;
}