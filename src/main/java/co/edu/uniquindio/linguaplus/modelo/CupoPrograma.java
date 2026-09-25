package co.edu.uniquindio.linguaplus.modelo;

public class CupoPrograma implements Cloneable {
    private final ProgramaFormacion programa;
    private final String horario;
    private int cuposDisponibles;

    public CupoPrograma(ProgramaFormacion programa, String horario, int cuposDisponibles) {
        this.programa = programa;
        this.horario = horario;
        this.cuposDisponibles = cuposDisponibles;
    }

    public boolean hayCupos() { return cuposDisponibles > 0; }

    public void ocuparCupo() {
        if (cuposDisponibles == 0) {
            throw new IllegalStateException("Sin cupos disponibles: " + programa.getNombre());
        }
        cuposDisponibles--;
    }

    @Override
    public CupoPrograma clone() {
        return new CupoPrograma(programa, horario, cuposDisponibles);
    }

    public ProgramaFormacion getPrograma() { return programa; }
    public String getHorario() { return horario; }
    public int getCuposDisponibles() { return cuposDisponibles; }
}