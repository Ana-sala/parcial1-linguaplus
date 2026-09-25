package co.edu.uniquindio.linguaplus.modelo.kit;

public class ServicioKitBienvenida {
    public String prepararKit(FabricaKitBienvenida fabrica) {
        MaterialEstudio material = fabrica.crearMaterial();
        Carnet carnet = fabrica.crearCarnet();
        return "  " + material.obtenerMaterial() + "\n  " + carnet.obtenerCarnet();
    }
}