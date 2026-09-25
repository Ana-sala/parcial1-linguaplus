package co.edu.uniquindio.linguaplus.app;

import co.edu.uniquindio.linguaplus.modelo.*;
import co.edu.uniquindio.linguaplus.modelo.comprobante.*;
import co.edu.uniquindio.linguaplus.modelo.kit.*;
import java.time.LocalDate;
import java.util.List;

public class MainConsola {
    public static void main(String[] args) {
        AcademiaLinguaPlus academia = new AcademiaLinguaPlus("LinguaPlus", "900456789-1",
                "Calle 10 # 15-20, Armenia", "6067450000", "info@linguaplus.co", "www.linguaplus.co");

        Estudiante ana = new Estudiante("Ana Perez", "1001", "8589869056", "ana@mail.com", 20, LocalDate.now());
        Estudiante luis = new Estudiante("Luis Gomez", "1002", "3104567890", "luis@mail.com", 25, LocalDate.now());
        Docente tutor = new Docente("D01", "Laura Rios", "Frances", "3001112233", 50000);
        ProgramaFormacion basico = new ProgramaBasico("P01", "Ingles A1", "Ingles",
                "Nivel inicial", 3, 200000, EstadoPrograma.ACTIVO, Modalidad.PRESENCIAL);
        ProgramaFormacion intensivo = new ProgramaIntensivo("P02", "Ingles B2 Intensivo", "Ingles",
                "Preparacion rapida", 2, 350000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL);
        ProgramaFormacion personalizado = new ProgramaPersonalizado("P03", "Frances 1 a 1", "Frances",
                "Clases uno a uno", 2, 300000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL,
                8, "B1", "Viajar a Paris");
        ServicioAdicional simulacro = new ServicioAdicional("S01", "Simulacro DELF",
                "Examen de prueba", 80000, true);

        academia.registrarEstudiante(ana);
        academia.registrarEstudiante(luis);
        academia.registrarDocente(tutor);
        academia.registrarPrograma(basico);
        academia.registrarPrograma(intensivo);
        academia.registrarPrograma(personalizado);
        academia.registrarServicio(simulacro);

        // ---------- 1. Prototype ----------
        System.out.println("=== Prototype: oferta por periodo ===");
        OfertaPeriodo base = new OfertaPeriodo("base");
        base.cargarOfertaBase(academia.getProgramas(), 20);
        OfertaPeriodo p2026_1 = base.clone();
        p2026_1.setPeriodo("2026-1");
        OfertaPeriodo p2026_2 = base.clone();
        p2026_2.setPeriodo("2026-2");

        // ---------- 2. Builder + Singleton ----------
        System.out.println("=== Builder + Singleton ===");
        Matricula m1 = new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.of(2026, 2, 1)).build();
        academia.registrarMatricula(m1, p2026_1);

        Matricula m2 = new Matricula.Builder()
                .conEstudiante(luis).conPrograma(personalizado)
                .conFechaInicio(LocalDate.of(2026, 3, 1))
                .conTutor(tutor).agregarServicio(simulacro)
                .conDescuento(10).conObservaciones("Horario nocturno").build();
        academia.registrarMatricula(m2, p2026_1);

        Matricula m3 = new Matricula.Builder()
                .conEstudiante(ana).conPrograma(intensivo)
                .conFechaInicio(LocalDate.of(2026, 8, 1)).build();
        academia.registrarMatricula(m3, p2026_2);

        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);

        probar("Sin programa", () -> new Matricula.Builder()
                .conEstudiante(ana).conFechaInicio(LocalDate.now()).build());
        probar("Descuento 35%", () -> new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.now()).conDescuento(35).build());
        probar("Tutor en programa basico", () -> new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.now()).conTutor(tutor).build());

        System.out.println("\nCupos de " + basico.getNombre() + " despues de matricular en 2026-1:");
        System.out.println("  2026-1 -> " + p2026_1.buscarCupo("P01").getCuposDisponibles());
        System.out.println("  2026-2 -> " + p2026_2.buscarCupo("P01").getCuposDisponibles());
        System.out.println("  comparten la lista de cupos -> " + p2026_1.compartenCupos(p2026_2));

        // ---------- 3. Factory Method ----------
        System.out.println("\n=== Factory Method: comprobantes ===");
        List<GeneradorComprobante> generadores = List.of(new GeneradorPdf(), new GeneradorExcel());
        for (GeneradorComprobante g : generadores) {
            System.out.println(g.emitir(m2));
            System.out.println();
        }

        // ---------- 4. Abstract Factory ----------
        System.out.println("=== Abstract Factory: kits ===");
        ServicioKitBienvenida servicioKit = new ServicioKitBienvenida();
        for (Matricula m : List.of(m1, m3)) {
            FabricaKitBienvenida fabrica = m.getPrograma().getModalidad() == Modalidad.PRESENCIAL
                    ? new FabricaKitPresencial() : new FabricaKitVirtual();
            System.out.println("Kit " + m.getPrograma().getModalidad() + ":");
            System.out.println(servicioKit.prepararKit(fabrica));
        }

        // ---------- 5. Consultas ----------
        System.out.println("\n=== Consultas ===");
        for (String tel : List.of("8589869056", "3104567890")) {
            Estudiante e = academia.buscarEstudiantePorTelefono(tel);
            System.out.println(tel + " -> " + e.getNombreCompleto()
                    + " | perfecto: " + academia.esTelefonoNumeroPerfecto(tel));
        }
        System.out.printf("Ingresos 2026-1 (ene-jun): %.0f%n",
                academia.calcularIngresosPeriodo(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 30)));
        System.out.printf("Ingresos 2026-2 (jul-dic): %.0f%n",
                academia.calcularIngresosPeriodo(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 12, 31)));
    }

    private static void probar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println(caso + " -> ERROR: se creo");
        } catch (RuntimeException e) {
            System.out.println(caso + " -> rechazada: " + e.getMessage());
        }
    }
}