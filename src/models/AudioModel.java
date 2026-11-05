
package models;

public class AudioModel extends EquipoModel {

    public AudioModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public double calcularMonto(int dias) {
        int diasEfectivo = Math.max(dias, 2);
        return getTarifaDia() * diasEfectivo;
    }

    @Override
    public String getTipo() {
        return "AUDIO";
    }
}
