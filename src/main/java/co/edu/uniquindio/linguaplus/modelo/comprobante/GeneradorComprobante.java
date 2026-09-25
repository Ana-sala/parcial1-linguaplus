package co.edu.uniquindio.linguaplus.modelo.comprobante;
import co.edu.uniquindio.linguaplus.modelo.Matricula;

public abstract class GeneradorComprobante {
    protected abstract Comprobante crearComprobante();

    public final String emitir(Matricula matricula) {
        Comprobante comprobante = crearComprobante();
        return "=== LinguaPlus ===\n" + comprobante.generar(matricula) + "\n=== Gracias por su pago ===";
    }
}