
package models;

import java.math.BigDecimal;

public class AudioModel extends EquipoModel {

    public AudioModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        int diasAlquiler = Math.max(dias, 2);
        BigDecimal monto = getTarifaDia().multiply(BigDecimal.valueOf(diasAlquiler));
        return redondear(monto);
    }

    @Override
    public String getTipo() {
        return "AUDIO";
    }
}
