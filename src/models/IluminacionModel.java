package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IluminacionModel extends EquipoModel {

    private static final int DIAS_PARA_DESCUENTO = 5;
    private static final BigDecimal DESCUENTO = new BigDecimal("0.90");

    public IluminacionModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser mayores a cero.");
        }

        BigDecimal subtotal = getTarifaDia().multiply(BigDecimal.valueOf(dias));

        if (dias > DIAS_PARA_DESCUENTO) {
            subtotal = subtotal.multiply(DESCUENTO);
        }

        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getTipo() {
        return "ILUMINACION";
    }
}