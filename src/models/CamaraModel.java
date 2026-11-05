package models;

public class CamaraModel extends EquipoModel {

    public CamaraModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        double subtotal = getTarifaDia() * dias;
        return subtotal * 1.15;
    }
}