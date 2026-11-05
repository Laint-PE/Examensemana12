package models;

public class IluminacionModel extends EquipoModel {

    private static final int DIAS_PARA_DESCUENTO = 5;
    private static final double DESCUENTO = 0.10;

    public IluminacionModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        double subtotal = getTarifaDia() * dias;
        if (dias > DIAS_PARA_DESCUENTO) {
            subtotal *= (1 - DESCUENTO);
        }
        return subtotal;
    }

    @Override
    public String getTipo() { return "ILUMINACION"; }
}