package models;

public abstract class EquipoModel {

    private int codigo;
    private String descripcion;
    private double tarifaDia;
    private boolean disponible;

    public EquipoModel(int codigo, String descripcion, double tarifaDia, boolean disponible) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tarifaDia = tarifaDia;
        this.disponible = disponible;
    }

    public abstract double calcularMonto(int dias);

    public abstract String getTipo();

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getTarifaDia() {
        return tarifaDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}