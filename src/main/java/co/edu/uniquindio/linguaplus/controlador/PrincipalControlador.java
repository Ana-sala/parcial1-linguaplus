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
    @FXML private ProgramasControlador programasController;
    @FXML private MatriculasControlador matriculasController;
    @FXML private ConsultasControlador consultasController;

    public void inicializar(AcademiaLinguaPlus academia, List<OfertaPeriodo> ofertas) {
        lblAcademia.setText("NIT " + academia.getNit() + "  ·  " + academia.getDireccion()
                + "  ·  " + academia.getWeb());
        estudiantesController.inicializar(academia);
        programasController.inicializar(academia, ofertas);
        matriculasController.inicializar(academia, ofertas);
        consultasController.inicializar(academia);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, antes, ahora) -> refrescar());
    }

    /** Al cambiar de pestana, cada una vuelve a leer el modelo. */
    private void refrescar() {
        estudiantesController.refrescar();
        programasController.refrescar();
        matriculasController.refrescar();
    }
}