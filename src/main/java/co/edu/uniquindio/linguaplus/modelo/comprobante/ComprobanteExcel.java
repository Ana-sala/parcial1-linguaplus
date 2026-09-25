package co.edu.uniquindio.linguaplus.modelo.comprobante;
import co.edu.uniquindio.linguaplus.modelo.Matricula;

public class ComprobanteExcel implements Comprobante {
    @Override
    public String generar(Matricula m) {
        return "matricula;estudiante;programa;servicios;descuento;total\n"
                + m.getNumeroMatricula() + ";"
                + m.getEstudiante().getNombreCompleto() + ";"
                + m.getPrograma().getNombre() + ";"
                + String.format("%.0f;%.0f;%.0f", m.calcularValorServicios(),
                m.calcularValorDescuento(), m.calcularTotal());
    }
}