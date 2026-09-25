package co.edu.uniquindio.linguaplus.app;

import co.edu.uniquindio.linguaplus.modelo.*;
import java.time.LocalDate;
import java.util.List;

public final class DatosIniciales {

    private DatosIniciales() { }

    public static AcademiaLinguaPlus crearAcademia() {
        AcademiaLinguaPlus academia = new AcademiaLinguaPlus("LinguaPlus", "900456789-1",
                "Calle 10 # 15-20, Armenia", "6067450000", "info@linguaplus.co", "www.linguaplus.co");

        academia.registrarPrograma(new ProgramaBasico("P01", "Ingles A1", "Ingles",
                "Nivel inicial", 3, 200000, EstadoPrograma.ACTIVO, Modalidad.PRESENCIAL));
        academia.registrarPrograma(new ProgramaIntensivo("P02", "Ingles B2 Intensivo", "Ingles",
                "Preparacion rapida", 2, 350000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL));
        academia.registrarPrograma(new ProgramaPersonalizado("P03", "Frances 1 a 1", "Frances",
                "Clases uno a uno", 2, 300000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL,
                8, "B1", "Viajar a Paris"));
        academia.registrarPrograma(new ProgramaBasico("P04", "Aleman A1", "Aleman",
                "Nivel inicial", 4, 220000, EstadoPrograma.SUSPENDIDO, Modalidad.PRESENCIAL));

        academia.registrarEstudiante(new Estudiante("Ana Perez", "1001", "8589869056",
                "ana@mail.com", 20, LocalDate.of(2026, 1, 15)));
        academia.registrarEstudiante(new Estudiante("Luis Gomez", "1002", "3104567890",
                "luis@mail.com", 25, LocalDate.of(2026, 1, 20)));

        academia.registrarDocente(new Docente("D01", "Laura Rios", "Frances", "3001112233", 50000));
        academia.registrarDocente(new Docente("D02", "Mark Smith", "Ingles", "3005556677", 45000));

        academia.registrarServicio(new ServicioAdicional("S01", "Examen de nivelacion",
                "Prueba de ubicacion", 50000, true));
        academia.registrarServicio(new ServicioAdicional("S02", "Simulacro de certificacion",
                "Examen de prueba internacional", 80000, true));
        academia.registrarServicio(new ServicioAdicional("S03", "Taller de pronunciacion",
                "Taller especial", 40000, false));
        return academia;
    }

    /** Prototype: la oferta base se arma una vez y cada periodo es un clon. */
    public static List<OfertaPeriodo> crearOfertas(AcademiaLinguaPlus academia) {
        OfertaPeriodo base = new OfertaPeriodo("base");
        base.cargarOfertaBase(academia.getProgramas(), 20);
        OfertaPeriodo p1 = base.clone();
        p1.setPeriodo("2026-1");
        OfertaPeriodo p2 = base.clone();
        p2.setPeriodo("2026-2");
        return List.of(p1, p2);
    }
}