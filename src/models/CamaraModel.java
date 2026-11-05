
package models;

import java.math.BigDecimal;

public class CamaraModel extends EquipoModel {

    public CamaraModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        BigDecimal monto = getTarifaDia().multiply(BigDecimal.valueOf(dias));
        monto = monto.multiply(new BigDecimal("1.15"));
        return redondear(monto);
    }

    @Override
    public String getTipo() {
        return "CAMARA";
    }
}
