package co.edu.uniquindio.linguaplus.controlador;

import co.edu.uniquindio.linguaplus.modelo.AcademiaLinguaPlus;
import co.edu.uniquindio.linguaplus.modelo.OfertaPeriodo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import java.util.List;

public class PrincipalControlador {

    @FXML private Label lblAcademia;
    @FXML private TabPane tabPane;
    @FXML private EstudiantesControlador estudiantesController;

    public void inicializar(AcademiaLinguaPlus academia, List<OfertaPeriodo> ofertas) {
        lblAcademia.setText("NIT " + academia.getNit() + "  ·  " + academia.getDireccion()
                + "  ·  " + academia.getWeb());
        estudiantesController.inicializar(academia);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, antes, ahora) -> refrescar());
    }

    private void refrescar() {
        estudiantesController.refrescar();
    }
}