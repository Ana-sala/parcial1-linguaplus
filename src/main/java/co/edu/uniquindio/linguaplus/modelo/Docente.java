package co.edu.uniquindio.linguaplus.modelo;

public class Docente {
    private final String identificacion;
    private final String nombre;
    private final String idiomaEspecialidad;
    private final String telefono;
    private final double tarifaPorSesion;

    public Docente(String identificacion, String nombre, String idiomaEspecialidad,
                   String telefono, double tarifaPorSesion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.idiomaEspecialidad = idiomaEspecialidad;
        this.telefono = telefono;
        this.tarifaPorSesion = tarifaPorSesion;
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getIdiomaEspecialidad() { return idiomaEspecialidad; }
    public String getTelefono() { return telefono; }
    public double getTarifaPorSesion() { return tarifaPorSesion; }

    @Override
    public String toString() { return nombre + " - " + idiomaEspecialidad; }
}