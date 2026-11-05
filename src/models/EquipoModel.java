
package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class EquipoModel {
    private int codigo;
    private String descripcion;
    private BigDecimal tarifaDia;
    private boolean disponible;

    public EquipoModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tarifaDia = tarifaDia;
        this.disponible = disponible;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getTarifaDia() {
        return tarifaDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    protected BigDecimal redondear(BigDecimal monto) {
        return monto.setScale(2, RoundingMode.HALF_UP);
    }

    public abstract BigDecimal calcularMonto(int dias);

    public abstract String getTipo();
}
