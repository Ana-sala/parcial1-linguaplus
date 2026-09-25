package co.edu.uniquindio.linguaplus.modelo.kit;
public class FabricaKitPresencial implements FabricaKitBienvenida {

    @Override public MaterialEstudio crearMaterial() {
        return new MaterialImpreso(); }
    @Override public Carnet crearCarnet() {
        return new CarnetFisico(); }
}