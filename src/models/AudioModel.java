package models;

public class AudioModel extends EquipoModel {

    private static final int DIAS_MINIMOS = 2;

    public AudioModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        return getTarifaDia() * Math.max(dias, DIAS_MINIMOS);
    }

    @Override
    public String getTipo() { return "AUDIO"; }
}