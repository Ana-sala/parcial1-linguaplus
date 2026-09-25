package co.edu.uniquindio.linguaplus.modelo.comprobante;
import co.edu.uniquindio.linguaplus.modelo.Matricula;

public class ComprobantePdf implements Comprobante {
    @Override
    public String generar(Matricula m) {
        return "[PDF] Comprobante de pago\n"
                + "Matricula: #" + m.getNumeroMatricula() + "\n"
                + "Estudiante: " + m.getEstudiante().getNombreCompleto() + "\n"
                + "Programa: " + m.getPrograma().getNombre() + "\n"
                + String.format("Valor programa: %.0f%n", m.getPrograma().calcularValor())
                + String.format("Tutoria: %.0f%n", m.getPrograma().calcularCostoTutoria(m.getTutor()))
                + String.format("Servicios: %.0f%n", m.calcularValorServicios())
                + String.format("Descuento: -%.0f%n", m.calcularValorDescuento())
                + String.format("TOTAL: %.0f", m.calcularTotal());
    }
}