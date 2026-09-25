package co.edu.uniquindio.linguaplus.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AcademiaLinguaPlus {
    private final String nombre;
    private final String nit;
    private final String direccion;
    private final String telefono;
    private final String correo;
    private final String web;
    private final List<Estudiante> estudiantes = new ArrayList<>();
    private final List<Docente> docentes = new ArrayList<>();
    private final List<ProgramaFormacion> programas = new ArrayList<>();
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private final List<Matricula> matriculas = new ArrayList<>();

    public AcademiaLinguaPlus(String nombre, String nit, String direccion,
                              String telefono, String correo, String web) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.web = web;
    }

    public void registrarEstudiante(Estudiante e) {
        if (e.getNombreCompleto() == null || e.getNombreCompleto().isBlank()) {
            throw new IllegalStateException("El estudiante requiere un nombre");
        }
        if (e.getIdentificacion() == null || e.getIdentificacion().isBlank()) {
            throw new IllegalStateException("El estudiante requiere un documento");
        }
        if (e.getTelefono() == null || e.getTelefono().isBlank()) {
            throw new IllegalStateException("El estudiante requiere un telefono");
        }
        for (Estudiante existente : estudiantes) {
            if (existente.getIdentificacion().equals(e.getIdentificacion())) {
                throw new IllegalStateException("Ya existe un estudiante con documento " + e.getIdentificacion());
            }
        }
        estudiantes.add(e);
    }
    public void registrarDocente(Docente d) { docentes.add(d); }
    public void registrarPrograma(ProgramaFormacion p) { programas.add(p); }
    public void registrarServicio(ServicioAdicional s) { servicios.add(s); }

    /** Ocupa el cupo en la oferta del periodo (RN-06) y guarda la matricula. */
    public void registrarMatricula(Matricula m, OfertaPeriodo oferta) {
        if (oferta == null) {
            throw new IllegalStateException("Seleccione el periodo de la oferta");
        }
        oferta.buscarCupo(m.getPrograma().getCodigo()).ocuparCupo();
        matriculas.add(m);
    }

    public Estudiante buscarEstudiantePorTelefono(String telefono) {
        for (Estudiante e : estudiantes) {
            if (e.getTelefono().equals(telefono)) {
                return e;
            }
        }
        throw new IllegalStateException("No existe un estudiante con telefono " + telefono);
    }

    public boolean esTelefonoNumeroPerfecto(String telefono) {
        String soloDigitos = telefono.replaceAll("\\D", "");
        if (soloDigitos.isEmpty()) {
            return false;
        }
        return esNumeroPerfecto(Long.parseLong(soloDigitos));
    }

    /** Suma los divisores propios recorriendo solo hasta la raiz cuadrada. */
    private boolean esNumeroPerfecto(long n) {
        if (n < 2) {
            return false;
        }
        long suma = 1;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                suma += i;
                long pareja = n / i;
                if (pareja != i) {
                    suma += pareja;
                }
            }
        }
        return suma == n;
    }

    public double calcularIngresosPeriodo(LocalDate inicio, LocalDate fin) {
        double total = 0;
        for (Matricula m : matriculas) {
            LocalDate f = m.getFechaInicio();
            if (!f.isBefore(inicio) && !f.isAfter(fin)) {
                total += m.calcularTotal();
            }
        }
        return total;
    }

    public List<Estudiante> getEstudiantes() { return Collections.unmodifiableList(estudiantes); }
    public List<Docente> getDocentes() { return Collections.unmodifiableList(docentes); }
    public List<ProgramaFormacion> getProgramas() { return Collections.unmodifiableList(programas); }
    public List<ServicioAdicional> getServicios() { return Collections.unmodifiableList(servicios); }
    public List<Matricula> getMatriculas() { return Collections.unmodifiableList(matriculas); }
    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getWeb() { return web; }
}