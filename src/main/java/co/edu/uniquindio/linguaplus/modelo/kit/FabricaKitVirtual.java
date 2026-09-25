package co.edu.uniquindio.linguaplus.modelo.kit;

public class FabricaKitVirtual implements FabricaKitBienvenida {
    @Override public MaterialEstudio crearMaterial() {
        return new LicenciaPlataforma(); }
    @Override public Carnet crearCarnet() {
        return new CarnetDigital(); }
}