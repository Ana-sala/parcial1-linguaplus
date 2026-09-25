package co.edu.uniquindio.linguaplus.app;

import co.edu.uniquindio.linguaplus.modelo.*;
import java.time.LocalDate;

public class MainConsola {
    public static void main(String[] args) {
        Estudiante ana = new Estudiante("Ana Perez", "1001", "8589869056",
                "ana@mail.com", 20, LocalDate.now());
        Docente tutor = new Docente("D01", "Laura Rios", "Ingles", "3001112233", 50000);
        ProgramaFormacion basico = new ProgramaBasico("P01", "Ingles A1", "Ingles",
                "Nivel inicial", 3, 200000, EstadoPrograma.ACTIVO, Modalidad.PRESENCIAL);
        ProgramaFormacion personalizado = new ProgramaPersonalizado("P03", "Frances 1a1",
                "Frances", "Clases uno a uno", 2, 300000, EstadoPrograma.ACTIVO,
                Modalidad.VIRTUAL, 8, "B1", "Viajar a Paris");
        ServicioAdicional simulacro = new ServicioAdicional("S01", "Simulacro TOEFL",
                "Examen de prueba", 80000, true);

        System.out.println("=== Builder + Singleton ===");
        Matricula m1 = new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.of(2026, 2, 1))
                .build();
        Matricula m2 = new Matricula.Builder()
                .conEstudiante(ana).conPrograma(personalizado)
                .conFechaInicio(LocalDate.of(2026, 8, 1))
                .conTutor(tutor).agregarServicio(simulacro)
                .conDescuento(10).conObservaciones("Horario nocturno")
                .build();
        System.out.println(m1);
        System.out.println(m2);

        probar("Sin programa", () -> new Matricula.Builder()
                .conEstudiante(ana).conFechaInicio(LocalDate.now()).build());
        probar("Descuento 35%", () -> new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.now()).conDescuento(35).build());
        probar("Tutor en programa basico", () -> new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.now()).conTutor(tutor).build());

        Matricula m3 = new Matricula.Builder()
                .conEstudiante(ana).conPrograma(basico)
                .conFechaInicio(LocalDate.now()).build();
        System.out.println("Siguiente valida -> #" + m3.getNumeroMatricula()
                + " (los rechazos no gastaron numero)");
    }

    private static void probar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println(caso + " -> ERROR: se creo");
        } catch (IllegalStateException e) {
            System.out.println(caso + " -> rechazada: " + e.getMessage());
        }
    }
}