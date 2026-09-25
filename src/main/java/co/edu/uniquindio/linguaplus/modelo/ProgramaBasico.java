package co.edu.uniquindio.linguaplus.modelo;

public class ProgramaBasico extends ProgramaFormacion {

    public ProgramaBasico(String codigo, String nombre, String idioma, String descripcion,
                          int duracionMeses, double valorMensual,
                          EstadoPrograma estado, Modalidad modalidad) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, modalidad);
    }

    @Override
    public double calcularValor() {
        return calcularValorBase();
    }
}