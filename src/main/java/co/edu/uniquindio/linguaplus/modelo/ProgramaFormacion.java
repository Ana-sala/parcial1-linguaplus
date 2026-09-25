package co.edu.uniquindio.linguaplus.modelo;

public abstract class ProgramaFormacion {
    private final String codigo;
    private final String nombre;
    private final String idioma;
    private final String descripcion;
    private final int duracionMeses;
    private final double valorMensual;
    private EstadoPrograma estado;
    private final Modalidad modalidad;

    protected ProgramaFormacion(String codigo, String nombre, String idioma, String descripcion,
                                int duracionMeses, double valorMensual,
                                EstadoPrograma estado, Modalidad modalidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
        this.modalidad = modalidad;
    }

    /** Valor común a todos: valor mensual por meses de duración. */
    protected double calcularValorBase() {
        return valorMensual * duracionMeses;
    }

    /** Cada tipo de programa decide su valor final. */
    public abstract double calcularValor();

    /** Por defecto un programa no admite tutor (RN-09). */
    public boolean puedeTenerTutor() { return false; }

    /** Por defecto no hay costo de tutoría. */
    public double calcularCostoTutoria(Docente tutor) { return 0; }

    public boolean estaActivo() { return estado == EstadoPrograma.ACTIVO; }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getIdioma() { return idioma; }
    public String getDescripcion() { return descripcion; }
    public int getDuracionMeses() { return duracionMeses; }
    public double getValorMensual() { return valorMensual; }
    public EstadoPrograma getEstado() { return estado; }
    public void setEstado(EstadoPrograma estado) { this.estado = estado; }
    public Modalidad getModalidad() { return modalidad; }

    @Override
    public String toString() { return codigo + " - " + nombre + " (" + modalidad + ")"; }
}