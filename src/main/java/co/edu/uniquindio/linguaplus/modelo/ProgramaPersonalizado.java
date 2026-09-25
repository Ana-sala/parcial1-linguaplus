package co.edu.uniquindio.linguaplus.modelo;

public class ProgramaPersonalizado extends ProgramaFormacion {
    private static final double RECARGO_PERSONALIZADO = 0.30;
    private final int sesionesTutor;
    private final String nivelRequerido;
    private final String objetivos;

    public ProgramaPersonalizado(String codigo, String nombre, String idioma, String descripcion,
                                 int duracionMeses, double valorMensual,
                                 EstadoPrograma estado, Modalidad modalidad,
                                 int sesionesTutor, String nivelRequerido, String objetivos) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, modalidad);
        this.sesionesTutor = sesionesTutor;
        this.nivelRequerido = nivelRequerido;
        this.objetivos = objetivos;
    }

    @Override
    public double calcularValor() {
        return calcularValorBase() * (1 + RECARGO_PERSONALIZADO);
    }

    @Override
    public boolean puedeTenerTutor() { return true; }

    /** Sesiones contratadas por la tarifa del docente asignado. */
    @Override
    public double calcularCostoTutoria(Docente tutor) {
        return tutor == null ? 0 : sesionesTutor * tutor.getTarifaPorSesion();
    }

    public int getSesionesTutor() { return sesionesTutor; }
    public String getNivelRequerido() { return nivelRequerido; }
    public String getObjetivos() { return objetivos; }
}