module a.wright.wgusoftware1c482 {
    requires javafx.controls;
    requires javafx.fxml;

    opens Main to javafx.fxml;
    exports Main;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to javafx.base;
}