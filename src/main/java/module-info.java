module lbgui.temper {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.osgi;



    opens lbgui.listbuilder to javafx.fxml;
    exports lbgui.listbuilder;
    exports lbgui.factories;
    opens lbgui.factories to javafx.fxml;
    exports lbgui.controllers;
    opens lbgui.controllers to javafx.fxml;
}