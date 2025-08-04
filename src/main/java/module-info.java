module espol.tresenrayafxg3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.graphics;
    requires javafx.base;

    opens espol.tresenrayafxg3 to javafx.fxml;
    exports espol.tresenrayafxg3;
    opens espol.tresenrayafxg3.controllers to javafx.fxml;
    exports espol.tresenrayafxg3.controllers;
}
