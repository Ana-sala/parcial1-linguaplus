package co.edu.uniquindio.linguaplus.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matricula {
    private static final double DESCUENTO_MAXIMO = 30.0;

    private final int numeroMatricula;
    private final Estudiante estudiante;
    private final ProgramaFormacion programa;
    private final LocalDate fechaInicio;
    private final Docente tutor;
    private final List<ServicioAdicional> servicios;
    private final double descuento;          // porcentaje: 0 a 30
    private final String observaciones;

    private Matricula(Builder b) {
        this.estudiante = b.estudiante;
        this.programa = b.programa;
        this.fechaInicio = b.fechaInicio;
        this.tutor = b.tutor;
        this.servicios = List.copyOf(b.servicios);
        this.descuento = b.descuento;
        this.observaciones = b.observaciones;
        this.numeroMatricula = ConsecutivoMatricula.getInstancia().siguiente();
    }

    public double calcularValorServicios() {
        double total = 0;
        for (ServicioAdicional s : servicios) {
            total += s.getPrecio();
        }
        return total;
    }

    public double calcularValorDescuento() {
        return programa.calcularValor() * descuento / 100;
    }

    /** Valor final: programa + tutoría + servicios - descuento. */
    public double calcularTotal() {
        return programa.calcularValor()
                + programa.calcularCostoTutoria(tutor)
                + calcularValorServicios()
                - calcularValorDescuento();
    }

    public int getNumeroMatricula() { return numeroMatricula; }
    public Estudiante getEstudiante() { return estudiante; }
    public ProgramaFormacion getPrograma() { return programa; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public Docente getTutor() { return tutor; }
    public List<ServicioAdicional> getServicios() { return servicios; }
    public double getDescuento() { return descuento; }
    public String getObservaciones() { return observaciones; }

    @Override
    public String toString() {
        return "Matricula #" + numeroMatricula + " | " + estudiante.getNombreCompleto()
                + " | " + programa.getNombre() + " | inicio " + fechaInicio
                + (tutor != null ? " | tutor " + tutor.getNombre() : "")
                + " | total $" + String.format("%.0f", calcularTotal());
    }

    public static class Builder {
        private Estudiante estudiante;
        private ProgramaFormacion programa;
        private LocalDate fechaInicio;
        private Docente tutor;
        private final List<ServicioAdicional> servicios = new ArrayList<>();
        private double descuento = 0;
        private String observaciones;

        public Builder conEstudiante(Estudiante e) { this.estudiante = e; return this; }
        public Builder conPrograma(ProgramaFormacion p) { this.programa = p; return this; }
        public Builder conFechaInicio(LocalDate f) { this.fechaInicio = f; return this; }
        public Builder conTutor(Docente t) { this.tutor = t; return this; }
        public Builder agregarServicio(ServicioAdicional s) { this.servicios.add(s); return this; }
        public Builder conDescuento(double d) { this.descuento = d; return this; }
        public Builder conObservaciones(String o) { this.observaciones = o; return this; }

        public Matricula build() {
            if (estudiante == null) {
                throw new IllegalStateException("La matricula requiere un estudiante");
            }
            if (programa == null) {
                throw new IllegalStateException("La matricula requiere un programa");
            }
            if (fechaInicio == null) {
                throw new IllegalStateException("La matricula requiere una fecha de inicio");
            }
            if (!programa.estaActivo()) {
                throw new IllegalStateException("El programa no esta activo");
            }
            if (descuento < 0 || descuento > DESCUENTO_MAXIMO) {
                throw new IllegalStateException("El descuento no puede superar el 30%");
            }
            if (tutor != null && !programa.puedeTenerTutor()) {
                throw new IllegalStateException("Solo los programas personalizados llevan tutor");
            }
            for (ServicioAdicional s : servicios) {
                if (!s.isDisponible()) {
                    throw new IllegalStateException("Servicio no disponible: " + s.getNombre());
                }
            }
            return new Matricula(this);
        }
    }
}