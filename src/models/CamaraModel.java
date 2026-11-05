package models;

public class CamaraModel extends EquipoModel {

    private static final double SEGURO = 0.15;

    public CamaraModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        return getTarifaDia() * dias * (1 + SEGURO);
    }

    @Override
    public String getTipo() { return "CAMARA"; }
}