
package models;

import java.math.BigDecimal;

public class IluminacionModel extends EquipoModel {

    public IluminacionModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        BigDecimal monto = getTarifaDia().multiply(BigDecimal.valueOf(dias));

        if (dias > 5) {
            monto = monto.multiply(new BigDecimal("0.90"));
        }

        return redondear(monto);
    }

    @Override
    public String getTipo() {
        return "ILUMINACION";
    }
}
