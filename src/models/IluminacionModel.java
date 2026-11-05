package models;

public class IluminacionModel extends EquipoModel {

    public IluminacionModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        double subtotal = getTarifaDia() * dias;

        if (dias > 5) {
            subtotal *= 0.90;
        }

        return subtotal;
    }
}