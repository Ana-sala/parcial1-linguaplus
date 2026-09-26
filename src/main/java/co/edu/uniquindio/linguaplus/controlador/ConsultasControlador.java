package co.edu.uniquindio.linguaplus.controlador;

import co.edu.uniquindio.linguaplus.modelo.AcademiaLinguaPlus;
import co.edu.uniquindio.linguaplus.modelo.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class ConsultasControlador {

    @FXML private TextField txtTelefono;
    @FXML private Label lblEstudiante;
    @FXML private Label lblPerfecto;
    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private Label lblIngresos;
    @FXML private Label lblMensaje;

    private AcademiaLinguaPlus academia;

    @FXML
    private void initialize() {
        dpDesde.setValue(LocalDate.of(2026, 1, 1));
        dpHasta.setValue(LocalDate.of(2026, 12, 31));
    }

    public void inicializar(AcademiaLinguaPlus academia) {
        this.academia = academia;
    }

    @FXML
    private void buscarEstudiante() {
        String telefono = txtTelefono.getText().trim();
        try {
            Estudiante e = academia.buscarEstudiantePorTelefono(telefono);
            lblEstudiante.setText(e.getNombreCompleto() + "  ·  Doc. " + e.getIdentificacion()
                    + "  ·  " + e.getEmail());
            boolean perfecto = academia.esTelefonoNumeroPerfecto(telefono);
            lblPerfecto.setText(perfecto
                    ? "El telefono " + telefono + " SI es un numero perfecto"
                    : "El telefono " + telefono + " NO es un numero perfecto");
            lblPerfecto.getStyleClass().setAll(perfecto ? "insignia-si" : "insignia-no");
        } catch (IllegalStateException ex) {
            lblEstudiante.setText("");
            lblPerfecto.setText(ex.getMessage());
            lblPerfecto.getStyleClass().setAll("insignia-no");
        }
    }

    @FXML
    private void calcularIngresos() {
        try {
            double total = academia.calcularIngresosPeriodo(dpDesde.getValue(), dpHasta.getValue());
            lblIngresos.setText(String.format("$%,.0f", total));
            lblMensaje.setText("Del " + dpDesde.getValue() + " al " + dpHasta.getValue());
            lblMensaje.getStyleClass().setAll("nota");
        } catch (IllegalStateException ex) {
            lblIngresos.setText("");
            lblMensaje.setText(ex.getMessage());
            lblMensaje.getStyleClass().setAll("mensaje-error");
        }
    }
}