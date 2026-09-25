module co.edu.uniquindio.linguaplus {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.linguaplus.controlador to javafx.fxml;
    exports co.edu.uniquindio.linguaplus.app;
}