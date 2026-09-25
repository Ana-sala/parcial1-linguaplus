package co.edu.uniquindio.linguaplus.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OfertaPeriodo implements Cloneable {
    private String periodo;
    private List<CupoPrograma> cupos;

    public OfertaPeriodo(String periodo) {
        this.periodo = periodo;
        this.cupos = new ArrayList<>();
    }

    /** Operacion costosa: en la realidad consulta docentes, salones y tarifas. Se hace UNA vez. */
    public void cargarOfertaBase(List<ProgramaFormacion> programas, int cuposPorPrograma) {
        for (ProgramaFormacion p : programas) {
            cupos.add(new CupoPrograma(p, "Lunes a jueves 6-8 p.m.", cuposPorPrograma));
        }
    }

    public CupoPrograma buscarCupo(String codigoPrograma) {
        for (CupoPrograma c : cupos) {
            if (c.getPrograma().getCodigo().equals(codigoPrograma)) {
                return c;
            }
        }
        throw new IllegalStateException("El programa " + codigoPrograma + " no esta ofertado en " + periodo);
    }

    /** Copia profunda: lista nueva y cada cupo clonado. */
    @Override
    public OfertaPeriodo clone() {
        OfertaPeriodo copia = new OfertaPeriodo(this.periodo);
        copia.cupos = new ArrayList<>();
        for (CupoPrograma c : this.cupos) {
            copia.cupos.add(c.clone());
        }
        return copia;
    }

    @Override
    public String toString() { return periodo; }

    public boolean compartenCupos(OfertaPeriodo otra) { return this.cupos == otra.cupos; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public List<CupoPrograma> getCupos() { return Collections.unmodifiableList(cupos); }
}