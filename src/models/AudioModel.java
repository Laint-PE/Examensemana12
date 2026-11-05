package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AudioModel extends EquipoModel {

    private static final int DIAS_MINIMOS = 2;

    public AudioModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser mayores a cero.");
        }

        int diasCobro = Math.max(dias, DIAS_MINIMOS);

        return getTarifaDia().multiply(BigDecimal.valueOf(diasCobro))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getTipo() {
        return "AUDIO";
    }
}