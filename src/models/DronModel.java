
package models;

import java.math.BigDecimal;

public class DronModel extends EquipoModel {

    public DronModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        super(codigo, descripcion, tarifaDia, disponible);
    }

    @Override
    public BigDecimal calcularMonto(int dias) {
        BigDecimal monto = getTarifaDia().multiply(BigDecimal.valueOf(dias));
        return redondear(monto);
    }

    @Override
    public String getTipo() {
        return "DRON";
    }
}
