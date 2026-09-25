package co.edu.uniquindio.linguaplus.modelo;

import java.time.LocalDate;

public class Estudiante {
    private final String nombreCompleto;
    private final String identificacion;
    private final String telefono;
    private final String email;
    private final int edad;
    private final LocalDate fechaRegistro;

    public Estudiante(String nombreCompleto, String identificacion, String telefono,
                      String email, int edad, LocalDate fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.email = email;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public String getIdentificacion() { return identificacion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public int getEdad() { return edad; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    @Override
    public String toString() { return nombreCompleto + " (" + identificacion + ")"; }
}