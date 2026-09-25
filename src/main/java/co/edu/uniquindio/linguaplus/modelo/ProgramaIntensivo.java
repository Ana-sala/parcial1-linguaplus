package co.edu.uniquindio.linguaplus.modelo;

public class ProgramaIntensivo extends ProgramaFormacion {
    private static final double RECARGO_INTENSIVO = 0.20;

    public ProgramaIntensivo(String codigo, String nombre, String idioma, String descripcion,
                             int duracionMeses, double valorMensual,
                             EstadoPrograma estado, Modalidad modalidad) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, modalidad);
    }

    @Override
    public double calcularValor() {
        return calcularValorBase() * (1 + RECARGO_INTENSIVO);
    }
}