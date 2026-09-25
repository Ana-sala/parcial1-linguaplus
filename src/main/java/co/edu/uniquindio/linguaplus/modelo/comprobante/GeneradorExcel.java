package co.edu.uniquindio.linguaplus.modelo.comprobante;

public class GeneradorExcel extends GeneradorComprobante {
    @Override protected Comprobante crearComprobante() {
        return new ComprobanteExcel(); }
}