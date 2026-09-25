package co.edu.uniquindio.linguaplus.modelo.comprobante;

public class GeneradorPdf extends GeneradorComprobante {

    @Override protected Comprobante crearComprobante() { return new ComprobantePdf(); }
}