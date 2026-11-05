package models;

import java.math.BigDecimal;

public abstract class EquipoModel {

    private final int codigo;
    private String descripcion;
    private final BigDecimal tarifaDia;
    private boolean disponible;

    public EquipoModel(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        if (codigo <= 0 || descripcion == null || descripcion.trim().isEmpty() ||
                tarifaDia == null || tarifaDia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Datos de equipo no válidos.");
        }

        this.codigo = codigo;
        this.descripcion = descripcion.trim();
        this.tarifaDia = tarifaDia;
        this.disponible = disponible;
    }

    public abstract BigDecimal calcularMonto(int dias);

    public abstract String getTipo();

    public int getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getTarifaDia() { return tarifaDia; }
    public boolean isDisponible() { return disponible; }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        this.descripcion = descripcion.trim();
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}