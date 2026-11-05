package models;

import java.math.BigDecimal;

public class CamaraModel extends EquipoModel {

    private static final BigDecimal SEGURO = new BigDecimal("1.15");

    public CamaraModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser mayores a cero.");
        }

        return getTarifaDia().multiply(BigDecimal.valueOf(dias))
                .multiply(SEGURO)
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public String getTipo() {
        return "CAMARA";
    }
}