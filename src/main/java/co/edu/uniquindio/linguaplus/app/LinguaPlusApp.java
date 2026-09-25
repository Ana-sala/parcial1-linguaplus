package co.edu.uniquindio.linguaplus.app;

import co.edu.uniquindio.linguaplus.controlador.PrincipalControlador;
import co.edu.uniquindio.linguaplus.modelo.AcademiaLinguaPlus;
import co.edu.uniquindio.linguaplus.modelo.OfertaPeriodo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

public class LinguaPlusApp extends Application {

    private static final String RUTA_VISTA = "/co/edu/uniquindio/linguaplus/vista/";

    @Override
    public void start(Stage stage) throws Exception {
        AcademiaLinguaPlus academia = DatosIniciales.crearAcademia();
        List<OfertaPeriodo> ofertas = DatosIniciales.crearOfertas(academia);

        FXMLLoader loader = new FXMLLoader(LinguaPlusApp.class.getResource(RUTA_VISTA + "principal-view.fxml"));
        Parent root = loader.load();
        PrincipalControlador controlador = loader.getController();
        controlador.inicializar(academia, ofertas);

        Scene scene = new Scene(root, 1150, 720);
        scene.getStylesheets().add(LinguaPlusApp.class.getResource(RUTA_VISTA + "estilos.css").toExternalForm());
        stage.setTitle("LinguaPlus - Gestion academica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}