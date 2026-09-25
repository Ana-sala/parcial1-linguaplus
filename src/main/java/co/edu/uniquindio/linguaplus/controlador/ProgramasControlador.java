package co.edu.uniquindio.linguaplus.controlador;

import co.edu.uniquindio.linguaplus.modelo.AcademiaLinguaPlus;
import co.edu.uniquindio.linguaplus.modelo.OfertaPeriodo;
import co.edu.uniquindio.linguaplus.modelo.ProgramaFormacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;

public class ProgramasControlador {

    @FXML private TableView<ProgramaFormacion> tblProgramas;
    @FXML private TableColumn<ProgramaFormacion, String> colCodigo;
    @FXML private TableColumn<ProgramaFormacion, String> colNombre;
    @FXML private TableColumn<ProgramaFormacion, String> colTipo;
    @FXML private TableColumn<ProgramaFormacion, String> colModalidad;
    @FXML private TableColumn<ProgramaFormacion, String> colMeses;
    @FXML private TableColumn<ProgramaFormacion, String> colValorMensual;
    @FXML private TableColumn<ProgramaFormacion, String> colValorPrograma;
    @FXML private TableColumn<ProgramaFormacion, String> colEstado;

    private AcademiaLinguaPlus academia;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getClass().getSimpleName().replace("Programa", "")));
        colModalidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getModalidad().toString()));
        colMeses.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuracionMeses())));
        colValorMensual.setCellValueFactory(c -> new SimpleStringProperty(dinero(c.getValue().getValorMensual())));
        colValorPrograma.setCellValueFactory(c -> new SimpleStringProperty(dinero(c.getValue().calcularValor())));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
    }

    /** Agrega una columna de cupos por cada periodo clonado. */
    public void inicializar(AcademiaLinguaPlus academia, List<OfertaPeriodo> ofertas) {
        this.academia = academia;
        for (OfertaPeriodo oferta : ofertas) {
            TableColumn<ProgramaFormacion, String> col = new TableColumn<>("Cupos " + oferta.getPeriodo());
            col.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(
                    oferta.buscarCupo(c.getValue().getCodigo()).getCuposDisponibles())));
            tblProgramas.getColumns().add(col);
        }
        refrescar();
    }

    public void refrescar() {
        tblProgramas.setItems(FXCollections.observableArrayList(academia.getProgramas()));
        tblProgramas.refresh();
    }

    private static String dinero(double valor) {
        return String.format("$%,.0f", valor);
    }
}