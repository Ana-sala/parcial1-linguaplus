package co.edu.uniquindio.linguaplus.controlador;

import co.edu.uniquindio.linguaplus.modelo.*;
import co.edu.uniquindio.linguaplus.modelo.comprobante.GeneradorComprobante;
import co.edu.uniquindio.linguaplus.modelo.comprobante.GeneradorExcel;
import co.edu.uniquindio.linguaplus.modelo.comprobante.GeneradorPdf;
import co.edu.uniquindio.linguaplus.modelo.kit.FabricaKitBienvenida;
import co.edu.uniquindio.linguaplus.modelo.kit.FabricaKitPresencial;
import co.edu.uniquindio.linguaplus.modelo.kit.FabricaKitVirtual;
import co.edu.uniquindio.linguaplus.modelo.kit.ServicioKitBienvenida;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

public class MatriculasControlador {

    @FXML private ComboBox<OfertaPeriodo> cmbPeriodo;
    @FXML private ComboBox<Estudiante> cmbEstudiante;
    @FXML private ComboBox<ProgramaFormacion> cmbPrograma;
    @FXML private DatePicker dpFechaInicio;
    @FXML private ComboBox<Docente> cmbTutor;
    @FXML private ListView<ServicioAdicional> lstServicios;
    @FXML private TextField txtDescuento;
    @FXML private TextArea txtObservaciones;
    @FXML private Label lblMensaje;
    @FXML private TableView<Matricula> tblMatriculas;
    @FXML private TableColumn<Matricula, String> colNumero;
    @FXML private TableColumn<Matricula, String> colEstudiante;
    @FXML private TableColumn<Matricula, String> colPrograma;
    @FXML private TableColumn<Matricula, String> colInicio;
    @FXML private TableColumn<Matricula, String> colTutor;
    @FXML private TableColumn<Matricula, String> colTotal;
    @FXML private ComboBox<String> cmbFormato;
    @FXML private TextArea txtComprobante;
    @FXML private Label lblKit;

    private AcademiaLinguaPlus academia;
    private final ServicioKitBienvenida servicioKit = new ServicioKitBienvenida();

    @FXML
    private void initialize() {
        lstServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dpFechaInicio.setValue(LocalDate.now());
        cmbFormato.setItems(FXCollections.observableArrayList("PDF", "Excel"));
        cmbFormato.setValue("PDF");

        colNumero.setCellValueFactory(c -> new SimpleStringProperty("#" + c.getValue().getNumeroMatricula()));
        colEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstudiante().getNombreCompleto()));
        colPrograma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrograma().getNombre()));
        colInicio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaInicio().toString()));
        colTutor.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getTutor() == null ? "-" : c.getValue().getTutor().getNombre()));
        colTotal.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("$%,.0f", c.getValue().calcularTotal())));

        tblMatriculas.getSelectionModel().selectedItemProperty()
                .addListener((obs, antes, ahora) -> mostrarDetalle(ahora));
        cmbFormato.valueProperty()
                .addListener((obs, antes, ahora) -> mostrarDetalle(tblMatriculas.getSelectionModel().getSelectedItem()));
    }

    public void inicializar(AcademiaLinguaPlus academia, List<OfertaPeriodo> ofertas) {
        this.academia = academia;
        cmbPeriodo.setItems(FXCollections.observableArrayList(ofertas));
        cmbPeriodo.setValue(ofertas.get(0));
        refrescar();
    }

    public void refrescar() {
        cmbEstudiante.setItems(FXCollections.observableArrayList(academia.getEstudiantes()));
        cmbPrograma.setItems(FXCollections.observableArrayList(academia.getProgramas()));
        cmbTutor.setItems(FXCollections.observableArrayList(academia.getDocentes()));
        lstServicios.setItems(FXCollections.observableArrayList(academia.getServicios()));
        tblMatriculas.setItems(FXCollections.observableArrayList(academia.getMatriculas()));
    }

    @FXML
    private void registrarMatricula() {
        try {
            String textoDescuento = txtDescuento.getText().trim();
            double descuento = textoDescuento.isEmpty() ? 0 : Double.parseDouble(textoDescuento);

            Matricula.Builder builder = new Matricula.Builder()
                    .conEstudiante(cmbEstudiante.getValue())
                    .conPrograma(cmbPrograma.getValue())
                    .conFechaInicio(dpFechaInicio.getValue())
                    .conTutor(cmbTutor.getValue())
                    .conDescuento(descuento)
                    .conObservaciones(txtObservaciones.getText());
            for (ServicioAdicional s : lstServicios.getSelectionModel().getSelectedItems()) {
                builder.agregarServicio(s);
            }
            Matricula matricula = builder.build();
            academia.registrarMatricula(matricula, cmbPeriodo.getValue());

            mostrar("Matricula #" + matricula.getNumeroMatricula() + " registrada en "
                    + cmbPeriodo.getValue(), true);
            limpiar();
            refrescar();
            tblMatriculas.getSelectionModel().select(matricula);
        } catch (NumberFormatException e) {
            mostrar("El descuento debe ser un numero", false);
        } catch (IllegalStateException e) {
            mostrar(e.getMessage(), false);
        }
    }

    @FXML
    private void quitarTutor() {
        cmbTutor.setValue(null);
    }

    /** Factory Method (comprobante) y Abstract Factory (kit): aqui se ELIGE; el modelo crea. */
    private void mostrarDetalle(Matricula m) {
        if (m == null) {
            txtComprobante.clear();
            lblKit.setText("Seleccione una matricula");
            return;
        }
        GeneradorComprobante generador = "Excel".equals(cmbFormato.getValue())
                ? new GeneradorExcel() : new GeneradorPdf();
        txtComprobante.setText(generador.emitir(m));

        FabricaKitBienvenida fabrica = m.getPrograma().getModalidad() == Modalidad.PRESENCIAL
                ? new FabricaKitPresencial() : new FabricaKitVirtual();
        lblKit.setText("Modalidad " + m.getPrograma().getModalidad() + "\n\n" + servicioKit.prepararKit(fabrica));
    }

    private void mostrar(String texto, boolean exito) {
        lblMensaje.setText(texto);
        lblMensaje.getStyleClass().setAll(exito ? "mensaje-exito" : "mensaje-error");
    }

    private void limpiar() {
        cmbTutor.setValue(null);
        lstServicios.getSelectionModel().clearSelection();
        txtDescuento.clear();
        txtObservaciones.clear();
    }
}