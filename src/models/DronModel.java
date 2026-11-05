
package models;

public class DronModel extends EquipoModel {

    public DronModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        return getTarifaDia() * dias;
    }

    @Override
    public String getTipo() {
        return "DRON";
    }
}
