package co.edu.uniquindio.linguaplus.controlador;

import co.edu.uniquindio.linguaplus.modelo.AcademiaLinguaPlus;
import co.edu.uniquindio.linguaplus.modelo.Estudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class EstudiantesControlador {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;
    @FXML private Label lblMensaje;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colDocumento;
    @FXML private TableColumn<Estudiante, String> colTelefono;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, String> colEdad;
    @FXML private TableColumn<Estudiante, String> colFecha;

    private AcademiaLinguaPlus academia;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreCompleto()));
        colDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colEdad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEdad())));
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaRegistro().toString()));
    }

    public void inicializar(AcademiaLinguaPlus academia) {
        this.academia = academia;
        refrescar();
    }

    public void refrescar() {
        tblEstudiantes.setItems(FXCollections.observableArrayList(academia.getEstudiantes()));
    }

    @FXML
    private void registrarEstudiante() {
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            Estudiante estudiante = new Estudiante(txtNombre.getText().trim(), txtDocumento.getText().trim(),
                    txtTelefono.getText().trim(), txtCorreo.getText().trim(), edad, LocalDate.now());
            academia.registrarEstudiante(estudiante);
            mostrar("Estudiante registrado: " + estudiante.getNombreCompleto(), true);
            limpiar();
            refrescar();
        } catch (NumberFormatException e) {
            mostrar("La edad debe ser un numero", false);
        } catch (IllegalStateException e) {
            mostrar(e.getMessage(), false);
        }
    }

    private void mostrar(String texto, boolean exito) {
        lblMensaje.setText(texto);
        lblMensaje.getStyleClass().setAll(exito ? "mensaje-exito" : "mensaje-error");
    }

    private void limpiar() {
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
    }
}